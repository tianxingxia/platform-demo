package com.yk.platform.common;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<T>
{

    /**
     * 添加一个新的对象
     * @param t
     */
    public void add(T t);

    /**
     * 批量插入对象
     * @param list
     */
    public void batchAdd(List<T> list);

    /**
     * 更新对象
     * @param t
     */
    public void update(T t);

    /**
     * 根据qo来查询列表
     * @param qo
     * @return
     */
    public List<T> getList(Qo qo);

    /**
     * 获取总记录数
     * @return
     */
    public long getTotal(Qo qo);

    /**
     * 根据主键获取实体类
     * @param id
     * @return
     */
    public T get(Serializable id);

    /**
     * 通用的根据逐渐删除的方法
     * @param id
     */
    public void delete(Serializable id);

    /**
     * 根据字段名查询唯一值，业务主键查询会经常使用
     * @param fieldName
     * @param value
     * @return
     */
    public T getUniqe(Qo qo);

    /**
     * 获取dao
     * @return
     */
    public IBaseDao<T> getBaseDao();

    /**
     * 批量更新
     * @param list
     */
    public void batchUpdate(List<T> list);

}
