package team.antelope.fg.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.IPublishSkillDao;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.entity.PublishSkill;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:31
 * @Desc: 发布dao实现类
 */

public class PublishSkillDaoImpl implements IPublishSkillDao {
    private DBOpenHelper openHelper;

    public PublishSkillDaoImpl(Context context){
        openHelper = new DBOpenHelper(context);
    }

    @Override
    public long insert(PublishSkill publishSkill) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", publishSkill.getId());
        values.put("uid", publishSkill.getuId());
        values.put("title", publishSkill.getTitle());
        values.put("content", publishSkill.getContent());
        values.put("publishdate", publishSkill.getPublishDate().getTime());
        values.put("stopdate", publishSkill.getStopDate().getTime());
        values.put("img", publishSkill.getImg());
        values.put("skilltype", publishSkill.getSkillType());
        values.put("iscomplete", publishSkill.isComplete() ? 1 : 0);
        values.put("isonline", publishSkill.isOnline() ? 1 : 0);
        values.put("addressdesc", publishSkill.getAddressDesc());
        values.put("longitude", publishSkill.getLongitude());
        values.put("latitude", publishSkill.getLatitude());
        return db.insert("publishskill", null, values);//不成功返回-1；
    }

    @Override
    public int update(PublishSkill publishSkill) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("uid", publishSkill.getuId());
        values.put("title", publishSkill.getTitle());
        values.put("content", publishSkill.getContent());
        values.put("publishdate", publishSkill.getPublishDate().getTime());
        values.put("stopdate", publishSkill.getStopDate().getTime());
        values.put("img", publishSkill.getImg());
        values.put("skilltype", publishSkill.getSkillType());
        values.put("iscomplete", publishSkill.isComplete() ? 1 : 0);
        values.put("isonline", publishSkill.isOnline() ? 1 : 0);
        values.put("addressdesc", publishSkill.getAddressDesc());
        values.put("longitude", publishSkill.getLongitude());
        values.put("latitude", publishSkill.getLatitude());
        return db.update("publishskill", values, "id=?",
                new String[]{String.valueOf(publishSkill.getId())});//不成功返回-1；

    }

    @Override
    public int delete(PublishSkill publishSkill) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.delete("publishskill","id=?",
                new String[]{String.valueOf(publishSkill.getId())});
    }

    @Override
    public PublishSkill queryById(Long id) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from publishskill where id=?",
                new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            PublishSkill publishSkill = new PublishSkill();
            publishSkill.setId(cursor.getLong(cursor.getColumnIndex("id")));
            publishSkill.setuId(cursor.getLong(cursor.getColumnIndex("uid")));
            publishSkill.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            publishSkill.setContent(cursor.getString(cursor.getColumnIndex("content")));
            publishSkill.setPublishDate(new Date(cursor.getLong(cursor.getColumnIndex("publishdate"))));
            publishSkill.setStopDate(new Date(cursor.getLong(cursor.getColumnIndex("stopdate"))));
            publishSkill.setSkillType(cursor.getString(cursor.getColumnIndex("skilltype")));
            publishSkill.setImg(cursor.getString(cursor.getColumnIndex("img")));
            publishSkill.setComplete(cursor.getInt(cursor.getColumnIndex("iscomplete")) == 1 ? true : false);
            publishSkill.setOnline(cursor.getInt(cursor.getColumnIndex("isonline")) == 1 ? true : false);
            publishSkill.setAddressDesc(cursor.getString(cursor.getColumnIndex("addressdesc")));
            publishSkill.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            publishSkill.setLongitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            cursor.close();
            return publishSkill;
        }
        cursor.close();//关闭资源
        return null;
    }

    @Override
    public List<PublishSkill> queryAllPublishSkill() {
        List<PublishSkill> list = new ArrayList<PublishSkill>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from publishskill", null);
        while(cursor.moveToNext()){
            PublishSkill publishSkill = new PublishSkill();
            publishSkill.setId(cursor.getLong(cursor.getColumnIndex("id")));
            publishSkill.setuId(cursor.getLong(cursor.getColumnIndex("uid")));
            publishSkill.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            publishSkill.setContent(cursor.getString(cursor.getColumnIndex("content")));
            publishSkill.setPublishDate(new Date(cursor.getLong(cursor.getColumnIndex("publishdate"))));
            publishSkill.setStopDate(new Date(cursor.getLong(cursor.getColumnIndex("stopdate"))));
            publishSkill.setSkillType(cursor.getString(cursor.getColumnIndex("skilltype")));
            publishSkill.setImg(cursor.getString(cursor.getColumnIndex("img")));
            publishSkill.setComplete(cursor.getInt(cursor.getColumnIndex("iscomplete")) == 1 ? true : false);
            publishSkill.setOnline(cursor.getInt(cursor.getColumnIndex("isonline")) == 1 ? true : false);
            publishSkill.setAddressDesc(cursor.getString(cursor.getColumnIndex("addressdesc")));
            publishSkill.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            publishSkill.setLongitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            list.add(publishSkill);
        }
        cursor.close();
        return list;
    }

    @Override
    public int queryTotalRecords() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select coutn(*) from publishskill", null);
        int total = 0;
        if(cursor.moveToNext()){
            total = cursor.getInt(1);
        }
        return total;
    }

    @Override
    public List<PublishSkill> queryAllPublishSkill(int from, int to) {
        List<PublishSkill> list = new ArrayList<PublishSkill>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from publishskill limit ?, ?",
                new String[]{String.valueOf(from), String.valueOf(to)});
        while(cursor.moveToNext()){
            PublishSkill publishSkill = new PublishSkill();
            publishSkill.setId(cursor.getLong(cursor.getColumnIndex("id")));
            publishSkill.setuId(cursor.getLong(cursor.getColumnIndex("uid")));
            publishSkill.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            publishSkill.setContent(cursor.getString(cursor.getColumnIndex("content")));
            publishSkill.setPublishDate(new Date(cursor.getLong(cursor.getColumnIndex("publishdate"))));
            publishSkill.setStopDate(new Date(cursor.getLong(cursor.getColumnIndex("stopdate"))));
            publishSkill.setSkillType(cursor.getString(cursor.getColumnIndex("img")));
            publishSkill.setComplete(cursor.getInt(cursor.getColumnIndex("iscomplete")) == 1 ? true : false);
            publishSkill.setOnline(cursor.getInt(cursor.getColumnIndex("isonline")) == 1 ? true : false);
            publishSkill.setAddressDesc(cursor.getString(cursor.getColumnIndex("addressdesc")));
            publishSkill.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            publishSkill.setLongitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            list.add(publishSkill);
        }
        cursor.close();
        return list;
    }
}
