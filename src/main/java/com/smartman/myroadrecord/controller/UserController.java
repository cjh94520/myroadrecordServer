package com.smartman.myroadrecord.controller;

import com.smartman.myroadrecord.model.User;
import com.smartman.myroadrecord.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


/**
 * Created by jiahui.chen on 2016/1/24.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private HttpServletRequest request;

    /**
     * 查询用户是否存在
     *
     * @return boolean
     */
    @RequestMapping(value = "/check")
    @ResponseBody
    public boolean check() {
        String id = request.getParameter("id");
        return userService.checkUser(id);
    }

    /**
     * 登录
     *
     * @return String
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login() {
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        return userService.loginByNamePwd(user);
    }

    /**
     * 插入新用户
     *
     * @return Boolean
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    public Boolean register() {
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        User temp = userService.selectByUsername(id);
        if (temp == null) {
            User user = new User();
            user.setId(id);
            user.setPassword(password);
            return userService.registerUser(user);
        } else {
            return Boolean.FALSE;
        }

    }

    /**
     * 修改密码
     *
     * @return Boolean
     */
    @RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
    @ResponseBody
    public Boolean updatePwd() {
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        return userService.updatePassword(id, password);
    }

    /**
     * 上传用户头像
     * 文件名为id+格式
     *
     * @return Boolean
     */
    @RequestMapping(value = "/uploadImg")
    @ResponseBody
    public Boolean uploadImg(@RequestParam(value = "file", required = false) MultipartFile file) {
        System.out.println("-----------开始上传文件了--------------");
        String path = request.getSession().getServletContext().getRealPath("upload");
        String id = request.getParameter("id");
        String originalName = file.getOriginalFilename();
        int splits = originalName.indexOf(".");
        String fileName = id + originalName.substring(splits);
        File targetFile = new File(path, fileName);
        System.out.print(targetFile.getAbsolutePath());
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
            userService.insertImg(id, targetFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("-----------上传文件结束了--------------");
        return true;
    }

    /**
     * 下载用户头像
     *
     * @return Boolean
     */
    @RequestMapping(value = "/downloadImg")
    public ResponseEntity<byte[]> downloadImg() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String path = request.getSession().getServletContext().getRealPath("upload");
        String id = request.getParameter("id");
        File file = new File(path);
        File finalFile = null;
        if (file.isDirectory()) {
            for (File temp : file.listFiles()) {
                if (temp.getName().startsWith(id)) {
                    finalFile = temp;
                    break;
                }
            }
        }
        if (finalFile == null) {
            return null;
        }
        String type = finalFile.getName().toLowerCase();
        if (type.endsWith("png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if (type.endsWith("jpg") || type.endsWith("jpeg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else if (type.endsWith("gif")) {
            headers.setContentType(MediaType.IMAGE_GIF);
        } else {
            return null;
        }
        headers.setContentDispositionFormData("attachment", finalFile.getName());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(finalFile), headers, HttpStatus.CREATED);
    }


}
