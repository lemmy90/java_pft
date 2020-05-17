package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.security.acl.Group;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {

    app.getNavigationHelper().gotoGroupPage();

    List<GroupData> before = app.getGroupHelper().getGroupList();
    GroupData group = new GroupData("test2", null, "test3");
    app.getGroupHelper().createGroup(group);

    List<GroupData> after = app.getGroupHelper().getGroupList();

    Assert.assertEquals(after.size(), before.size()+1);

//    int max = 0; //find maximum of all group id
//    for (GroupData g : after) {
//      if (g.getId() > max){
//        max = g.getId();
//      }
//    }

    group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(),o2.getId())).get().getId()); //with lambda
    before.add(group);

    //сортировка по id
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before,after);


    //Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after)); //преобразование списка во множество
  }

}
