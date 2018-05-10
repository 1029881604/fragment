package team.antelope.fg;

import org.junit.Test;

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

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        DBUtil.getInstance(FgApp.getInstance()).createDB();
//        L.i(FgApp.getInstance().databaseList().toString());
//        // Attention CompleteCustom Person PrivateMessage PublicMessage PublishNeed
//        //PublishSkill User
//        User u = new User(3333l, "张三g", "zhangsan", "ddd@qq.com");
//        new UserDaoImpl(FgApp.getInstance()).insert(u);
        assertEquals(4, 2 + 2);
    }
}