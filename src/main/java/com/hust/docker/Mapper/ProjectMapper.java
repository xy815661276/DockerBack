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
         * 查询所有博客信息
         * @return
         */
        List<DockerProject> selectAll();
}
