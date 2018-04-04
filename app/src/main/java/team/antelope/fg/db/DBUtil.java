package team.antelope.fg.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

import team.antelope.fg.db.dao.impl.AttentionDaoImpl;
import team.antelope.fg.db.dao.impl.CompleteCustomDaoImpl;
import team.antelope.fg.db.dao.impl.NearbyModularInfoDaoImpl;
import team.antelope.fg.db.dao.impl.PersonDaoImpl;
import team.antelope.fg.db.dao.impl.PrivateMessageDaoImpl;
import team.antelope.fg.db.dao.impl.PublicMessageDaoImpl;
import team.antelope.fg.db.dao.impl.PublishNeedDaoImpl;
import team.antelope.fg.db.dao.impl.PublishSkillDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Attention;
import team.antelope.fg.entity.CompleteCustom;
import team.antelope.fg.entity.NearbyModularInfo;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.PrivateMessage;
import team.antelope.fg.entity.PublicMessage;
import team.antelope.fg.entity.PublishNeed;
import team.antelope.fg.entity.PublishSkill;
import team.antelope.fg.entity.User;
import team.antelope.fg.util.L;

/**
 * @Author：hwc
 * @Date：2017/12/7 13:02
 * @Desc: ...
 */

public class DBUtil {
    DBOpenHelper openHelper;
    private static DBUtil instance;
    private Context mContext;
    private DBUtil(Context context) {
        mContext = context;
    }
    /**
     * @Description 单例模式
     * @date 2017/12/7
     */
    public static DBUtil getInstance(Context context){
        if(instance == null){
            synchronized (DBUtil.class){
                if(instance == null){
                    instance = new DBUtil(context);
                }
            }
        }
        return instance;
    }
    /**
     * @Description 创建数据库
     * @date 2017/12/7
     */
    public void createDB(){
        L.i("createdb1");
        openHelper = new DBOpenHelper(mContext, 1, new String[]{
                /**
                 * user表
                */
                "create table user(" +
                        "id BIGINT primary key,  " +
                        "name CHARACTER(20) not null unique, " +
                        "password CHARACTER(20) not null unique,  " +
                        "email CHARACTER(30)" +
                        ");",
                /**
                 * attention表
                */
                "create table attention(" +
                        "uid BIGINT not null," +
                        "attentionuserid BIGINT not null," +
                        "primary key (uid, attentionuserid)" +
                        ");",
                /**
                 * person表
                */
                "create table person(" +
                        "id BIGINT primary key,  " +
                        "name CHARACTER(20) not null unique, " +
                        "sex CHARACTER(10)," +
                        "age INT," +
                        "email CHARACTER(30)," +
                        "headimg text," +
                        "starnum REAL," +
                        "dealnum int," +
                        "fansnum bigint" +
                        ");",
                /**
                 * publishskill表
                */
                "create table publishskill(" +
                        "id BIGINT primary key," +
                        "uid BIGINT not null," +
                        "title CHARACTER(60)," +
                        "content text," +
                        "publishdate bigint," +
                        "stopdate bigint," +
                        "img text," +
                        "skilltype text," +
                        "iscomplete int," +
                        "isonline int," +
                        "addressdesc text," +
                        "longitude real," +
                        "latitude real" +
                        ");",
                /**
                 * publishneed表
                */
                "create table publishneed(" +
                        "id BIGINT primary key," +
                        "uid BIGINT not null," +
                        "title CHARACTER(20)," +
                        "content text," +
                        "needtype CHARACTER(60)," +
                        "customdate bigint," +
                        "requestdate bigint," +
                        "iscomplete int," +
                        "isonline int," +
                        "addressdesc text," +
                        "longitude real," +
                        "latitude real" +
                        ");",

                /**
                 * PrivateMessage表
                */
                "create table privatemessage(" +
                        "id BIGINT primary key ," +
                        "senderid BIGINT not null," +
                        "sendername CHARACTER(20) not null," +
                        "receiverid bigint not null," +
                        "receivername CHARACTER(20) not null," +
                        "sendtime bigint not null," +
                        "content text," +
                        "isread int" +
                        ");",
                /**
                 * PublicMessage表
                */
                "create table publicmessage(" +
                        "id BIGINT primary key ," +
                        "sendtime bigint not null," +
                        "receiverid int not null," +
                        "title CHARACTER(40)," +
                        "content text," +
                        "isread int" +
                        ");",
                /**
                 * nearbymodular 附近板块的模块信息表
                */
                "create table nearbymodular(" +
                        "needtitle CHARACTER(120) ," +
                        "needbody text ," +
                        "needimg CHARACTER(300)," +
                        "skilltitle CHARACTER(120)," +
                        "skillbody text not null," +
                        "skillimg CHARACTER(300)," +
                        "type CHARACTER(50) not null primary key," +
                        "needupdatetime bigint," +
                        "skillupdatetime bigint" +
                        ");"
        });
        L.i("createdb2");
        SQLiteDatabase db = openHelper.getWritableDatabase();

//        insertNearbymodular();   联网之后数据从服务器端读取

        User u = new User(1001l, "吃烤鸭", "zhangsan", "ddd@qq.com");
        new UserDaoImpl(mContext).insert(u);

        //Person 表 Start
        Person person1 = new Person(1003l, "吃鸡腿", "男",
                22, "chijitui@gmail.com",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3013222128,1128640698&fm=27&gp=0.jpg",
                3.3f, 200, 500);
        Person person0 = new Person(1001l, "吃烤df鸭", "女", 23,
                "chikaoya@qq.com", "imgurl",
                3.3f, 23, 3);
        Person person2 = new Person(1002l, "吃炸鸡", "女", 21, "chizhaji@gmail.com", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1018364764,1223529536&fm=27&gp=0.jpg", 3.3f, 666, 250);
        Person person3 = new Person(1004l, "李四", "男", 23, "ddd@qq.com", "imgurl", 3.3f, 333, 500);
        Person person4 = new Person(1005l, "王二", "男", 23, "ddd@qq.com", "imgurl", 3.3f, 333, 500);
        PersonDaoImpl personDao = new PersonDaoImpl(mContext);
        personDao.insert(person1);
        personDao.insert(person2);
        personDao.insert(person3);
        personDao.insert(person4);
        personDao.insert(person0);
        //Person 表 ending


        //Attention 表 Start Long L long l
        Attention attention1 = new Attention(1002L, 1003l);
        AttentionDaoImpl attentionDao =new AttentionDaoImpl(mContext);
        Attention attention = new Attention(1001l, 1003l);
        Attention attention2 = new Attention(1002l, 1001L);
        attentionDao.insert(attention);
        attentionDao.insert(attention1);
        attentionDao.insert(attention2);

        //Attention 表 Ending


        //publishSkill   start
        insertPublishSkill();

        //publishSkill   end



        PublishNeed publishNeed = new PublishNeed(3000l, 1003l, "needtitle", "needcont","hhhh",
                new Date(), new Date(System.currentTimeMillis()+3600*24), false, false,
                "江西师范大学", 3333.3, 34.3);
        new PublishNeedDaoImpl(mContext).insert(publishNeed);
//        CompleteCustom completeCustom = new CompleteCustom(20001l, 10002l, "customtitle", "hcont",
//                new Date(), "http:url", "TYPE1", true);
//        new CompleteCustomDaoImpl(mContext).insert(completeCustom);

        PrivateMessage privateMessage1 = new PrivateMessage(5003l, 1999l, "王五", 1001l, "吃烤鸭",
                new Date(System.currentTimeMillis()-60000*3), "在", false);
        PrivateMessage privateMessage2 = new PrivateMessage(5004l, 1999l, "王五", 1001l, "吃烤鸭",
                new Date(System.currentTimeMillis()-60000*5), "吗", false);
        PrivateMessage privateMessage3 = new PrivateMessage(5005l, 1999l, "王五", 1001l, "吃烤鸭",
                new Date(System.currentTimeMillis()-60000*7), "我", false);
        PrivateMessage privateMessage4 = new PrivateMessage(5006l, 1999l, "王五", 1001l, "吃烤鸭",
                new Date(System.currentTimeMillis()-60000*8), "是", false);
        PrivateMessage privateMessage5 = new PrivateMessage(5007l, 2000l, "李四", 1001l, "吃烤鸭",
                new Date(System.currentTimeMillis()-60000*6), "谁", false);
        PrivateMessage privateMessage10 = new PrivateMessage(5007l, 2000l, "李四", 1001l, "吃烤鸭",
                new Date(System.currentTimeMillis()-60000*5), "谁", false);
        PrivateMessage privateMessage6 = new PrivateMessage(5038l, 1001l, "吃烤鸭", 1999l, "王五",
                new Date(System.currentTimeMillis()-60000*2), "呀", false);
        PrivateMessage privateMessage9 = new PrivateMessage(5020l, 1001l, "吃烤鸭", 1999l, "王五",
                new Date(System.currentTimeMillis()-60000*4), "呀", false);
        PrivateMessage privateMessage7 = new PrivateMessage(5160l, 1001l, "吃烤鸭", 2000l, "李四",
                new Date(System.currentTimeMillis()-60000*5), "哈", false);
        PrivateMessage privateMessage8 = new PrivateMessage(5208l, 1001l, "吃烤鸭", 2000l, "李四",
                new Date(System.currentTimeMillis()-60000*3), "去", false);
        PrivateMessageDaoImpl privateMessageDao = new PrivateMessageDaoImpl(mContext);
        privateMessageDao.insert(privateMessage1);
        privateMessageDao.insert(privateMessage2);
        privateMessageDao.insert(privateMessage3);
        privateMessageDao.insert(privateMessage4);
        privateMessageDao.insert(privateMessage5);
        privateMessageDao.insert(privateMessage6);
        privateMessageDao.insert(privateMessage7);
        privateMessageDao.insert(privateMessage8);
        privateMessageDao.insert(privateMessage9);


        PublicMessage publicMessage = new PublicMessage(6001l, new Date(),1003l, "title", "publicMsgcont",false);
        long count2 = new PublicMessageDaoImpl(mContext).insert(publicMessage);
        L.i("count",count2+"");
        int version = db.getVersion();
        L.i("数据库版本为: "+version);
    }

    private void insertPublishSkill() {
        //PublishSkill表 Start——————————————————————————————————————
        PublishSkill publishSkill1 = new PublishSkill(3000l, 1001l,"动画制作1","专业的动画制作1，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作1，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作1，为您打造顶级演示效果，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "动画制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill2 = new PublishSkill(3001l, 1001l,"线上测试标题","这个是测试内容，", new Date(),
                new Date(System.currentTimeMillis()+3600*12), "http:img",
                "TestType1", false, true,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill3 = new PublishSkill(3002l, 1002l,"动画制作3","专业的动画制作3，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作3，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作3，为您打造顶级演示效果，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "动画制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill4 = new PublishSkill(3003l, 1003l,"动画制作4","专业的动画制作4，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作4，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作4，为您打造顶级演示效果，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "动画制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill5 = new PublishSkill(3004l, 1004l,"动画制作5","专业的动画制作5，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作5，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作5，为您打造顶级演示效果，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "动画制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill6 = new PublishSkill(3005l, 1005l,"动画制作6","专业的动画制作6，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作6，为您打造顶级演示效果，这里是具体的内容介绍，专业的动画制作6，为您打造顶级演示效果，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "动画制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill7 = new PublishSkill(3006l, 1001l,"UI设计制作1","专业的UI设计制作效果1，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果1，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果1，为您提供顶级人机交互体验，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "UI设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill8 = new PublishSkill(3007l, 1002l,"UI设计制作2","专业的UI设计制作效果2，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果2，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果2，为您提供顶级人机交互体验，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "UI设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill9 = new PublishSkill(3008l, 1003l,"UI设计制作3","专业的UI设计制作效果3，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果3，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果3，为您提供顶级人机交互体验，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "UI设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill10 = new PublishSkill(3009l, 1004l,"UI设计制作4","专业的UI设计制作效果4，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果4，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果4，为您提供顶级人机交互体验，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "UI设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill11 = new PublishSkill(3010l, 1005l,"UI设计制作5","专业的UI设计制作效果5，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果5，为您提供顶级人机交互体验，这里是具体的内容介绍，专业的UI设计制作效果5，为您提供顶级人机交互体验，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "UI设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill12 = new PublishSkill(3011l, 1001l,"网页设计/制作制作1","专业的网页设计/制作原型制作1，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作1，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作1，制作出色的网页展示效果，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "网页设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill13 = new PublishSkill(3012l, 1002l,"网页设计/制作制作2","专业的网页设计/制作原型制作2，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作2，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作2，制作出色的网页展示效果，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "网页设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill14 = new PublishSkill(3013l, 1003l,"网页设计/制作制作3","专业的网页设计/制作原型制作3，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作3，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作3，制作出色的网页展示效果，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "网页设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill15 = new PublishSkill(3014l, 1004l,"网页设计/制作制作4","专业的网页设计/制作原型制作4，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作4，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作4，制作出色的网页展示效果，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "网页设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill16 = new PublishSkill(3015l, 1005l,"网页设计/制作制作5","专业的网页设计/制作原型制作5，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作5，制作出色的网页展示效果，这里是具体的内容显示，专业的网页设计/制作原型制作5，制作出色的网页展示效果，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "网页设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill17 = new PublishSkill(3016l, 1001l,"平面设计1","专业的平面设计1，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计1，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计1，打造顶级平面设计样式，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "平面设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill18 = new PublishSkill(3017l, 1002l,"平面设计2","专业的平面设计2，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计2，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计2，打造顶级平面设计样式，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "平面设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill19 = new PublishSkill(3018l, 1003l,"平面设计3","专业的平面设计3，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计3，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计3，打造顶级平面设计样式，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "平面设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill20 = new PublishSkill(3019l, 1004l,"平面设计4","专业的平面设计4，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计4，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计4，打造顶级平面设计样式，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "平面设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill21 = new PublishSkill(3020l, 1005l,"平面设计5","专业的平面设计5，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计5，打造顶级平面设计样式，这里是具体的内容显示，专业的平面设计5，打造顶级平面设计样式，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "平面设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill22 = new PublishSkill(3021l, 1001l,"视频/后期制作制作1","专业的视频/后期制作制作团队1，移动端视频/后期制作定制,专业的视频/后期制作制作团队1，移动端视频/后期制作定制,专业的视频/后期制作制作团队1，移动端视频/后期制作定制,专业的视频/后期制作制作团队1，移动端视频/后期制作定制",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "视频/后期制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill23 = new PublishSkill(3022l, 1002l,"视频/后期制作制作2","专业的视频/后期制作制作团队2，移动端视频/后期制作定制,专业的视频/后期制作制作团队2，移动端视频/后期制作定制,专业的视频/后期制作制作团队2，移动端视频/后期制作定制,专业的视频/后期制作制作团队2，移动端视频/后期制作定制",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "视频/后期制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill24 = new PublishSkill(3023l, 1003l,"视频/后期制作制作3","专业的视频/后期制作制作团队3，移动端视频/后期制作定制,专业的视频/后期制作制作团队3，移动端视频/后期制作定制,专业的视频/后期制作制作团队3，移动端视频/后期制作定制,专业的视频/后期制作制作团队3，移动端视频/后期制作定制",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "视频/后期制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill25 = new PublishSkill(3024l, 1004l,"视频/后期制作制作4","专业的视频/后期制作制作团队4，移动端视频/后期制作定制,专业的视频/后期制作制作团队4，移动端视频/后期制作定制,专业的视频/后期制作制作团队4，移动端视频/后期制作定制,专业的视频/后期制作制作团队4，移动端视频/后期制作定制",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "视频/后期制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill26 = new PublishSkill(3025l, 1005l,"视频/后期制作制作5","专业的视频/后期制作制作团队5，移动端视频/后期制作定制,专业的视频/后期制作制作团队5，移动端视频/后期制作定制,专业的视频/后期制作制作团队5，移动端视频/后期制作定制,专业的视频/后期制作制作团队5，移动端视频/后期制作定制",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "视频/后期制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill27 = new PublishSkill(3026l, 1001l,"App开发制作1","专业的App开发制作1，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作1，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作1，打造顶级App制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "App开发", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill28 = new PublishSkill(3027l, 1002l,"App开发制作2","专业的App开发制作2，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作2，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作2，打造顶级App制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "App开发", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill29 = new PublishSkill(3028l, 1003l,"App开发制作3","专业的App开发制作3，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作3，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作3，打造顶级App制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "App开发", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill30 = new PublishSkill(3029l, 1004l,"App开发制作4","专业的App开发制作4，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作4，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作4，打造顶级App制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "App开发", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill31 = new PublishSkill(3030l, 1005l,"App开发制作5","专业的App开发制作5，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作5，打造顶级App制作团队，这里是具体的内容介绍，专业的App开发制作5，打造顶级App制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "App开发", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill32 = new PublishSkill(3031l, 1001l,"程序语言设计1","专业的程序语言设计1，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计1，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计1，打造顶级语言程序设计，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "程序语言设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill33 = new PublishSkill(3032l, 1002l,"程序语言设计2","专业的程序语言设计2，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计2，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计2，打造顶级语言程序设计，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "程序语言设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill34 = new PublishSkill(3033l, 1003l,"程序语言设计3","专业的程序语言设计3，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计3，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计3，打造顶级语言程序设计，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "程序语言设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill35 = new PublishSkill(3034l, 1004l,"程序语言设计4","专业的程序语言设计4，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计4，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计4，打造顶级语言程序设计，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "程序语言设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill36 = new PublishSkill(3035l, 1005l,"程序语言设计5","专业的程序语言设计5，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计5，打造顶级语言程序设计，这里是具体的内容显示，专业的程序语言设计5，打造顶级语言程序设计，这里是具体的内容显示",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "程序语言设计", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill37 = new PublishSkill(3036l, 1001l,"排版设计/制作制作1","专业的排版设计/制作制作1，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作1，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作1，打造顶级排版设计制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "排版设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill38 = new PublishSkill(3037l, 1002l,"排版设计/制作制作2","专业的排版设计/制作制作2，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作2，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作2，打造顶级排版设计制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "排版设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill39 = new PublishSkill(3038l, 1003l,"排版设计/制作制作3","专业的排版设计/制作制作3，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作3，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作3，打造顶级排版设计制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "排版设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill40 = new PublishSkill(3039l, 1004l,"排版设计/制作制作4","专业的排版设计/制作制作4，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作4，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作4，打造顶级排版设计制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "排版设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);
        PublishSkill publishSkill41 = new PublishSkill(3040l, 1005l,"排版设计/制作制作5","专业的排版设计/制作制作5，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作5，打造顶级排版设计制作团队，这里是具体的内容介绍，专业的排版设计/制作制作5，打造顶级排版设计制作团队，这里是具体的内容介绍",
                new Date(), new Date(System.currentTimeMillis()+3600*12), "http:img",
                "排版设计/制作", false, false,
                "江西师范大学", 122.3, 28.7);

        PublishSkillDaoImpl publishSkillDao=new PublishSkillDaoImpl(mContext);

        publishSkillDao.insert(publishSkill1);
        publishSkillDao.insert(publishSkill2);
        publishSkillDao.insert(publishSkill3);
        publishSkillDao.insert(publishSkill4);
        publishSkillDao.insert(publishSkill5);
        publishSkillDao.insert(publishSkill6);
        publishSkillDao.insert(publishSkill7);
        publishSkillDao.insert(publishSkill8);
        publishSkillDao.insert(publishSkill9);
        publishSkillDao.insert(publishSkill10);
        publishSkillDao.insert(publishSkill11);
        publishSkillDao.insert(publishSkill12);
        publishSkillDao.insert(publishSkill13);
        publishSkillDao.insert(publishSkill14);
        publishSkillDao.insert(publishSkill15);
        publishSkillDao.insert(publishSkill16);
        publishSkillDao.insert(publishSkill17);
        publishSkillDao.insert(publishSkill18);
        publishSkillDao.insert(publishSkill19);
        publishSkillDao.insert(publishSkill20);
        publishSkillDao.insert(publishSkill21);
        publishSkillDao.insert(publishSkill22);
        publishSkillDao.insert(publishSkill23);
        publishSkillDao.insert(publishSkill24);
        publishSkillDao.insert(publishSkill25);
        publishSkillDao.insert(publishSkill26);
        publishSkillDao.insert(publishSkill27);
        publishSkillDao.insert(publishSkill28);
        publishSkillDao.insert(publishSkill29);
        publishSkillDao.insert(publishSkill30);
        publishSkillDao.insert(publishSkill31);
        publishSkillDao.insert(publishSkill32);
        publishSkillDao.insert(publishSkill33);
        publishSkillDao.insert(publishSkill34);
        publishSkillDao.insert(publishSkill35);
        publishSkillDao.insert(publishSkill36);
        publishSkillDao.insert(publishSkill37);
        publishSkillDao.insert(publishSkill38);
        publishSkillDao.insert(publishSkill39);
        publishSkillDao.insert(publishSkill40);
        publishSkillDao.insert(publishSkill41);
        //PublishSkill表 End————————————————————————————————————————


    }

    private void insertNearbymodular() {
        NearbyModularInfo nearbyModularInfo = new NearbyModularInfo("needtitle", "巴拉巴拉balabalabala, we have the bast py in the world, balabala...., now you should...",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/need_photography.jpg",
                "最会拍的摄影师", "不知道说啥，反正这里是内容，巴拉巴拉巴拉巴拉，，balabalabala...巴拉巴拉巴拉巴拉.小魔仙",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/skill_photography.jpg", "photography",
                new Date(), new Date());
        NearbyModularInfo nearbyModularInfo1 = new NearbyModularInfo("needtitle", "巴拉巴拉balabalabala, we have the bast py in the world, balabala...., now you should...",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/need_accompany.jpg",
                "最有趣的陪玩", "不知道说啥，反正这里是内容，巴拉巴拉巴拉巴拉，，balabalabala...巴拉巴拉巴拉巴拉.小魔仙",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/skill_accompany.jpg", "accompany",
                new Date(), new Date());
        NearbyModularInfo nearbyModularInfo2 = new NearbyModularInfo("needtitle", "巴拉巴拉balabalabala, we have the bast py in the world, balabala...., now you should...",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/need_errand.jpeg",
                "最速度的跑腿", "不知道说啥，反正这里是内容，巴拉巴拉巴拉巴拉，，balabalabala...巴拉巴拉巴拉巴拉.小魔仙",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/skill_errand.jpeg", "errand",
                new Date(), new Date());
        NearbyModularInfo nearbyModularInfo3 = new NearbyModularInfo("needtitle", "巴拉巴拉balabalabala, we have the bast py in the world, balabala...., now you should...",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/need_manual.jpg",
                "最尽力的苦力", "不知道说啥，反正这里是内容，巴拉巴拉巴拉巴拉，，balabalabala...巴拉巴拉巴拉巴拉.小魔仙",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/skill_manual.jpg", "manual",
                new Date(), new Date());
        NearbyModularInfo nearbyModularInfo4 = new NearbyModularInfo("needtitle", "巴拉巴拉balabalabala, we have the bast py in the world, balabala...., now you should...",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/need_guide.jpg",
                "最专业的指导", "不知道说啥，反正这里是内容，巴拉巴拉巴拉巴拉，，balabalabala...巴拉巴拉巴拉巴拉.小魔仙",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/skill_guide.jpg", "guide",
                new Date(), new Date());
        NearbyModularInfo nearbyModularInfo5 = new NearbyModularInfo("needtitle", "巴拉巴拉balabalabala, we have the bast py in the world, balabala...., now you should...",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/need_other.jpg",
                "最专业的啥", "不知道说啥，反正这里是内容，巴拉巴拉巴拉巴拉，，balabalabala...巴拉巴拉巴拉巴拉.小魔仙",
                "http://192.168.191.1:8080/fragment_server/images/nearby_modular_img/skill_other.png", "other",
                new Date(), new Date());
        NearbyModularInfoDaoImpl modularInfoDao = new NearbyModularInfoDaoImpl(mContext);
        modularInfoDao.insert(nearbyModularInfo);
        modularInfoDao.insert(nearbyModularInfo1);
        modularInfoDao.insert(nearbyModularInfo2);
        modularInfoDao.insert(nearbyModularInfo3);
        modularInfoDao.insert(nearbyModularInfo4);
        modularInfoDao.insert(nearbyModularInfo5);
    }

    /**
     * @Description 删除数据库，失败返回false
     * @date 2017/12/7
     */
    public boolean deleteDB () {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db.deleteDatabase(mContext.getDatabasePath(DBOpenHelper.DBNAME));
    }
    /**
     * @Description 升级数据库
     * @date 2017/12/7
     */
    public void upGradeDB  () {
        openHelper = new DBOpenHelper(mContext, 2, new String[]{
                /**
                * user表
                */
                "create table user("
                        + "id BIGINT not null primary key,"
                        + "name CHARACTER(20) not null unique,"
                        + "password CHARACTER(20) not null unique,"
                        + "email CHARACTER(20) ,"
                        + "sex CHARACTER(20) ,"
                        + "age INT"
                        + ");",
                /**
                * attention表
                */
                "create table attention("
                        + "id BIGINT not null primary key,"
                        + "uid BIGINT not null,"
                        + "attentionuserid BIGINT not null"
                        + ");"});
        SQLiteDatabase db = openHelper.getWritableDatabase();
        int version = db.getVersion();
        L.i("数据库版本为: "+version);
    }

}
