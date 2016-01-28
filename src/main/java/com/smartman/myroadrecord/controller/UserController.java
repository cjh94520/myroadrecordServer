package com.smartman.myroadrecord.controller;

import com.smartman.myroadrecord.model.User;
import com.smartman.myroadrecord.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    @RequestMapping(value = "/check", method = RequestMethod.GET)
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
     *
     * @return Boolean
     */
    @RequestMapping(value = "/uploadImg")
    @ResponseBody
    public Boolean uploadImg() {
        logger.debug("--------------------uploadImg start()-------------------");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
        /** 构建文件保存的目录* */
        String logoPathDir = "/upload/"
                + dateformat.format(new Date());
        /** 得到文件保存目录的真实路径* */
        String logoRealPathDir = request.getSession().getServletContext()
                .getRealPath(logoPathDir);
        /** 根据真实路径创建目录* */
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists())
            logoSaveFile.mkdirs();
        /** 页面控件的文件流* */
        MultipartFile multipartFile = multipartRequest.getFile("file");
        /** 获取文件的后缀* */
        String suffix = multipartFile.getOriginalFilename().substring(
                multipartFile.getOriginalFilename().lastIndexOf("."));
        /** 使用UUID生成文件名称* */
        String logImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
        /** 拼成完整的文件保存路径加文件* */
        String fileName = logoRealPathDir + File.separator + logImageName;
        File file = new File(fileName);
        try {
            multipartFile.transferTo(file);
            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** 打印出上传到服务器的文件的绝对路径* */
        logger.debug("****************"+fileName+"**************");
        return false;
    }

}
