package team.antelope.fg.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.IPrivateMessageDao;
import team.antelope.fg.entity.Attention;
import team.antelope.fg.entity.PrivateMessage;
import team.antelope.fg.util.L;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:54
 * @Desc: ...
 */

public class PrivateMessageDaoImpl implements IPrivateMessageDao {
    private DBOpenHelper openHelper;

    public PrivateMessageDaoImpl(Context context){
        openHelper = new DBOpenHelper(context);
    }

    @Override
    public long insert(PrivateMessage privateMessage) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", privateMessage.getId());
        values.put("senderid", privateMessage.getSenderId());
        values.put("sendername", privateMessage.getSenderName());
        values.put("receiverid", privateMessage.getReceiverId());
        values.put("receivername", privateMessage.getReceiverName());
        values.put("sendtime", privateMessage.getSendTime().getTime());
        values.put("content", privateMessage.getContent());
        values.put("isread", privateMessage.isRead() ? 1 : 0);
        return db.insert("privatemessage", null, values);//不成功返回-1；
    }

    @Override
    public int update(PrivateMessage privateMessage) {
        HashMap<String, String> hashMap = new HashMap<>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("senderid", privateMessage.getSenderId());
        values.put("sendername", privateMessage.getSenderName());
        values.put("receiverid", privateMessage.getReceiverId());
        values.put("receivername", privateMessage.getReceiverName());
        values.put("sendtime", privateMessage.getSendTime().getTime());
        values.put("content", privateMessage.getContent());
        values.put("isread", privateMessage.isRead() ? 1 : 0);
        return db.update("privatemessage", values,
                "id=?", new String[]{String.valueOf(privateMessage.getId())});

    }

    @Override
    public int delete(PrivateMessage privateMessage) {
     SQLiteDatabase  db = openHelper.getReadableDatabase();
     return db.delete("privatemessage", "id=?",
            new String[]{String.valueOf(privateMessage.getId())});
    }

    @Override
    public PrivateMessage queryById(Long id) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from privatemessage where id = ? ",
                new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            PrivateMessage message = new PrivateMessage();
            message.setId(id);
            message.setContent(cursor.getString(cursor.getColumnIndex("content")));
            message.setReceiverId(cursor.getLong(cursor.getColumnIndex("receiverid")));
            message.setReceiverName(cursor.getString(cursor.getColumnIndex("receivername")));
            message.setSenderId(cursor.getLong(cursor.getColumnIndex("senderid")));
            message.setSenderName(cursor.getString(cursor.getColumnIndex("sendername")));
            message.setSendTime(new Date(cursor.getLong(cursor.getColumnIndex("sendtime"))));
            message.setRead(cursor.getInt(cursor.getColumnIndex("isread"))== 1 ? true : false);
            cursor.close();
            return message;
        }
        cursor.close();
        return null;
    }

    @Override
    public List<PrivateMessage> queryAllPrivateMessage() {
        List<PrivateMessage> list = new ArrayList<PrivateMessage>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id, senderid, sendername," +
                " receiverid, receivername, sendtime, content, isread from privatemessage",
                null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            Long senderId = cursor.getLong(cursor.getColumnIndex("senderid"));
            String senderName = cursor.getString(cursor.getColumnIndex("sendername"));
            Long receiverId = cursor.getLong(cursor.getColumnIndex("receiverid"));
            String receiverName = cursor.getString(cursor.getColumnIndex("receivername"));
            Date sendTime = new Date(cursor.getLong(cursor.getColumnIndex("sendtime")));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            boolean isRead = cursor.getInt(cursor.getColumnIndex("isread"))== 1 ? true : false;
            list.add(new PrivateMessage(id, senderId, senderName, receiverId,
                    receiverName, sendTime, content, isRead));
        }
        cursor.close();
        return list;
    }

    @Override
    public List<PrivateMessage> queryAllNotReadMessage() {
        List<PrivateMessage> list = new ArrayList<PrivateMessage>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id, senderid, sendername," +
                        " receiverid, receivername, sendtime, content, isread from privatemessage " +
                        "where isread = 0",
                null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            Long senderId = cursor.getLong(cursor.getColumnIndex("senderid"));
            String senderName = cursor.getString(cursor.getColumnIndex("sendername"));
            Long receiverId = cursor.getLong(cursor.getColumnIndex("receiverid"));
            String receiverName = cursor.getString(cursor.getColumnIndex("receivername"));
            Date sendTime = new Date(cursor.getLong(cursor.getColumnIndex("sendtime")));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            boolean isRead = cursor.getInt(cursor.getColumnIndex("isread"))== 1 ? true : false;
            list.add(new PrivateMessage(id, senderId, senderName, receiverId,
                    receiverName, sendTime, content, isRead));
        }
        cursor.close();
        return list;
    }

    @Override
    public int queryTotalRecords() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from privatemessage", null);
        int total = 0;
        if(cursor.moveToFirst()){
            total = cursor.getInt(1);
        }
        return total;
    }

    @Override
    public List<PrivateMessage> queryAllPrivateMessage(int from, int to) {
        List<PrivateMessage> list = new ArrayList<PrivateMessage> ();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from privatemessage limit ?, ?",
                new String[]{String.valueOf(from),String.valueOf(to)});
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            Long senderId = cursor.getLong(cursor.getColumnIndex("senderid"));
            String senderName = cursor.getString(cursor.getColumnIndex("sendername"));
            Long receiverId = cursor.getLong(cursor.getColumnIndex("receiverid"));
            String receiverName = cursor.getString(cursor.getColumnIndex("receivername"));
            Date sendTime = new Date(cursor.getLong(cursor.getColumnIndex("sendtime")));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            boolean isRead = cursor.getInt(cursor.getColumnIndex("isread"))== 1 ? true : false;
            list.add(new PrivateMessage(id, senderId, senderName, receiverId,
                    receiverName, sendTime, content, isRead));
        }
        cursor.close();
        return list;
    }

    @Override
    public List<PrivateMessage> queryBySenderId(long senderId) {
        List<PrivateMessage> list = new ArrayList<PrivateMessage>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id, senderid, sendername," +
                        " receiverid, receivername, sendtime, content, isread from privatemessage " +
                        "where senderid=? ",new String[]{String.valueOf(senderId)});
        while(cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String senderName = cursor.getString(cursor.getColumnIndex("sendername"));
            Long receiverId = cursor.getLong(cursor.getColumnIndex("receiverid"));
            String receiverName = cursor.getString(cursor.getColumnIndex("receivername"));
            Date sendTime = new Date(cursor.getLong(cursor.getColumnIndex("sendtime")));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            boolean isRead = cursor.getInt(cursor.getColumnIndex("isread"))== 1 ? true : false;
            list.add(new PrivateMessage(id, senderId, senderName, receiverId,
                    receiverName, sendTime, content, isRead));
        }
        cursor.close();
        return list;
    }

    @Override
    public List<PrivateMessage> queryAllChatMessage(long userId, long otherId) {
        List<PrivateMessage> list = new ArrayList<PrivateMessage>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from privatemessage where senderid=? and " +
                " receiverid=? union select * from privatemessage where senderid=? and receiverid=? " +
                        " order by sendtime asc",
                new String[]{String.valueOf(userId), String.valueOf(otherId),
                        String.valueOf(otherId), String.valueOf(userId)});
        while(cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String senderName = cursor.getString(cursor.getColumnIndex("sendername"));
            Long senderId = cursor.getLong(cursor.getColumnIndex("senderid"));
            Long receiverId = cursor.getLong(cursor.getColumnIndex("receiverid"));
            String receiverName = cursor.getString(cursor.getColumnIndex("receivername"));
            Date sendTime = new Date(cursor.getLong(cursor.getColumnIndex("sendtime")));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            boolean isRead = cursor.getInt(cursor.getColumnIndex("isread"))== 1 ? true : false;
            list.add(new PrivateMessage(id, senderId, senderName, receiverId,
                    receiverName, sendTime, content, isRead));
        }
        cursor.close();
        return list;
    }
}
