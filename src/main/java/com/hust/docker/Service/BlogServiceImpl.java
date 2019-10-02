package com.hust.docker.Service;

import com.hust.docker.Mapper.BlogMapper;
import com.hust.docker.entity.DockerBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogServer{
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<DockerBlog> getBlog() throws Exception {
        return blogMapper.selectAll();
    }

    @Override
    public DockerBlog getBlogById(int id) throws Exception {
        return blogMapper.selectById(id);
    }

    @Override
    public void deleteBlog(int id) throws Exception {
        blogMapper.deleteById(id);
    }

    @Override
    public void addBlog(DockerBlog blog) throws Exception {
        blogMapper.save(blog);
    }
}
