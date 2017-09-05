package com.shuoyi.txtProcess;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaosy-c on 2017/9/4.
 */
public class TxtPro {

    public static void readByLine(String path) throws Exception {
        File file = new File(path);
        //理解各种文件流的处理方式，原始流和链接流
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        Map<String, List<String>> res = new HashMap<String, List<String>>();
        String str = null;
        while((str = reader.readLine()) != null){
            String[] strs = str.split(",");
            String key = strs[0];
            if(res.containsKey(key))
                res.get(key).add(strs[1]);
            else{
                List<String> clients = new ArrayList<String>();
                clients.add(strs[1]);
                res.put(key, clients);
            }
        }
    }

    public static void readByChar(String path1, String path2) throws Exception {
        File file1 = new File(path1);
        File file2 = new File(path2);
        FileReader fileReader = new FileReader(file1);
        FileWriter fileWriter = new FileWriter(file2);

        int ch = 0;
        while((ch = fileReader.read()) != -1){
//			if ((char)ch == ':')
//				fileWriter.write("=>");
//			else if ((char)ch == ' ' || (char)ch == '\r'  || (char)ch == '\n')
            if ((char)ch == '{' || (char)ch == '}')
                continue;
            else if ((char)ch == ','){
                fileReader.read();
                fileReader.read();
            }
            else if ((char)ch == '/'){
                fileReader.read();
                fileReader.read();
                fileReader.read();
                fileReader.read();
                fileReader.read();
            }
            else
                fileWriter.write((char)ch);
        }
        fileWriter.flush();
        fileReader.close();
        fileWriter.close();
    }


    public static void main(String[] args) throws Exception {
        String path1 = "C:/Users/zhaosy-c/Desktop/beforeprocess.txt";
        String path2 = "C:/Users/zhaosy-c/Desktop/afterprocess.txt";
        readByChar(path1, path2);
    }
}
