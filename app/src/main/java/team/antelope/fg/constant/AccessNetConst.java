package team.antelope.fg.constant;

/**
 * @Author：hwc
 * @Date：2017/12/14 20:09
 * @Desc: 访问网络的常量
 */

public interface AccessNetConst {
    String LOADING = "正在加载，请稍后...";
    String LOADFAIL = "加载失败";
    String LOGINING = "正在登入...";
    String REGISTERING = "正在注册...";
    //base
    String BASEPATH = "basePath";
    //use
    String LOGINENDPATH = "loginEndPath";
    String LOGOUTENDPATH = "logoutEndPath";
    String FINDFRIENDENDPATH = "findFriendEndPath";
    String REGISTERENDPATH = "registerEndPath";
    String GETREQVERICODEENDPATH = "getReqVeriCodeEndPath";
    //nearby
    String GETSKILLINFOENDPATH = "getNearbySkillInfoEndPath";
    String GETNEEDINFOENDPATH = "getNearbyNeedInfoEndPath";
    String GETPUBLISHNEEDENDPATH = "getPublishNeedEndPath";
    String GETPUBLISHSKILLENDPATH = "getPublishSkillEndPath";
    String NEARBYFRAGMENTINFOSENDPATH = "NearbyFragmentInfosEndPath";
    //nearby need
    String TONEEDINFO = "toNeedInfo";
    //nearby skill
    String TOSKILLINFO = "toSkillInfo";

    //forward
    String TOPERSONINFOENDPATH = "toPersonInfoEndPath";



    /*yy添加的*/
    String CHANGEPROFILESERVLEENDTPATH = "changeProfileServletEndPath";
    //获取用户的person信息
    String GETUSERENDPATH = "getuserendpath";

}
