package team.antelope.fg.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import team.antelope.fg.db.DBOpenHelper;
import team.antelope.fg.db.dao.IAttentionDao;
import team.antelope.fg.entity.Attention;
import team.antelope.fg.entity.Person;

/**
 * @Author：hwc
 * @Date：2017/12/6 22:55
 * @Desc: AttentionDao的实现类，特别注意key必须和数据库表的字段一致而不是实体类的
 * 注意要保证DBUtil的createDB先被调用
 * sqlite 支持limit
 */

public class AttentionDaoImpl implements IAttentionDao {
    private DBOpenHelper openHelper;

    public AttentionDaoImpl(Context context){
        openHelper = new DBOpenHelper(context);
    }

    @Override
    public long insert(Attention attention) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("uid", attention.getuId());
        values.put("attentionuserid", attention.getAttentionUserId());
        return db.insert("attention", null, values);//不成功返回-1；
    }

    @Deprecated
    @Override
    public int update(Attention attention) {
        return 0;
    }

    @Override
    public int delete(Attention attention) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.delete("attention","uid=? and attentionuserid=?",
                new String[]{String.valueOf(attention.getuId()),
                        String.valueOf(attention.getAttentionUserId())});
    }
    @Deprecated
    @Override
    public Attention queryById(Long id) {
        return null;
    }

    @Override
    public Attention queryById(Long uid, long fid) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from attention where uid=? and attentionuserid=?",
                new String[]{String.valueOf(uid), String.valueOf(fid)});
        if(cursor.moveToFirst()){
            Attention attention = new Attention();
            attention.setuId(cursor.getLong(cursor.getColumnIndex("uid")));
            attention.setAttentionUserId(cursor.getLong(cursor.getColumnIndex("attentionuserid")));
            cursor.close();
            return attention;
        }
        cursor.close();//关闭资源
        return null;
    }

    @Override
    public List<Person> findFriends(Long id) {
        List<Person> list = new ArrayList<Person>();
        SQLiteDatabase db = openHelper.getReadableDatabase();

        String sql = "select * from person where " +
                " id in ( select uid fid from attention where attentionuserid = ?  " +
                " union select attentionuserid fid from attention where uid = ?" +
                ")";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id), String.valueOf(id)});
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
    public List<Attention> queryAllAttention() {
        List<Attention> list = new ArrayList<Attention>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select uid, attentionuserid from attention", null);
        while(cursor.moveToNext()){
            Long uId = cursor.getLong(cursor.getColumnIndex("uid"));
            Long attentionUserId = cursor.getLong(cursor.getColumnIndex("attentionuserid"));
            list.add(new Attention(uId, attentionUserId));
        }
        cursor.close();
        return list;
    }

    /**
     * @Description 返回总记录条数，若没有则返回0
     * @date 2017/12/7
     */
    @Override
    public int queryTotalRecords() {
       SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select coutn(*) from attention", null);
        int total = 0;
        if(cursor.moveToNext()){
            total = cursor.getInt(0);
        }
        return total;
    }

    @Override
    public List<Attention> queryAllPrivateAttention(int from, int to) {
        List<Attention> list = new ArrayList<Attention> ();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from attention limit ?, ?",
                new String[]{String.valueOf(from),String.valueOf(to)});
        while(cursor.moveToNext()){
            Long uId = cursor.getLong(cursor.getColumnIndex("uid"));
            Long attentionUserId = cursor.getLong(cursor.getColumnIndex("attentionUserId"));
            list.add(new Attention(uId, attentionUserId));
        }
        cursor.close();
        return list;
    }
}
