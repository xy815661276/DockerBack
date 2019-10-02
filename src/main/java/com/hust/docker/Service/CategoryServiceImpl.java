package com.hust.docker.Service;

import com.hust.docker.Mapper.CategoryMapper;
import com.hust.docker.Mapper.ResourceMapper;
import com.hust.docker.entity.DockerCategory;
import com.hust.docker.entity.DockerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryServer{
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<DockerCategory> getCategory() throws Exception {
        return categoryMapper.selectAll();
    }

    @Override
    public DockerCategory getCategoryById(int id) throws Exception {
        return categoryMapper.selectById(id);
    }
}
