package com.hust.docker.service.impl;

import com.hust.docker.mapper.VisitorMapper;
import com.hust.docker.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Autubrew
 * @date 2020-02-22 17:38
 */

@Service
public class VisitorServiceImpl implements VisitorService {

    private VisitorMapper visitorMapper;

    @Autowired
    public VisitorServiceImpl(VisitorMapper visitorMapper) {
        this.visitorMapper = visitorMapper;
    }

    @Override
    public Integer getPv() throws Exception {
        return visitorMapper.getPv();
    }

    @Override
    public Integer getUv() throws Exception {
        return visitorMapper.getUv();
    }

    @Override
    public Integer getIp() throws Exception {
        return visitorMapper.getIp();
    }

    @Override
    public void addPv() throws Exception {
        visitorMapper.addPv();
    }

    @Override
    public void addUv() throws Exception {
        visitorMapper.addUv();
    }

    @Override
    public void addIp() throws Exception {
        visitorMapper.addIp();
    }
}
