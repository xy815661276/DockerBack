package com.hust.docker.service;

/**
 * @author Autubrew
 * @date 2020-02-22 17:29
 */

public interface VisitorService {

    /**
     * 获取网站访问量
     */
    Integer getPv() throws Exception;

    /**
     * 获取独立访客数
     */
    Integer getUv() throws Exception;

    /**
     * 获取独立ip数 （一天内访问的IP数）
     */
    Integer getIp() throws Exception;

    /**
     * 网站访问量 +1
     */
    void addPv() throws Exception;

    /**
     * 独立访客数 +1
     */
    void addUv() throws Exception;

    /**
     * 独立ip数 +1
     * */
    void addIp() throws Exception;

}
