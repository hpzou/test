package com.anshare.project.controller;

import com.anshare.project.core.ResultCore.Result;
import com.anshare.project.core.ResultCore.ResultGenerator;
import com.anshare.project.core.Util.ConditionUtils;
import com.anshare.project.core.Util.ExportExcel;
import com.anshare.project.model.ConditionsModel;
import com.anshare.project.model.Role;
import com.anshare.project.model.SearchConditionsModel;
import com.anshare.project.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Anshare on 2018/09/20.
 */
@RestController
@RequestMapping("/role")
@Api(value = "Role管理", description = "Role管理")

public class RoleController {
    @Resource
    private RoleService roleService;

    @ApiOperation(value = "addRole")
    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public Result add(@RequestBody Role role) {
        roleService.save(role,true);
        return ResultGenerator.genSuccessResult("保存成功");
    }

    @ApiOperation(value = "deleteRole")
    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        roleService.deleteById(id);
        return ResultGenerator.genSuccessResult("删除成功");
    }

    @ApiOperation(value = "updateRole")
    @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public Result update(@RequestBody Role role) {
        roleService.update(role);
        return ResultGenerator.genSuccessResult("更新成功");
    }


    @ApiOperation(value = "detailRole")
    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        Role role = roleService.findById(id);
        return ResultGenerator.genSuccessResult(role);
    }

    @ApiOperation(value = "listRole")
    @PostMapping("/list")
    public Result list(@RequestBody SearchConditionsModel searchConditionsModel) {
        PageHelper.startPage(searchConditionsModel.getPageIndex(), searchConditionsModel.getPageSize());
        Condition condition = new Condition(Role.class);
        List<Role> list = roleService.findByCondition(ConditionUtils.getConditions(condition,searchConditionsModel.getSearchCondition(),searchConditionsModel.getOrderCondition()));
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * @Description: 导出
     * @param
     * @return
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate:     2018/11/18 19:14
     * @UpdateRemark:   修改内容
     * @date 2018/11/18 19:14
     */
    @ApiOperation(value = "dataExport")
    @PostMapping("/dataExport")
    public void dataExport(@RequestBody @Validated ConditionsModel conditionsModel, HttpServletResponse response) {
        PageHelper.startPage(conditionsModel.getPageIndex(), conditionsModel.getPageSize());
        Condition condition = new Condition(Role.class);
        List<Role> list = roleService.findByCondition(ConditionUtils.getConditions(condition,conditionsModel.getSearchCondition(),conditionsModel.getOrderCondition()));
        PageInfo pageInfo = new PageInfo(list);
        ExportExcel.exportExcelPlus(conditionsModel.getExportCondition(),pageInfo.getList(),response);
    }
}
