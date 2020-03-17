package com.hust.docker.controller;

import com.hust.docker.entity.Project;
import com.hust.docker.service.ProjectService;
import com.hust.docker.util.Pagination;
import com.hust.docker.util.ResponseJson;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * 查找所有的项目
     *
     * @param lang 语言，默认英文
     * @return 所有项目
     * data: {
     * projects: {
     * items: {},
     * total:
     * }
     * }
     */
    @GetMapping("/all")
    public ResponseJson<Map<String, Object>> getAllProjects(
            @RequestParam(value = "lang", defaultValue = "en") String lang)
            throws Exception {
        List<Project> allProjects = projectService.getAllProjects(lang);
        Collections.sort(allProjects);
        Map<String, Object> projects = new HashMap<>(2);
        projects.put("items", allProjects);
        projects.put("total", allProjects.size());
        Map<String, Object> data = new HashMap<>(1);
        data.put("projects", projects);
        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        responseJson.setData(data);
        if (allProjects != null && !allProjects.isEmpty()) {
            responseJson.setCode(200);
            responseJson.setMsg("Find all projects successfully!");
        } else {
            responseJson.setCode(204);
            responseJson.setMsg("Failed to find all projects!");
        }
        return responseJson;
    }

    /**
     * 按页查找指定范围的项目
     *
     * @param index 页数，从1开始
     * @param limit 每页的项目数量，默认5
     * @param lang  语言，默认英文
     * @return 一页的项目
     * data: {
     * projects: {},
     * pages:
     * }
     */
    @GetMapping("/page")
    public ResponseJson<Map<String, Object>> getProjectsOnPage(
            @RequestParam(value = "index") Integer index,
            @RequestParam(value = "limit", required = false, defaultValue = "5") Integer limit,
            @RequestParam(value = "lang", defaultValue = "en") String lang
    ) throws Exception {
        List<Project> allProjects = projectService.getAllProjectsBrief(lang);
        Collections.sort(allProjects);
        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        Map<String, Object> data = new HashMap<>(1);
        Pair<List<Project>, Integer> pageList = Pagination.getPage(allProjects, index, limit);
        data.put("projects", pageList.getLeft());
        data.put("pages", pageList.getRight());
        if (!pageList.getLeft().isEmpty()) {
            responseJson.setCode(200);
            responseJson.setMsg("Find requested projects successfully!");
        } else {
            responseJson.setCode(204);
            responseJson.setMsg("Requested page out of scope!");
        }
        responseJson.setData(data);
        return responseJson;
    }

    /**
     * 获取指定项目的内容，若成功则刷新访问记录
     *
     * @param slug 项目slug
     * @param lang 语言
     * @return id对应的项目内容及其他信息
     * data: {
     * project: {}
     * }
     */
    @GetMapping("/post")
    public ResponseJson<Map<String, Object>> getPost(
            @RequestParam(value = "slug") String slug,
            @RequestParam(value = "lang", required = false, defaultValue = "en") String lang
    ) throws Exception {
        Project project = projectService.getProjectBySlug(slug, lang);
        Map<String, Object> data = new HashMap<>(1);
        data.put("project", project);
        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        responseJson.setData(data);
        if (project != null) {
            projectService.addViews(project.getId());
            responseJson.setCode(200);
            responseJson.setMsg("Find the post successfully!");
        } else {
            responseJson.setCode(204);
            responseJson.setMsg("Failed to find the post!");
        }
        return responseJson;
    }

}