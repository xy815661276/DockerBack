package com.hust.docker.controller;

import com.hust.docker.entity.Resource;
import com.hust.docker.service.ResourceService;
import com.hust.docker.util.Pagination;
import com.hust.docker.util.ResponseJson;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/resources", method = {RequestMethod.GET, RequestMethod.PUT})
public class ResourceController {

    private ResourceService resourceService;

    @Autowired
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 获取所有的资源
     *
     * @param lang 语言，默认英文
     * @return 所有资源
     * data: {
     * resources: {
     * items: {},
     * total:
     * }
     * }
     */
    @GetMapping("/all")
    public ResponseJson<Map<String, Object>> getAllResources(
            @RequestParam(value = "lang", defaultValue = "en") String lang
    ) throws Exception {
        List<Resource> allResources = resourceService.getAllResources(lang);
        Collections.sort(allResources);
        Map<String, Object> resources = new HashMap<>(2);
        resources.put("items", allResources);
        resources.put("total", allResources.size());
        Map<String, Object> data = new HashMap<>(1);
        data.put("resources", resources);
        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        responseJson.setData(data);
        if (allResources != null && !allResources.isEmpty()) {
            responseJson.setCode(200);
            responseJson.setMsg("Find all resources successfully!");
        } else {
            responseJson.setCode(204);
            responseJson.setMsg("Failed to find all resources!");
        }
        return responseJson;
    }

    /**
     * 按页查找指定范围的资源
     *
     * @param index 页数，从1开始
     * @param limit 每页的项目数量，默认5
     * @param lang  语言，默认英文
     * @return 一页的资源
     * data: {
     * resources: {},
     * pages:
     * }
     */
    @GetMapping("/page")
    public ResponseJson<Map<String, Object>> getResourcesOnPage(
            @RequestParam(value = "index") Integer index,
            @RequestParam(value = "limit", required = false, defaultValue = "5") Integer limit,
            @RequestParam(value = "lang", defaultValue = "en") String lang
    ) throws Exception {
        List<Resource> allResources = resourceService.getAllResources(lang);
        Collections.sort(allResources);
        Map<String, Object> data = new HashMap<>(1);
        Pair<List<Resource>, Integer> pageList = Pagination.getPage(allResources, index, limit);
        data.put("resources", pageList.getLeft());
        data.put("pages", pageList.getRight());
        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        if (!pageList.getLeft().isEmpty()) {
            responseJson.setCode(200);
            responseJson.setMsg("Find requested resources successfully!");
        } else {
            responseJson.setCode(204);
            responseJson.setMsg("Requested page out of scope!");
        }
        responseJson.setData(data);
        return responseJson;
    }

    /**
     * 获取所有资源的分类列表
     *
     * @param lang 语言
     * @return 所有资源
     * data: {
     * categoryList: {
     * conference: { {}, {}, ... },
     * tag: { {}, {}, ... },
     * year: { {}, {}, ... }
     * }
     * }
     */
    @GetMapping("/categoryList")
    public ResponseJson<Map<String, Object>> getCategoryList(
            @RequestParam(value = "lang", defaultValue = "en") String lang
    ) throws Exception {
        List<Map<String, Integer>> conference = resourceService.getResourceGroupByConference(lang);
        List<Map<String, Integer>> tag = resourceService.getResourceGroupByTag(lang);
        List<Map<String, Integer>> year = resourceService.getResourceGroupByYear(lang);
        Map<String, List<Map<String, Integer>>> categoryList = new HashMap<>(3);
        categoryList.put("conference", conference);
        categoryList.put("tag", tag);
        categoryList.put("year", year);
        Map<String, Object> data = new HashMap<>(1);
        data.put("categoryList", categoryList);

        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        responseJson.setData(data);
        if (tag != null && !tag.isEmpty()) {
            responseJson.setCode(200);
            responseJson.setMsg("Find all tag groups success!");
        } else {
            responseJson.setCode(204);
            responseJson.setMsg("Find all tag groups failed!");
        }
        return responseJson;
    }

    /**
     * 获取某一类别的所有资源
     *
     * @param keyword 类别type的具体关键词
     * @param type    分类类别，如conference、tag、year等
     * @param lang    语言
     * @return 某一分类下的资源
     * data: {
     * type: {},
     * pages:
     * }
     */
    @GetMapping("/oneCategory")
    public ResponseJson<Map<String, Object>> getOneCategory(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "lang", defaultValue = "en") String lang
    ) throws Exception {
        List<Resource> oneCategoryResources = resourceService.getOneCategoryResource(type, keyword, lang);
        Collections.sort(oneCategoryResources);
        Map<String, Object> data = new HashMap<>(1);
        data.put("type", oneCategoryResources);
        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        if (!oneCategoryResources.isEmpty()) {
            responseJson.setCode(200);
            responseJson.setMsg("Find all resources in a specified category successfully!");
        } else {
            responseJson.setCode(204);
            responseJson.setMsg("Failed to find all resources in a specified category!");
        }
        responseJson.setData(data);
        return responseJson;
    }

    /**
     * 按页查找指定类别的资源
     *
     * @param category 属于哪一类
     * @param keyword  类别下的具体值
     * @param index    页数，从1开始
     * @param limit    每页数量
     * @param lang     语言
     * @return 返回分类type，值为key的第index页的资源列表
     * data: {
     * resources: {}
     * pages:
     * }
     */
    @GetMapping("/oneCategoryPage")
    public ResponseJson<Map<String, Object>> getOnCategoryOnPage(
            @RequestParam(value = "category") String category,
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "index") Integer index,
            @RequestParam(value = "limit", required = false, defaultValue = "5") Integer limit,
            @RequestParam(value = "lang", defaultValue = "en") String lang
    ) throws Exception {
        List<Resource> oneCategoryResources = resourceService.getOneCategoryResource(category, keyword, lang);
        Collections.sort(oneCategoryResources);
        Pair<List<Resource>, Integer> pageList = Pagination.getPage(oneCategoryResources, index, limit);
        Map<String, Object> data = new HashMap<>(1);
        data.put("resources", pageList.getLeft());
        data.put("pages", pageList.getRight());
        ResponseJson<Map<String, Object>> responseJson = new ResponseJson<>();
        responseJson.setData(data);
        if (!pageList.getLeft().isEmpty()) {
            responseJson.setCode(200);
            responseJson.setMsg("Find requested resources successfully!");
        } else {
            responseJson.setCode(204);
            responseJson.setMsg("Requested page out of scope!");
        }
        return responseJson;
    }

    @PostMapping("/pdf")
    public void visitPdf(@RequestBody Map<String, Integer> obj) throws Exception {
        resourceService.addViews(obj.get("id"));
    }

}
