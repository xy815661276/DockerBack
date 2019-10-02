package com.hust.docker.Mapper;

import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.DockerCategory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryMapper {

        /**
         * 根据id查询
         * @param id
         * @return
         */
        DockerCategory selectById(int id);

        /**
         * 查询所有
         * @return
         */
        List<DockerCategory> selectAll();
}
