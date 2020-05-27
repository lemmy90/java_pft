package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomephone());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }

  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void initContactModification(int index) {
    click(By.xpath("//table[@id='maintable']/tbody/tr[" + (index + 1) + "]/td[8]"));
  }

  public void initContactUpdate() {
    click(By.name("update"));
  }

  public void viewContact() {
    click(By.xpath("//img[@alt='Details']"));
  }

  public void markCheckbox(int index) {

    wd.findElements(By.name("selected[]")).get(index).click(); //2. выбираем элемент по индексу
    //click(By.name("selected[]"));
    //click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input"));
  }

  public void delete() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    returnToHomePage();

  }

  public void modify(int index, ContactData contact) {
    initContactModification(index); //2. выбираем на изменение последний элемент
    fillContactForm(contact, false);
    initContactUpdate();
    returnToHomePage();
  }

  public void delete(int index) {
    markCheckbox(index); //2. Выбираем на удаление последний элемент
    delete();
    acceptAlert();
    returnToHomePage();
  }

  private void returnToHomePage() {
    click(By.linkText("home"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input"));
  }

  // 1. создаём метод который возвращает размер списка по элементам
  public int getContactCount() {

    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();

    List<WebElement> elements = wd.findElements(By.name("entry"));

    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
        String lastName = cells.get(1).getText();
        String firstName = cells.get(2).getText();
        int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));

        ContactData contact = new ContactData()
                .setId(id)
                .setFirstname(firstName)
                .setLastname(lastName);

        contacts.add(contact);
    }
    return contacts;
  }

}
