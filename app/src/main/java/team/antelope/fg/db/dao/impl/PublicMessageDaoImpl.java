package team.antelope.fg.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.IPublicMessageDao;
import team.antelope.fg.entity.Attention;
import team.antelope.fg.entity.PublicMessage;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:34
 * @Desc: ...
 */

public class PublicMessageDaoImpl implements IPublicMessageDao {
    private DBOpenHelper openHelper;

    public PublicMessageDaoImpl(Context context){
        openHelper = new DBOpenHelper(context);
    }

    @Override
    public long insert(PublicMessage publicMessage) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", publicMessage.getId());
        values.put("sendtime", publicMessage.getSendTime().getTime());
        values.put("receiverid", publicMessage.getReceiverId());
        values.put("title", publicMessage.getTitle());
        values.put("content", publicMessage.getContent());
        return db.insert("publicmessage", null, values);//不成功返回-1；
    }

    @Override
    public int update(PublicMessage publicMessage) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("sendtime", publicMessage.getSendTime().getTime());
        values.put("title", publicMessage.getTitle());
        values.put("receiverid", publicMessage.getReceiverId());
        values.put("content", publicMessage.getContent());
        return db.update("publicmessage", values, "id=?",
                new String []{String.valueOf(publicMessage.getId())});//不成功返回-1；

    }

    @Override
    public int delete(PublicMessage publicMessage) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.delete("publicmessage", "id=?",
                new String[]{String.valueOf(publicMessage.getId())});
    }

    @Override
    public PublicMessage queryById(Long id) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from publicmessage where id=?",
                new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            PublicMessage message = new PublicMessage();
            message.setId(cursor.getLong(cursor.getColumnIndex("id")));
            message.setReceiverId(cursor.getLong(cursor.getColumnIndex("receiverid")));
            message.setSendTime(new Date(cursor.getLong(cursor.getColumnIndex("sendtime"))));
            message.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            message.setContent(cursor.getString(cursor.getColumnIndex("content")));
            cursor.close();
            return message;
        }
        cursor.close();//关闭资源
        return null;
    }

    @Override
    public List<PublicMessage> queryAllPublicMessage() {
        List<PublicMessage> list = new ArrayList<PublicMessage>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from publicmessage", null);
        while(cursor.moveToNext()){
            PublicMessage message = new PublicMessage();
            message.setId(cursor.getLong(cursor.getColumnIndex("id")));
            message.setReceiverId(cursor.getLong(cursor.getColumnIndex("receiverid")));
            message.setSendTime(new Date(cursor.getLong(cursor.getColumnIndex("sendtime"))));
            message.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            message.setContent(cursor.getString(cursor.getColumnIndex("content")));
            list.add(message);
        }
        cursor.close();
        return list;
    }

    @Override
    public int queryTotalRecords() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from publicmessage ", null);
        int total = 0;
        if(cursor.moveToFirst()){
            total = cursor.getInt(1);
        }
        return total;
    }

    @Override
    public List<PublicMessage> queryAllPublicMessage(int from, int to) {
        List<PublicMessage> list = new ArrayList<PublicMessage> ();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from publicmessage limit ?, ?",
                new String[]{String.valueOf(from),String.valueOf(to)});
        while(cursor.moveToNext()){
            PublicMessage message = new PublicMessage();
            message.setId(cursor.getLong(cursor.getColumnIndex("id")));
            message.setReceiverId(cursor.getLong(cursor.getColumnIndex("receiverid")));
            message.setSendTime(new Date(cursor.getLong(cursor.getColumnIndex("sendtime"))));
            message.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            message.setContent(cursor.getString(cursor.getColumnIndex("content")));
            list.add(message);
        }
        cursor.close();
        return list;
    }
}
