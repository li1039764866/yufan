package org.yufan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.yufan.result.PicUploadResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/pic")
public class UploadController {




    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public PicUploadResult   upload(@RequestParam MultipartFile uploadFile) throws IOException {



            //上传文件的路径
            /*String path = "F:\\code\\shop\\image";*/
             String path = "H:\\IntelliJ IDEA 2018.1.2\\Projects\\" +
                     "yufan\\admin\\src\\main\\webapp\\itemimage";
            //获取后缀
            String filename = uploadFile.getOriginalFilename();
            String end = filename.substring(filename.lastIndexOf("."));
            //生成新的文件名
            String uuid_name = UUID.randomUUID().toString().replace("-", "") + end;

            File file = new File(path + File.separator + uuid_name);

            //保存图片到磁盘
            uploadFile.transferTo(file);


        BufferedImage image=ImageIO.read(file);

        if(image!=null){

            //上传成功!  nginx服务器代理静态资源image.yufan.com，配置host
            return PicUploadResult.buildSuccess("http://image.yufan.com/itemimage/"+uuid_name,image.getWidth()+"",image.getHeight()+"");
        }else{
            return PicUploadResult.buildFail("图片上传失败");
        }





    }

}
