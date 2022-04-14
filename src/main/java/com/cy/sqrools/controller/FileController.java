package com.cy.sqrools.controller;

import com.cy.sqrools.drools.Drools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/file")
@RestController
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Value("${file.upload.dir}")
    private String realPath;

    /**
     * 第二种文件上传
     * 注意：这种方式适用于任何一种部署方式 推荐使用这种方式
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload")
    // 定义：接收文件对象 MultipartFile file变量名要与form表单中input type="file" 标签name属性名一致
    public List<String> upload(MultipartFile file) throws IOException {
        if(file.isEmpty()){
//            return "上传失败，请选择文件";
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        log.debug("文件名: {}", file.getOriginalFilename());
        log.debug("文件大小: {}", file.getSize());
        log.debug("文件类型: {}", file.getContentType());

        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS-").format(new Date()) + UUID.randomUUID() + ext;

        file.transferTo(new File(realPath, newFileName));
        System.out.println(realPath+"\\\\"+newFileName);
        Drools drools=new Drools();
        List<String> list1 = drools.read(realPath + "\\\\" + newFileName);
        System.out.println(list1);
        int n=0;
        for(String s:list1)
        {
            System.out.println(++n+": "+s);
        }
//        return "redirect:/";
        return  list1;
    }
//    @RequestMapping("/upload")
//    public void datasource(MultipartFile file)throws IOException{
//        String filepath= UploadUtils.upload(file);
//        System.out.println(filepath);
//    }
    /**
     * 用来测试文件上传 - 这种方式：不能用于jar包部署
     * 注意：这种方式存在局限性, 不推荐再使用这种方式进行文件上传了
     *
     * @return
     */
    /*
    @RequestMapping("/uploadFile")
    // 定义：接收文件对象 MultipartFile file变量名要与form表单中input type="file" 标签name属性名一致
    public String uploadFileTest(MultipartFile file, HttpServletRequest request) throws IOException {

        // 文件名
        String originalFilename = file.getOriginalFilename();
        log.debug("文件名: {}", file.getOriginalFilename());
        log.debug("文件大小: {}", file.getSize());
        log.debug("文件类型: {}", file.getContentType());

        // 处理文件上传
        // 1.根据相对路径 "uploads" 获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/uploads");
        log.debug("获取的绝对路径: {}", realPath);

        // 2.上传文件  参数1：将文件写入到那个目录
        // 修改文件名

        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS-").format(new Date()) + UUID.randomUUID() + ext;
        file.transferTo(new File(realPath, newFileName));

        return "redirect:/";
    }
    */

}
