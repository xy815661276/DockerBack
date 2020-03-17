package com.hust.docker.controller;

import com.hust.docker.entity.Project;
import com.hust.docker.entity.Resource;
import com.hust.docker.service.ProjectService;
import com.hust.docker.service.ResourceService;
import com.hust.docker.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Autubrew
 * @date 2020-02-22 18:21
 */

@RestController
public class SearchController {

    private ProjectService projectService;
    private ResourceService resourceService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 搜索项目和资源
     *
     * @param query 查询内容
     * @param lang  语言
     * @return 匹配的项目和资源列表
     * data: {
     *     projects: {},
     *     resources: {}
     * }
     */
    @GetMapping("/search")
    public ResponseJson<Map<String, Object>> search(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "lang", required = false, defaultValue = "en") String lang
    ) throws Exception {
        List<Project> matchedProjects = projectService.search(query, lang);
        List<Resource> matchedResources = resourceService.search(query, lang);
        Map<String, Object> data = new HashMap<>(2);
        data.put("projects", matchedProjects);
        data.put("resources", matchedResources);
        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        responseJson.setData(data);
        if(matchedProjects != null && !matchedProjects.isEmpty() || matchedResources != null && !matchedResources.isEmpty()) {
            responseJson.setCode(200);
            responseJson.setMsg("Success to find!");
        } else {
            responseJson.setCode(204);
            responseJson.setMsg("No results!");
        }
        return responseJson;
    }
}
