package com.hust.docker.Service;

import com.hust.docker.Mapper.ProjectMapper;
import com.hust.docker.Mapper.ResourceMapper;
import com.hust.docker.entity.DockerProject;
import com.hust.docker.entity.DockerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceServer{
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<DockerResource> getResource() throws Exception {
        return resourceMapper.selectAll();
    }

    @Override
    public DockerResource getResourceById(int id) throws Exception {
        return resourceMapper.selectById(id);
    }
    @Override
    public List<DockerResource> getResourceByType(int type) throws Exception {
        return resourceMapper.selectByType(type);
    }
    @Override
    public void addViews(int id) throws Exception {
        resourceMapper.addViews(id);
    }

    @Override
    public List<DockerResource> getCnResource() throws Exception {
        return resourceMapper.selectCnAll();
    }

    @Override
    public List<DockerResource> getCnResourceByType(int type) throws Exception {
        return resourceMapper.selectCnByType(type);
    }

    @Override
    public List<DockerResource> getCnResourceByTime(int time) throws Exception {
        return resourceMapper.selectCnByTime(time);
    }

    @Override
    public List<DockerResource> getResourceByTime(int time) throws Exception {
        return resourceMapper.selectByTime(time);
    }
}
