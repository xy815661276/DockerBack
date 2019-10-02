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
}