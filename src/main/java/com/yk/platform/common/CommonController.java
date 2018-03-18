package com.yk.platform.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yk.platform.common.util.ResponseUtils;

import io.swagger.annotations.Api;

@Api(tags = "公共接口处理类", value = "公共接口集合")
@Controller
@RequestMapping(value = "/common")
public class CommonController extends AbstractController {

    @Value("${cas.moduleId}")
    private String moduleId;

    @RequestMapping(value = "/currentuser", method = RequestMethod.GET)
    public void getCurtrentUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultBean resultBean = getSuccessResultBean();
        resultBean.setData(ResponseUtils.getUserInfo());
        ResponseUtils.setObjectResponse(response, resultBean);
    }

    /**
    * @description js中通过接口获取moduleId
    * @author 阳凯
    * @date 2017年10月21日 下午3:49:23
    * @param request
    * @param response
    * @throws Exception
    */
    @RequestMapping(value = "/jsconfig", method = RequestMethod.GET)
    public void getJsConfigValue(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultBean resultBean = getSuccessResultBean();
        Map<String, String> map = new HashMap<>();
        map.put("moduleId", moduleId);
        resultBean.setData(map);
        ResponseUtils.setObjectResponse(response, resultBean);
    }
}
