
using Microsoft.VisualBasic;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.Diagnostics;
public class FindClassPath {

    private static string binFolderPath = Environment.CurrentDirectory;

    public string find(){
        String[] binFolder = binFolderPath.Split('\\');  
        string classPath = "";
        for (int cont = 0; cont <= binFolder.Length - 3; cont++)
        {
            if (cont == 0)
            {
                classPath = classPath + binFolder[cont];
            }
            else {
                classPath = classPath + "\\" + binFolder[cont];
            }
        }
        return classPath;
    }
}
