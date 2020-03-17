package com.hust.docker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Resource implements Comparable<Resource> {

    private Integer id;

    private String title;

    private String introduction;

    private String conference;

    private String conferenceName;

    private Integer year;

    private String yearName;

    private String tag;

    private String tagName;

    private Integer citations;

    private String thumbnail;

    private String pdf;

    private Integer views;

    /**
     * 按year降序排序。若year相同，则按conference升序排序。若conference相同，则按id升序排序。
     * */
    @Override
    public int compareTo(Resource o) {
        if(this.year.equals(o.year)) {
            if(this.conference.equals(o.conference)) {
                return this.id - o.id;
            } else {
                return this.conference.compareTo(o.conference);
            }
        } else {
            return o.year - this.year;
        }
    }
}
