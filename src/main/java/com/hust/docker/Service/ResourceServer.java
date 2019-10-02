package com.hust.docker.Service;

import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.DockerResource;

import java.util.List;

public interface ResourceServer {
    public DockerResource getResourceById(int id) throws Exception;
    public List<DockerResource> getResource() throws Exception;
    public List<DockerResource> getResourceByType(int type) throws Exception;
}
