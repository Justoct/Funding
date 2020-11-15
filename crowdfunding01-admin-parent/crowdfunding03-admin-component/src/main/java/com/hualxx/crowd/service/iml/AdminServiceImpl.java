package com.hualxx.crowd.service.iml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hualxx.crowd.constant.CrowdConstant;
import com.hualxx.crowd.entity.Admin;
import com.hualxx.crowd.entity.AdminExample;
import com.hualxx.crowd.exception.LoginAcctAlreadyInUseException;
import com.hualxx.crowd.mapper.AdminMapper;
import com.hualxx.crowd.service.api.AdminService;
import com.hualxx.crowd.util.Crowdutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author hual
 * @create 2020-11-10 3:15
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public void saveAdmin(Admin admin) {

        //生成当前的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String createTime = sdf.format(new Date());

        admin.setCreateTime(createTime);

        //针对登录密码进行加密
        String source = admin.getUserPswd();
        String encoded = Crowdutil.md5(source);

        admin.setUserPswd(encoded);

        //执行保存，如果账户被占用会被抛出异常
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();

            //检测当前捕获的异常对象，如果是DuplicateKeyException类型说明是账号重复导致的
            if (e instanceof DuplicateKeyException) {

                //抛出自定义的LoginAcctAlreadyUseException异常
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

        //1.根据登录账号查询Admin对象
        //1)创建AdminExample对象
        AdminExample adminExample = new AdminExample();

        //2)创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();

        //3)在Criteria对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);

        //4)调用AdminMapper的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);

        //2.判断Admin对象是否为null
        if (list == null || list.size() == 0) {
            throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);

        }

        Admin admin = list.get(0);

        //3.如果Admin对象为null则抛出异常
        if (admin == null) {
            throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //4.若果Admin对象不为null则将数据库密码从Admin对象中取出
        String userPswdDB = admin.getUserPswd();

        //5.将表单提交的明文密码进行加密
        String userPswdForm = Crowdutil.md5(userPswd);

        //6.对密码进行比较
        if (!Objects.equals(userPswdDB, userPswdForm)) {

            //7.如果比较结果不一致则抛出异常
            throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //8.如果一致则返回Admin对象
        return admin;


    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        //1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        //2.查询Admin数据
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);

        //3.封装到PageInfo对象中
        return new PageInfo<>(adminList);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {

        String source = admin.getUserPswd();
        String encoded = Crowdutil.md5(source);
        admin.setUserPswd(encoded);

        //"Selective"表示有选择的更新，对于null值的字段不更新
        try {

            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();

            logger.info("异常全类名=" + e.getClass().getName());

            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {

    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        return null;
    }
}
