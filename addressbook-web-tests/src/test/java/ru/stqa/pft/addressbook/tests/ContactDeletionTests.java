package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1"));

    }
  }

  @Test
  public void testContactDeletion() {

    List<ContactData> before = app.getContactHelper().getContactList(); //3. Получаем список элементов ДО

    app.getContactHelper().markCheckbox(before.size() - 1); //2. Выбираем на удаление последний элемент
    app.getContactHelper().deleteContact();
    app.getContactHelper().acceptAlert();
    app.goTo().returnToHomePage();

    List<ContactData> after = app.getContactHelper().getContactList(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(before.size() - 1); //4. приводим список ДО к состоянию ПОСЛЕ
    Assert.assertEquals(before, after); // 4. сравниваем два списка

  }
}
