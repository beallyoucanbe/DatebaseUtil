package com.shuoyi.txtProcess.test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaosy-c on 2017/9/5.
 */
public class GetErrorCode {

    public static void main(String[] args) throws IOException {
        //输入的文件为errorType，hardwareId，createTimeStr
        String path = "C:/Users/zhaosy-c/Desktop/errorCode001/errorCode.txt";
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        Map<String, List<String>> map = new HashMap<>();
        String str = null;
        while ((str = reader.readLine()) != null) {
            String[] strs = str.split(",");
            String key = strs[1] +", " + strs[2];
            if(map.containsKey(key)){
                map.get(key).add(strs[0]);
            }else {
                List<String> codes = new ArrayList<>();
                codes.add(strs[0]);
                map.put(key, codes);
            }
        }
        reader.close();
        System.out.println("=================================================");
//        for(Map.Entry<String, List<String>> entry: map.entrySet())
//            System.out.println(entry.getKey() + ", " + entry.getValue());
        int count = 0;
        while ((str = reader1.readLine()) != null) {
            String[] strs = str.split(",");
            if(strs[0].equals("1000")){
                String key = strs[1] +", " + strs[2];
                if(map.get(key).contains("1002"))
                    count++;
                System.out.println(key + ", " + map.get(key));
            }
        }
        reader1.close();
        System.out.println(count);
    }
}
