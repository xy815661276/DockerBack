package com.hust.docker.control;

import com.hust.docker.Mapper.ProjectMapper;
import com.hust.docker.Service.ProjectServer;
import com.hust.docker.Service.ResourceServer;
import com.hust.docker.entity.DockerProject;
import com.hust.docker.entity.DockerResource;
import com.hust.docker.entity.ResponseJSON;
import com.hust.docker.entity.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SearchControl {
    @Autowired
    ProjectServer projectServer;
    @Autowired
    ResourceServer resourceServer;

    @GetMapping("/search")
    public SearchResponse search(@RequestParam("index") String index)throws Exception{
        List<DockerResource> list =  resourceServer.search(index);
        List<DockerProject> list1 =  projectServer.search(index);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setCode(1);
        searchResponse.setMessage("Search Success!");
        searchResponse.setResources(list);
        searchResponse.setProjects(list1);
        if(list.size()==0&&list1.size()==0)
            searchResponse.setCode(-1);
        return searchResponse;
    }
}
