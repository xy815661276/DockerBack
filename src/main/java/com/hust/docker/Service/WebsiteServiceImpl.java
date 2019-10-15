package com.hust.docker.Service;

import com.hust.docker.Mapper.BlogMapper;
import com.hust.docker.Mapper.WebsiteMapper;
import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.DockerWebsite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteServiceImpl implements WebsiteServer{
    @Autowired
    private WebsiteMapper websiteMapper;

    @Override
    public DockerWebsite getWebsiteViewsById(int id) throws Exception {
        return websiteMapper.selectById(id);
    }

    @Override
    public List<DockerWebsite> getViews() throws Exception {
        return websiteMapper.selectAll();
    }

    @Override
    public void addViews() throws Exception {
        websiteMapper.addViews();
    }
}
