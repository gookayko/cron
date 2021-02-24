package com.xing.libao;

import com.sleepycat.je.*;

import java.io.File;

/**
 * Created by YLZX-Z0107 on 2014/7/22.
 */
public class BerkeleyDBUtil {
    Environment myDbEnvironment = null;
    Database myDatabase = null;


    private BerkeleyDBUtil() {
        System.out.println("BerkeleyDBSingleton create");
        try {
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);//如果不存在则创建一个
            myDbEnvironment = new Environment(new File("E:\\Berkeley_DB\\douban"), envConfig);
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            dbConfig.setExclusiveCreateVoid(false);
            myDatabase = myDbEnvironment.openDatabase(null, "sample", dbConfig); //打开一个数据库，数据库名为sampleDatabase,数据库的配置为dbConfig

        } catch (DatabaseException dbe) {
            dbe.printStackTrace();
        }
    }

    public void cleanDatabase(){
        myDbEnvironment.truncateDatabase(null,myDatabase.getDatabaseName(),false);
    }

    public boolean has(String hash){
        DatabaseEntry key = new DatabaseEntry(hash.getBytes());
        DatabaseEntry data = new DatabaseEntry(new byte[]{});
        return myDatabase.get(null, key, data, LockMode.DEFAULT) == OperationStatus.SUCCESS;
    }

    public boolean add(String urlKey) {
        // 先从库里取，判断是否存在
        try {
            DatabaseEntry key = new DatabaseEntry(urlKey.getBytes());
            DatabaseEntry data = new DatabaseEntry(new byte[]{});
            if (myDatabase.get(null, key, data, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                return false;
            } else {
                myDatabase.put(null, key, data);
                myDbEnvironment.flushLog(true);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("bdb error");
        return true;
    }

    public void closeDB() {
        try {
            if (myDatabase != null) {
                myDatabase.close();
            }
            if (myDbEnvironment != null) {
                myDbEnvironment.cleanLog(); // 在关闭环境前清理下日志
                myDbEnvironment.close();
            }
        } catch (DatabaseException dbe) {
            dbe.printStackTrace();
        }
    }

    private static class SingletonHolder {
        private static BerkeleyDBUtil instance = new BerkeleyDBUtil();
    }

    public static BerkeleyDBUtil getInstance() {
        return SingletonHolder.instance;
    }
}
