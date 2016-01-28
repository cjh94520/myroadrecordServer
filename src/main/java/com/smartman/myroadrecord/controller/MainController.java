package com.smartman.myroadrecord.controller;

import com.smartman.myroadrecord.model.User;
import com.smartman.myroadrecord.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by jiahui.chen on 2016/1/21.
 */
@Controller
@RequestMapping("/main")
public class MainController {
    @Resource
    private UserService userService;
    @Resource
    private HttpServletRequest request;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
//        try {
//            request.setCharacterEncoding("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=utf-8");
//
//        String mcc = request.getParameter("mcc");
//        String userName = request.getParameter("userID");
//        String password = request.getParameter("userPwd");
//
//        try {
//            User user = userService.selectByUsername("18688553035");
//            PrintWriter out = response.getWriter();
//            out.println("Hello World");
//            if(user==null)
//            {
//                out.println("user is null");
//            }
//            else
//            {
//                out.println("user is not null");
//            }
//            out.println("年龄"+String.valueOf(user.getAge()));
//            out.println(user.getName());
//            out.println(user.getPassword());
//            out.println(user.getImageurl());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "second";
    }
}
