package team.antelope.fg.db.dao;

import java.util.List;

import team.antelope.fg.entity.NearbyModularInfo;

public interface INearbyModularInfoDao extends IBaseDao<NearbyModularInfo> {
    List<NearbyModularInfo> queryAllModularInfo();    
    int queryTotalRecords();    
    List <NearbyModularInfo> queryAllModularInfo(int from, int to);
    // add
    NearbyModularInfo queryByType(String type);
}
