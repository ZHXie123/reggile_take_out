package com.xzh.reggie.controller;

import com.xzh.reggie.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        log.info(file.toString());

        String originalFilename = file.getOriginalFilename();
//        后面是有带"."的
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName =  UUID.randomUUID().toString()+suffix;

        File dir = new File(basePath);

        if(!dir.exists()){
            //创建目录
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Result.success(fileName);
    }

    /**
     * 下载图片
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));

            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
