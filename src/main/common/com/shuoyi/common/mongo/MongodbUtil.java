package com.shuoyi.common.mongo;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by zhaosy-c on 2017/9/4.
 */
public class MongodbUtil {


        private static MongoClient oClient;
        private static String sServerAddr;
        private static String sDbName;
        private static String sCollectionName;
        private static DB oDefDb;
        private static DBCollection oCollection;

        private final static int CConPoolSize = 30; // 连接数量
        private final static int CBlockSize = 10; // 连接最大等待线程数
        private final static int CMaxWaitTime = 5000; // 等待连接的最长时间
        private final static int CConTimeOut = 5000; // 连接超时时间

        private static ResourceBundle bundle = ResourceBundle.getBundle("Mongodb");

        static {
            initDB();
        }
        /**
         * 连接的初始化
         */
        private static void initDB(){
            sServerAddr = bundle.getString("Mongodb.serverAddr");
            sDbName = bundle.getString("Mongodb.defDbName");
            sCollectionName = bundle.getString("Mongodb.defCollectionName");
            MongoClientOptions.Builder obuild = new MongoClientOptions.Builder().cursorFinalizerEnabled(false);
            obuild.connectionsPerHost(CConPoolSize);
            obuild.maxWaitTime(CMaxWaitTime);
            obuild.threadsAllowedToBlockForConnectionMultiplier(CBlockSize);
            obuild.connectTimeout(CConTimeOut);
            MongoClientOptions oOption = obuild.build();

            oClient = new MongoClient(new ServerAddress(sServerAddr), oOption);
            oDefDb = oClient.getDB(sDbName);
            oCollection = oDefDb.getCollection(sCollectionName);
        }

        /**
         * 获取Collection
         * @return
         */
        public static DBCollection getDBCollection(){
            if(oCollection == null){
                synchronized (MongodbUtil.class) {
                    if (oCollection == null)
                        initDB();
                }
            }
            return oCollection;
        }

        /**
         *插入数据
         */
        public static void insert(BasicDBObject obasicDBObject){
            try {
                getDBCollection().insert(obasicDBObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 批量插入数据
         * @param oBasicDBObjectList
         */
        public static void insertList(List<DBObject> oBasicDBObjectList){
            try {
                getDBCollection().insert(oBasicDBObjectList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 查找该collection的第一条数据
         * @return
         */
        public DBObject findFirstOne(){
            try {
                return getDBCollection().findOne();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 查找该collection指定的数据
         * @param oQuery
         * @return
         */
        public List<DBObject> findByCondition(BasicDBObject oQuery){
            List<DBObject> objectList = new ArrayList<DBObject>();
            DBCursor oCursor = getDBCollection().find(oQuery);
            try {
                while (oCursor.hasNext())
                    objectList.add(oCursor.next());
                return objectList;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                oCursor.close();
            }

            return null;
        }

        /**
         *修改指定查询到额数据
         */
        public WriteResult update(BasicDBObject oQuery, BasicDBObject oUpdate){
            try {
                return getDBCollection().update(oQuery, oUpdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         *
         */
        public WriteResult delete(BasicDBObject oQuery){
            try {
                return getDBCollection().remove(oQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

}
