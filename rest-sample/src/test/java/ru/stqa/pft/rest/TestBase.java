package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;

import java.io.IOException;
import java.util.Set;

public class TestBase {
    protected Set<Issue> getIssues() throws IOException {
        String json = getExecutor()
                .execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
                .returnContent()
                .asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");

        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());

    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    protected int createIssue(Issue newIssues) throws IOException {

        String json = getExecutor()
                .execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                        .bodyForm(new BasicNameValuePair("subject", newIssues.getSubject()),
                                new BasicNameValuePair("description", newIssues.getDescription())))
                .returnContent()
                .asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    public String getIssueStatus(int issueId) throws IOException {
        String status = "";
        String json = getExecutor()
                .execute(Request.Get("https://bugify.stqa.ru/api/issues/" + issueId + ".json"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonArray issues = (JsonArray) parsed.getAsJsonObject().get("issues");
        for (JsonElement issue : issues) {
            status = issue.getAsJsonObject().get("state_name").getAsString();
        }
        return status;
    }

    private boolean isIssueOpen(int issueId) throws IOException {
        String status = getIssueStatus(issueId);
        if (status.equals("Resolved") || status.equals("Closed")) {
            return false;
        } else {
            return true;

        }
    }
}
