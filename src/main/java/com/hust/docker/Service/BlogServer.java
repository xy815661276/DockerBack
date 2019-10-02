package com.hust.docker.Service;

import com.hust.docker.entity.DockerBlog;

import java.util.List;

public interface BlogServer {
    //显示所有用户
    public DockerBlog getBlogById(int id) throws Exception;
    //显示所有用户
    public List<DockerBlog> getBlog() throws Exception;
    //根据id删除用户
    public void deleteBlog(int id) throws Exception;
    //新增用户
    public void addBlog(DockerBlog blog) throws Exception;
}
