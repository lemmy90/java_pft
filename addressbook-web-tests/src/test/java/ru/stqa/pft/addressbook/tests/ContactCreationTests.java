package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    List<ContactData> before = app.getContactHelper().getContactList(); //3. Получаем список элементов ДО

    ContactData contact = new ContactData("test1", "test2", "test3", "12345678910", "test@mail.com", "test1");
    app.getContactHelper().createContact(contact);

    List<ContactData> after = app.getContactHelper().getContactList(); //3. Получаем список элементов ПОСЛЕ того как создан новый контакт
    Assert.assertEquals(after.size(), before.size() + 1);


//    int max = 0;
//    for (ContactData g : after) {
//      if (g.getId() > max) {
//        max = g.getId();
//      }
//    }
    
    int max = after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();
    contact.setId(max);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after)); // 5. преобразование списка во множество

  }

}
