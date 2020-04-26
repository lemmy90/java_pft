package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.initContactCreation();
    app.fillContactForm(new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com"));
    app.submitContactCreation();
    app.returnToHomePage();
  }

}