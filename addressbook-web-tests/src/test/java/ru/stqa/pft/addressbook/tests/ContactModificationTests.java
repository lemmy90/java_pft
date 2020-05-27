package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1"));

    }

    List<ContactData> before = app.getContactHelper().getContactList(); //3. Получаем список элементов ДО

    app.getContactHelper().initContactModification(before.size() - 1); //2. выбираем на изменение последний элемент
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "test11", "test21", "test31", "12345678910", "test@mail.com", null);
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().initContactUpdate();
    app.getNavigationHelper().returnToHomePage();

    List<ContactData> after = app.getContactHelper().getContactList(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);


  }
}
