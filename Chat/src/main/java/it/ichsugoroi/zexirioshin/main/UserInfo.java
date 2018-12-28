package it.ichsugoroi.zexirioshin.main;

import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.CloseableUtils;
import it.ichsugoroi.zexirioshin.utils.StringReferences;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserInfo {

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
            writer = new PrintWriter(StringReferences.USERNAMEFILE, "UTF-8");
            System.out.println(username + " " + password);
            writer.println(username);
            writer.println(password);
        } catch (FileNotFoundException e) {
            throw new ApplicationException("File " + StringReferences.USERNAMEFILE + " not found!", e);
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException(e);
        } finally {
            CloseableUtils.close(writer);
        }
    }

    public static void deleteUserNameFolderIfExists() {
        File roamingDir = new File(StringReferences.APPDATAROAMINGPATH);
        if(roamingDir.exists()) {
            System.out.println("deleting roaming directory...");
            deleteFolder(roamingDir);
        }
    }

    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    if(f.delete()) {
                        System.out.println("File " + f.getPath() + " deleted successfully");
                    } else {
                        throw new ApplicationException("Error occured removing " + f.getPath() + " file");
                    }
                }
            }
        }
        if(folder.delete()) {
            System.out.println("Folder " + folder.getPath() + " deleted successfully");
        } else {
            throw new ApplicationException("Error occured removing " + folder.getPath() + " folder");
        }
    }

    public static void createRoamingDirIfNotAlreadyExists() {
        if(!checkIfRoamingDirExists()) { createRoamingDir(); }
    }

    private static void createRoamingDir() {
        File roamingDir = new File(StringReferences.APPDATAROAMINGPATH);
        if(!roamingDir.exists()) {
            System.out.println("Creating roaming directory...");
            if(roamingDir.mkdir()) {
                System.out.println("Roaming directory successfully created!");
            } else {
                throw new ApplicationException("An error occured during roaming dir creation!");
            }
        }
    }

    public static boolean checkIfRoamingDirExists() {
        File roamingDir = new File(StringReferences.APPDATAROAMINGPATH);
        return roamingDir.exists() && roamingDir.isDirectory();
    }

    private static User getUserInfoFromFile() {
        BufferedReader br = null;
        try {
            User res = new User();
            br = new BufferedReader(new FileReader(StringReferences.USERNAMEFILE));
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
