package com.anshare.project.controller;

import com.anshare.project.core.ResultCore.Result;
import com.anshare.project.core.ResultCore.ResultGenerator;
import com.anshare.project.core.Util.ConditionUtils;
import com.anshare.project.core.Util.ExportExcel;
import com.anshare.project.core.Util.StringUtils;
import com.anshare.project.model.AdCodelist;
import com.anshare.project.model.ConditionsModel;
import com.anshare.project.model.SearchConditionsModel;
import com.anshare.project.service.AdCodelistService;
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
* Created by Anshare on 2018/11/01.
*/
@RestController
@RequestMapping("/ad/codelist")
@Api(value = "字典管理", description = "字典管理")
public class AdCodelistController {
    @Resource
    private AdCodelistService adCodelistService;

    @ApiOperation(value = "通过代码获取codelist")
    @PostMapping(value = "/getUpCodeList")
    public Result<List<AdCodelist>> getUpCodeList(String codeType) {
        Condition condition = new Condition(AdCodelist.class);
        condition.createCriteria()
                .andEqualTo("codetype", codeType);
        List<AdCodelist> adCodelist = adCodelistService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(adCodelist);
    }




    /**------------以下代码待用-------------------**/

    @ApiOperation(value = "addAdCodelist")
    @PostMapping(value = "/add",produces = "application/json;charset=UTF-8")
    public Result add(@RequestBody AdCodelist adCodelist) {
        Condition condition = new Condition(AdCodelist.class);
        Example.Criteria criteria  = condition.createCriteria();
        criteria.andEqualTo("codetype", adCodelist.getCodetype());
        criteria.andEqualTo("codevalue", adCodelist.getCodevalue());
        List<AdCodelist> adCodelists = adCodelistService.findByCondition(condition);
        if(adCodelists.size()>0){
            return ResultGenerator.genSuccessResult("此字典key已重复");
        }else{
            adCodelistService.save(adCodelist, StringUtils.isGenerator(adCodelist.getId()));
        }
        return ResultGenerator.genSuccessResult("保存成功");
    }

    @ApiOperation(value = "deleteAdCodelist")
    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        adCodelistService.deleteById(id);
        return ResultGenerator.genSuccessResult("删除成功");

    }

    @ApiOperation(value = "updateAdCodelist")
    @PostMapping(value = "/update",produces = "application/json;charset=UTF-8")
    public Result update(@RequestBody AdCodelist adCodelist) {
        adCodelistService.update(adCodelist);
        return ResultGenerator.genSuccessResult("更新成功");
    }

    @ApiOperation(value = "获取AdCodelist对象所有字段")
    @PostMapping("/getkey")
    public Result getkey() {
        AdCodelist adCodelist = new AdCodelist();
        return ResultGenerator.genSuccessResult(adCodelist);
    }

    @ApiOperation(value = "detailAdCodelist")
    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        AdCodelist adCodelist = adCodelistService.findById(id);
        return ResultGenerator.genSuccessResult(adCodelist);
    }

    @ApiOperation(value = "listAdCodelist")
    @PostMapping("/list")
    public Result list(@RequestBody SearchConditionsModel searchConditionsModel) {
        PageHelper.startPage(searchConditionsModel.getPageIndex(), searchConditionsModel.getPageSize());
        Condition condition = new Condition(AdCodelist.class);
        condition = ConditionUtils.getConditions(condition,searchConditionsModel.getSearchCondition(),searchConditionsModel.getOrderCondition());
        condition.setOrderByClause(" codeorder ASC,"+condition.getOrderByClause());
        List<AdCodelist> list = adCodelistService.findByCondition(condition);
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
        Condition condition = new Condition(AdCodelist.class);
        List<AdCodelist> list = adCodelistService.findByCondition(ConditionUtils.getConditions(condition,conditionsModel.getSearchCondition(),conditionsModel.getOrderCondition()));
        PageInfo pageInfo = new PageInfo(list);
        ExportExcel.exportExcelPlus(conditionsModel.getExportCondition(),pageInfo.getList(),response);
    }
}
