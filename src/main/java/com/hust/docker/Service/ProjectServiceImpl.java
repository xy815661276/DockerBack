package com.hust.docker.Service;

import com.hust.docker.Mapper.BlogMapper;
import com.hust.docker.Mapper.ProjectMapper;
import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.DockerProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectServer{
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public List<DockerProject> getProject() throws Exception {
        return projectMapper.selectAll();
    }

    @Override
    public DockerProject getProjectById(int id) throws Exception {
        return projectMapper.selectById(id);
    }

    @Override
    public void addViews(int id) throws Exception {
        projectMapper.addViews(id);
    }

    @Override
    public List<DockerProject> getCnProject() throws Exception {
        return projectMapper.selectCnAll();
    }
}
