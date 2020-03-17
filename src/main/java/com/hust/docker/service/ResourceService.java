package com.hust.docker.service;

import com.hust.docker.entity.Resource;

import java.util.List;
import java.util.Map;

public interface ResourceService {

    /**
     * 根据id获取资源
     *
     * @param id   资源id
     * @param lang 语言
     * @return id唯一对应的一个资源，或查询不到为空
     */
    Resource getResourceById(Integer id, String lang) throws Exception;

    /**
     * 获取所有资源
     *
     * @param lang 语言
     * @return 所有资源
     */
    List<Resource> getAllResources(String lang) throws Exception;

    /**
     * 根据标签获取资源
     *
     * @param tag  资源标签
     * @param lang 语言
     * @return 标签对应的一个或若干个资源，若标签不存在则返回空。
     */
    List<Resource> getResourceByTag(String tag, String lang) throws Exception;

    /**
     * 根据年份获取资源
     *
     * @param year 资源发表的年份
     * @param lang 语言
     * @return 年份对应的一个或若干个资源，若不存在对应的年份则返回空。
     */
    List<Resource> getResourceByYear(Integer year, String lang) throws Exception;

    /**
     * 根据会议获取资源
     *
     * @param conference 资源所属的会议
     * @param lang       语言
     * @return 会议对应的一个或若干个资源，若不存在对应的会议则返回空
     */
    List<Resource> getResourceByConference(String conference, String lang) throws Exception;

    /**
     * 按会议给资源分组，同时统计每组的数量
     *
     * @param lang 语言
     * @return 三元组 [conference, conferenceName, count]
     */
    List<Map<String, Integer>> getResourceGroupByConference(String lang) throws Exception;

    /**
     * 按标签给资源分组，同时统计每组的数量
     *
     * @param lang 语言
     * @return 三元组 [tag, tagName, count]
     */
    List<Map<String, Integer>> getResourceGroupByTag(String lang) throws Exception;

    /**
     * 按年份给资源分组，同时统计每组的数量
     *
     * @param lang 语言
     * @return 三元组 [year, yearName, count]
     */
    List<Map<String, Integer>> getResourceGroupByYear(String lang) throws Exception;

    /**
     * 按keyword获取分类type下的资源列表
     *
     * @param category 分类类别
     * @param keyword  类别关键词
     * @param lang     语言
     * @return 分类type下关键词为keyword的资源列表
     */
    List<Resource> getOneCategoryResource(String category, String keyword, String lang) throws IllegalStateException;

    /**
     * 搜索资源
     *
     * @param query 查询的内容
     * @param lang  语言
     * @return 查询匹配的资源
     */
    List<Resource> search(String query, String lang) throws Exception;

    /**
     * 所有语言的同一个资源表访问量 +1
     *
     * @param id 用户访问的资源所对应的id
     */
    void addViews(Integer id) throws Exception;

}
