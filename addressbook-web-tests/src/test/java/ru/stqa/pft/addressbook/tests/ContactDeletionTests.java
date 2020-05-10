package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {

    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1"));

    }
    app.getContactHelper().markCheckbox();
    app.getContactHelper().deleteContact();
    app.getContactHelper().acceptAlert();
    app.getNavigationHelper().returnToHomePage();

  }
}
