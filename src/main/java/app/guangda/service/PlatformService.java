package app.guangda.service;

import app.guangda.repository.dao.GdPtoperMapper;
import app.guangda.repository.model.GdPtoper;
import app.guangda.repository.model.GdPtoperExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 平台表处理
 * User: zhanrui
 * Date: 11-4-5
 * Time: 下午7:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PlatformService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GdPtoperMapper gdPtoperMapper;

    public GdPtoper selectOper(String operid) {
        GdPtoperExample example = new GdPtoperExample();
        example.createCriteria().andIdEqualTo(operid);
        List<GdPtoper> opers = gdPtoperMapper.selectByExample(example);
        if (opers.size() != 1) {
            return null;
        }
        return opers.get(0);
    }
    public void updateOperLoginInfo(GdPtoper oper){
        gdPtoperMapper.updateByPrimaryKey(oper);
    }
}
