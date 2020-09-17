package ru.stqa.pft.mantis.appmanager;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.NameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
    private CloseableHttpClient httpClient;
    private ApplicationManager app;

    public HttpSession(ApplicationManager app){
        this.app = app;
        httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    public boolean login(String username, String password) throws IOException {
        HttpPost post = new HttpPost(app.getProperty("web.baseURL") + "/login.php"); //create new empty request
        List<BasicNameValuePair> params = new ArrayList<>(); //form the set of parameters
        params.add(new BasicNameValuePair("username", username)); //form the set of parameters
        params.add(new BasicNameValuePair("password", password)); //form the set of parameters
        params.add(new BasicNameValuePair("secure_session", "on")); //form the set of parameters
        params.add(new BasicNameValuePair("return", "index.php")); //form the set of parameters
        post.setEntity(new UrlEncodedFormEntity(params)); //puck this parameters into the post.sentEntity
        CloseableHttpResponse response = httpClient.execute(post); //send the request
        String body = getTextForm(response); //read the response
        return body.contains(String.format("<span class=\"italic\">%s</span>", username));

    }

    private String getTextForm(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
    }

    public boolean isLoggedInAs(String username) throws IOException {
        HttpGet get = new HttpGet(app.getProperty("web.baseURL") + "/login.php");
        CloseableHttpResponse response = httpClient.execute(get);
        String body = getTextForm(response);
        return body.contains(String.format("<span class=\"italic\">%s</span>", username));
    }

}
