package com.hust.docker.Service;

import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.DockerProject;

import java.util.List;

public interface ProjectServer {
    //通过id查找项目
    public DockerProject getProjectById(int id) throws Exception;
    //获取所有项目
    public List<DockerProject> getProject() throws Exception;
    //增加浏览量
    public void addViews(int id) throws Exception;
}
