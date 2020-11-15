package com.hualxx.crowd.mvc.handler;

import com.hualxx.crowd.entity.Admin;
import com.hualxx.crowd.service.api.AdminService;
import com.hualxx.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author hual
 * @create 2020-11-10 23:29
 */

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping("/send/array.json")
    public ResultEntity<Admin> testReceiveArray(@RequestBody List<Integer> array){

        Logger logger = LoggerFactory.getLogger(TestHandler.class);

        for (Integer number :
                array) {
            logger.info("number=" + number);
        }
        List<Admin> adminList = adminService.getAll();

        return ResultEntity.successWithData(adminList.get(0));
    }
    /*
      public String testReceiveArray(@RequestBody List<Integer> array){

        Logger logger = LoggerFactory.getLogger(TestHandler.class);

        for (Integer number :
                array) {
            logger.info("number=" + number);
        }

        return "success";
    }
     */

    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap){

        List<Admin> adminList = adminService.getAll();

        modelMap.addAttribute("adminList",adminList);
        System.out.println(10 / 0);

   /*     String str = null;
        System.out.println(str.length());*/
        return "test";

    }


}




