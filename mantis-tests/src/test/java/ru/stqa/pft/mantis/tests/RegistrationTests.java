package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

    //@BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }


    @Test
    public void testRegistration() throws IOException, MessagingException {
        long now = System.currentTimeMillis();
        //String email = String.format("nadia%s@localhost.localdomain",now); //this is for inside server
        String email = String.format("nadia%s@localhost",now); //this is for the James server
        String user = String.format("nadia%s",now);
        String password = "password";
        app.james().createUser(user, password);
        app.registration().start(user, email);
        //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000); //this is for inside server
        List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000); //this is for the James server
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password, user);
        assertTrue(app.newSession().login(user,password));

    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get(); //find email sent to
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build(); //find in email part with the link
        return regex.getText(mailMessage.text); //ge the text with the link

    }

    //@AfterMethod (alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
