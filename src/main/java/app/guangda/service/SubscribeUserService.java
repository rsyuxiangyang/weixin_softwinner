package app.guangda.service;

import app.guangda.constant.SubscribeType;
import app.guangda.repository.dao.GdSubscribeLogMapper;
import app.guangda.repository.dao.GdSubscribeUserMapper;
import app.guangda.repository.model.*;
import app.guangda.repository.model.GdSubscribeUser;
import app.guangda.repository.model.GdSubscribeUserExample;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by XIANGYANG on 2016-1-7.
 */
@Service
public class SubscribeUserService {
    private static Logger logger = LoggerFactory.getLogger(SubscribeUserService.class);

    @Autowired
    GdSubscribeUserMapper gdSubscribeUserMapper;
    @Autowired
    GdSubscribeLogMapper gdSubscribeLogMapper;

    public void insertRecord(GdSubscribeUser gdSubscribeUser){
        gdSubscribeUser.setSubscribeFlag(SubscribeType.SUBSCRIBE.getCode());
        gdSubscribeUser.setRecversion(1);
        gdSubscribeUserMapper.insert(gdSubscribeUser);

        insertSubscribeLog(gdSubscribeUser, SubscribeType.SUBSCRIBE);
    }

    public void updateRecord(GdSubscribeUser gdSubscribeUserPara,SubscribeType typePara){
        GdSubscribeUser gdSubscribeUser=qryRecord(gdSubscribeUserPara);
        gdSubscribeUser.setCreateTime(gdSubscribeUserPara.getCreateTime());
        gdSubscribeUser.setSubscribeFlag(typePara.getCode());
        gdSubscribeUser.setRecversion(gdSubscribeUser.getRecversion()+1);
        gdSubscribeUserMapper.updateByPrimaryKey(gdSubscribeUser);

        insertSubscribeLog(gdSubscribeUser, typePara);
    }

    public void insertSubscribeLog(GdSubscribeUser gdSubscribeUserPara,SubscribeType typePara){
        GdSubscribeLog log=new GdSubscribeLog();
        log.setDeptCode(gdSubscribeUserPara.getDeptCode());
        log.setOpenid(gdSubscribeUserPara.getOpenid());
        log.setSubscribeType(typePara.getCode());
        log.setCreateTime(gdSubscribeUserPara.getCreateTime());
        gdSubscribeLogMapper.insert(log);
    }

    public boolean isExistsRecord(GdSubscribeUser gdSubscribeUserPara){
        GdSubscribeUserExample example=new GdSubscribeUserExample();
        GdSubscribeUserExample.Criteria criteria= example.createCriteria();
        if (StringUtils.isNotEmpty(gdSubscribeUserPara.getDeptCode())){
            criteria.andDeptCodeEqualTo(gdSubscribeUserPara.getDeptCode());
        }
        criteria.andOpenidEqualTo(gdSubscribeUserPara.getOpenid());
        return gdSubscribeUserMapper.selectByExample(example).size()>0?true:false;
    }

    public GdSubscribeUser qryRecord(GdSubscribeUser gdSubscribeUser){
        GdSubscribeUserExample example=new GdSubscribeUserExample();
        example.createCriteria().andOpenidEqualTo(gdSubscribeUser.getOpenid());
        return gdSubscribeUserMapper.selectByExample(example).get(0);
    }
}
