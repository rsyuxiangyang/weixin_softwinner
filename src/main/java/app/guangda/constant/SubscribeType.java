package app.guangda.constant;

import java.util.Hashtable;

/**
 * Created by XIANGYANG on 2016-1-7.
 */
public enum SubscribeType {
    SUBSCRIBE("1", "关注"),
    UNSUBSCRIBE("0", "取消关注");

    private String code = null;
    private String title = null;
    private static Hashtable<String, SubscribeType> aliasEnums;

    SubscribeType(String code, String title) {
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

    public static SubscribeType valueOfAlias(String alias) {
        SubscribeType cutpayChannelEnum = aliasEnums.get(alias);
        return cutpayChannelEnum;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}
