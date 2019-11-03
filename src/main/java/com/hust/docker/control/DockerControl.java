package com.hust.docker.control;

import com.hust.docker.Service.ResourceServer;
import com.hust.docker.Service.WebsiteServer;
import com.hust.docker.entity.DockerWebsite;
import com.hust.docker.entity.ResponseJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@ResponseBody
public class DockerControl {

    private final static Logger logger = LoggerFactory.getLogger(DockerControl.class);
    @Autowired
    private ResourceServer resourceServer;
    @Autowired
    private WebsiteServer websiteServer;
    @Autowired
    private HttpServletRequest httpRequest;
    @RequestMapping("/website")
    public String index() throws Exception{
        logger.info("有人访问网站 "+httpRequest.getHeader("URL"));
        websiteServer.addViews();
        return "welcome";
    }
    @GetMapping("/getWebsiteViews")
    public ResponseJSON getViews() throws Exception{
        DockerWebsite dockerWebsite = websiteServer.getWebsiteViewsById(1);
        ResponseJSON responseJSON=new ResponseJSON();
        responseJSON.setCode(1);
        responseJSON.setMessage("success!");
        responseJSON.setBody(dockerWebsite);
        return responseJSON;
    }
    /**
     * 浏览量加一
     * @param
     * @return responseJSON
     * @throws Exception
     */
    @RequestMapping("/pdf")
    public String addViewsPdf() throws Exception{
        String url = httpRequest.getHeader("URL");
        resourceServer.addViews(url);
        logger.info("有人访问网站的pdf资源,链接为:"+httpRequest.getHeader("URL"));
        return "success";
    }


}
