package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    Groups groups = app.db().groups();
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("test1")
              .withLastname("test2")
              .withAddress("test3")
              .withHomePhone("111")
              .withMobilePhone("222")
              .withWorkPhone("333")
              .withEmail("test@mail.com")
              .withEmail2("test@google.com")
              .inGroup(groups.iterator().next()));
    }
  }

  @Test
  public void testContactDeletion() {

    Contacts before = app.db().contacts(); //3. Получаем список элементов ДО
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    assertThat(app.contact().count(), equalTo(before.size() - 1));


    Contacts after = app.db().contacts(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    assertThat(after, equalTo(before.withOut(deletedContact)));

    verifyContactListUI();

  }

}
