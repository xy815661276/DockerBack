package com.hust.docker.Mapper;

import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.DockerProject;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectMapper {
        /**
         * 根据id查询
         * @param id
         * @return
         */
        DockerProject selectById(int id);

        /**
         * 查询所有项目信息
         * @return
         */
        List<DockerProject> selectAll();/**
         * 查询中文所有项目信息
         * @return
         */
        List<DockerProject> selectCnAll();
        /**
         * 浏览量加一
         */
        void addViews(int id);
        /**
         * 模糊搜索
         * @return
         */
        List<DockerProject> search(String index);
        /**
         * 模糊搜索
         * @return
         */
        List<DockerProject> searchCn(String index);
}
