package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTests extends TestBase {

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
  public void testContactModification() {

    Set<ContactData> before = app.contact().all(); //3. Получаем список элементов ДО
    ContactData modifiedContact = before.iterator().next();

    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstname("test11")
            .withLastname("test21")
            .withAddress("test31")
            .withHomephone("12345678910")
            .withEmail("test@mail.com");

    app.contact().modifyById(contact);

    Set<ContactData> after = app.contact().all(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);

    Assert.assertEquals(before, after);


  }

}
