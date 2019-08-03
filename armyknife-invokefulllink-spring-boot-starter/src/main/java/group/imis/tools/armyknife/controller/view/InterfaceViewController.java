package group.imis.tools.armyknife.controller.view;

import group.imis.tools.armyknife.common.path.ViewPath;
import group.imis.tools.armyknife.common.util.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhoujun
 */

@RequestMapping
public class InterfaceViewController {

    @RequestMapping(ViewPath.INTERFACE_VIEW_PATH)
    public void consume(HttpServletResponse response) throws IOException {
        InputStream in = InterfaceViewController.class.getClassLoader().getResourceAsStream("templates/interface/interfaceinvoke.html");
        response.setContentType("text/html; charset=utf-8");
        response.getOutputStream().write(IOUtils.readByteArray(in));
        return;
    }
}
