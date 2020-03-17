package com.hust.docker.mapper;

import com.hust.docker.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectMapper {

    /**
     * 根据id查询
     *
     * @param id   项目id
     * @param lang 语言
     * @return id唯一对应的一个项目，或查询不到为空
     */
    Project selectById(Integer id, String lang);

    /**
     * 根据slug查询
     *
     * @param slug 项目slug
     * @param lang 语言
     * @return id唯一对应的一个项目，或查询不到为空
     */
    Project selectBySlug(String slug, String lang);

    /**
     * 查询所有项目简介信息
     *
     * @param lang 语言
     * @return 所有项目
     */
    List<Project> selectAllBrief(String lang);


    /**
     * 查询所有项目
     *
     * @param lang 语言
     * @return 所有项目
     */
    List<Project> selectAll(String lang);

    /**
     * 搜索项目
     *
     * @param query 查询内容
     * @param lang  语言
     * @return 查询匹配的项目
     */
    List<Project> search(String query, String lang);

    /**
     * 浏览量加一，该方法应作用到所有语言的project表上的views值
     *
     * @param id 项目id
     */
    void addViews(Integer id);

}