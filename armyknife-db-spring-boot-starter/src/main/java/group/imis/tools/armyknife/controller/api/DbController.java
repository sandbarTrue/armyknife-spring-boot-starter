package group.imis.tools.armyknife.controller.api;

import group.imis.tools.armyknife.common.path.ApiPath;
import group.imis.tools.armyknife.common.domain.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jinyu.meng
 * @date 2018/3/27
 */

@RequestMapping
@RestController
public class DbController {
    @Autowired
    ApplicationContext applicationContext;
    /**
     * 获取数据源
     * @return
     */
    @RequestMapping(ApiPath.MYSQL_LOAD_API)
    public Object load() {
        Map<String, String> result = new HashMap<String, String>();
        Map<String, DataSource> map = applicationContext.getBeansOfType(DataSource.class);
        for (Map.Entry<String, DataSource> entry : map.entrySet()) {
            result.put("数据源:" + entry.getKey(), entry.getKey());
        }
        return result;
    }
    /**
     * 执行sql
     * @param dataSource
     * @param sql
     * @return
     * @throws SQLException
     */
    @RequestMapping(ApiPath.MYSQL_DOSQL_API)
    public Object doSql(@RequestParam("dataSource") String dataSource,
                        @RequestParam("sql") String sql) throws SQLException {
        //校验sql是否合法
        if (!validSql(sql)) {
            return false;
        }
        String executeSQl = sql;
        if (StringUtils.isBlank(executeSQl)) {
            return Boolean.FALSE;
        }
        PreparedStatement preparedStatement = null;
        Connection connection = ((DataSource) applicationContext.getBean(dataSource)).getConnection();
        Boolean autoCommit = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(executeSQl);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            return Boolean.FALSE;
        } finally {
            connection.setAutoCommit(autoCommit);
            connection.close();
            preparedStatement.close();
        }
        return Boolean.TRUE;
    }
    private boolean validSql(String sql) {
        if(StringUtils.isBlank(sql)){
            return false;
        }
        //含有 SELECT DELETE DROP ALTER 的SQL 不执行
        if (sql.toUpperCase().contains(Constants.SELECT)  || sql.toUpperCase().contains(Constants.DROP) || sql.toUpperCase().contains(Constants.ALTER)) {
            return false;
        }
        //不含有where的不能执行
        if(!sql.toUpperCase().contains(Constants.WHERE)){
            return false;
        }
        //含有 update insert 和 delete 可以执行
        if (sql.toUpperCase().contains(Constants.UPDATE) || sql.toUpperCase().contains(Constants.INSERT) || sql.toUpperCase().contains(Constants.DELETE)) {
            return  true;
        }
        return false;
    }

}
