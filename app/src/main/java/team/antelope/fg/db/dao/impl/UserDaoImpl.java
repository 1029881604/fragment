package team.antelope.fg.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.IUserDao;
import team.antelope.fg.entity.User;


/**
 * @Author：hwc
 * @Date：2017/12/6 22:16
 * @Desc: 用户dao实现类
 */

public class UserDaoImpl implements IUserDao {
    private DBOpenHelper openHelper;

    public UserDaoImpl(Context context){
        openHelper = new DBOpenHelper(context);
    }
    /**
     * @Description 查询所有的用户
     * @date 2017/12/6
     */
    @Override
    public List<User> queryAllUser() {
        List<User> list = new ArrayList<User>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user", null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            list.add(new User(id, name, password, email));
        }
        cursor.close();
        return list;
    }

    @Override
    public User queryByName(String name) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where name=?",
                new String[]{name});
        if(cursor.moveToFirst()){
            User user = new User();
            user.setId(cursor.getLong(cursor.getColumnIndex("id")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            cursor.close();
            return user;
        }
        cursor.close();//关闭资源
        return null;
    }

    @Override
    public int deleteAll() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.delete("user",null, null);
    }

    /**
     * @Description 添加用户
     * @date 2017/12/6
     */
    @Override
    public long insert(User user) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("name", user.getName());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        return db.insert("user", null, values);//不成功返回-1；
    }

    @Override
    public int update(User user) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
//        values.put("id", user.getId());
        values.put("name", user.getName());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        return db.update("user", values, "id=?", new String[]{String.valueOf(user.getId())});
    }

    @Override
    public int delete(User user) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.delete("user","name=?", new String[]{user.getName()});
    }

    @Override
    public User queryById(Long id) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where id=?",
                new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            User user = new User();
            user.setId(cursor.getLong(cursor.getColumnIndex("id")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            cursor.close();
            return user;
        }
        cursor.close();//关闭资源
        return null;
    }

}
