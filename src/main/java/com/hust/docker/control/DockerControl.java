package com.hust.docker.control;

import com.hust.docker.Service.ResourceServer;
import com.hust.docker.entity.ResponseJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
@RequestMapping("/website")
public class DockerControl {

    private final static Logger logger = LoggerFactory.getLogger(DockerControl.class);
    @Autowired
    private ResourceServer resourceServer;
    @RequestMapping("/")
    public String index(){
        logger.info("有人访问网站");
        return "welcome";
    }

    /**
     * 浏览量加一
     * @param index
     * @return responseJSON
     * @throws Exception
     */
    @GetMapping("/pdf/{index}")
    public String addViews(@PathVariable(value = "index") String index) throws Exception{
        resourceServer.addViews(index);
        logger.info("有人访问网站的pdf资源,资源为"+index);
        return "success";
    }

}
