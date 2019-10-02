package com.hust.docker.Mapper;

import com.hust.docker.entity.DockerProject;
import com.hust.docker.entity.DockerResource;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResourceMapper {
        /**
         * 根据id查询
         * @param id
         * @return
         */
        DockerResource selectById(int id);

        /**
         * 查询所有博客信息
         * @return
         */
        List<DockerResource> selectAll();
        /**
         * 查询所有博客信息
         * @return
         */
        List<DockerResource> selectByType(int id);
}
