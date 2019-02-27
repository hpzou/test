package com.anshare.project.controller;

import com.anshare.project.core.ResultCore.Result;
import com.anshare.project.core.ResultCore.ResultGenerator;
import com.anshare.project.core.Util.ConditionUtils;
import com.anshare.project.model.Log;
import com.anshare.project.model.SearchConditionsModel;
import com.anshare.project.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Anshare on 2018/12/04.
*/
@RestController
@RequestMapping("/log")
@Api(value = "Log管理", description = "Log管理")
public class LogController {
    @Resource
    private LogService logService;
	
    @ApiOperation(value = "addLog")
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    public Result add(@RequestBody Log log) {
        logService.save(log,true);
        return ResultGenerator.genSuccessResult("保存成功");
    }
	
    @ApiOperation(value = "deleteLog")
    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        logService.deleteById(id);
        return ResultGenerator.genSuccessResult("删除成功");
    }
	
    @ApiOperation(value = "updateLog")
    @PostMapping(value = "/update",produces = "application/json;charset=UTF-8")
    public Result update(@RequestBody Log log) {
        logService.update(log);
        return ResultGenerator.genSuccessResult("更新成功");
    }

    @ApiOperation(value = "获取Log对象所有字段")
    @PostMapping("/getkey")
    public Result getkey() {
		Log log = new Log();
        return ResultGenerator.genSuccessResult(log);
    }

    @ApiOperation(value = "detailLog")
    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        Log log = logService.findById(id);
        return ResultGenerator.genSuccessResult(log);
    }

    @ApiOperation(value = "listDocTwoResponsibility")
    @PostMapping("/list")
    public Result list(@RequestBody SearchConditionsModel searchConditionsModel) {
        PageHelper.startPage(searchConditionsModel.getPageIndex(), searchConditionsModel.getPageSize());
        Condition condition = new Condition(Log.class);
        List<Log> list = logService.findByCondition(ConditionUtils.getConditions(condition,searchConditionsModel.getSearchCondition(),searchConditionsModel.getOrderCondition()));
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
