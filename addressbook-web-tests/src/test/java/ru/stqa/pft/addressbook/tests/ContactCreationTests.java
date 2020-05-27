package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    List<ContactData> before = app.contact().list(); //3. Получаем список элементов ДО

    ContactData contact = new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1");
    app.contact().create(contact);

    List<ContactData> after = app.contact().list(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size() + 1);

    //Нахождение максимума без пафоса
//    int max = 0;
//    for (ContactData g : after) {
//      if (g.getId() > max) {
//        max = g.getId();
//      }
//    }

    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after); // сравниваем упорядоченные по id списки, предварительно убрав сравнение по id

  }

}
