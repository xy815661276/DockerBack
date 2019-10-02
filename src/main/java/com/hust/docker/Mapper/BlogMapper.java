package com.hust.docker.Mapper;

import com.hust.docker.entity.DockerBlog;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogMapper {
        /**
         * 新增博客
         * @param blog
         * @return
         */
        int save(DockerBlog blog);

        /**
         * 更新博客信息
         * @param blog
         * @return
         */
        int update(DockerBlog blog);

        /**
         * 根据id删除
         * @param id
         * @return
         */
        int deleteById(int id);

        /**
         * 根据id查询
         * @param id
         * @return
         */
        DockerBlog selectById(int id);

        /**
         * 查询所有博客信息
         * @return
         */
        List<DockerBlog> selectAll();
}
