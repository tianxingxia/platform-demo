package com.yk.platform.common;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {
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
	 * 批量更新
	 * @param list
	 */
	public void batchUpdate(List<T> list);

	/**
	 * 根据qo来查询列表
	 * @param qo
	 * @return
	 */
	public List<T> getList(Qo qo);

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
	 * 获取总记录数
	 * @return
	 */
	public long getTotal(Qo qo);
}
