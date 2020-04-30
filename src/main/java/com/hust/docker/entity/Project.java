package com.hust.docker.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.StringReader;

@Getter
@Setter
@NoArgsConstructor
public class Project implements Comparable<Project> {

    private Integer id;

    private String title;

    private String excerpt;

    private String content;

    private String authors;

    private String conference;

    private Integer year;

    private String thumbnail;

    private String slug;

    private String pdf;

    private String code;

    private Integer views;

    /**
     * 按year降序排序。若year相同，则按conference升序排序。若conference相同，则按id升序排序。
     */
    @Override
    public int compareTo(Project o) {
        if (this.year.equals(o.year)) {
            if (this.conference.equals(o.conference)) {
                return this.id - o.id;
            } else {
                return this.conference.compareTo(o.conference);
            }
        } else {
            return o.year - this.year;
        }
    }
}
