package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.Arrays;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        File folder = new File(args[0]);
        Arrays.sort(folder.listFiles());
        FileUtils.renameFile(new File (args[1]),new File("allFilesContent.txt"));
        for (File file: folder.listFiles()){
            if(file.length()<=50){
                FileWriter fos = new FileWriter(new File(args[1]));
                fos.write(file.);
            }
        }
    }
}
