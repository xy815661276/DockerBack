package com.hust.docker.control;

import com.hust.docker.Service.ProjectServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class FileControl {
    @Autowired
    private ProjectServer projectServer;

    @ResponseBody
    @RequestMapping("/getArticle/{filename}")
    public void downloadFile(@PathVariable(value = "filename")String filename, HttpServletResponse response) throws Exception {
        getFile(filename, response);
    }
    @ResponseBody
    @RequestMapping("/zh-cn/getArticle/{filename}")
    public void downloadCnFile(@PathVariable(value = "filename")String filename, HttpServletResponse response) throws Exception {
        filename = filename+"_CH";
        getFile(filename, response);
    }
    private void getFile(String filename, HttpServletResponse response) throws Exception {
        InputStream f= new FileInputStream("/root/resources/markdown/"+filename+".md");
        projectServer.addViews(filename);
        response.reset();
        response.setContentType("application/x-msdownload;charset=utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".md").getBytes("gbk"), "iso-8859-1"));//下载文件的名称
            response.setHeader("Access-Control-Allow-Origin", "*");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream sout = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(f);
            bos = new BufferedOutputStream(sout);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null){
                bis.close();
            }
            if (bos != null){
                bos.close();
            }
        }
    }
}
