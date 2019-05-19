package com.cqupt.imis.tools.armyknife.controller.view;

import com.cqupt.imis.tools.armyknife.common.path.ViewPath;
import com.cqupt.imis.tools.armyknife.common.util.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author jinyu.meng
 * @date 2018/3/27
 */
@RequestMapping
public class DbViewController {

    @RequestMapping(value = ViewPath.MYSQL_VIEW_PATH)
    public void load(HttpServletResponse response) throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("templates/mysql.html");
        response.setContentType("text/html; charset=utf-8");
        response.getOutputStream().write(new String(IOUtils.readByteArray(in)).getBytes());

    }
}
