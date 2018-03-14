package team.antelope.fg.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.IPublishNeedDao;
import team.antelope.fg.entity.PublishNeed;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:55
 * @Desc: ...
 */

public class PublishNeedDaoImpl implements IPublishNeedDao {
    private DBOpenHelper openHelper;

    public PublishNeedDaoImpl(Context context){
        openHelper = new DBOpenHelper(context);
    }

    @Override
    public long insert(PublishNeed publishNeed) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", publishNeed.getId());
        values.put("uid", publishNeed.getuId());
        values.put("title", publishNeed.getTitle());
        values.put("content", publishNeed.getContent());
        values.put("needtype", publishNeed.getNeedtype());
        values.put("customdate", publishNeed.getCustomDate().getTime());
        values.put("requestdate", publishNeed.getRequestDate().getTime());
        values.put("iscomplete", publishNeed.isComplete() ? 1 : 0);
        values.put("isonline", publishNeed.isOnline() ? 1 : 0);
        values.put("addressdesc", publishNeed.getAddressDesc());
        values.put("longitude", publishNeed.getLongitude());
        values.put("latitude", publishNeed.getLatitude());
        return db.insert("publishneed", null, values);//不成功返回-1；
    }

    @Override
    public int update(PublishNeed publishNeed) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("uid", publishNeed.getuId());
        values.put("title", publishNeed.getTitle());
        values.put("content", publishNeed.getContent());
        values.put("needtype", publishNeed.getNeedtype());
        values.put("customdate", publishNeed.getCustomDate().getTime());
        values.put("requestdate", publishNeed.getRequestDate().getTime());
        values.put("iscomplete", publishNeed.isComplete() ? 1 : 0);
        values.put("isonline", publishNeed.isOnline() ? 1 : 0);
        values.put("addressdesc", publishNeed.getAddressDesc());
        values.put("longitude", publishNeed.getLongitude());
        values.put("latitude", publishNeed.getLatitude());
        return db.update("publishneed", values, "id=?",
                new String[]{String.valueOf(publishNeed.getId())});

    }

    @Override
    public int delete(PublishNeed publishNeed) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.delete("publishneed","id=?",
                new String[]{String.valueOf(publishNeed.getId())});
    }

    @Override
    public PublishNeed queryById(Long id) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from publishneed where id=?",
                new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            PublishNeed publishNeed = new PublishNeed();
            publishNeed.setId(cursor.getLong(cursor.getColumnIndex("id")));
            publishNeed.setuId(cursor.getLong(cursor.getColumnIndex("uid")));
            publishNeed.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            publishNeed.setContent(cursor.getString(cursor.getColumnIndex("content")));
            publishNeed.setNeedtype(cursor.getString(cursor.getColumnIndex("needtype")));
            publishNeed.setCustomDate(new Date(cursor.getLong(cursor.getColumnIndex("customdate"))));
            publishNeed.setCustomDate(new Date(cursor.getLong(cursor.getColumnIndex("requestdate"))));
            publishNeed.setComplete(cursor.getInt(cursor.getColumnIndex("iscomplete")) == 1 ? true : false);
            publishNeed.setOnline(cursor.getInt(cursor.getColumnIndex("isonline")) == 1 ? true : false);
            publishNeed.setAddressDesc(cursor.getString(cursor.getColumnIndex("addressdesc")));
            publishNeed.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            publishNeed.setLongitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            cursor.close();
            return publishNeed;
        }
        cursor.close();//关闭资源
        return null;
    }

    @Override
    public List<PublishNeed> queryAllPublishNeed() {
        List<PublishNeed> list = new ArrayList<PublishNeed>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from publishneed", null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            Long uId = cursor.getLong(cursor.getColumnIndex("uid"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String type = cursor.getString(cursor.getColumnIndex("needtype"));
            Date customDate = new Date(cursor.getLong(cursor.getColumnIndex("customdate")));
            Date requestDate = new Date(cursor.getLong(cursor.getColumnIndex("requestdate")));
            boolean isComplete = cursor.getInt(cursor.getColumnIndex("iscomplete")) == 1 ? true : false;
            boolean isOnline = cursor.getInt(cursor.getColumnIndex("isonline")) == 1 ? true : false;
            String addressDesc = cursor.getString(cursor.getColumnIndex("addressdesc"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            list.add(new PublishNeed(id, uId, title, content, type, customDate, requestDate, isComplete,
                    isOnline, addressDesc, longitude, latitude));
        }
        cursor.close();
        return list;
    }

    @Override
    public int queryTotalRecords() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select coutn(*) from publishneed", null);
        int total = 0;
        if(cursor.moveToNext()){
            total = cursor.getInt(1);
        }
        return total;
    }

    @Override
    public List<PublishNeed> queryAllPublishNeed(int from, int to) {
        List<PublishNeed> list = new ArrayList<PublishNeed> ();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from publishneed limit ?, ?",
                new String[]{String.valueOf(from),String.valueOf(to)});
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            Long uId = cursor.getLong(cursor.getColumnIndex("uid"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String type = cursor.getString(cursor.getColumnIndex("needtype"));
            Date customDate = new Date(cursor.getLong(cursor.getColumnIndex("customdate")));
            Date requestDate = new Date(cursor.getLong(cursor.getColumnIndex("requestdate")));
            boolean isComplete = cursor.getInt(cursor.getColumnIndex("iscomplete")) == 1 ? true : false;
            boolean isOnline = cursor.getInt(cursor.getColumnIndex("isonline")) == 1 ? true : false;
            String addressDesc = cursor.getString(cursor.getColumnIndex("addressdesc"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            list.add(new PublishNeed(id, uId, title, content, type, customDate, requestDate, isComplete,
                    isOnline, addressDesc, longitude, latitude));
        }
        cursor.close();
        return list;
    }
}
