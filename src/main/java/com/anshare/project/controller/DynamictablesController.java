package com.anshare.project.controller;

import com.anshare.project.core.MyLog;
import com.anshare.project.core.ProjectConstant;
import com.anshare.project.core.ResultCore.Result;
import com.anshare.project.core.ResultCore.ResultGenerator;
import com.anshare.project.core.Util.ConditionUtils;
import com.anshare.project.core.Util.DateUtil;
import com.anshare.project.core.Util.StringUtils;
import com.anshare.project.model.Dynamictables;
import com.anshare.project.model.Form;
import com.anshare.project.model.SearchConditionsModel;
import com.anshare.project.service.DynamictablesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Anshare on 2018/11/06.
*/
@RestController
@RequestMapping("/dynamictables")
@Api(value = "Dynamictables管理", description = "Dynamictables管理")

public class DynamictablesController {
    @Resource
    private DynamictablesService dynamictablesService;

    @ApiOperation(value = "addDynamictables")
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    @MyLog(value = "新增记录")
    public Result add(@RequestBody Dynamictables dynamictables) {
        dynamictables.setOptime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
        dynamictablesService.save(dynamictables,StringUtils.isGenerator(dynamictables.getId()));
        return ResultGenerator.genSuccessResult("保存成功");
    }
	
    @ApiOperation(value = "deleteDynamictables")
    @PostMapping("/delete")
    @MyLog(value = "删除记录")
    public Result delete(@RequestParam String id) {
        dynamictablesService.deleteById(id);
        return ResultGenerator.genSuccessResult("删除成功");

    }
	
    @ApiOperation(value = "updateDynamictables")
    @PostMapping(value = "/update",produces = "application/json;charset=UTF-8")
    @MyLog(value = "更新记录")
    public Result update(@RequestBody Dynamictables dynamictables) {
        dynamictables.setOptime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
        dynamictablesService.update(dynamictables);
        return ResultGenerator.genSuccessResult("更新成功");
    }


    @ApiOperation(value = "获取Dynamictables对象所有字段")
    @PostMapping("/getkey")
    public Result getkey() {
		Dynamictables dynamictables = new Dynamictables();
        return ResultGenerator.genSuccessResult(dynamictables);
    }



    @ApiOperation(value = "detailDynamictables")
    @PostMapping("/detail")
    public Result detail(@RequestParam String tablename) {
        Condition condition = new Condition(Dynamictables.class);
        condition.createCriteria()
                .andEqualTo("tableName", tablename);
        List<Dynamictables> dynamictables = dynamictablesService.findByCondition(condition);
        if(dynamictables.size()==0){
            return ResultGenerator.genSuccessResult("首次制作");
        } else{
            return ResultGenerator.genSuccessResult(dynamictables.get(0));
        }
    }

    @ApiOperation(value = "listDynamictables")
    @PostMapping("/list")
    public Result list(@RequestBody SearchConditionsModel searchConditionsModel) {
        PageHelper.startPage(searchConditionsModel.getPageIndex(), searchConditionsModel.getPageSize());
        Condition condition = new Condition(Dynamictables.class);
        List<Dynamictables> list = dynamictablesService.findByCondition(ConditionUtils.getConditions(condition,searchConditionsModel.getSearchCondition(),searchConditionsModel.getOrderCondition()));
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
