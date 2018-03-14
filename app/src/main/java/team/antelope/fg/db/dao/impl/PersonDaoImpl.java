package team.antelope.fg.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.IPersonDao;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PublishSkill;

/**
 * @Author：hwc
 * @Date：2017/12/12 20:31
 * @Desc: ...
 */

public class PersonDaoImpl implements IPersonDao {
    private DBOpenHelper openHelper;

    public PersonDaoImpl(Context context){
        openHelper = new DBOpenHelper(context);
    }
    @Override
    public long insert(Person person) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", person.getId());
        values.put("name", person.getName());
        values.put("sex", person.getSex());
        values.put("age", person.getAge());
        values.put("email", person.getEmail());
        values.put("starnum", person.getStarnum());
        values.put("headimg", person.getHeadImg());
        values.put("dealnum", person.getDealnum());
        values.put("fansnum", person.getFansnum());
        return db.insert("person", null, values);//不成功返回-1；
    }

    @Override
    public int update(Person person) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", person.getName());
        values.put("sex", person.getSex());
        values.put("age", person.getAge());
        values.put("email", person.getEmail());
        values.put("starnum", person.getStarnum());
        values.put("headimg", person.getHeadImg());
        values.put("dealnum", person.getDealnum());
        values.put("fansnum", person.getFansnum());
        return db.update("person", values, "id=?",
                new String[]{String.valueOf(person.getId())});//不成功返回-1；
    }

    @Override
    public int delete(Person person) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.delete("person", "id=?",
                new String[]{String.valueOf(person.getId())});
    }

    @Override
    public Person queryById(Long id) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from person where id=?",
                new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            Person person = new Person();
            person.setId(cursor.getLong(cursor.getColumnIndex("id")));
            person.setName(cursor.getString(cursor.getColumnIndex("name")));
            person.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            person.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            person.setStarnum(cursor.getFloat(cursor.getColumnIndex("starnum")));
            person.setHeadImg(cursor.getString(cursor.getColumnIndex("headimg")));
            person.setDealnum(cursor.getInt(cursor.getColumnIndex("dealnum")));
            person.setFansnum(cursor.getInt(cursor.getColumnIndex("fansnum")));
            cursor.close();
            return person;
        }
        cursor.close();//关闭资源
        return null;
    }

    @Override
    public List<Person> queryAllPerson() {
        List<Person> list = new ArrayList<Person>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from person", null);
        while(cursor.moveToNext()){
            Person person = new Person();
            person.setId(cursor.getLong(cursor.getColumnIndex("id")));
            person.setName(cursor.getString(cursor.getColumnIndex("name")));
            person.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            person.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            person.setStarnum(cursor.getFloat(cursor.getColumnIndex("starnum")));
            person.setHeadImg(cursor.getString(cursor.getColumnIndex("headimg")));
            person.setDealnum(cursor.getInt(cursor.getColumnIndex("dealnum")));
            person.setFansnum(cursor.getInt(cursor.getColumnIndex("fansnum")));
            list.add(person);
        }
        cursor.close();
        return list;
    }

    @Override
    public int queryTotalRecords() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select coutn(*) from person", null);
        int total = 0;
        if(cursor.moveToNext()){
            total = cursor.getInt(1);
        }
        return total;
    }

    @Override
    public List<Person> queryAllPerson(int from, int to) {
        List<Person> list = new ArrayList<Person>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from person limit ?, ?",
                new String[]{String.valueOf(from), String.valueOf(to)});
        while(cursor.moveToNext()){
            Person person = new Person();
            person.setId(cursor.getLong(cursor.getColumnIndex("id")));
            person.setName(cursor.getString(cursor.getColumnIndex("name")));
            person.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            person.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            person.setStarnum(cursor.getFloat(cursor.getColumnIndex("starnum")));
            person.setHeadImg(cursor.getString(cursor.getColumnIndex("headimg")));
            person.setDealnum(cursor.getInt(cursor.getColumnIndex("dealnum")));
            person.setFansnum(cursor.getInt(cursor.getColumnIndex("fansnum")));
            list.add(person);
        }
        cursor.close();
        return list;
    }
}
