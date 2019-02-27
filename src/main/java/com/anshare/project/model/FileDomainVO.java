package com.anshare.project.model;

import lombok.Data;

import java.util.Date;
@Data
public class FileDomainVO {
    private Integer id;
    private Date time;  //操作时间
    private String filename;  //原文件名
    private String fileNameAfter;  //转换后文件名
    private String filesize;   //原文件大小
    private String filetype;   //原文件类型
    private String filetypeafter;  //后文件类型
    private String details;     //操作详情
    private String outputfile;  //保存路径
    private String inputfile;   //原文件路径
    private Integer pageno;
    private Integer pages;
    private Integer rid;
    private Integer pageoperation;
    private Integer pagestart;
    private Integer pageend;
    private String fileSizeAfter;  //后  文件大小
    private Integer status;  //操作状态
    private Integer afterpages;
    private String detail;
    private String timeConsuming; //消耗时长
}
