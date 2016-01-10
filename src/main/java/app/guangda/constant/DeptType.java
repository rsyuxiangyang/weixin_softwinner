package app.guangda.constant;

import java.util.Hashtable;

/**
 * Created by XIANGYANG on 2016-1-7.
 */
public enum DeptType {
    COMMUNITY_BRANCH("COMMUNITY_BRANCH", "社区支行"),
    SUB_BRANCH("SUB_BRANCH", "分支行");

    private String code = null;
    private String title = null;
    private static Hashtable<String, DeptType> aliasEnums;

    DeptType(String code, String title) {
        this.init(code, title);
    }

    @SuppressWarnings("unchecked")
    private void init(String code, String title) {
        this.code = code;
        this.title = title;
        synchronized (this.getClass()) {
            if (aliasEnums == null) {
                aliasEnums = new Hashtable();
            }
        }
        aliasEnums.put(code, this);
        aliasEnums.put(title, this);
    }

    public static DeptType valueOfAlias(String alias) {
        DeptType cutpayChannelEnum = aliasEnums.get(alias);
        return cutpayChannelEnum;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}
