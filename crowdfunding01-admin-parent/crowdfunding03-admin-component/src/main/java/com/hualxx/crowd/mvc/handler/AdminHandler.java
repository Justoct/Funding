package com.hualxx.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.hualxx.crowd.constant.CrowdConstant;
import com.hualxx.crowd.entity.Admin;
import com.hualxx.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author hual
 * @create 2020-11-12 16:42
 */
@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;

    @RequestMapping("/admin/update.html")
    public String update(Admin admin,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("keyword") String keyword) {
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(Integer adminId,
                             /* @RequestParam("pageNum") Integer pageNum,
                                @ReuqestParam("keyword") String keyword,*/
                             //访问同一页面时可以从隐藏域中取出来
                             ModelMap modelMap) {

        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin", admin);

        return "admin-edit";
    }

    @RequestMapping("/admin/remove.html/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword) {
        adminService.remove(adminId);

        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/save.html")
    public String saveAdmin(Admin admin) {

        //执行保存
        adminService.saveAdmin(admin);

        //重定向到分页页面，使用重定向是为了避免刷新浏览器而重复提交表单
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/get/page.html")
    public String getAdminPage(

            //注意：页面上有可能不提供关键词，要进行适配
            //在@RequesParam注解中设置defaultValue属性为空字符串表示浏览器不提供关键词时，keyword变量赋值为空字符串
            @RequestParam(value = "keyword", defaultValue = "") String keyword,

            //浏览器未提供pageNum时，默认前往第一页
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,

            //浏览器未提供pageSize时，默认每页显示5条记录
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,

            ModelMap modelMap

    ) {

        //查询得到分页数据
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        //将分页数据存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);

        return "admin-page";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession httpSession) {

        //强制Session失效
        httpSession.invalidate();

        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ) {
        //调用Service方法执行登录检查
        //这个方法如果能够返回admin对象说明登录成功，如果账号、密码不正确则会抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        //将登录成功返回的admin对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

//        return "admin-main";

        return "redirect:/admin/to/main/page.html";
    }


}
