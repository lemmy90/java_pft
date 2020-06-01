package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    Contacts before = app.contact().all(); //3. Получаем список элементов ДО

    File photo = new File("src/test/resources/test.png");
    ContactData contact = new ContactData()
            .withFirstname("test1")
            .withLastname("test2")
            .withAddress("test3")
            .withHomePhone("12345678910")
            .withEmail("test@mail.com")
            .withGroup("test1")
            .withPhoto(photo);
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));


    Contacts after = app.contact().all(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

  }


}
