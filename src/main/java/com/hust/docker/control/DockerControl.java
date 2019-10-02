package com.hust.docker.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DockerControl {
    @RequestMapping({"", "index"})
    public String index(){
        return "index";
    }

}
