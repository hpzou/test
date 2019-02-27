package com.anshare.project.core.Util;


import javax.servlet.http.HttpServletResponse;
import java.io.*;

//用List构建带有层次结构的json数据
//List父子节点构造树形Json
public class FileUtil {

    //上传文件
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    //得到文件后缀名
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    //Java文件操作 获取不带扩展名的文件名
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
    /**
     * 下载项目根目录下doc下的文件
     * @param response response
     * @param fileName 文件名
     * @return 返回结果 成功或者文件不存在
     */
    public static String downloadFile(HttpServletResponse response, String fileName, boolean notIE) {
        InputStream stream = FileUtil.class.getClassLoader().getResourceAsStream("UploadFiles/" + fileName);
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            String name = java.net.URLEncoder.encode(fileName, "UTF-8");
            if (notIE) {
                name = java.net.URLDecoder.decode(name, "ISO-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + name );
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(stream);
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (FileNotFoundException e1) {
            //e1.getMessage()+"系统找不到指定的文件";
            return "系统找不到指定的文件";
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }

    //复制方法
    public static void FilesCopy(String src, String des) throws Exception {
        //初始化文件复制
        File file1=new File(src);
        //把文件里面内容放进数组
        File[] fs=file1.listFiles();
        //初始化文件粘贴
        File file2=new File(des);
        //判断是否有这个文件有不管没有创建
        if(!file2.exists()){
            file2.mkdirs();
        }
        //遍历文件及文件夹
        for (File f : fs) {
            if(f.isFile()){
                //文件
                fileCopy(f.getPath(),des+"\\"+f.getName()); //调用文件拷贝的方法
                System.out.println(f);
            }else if(f.isDirectory()){
                //文件夹
                FilesCopy(f.getPath(),des+"\\"+f.getName());//继续调用复制方法      递归的地方,自己调用自己的方法,就可以复制文件夹的文件夹了
            }
        }

    }

    /**
     * 文件复制的具体方法
     */
    private static void fileCopy(String src, String des) throws Exception {
        //io流固定格式
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
        int i = -1;//记录获取长度
        byte[] bt = new byte[2014];//缓冲区
        while ((i = bis.read(bt))!=-1) {
            bos.write(bt, 0, i);
        }
        bis.close();
        bos.close();
        //关闭流
    }
}

