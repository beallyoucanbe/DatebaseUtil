package com.shuoyi.test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaosy-c on 2017/9/4.
 */
public class MongoTest {

    public static void main(String[] args) throws IOException {

        File file = new File("C:/Users/zhaosy-c/Desktop/afterprocedss.txt");
        File ofile = new File("C:/Users/zhaosy-c/Desktop/data.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ofile)));

        Map<String, List<String>> res = new HashMap<String, List<String>>();
        Map<String, Integer> counts = new HashMap<String, Integer>();
        String str = null;
        while ((str = reader.readLine()) != null) {
            String[] strs = str.split(",");
            String client = strs[1];
            if (res.containsKey(client)) {
                res.get(client).add(strs[0]);
                counts.put(client, counts.get(client) + Integer.parseInt(strs[2]));
            } else {
                List<String> keys = new ArrayList<String>();
                keys.add(strs[0]);
                res.put(client, keys);
                counts.put(client, Integer.parseInt(strs[2]));
            }
        }

        reader.close();
        int count = 0;
        System.out.println(res.size());
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (Map.Entry<String, List<String>> entry : res.entrySet()) {
            count += entry.getValue().size();
            if (entry.getValue().size() >= 1 && entry.getValue().size() < 2)
                result.put(entry.getKey(), entry.getValue().size());
        }

        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            String string = entry.getKey() + " " + entry.getValue() + " " + counts.get(entry.getKey());
            System.out.println(string);
            writer.write(string, 0, string.length());
            writer.newLine();
        }
        writer.flush();
        writer.close();
        System.out.println(count);
    }

}
