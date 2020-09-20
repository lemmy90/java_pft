package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {
    private final ApplicationManager app;
    private FTPClient ftp;

    public FtpHelper(ApplicationManager app){
        this.app = app;
        ftp = new FTPClient(); //initialisation, creation of FTP Client
    }

    public void upload(File file, String target, String backup) throws IOException {
        // file - local file to be uploaded to the sever
        // target - name of the file
        // backup - name of the reserve copy, if the file is already exists
        ftp.connect(app.getProperty("ftp.host")); // connect to server
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password")); //login to server
        ftp.deleteFile(backup); //delete previous reserve copy of backup
        ftp.rename(target, backup);
        ftp.enterLocalPassiveMode();
        ftp.storeFile(target, new FileInputStream(file)); //send file
        ftp.disconnect();
    }

    public void restore(String backup, String target) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        ftp.deleteFile(target);
        ftp.rename(backup, target); //restore file from the backup copy
        ftp.disconnect();
    }
}
