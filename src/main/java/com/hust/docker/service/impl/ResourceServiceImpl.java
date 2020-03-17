package com.hust.docker.service.impl;

import com.hust.docker.entity.Resource;
import com.hust.docker.mapper.ResourceMapper;
import com.hust.docker.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResourceServiceImpl implements ResourceService {

    private ResourceMapper resourceMapper;

    @Autowired
    public ResourceServiceImpl(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    @Override
    public Resource getResourceById(Integer id, String lang) throws Exception {
        return resourceMapper.selectById(id, lang);
    }

    @Override
    public List<Resource> getAllResources(String lang) throws Exception {
        return resourceMapper.selectAll(lang);
    }

    @Override
    public List<Resource> getResourceByTag(String tag, String lang) throws Exception {
        return resourceMapper.selectByTag(tag, lang);
    }

    @Override
    public List<Resource> getResourceByYear(Integer year, String lang) throws Exception {
        return resourceMapper.selectByYear(year, lang);
    }

    @Override
    public List<Resource> getResourceByConference(String conference, String lang) throws Exception {
        return resourceMapper.selectByConference(conference, lang);
    }

    @Override
    public List<Map<String, Integer>> getResourceGroupByConference(String lang) throws Exception {
        return resourceMapper.selectGroupByConference(lang);
    }

    @Override
    public List<Map<String, Integer>> getResourceGroupByTag(String lang) throws Exception {
        return resourceMapper.selectGroupByTag(lang);
    }

    @Override
    public List<Map<String, Integer>> getResourceGroupByYear(String lang) throws Exception {
        return resourceMapper.selectGroupByYear(lang);
    }

    @Override
    public List<Resource> getOneCategoryResource(String category, String keyword, String lang) throws IllegalStateException {
        switch (category) {
            case "conference":
                return resourceMapper.selectByConference(keyword, lang);
            case "tag":
                return resourceMapper.selectByTag(keyword, lang);
            case "year":
                return resourceMapper.selectByYear(Integer.parseInt(keyword), lang);
            default:
                throw new IllegalStateException("Unexpected value: " + category);
        }
    }

    @Override
    public List<Resource> search(String query, String lang) throws Exception {
        return resourceMapper.search(query, lang);
    }

    @Override
    public void addViews(Integer id) throws Exception {
        resourceMapper.addViews(id);
    }
}
