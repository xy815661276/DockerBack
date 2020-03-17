package com.hust.docker.mapper;

import com.hust.docker.entity.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ResourceMapper {
    /**
     * 根据id查询
     *
     * @param id   资源id
     * @param lang 语言
     * @return id唯一对应的一个资源，或查询不到为空
     */
    Resource selectById(Integer id, String lang);

    /**
     * 查询所有资源
     *
     * @param lang 语言
     * @return 所有资源
     */
    List<Resource> selectAll(String lang);

    /**
     * 按标签key查询资源（同一resources不同lang下的tag可能不同，但tagKey一定相同）
     *
     * @param tag  资源标签
     * @param lang 语言
     * @return 返回一个或一组资源，若不存在则返回空
     */
    List<Resource> selectByTag(String tag, String lang);

    /**
     * 按年份查询资源
     *
     * @param year 资源年代
     * @param lang 语言
     * @return 返回一个或一组资源，若不存在则返回空
     */
    List<Resource> selectByYear(Integer year, String lang);

    /**
     * 查询分类会议资源信息
     *
     * @param conference 资源会议
     * @param lang       语言
     * @return 返回一个或一组资源，若不存在则返回空
     */
    List<Resource> selectByConference(String conference, String lang);

    /**
     * 按conference查询分组，并统计每组数量
     *
     * @param lang 语言
     * @return 返回一组或若干组[conference, conferenceName, count]，不存在则为空
     */
    List<Map<String, Integer>> selectGroupByConference(String lang);

    /**
     * 按tagKey查询分组，并统计每组数量
     *
     * @param lang 语言
     * @return 返回一组或若干组[tag, tagName, count]，不存在则为空
     */
    List<Map<String, Integer>> selectGroupByTag(String lang);

    /**
     * 按year查询分组，并统计每组数量
     *
     * @param lang 语言
     * @return 返回一组或若干组[year, yearName, count]，不存在则为空
     */
    List<Map<String, Integer>> selectGroupByYear(String lang);

    /**
     * 搜索资源
     *
     * @param query 查询内容
     * @param lang  语言
     * @return 查询匹配的资源
     */
    List<Resource> search(String query, String lang);

    /**
     * 浏览量加一，该方法应作用到所有语言的project表上的views值
     *
     * @param id 资源id
     */
    void addViews(Integer id);

}
