package com.yk.platform.business.excel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yk.platform.business.excel.entity.ExcelLogAspectParam;
import com.yk.platform.business.excel.entity.ExcelOpLog;
import com.yk.platform.business.excel.entity.ExcelOpLogConstant;
import com.yk.platform.business.excel.entity.ExcelOpLogDetail;
import com.yk.platform.business.excel.entity.ImportInfoBean;
import com.yk.platform.business.excel.qo.ExcelOpLogDetailQo;
import com.yk.platform.business.excel.service.IExcelBussinessDataService;
import com.yk.platform.business.excel.service.IExcelOpLogDetailService;
import com.yk.platform.business.excel.service.IExcelOpLogService;
import com.yk.platform.common.AbstractController;
import com.yk.platform.common.PageResult;
import com.yk.platform.common.ResultBean;
import com.yk.platform.common.errorcode.ErrorCode;
import com.yk.platform.common.util.ExceptionUtil;
import com.yk.platform.common.util.FileUtil;
import com.yk.platform.common.util.JSONUtil;
import com.yk.platform.common.util.ResponseUtils;
import com.yk.platform.common.util.UUIDUtil;
import com.yk.platform.common.util.UserUtil;
import com.yk.platform.common.util.ZipUtil;
import com.yk.platform.common.util.poi.ExportUtil;
import com.yk.platform.common.util.poi.FieldsReader;
import com.yk.platform.common.util.poi.ImportUtil;

/**
 * Excel导入、导出Controller
 * 
 * @author yangkai
 * @date 2015-12-02
 * @version 1.0.0
 */
@Controller
@RequestMapping("/excelhandle")
public class ExcelHandleController extends AbstractController {

    @Autowired
    private IExcelBussinessDataService excelBussinessDataService;
    @Autowired
    private IExcelOpLogService excelOpLogService;
    @Autowired
    private IExcelOpLogDetailService excelOpLogDetailService;
    @Autowired
    private HttpServletRequest request;

    private static Logger logger = Logger.getLogger(ExcelHandleController.class);

    private final static String FILE_SEPARATOR = File.separator;

    /**
     * 导出excel第一步,生成excel文件，返回excel名称 <br>
     * 实现步骤: <br>
     * 
     * @param tempId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel")
    public String exportExcel(String tempId, String customParas, HttpServletRequest request) {
        logger.info("exportExcel in,tempId:" + tempId);
        String templetePath = getTempletePath(tempId);
        String forlderPath = getExcelForlderPath(request);
        String newFileName = UUID.randomUUID().toString().replaceAll("\\-", "") + ".xlsx";
        String exportPath = forlderPath + FILE_SEPARATOR + newFileName;
        Map<String, String> map = JSONUtil.getParasMap(customParas);
        List<?> ls = excelBussinessDataService.getExortDataByPara(tempId, map);
        logger.info("size:" + ls.size());
        // 记录日志
        ExcelOpLog oplog = ExcelLogAspectParam.getExcelOpLog();
        oplog.setOpId(UUIDUtil.getUUID());
        oplog.setOpType(ExcelOpLogConstant.EXPORTEXCEL);// 设置操作类型为导出
        String excelName = FieldsReader.getLevel1AttributeByName(templetePath, tempId, "name");
        if (excelName == null) {
            excelName = tempId;
        }
        String opUserName = ResponseUtils.getUserId();
        oplog.setOpUser(opUserName);
        ExportUtil.doExport(templetePath, tempId, exportPath, ls);
        oplog.setTotalRows(ls.size());// 导出条数
        oplog.setOpResult(ExcelOpLogConstant.OP_SUCCESS);// 操作成功
        oplog.setLogDesc("导出:" + excelName);
        try {
            excelOpLogService.insert(oplog);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("生成文件:" + newFileName);
        return newFileName;

    }

    /**
     * 
     * 导出excel第二步，客户端下载excel文件
     * 
     * @param fileName
     * @param response
     * @param request
     */
    @RequestMapping("/download")
    public void downloadFile(String fileName, String newFileName, HttpServletResponse response, HttpServletRequest request) {
        logger.info("downloadFile in.");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + newFileName);
        try {
            String forlderPath = getExcelForlderPath(request);
            String exportPath = forlderPath + FILE_SEPARATOR + fileName;
            InputStream inputStream = new FileInputStream(exportPath);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            inputStream.close();
            File file = new File(exportPath);
            if (file.exists()) {
                file.delete();
            }
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 上传页面 方法用途: <br>
     * 实现步骤: <br>
     * 
     * @return
     */
    @RequestMapping(value = "/uploadPage")
    public ModelAndView updPage(@RequestParam("excelName") String excelName,
            @RequestParam(value = "commonParams", required = false) String commonParams) {
        String url = "excel/uploadPage";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tempId", excelName);
        if (null != commonParams) {
            map.put("customParas", commonParams);
        }
        return new ModelAndView(url, map);
    }

    @RequestMapping(value = "/doUpload", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public @ResponseBody ResultBean doUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
        ResultBean responseInfo = new ResultBean();
        Map<String, MultipartFile> fileMap = request.getFileMap();
        String fileName = null;
        String forlderPath = getExcelForlderPath(request);
        String newFileName = null;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("\\-", "");// 返回一个随机UUID。
            String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
            newFileName = uuid + (suffix != null ? suffix : "");// 构成新文件名。
            String uplPath = forlderPath + FILE_SEPARATOR + newFileName;
            File uploadFile = new File(uplPath);
            try {
                FileCopyUtils.copy(mf.getBytes(), uploadFile);
                responseInfo.setData(newFileName);
            }
            catch (IOException e) {
                ExceptionUtil.log(e, logger);
                responseInfo.setStateCode(ErrorCode.CODE_FILE_COPY_FAIL);
                responseInfo.setStateInfo(ErrorCode.DESC_FILE_COPY_FAIL);
            }
        }

        return responseInfo;
    }

    /**
     * 导入excel文件 第二步： 拿到上传的文件名称解析excel 如果有错误则返回日志表ID 如果没错误则返回0 方法用途: <br>
     * 实现步骤: <br>
     * 
     * @param tempId
     * @param request
     * @return
     */
    @RequestMapping(value = "/parseExcel")
    public @ResponseBody ResultBean parseExcel(String fileName, String tempId, String customParas, HttpServletRequest request) {
        logger.debug("parseExcel in==============tempId:" + tempId);
        ResultBean resultBean = new ResultBean();
        String templetePath = getTempletePath(tempId);
        String forlderPath = getExcelForlderPath(request);

        String excelPath = forlderPath + FILE_SEPARATOR + fileName;
        logger.error(excelPath);

        ImportInfoBean<?> importInfoBean = null;
        String className = FieldsReader.getLevel1AttributeByName(templetePath, tempId, "class");
        try {
            Class<?> c = Class.forName(className);
            logger.debug("解析模板开始");
            importInfoBean = ImportUtil.parseExcel(excelPath, templetePath, tempId, c);
            logger.debug("解析模板完成");
        }
        catch (ClassNotFoundException e) {
            logger.error("模板配置的类名找不到对应的类", e);
            resultBean.setStateCode(ErrorCode.CODE_EXCEL_TEMPLATE_INVALID);
            resultBean.setStateInfo("Excel模板配置的类名不正确");
        }
        catch (NullPointerException e) {
            logger.error("解析模板异常:", e);
            resultBean.setStateCode(ErrorCode.CODE_EXCEL_BLANK_LINE);
            resultBean.setStateInfo("Excel中有空行");
        }
        if (importInfoBean != null) {
            List<?> list = importInfoBean.getBeanList();// 获得的bean
            logger.error("list size:" + list.size());

            List<ExcelOpLogDetail> errors = importInfoBean.getErrInfoList();// 错误信息
            int errCount = errors.size();
            logger.error("errCount:" + errCount);

            // 清除临时文件
            File file = new File(excelPath);
            if (file.exists()) {
                file.delete();
            }

            if (errCount > 0) {
                // 错误数量大于0则返回日志ID
                String opId = insertExcelOpLog(templetePath, tempId, errors);
                resultBean.setStateCode(ErrorCode.CODE_EXCEL_IMPORT_FAIL);
                resultBean.setStateInfo(ErrorCode.DESC_EXCEL_IMPORT_FAIL);
                resultBean.setData(opId);
            }
            else {
                // ==============调用service方法插入 list<?>
                Map<String, String> filedmap = JSONUtil.getParasMap(customParas);
                resultBean = excelBussinessDataService.insertExcelData(tempId, filedmap, list);
            }
        }

        return resultBean;
    }

    /**
     * 下载模板 @param tempId @param request @return @throws
     */
    @ResponseBody
    @RequestMapping(value = "/createTemplete")
    public void createTemplete(String tempId, HttpServletRequest request, HttpServletResponse response) {
        logger.error("createTemplete ing");
        ResultBean resultBean = getSuccessResultBean();
        String templetePath = getTempletePath(tempId);
        String forlderPath = getExcelForlderPath(request);
        String newFileName = UUID.randomUUID().toString().replaceAll("\\-", "") + ".xlsx";
        String exportPath = forlderPath + FILE_SEPARATOR + newFileName;
        ExportUtil.doExport(templetePath, tempId, exportPath, null);
        resultBean.setData(newFileName);
        ResponseUtils.setObjectResponse(response, resultBean);
    }

    @RequestMapping(value = "/listall", method = RequestMethod.GET)
    public @ResponseBody ResultBean showList(HttpServletRequest request, HttpServletResponse response, ExcelOpLogDetailQo qo) {
        // 返回Excel详细日志
        ResultBean resultBean = getSuccessResultBean();
        PageResult pageResult = excelOpLogDetailService.getExcelOpLogDetailsByPage(qo);
        resultBean.setData(pageResult);
        return resultBean;
    }

    /**
     * 导出大数据 生成压缩文件 @param tempId @param customParas @param request @throws
     */
    @ResponseBody
    @RequestMapping(value = "/createZipFile")
    public String createZipFile(String tempId, String customParas, HttpServletRequest request) {
        Map<String, String> map = JSONUtil.getParasMap(customParas);
        String templetePath = getTempletePath(tempId);
        String forlderPath = getExcelForlderPath(request); // excel生成文件保存目录
        String newForlder = forlderPath + FILE_SEPARATOR + UUID.randomUUID().toString().replaceAll("\\-", "");// 新文件夹名
        File newForlderFile = new File(newForlder);
        if (!newForlderFile.exists()) {
            newForlderFile.mkdirs();
        }

        int pageNo = 1;
        boolean result = true;
        List<?> list = null;
        int allCount = 0;// 总数
        while (result) {
            list = excelBussinessDataService.getExortDataByPara(tempId, map);// 获得要导出的数据,暂时不分页
            logger.info(list != null ? list.size() : 0);

            if (list == null || list.size() < 1) {
                result = false;
            }
            else {
                allCount += list.size();
                String newFileName = pageNo + ".xlsx";
                String exportPath = newForlder + FILE_SEPARATOR + newFileName;
                ExportUtil.doExport(templetePath, tempId, exportPath, list);
            }
            pageNo++;

        }

        File sourcefile = new File(newForlder);
        String zipPath = newForlder + ".zip";
        ZipUtil.zip(sourcefile.getAbsolutePath(), zipPath);// 生成压缩文件
        if (sourcefile.exists()) {// 删除生成的excel文件夹
            FileUtil.delFolder(sourcefile.getAbsolutePath());
        }

        File zipFile = new File(zipPath);
        String zipName = zipFile.getName();

        // 记录日志
        ExcelOpLog oplog = ExcelLogAspectParam.getExcelOpLog();
        oplog.setOpType(ExcelOpLogConstant.EXPORTEXCEL);// 设置操作类型为导出
        String excelName = FieldsReader.getLevel1AttributeByName(templetePath, tempId, "name");
        if (excelName == null) {
            excelName = tempId;
        }
        logger.info(excelName + "===");
        String opUserName = UserUtil.getUserName();
        oplog.setOpUser(opUserName);
        oplog.setTotalRows(allCount);// 导出条数
        if (zipFile.exists()) {
            oplog.setOpResult(ExcelOpLogConstant.OP_SUCCESS);// 操作失败
        }
        else {
            oplog.setOpResult(ExcelOpLogConstant.OP_FAILURE);// 操作失败
        }
        oplog.setLogDesc("大数据导出" + excelName);
        try {
            excelOpLogService.insert(oplog);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info(zipName);
        return zipName;
    }

    /**
     * 获得excel配置文件路径
     * 
     * @param tempId
     * @return
     * @throws excelTemplates/+excelTempName
     */
    private String getTempletePath(String tempId) {
        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/excelTemplates");
        String excelTempName = tempId + ".xml";
        String templetePath = filePath + FILE_SEPARATOR + excelTempName;
        return templetePath;
    }

    /**
     * 获得生成excel文件的保存目录
     */
    private String getExcelForlderPath(HttpServletRequest request) {
        String forlderName = "/exceltempfile/";
        String realPathDir = request.getSession().getServletContext().getRealPath(forlderName);
        File forlder = new File(realPathDir);
        if (!forlder.exists()) {
            forlder.mkdirs();
        }
        logger.info(forlder.getAbsolutePath());
        return realPathDir;
    }

    @RequestMapping(value = "/excelOpLogPage")
    public ModelAndView excelOpLogPage() {
        ModelMap modelMap = new ModelMap();
        return new ModelAndView("excelOpLogPage", modelMap);
    }

    /**
     * 插入Excel操作日志和错误详情日志
     * 
     * @param errors
     */
    private String insertExcelOpLog(String templetePath, String tempId, List<ExcelOpLogDetail> errors) {
        int errCount = errors.size();// 错误总数
        int opResult = (errCount > 0) ? 0 : 1;

        // 第一步，插入Excel操作日志
        ExcelOpLog opLog = new ExcelOpLog();
        opLog.setOpId(UUIDUtil.getUUID());
        opLog.setOpDate(new Date());
        opLog.setOpType(ExcelOpLogConstant.IMPORTEXCEL);
        opLog.setErrorCount(errCount);
        opLog.setOpResult(opResult);
        String opUserName = "Admin"; // 没有登录限制，先默认一个操作人
        opLog.setOpUser(opUserName);// ============= 获取操作人
        opLog.setDetails(errors);
        String excelName = FieldsReader.getLevel1AttributeByName(templetePath, tempId, "name");
        if (excelName == null) {
            excelName = tempId;
        }
        opLog.setLogDesc("解析导入模板:" + excelName);
        try {
            excelOpLogService.insert(opLog);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        // 第二步，如果有错误，则插入错误详情日志
        if (errCount > 0) {
            // 对错误详情日志list做处理，绑定日志id
            setOpId(errors, opLog.getOpId());
            excelOpLogDetailService.insertExcelOLDBatch(errors);
        }

        return opLog.getOpId();
    }

    /**
     * 设置错误详情项的logId
     * 
     * @param errors
     * @param logId
     */
    private void setOpId(List<ExcelOpLogDetail> errors, String logId) {
        if (null != errors && !errors.isEmpty()) {
            for (int i = 0; i < errors.size(); i++) {
                errors.get(i).setLogId(logId);
            }
        }
    }
}
