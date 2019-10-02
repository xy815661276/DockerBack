package com.hust.docker.control;

import com.hust.docker.Service.BlogServer;
import com.hust.docker.entity.DockerBlog;
import com.hust.docker.entity.ResponseJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog")
public class BlogControl {
    @Autowired
    private BlogServer blogServer;
    /**
     * 查找所有的BLOG
     * @return ResponseJSON
     */
    @GetMapping("/getAll")
    public ResponseJSON getBlog() throws Exception{
        List<DockerBlog> list = blogServer.getBlog();
        if (list != null && list.size() > 0) {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(1);
            responseJSON.setMessage("Find All Blog Success!");
            responseJSON.setBody(list);
            return responseJSON;
        } else {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(2);
            responseJSON.setMessage("Failed!");
            responseJSON.setBody(list);
            return responseJSON;
        }
    }/**
     * 按页查找BLOG
     * @return ResponseJSON
     */
    @GetMapping("/page/{index}")
    public ResponseJSON getBlogById(@PathVariable(value = "index") int index) throws Exception{
        List<DockerBlog> list = blogServer.getBlog();
        if (list != null && list.size() > 0) {
            ResponseJSON responseJSON=new ResponseJSON();
            responseJSON.setCode(3);
            responseJSON.setMessage("Find Page Blog Success!");
            List<DockerBlog> page = new ArrayList<>();
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
