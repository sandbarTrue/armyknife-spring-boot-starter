package com.cqupt.imis.tools.armyknife.controller.api;

import com.cqupt.imis.tools.armyknife.common.path.ApiPath;
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

    @RequestMapping(ApiPath.MYSQL_LOAD_API)
    public Object load() {

        Map<String, String> result = new HashMap<String, String>();

        Map<String, DataSource> map = applicationContext.getBeansOfType(DataSource.class);

        for (Map.Entry<String, DataSource> entry : map.entrySet()) {
            result.put("数据源:" + entry.getKey(), entry.getKey());
        }
        return result;
    }

    @RequestMapping(ApiPath.MYSQL_DOSQL_API)
    public Object doSql(@RequestParam("dataSource") String dataSource,
                        @RequestParam("sql") String sql) throws SQLException {
        String executeSQl = "";
        PreparedStatement preparedStatement = null;
        Connection connection = ((DataSource) applicationContext.getBean(dataSource)).getConnection();
        //|| sql.toUpperCase().contains("DELETE")
        if (sql.toUpperCase().contains("SELECT")  || sql.toUpperCase().contains("DROP") || sql.toUpperCase().contains("ALTER")) {
            /**
             * 含有 SELECT DELETE DROP ALTER 的SQL 不执行
             */

            return false;
        }
        //&& sql.toUpperCase().contains("WHERE ID=")
        if (sql.toUpperCase().contains("UPDATE") ) {
            executeSQl = sql;
        }

        if (sql.toUpperCase().contains("INSERT")) {
            executeSQl = sql;
        }

        if (sql.toUpperCase().contains("DELETE")){
            executeSQl = sql;
        }

        if (StringUtils.isBlank(executeSQl)) {
            return Boolean.FALSE;
        }
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

}
