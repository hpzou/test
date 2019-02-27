package com.anshare.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.anshare.project.configurer.JwtConfig;
import com.anshare.project.core.MyLog;
import com.anshare.project.core.ProjectConstant;
import com.anshare.project.core.ResultCore.Result;
import com.anshare.project.core.ResultCore.ResultGenerator;
import com.anshare.project.core.Util.FileUtil;
import com.anshare.project.core.Util.JwtUtils;
import com.anshare.project.core.Util.RedisUtil;
import com.anshare.project.dto.UserDto;
import com.anshare.project.model.Log;
import com.anshare.project.model.Role;
import com.anshare.project.model.Users;
import com.anshare.project.service.RoleService;
import com.anshare.project.service.UsersService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@Api(value = "登录管理", description = "登录管理")
@Slf4j
@RequestMapping("/user")
public class LoginController {
    @Resource
    private UsersService usersService;

    @Resource
    private RoleService roleService;


    @Autowired
    RedisUtil redis;

    @ApiOperation(value = "登录接口")
    @PostMapping(value = "/login")
    @MyLog(value = "登陆系统")
    /**
     * @Description: 登录接口
     * @param [username, password, request, response]
     * @return com.anshare.project.core.ResultCore.Result
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate: 2018/11/14 10:00
     * @UpdateRemark: 修改内容
     * @date 2018/11/14 10:00
     */
    public Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        Condition condition = new Condition(Users.class);
        condition.createCriteria()
                .andEqualTo("username", username)
                .andEqualTo("password", password);
        List<Users> userVo = usersService.findByCondition(condition);
        if (userVo != null && userVo.size() > 0) {
            Users user = userVo.get(0);
            Role role = roleService.findById(user.getRoleid());
            if (role != null) {
                String subject = user.getUsername() + ","
                        + user.getRealname() + ","
                        + user.getRoleid() + ","
                        + user.getId();
                long time = System.currentTimeMillis();
                String token = Jwts.builder()
                        .setSubject(subject)
                        .setExpiration(new Date(time + JwtConfig.Time)) // 设置过期时间 1 * 24 * 60 * 60秒情况修改)
                        .signWith(SignatureAlgorithm.HS512, JwtConfig.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                        .compact();
                UserDto u = new UserDto();
                u.setHomepage(role.getHomepage());//该角色默认首页页面信息
                u.setToken(token);
                // 登录成功后，返回token到header里面
                redis.setStr(user.getUsername(), token);
                redis.setStr("currentUser", user.getUsername());
                log.info("用户" + user.getUsername() + "成功登陆");
                return ResultGenerator.genSuccessResult(u);
            } else {
                return ResultGenerator.genFailCodeResult(ProjectConstant.USER_OR_PASSWORD_INVALID, "该用户未绑定角色");
            }
        } else {
            return ResultGenerator.genFailCodeResult(ProjectConstant.USER_NO_ROLE, "用户名或密码错误");

        }
    }

    @ApiOperation(value = "获取用户身份信息以及对应的菜单信息")
    @PostMapping(value = "/userinfo")
    /**
     * @Description: 获取用户身份信息以及对应的菜单信息
     * @param [request, response]
     * @return com.anshare.project.core.ResultCore.Result
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate: 2018/11/14 10:01
     * @UpdateRemark: 修改内容
     * @date 2018/11/14 10:01
     */
    public Result userInfo(HttpServletRequest request, HttpServletResponse response) {
        String auth = request.getHeader("Auth");
        try {
            if (auth != null && auth != "") {
                String[] subject = JwtUtils.GetDetails();
                String userId = subject[3];
                Users u = usersService.findById(userId);
                JSONObject json = new JSONObject();
                json.put("RealName", u.getRealname());
                json.put("UserName", u.getUsername());
                json.put("RoleAuthName", roleService.findById(u.getRoleid()).getRoleauthname());
                return ResultGenerator.genSuccessResult(json);
            } else {
                return ResultGenerator.genFailResult("获取用户信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailCodeResult(ProjectConstant.TOKEN_INVALID, "Token失效");
        }
    }

    @ApiOperation(value = "登出")
    @PostMapping(value = "/logout")
    @MyLog(value = "退出系统记录")
    /**
     * @Description: 登出
     * @param [request, response]
     * @return com.anshare.project.core.ResultCore.Result
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate: 2018/11/14 10:02
     * @UpdateRemark: 修改内容
     * @date 2018/11/14 10:02
     */
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        String subject = JwtUtils.parseJWT(request);
        String userName = subject.split(",")[0];
        redis.delStr(userName);
        log.info("用户" + userName + "成功退出");
        return ResultGenerator.genAuthTokenErrResult("");
    }

    @ApiOperation(value = "修改密码")
    @PostMapping(value = "/changepassword")
    @MyLog(value = "修改密码记录")
    /**
     * @Description: 修改密码
     * @param [request, password]
     * @return com.anshare.project.core.ResultCore.Result
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate: 2018/11/14 10:03
     * @UpdateRemark: 修改内容
     * @date 2018/11/14 10:03
     */
    public Result changepassword(HttpServletRequest request, String password) {
        String[] subject = JwtUtils.GetDetails();
        String UserName = subject[0];
        Condition condition = new Condition(Users.class);
        condition.createCriteria()
                .andEqualTo("username", UserName);
        List<Users> userVo = usersService.findByCondition(condition);
        if (userVo != null && userVo.size() > 0) {
            Users user = userVo.get(0);
            user.setPassword(password);
            usersService.update(user);
            redis.delStr(UserName);
        }
        return ResultGenerator.genSuccessResult("修改成功");
    }

    @ApiOperation(value = "文件复制")
    @PostMapping(value = "/FileCopy", produces = "application/json;charset=UTF-8")
    public Result FileCopy() {
        String desPathStr = "C:\\Users\\Zhp\\Desktop"; //目标文件地址
        String srcPathStr = "C:\\1.db"; //源文件地址

        //1.获取源文件的名称
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\") + 1); //目标文件地址
        System.out.println(newFileName);
        desPathStr = desPathStr + File.separator + newFileName; //源文件地址
        System.out.println(desPathStr);

        try {
            //2.创建输入输出流对象
            FileInputStream fis = new FileInputStream(srcPathStr);
            FileOutputStream fos = new FileOutputStream(desPathStr);

            //创建搬运工具
            byte datas[] = new byte[1024 * 8];
            //创建长度
            int len = 0;
            //循环读取数据
            while ((len = fis.read(datas)) != -1) {
                fos.write(datas, 0, len);
            }
            //3.释放资源
            fis.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult("复制成功");
    }

    @ApiOperation(value = "文件夹复制")
    @PostMapping(value = "/FilesCopy", produces = "application/json;charset=UTF-8")
    public Result FilesCopy() {
        try {
            FileUtil.FilesCopy("C:\\test","D:\\COPY");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult("复制成功");
    }
}

