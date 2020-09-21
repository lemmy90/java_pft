package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper {

    private ApplicationManager app;
    private TelnetClient telnet;
    private InputStream in;
    private PrintStream out;

    private Session mailSession;
    private Store store;
    private String mailserver;

    public JamesHelper(ApplicationManager app) {
        this.app = app;
        telnet = new TelnetClient(); //при инициализации JamesHelper создаётся Telnet клиент
        mailSession = Session.getDefaultInstance(System.getProperties()); // создаётся почтовая сессия
    }

//    public boolean doesUserExists(String name) {
//        initTelnetSession();
//        write("verify " + name);
//        String result = readUntil("exists");
//        closeTelnetSession();
//        return run.trim().equals("User " + name + "exists");
//
//    }

    public void createUser(String name, String passwd){
        initTelnetSession(); // устанавливается соединение
        write("adduser " + name + " " + passwd); //пишем команду
        String result = readUntil("User " + name + " added"); //ждём пока не появится текст
        closeTelnetSession();
    }

//    public void deleteUser(String name) {
//        initTelnetSession();
//        write("deluser " + name + " deleted");
//        closeTelnetSession();
//    }

    private void initTelnetSession() {
        mailserver = app.getProperty("mailserver.host");
        int port = Integer.parseInt(app.getProperty("mailserver.port"));
        String login = app.getProperty("mailserver.adminlogin");
        String password = app.getProperty("mailserver.adminpassword");

        try {
            telnet.connect(mailserver, port); //устанавливаем соединение с сервером
            in = telnet.getInputStream(); // берём входной поток для того чтобы читать что сервер отправляет нам
            out = new PrintStream(telnet.getOutputStream()); //берём выходной поток чтобы что-то ему писать

        } catch (Exception e) {
            e.printStackTrace();
        }

        //First attempt
        readUntil("Login id:");
        write(" ");
        readUntil("Password:");
        write(" ");

        //Second attempt
        readUntil("Login id:");
        write(login);
        readUntil("Password:");
        write(password);

        //Read welcome message
        readUntil("Welcome " + login + ". HELP for a list of commands");
    }

    private String readUntil(String pattern) {
        try {
            char lastChar  = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            while (true) {
                System.out.println(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String value) {
        try {
            out.println(value);
            out.flush();
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeTelnetSession() {
        write("quit");
    }

//    МЕТОД ОЧИЩЕНИЯ ПОЧТОВОГО ЯЩИКА ДЛЯ ПОЛЬЗОВАТЕЛЯ (ЕЛИ НАДО МНОГКРАТНО ИСПОЛЬЗОВАТЬ ОДИН И ТОТ ЖЕ ЯЩИК)
//    public void drainEmail(String username, String password) throws MessagingException {
//        Folder inbox = openInbox(username, password);
//        for (Message message : inbox.getMessages()) {
//            message.setFlag(Flags.Flag.DELETED,true);
//        }
//        closeFolder(inbox);
//    }

    public void closeFolder(Folder folder) throws MessagingException {
        folder.close(true); //параметр true означает, что надо удалить все письма помеченные к удалению
        store.close(); // закрываем соединение с почтовым сервером
    }

    private Folder openInbox(String username, String password) throws MessagingException {
        store = mailSession.getStore("pop3"); //берём почтовую сессию
        store.connect(mailserver, username, password); //устанавливаем соединение
        Folder folder = store.getDefaultFolder().getFolder("INBOX"); //получаем доступ к папке. По протоколу pop3 получить доступ можно только к ней
        folder.open(Folder.READ_WRITE); //открываем на чтение и на запись
        return folder;
    }

    public List<MailMessage> waitForMail (String username, String password, long timeout) throws MessagingException {
        long now = System.currentTimeMillis(); //запоминаем момент начала ожидания
        while (System.currentTimeMillis() < now + timeout) { //проверяем, что текущее время не привышает момента старта + таймаут
            List<MailMessage> allMail = getAllMail(username, password);
            if (allMail.size() > 0) { //если в ящике есть писма - возвращаем их
                return allMail;
            }
            try {
                Thread.sleep(1000); //если писем нет, то ждём 1 секунду и идём на второй заход
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Error("No mail :("); //если время истекло
    }

    public List<MailMessage> getAllMail(String username, String password) throws MessagingException {
        Folder inbox = openInbox(username, password); //открываем почтовый ящик
        //берём список писем, превращаем в поток, применяем функцию, которая первращает их в модельные объекты
        //собираем в список
        List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
        closeFolder(inbox); //закрываем почтовый ящик
        return messages;
    }

    public static MailMessage toModelMail(Message m) {
        try {
            return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
