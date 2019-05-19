package com.cqupt.imis.tools.armyknife.controller.view;

import com.cqupt.imis.tools.armyknife.common.annotation.ArmyknifeTools;
import com.cqupt.imis.tools.armyknife.common.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @author  zhoujun
 */
@Slf4j
@RestController
@RequestMapping("/armyknife")
public class ArmyknifeViewController {

    @Autowired
    ApplicationContext applicationContext;
    @GetMapping("/tools")
    public void menu(HttpServletResponse response) throws IOException {
        Map<String, Object> map=applicationContext.getBeansWithAnnotation(ArmyknifeTools.class);
        if (CollectionUtils.isEmpty(map)){
            InputStream in = ArmyknifeViewController.class.getClassLoader().getResourceAsStream("templates/armyknife/error.html");
            response.setContentType("text/html; charset=utf-8");
            response.getOutputStream().write(IOUtils.readByteArray(in));
            return;
        }
        InputStream in = ArmyknifeViewController.class.getClassLoader().getResourceAsStream("templates/armyknife/menu.html");
        response.setContentType("text/html; charset=utf-8");
        response.getOutputStream().write(IOUtils.readByteArray(in));

    }

    @GetMapping("/index")
    public void error(HttpServletResponse response) throws IOException {
        Map<String, Object> map=applicationContext.getBeansWithAnnotation(ArmyknifeTools.class);
        if (CollectionUtils.isEmpty(map)){
            InputStream in = ArmyknifeViewController.class.getClassLoader().getResourceAsStream("templates/armyknife/error.html");
            response.setContentType("text/html; charset=utf-8");
            response.getOutputStream().write(IOUtils.readByteArray(in));
            return;
        }
        InputStream in = ArmyknifeViewController.class.getClassLoader().getResourceAsStream("templates/armyknife/index.html");
        response.setContentType("text/html; charset=utf-8");
        response.getOutputStream().write(IOUtils.readByteArray(in));

    }


//    @RequestMapping("/login.html")
//    public void login(HttpServletResponse response) throws IOException {
//        InputStream in = getClass().getClassLoader().getResourceAsStream("templates/login.html");
//        response.setContentType("text/html; charset=utf-8");
//        response.getOutputStream().write(new String(IOUtils.readByteArray(in)).getBytes());
//    }
}
