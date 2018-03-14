package team.antelope.fg.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.ICompleteCustom;
import team.antelope.fg.entity.CompleteCustom;
import team.antelope.fg.entity.PublishSkill;

/**
 * @Author：hwc
 * @Date：2017/12/12 20:05
 * @Desc: ...
 */

public class CompleteCustomDaoImpl implements ICompleteCustom {
    private DBOpenHelper openHelper;

    public CompleteCustomDaoImpl(Context context){
        openHelper = new DBOpenHelper(context);
    }

    @Override
    public long insert(CompleteCustom completeCustom) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", completeCustom.getId());
        values.put("uid", completeCustom.getuId());
        values.put("title", completeCustom.getTitle());
        values.put("content", completeCustom.getContent());
        values.put("customdate", completeCustom.getCustomDate().getTime());
        values.put("img", completeCustom.getImg());
        values.put("skilltype", completeCustom.getSkillType());
        values.put("isopen", completeCustom.isOpen() ? 1 : 0);
        return db.insert("completecustom", null, values);//不成功返回-1；
    }

    @Override
    public int update(CompleteCustom completeCustom) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", completeCustom.getId());
        values.put("uid", completeCustom.getuId());
        values.put("title", completeCustom.getTitle());
        values.put("content", completeCustom.getContent());
        values.put("customdate", completeCustom.getCustomDate().getTime());
        values.put("img", completeCustom.getImg());
        values.put("skilltype", completeCustom.getSkillType());
        values.put("isopen", completeCustom.isOpen() ? 1 : 0);
        return db.update("completecustom", values, "id=?",
                new String[]{String.valueOf(completeCustom.getId())});//不成功返回-1；
    }

    @Override
    public int delete(CompleteCustom completeCustom) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.delete("completecustom","id=?",
                new String[]{String.valueOf(completeCustom.getId())});
    }

    @Override
    public CompleteCustom queryById(Long id) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from completecustom where id=?",
                new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            CompleteCustom completeCustom = new CompleteCustom();
            completeCustom.setId(id);
            completeCustom.setuId(cursor.getLong(cursor.getColumnIndex("uid")));
            completeCustom.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            completeCustom.setContent(cursor.getString(cursor.getColumnIndex("content")));
            completeCustom.setCustomDate(new Date(cursor.getLong(cursor.getColumnIndex("customdate"))));
            completeCustom.setSkillType(cursor.getString(cursor.getColumnIndex("skilltype")));
            completeCustom.setImg(cursor.getString(cursor.getColumnIndex("img")));
            completeCustom.setOpen(cursor.getInt(cursor.getColumnIndex("isopen")) == 1 ? true : false);
            cursor.close();
            return completeCustom;
        }
        cursor.close();//关闭资源
        return null;
    }

    @Override
    public List<CompleteCustom> queryAllCompleteCustom() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from completecustom ",
                null);
        List<CompleteCustom> list = new ArrayList<CompleteCustom>();
        while (cursor.moveToNext()){
            CompleteCustom completeCustom = new CompleteCustom();
            completeCustom.setId(cursor.getLong(cursor.getColumnIndex("id")));
            completeCustom.setuId(cursor.getLong(cursor.getColumnIndex("uid")));
            completeCustom.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            completeCustom.setContent(cursor.getString(cursor.getColumnIndex("content")));
            completeCustom.setCustomDate(new Date(cursor.getLong(cursor.getColumnIndex("customdate"))));
            completeCustom.setSkillType(cursor.getString(cursor.getColumnIndex("skilltype")));
            completeCustom.setImg(cursor.getString(cursor.getColumnIndex("img")));
            completeCustom.setOpen(cursor.getInt(cursor.getColumnIndex("isopen")) == 1 ? true : false);
            list.add(completeCustom);
        }
        cursor.close();
        return list;
    }

    @Override
    public int queryTotalRecords() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select coutn(*) from completecustom", null);
        int total = 0;
        if(cursor.moveToNext()){
            total = cursor.getInt(1);
        }
        return total;
    }

    @Override
    public List<CompleteCustom> queryAllCompleteCustom(int from, int to) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from completecustom limit ?, ? ",
                new String[]{String.valueOf(from), String.valueOf(to)});
        List<CompleteCustom> list = new ArrayList<CompleteCustom>();
        while (cursor.moveToNext()){
            CompleteCustom completeCustom = new CompleteCustom();
            completeCustom.setId(cursor.getLong(cursor.getColumnIndex("id")));
            completeCustom.setuId(cursor.getLong(cursor.getColumnIndex("uid")));
            completeCustom.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            completeCustom.setContent(cursor.getString(cursor.getColumnIndex("content")));
            completeCustom.setCustomDate(new Date(cursor.getLong(cursor.getColumnIndex("customdate"))));
            completeCustom.setSkillType(cursor.getString(cursor.getColumnIndex("skilltype")));
            completeCustom.setImg(cursor.getString(cursor.getColumnIndex("img")));
            completeCustom.setOpen(cursor.getInt(cursor.getColumnIndex("isopen")) == 1 ? true : false);
            list.add(completeCustom);
        }
        cursor.close();
        return list;
    }
}
