package com.anshare.project.controller;

import com.anshare.project.core.MyLog;
import com.anshare.project.core.ProjectConstant;
import com.anshare.project.core.ResultCore.Result;
import com.anshare.project.core.ResultCore.ResultGenerator;
import com.anshare.project.core.Util.*;
import com.anshare.project.model.*;
import com.anshare.project.service.RoleService;
import com.anshare.project.service.UsersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* Created by Anshare on 2019/01/22.
*/
@RestController
@RequestMapping("/users")
@Api(value = "Users管理", description = "Users管理")
public class UsersController {
    @Resource
    private UsersService usersService;

    @Resource
    private RoleService roleService;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "addUsers")
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    public Result add(@RequestBody Users users) {
        usersService.save(users,StringUtils.isGenerator(users.getId()));
        return ResultGenerator.genSuccessResult("添加成功");
    }

    @ApiOperation(value = "clear")
    @PostMapping("/clear")
    public Result clear() {
        usersService.clearUsers();
        usersService.clearPatrolData();
        usersService.updatePassword();
        return ResultGenerator.genSuccessResult("清除成功");
    }
	
    @ApiOperation(value = "deleteUsers")
    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        usersService.deleteById(id);
        return ResultGenerator.genSuccessResult("删除成功");
    }
	
    @ApiOperation(value = "updateUsers")
    @PostMapping(value = "/update",produces = "application/json;charset=UTF-8")
    public Result update(@RequestBody Users users) {
        usersService.update(users);
        return ResultGenerator.genSuccessResult("更新成功");
    }

    @ApiOperation(value = "获取Users对象所有字段")
    @PostMapping("/getkey")
    public Result getkey() {
		Users users = new Users();
        return ResultGenerator.genSuccessResult(users);
    }

    @ApiOperation(value = "detailUsers")
    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        Users users = usersService.findById(id);
        return ResultGenerator.genSuccessResult(users);
    }
	
    @ApiOperation(value = "listUsers")
    @PostMapping("/list")
    public Result list(@RequestBody SearchConditionsModel searchConditionsModel){
        SqlModel sqlModel =  ConditionUtils.getConditionsPlus(searchConditionsModel);
        List<Users> list = usersService.listPlus(sqlModel);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @ApiOperation(value = "dataExport")
    @PostMapping("/dataExport")
    @MyLog(value = "导出记录")
    public void dataExport(@RequestBody @Validated ConditionsModel conditionsModel, HttpServletResponse response) {
        PageHelper.startPage(conditionsModel.getPageIndex(), conditionsModel.getPageSize());
        SearchConditionsModel searchConditionsModel = CustomUtil.exchange(conditionsModel);
        SqlModel sqlModel =  ConditionUtils.getConditionsPlus(searchConditionsModel);
        sqlModel.setOrderSql(" a.Number asc, ");
        List<Users> list = usersService.listPlus(sqlModel);
        PageInfo pageInfo = new PageInfo(list);
        ExportExcel.exportExcelPlus(conditionsModel.getExportCondition(),pageInfo.getList(),response);
    }
}