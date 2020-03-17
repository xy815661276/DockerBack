package com.hust.docker.service.impl;

import com.hust.docker.mapper.ProjectMapper;
import com.hust.docker.entity.Project;
import com.hust.docker.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectMapper projectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectMapper projectMapper){
        this.projectMapper = projectMapper;
    }

    @Override
    public Project getProjectById(Integer id, String lang) throws Exception {
        return projectMapper.selectById(id, lang);
    }

    @Override
    public Project getProjectBySlug(String slug, String lang) throws Exception {
        return projectMapper.selectBySlug(slug, lang);
    }

    @Override
    public List<Project> getAllProjectsBrief(String lang) throws Exception {
        return projectMapper.selectAllBrief(lang);
    }

    @Override
    public List<Project> getAllProjects(String lang) throws Exception {
        return projectMapper.selectAll(lang);
    }

    @Override
    public List<Project> search(String query, String lang) throws Exception {
        return projectMapper.search(query, lang);
    }

    @Override
    public void addViews(Integer id) throws Exception {
        projectMapper.addViews(id);
    }
}