package com.yk.platform.business.constant.service;

import java.util.List;

import com.yk.platform.business.constant.entity.Constant;

public interface ConstantService {

    // 七牛公共空间下载地址映射常量
    public static final String PUBLIC_DOWNLOAD_URL = "public_download_url";

    /**
     * 根据KEY来获得Constant(从缓存中获取)
     * 
     * @param key
     * @return
     */
    public Constant getConstant(String key);

    /**
     * 根据KEY来获得Constant的value(从缓存中获取)
     * 
     * @param key
     * @return
     */
    public String getConstantValue(String key);

    /**
     * 获得所有的常量数据
     * 
     * @return
     */
    public List<Constant> getAllConstant();

    /**
     * 加载常量信息到缓存(redis)
     */
    public void loadConstant2Cache();

    /**
     * 插入常量
     * 
     * @param constant
     * @return
     */
    public int addConstant(Constant constant);

    /**
     * 从数据库取数据
     * 
     * @param k
     * @return
     */
    public Constant getConstantFromDB(String k);

    /**
     * 更新常量
     * 
     * @param constant
     * @return
     */
    public int updateConstant(Constant constant);

    /**
     * 删除常量
     * 
     * @param k
     * @return
     */
    public int deleteConstant(String k);

    /**
     * @description 判断是否为4G手表的设备
     * @author 阳凯
     * @date 2017年10月30日 下午4:15:15
     * @param device
     * @return
     */
    boolean is4GDevice(String device);

    /**
     * @description 是否为4G的机型
     * @author 阳凯
     * @date 2017年11月10日 上午9:43:01
     * @param model
     * @return
     */
    boolean is4GModel(String model);
}
