package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class EditHelper extends HelperBase {

    public EditHelper(ApplicationManager app) {
        super(app);
    }

    public void user(String user) {
        if (!isElementPresent(By.linkText("Manage Users")) ) {
            click(By.xpath("//li[6]/a/span"));
        }
        if (!wd.findElement(By.tagName("h4")).getText().equals("\n" + "\tManage Accounts\t")) {
            click(By.linkText("Manage Users"));
        }
        click(By.linkText(user));
    }

    public void passwordBegin() {
        click(By.cssSelector("input[value='Reset Password']"));
    }

    public void passwordFinish(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("button[type='submit']"));
    }

}

