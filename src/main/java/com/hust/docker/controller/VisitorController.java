package com.hust.docker.controller;

import com.hust.docker.service.VisitorService;
import com.hust.docker.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Autubrew
 * @date 2020-02-22 17:41
 */

@RestController
public class VisitorController {

    private VisitorService visitorService;

    @Autowired
    public void setVisitorService(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    /**
     * 获取网站访问量
     *
     * @return 网站的总访问量
     * data: {
     * pv:
     * uv:
     * ip:
     * }
     */
    @GetMapping("/views")
    public ResponseJson<Map<String, Object>> getPv() throws Exception {
        visitorService.addPv();
        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        Map<String, Object> data = new HashMap<>(1);
        data.put("pv", visitorService.getPv());
        data.put("uv", visitorService.getUv());
        data.put("ip", visitorService.getIp());
        responseJson.setCode(200);
        responseJson.setMsg("OK!");
        responseJson.setData(data);
        return responseJson;
    }

    @PutMapping("/pv")
    public void addPv() throws Exception {
        visitorService.addPv();
    }

    @PutMapping("/uv")
    public void addUv() throws Exception {
        visitorService.addUv();
    }

    @PutMapping("/ip")
    public void addIp() throws Exception {
        visitorService.addIp();
    }

}
