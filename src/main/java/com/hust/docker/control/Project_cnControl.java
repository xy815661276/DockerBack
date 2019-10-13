package com.hust.docker.control;

import com.hust.docker.Service.ProjectServer;
import com.hust.docker.entity.DockerProject;
import com.hust.docker.entity.ResponseJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cn/projects")
public class Project_cnControl {
    @Autowired
    private ProjectServer projectServer;
    /**
     * 查找所有的BLOG
     * @return ResponseJSON
     */
    @GetMapping("/getAll")
    public ResponseJSON getCnProjects() throws Exception{
        List<DockerProject> list = projectServer.getCnProject();
        if (list != null && list.size() > 0) {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(1);
            responseJSON.setMessage("Find All Projects Success!");
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
     * 按页查找BLOG
     * @return ResponseJSON
     */
    @GetMapping("/page/{index}")
    public ResponseJSON getCnProjectById(@PathVariable(value = "index") int index) throws Exception{
        List<DockerProject> list = projectServer.getCnProject();
        if (list != null && list.size() > 0) {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(3);
            responseJSON.setMessage("Find Page Projects Success!");
            List<DockerProject> page = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(i>=(index-1)*5&&i<index*5){
                    page.add(list.get(i));
                }
            }
            responseJSON.setBody(page);
            responseJSON.setData((int)Math.ceil(((double)list.size())/5));
            return responseJSON;
        } else {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(4);
            responseJSON.setMessage("Failed!");
            responseJSON.setBody(list);
            return responseJSON;
        }
    }
}
