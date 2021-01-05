package com.nowcoder.community.controller;


import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//@RestController
@RequestMapping("/alpha")
//方法前面的uri名字
@Controller
public class AlphaController {

    @Autowired
    private AlphaService alphaService;


    //controller的前提是需要标注访问路径
    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }


    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "nowCoder";
    }


    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {

        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());

        //请求行所有数据
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();//key
            String value = request.getHeader(name);//value
            System.out.println(name + ":" + value);
        }
        //传入参数code
        System.out.println(request.getParameter("code"));

        //返回响应数据，网页版本
        response.setContentType("text/html;charset=utf-8");
        //输出流
        try (
                PrintWriter writer = response.getWriter();//java7的默认会关闭，前提有close方法
        ) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get请求   默认
    // /students?current=1&limit=20   加参数  1-10   3-20
    //指定请求   path路径，方法get方法
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        //  get请求获取参数怎么获取，在方法形参中传入表示我要取这个参数current、limit
        //  DispatcherServlet 检测到current和limit之后，会把
        //@RequestParam 请求参数，name = "current"意思是request当中名为current的值给current，
        //required=false，意思是也可以不传入这个参数，defaultValue  不传的时候默认值

        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123  get请求表示从服务器获取数据，post请求表示从浏览器向服务器提交数据
    //直接把参数编排到路径当中，当参数称为路径中的一部分就不是上面那样获取
    //path:"/student/{id}"，id表示变量
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        //注解会从路径中得到变量赋给id，@PathVariable表示路径变量，括号里面写上变量的名字
        //注解会从路径中的得到变量赋给形参id
        System.out.println(id);
        return "a student";
    }


    //Post请求，通常用post请求，从浏览器向服务器提交数据，后台应该怎么获取呢？
    //浏览器向服务器提交数据，浏览器首先应该具有一个表单的网页
    //get请求也能传参，但是参数在明面上，且输入框有限，
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        //只要直接声明参数，声明的参数与form表单的参数一致就会自动传入进来
        return "success";
    }

    //响应HTML数据
    //DispatcherServlet会调用某个controller方法，controller方法会
    //返回ModelAndView数据，ModelAndView数据传给模板引擎，
    // 由模板引擎进行渲染，生成动态的HTML
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();//需要new
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");//指的是templates下demo包里view.html
        return mav;
    }

    //与前者类似，
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "demo/view";
        //return的还是路径，String表示view的返回路径，
        // model数据怎么传呢，因为返回的view，不可能带model
        //model放进方法体的形参列表中，Model不是我们创建的，
        // 是DispatchServlet在调用方法时，会自动实例化Model对象，这是一个Bean
        //DispatchServlet持有该对象的引用，可以往这里面写入数据，它也能得到
        //与前者不同，前者是把Model和View都装进ModelAndView，后者是把model装进参数里，视图view直接返回
        //返回的值给了DispatcherServlet，而model的引用也被DispatcherServlet持有，所以这两个数都能得到
    }


    //响应JSON数据(异步请求)
    //java对象-->JSON字符串 -->（浏览器利用JSON数据封装成） JS对象
    // 任何语言有字符串类型，任何语言都能解析，方便跨语言  异步请求（成功、失败响应结果）
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 7000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 9000.00);
        list.add(emp);
        return list;
    }

}
