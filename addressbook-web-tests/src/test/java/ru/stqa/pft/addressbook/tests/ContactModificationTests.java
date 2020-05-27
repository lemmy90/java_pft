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
      app.contact().create(new ContactData()
              .setFirstname("test1")
              .setLastname("test2")
              .setAddress("test3")
              .setHomephone("12345678910")
              .setEmail("test@mail.com")
              .setGroup("test1"));
    }
  }

  @Test
  public void testContactModification() {

    List<ContactData> before = app.contact().list(); //3. Получаем список элементов ДО

    int index = before.size() - 1;

    ContactData contact = new ContactData()
            .setId(before.get(index).getId())
            .setFirstname("test11")
            .setLastname("test21")
            .setAddress("test31")
            .setHomephone("12345678910")
            .setEmail("test@mail.com");

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
