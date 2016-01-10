package app.guangda.view.cust;

import app.guangda.constant.DeptType;
import app.guangda.repository.model.GdDept;
import app.guangda.repository.model.nonauto.GdDeptShow;
import app.guangda.service.DeptService;
import common.jsf.MessageUtil;
import common.utils.PropertyManager;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by XIANGYANG on 2016-1-7.
 */
@ManagedBean
@ViewScoped
public class DeptAction {
    private static final Logger logger = LoggerFactory.getLogger(DeptAction.class);

    private GdDept gdDeptQry;
    private GdDept gdDeptAdd;
    private GdDept gdDeptUpd;
    private GdDept gdDeptDel;
    private List<GdDept> gdDeptList;
    private List<GdDeptShow> gdDeptShowList=new ArrayList<>();
    private String strSubmitType;
    private List<SelectItem> deptTypeList;

    @ManagedProperty(value = "#{deptService}")
    private DeptService deptService;

    @PostConstruct
    public void init() {
        try {
            initData();
        } catch (Exception e) {
            logger.error("初始化失败", e);
        }
    }

    public void initData() {
        gdDeptQry = new GdDept();
        gdDeptAdd = new GdDept();
        gdDeptUpd = new GdDept();
        gdDeptDel = new GdDept();
        gdDeptList = deptService.qryRecordsByModel(gdDeptQry);
        deptTypeList = new ArrayList<>();
        deptTypeList.add(new SelectItem(DeptType.COMMUNITY_BRANCH.getTitle(), DeptType.COMMUNITY_BRANCH.getTitle()));
        deptTypeList.add(new SelectItem(DeptType.SUB_BRANCH.getTitle(), DeptType.SUB_BRANCH.getTitle()));
    }


    public List<GdDeptShow> initDeptSubscribeInfo(){
        String starDate=getDateAfter(new Date(), 0, "yyyy-MM-dd");
        String endDate=getDateAfter(new Date(), 1, "yyyy-MM-dd");
        gdDeptShowList=deptService.qryDeptSubscribeInfo(starDate,endDate);
        return gdDeptShowList;
    }


    public void resetActionForAdd() {
        gdDeptAdd = new GdDept();
        strSubmitType = "Add";
    }

    public void submitRecordAction() {
        if (strSubmitType.equals("Add")) {
            if (deptService.isExistInDb(gdDeptAdd)) {
                MessageUtil.addError("该记录已存在，请重新录入！");
            } else {
                addRecordAction(gdDeptAdd);
                resetActionForAdd();
            }
        } else if (strSubmitType.equals("Upd")) {
            updRecordAction(gdDeptUpd);
        } else if (strSubmitType.equals("Del")) {
            deleteRecordAction(gdDeptDel);
        }
        initData();
    }

    public boolean preCheck(GdDept gdDeptPara) {
        if (StringUtils.isEmpty(gdDeptPara.getDeptCode())) {
            MessageUtil.addError("请输入机构号码！");
            return false;
        } else if (StringUtils.isEmpty(gdDeptPara.getDeptName())) {
            MessageUtil.addError("请输入机构名称！");
            return false;
        } else if (StringUtils.isEmpty(gdDeptPara.getDeptType())) {
            MessageUtil.addError("请输选择机构类型！");
            return false;
        }
        return true;
    }

    private void addRecordAction(GdDept gdDeptPara) {
        try {
            if (preCheck(gdDeptPara)) {
                deptService.insertRecord(gdDeptPara);
                MessageUtil.addInfo("新增数据完成。");

            }
        } catch (Exception e) {
            logger.error("新增数据失败，", e);
            MessageUtil.addError(e.getMessage());
        }
    }

    private void updRecordAction(GdDept gdDeptPara) {
        try {
            deptService.updateRecord(gdDeptPara);
            MessageUtil.addInfo("更新数据完成。");
        } catch (Exception e) {
            logger.error("更新数据失败，", e);
            MessageUtil.addError(e.getMessage());
        }
    }

    private void deleteRecordAction(GdDept gdDeptPara) {
        try {
            int deleteRecordNum = deptService.deleteRecord(gdDeptPara);
            if (deleteRecordNum <= 0) {
                MessageUtil.addInfo("该记录已删除。");
                return;
            }
            MessageUtil.addInfo("删除数据完成。");
        } catch (Exception e) {
            logger.error("删除数据失败，", e);
            MessageUtil.addError(e.getMessage());
        }
    }

    public List<GdDept> onQueryAction() {
        try {
            this.gdDeptList = deptService.qryRecordsByModel(gdDeptQry);
            if (gdDeptList.isEmpty()) {
                MessageUtil.addWarn("没有查询到数据。");
            }
        } catch (Exception e) {
            logger.error("信息查询失败", e);
            MessageUtil.addError("信息查询失败");
        }
        return gdDeptList;
    }

    public void selectRecordAction(String strSubmitTypePara, GdDept gdDeptPara) {
        try {
            strSubmitType = strSubmitTypePara;
            if (strSubmitTypePara.equals("Upd")) {
                gdDeptUpd = gdDeptPara;
            } else if (strSubmitTypePara.equals("Del")) {
                gdDeptDel = gdDeptPara;
            }
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
        }
    }

    public void generateQRcodeAction(GdDept gdDeptPara) {
        try {
            String realPath = PropertyManager.getProperty("QRcode.fileSavePath.guangda");
            String fileName=realPath+gdDeptPara.getDeptCode()+".jpg";
            File file =new File(fileName);
            if (file.exists()){
                gdDeptPara.setDeptQrcode(gdDeptPara.getDeptCode() + ".jpg");
                deptService.updateRecord(gdDeptPara);
            }else{
                while (file.exists()){
                    gdDeptPara.setDeptQrcode(gdDeptPara.getDeptCode() + ".jpg");
                    deptService.updateRecord(gdDeptPara);
                    break;
                }
            }

            initData();
            MessageUtil.addInfo("生成二维码成功！");
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
        }
    }

    private String getDateAfter(Date date, int days, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
        return sdf.format(calendar.getTime());
    }


    public GdDept getGdDeptQry() {
        return gdDeptQry;
    }

    public void setGdDeptQry(GdDept gdDeptQry) {
        this.gdDeptQry = gdDeptQry;
    }

    public GdDept getGdDeptAdd() {
        return gdDeptAdd;
    }

    public void setGdDeptAdd(GdDept gdDeptAdd) {
        this.gdDeptAdd = gdDeptAdd;
    }

    public GdDept getGdDeptUpd() {
        return gdDeptUpd;
    }

    public void setGdDeptUpd(GdDept gdDeptUpd) {
        this.gdDeptUpd = gdDeptUpd;
    }

    public GdDept getGdDeptDel() {
        return gdDeptDel;
    }

    public void setGdDeptDel(GdDept gdDeptDel) {
        this.gdDeptDel = gdDeptDel;
    }

    public List<GdDept> getGdDeptList() {
        return gdDeptList;
    }

    public void setGdDeptList(List<GdDept> gdDeptList) {
        this.gdDeptList = gdDeptList;
    }

    public DeptService getDeptService() {
        return deptService;
    }

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    public String getStrSubmitType() {
        return strSubmitType;
    }

    public void setStrSubmitType(String strSubmitType) {
        this.strSubmitType = strSubmitType;
    }

    public List<SelectItem> getDeptTypeList() {
        return deptTypeList;
    }

    public void setDeptTypeList(List<SelectItem> deptTypeList) {
        this.deptTypeList = deptTypeList;
    }

    public List<GdDeptShow> getGdDeptShowList() {
        return gdDeptShowList;
    }

    public void setGdDeptShowList(List<GdDeptShow> gdDeptShowList) {
        this.gdDeptShowList = gdDeptShowList;
    }
}
