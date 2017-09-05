package com.shuoyi.mongoTest;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.shuoyi.common.mongo.MongodbUtil;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by zhaosy-c on 2017/9/4.
 */
public class Test {

    private static DBCollection collection = MongodbUtil.getDBCollection();


    /**
     * 方法用来统计某月每天的错误码出现次数
     */
    public static void getErrorcodeByDay() {
        String[] datas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String[] errorcodes = {"1001", "1002", "1003", "1004", "1006"};
        long[] res = new long[datas.length];

        for (int j = 0; j < errorcodes.length; j++) {
            String errorcode = errorcodes[j];
            System.out.println(errorcode);
            for (int i = 0; i < datas.length; i++) {
                BasicDBObject query = new BasicDBObject();
                Pattern queryPattern = Pattern.compile("2017-08-" + datas[i], Pattern.CASE_INSENSITIVE);
                query.put("createTimeStr", queryPattern);
                query.put("errorType", errorcode);
                long count = collection.count(query);
                System.out.println(count);
                res[i] = count;
            }
            System.out.println(Arrays.toString(res));
        }
    }

    public static List<String> getClient(String path) throws IOException {

        File file = new File(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        List<String> client = new ArrayList<>();
        String line = null;
        while((line = br.readLine()) != null){
            client.add(line.split(",")[0]);
        }
        return client;
    }
    public static void main(String[] args) throws IOException {
        String path08 = "C:/Users/zhaosy-c/Desktop/errorCode001/001.txt";
        String path07 = "C:/Users/zhaosy-c/Desktop/errorCode001/002.txt";
        String path06 = "C:/Users/zhaosy-c/Desktop/errorCode001/001.txt";
        List<String> client08 = getClient(path08);
        List<String> client07 = getClient(path07);
        List<String> client06 = getClient(path06);
        System.out.println(client08.size());
        System.out.println(client07.size());
        System.out.println(client06.size());
        Set<String> set = new HashSet<>(client08);
        for(String client: client07)
            set.add(client);
//        for(String client: client06)
//            set.add(client);

        System.out.println(set.size());
    }
}
