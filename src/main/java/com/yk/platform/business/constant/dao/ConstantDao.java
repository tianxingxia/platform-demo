package com.yk.platform.business.constant.dao;

import java.util.List;

import com.yk.platform.business.constant.entity.Constant;
import com.yk.platform.common.IBaseDao;

public interface ConstantDao extends IBaseDao<Constant> {

    /**
     * 根据键得到常量对象
     * @param k
     * @return
     */
    public Constant getConstant(String k);

    /**
     * 查询所有的常量
     * @return
     */
    public List<Constant> getAllConstant();

    /**
     * 插入常量
     * @param constant
     * @return
     */
    public int addConstant(Constant constant);

    /**
     * 更新常量
     * @param constant
     * @return
     */
    public int updateConstant(Constant constant);

    /**
     * 根据k删除常量
     * @param k
     * @return
     */
    public int deleteConstant(String k);
}
