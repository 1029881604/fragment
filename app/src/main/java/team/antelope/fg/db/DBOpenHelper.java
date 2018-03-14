package team.antelope.fg.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import team.antelope.fg.util.L;

/**
 * @Author：hwc
 * @Date：2017/12/6 14:24
 * @Desc: 数据库帮助类
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static int VERSION = 1;
    public static String DBNAME = "my.db";
    private String createDBSql[]; //建表语句

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO 自动生成的构造函数存根
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                        DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        // TODO 自动生成的构造函数存根
    }
    /**
     * 重写构造方法1
     * @para context
     * @Description Constructor
     */
    public DBOpenHelper(Context context,int version, String[] createDBSql){
        this(context, DBNAME, null, version);
        this.createDBSql = createDBSql;
    }
    public DBOpenHelper(Context context){
        this(context, DBNAME, null, VERSION);
    }

    /**
     * 数据库创建后首次且唯一一次调用该方法，创建表级对象
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < createDBSql.length; i++) {
            db.execSQL(createDBSql[i]);
            L.i("数据表被创建!" + i);
        }
    }
    /**
     * @Description 数据库升级时候调用
     * @date 2017/12/6
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 自动生成的方法存根
        L.i("数据库升级");
    }
}
