package com.anshare.project.core.Util;

import java.io.*;
import com.anshare.project.core.ProjectConstant;
import com.anshare.project.model.FileDomainVO;
import com.aspose.words.*;
import com.aspose.words.SaveFormat;
public class WordToPdfUtil {
    /**
     * word转换
     * 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
     * @param
     */
    public static FileDomainVO wordToPdf(FileDomainVO  vo) {
        //默认失败
        vo.setStatus(ProjectConstant.FailStatus);
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            System.out.println("验证失败,会产生水印");
        }
        //创建一个doc对象
        Document doc =null;
        try {
            //统计时间
            long old = System.currentTimeMillis();
            //初始化定义
            File file =null;
            ImageSaveOptions iso = null;
            //输出流
            FileOutputStream os =null;
            //判断是否转换图片
            if(vo.getDetails().equals(ProjectConstant.DocToJPEG)){
                iso = new ImageSaveOptions(SaveFormat.JPEG);
            }else{
                //file.mkdirs();
                //新建一个空白文档
                file = new File(vo.getOutputfile());
                //创建文件夹
                file.mkdirs();
                file = new File(vo.getOutputfile()+vo.getFileNameAfter());
                os = new FileOutputStream(file);
            }
            //getInputfile是将要被转化文档
            doc = new Document(vo.getInputfile());
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            //------------------------------------------逻辑判断
            //>>>>>>DocToPDF
            if(vo.getDetails().equals(ProjectConstant.DocToPDF)){
                doc.save(os, SaveFormat.PDF);
            }//>>>>>>DocxToPDF
            if(vo.getDetails().equals(ProjectConstant.DocxToPDF)){
                doc.save(os, SaveFormat.PDF);
            }//>>>>>>DocToDocx
            else if(vo.getDetails().equals(ProjectConstant.DocToDocx)){
                doc.save(os, SaveFormat.DOCX);
            }//>>>>>>DocToTexT
            else if(vo.getDetails().equals(ProjectConstant.DocToTexT)){
                doc.save(os, SaveFormat.TEXT);
            }//>>>>>>DocToXps
            else if(vo.getDetails().equals(ProjectConstant.DocToXps)){
                doc.save(os, SaveFormat.XPS);
            }//>>>>>>HtmlToDoc
            else if(vo.getDetails().equals(ProjectConstant.HtmlToDoc)){
                doc.save(os, SaveFormat.DOC);
            }//>>>>>>DocToJPEG
            else if(vo.getDetails().equals(ProjectConstant.DocToJPEG)){
                //iso.setResolution(128);
                iso.setPrettyFormat(true);
                iso.setUseAntiAliasing(true);
                for (int i = 0; i < doc.getPageCount(); i++)
                {
                    iso.setPageIndex(i);
                    doc.save(vo.getOutputfile() + vo.getFileNameAfter() +"---"+(i+1)+ ".jpeg", iso);
                }
            }
            //------------------------------------------逻辑判断
            //统计时间
            long now = System.currentTimeMillis();
            //System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"+"文件保存在:" + vo.getOutputfile());
            //设置时间
            vo.setTimeConsuming((((now - old) / 1000.0)+"").trim());
            //成功
            vo.setStatus(ProjectConstant.SuccessStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    /**
     * 验证License
     * @return boolean
     */
    public static boolean getLicense() {
        boolean result = false;
        try {
            //File file = new File("src/main/resources/static/license.xml");
            File file = new File("C:\\license.xml");
            InputStream is = new FileInputStream(file);
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        FileDomainVO vo = new FileDomainVO();
        vo.setDetails(ProjectConstant.DocxToPDF);    //进行word转pdf
        vo.setInputfile("E:\\GZLJ3\\GZLJJG1\\AnshareSpringBootAdmin\\src\\main\\webapp\\upload\\2018-11-238ee06b3e-5d03-452d-9188-833f15035a3f.docx");     //需要转换的word
        vo.setOutputfile("E:\\GZLJ3\\GZLJJG1\\AnshareSpringBootAdmin\\src\\main\\webapp\\upload\\");         //保存路径
        vo.setFileNameAfter("123.pdf");           //转换后的文件名,自己取
        wordToPdf(vo);                           //开始转换
    }
}
