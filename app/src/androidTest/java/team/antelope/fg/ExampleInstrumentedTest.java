package team.antelope.fg;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import team.antelope.fg.db.DBUtil;
import team.antelope.fg.db.dao.impl.AttentionDaoImpl;
import team.antelope.fg.db.dao.impl.CompleteCustomDaoImpl;
import team.antelope.fg.db.dao.impl.UserDaoImpl;
import team.antelope.fg.entity.Attention;
import team.antelope.fg.entity.CompleteCustom;
import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.util.L;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getContext();
        DBUtil.getInstance(appContext).createDB();
        L.i(appContext.databaseList().toString());
        // Attention CompleteCustom Person PrivateMessage PublicMessage PublishNeed
        //PublishSkill User
        User u = new User(3333l, "张三", "zhangsan", "ddd@qq.com", "男", 33);
        new UserDaoImpl(appContext).insert(u);
        Attention attention = new Attention(00001l, 10002l, 10003l);
        new AttentionDaoImpl(appContext).insert(attention);
        CompleteCustom completeCustom = new CompleteCustom(20001l, 10002l, "hcont",
                new Date(), "http:url", "TYPE1", true);
        new CompleteCustomDaoImpl(appContext).insert(completeCustom);
        new Person(1003l, "张三", "男", 23, 3.3f, 333, 500  );
        assertEquals("team.antelope.fg", appContext.getPackageName());
    }
}
