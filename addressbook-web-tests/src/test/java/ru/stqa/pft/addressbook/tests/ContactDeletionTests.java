package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("test1")
              .withLastname("test2")
              .withAddress("test3")
              .withHomephone("12345678910")
              .withEmail("test@mail.com")
              .withGroup("test1"));
    }
  }

  @Test
  public void testContactDeletion() {

    Set<ContactData> before = app.contact().all(); //3. Получаем список элементов ДО
    ContactData deletedContact = before.iterator().next();

    app.contact().delete(deletedContact);

    Set<ContactData> after = app.contact().all(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(deletedContact); //4. приводим список ДО к состоянию ПОСЛЕ
    Assert.assertEquals(before, after); // 4. сравниваем два списка

  }

}
