package com.anshare.project.core.Util;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;


/**
* @ClassName ExportExcel
* @Description Excel导出工具类
* @Author wukunfan
* @Date 2018/11/14 14:48
* @UpdateUser:
* @UpdateDate:     2018/11/14 14:48
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@SuppressWarnings("deprecation")
public class ExportExcel {

    /**
     * 名称
     */
    private String   name;

    /**
     * 构造方法
     */
    public ExportExcel() {

    }

    /** 
     * EXCEL名称 
     * @param name 
     */
    public ExportExcel(String name) {

        this.name = name;
    }

    /** 
     * 生成EXCEL 
     * @param response HttpServletResponse
     * @param data 数据 
     * @param headers 表头 
     * @throws Exception 异常
     */
    public void exportExcel(HttpServletResponse response, List<Map<String, Object>> data, Columns[] headers)
            throws Exception {
        this.exportExcel(response, data, headers, null);
    }

    public void exportExcelPlus(HttpServletResponse response, List<Map<String, Object>> data, List<ExportExcel.Columns> columnsList)
            throws Exception {
        this.exportExcelPlus(response, data, columnsList, null);
    }

    /** 
     * 生成EXCEL 
     * @param response HttpServletResponse
     * @param data 数据 
     * @param headers 表头 
     * @param footers 尾行 
     * @return 返回相对路径，失败返回null 
     * @throws Exception 异常
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public HSSFWorkbook exportExcel(HttpServletResponse response, List<Map<String, Object>> data, Columns[] headers,
            List<Map> footers) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(name);
        //四个参数分别是：起始行，起始列，结束行，结束列      
        // sheet.addMergedRegion(new Region(0, (short) (celln + 1), 0,(short) (celle + 1)));      

        //生成表头单元样式       
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //生成一个字体         
        HSSFFont headerFont = workbook.createFont();
        headerFont.setColor(HSSFColor.WHITE.index);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);

        //生成数据单元样式      
        HSSFCellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //生成另一个字体         
        HSSFFont dataFont = workbook.createFont();
        dataFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        //把字体应用到当前的样式         dataStyle.setFont(dataFont); 

        //尾行样式         
        HSSFCellStyle footerStyle = workbook.createCellStyle();
        footerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        footerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        footerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        footerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        footerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        footerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        footerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //生成一个字体        
        HSSFFont footerFont = workbook.createFont();
        footerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        footerStyle.setFont(footerFont);

        //生成表头  
        HSSFRow row = sheet.createRow(0);
        Map<String, Integer> columnIndex = new HashMap<String, Integer>();
        for (int i = 0; i < headers.length; i++) {
            //设置列宽           
            sheet.setColumnWidth((short) i, (short) (headers[i].getWidth() * 256));
            //填充数据          
            HSSFCell cell = row.createCell((short) i);
            cell.setCellStyle(headerStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i].getTitle());
            cell.setCellValue(text);

            columnIndex.put(headers[i].getField(), i);
        }
        //生成数据        
        for (int i = 0; i < data.size(); i++) {
            int excelIndex = i + 1; //实际EXCEL行号            
            HSSFRow dataRow = sheet.createRow((short) excelIndex);
            Map map = data.get(i);
            
            for (int h = 0; h < headers.length; h++) {
                HSSFCell cell = dataRow.createCell((short) h);
                cell.setCellStyle(dataStyle);
                Object cellValue = headers[h].getFormatter().formatter(map.get(headers[h].getField()), excelIndex);
                dataProcessing(cell, cellValue);
            }
        }
        //设置尾行       
        if (footers != null) {
            int footersIndex = data.size() + 1;
            for (int i = 0; i < footers.size(); i++) {
                footersIndex += i;
                HSSFRow footerRow = sheet.createRow((short) footersIndex);
                Map<String, Object> map = footers.get(i);
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    HSSFCell cell = footerRow.createCell(columnIndex.get(m.getKey()).shortValue());
                    cell.setCellStyle(footerStyle);
                    dataProcessing(cell, m.getValue());
                }
            }
        }
        // 设置response参数，打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream out = null;
        try {
            // 设置返回文件名
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((name + ".xls").getBytes("GBK"), "ISO8859-1"));
            // 获取页面返回输出流
            out = response.getOutputStream();
            // Excel文件写入输出流
            workbook.write(out);
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                // 关闭输出流
                out.close();
            }
        }
        return workbook;

    }

    /** 
     * 对各种类型处理 
     * @param value 值
     * @param cell 列
     */
    private void dataProcessing(HSSFCell cell, Object value) {

        if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Float || value instanceof Long || value instanceof Integer) {
            cell.setCellValue(Double.valueOf(value.toString()));
        } else {
            HSSFRichTextString str = new HSSFRichTextString(StringUtils.commCovent(value));
            cell.setCellValue(str);
        }
    }

    /** 
     * 数据处理回调 
     */
    public interface Formatter {

        /** 
         * 数据单元的回调方法 
         * @param value 
         * @param index   
         * @return 返回处理后的数据 
         */
        public Object formatter(Object value, int index);
    }

    /** 
     * 表头数据类型 
     */
    public class Columns {

        /**
         * 域
         */
        private String field;

        /**
         * 标题
         */
        private String title;

        /**
         * 宽度
         */
        private int width = 15;

        /**
         * 格式
         */
        private Formatter formatter = new Formatter() {

            public Object formatter(Object value, int index) {

                return value;
            }
        };

        /** 
         * 设置数据 
         * @param field 字段 
         * @param title 标题   
         */
        public Columns(String field, String title) {

            this.field = field;
            this.title = title;
        }

        /**
         * 域获得
         * @return 域
         */
        public String getField() {

            return field;
        }

        /**
         * 标题获得
         * @return 标题
         */
        public String getTitle() {

            return title;
        }

        /**
         * 格式获得
         * @return 格式
         */
        public Formatter getFormatter() {

            return formatter;
        }

        /** 
         * 数据处理回调方法 
         * @param formatter  Formatter
         * @return 列信息
         */
        public Columns setFormatter(Formatter formatter) {

            this.formatter = formatter;
            return this;
        }

        /**
         * 宽度获得
         * @return 宽度
         */
        public int getWidth() {

            return width;
        }

        /** 
         * 设置表格宽度（字符为单位） 
         * @param width 宽
         * @return 列信息
         */
        public Columns setWidth(int width) {

            this.width = width;
            return this;
        }
    }

    /**
     * 生成EXCEL加强版
     * @param response HttpServletResponse
     * @param data 数据
     * @param columnsList 表头
     * @param footers 尾行
     * @return 返回相对路径，失败返回null
     * @throws Exception 异常
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public HSSFWorkbook exportExcelPlus(HttpServletResponse response, List<Map<String, Object>> data, List<ExportExcel.Columns> columnsList,
                                    List<Map> footers) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(name);
        //四个参数分别是：起始行，起始列，结束行，结束列
        // sheet.addMergedRegion(new Region(0, (short) (celln + 1), 0,(short) (celle + 1)));

        //生成表头单元样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        //自定义设置颜色
        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte) 242, (byte) 242, (byte) 242);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //生成一个字体
        HSSFFont headerFont = workbook.createFont();
        headerFont.setColor(HSSFColor.BLACK.index);
        headerFont.setFontHeightInPoints((short) 12);
        //加粗显示
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);

        //生成数据单元样式
        HSSFCellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //生成另一个字体
        HSSFFont dataFont = workbook.createFont();
        dataFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        //把字体应用到当前的样式         dataStyle.setFont(dataFont);

        //尾行样式
        HSSFCellStyle footerStyle = workbook.createCellStyle();
        footerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        footerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        footerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        footerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        footerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        footerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        footerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //生成一个字体
        HSSFFont footerFont = workbook.createFont();
        footerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        footerStyle.setFont(footerFont);

        //生成表头
        HSSFRow row = sheet.createRow(0);
        Map<String, Integer> columnIndex = new HashMap<String, Integer>();

        for(int i = 0;i<columnsList.size();i++){
            //设置列宽
            sheet.setColumnWidth((short) i, (short) (columnsList.get(i).getWidth() * 256));
            //填充数据
            HSSFCell cell = row.createCell((short) i);
            cell.setCellStyle(headerStyle);
            HSSFRichTextString text = new HSSFRichTextString(columnsList.get(i).getTitle());
            cell.setCellValue(text);

            columnIndex.put(columnsList.get(i).getField(), i);
        }
        //生成数据
        for (int i = 0; i < data.size(); i++) {
            int excelIndex = i + 1; //实际EXCEL行号
            HSSFRow dataRow = sheet.createRow((short) excelIndex);
            Map map = data.get(i);

            for (int h = 0; h < columnsList.size(); h++) {
                HSSFCell cell = dataRow.createCell((short) h);
                cell.setCellStyle(dataStyle);
                Object cellValue = columnsList.get(h).getFormatter().formatter(map.get(columnsList.get(h).getField()), excelIndex);
                dataProcessing(cell, cellValue);
            }
        }
        //设置尾行
        if (footers != null) {
            int footersIndex = data.size() + 1;
            for (int i = 0; i < footers.size(); i++) {
                footersIndex += i;
                HSSFRow footerRow = sheet.createRow((short) footersIndex);
                Map<String, Object> map = footers.get(i);
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    HSSFCell cell = footerRow.createCell(columnIndex.get(m.getKey()).shortValue());
                    cell.setCellStyle(footerStyle);
                    dataProcessing(cell, m.getValue());
                }
            }
        }
        // 设置response参数，打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //跨域设置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        OutputStream out = null;
        try {
            // 设置返回文件名
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((name + ".xls").getBytes("GBK"), "ISO8859-1"));
            // 获取页面返回输出流
            out = response.getOutputStream();
            // Excel文件写入输出流
            workbook.write(out);
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                // 关闭输出流
                out.close();
            }
        }
        return workbook;
    }

    public static void exportExcelPlus(String exportCondition,List<?> list,HttpServletResponse response){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        String name = "表格导出数据";
        try {
            Object obj = null;
            Iterator<?> listIt = list.iterator();
            while(listIt.hasNext()){
                obj = listIt.next();
                Map<String, Object> temp = new HashMap<String, Object>();
                if(!exportCondition.isEmpty()) {
                    JSONArray jsonArray = JSONArray.parseArray(exportCondition);
                    if(jsonArray.size()>0){
                        for(int i=0;i<jsonArray.size();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String strKey = jsonObject.get("field")+"";//查询Key
                            //获取方法
                            System.out.println("get"+StringUtils.toUpperCaseFirstOne(strKey));
                            Method m = obj.getClass().getDeclaredMethod("get"+StringUtils.toUpperCaseFirstOne(strKey));
                            //调用方法
                            temp.put(strKey, m.invoke(obj) );
                        }
                    }
                }
                data.add(temp);
            }
        }  catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 表头列设置
        if(list.size()==0){
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("warning", "最近没有数据或者您筛选的数据不存在。");
            data.add(temp);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(name);
            Columns c = new ExportExcel().new Columns("warning", "提示");
            c.setWidth(50);
            ExportExcel.Columns[] headers = new ExportExcel.Columns[] {
                    c,
            };
            try {
                new ExportExcel(name).exportExcel(response, data, headers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            List<ExportExcel.Columns> columnsList = new ArrayList<>();
            if(!exportCondition.isEmpty()) {
                JSONArray jsonArray = JSONArray.parseArray(exportCondition);
                if(jsonArray.size()>0){
                    for(int i=0;i<jsonArray.size();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strKey = jsonObject.get("field")+"";//查询Key
                        String strValue = jsonObject.get("value")+"";//查询条件值
                        Columns c = new ExportExcel().new Columns(strKey, strValue);
                        columnsList.add(c);
                    }
                }
            }
            try {
                new ExportExcel(name).exportExcelPlus(response, data, columnsList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
