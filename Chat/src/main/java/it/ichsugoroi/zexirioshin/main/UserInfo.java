package it.ichsugoroi.zexirioshin.main;

import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.CloseableUtils;
import it.ichsugoroi.zexirioshin.utils.Constant;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserInfo {

    static String getUserNameInfo() {
        if(!checkIfRoamingDirExists()) {
            createRoamingDir();
            createUserNameFileInRoamingDir();
            return getUserNameFromFile();
        } else {
            return getUserNameFromFile();
        }
    }

    public static User getUserInfo() {
        if(checkIfRoamingDirExists()) {
            return getUserInfoFromFile();
        } else {
            throw new ApplicationException("Impossibile trovare info user perché non esiste neanche la cartella delle info!");
        }
    }

    public static void createUserNameFileInRoamingDir(String username, String password) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(Constant.USERNAMEFILE, "UTF-8");
            System.out.println(username + " " + password);
            writer.println(username);
            writer.println(password);
        } catch (FileNotFoundException e) {
            throw new ApplicationException("File " + Constant.USERNAMEFILE + " not found!", e);
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException(e);
        } finally {
            CloseableUtils.close(writer);
        }
    }

    public static void deleteUserNameFolderIfExists() {
        File roamingDir = new File(Constant.APPDATAROAMINGPATH);
        if(roamingDir.exists()) {
            System.out.println("deleting roaming directory...");
            deleteFolder(roamingDir);
        }
    }

    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    private static void createUserNameFileInRoamingDir() {
        String username = JOptionPane.showInputDialog("Insert Username");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(Constant.USERNAMEFILE, "UTF-8");
            writer.println(username);
        } catch (FileNotFoundException e) {
            throw new ApplicationException("File " + Constant.USERNAMEFILE + " not found!", e);
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException(e);
        } finally {
            CloseableUtils.close(writer);
        }
    }

    public static void createRoamingDirIfNotAlreadyExists() {
        if(!checkIfRoamingDirExists()) { createRoamingDir(); }
    }

    private static void createRoamingDir() {
        File roamingDir = new File(Constant.APPDATAROAMINGPATH);
        if(!roamingDir.exists()) {
            System.out.println("Creating roaming directory...");
            if(roamingDir.mkdir()) {
                System.out.println("Roaming directory successfully created!");
            } else {
                throw new ApplicationException("An error occured during roaming dir creation!");
            }
        }
    }

    private static String getUserNameFromFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Constant.USERNAMEFILE));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            throw new ApplicationException(e);
        } finally {
            CloseableUtils.close(br);
        }
    }

    public static boolean checkIfRoamingDirExists() {
        File roamingDir = new File(Constant.APPDATAROAMINGPATH);
        return roamingDir.exists() && roamingDir.isDirectory();
    }

    private static User getUserInfoFromFile() {
        BufferedReader br = null;
        try {
            User res = new User();
            br = new BufferedReader(new FileReader(Constant.USERNAMEFILE));
            List<String> infoFromFile = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                infoFromFile.add(line.trim());
                line = br.readLine();
            }

            if(infoFromFile.size()!=2) {
                throw new ApplicationException("Impossibile, formato file inatteso!");
            } else {
                res.setName(infoFromFile.get(0));
                res.setPassword(infoFromFile.get(1));
            }

            return res;
        } catch (IOException e) {
            throw new ApplicationException(e);
        } finally {
            CloseableUtils.close(br);
        }
    }
}
