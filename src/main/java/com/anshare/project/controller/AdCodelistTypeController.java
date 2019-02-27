package com.anshare.project.controller;

import com.anshare.project.core.ResultCore.Result;
import com.anshare.project.core.ResultCore.ResultGenerator;
import com.anshare.project.core.Util.ConditionUtils;
import com.anshare.project.core.Util.ExportExcel;
import com.anshare.project.core.Util.StringUtils;
import com.anshare.project.model.*;
import com.anshare.project.service.AdCodelistTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* Created by Anshare on 2018/11/22.
*/
@RestController
@RequestMapping("/ad/codelist/type")
@Api(value = "AdCodelistType管理", description = "AdCodelistType管理")
public class AdCodelistTypeController {
    @Resource
    private AdCodelistTypeService adCodelistTypeService;
	
    @ApiOperation(value = "addAdCodelistType")
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    public Result add(@RequestBody AdCodelistType adCodelistType) {
        Condition condition = new Condition(AdCodelistType.class);
        Example.Criteria criteria  = condition.createCriteria();
        criteria.andEqualTo("codevalue", adCodelistType.getCodevalue());
        List<AdCodelistType> adCodelistTypes = adCodelistTypeService.findByCondition(condition);
        if(adCodelistTypes.size()>0){
            return ResultGenerator.genSuccessResult("此字典key已重复");
        }else{
            adCodelistTypeService.save(adCodelistType,StringUtils.isGenerator(adCodelistType.getId()));
        }
        return ResultGenerator.genSuccessResult("保存成功");
    }
	
    @ApiOperation(value = "deleteAdCodelistType")
    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        adCodelistTypeService.deleteById(id);
        return ResultGenerator.genSuccessResult("删除成功");
    }
	
    @ApiOperation(value = "updateAdCodelistType")
    @PostMapping(value = "/update",produces = "application/json;charset=UTF-8")
    public Result update(@RequestBody AdCodelistType adCodelistType) {
        adCodelistTypeService.update(adCodelistType);
        return ResultGenerator.genSuccessResult("更新成功");
    }

    @ApiOperation(value = "获取AdCodelistType对象所有字段")
    @PostMapping("/getkey")
    public Result getkey() {
		AdCodelistType adCodelistType = new AdCodelistType();
        return ResultGenerator.genSuccessResult(adCodelistType);
    }

    @ApiOperation(value = "detailAdCodelistType")
    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        AdCodelistType adCodelistType = adCodelistTypeService.findById(id);
        return ResultGenerator.genSuccessResult(adCodelistType);
    }
	
    @ApiOperation(value = "listAdCodelistType")
    @PostMapping("/list")
    public Result list(@RequestBody SearchConditionsModel searchConditionsModel) {
        PageHelper.startPage(searchConditionsModel.getPageIndex(), searchConditionsModel.getPageSize());
        Condition condition = new Condition(AdCodelistType.class);
        List<AdCodelistType> list = adCodelistTypeService.findByCondition(ConditionUtils.getConditions(condition,searchConditionsModel.getSearchCondition(),searchConditionsModel.getOrderCondition()));
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
        Condition condition = new Condition(AdCodelistType.class);
        List<AdCodelistType> list = adCodelistTypeService.findByCondition(ConditionUtils.getConditions(condition,conditionsModel.getSearchCondition(),conditionsModel.getOrderCondition()));
        PageInfo pageInfo = new PageInfo(list);
        ExportExcel.exportExcelPlus(conditionsModel.getExportCondition(),pageInfo.getList(),response);
    }
}
