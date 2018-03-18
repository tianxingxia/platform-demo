package com.yk.platform.common.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yk.platform.business.excel.entity.ExcelOpLogDetail;
import com.yk.platform.business.excel.entity.ImportInfoBean;
import com.yk.platform.business.excel.entity.ValidateBean;

/**
 * 导入excel工具类
 * <p>
 * Description:
 * </p>
 * 
 * @date 2014年5月16日
 * @author 李志勇
 * @version 1.0
 *          <p>
 *          Company:Mopon
 *          </p>
 *          <p>
 *          Copyright:Copyright(c)2013
 *          </p>
 */
public class ImportUtil {

    private static Logger logger = Logger.getLogger(ImportUtil.class.getName());

    /**
     * 导入操作 方法用途: <br>
     * 实现步骤: <br>
     * 
     * @param excelpath
     * @param tempPath
     * @param tempName
     * @param claz
     * @return
     */
    public static <T> ImportInfoBean<T> parseExcel(String excelpath, String tempPath, String tempId, Class<T> claz) {
        ImportInfoBean<T> importInfoBean = new ImportInfoBean<T>();
        File file = null;
        InputStream input = null;
        Workbook workBook = null;
        Sheet sheet = null;
        if (excelpath != null && excelpath.length() > 0) {
            String suffix = excelpath.substring(excelpath.lastIndexOf("."), excelpath.length());
            file = new File(excelpath);
            try {
                input = new FileInputStream(file);
                if (".xls".equals(suffix)) {// 2003
                    workBook = new HSSFWorkbook(input);
                }
                else if (".xlsx".equals(suffix)) {// 2007
                    workBook = new XSSFWorkbook(input);
                }

                Map<String, String> fieldMap = FieldsReader.getTempleteMap(tempPath, tempId);
                String titles = FieldsReader.getTitles(tempPath, tempId);
                String[] cellNames = titles.split(",");
                sheet = workBook.getSheetAt(0); // 默认读取excel中下标为0的sheet
                Map<String, ValidateBean> vMap = FieldsReader.getValidateBean(tempPath, tempId);
                importInfoBean = getContent(sheet, claz, cellNames, fieldMap, vMap);
            }
            catch (FileNotFoundException e) {
                logger.error("文件不存在:" + excelpath);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            catch (IllegalArgumentException e) {
                logger.error("excel操作参数不正确！", e);
            }
            finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                }
                catch (IOException e) {
                    logger.error("关闭文件流错误", e);
                }
            }
        }
        else {
            logger.error(excelpath + "路径错误!");
        }
        return importInfoBean;
    }

    /**
     * 获得内容 方法用途: <br>
     * 实现步骤: <br>
     * 
     * @param sheet
     * @param clazz
     * @param cellNames
     * @param fieldMap
     * @param vMap验证属性
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> ImportInfoBean<T> getContent(Sheet sheet, Class<T> clazz, String[] cellNames, Map<String, String> fieldMap,
            Map<String, ValidateBean> vMap) {
        boolean errorflag = true;// 异常标记，如果出了异常则设为false,返回空的list

        List<T> list = new ArrayList<T>();// 解析excel获得的list数据集合
        List<ExcelOpLogDetail> errInfoList = new ArrayList<ExcelOpLogDetail>();// 错误信息集合
        int rowCount = sheet.getPhysicalNumberOfRows(); // 总行数
        Row row = null;
        Cell cell = null;
        String cellName = null;
        String fieldName = null;
        int cellCount = cellNames.length; // 总列数
        ValidateBean vBean = null;// 验证对象
        ExcelOpLogDetail errInfo = null;// 异常对象
        String validateMsg = null;
        try {
            Constructor<?> cons = clazz.getConstructor(new Class[] {});
            Object copyObject = null;
            Object cellValue = null;

            Field field = null;
            Method setMethod = null;
            String firstLetter = null;
            String setMethodName = null;

            for (int i = 1; i < rowCount; i++) {

                copyObject = cons.newInstance();
                row = sheet.getRow(i);
                for (int j = 0; j < cellCount; j++) {
                    cellValue = null;
                    cell = row.getCell(j);
                    cellName = cellNames[j];
                    fieldName = fieldMap.get(cellName);
                    vBean = vMap.get(cellName);
                    field = clazz.getDeclaredField(fieldName);
                    firstLetter = fieldName.substring(0, 1).toUpperCase();
                    setMethodName = "set" + firstLetter + fieldName.substring(1);
                    setMethod = clazz.getMethod(setMethodName, new Class[] { field.getType() });
                    try {
                        if (field.getType() == int.class) {
                            if (cell != null) {
                                cellValue = (int) cell.getNumericCellValue();
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);
                                errInfoList.add(errInfo);
                            }

                        }
                        else if (field.getType() == Integer.class) {
                            if (cell != null) {
                                cellValue = (int) cell.getNumericCellValue();
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);
                                errInfoList.add(errInfo);
                            }
                        }
                        else if (field.getType() == long.class) {
                            if (cell != null) {
                                cellValue = (long) cell.getNumericCellValue();
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);

                                errInfoList.add(errInfo);
                            }
                        }
                        else if (field.getType() == double.class) {
                            if (cell != null) {
                                cellValue = cell.getNumericCellValue();
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);
                                errInfoList.add(errInfo);
                            }
                        }
                        else if (field.getType() == float.class) {
                            if (cell != null) {
                                cellValue = (float) cell.getNumericCellValue();
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);
                                errInfoList.add(errInfo);
                            }
                        }
                        else if (field.getType() == String.class) {
                            if (cell != null) {// 字符串类型的属性时,要先判断cell的类型，因为如果是纯数字cell的类型会自动变为数字类型
                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    Double doubleValue = cell.getNumericCellValue();
                                    Integer intValue = doubleValue.intValue(); // 获得double类型的int值否则转为字符串时会有个小数点和0
                                    if (doubleValue > intValue) {
                                        cellValue = doubleValue.toString();// 要将数字类型转为string类型，否则给String属性赋值时会出错
                                    }
                                    else {
                                        cellValue = intValue.toString();// 要将数字类型转为string类型，否则给String属性赋值时会出错
                                    }
                                }
                                else {
                                    cellValue = cell.getStringCellValue();
                                }
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);
                                errInfoList.add(errInfo);
                            }

                        }
                        else if (field.getType() == Date.class) {
                            if (cell != null) {
                                cellValue = cell.getDateCellValue();
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);
                                errInfoList.add(errInfo);
                            }
                        }
                        else if (field.getType() == boolean.class) {
                            if (cell != null) {
                                cellValue = cell.getBooleanCellValue();
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);
                                errInfoList.add(errInfo);
                            }
                        }
                        else if (field.getType() == BigDecimal.class) {
                            if (cell != null) {
                                cellValue = new BigDecimal(cell.getNumericCellValue());
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);
                                errInfoList.add(errInfo);
                            }
                        }
                        else if (field.getType() == Short.class) {
                            if (cell != null) {
                                cellValue = (short) cell.getNumericCellValue();
                            }
                            validateMsg = FieldsReader.validateData(vBean, cellValue);
                            // 验证通过才设值
                            if (validateMsg.equals("true")) {
                                if (cellValue != null) {
                                    setMethod.invoke(copyObject, new Object[] { cellValue });
                                }
                            }
                            else {
                                errorflag = false;// 标记为异常
                                errInfo = new ExcelOpLogDetail();
                                errInfo.setRowNum(i + 1);
                                errInfo.setColNum(j + 1);
                                errInfo.setErrDate(new Date());
                                errInfo.setErrMsg(validateMsg);
                                errInfoList.add(errInfo);
                            }
                        }
                        else {
                            errInfo = new ExcelOpLogDetail();
                            errInfo.setRowNum(i + 1);
                            errInfo.setColNum(j + 1);
                            errInfo.setErrDate(new Date());
                            errInfo.setErrMsg(field.getType() + "is not support");
                            errInfoList.add(errInfo);
                        }
                    }
                    catch (Exception e) {
                        errorflag = false;// 标记为异常
                        errInfo = new ExcelOpLogDetail();
                        errInfo.setRowNum(i + 1);
                        errInfo.setColNum(j + 1);
                        errInfo.setErrDate(new Date());
                        errInfo.setErrMsg("取值或者其他错误," + e.getMessage() + e);
                        errInfoList.add(errInfo);
                    }
                }
                list.add((T) copyObject);
            }
        }
        catch (SecurityException e) {

            errInfo = new ExcelOpLogDetail();
            errInfo.setErrDate(new Date());
            errInfo.setErrMsg("SecurityException");
            errInfoList.add(errInfo);
            errorflag = false;// 标记为异常
            logger.error(e.getMessage(), e);
        }
        catch (IllegalArgumentException e) {
            errInfo = new ExcelOpLogDetail();
            errInfo.setErrDate(new Date());
            errInfo.setErrMsg("IllegalArgumentException");
            errInfoList.add(errInfo);
            errorflag = false;// 标记为异常
            logger.error(e.getMessage(), e);
        }
        catch (NoSuchMethodException e) {
            errInfo = new ExcelOpLogDetail();
            errInfo.setErrDate(new Date());
            errInfo.setErrMsg("NoSuchMethodException");
            errInfoList.add(errInfo);
            errorflag = false;// 标记为异常
            logger.error(e.getMessage(), e);
        }
        catch (NoSuchFieldException e) {
            errInfo = new ExcelOpLogDetail();
            errInfo.setErrDate(new Date());
            errInfo.setErrMsg("NoSuchFieldException");
            errInfoList.add(errInfo);
            errorflag = false;// 标记为异常
            logger.error(e.getMessage(), e);
        }
        catch (InstantiationException e) {
            errInfo = new ExcelOpLogDetail();
            errInfo.setErrDate(new Date());
            errInfo.setErrMsg("InstantiationException");
            errInfoList.add(errInfo);
            errorflag = false;// 标记为异常
            logger.error(e.getMessage(), e);
        }
        catch (IllegalAccessException e) {
            errInfo = new ExcelOpLogDetail();
            errInfo.setErrDate(new Date());
            errInfo.setErrMsg("IllegalAccessException");
            errInfoList.add(errInfo);
            errorflag = false;// 标记为异常
            logger.error(e.getMessage(), e);
        }
        catch (InvocationTargetException e) {
            errInfo = new ExcelOpLogDetail();
            errInfo.setErrDate(new Date());
            errInfo.setErrMsg("InvocationTargetException");
            errInfoList.add(errInfo);
            errorflag = false;// 标记为异常
            logger.error(e.getMessage(), e);
        }

        ImportInfoBean<T> importInfoBean = new ImportInfoBean<T>();
        importInfoBean.setBeanList(list);
        importInfoBean.setErrInfoList(errInfoList);
        importInfoBean.setHasError(errorflag);
        return importInfoBean;
    }

}
