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
      app.contact().create(new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1"));

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
