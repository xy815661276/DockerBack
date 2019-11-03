package com.hust.docker.control;

import com.hust.docker.Service.CategoryServer;
import com.hust.docker.Service.ProjectServer;
import com.hust.docker.Service.ResourceServer;
import com.hust.docker.entity.DockerCategory;
import com.hust.docker.entity.DockerProject;
import com.hust.docker.entity.DockerResource;
import com.hust.docker.entity.ResponseJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceControl {
    @Autowired
    private ResourceServer resourceServer;
    @Autowired
    private CategoryServer categoryServer;
    /**
     * 查找所有的BLOG
     * @return ResponseJSON
     */
    @GetMapping("/getAll")
    public ResponseJSON getResources() throws Exception{
        List<DockerResource> list = resourceServer.getResource();
        if (list != null && list.size() > 0) {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(1);
            responseJSON.setMessage("Find All Resources Success!");
            responseJSON.setBody(list);
            return responseJSON;
        } else {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(2);
            responseJSON.setMessage("Failed!");
            responseJSON.setBody(list);
            return responseJSON;
        }
    }
    /**
     * 按页查找资源
     * @return ResponseJSON
     */
    @GetMapping("/category/{type}/{index}")
    public ResponseJSON getCategoryById(@PathVariable(value = "type") int type,
                                    @PathVariable(value = "index") int index) throws Exception{

        List<DockerResource> list;
        if(type<100)
            list = resourceServer.getResourceByType(type);
        else if(type<=200){
            DockerCategory dockerCategory = categoryServer.getCategoryById(type);
            list = resourceServer.getResourceByTime(Integer.parseInt(dockerCategory.getName()));
        }
        else {
            DockerCategory dockerCategory = categoryServer.getCategoryById(type);
            list = resourceServer.getResourceByConference(dockerCategory.getName());
        }
        DockerResource dockerResource = list.get(0);
        list.remove(0);
        list.add(dockerResource);
        if (list.size() > 0) {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(3);
            responseJSON.setMessage("Find Resources Success!");
            List<DockerResource> page = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(i>=(index-1)*5&&i<index*5){
                    page.add(list.get(i));
                }
            }
            responseJSON.setBody(page);
            responseJSON.setPages((int)Math.ceil(((double)list.size())/5));
            return responseJSON;
        } else {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(4);
            responseJSON.setMessage("Failed!");
            responseJSON.setBody(list);
            return responseJSON;
        }
    }

    /**
     * 按页查找资源
     * @return ResponseJSON
     */
    @GetMapping("/category")
    public ResponseJSON getCategoryById() throws Exception{
        List<DockerCategory> list = categoryServer.getCategory();
        if (list != null && list.size() > 0) {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(7);
            responseJSON.setMessage("Find Category Success!");
            responseJSON.setBody(list);
            return responseJSON;
        } else {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(8);
            responseJSON.setMessage("Failed!");
            responseJSON.setBody(list);
            return responseJSON;
        }
    }
    /**
     * 按页查找资源
     * @return ResponseJSON
     */
    @GetMapping("/page/{index}")
    public ResponseJSON getResourcesByIndex(@PathVariable(value = "index") int index) throws Exception{
        List<DockerResource> list = resourceServer.getResource();
        DockerResource dockerResource = list.get(0);
        list.remove(0);
        list.add(dockerResource);
        if (list.size() > 0) {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(9);
            responseJSON.setMessage("Find Page Resources Success!");
            List<DockerResource> page = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(i>=(index-1)*5&&i<index*5){
                    page.add(list.get(i));
                }
            }
            responseJSON.setBody(page);
            responseJSON.setPages((int)Math.ceil(((double)list.size())/5));
            return responseJSON;
        } else {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(10);
            responseJSON.setMessage("Failed!");
            responseJSON.setBody(list);
            return responseJSON;
        }
    }
}
