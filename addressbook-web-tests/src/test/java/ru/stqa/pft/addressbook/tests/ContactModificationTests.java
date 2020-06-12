package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
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
  public void testContactModification() {

    Contacts before = app.db().contacts(); //3. Получаем список элементов ДО
    ContactData modifiedContact = before.iterator().next();

    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstname("test11")
            .withLastname("test21")
            .withAddress("test31")
            .withHomePhone("12345678910")
            .withMobilePhone("123")
            .withWorkPhone("123")
            .withEmail("test@mail.com")
            .withEmail2("test@mail.com")
            .withEmail3("test@mail.com");

    app.contact().modifyById(contact);
    assertThat(app.contact().count(), equalTo(before.size() ));


    Contacts after = app.db().contacts(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    assertThat(after, equalTo(before.withOut(modifiedContact).withAdded(contact)));

    verifyContactListUI();
  }

}
