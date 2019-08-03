package group.imis.tools.armyknife.common.enums;

import group.imis.tools.armyknife.common.path.ViewPath;

/**
 * Created by zhoujun5 on 27/03/2018.
 */
public enum MenuModelEnum {
    /**
     * mq一级菜单
     */
    MQMENU("mq管理",""),
    /**
     * 只有一级菜单的时候，二级菜单的值
     */
    NULL("",""),
    /**
     * mq二级菜单
     */
    PRODUCTMQ("生产MQ", ViewPath.MQ_PRODUCT_VIEW_PATH),
    /**
     * mq二级菜单
     */
    CONSUMEMQ("消费MQ",ViewPath.MQ_CONSUME_VIEW_PATH),
    /**
     * 接口测试一级菜单
     */
    INTERFACEINVOKE("接口调用",ViewPath.INTERFACE_VIEW_PATH),

    /**
     * 缓存管理一级菜单
     */
    CACHE_MANAGE("缓存管理",""),
    /**
     *数据库管理
     */
    DB_MENU("数据库管理",""),

    /**
     * 数据库二级菜单 mysql
     */
    DB_MYSQL_MENU("mysql",ViewPath.MYSQL_VIEW_PATH);

    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单链接
     */
    private String link;

    MenuModelEnum(String name,String link){
        this.link=link;
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
