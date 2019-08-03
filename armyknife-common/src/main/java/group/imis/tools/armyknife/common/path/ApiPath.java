package group.imis.tools.armyknife.common.path;

/**
 *
 * @author jinyu.meng
 * @date 2018/4/2
 */
public class ApiPath {
    /***
     * mysql 信息加载
     */
    public static final String MYSQL_LOAD_API="/api/armyknife/db/mysql/load";
    /***
     * mysql 执行sql
     */
    public static final String MYSQL_DOSQL_API="/api/armyknife/db/mysql/dosql";
    /***
     * 加载接口
     */
    public static final String INTERFACE_LOAD_API="/api/armyknife/interface/getInterfaces";
    /***
     * 执行接口
     */
    public static final String INTERFACE_INVOKW_API="/api/armyknife/interface/invoke";
    /***
     * 执行接口
     */
    public static final String MQ_LOAD_LISTENER_API="/api/armyknife/mq/getListeners";

    /***
     * 执行接口
     */
    public static final String MQ_LOAD_PRODUCER_API="/api/armyknife/mq/getProducers";

    /***
     * 执行接口
     */
    public static final String MQ_CONSUME_API="/api/armyknife/mq/consumemq";

    /***
     * 执行接口
     */
    public static final String MQ_PRODUCT_API="/api/armyknife/mq/sendmq";
}
