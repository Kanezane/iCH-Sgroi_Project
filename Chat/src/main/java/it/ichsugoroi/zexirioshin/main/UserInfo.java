package it.ichsugoroi.zexirioshin.main;

import it.ichsugoroi.zexirioshin.utils.ApplicationException;
import it.ichsugoroi.zexirioshin.utils.CloseableUtils;
import it.ichsugoroi.zexirioshin.utils.Constant;

import javax.swing.*;
import java.io.*;

public class UserInfo {

    public static String getUserNameInfo () {
        if(!checkIfRoamingDirExists()) {
            createRoamingDir();
            createUserNameFileInRoamingDir();
            return getUserNameFromFile();
        } else {
            return getUserNameFromFile();
        }
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
        } catch (FileNotFoundException e) {
            throw new ApplicationException(e);
        } catch (IOException e) {
            throw new ApplicationException(e);
        } finally {
            CloseableUtils.close(br);
        }
    }

    private static boolean checkIfRoamingDirExists() {
        File roamingDir = new File(Constant.APPDATAROAMINGPATH);
        if(roamingDir.exists() && roamingDir.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
}
