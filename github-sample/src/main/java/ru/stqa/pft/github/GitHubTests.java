package ru.stqa.pft.github;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import org.testng.annotations.Test;

public class GitHubTests {

    @Test
    public void testCommits() {

        Github github = new RtGithub(".. your OAuth token ..");

    }

}
