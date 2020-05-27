package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    Set<ContactData> before = app.contact().all(); //3. Получаем список элементов ДО

    ContactData contact = new ContactData()
            .withFirstname("test1")
            .withLastname("test2")
            .withAddress("test3")
            .withHomephone("12345678910")
            .withEmail("test@mail.com")
            .withGroup("test1");
    app.contact().create(contact);

    Set<ContactData> after = app.contact().all(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size() + 1);


    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after); // сравниваем упорядоченные по id списки, предварительно убрав сравнение по id

  }

}
