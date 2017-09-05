package com.shuoyi.mongoTest;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.shuoyi.common.mongo.MongodbUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by zhaosy-c on 2017/9/4.
 */
public class MongoTest2 {

    public static void main(String[] args) throws IOException {

        File file = new File("C:/Users/zhaosy-c/Desktop/新建文本文档.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        List<String> keys = new ArrayList<String>();
        String str = null;
        while ((str = reader.readLine()) != null) {
            keys.add(str);
        }
        reader.close();

        DBCollection collection = MongodbUtil.getDBCollection();
        for (String key : keys) {
            BasicDBObject query = new BasicDBObject();
            Pattern queryPattern = Pattern.compile("2017-07", Pattern.CASE_INSENSITIVE);
            query.put("createTimeStr", queryPattern);
            query.put("hardwareId", key);
            long count = collection.count(query);
            System.out.println(count);
        }
    }

}
