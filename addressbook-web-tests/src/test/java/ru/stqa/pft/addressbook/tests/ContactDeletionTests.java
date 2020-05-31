package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("test1")
              .withLastname("test2")
              .withAddress("test3")
              .withHomePhone("12345678910")
              .withEmail("test@mail.com")
              .withGroup("test1"));
    }
  }

  @Test
  public void testContactDeletion() {

    Contacts before = app.contact().all(); //3. Получаем список элементов ДО
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    assertThat(app.contact().count(), equalTo(before.size() - 1));


    Contacts after = app.contact().all(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    assertThat(after, equalTo(before.withOut(deletedContact)));

  }

}
