package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdditionToGroup extends TestBase {

    @BeforeMethod
    public void  ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        app.goTo().returnToHomePage();
    }

    @Test
    public void testContactAdditionToGroup() {
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        if (!contacts.stream().filter((s) -> (s.getGroups().size() < groups.size())).findAny().isPresent()) {
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
        ContactData addedContact =
                app.db().contacts().stream().filter((s) -> (s.getGroups().size() < groups.size())).findAny().get();
        Groups before = addedContact.getGroups();
        GroupData group = groups.without(addedContact.getGroups()).iterator().next();
        app.contact().addToGroup(addedContact, group);
        Groups after = app.db().contacts().stream().filter((s) -> s.equals(addedContact)).findFirst().get().getGroups();
        assertThat(after, equalTo(before.withAdded(group)));
    }
}

