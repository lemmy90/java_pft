package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

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
  public void testContactDeletion() {

    List<ContactData> before = app.contact().list(); //3. Получаем список элементов ДО
    int index = before.size() - 1;

    app.contact().delete(index);

    List<ContactData> after = app.contact().list(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index); //4. приводим список ДО к состоянию ПОСЛЕ
    Assert.assertEquals(before, after); // 4. сравниваем два списка

  }

}
