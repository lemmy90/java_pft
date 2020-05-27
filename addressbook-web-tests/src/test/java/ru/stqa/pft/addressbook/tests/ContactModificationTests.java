package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1"));

    }
  }

  @Test
  public void testContactModification() {

    List<ContactData> before = app.contact().list(); //3. Получаем список элементов ДО

    int index = before.size() - 1;

    ContactData contact = new ContactData(before.get(index).getId(), "test11", "test21", "test31", "12345678910", "test@mail.com", null);
    app.contact().modify(index, contact);

    List<ContactData> after = app.contact().list(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);


  }

}
