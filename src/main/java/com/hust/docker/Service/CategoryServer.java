package com.hust.docker.Service;

import com.hust.docker.entity.DockerCategory;
import com.hust.docker.entity.DockerResource;

import java.util.List;

public interface CategoryServer {
    public DockerCategory getCategoryById(int id) throws Exception;
    public List<DockerCategory> getCategory() throws Exception;
}
