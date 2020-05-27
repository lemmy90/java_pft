package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    Contacts before = app.contact().all(); //3. Получаем список элементов ДО

    ContactData contact = new ContactData()
            .withFirstname("test1")
            .withLastname("test2")
            .withAddress("test3")
            .withHomephone("12345678910")
            .withEmail("test@mail.com")
            .withGroup("test1");
    app.contact().create(contact);

    Contacts after = app.contact().all(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    assertThat(after.size(), equalTo(before.size() + 1));

    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

  }

}
