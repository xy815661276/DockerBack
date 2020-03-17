package com.hust.docker.util;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * @author Autubrew
 * @date 2020-02-22 0:46
 * @description 获取列表进行分页，同时计算页数
 */

public class Pagination {

    private Pagination() {
    }

    /**
     * 分页
     *
     * @param list 列表
     * @param index 页数，从1开始
     * @param limit 每页数量
     * @return 第index页的子列表已经总页数
     * */
    public static <T> Pair<List<T>, Integer> getPage(List<T> list, Integer index, Integer limit) {
        Pair<List<T>, Integer> result;
        int total = list.size();
        int pages = (int) Math.ceil((double)total / limit);
        if(index >= 1 && index <= pages) {
            if(index == pages) {
                result = new MutablePair<>(list.subList((index - 1) * limit, index * limit - (index * limit - total)), pages);
            } else {
                result = new MutablePair<>(list.subList((index - 1) * limit, index * limit), pages);
            }
        } else {
            result = new MutablePair<>(new ArrayList<>(), pages);
        }
        return result;
    }
}
