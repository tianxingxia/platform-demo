package com.yk.platform.business.constant.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yk.platform.business.constant.dao.ConstantDao;
import com.yk.platform.business.constant.entity.Constant;
import com.yk.platform.business.constant.service.ConstantService;
import com.yk.platform.common.util.StringUtil;

@Service
public class ConstantServiceImpl implements ConstantService {

    private static Logger logger = Logger.getLogger(ConstantServiceImpl.class);

    @Autowired
    ConstantDao constantDao;

    private static Map<String, String> constantCache = new ConcurrentHashMap<>();

    @Override
    public Constant getConstant(String k) {
        Constant constant = new Constant();
        constant.setK(k);
        String v = constantCache.get(k);
        if (StringUtil.isNull(v)) {
            v = constantDao.getConstant(k).getV();
            constantCache.put(k, v);
        }
        constant.setV(v);
        return constant;
    }

    /**
     * 根据KEY来获得Constant的value(从缓存中获取)
     * @param key
     * @return
     */
    public String getConstantValue(String k) {
        return constantDao.getConstant(k).getV();
    }

    /**
     * 获得所有的常量数据
     * @return
     */
    public List<Constant> getAllConstant() {
        return constantDao.getAllConstant();
    }

    @Override
    public void loadConstant2Cache() {
        List<Constant> constantList = constantDao.getAllConstant();
        for (Constant constant : constantList) {
            constantCache.put(constant.getK(), constant.getV());
        }
    }

    @Override
    @Transactional
    public int addConstant(Constant constant) {
        return constantDao.addConstant(constant);
    }

    @Override
    public int deleteConstant(String k) {
        return constantDao.deleteConstant(k);
    }

    @Override
    public Constant getConstantFromDB(String k) {
        return constantDao.getConstant(k);
    }

    @Override
    public int updateConstant(Constant constant) {
        return constantDao.updateConstant(constant);
    }

    @Override
    public boolean is4GDevice(String device) {
        String fGDeviceStr = getConstantValue("4G_DEVICE");
        fGDeviceStr = fGDeviceStr.replace("，", ",");
        String[] fGDeviceArr = fGDeviceStr.split(",");
        for (String fGDevice : fGDeviceArr) {
            if (device.equals(fGDevice)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean is4GModel(String model) {
        String fGModelStr = getConstantValue("4G_MODEL");
        fGModelStr = fGModelStr.replace("，", ",");
        String[] fGModelArr = fGModelStr.split(",");
        for (String fGModel : fGModelArr) {
            if (model.equals(fGModel)) {
                return true;
            }
        }
        return false;
    }
}
