package com.hust.docker.service;

import com.hust.docker.entity.Project;

import java.util.List;

public interface ProjectService {

    /**
     * 根据id获取项目
     *
     * @param id   项目id
     * @param lang 语言
     * @return id唯一对应的一个项目，或查询不到为空
     */
    public Project getProjectById(Integer id, String lang) throws Exception;

    /**
     * 根据id获取项目
     *
     * @param slug   项目slug
     * @param lang 语言
     * @return id唯一对应的一个项目，或查询不到为空
     */
    public Project getProjectBySlug(String slug, String lang) throws Exception;

    /**
     * 获取所有项目简介信息
     *
     * @param lang 语言
     * @return 所有项目
     */
    public List<Project> getAllProjectsBrief(String lang) throws Exception;

    /**
     * 获取所有项目
     *
     * @param lang 语言
     * @return 所有项目
     */
    public List<Project> getAllProjects(String lang) throws Exception;

    /**
     * 搜索项目
     *
     * @param query 查询的内容
     * @param lang  语言
     * @return 查询匹配的项目
     */
    public List<Project> search(String query, String lang) throws Exception;

    /**
     * 所有语言的同一个项目表访问量 +1
     *
     * @param id 用户访问的项目所对应的id
     */
    public void addViews(Integer id) throws Exception;
}