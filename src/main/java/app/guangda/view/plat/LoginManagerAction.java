package app.guangda.view.plat;

import app.guangda.repository.model.GdPtoper;
import app.guangda.service.PlatformService;
import common.utils.MD5Helper;
import common.jsf.MessageUtil;
import common.utils.SystemAttributeNames;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * User: zhanrui
 * Date: 2015-6-11
 */
@ManagedBean
@SessionScoped
public class LoginManagerAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private GdPtoper ptoper;
    private boolean isLogin;
    private boolean agree=true;
    private String deptId;
    private String operId;
    private String operPasswd;
    private String mailbox;
    private String loginTime;

    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;

    public String onLogin() {
        try {
            if (StringUtils.isBlank(operId)) {
                MessageUtil.addWarn("�������û���.");
                return null;
            }
            if (StringUtils.isBlank(operPasswd)) {
                MessageUtil.addWarn("�������û�����.");
                return null;
            }

            ptoper = platformService.selectOper(operId);

            if (ptoper == null) {
                MessageUtil.addWarn("���û������ڻ��������.");
                return null;
            } else {
                if (!MD5Helper.getMD5String(operPasswd).equals(ptoper.getPasswd())) {
                    //TODO �������ۼƴ�����ʱ��
                    //ptoper.setFillint6(ptoper.getFillint6() + 1);
                    MessageUtil.addWarn("���û������ڻ��������...");
                    return null;
                }

/*
                if (!"1".equals(ptoper.getEnabled())) {
                    MessageUtil.addWarn("���û��ѽ���.");
                    return null;
                }
*/
                //��֤ͨ��
                this.loginTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
                ptoper.setOnlineSituation(loginTime);
                platformService.updateOperLoginInfo(ptoper);

//                OperShow operShowTemp=new OperShow();
//                BeanUtils.copyProperties(operShowTemp, ptoper);
                //��ʱ�������ݾ�ƽ̨
                getSession().setAttribute(SystemAttributeNames.USER_INFO_NAME, ptoper);
                return "/UI/guangda/dashboard";
            }

        } catch (Exception e) {
            logger.error("��¼ʱ���ִ���", e);
            return "error?faces-redirect=true";
        }
    }

    public String onLogout() {
        HttpSession session = getSession();
        session.invalidate();
        return "/login";
    }

    private HttpSession getSession(){
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    //==============================================

    public GdPtoper getPtoper() {
        return ptoper;
    }

    public void setPtoper(GdPtoper ptoper) {
        this.ptoper = ptoper;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public String getDeptId() {
        return deptId;
    }


    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getOperPasswd() {
        return operPasswd;
    }

    public void setOperPasswd(String operPasswd) {
        this.operPasswd = operPasswd;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
