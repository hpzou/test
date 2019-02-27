package com.anshare.project.core.Util;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
* @ClassName ImportExcel
* @Description Excel导入工具类
* @Author Zhou
* @Date 2019/1/21 2019/1/21
* @UpdateUser:  
* @UpdateDate: 2019/1/21 2019/1/21
* @UpdateRemark:   修改内容
* @Version 1.0
*/
public class ImportExcel {
    private static Logger logger  = Logger.getLogger(ImportExcel.class);
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * @Description:  方法名
     * @param 
     * @return 
     * @throws 
     * @author Zhou
     * @UpdateUser: 
     * @UpdateDate:   2019/1/21 16:12
     * @UpdateRemark:   修改内容
     * @date  2019/1/21 16:12
     */
    public static List<String[]> readExcel(MultipartFile file) throws Exception{
        checkFile(file);
        Workbook workbook = getWorkBook(file);
        List<String[]> list = new ArrayList<>();
        if (workbook != null){
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++){
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null)
                    continue;
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++){
                    Row row = sheet.getRow(rowNum);
                    if (row == null)
                        continue;
                    int firstCellNum = row.getFirstCellNum();
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
        }
        return list;
    }

    /**
     * @Description:  方法名
     * @param 
     * @return 
     * @throws 
     * @author Zhou
     * @UpdateUser: 
     * @UpdateDate:   2019/1/21 16:12
     * @UpdateRemark:   修改内容
     * @date  2019/1/21 16:12
     */
    private static String getCellValue(Cell cell){
        String cellValue = "";
        if (cell == null)
            return cellValue;
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * @Description:  方法名
     * @param 
     * @return 
     * @throws 
     * @author Zhou
     * @UpdateUser: 
     * @UpdateDate:   2019/1/21 16:12
     * @UpdateRemark:   修改内容
     * @date  2019/1/21 16:12
     */
    private static Workbook getWorkBook(MultipartFile file) throws Exception{
        String fileName = file.getOriginalFilename();
        Workbook workbook = null;
        InputStream is;
        try {
            is = file.getInputStream();
            if (fileName.endsWith(xls)){
                workbook = new HSSFWorkbook(is);
            }else if (fileName.endsWith(xlsx)){
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }

    /**
     * @Description:  方法名
     * @param 
     * @return 
     * @throws 
     * @author Zhou
     * @UpdateUser: 
     * @UpdateDate:   2019/1/21 16:29
     * @UpdateRemark:   修改内容
     * @date  2019/1/21 16:29
     */
    private static String checkFile(MultipartFile file){
        if(null == file){
            return "文件不存在！";
        }
        String fileName = file.getOriginalFilename();
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            return "不是Excel文件";
        }
        return null;
    }
}
