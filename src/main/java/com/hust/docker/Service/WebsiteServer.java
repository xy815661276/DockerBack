package com.hust.docker.Service;

import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.DockerWebsite;

import java.util.List;

public interface WebsiteServer {
    //显示所有用户
    public DockerWebsite getWebsiteViewsById(int id) throws Exception;
    //显示所有用户
    public List<DockerWebsite> getViews() throws Exception;
    public void addViews() throws Exception;
}
