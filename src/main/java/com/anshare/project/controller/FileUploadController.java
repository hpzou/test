package com.anshare.project.controller;

import com.anshare.project.core.MyLog;
import com.anshare.project.core.ProjectConstant;
import com.anshare.project.core.ResultCore.Result;
import com.anshare.project.core.ResultCore.ResultGenerator;
import com.anshare.project.core.Util.ConditionUtils;
import com.anshare.project.core.Util.DateUtil;
import com.anshare.project.core.Util.FileUtil;
import com.anshare.project.core.Util.WordToPdfUtil;
import com.anshare.project.model.*;
import com.anshare.project.service.AffixService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Anshare on 2018/09/27.
 */
/**
* @ClassName FileUploadController
* @Description 文件处理器
* @Author wukunfan
* @Date 2018/11/12 18:07
* @UpdateUser:
* @UpdateDate:     2018/11/12 18:07
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@RestController
@RequestMapping("/file")
@Api(value = "上传文件", description = "上传文件")
@Slf4j
public class FileUploadController {
    @Resource
    private AffixService affixService;

    public static String fileUploadPath;
    @Value("${file.uploadPath}")
    public void setFileUploadPath(String fileUploadPath) {
        FileUploadController.fileUploadPath = fileUploadPath;
    }

    public static String uploadPathFlag;
    @Value("${file.uploadPathFlag}")
    public void setUploadPathFlag(String uploadPathFlag) {
        FileUploadController.uploadPathFlag = uploadPathFlag;
    }


    /**
     * 提取上传方法为公共方法
     * @param uploadDir 上传文件目录
     * @param file 上传对象
     * @throws Exception
     */
    private void executeUpload(String masterId,String uploadDir,MultipartFile file) throws Exception
    {
        //文件后缀名
        String suffix = "." + FileUtil.getExtensionName(file.getOriginalFilename());
        //上传文件名
        String fileName = file.getOriginalFilename();
        //保存文件名
        String saveName = DateUtil.getDate() + UUID.randomUUID() + suffix;
        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + saveName);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);

        //将word文档保存一份为pdf
        if(".doc".equals(suffix)||".docx".equals(suffix)){
            FileDomainVO vo = new FileDomainVO();
            vo.setDetails(ProjectConstant.DocToPDF);    //进行word转pdf
            vo.setInputfile(uploadDir+saveName);     //需要转换的word
            vo.setOutputfile(uploadDir);         //保存路径
            String pdfSaveName = saveName.substring(0,saveName.lastIndexOf("."));
            vo.setFileNameAfter(pdfSaveName+".pdf");           //转换后的文件名,自己取
            WordToPdfUtil.wordToPdf(vo);
        }

        //保存附件对象
        Affix temp = new Affix();
        temp.setResourceid(masterId);
        temp.setFilename(fileName);
        temp.setSavename(saveName);
        temp.setFileextension(suffix);
        affixService.save(temp, true);
    }

    /**
     * 上传文件方法
     * @param file 前台上传的文件对象
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @MyLog(value = "新增记录")
    public Result upload(HttpServletRequest request,MultipartFile file)
    {
        try {
            //资源目标
            String masterId = request.getParameter("resourceid");
            //上传目录地址
            String uploadDir ="";
            if("true".equals(uploadPathFlag)){
                uploadDir  = fileUploadPath;
            }else{
                uploadDir = request.getSession().getServletContext().getRealPath("/") +"upload/";
            }
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            //调用上传方法
            executeUpload(masterId,uploadDir,file);
        }catch (Exception e)
        {
            //打印错误堆栈信息
            e.printStackTrace();
            return ResultGenerator.genFailResult("上传失败");
        }
        return ResultGenerator.genSuccessResult("上传成功");
    }

    /**-----------------以下代码待用----------------------**/
    @ApiOperation(value = "下载文件")
    @MyLog(value = "下载记录")
    @PostMapping("/download")
    public void Download(@RequestBody Affix affix,HttpServletRequest request, HttpServletResponse response) throws Exception{
        if (!"".equals(affix.getId())) {
            //设置文件路径
            String filePath ="";
            if("true".equals(uploadPathFlag)){
                filePath  = fileUploadPath ;
            }else{
                filePath = request.getSession().getServletContext().getRealPath("/") +"upload/";
            }
            Affix temp = affixService.findById(affix.getId());
            File file = new File(filePath + temp.getSavename());
            response.setCharacterEncoding("UTF-8");
            //设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition","attachment;fileName="+ java.net.URLEncoder.encode(temp.getFilename(), "UTF-8"));
            InputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                response.getOutputStream().write(buffer, 0, len);
            }
            fis.close();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

    /**
     * 上传多个文件
     * @param request 请求对象
     * @param file 上传文件集合
     * @return
     */
    @RequestMapping(value = "/uploads",method = RequestMethod.POST)
    public Result uploads(HttpServletRequest request,MultipartFile[] file)
    {
        try {
            //资源目标
            String masterId = request.getParameter("resourceid");
            //上传目录地址
            String uploadDir ="";
            if("true".equals(uploadPathFlag)){
                uploadDir  = fileUploadPath;
            }else{
                uploadDir = request.getSession().getServletContext().getRealPath("/") +"upload/";
            }
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            //遍历文件数组执行上传
            for (int i =0;i<file.length;i++) {
                if(file[i] != null) {
                    //调用上传方法
                    executeUpload(masterId,uploadDir, file[i]);
                }
            }
        }catch (Exception e)
        {
            //打印错误堆栈信息
            e.printStackTrace();
            return ResultGenerator.genFailResult("上传失败");
        }
        return ResultGenerator.genSuccessResult("上传成功");
    }

    @PostMapping("/list")
    public Result list(@RequestBody SearchConditionsModel searchConditionsModel) {
        List<Affix> affixList = new ArrayList<>();
        if(searchConditionsModel.getExpandModel()!=null){
            String type = searchConditionsModel.getExpandModel().getType();
            if("0".equals(type)){
              SqlModel sqlModel =  ConditionUtils.getConditionsPlus(searchConditionsModel);
              String searchSql =  ConditionUtils.replaceKey("resourceid","substr(a.resourceid,1,36)",sqlModel.getSearchSql());
              sqlModel.setSearchSql(searchSql);
              affixList = affixService.list(sqlModel);
            }else{
                affixList = affixService.list(ConditionUtils.getConditionsPlus(searchConditionsModel));
            }
        }else{
            affixList = affixService.list(ConditionUtils.getConditionsPlus(searchConditionsModel));
        }
        PageInfo pageInfo = new PageInfo(affixList);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/delete")
    @MyLog(value = "删除记录")
    public Result delete(@RequestParam String id) {
        affixService.deleteById(id);
        return ResultGenerator.genSuccessResult("删除成功");
    }

    /**
     * @Description: 文件预览（文件路径在自定义盘符）
     * @param
     * @return
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate:     2018/12/1 10:10
     * @UpdateRemark:   修改内容
     * @date 2018/12/1 10:10
     */
    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        return ResultGenerator.genSuccessResult("/file/preview?id="+id);
    }

    /**
     * @Description: 文件预览（文件路径在项目中）
     * @param
     * @return
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate:     2018/12/1 10:10
     * @UpdateRemark:   修改内容
     * @date 2018/12/1 10:10
     */
    @GetMapping("/preview")
    public void preview(@RequestParam String id,HttpServletRequest request,HttpServletResponse response) {
        Affix affix = affixService.findById(id);
        String saveName = affix.getSavename();
        String pdfSaveName = saveName.substring(0,saveName.lastIndexOf("."))+".pdf";
        String uploadDir  = "" ;
        if("true".equals(uploadPathFlag)){
            uploadDir  = fileUploadPath;
        }else{
            uploadDir = request.getSession().getServletContext().getRealPath("/") +"upload/";
        }
        File file = new File(uploadDir+pdfSaveName);
        if (file.exists()){
            byte[] data = null;
            try {
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.setContentType("application/pdf");
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                log.error("pdf文件处理异常：" + e.getMessage());
            }
        }else{
            return ;
        }
    }
}