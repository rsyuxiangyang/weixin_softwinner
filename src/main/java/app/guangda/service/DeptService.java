package app.guangda.service;

import app.guangda.constant.SubscribeType;
import app.guangda.repository.dao.GdDeptMapper;
import app.guangda.repository.dao.GdSubscribeLogMapper;
import app.guangda.repository.dao.GdSubscribeUserMapper;
import app.guangda.repository.dao.nonauto.NonAutoGdDeptMapper;
import app.guangda.repository.model.*;
import app.guangda.repository.model.nonauto.GdDeptShow;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by XIANGYANG on 2016-1-7.
 */
@Service
public class DeptService {
    private static Logger logger = LoggerFactory.getLogger(DeptService.class);

    @Autowired
    GdDeptMapper gdDeptMapper;
    @Autowired
    NonAutoGdDeptMapper nonAutoGdDeptMapper;

    public List<GdDept> qryRecordsByModel(GdDept gdDeptPara){
        GdDeptExample example=new GdDeptExample();
        GdDeptExample.Criteria criteria=example.createCriteria();
        if (StringUtils.isNotEmpty(gdDeptPara.getDeptCode())){
            criteria.andDeptCodeEqualTo(gdDeptPara.getDeptCode());
        }
        if (StringUtils.isNotEmpty(gdDeptPara.getDeptName())){
            criteria.andDeptNameLike(gdDeptPara.getDeptName()+"%");
        }
        if (StringUtils.isNotEmpty(gdDeptPara.getDeptType())){
            criteria.andDeptTypeEqualTo(gdDeptPara.getDeptType());
        }
        example.setOrderByClause("dept_code ASC") ;
        return gdDeptMapper.selectByExample(example);
    }

    public void insertRecord(GdDept gdDeptPara){
        String maxSeneId=selectMaxSceneId();
        String currentSceneId=Integer.parseInt(maxSeneId)+1+"";
        gdDeptPara.setDeptSceneId(currentSceneId);
        gdDeptMapper.insert(gdDeptPara);
    }

    public void updateRecord(GdDept gdDeptPara){
        gdDeptMapper.updateByPrimaryKey(gdDeptPara);
    }

    public int deleteRecord(GdDept gdDeptPara){
        return gdDeptMapper.deleteByPrimaryKey(gdDeptPara.getPkid());
    }

    public boolean isExistInDb(GdDept gdDeptPara) {
        GdDeptExample example = new GdDeptExample();
        example.createCriteria().andDeptCodeEqualTo(gdDeptPara.getDeptCode());
        return gdDeptMapper.countByExample(example) >= 1;
    }

    public String selectMaxSceneId() {
        return nonAutoGdDeptMapper.selectMaxSceneId();
    }

    public List<GdDeptShow> qryDeptSubscribeInfo(String strStartDatePara,String strEndDatePara ) {
        return nonAutoGdDeptMapper.qryDeptSubscribeInfo(strStartDatePara,strEndDatePara);
    }

}
