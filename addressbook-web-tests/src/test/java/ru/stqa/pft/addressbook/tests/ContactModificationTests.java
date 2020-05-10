package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){

    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("test11", "test21", "test31", "12345678910", "test@mail.com", null), false);
    app.getContactHelper().initContactUpdate();
    app.getNavigationHelper().returnToHomePage();


  }
}
