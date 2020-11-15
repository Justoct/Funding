package com.hualxx.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.hualxx.crowd.entity.Admin;

import java.util.List;

/**
 * @author hual
 * @create 2020-11-10 3:10
 */
public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void remove(Integer adminId);
    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String username);
}
