package com.hust.docker.Mapper;

import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.DockerWebsite;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WebsiteMapper {

        /**
         * 根据id查询
         * @param id
         * @return
         */
        DockerWebsite selectById(int id);

        /**
         * 查询所有
         * @return
         */
        List<DockerWebsite> selectAll();

        void addViews();
}
