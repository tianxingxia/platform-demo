package com.yk.platform.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<T> implements IBaseService<T>
{
    /**
     * 添加一个新的对象
     * @param t
     */
    @Transactional
    public void add(T t)
    {
        getBaseDao().add(t);
    }

    /**
     * 更新对象
     * @param t
     */
    @Transactional
    public void update(T t)
    {
        getBaseDao().update(t);
    }

    /**
     * 批量更新
     * @param list
     */
    @Transactional
    public void batchUpdate(List<T> list)
    {
        if (list == null || list.size() == 0)
            return;
        getBaseDao().batchUpdate(list);
    }

    /**
     * 批量插入对象
     * @param list
     */
    public void batchAdd(List<T> list)
    {
        if (list == null || list.size() == 0)
            return;
        getBaseDao().batchAdd(list);
    }

    /**
     * 根据qo来查询列表
     * @param qo
     * @return
     */
    public List<T> getList(Qo qo)
    {
        // 如果需要分页，则设置分页
        if (qo.getPage() != null)
        {
            qo.getPage().setTotalRecord(getTotal(qo));
        }
        return getBaseDao().getList(qo);
    }

    /**
     * 获取总记录数
     * @return
     */
    public long getTotal(Qo qo)
    {
        return getBaseDao().getTotal(qo);
    }

    /**
     * 根据主键获取实体类
     * @param id
     * @return
     */
    public T get(Serializable id)
    {
        return getBaseDao().get(id);
    }

    /**
     * 通用的根据逐渐删除的方法
     * @param id
     */
    @Transactional
    public void delete(Serializable id)
    {
        getBaseDao().delete(id);
    }

    /**
     * 根据字段名查询唯一值，业务主键查询会经常使用
     * @param fieldName
     * @param value
     * @return
     */
    public T getUniqe(Qo qo)
    {
        List<T> list = getBaseDao().getList(qo);
        if (list.size() == 0)
        {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取dao
     * @return
     */
    public abstract IBaseDao<T> getBaseDao();

}
