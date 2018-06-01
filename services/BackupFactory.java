package services;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BackupFactory {

    private String database;
    private String username;
    private String password;

    public BackupFactory(String database, String username, String password) {
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void makeBackup() {
        int numerodobackup = 0;
        String dir = "c:/TecnutrionBackup";
        File diretorio = new File(dir);
        File bck = new File(dir + "/backup000000.sql");
// os zeros é para diferenciar um backup do outro
        // Cria diretório
        if (!diretorio.isDirectory()) {
            new File(dir).mkdir();
        } else {
        }
                // Cria Arquivo de Backup
        try {
            if (!bck.isFile()) {
                Runtime.getRuntime().exec("cmd /c mysqldump -u " + username + " -p" + password + " " + database + " > "+ dir + "/backup000000.sql");
                     
            } else {
                while (bck.isFile()) {
                    numerodobackup++;
                    bck = new File(dir + "/backup000000" + numerodobackup + ".sql");
                }
                System.out.println(bck);
                Runtime.getRuntime().exec("cmd /c mysqldump -u " + username + " -p" + password + " " + database + " > " + bck);
            }
        } catch (IOException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

}
