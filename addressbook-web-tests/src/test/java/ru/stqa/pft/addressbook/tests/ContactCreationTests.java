package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    List<ContactData> before = app.getContactHelper().getContactList(); //3. Получаем список элементов ДО

    app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1"));

    List<ContactData> after = app.getContactHelper().getContactList(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size() + 1);
  }

}
