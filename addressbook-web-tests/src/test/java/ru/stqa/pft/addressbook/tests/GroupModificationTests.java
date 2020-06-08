package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().groupPage();

    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModification() {

    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();

    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test1").withHeader("test2").withFooter("test3");

    app.group().modify(group);
    assertThat(app.group().count(),equalTo(before.size()));

    Groups after = app.db().groups();
    before.remove(modifiedGroup);
    before.add(group);

    assertEquals(before, after);
    assertThat(after, equalTo(before.withOut(modifiedGroup).withAdded(group)));
  }


}
