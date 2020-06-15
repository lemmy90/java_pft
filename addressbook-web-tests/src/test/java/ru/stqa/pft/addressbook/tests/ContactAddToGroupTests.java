package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactAddToGroupTests extends TestBase {

  @BeforeMethod
  public void ensureContactExists() {
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("test1")
              .withLastname("test2")
              .withAddress("test3")
              .withHomePhone("111")
              .withMobilePhone("222")
              .withWorkPhone("333")
              .withEmail("test@mail.com")
              .withEmail2("test@google.com"));
    }
  }

  @BeforeMethod
  public void ensureGroupExists(){
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testContactAddToGroup() {
    /*
    0. В базе данных проверяем количество групп для контакта
    1. Заходим на главную страницу
    2. Выбираем контакт
    3. Выбираем группу в селект боксе
    4. Нажимаем кнопку Add
    5. Проверяем в базе данных количество групп для контакта увеличилось на 1
     */

    app.goTo().returnToHomePage();

    ContactData modifiedContact = app.db().contacts().iterator().next(); // выбираем контакт
    Groups before = modifiedContact.getGroups(); // получаем список групп для выбранного контакта

    GroupData groupToAdd = app.db().groups().iterator().next(); // выбираем группу в которую будем добавлять
    app.contact().addContactToGroup(modifiedContact, groupToAdd); // добавляем группу

    Groups after = modifiedContact.getGroups(); // получаем список групп для выбранного контакта после добавления

    System.out.println(before);
    System.out.println(after);



  }

}
