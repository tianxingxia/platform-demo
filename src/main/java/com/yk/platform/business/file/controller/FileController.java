package com.yk.platform.business.file.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yk.platform.business.constant.service.ConstantService;
import com.yk.platform.business.file.bean.FrontFile;
import com.yk.platform.business.file.service.FileService;
import com.yk.platform.common.AbstractController;
import com.yk.platform.common.ResultBean;
import com.yk.platform.common.util.ResponseUtils;

@Controller
@RequestMapping(value = "/file")
public class FileController extends AbstractController {
    private static final Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private ConstantService constantService;

    /**
     * 文件的上传操作
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FrontFile> frontFiles = fileService.upload(request);
        ResultBean resultBean = getSuccessResultBean();
        resultBean.setData(frontFiles);
        ResponseUtils.setObjectResponse(response, resultBean);
    }

    /**
     * 下载文件
     * 
     * @param response
     * @param request
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, HttpServletRequest request) throws Exception {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        URL url = null;
        try {
            String fileName = URLEncoder.encode(request.getParameter("fileName"), "UTF-8");
            // 目前都是在公共空间，私有的暂时不考虑
            url = new URL(constantService.getConstantValue(ConstantService.PUBLIC_DOWNLOAD_URL) + fileName);
            conn = (HttpURLConnection) url.openConnection();
            inputStream = conn.getInputStream();
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            int i = 0;
            byte b[] = new byte[1024];
            while ((i = inputStream.read(b)) != -1)
                outputStream.write(b, 0, i);
        }
        catch (Exception e) {
            try {
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html; charset=utf-8");
                PrintWriter printWriter = response.getWriter();
                printWriter.println("<script>alert('下载失败');window.close();</script>");
                printWriter.flush();
                printWriter.close();
            }
            catch (IOException e1) {
                logger.error(e1);
            }
            logger.error(e);
        }
        finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (inputStream != null)
                    inputStream.close();
                if (conn != null)
                    conn.disconnect();
            }
            catch (IOException e) {
                logger.error(e);
            }
        }

    }
}
