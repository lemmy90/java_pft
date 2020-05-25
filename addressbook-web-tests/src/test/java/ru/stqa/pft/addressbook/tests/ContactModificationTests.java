package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){

    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1"));

    }

    List<ContactData> before = app.getContactHelper().getContactList(); //3. Получаем список элементов ДО

    app.getContactHelper().initContactModification(before.size() - 1); //2. выбираем на изменение последний элемент
    app.getContactHelper().fillContactForm(new ContactData("test11", "test21", "test31", "12345678910", "test@mail.com", null), false);
    app.getContactHelper().initContactUpdate();
    app.getNavigationHelper().returnToHomePage();

    List<ContactData> after = app.getContactHelper().getContactList(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size());


  }
}
