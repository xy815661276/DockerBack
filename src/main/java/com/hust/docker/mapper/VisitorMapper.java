package com.hust.docker.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author Autubrew
 * @date 2020-02-22 17:17
 */

@Repository
public interface VisitorMapper {

    /**
     * 获取网站访问量
     */
    Integer getPv();

    /**
     * 获取独立访客数
     */
    Integer getUv();

    /**
     * 获取独立ip数
     */
    Integer getIp();

    /**
     * 网站访问量 +1
     */
    void addPv();

    /**
     * 独立访客数 +1
     */
    void addUv();

    /**
     * 独立ip数 +1
     */
    void addIp();

}
