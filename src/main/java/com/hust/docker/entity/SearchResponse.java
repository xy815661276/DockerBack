package com.hust.docker.entity;

public class SearchResponse {
    private int code;
    private Object projects;
    private Object resources;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getProjects() {
        return projects;
    }

    public void setProjects(Object projects) {
        this.projects = projects;
    }

    public Object getResources() {
        return resources;
    }

    public void setResources(Object resources) {
        this.resources = resources;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
