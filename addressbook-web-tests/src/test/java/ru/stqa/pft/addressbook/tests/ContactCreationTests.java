package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    int before = app.getContactHelper().getContactCount(); //1. создаём метод определяющий кол-во элементов ДО

    app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1"));

    int after = app.getContactHelper().getContactCount(); //1. создаём метод определяющий кол-во элементов ПОСЛЕ
    Assert.assertEquals(after, before + 1);
  }

}
