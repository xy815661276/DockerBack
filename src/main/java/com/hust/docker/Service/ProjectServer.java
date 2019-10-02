package com.hust.docker.Service;

import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.DockerProject;

import java.util.List;

public interface ProjectServer {
    //显示所有用户
    public DockerProject getProjectById(int id) throws Exception;
    //显示所有用户
    public List<DockerProject> getProject() throws Exception;
}
