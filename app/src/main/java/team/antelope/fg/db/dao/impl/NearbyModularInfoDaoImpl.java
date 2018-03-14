package team.antelope.fg.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.INearbyModularInfoDao;
import team.antelope.fg.entity.Attention;
import team.antelope.fg.entity.NearbyModularInfo;

/**
 * @Author：hwc
 * @Date：2018/1/6 20:50
 * @Desc: ...
 */

public class NearbyModularInfoDaoImpl implements INearbyModularInfoDao {
    private DBOpenHelper openHelper;

    public NearbyModularInfoDaoImpl(Context context){
        openHelper = new DBOpenHelper(context);
    }

    @Override
    public long insert(NearbyModularInfo nearbyModularInfo) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("needtitle", nearbyModularInfo.getNeedtitle());
        values.put("needbody", nearbyModularInfo.getNeedbody());
        values.put("needimg", nearbyModularInfo.getNeedimg());
        values.put("skilltitle", nearbyModularInfo.getSkilltitle());
        values.put("skillbody", nearbyModularInfo.getSkillbody());
        values.put("skillimg", nearbyModularInfo.getNeedimg());
        values.put("type", nearbyModularInfo.getType());
        values.put("needupdatetime", nearbyModularInfo.getNeedupdatetime().getTime());
        values.put("skillupdatetime", nearbyModularInfo.getSkillupdatetime().getTime());
        return db.insert("nearbymodular", null, values);//不成功返回-1；
    }

    @Override
    public int update(NearbyModularInfo nearbyModularInfo) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("needtitle", nearbyModularInfo.getNeedtitle());
        values.put("needbody", nearbyModularInfo.getNeedbody());
        values.put("needimg", nearbyModularInfo.getNeedimg());
        values.put("skilltitle", nearbyModularInfo.getSkilltitle());
        values.put("skillbody", nearbyModularInfo.getSkillbody());
        values.put("skillimg", nearbyModularInfo.getNeedimg());
        values.put("needupdatetime", nearbyModularInfo.getNeedupdatetime().getTime());
        values.put("skillupdatetime", nearbyModularInfo.getSkillupdatetime().getTime());
        return db.update("nearbymodular",  values, "type=?",
                new String[]{nearbyModularInfo.getType()});//不成功返回-1；
    }

    @Override
    public int delete(NearbyModularInfo nearbyModularInfo) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.delete("nearbymodular","type=?",
                new String[]{nearbyModularInfo.getType()});
    }

    @Deprecated
    @Override
    public NearbyModularInfo queryById(Long id) {
        return null;
    }
    @Override
    public List<NearbyModularInfo> queryAllModularInfo() {
        List<NearbyModularInfo> list = new ArrayList<NearbyModularInfo>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from nearbymodular", null);
        while(cursor.moveToNext()){
            String needtitle = cursor.getString(cursor.getColumnIndex("needtitle"));
            String needbody = cursor.getString(cursor.getColumnIndex("needbody"));
            String needimg = cursor.getString(cursor.getColumnIndex("needimg"));
            String skilltitle = cursor.getString(cursor.getColumnIndex("skilltitle"));
            String skillbody = cursor.getString(cursor.getColumnIndex("skillbody"));
            String skillimg = cursor.getString(cursor.getColumnIndex("skillimg"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            Date needupdatetime = new Date(cursor.getLong(cursor.getColumnIndex("needupdatetime")));
            Date skillupdatetime = new Date(cursor.getLong(cursor.getColumnIndex("skillupdatetime")));
            list.add(new NearbyModularInfo(needtitle, needbody, needimg, skilltitle,
                    skillbody, skillimg, type, needupdatetime, skillupdatetime));
        }
        cursor.close();
        return list;
    }

    @Override
    public int queryTotalRecords() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select coutn(*) from nearbymodular", null);
        int total = 0;
        if(cursor.moveToNext()){
            total = cursor.getInt(0);
        }
        return total;
    }

    @Override
    public List<NearbyModularInfo> queryAllModularInfo(int from, int to) {
        List<NearbyModularInfo> list = new ArrayList<NearbyModularInfo> ();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from nearbymodular limit ?, ?",
                new String[]{String.valueOf(from),String.valueOf(to)});
        while(cursor.moveToNext()){
            String needtitle = cursor.getString(cursor.getColumnIndex("needtitle"));
            String needbody = cursor.getString(cursor.getColumnIndex("needbody"));
            String needimg = cursor.getString(cursor.getColumnIndex("needimg"));
            String skilltitle = cursor.getString(cursor.getColumnIndex("skilltitle"));
            String skillbody = cursor.getString(cursor.getColumnIndex("skillbody"));
            String skillimg = cursor.getString(cursor.getColumnIndex("skillimg"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            Date needupdatetime = new Date(cursor.getLong(cursor.getColumnIndex("needupdatetime")));
            Date skillupdatetime = new Date(cursor.getLong(cursor.getColumnIndex("skillupdatetime")));
            list.add(new NearbyModularInfo(needtitle, needbody, needimg, skilltitle,
                    skillbody, skillimg, type, needupdatetime, skillupdatetime));
        }
        cursor.close();
        return list;
    }

    @Override
    public NearbyModularInfo queryByType(String type) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from nearbymodular where type=?",
                new String[]{type});
        if(cursor.moveToFirst()){
            String needtitle = cursor.getString(cursor.getColumnIndex("needtitle"));
            String needbody = cursor.getString(cursor.getColumnIndex("needbody"));
            String needimg = cursor.getString(cursor.getColumnIndex("needimg"));
            String skilltitle = cursor.getString(cursor.getColumnIndex("skilltitle"));
            String skillbody = cursor.getString(cursor.getColumnIndex("skillbody"));
            String skillimg = cursor.getString(cursor.getColumnIndex("skillimg"));
            Date needupdatetime = new Date(cursor.getLong(cursor.getColumnIndex("needupdatetime")));
            Date skillupdatetime = new Date(cursor.getLong(cursor.getColumnIndex("skillupdatetime")));
            NearbyModularInfo info = new NearbyModularInfo(needtitle, needbody, needimg,
                    skilltitle, skillbody, skillimg, type, needupdatetime, skillupdatetime);
            cursor.close();
            return info;
        }
        cursor.close();//关闭资源
        return null;
    }
}
