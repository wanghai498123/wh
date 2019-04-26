package cn.com.tass.chsmc.modules.common.dao;

import java.util.List;
import java.util.Map;

import cn.com.tass.chsmc.exception.SqlException;

/**
 * 标题: 通用Dao访问层接口
 * <p>
 * 描述: 通用Dao访问层接口
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-22 下午10:47:34
 * @version 1.0
 */
public interface GenericDao<T> {

	/**
	 * 保存对象
	 * @param o
	 * @throws SqlException
	 */
	public void save(T o) throws SqlException;

	/**
	 * 删除对象
	 * @param  o 要删除的对象
	 * @throws   SqlException Sql异常
	 */
	public void delete(T o) throws SqlException;

	/**
	 * 更新对象
	 * @param o 要更新的对象
	 * @throws SqlException  Sql异常
	 */
	public void update(T o) throws SqlException;
	

	/**
	 * 刷新对象
	 * @param o 要刷新的对象
	 * @throws SqlException  Sql异常
	 */
	public void refresh(T o) throws SqlException;
	
	/**
	 * 根据ID获取一个对象
	 * @param c		对象类型
	 * @param id	对象ID
	 * @return		对象
	 * @throws SqlException Sql异常
	 */
	public T get(int id) throws SqlException;

	/**
	 * 根据条件获取一个对象
	 * @param hql	查询HQL语句
	 * @param param	查询参数
	 * @return		对象
	 * @throws SqlException Sql异常
	 */
	public T get(String hql, Object[] param) throws SqlException;

	/**
	 * 根据条件获取一个对象
	 * @param hql	查询HQL语句
	 * @param param	查询参数
	 * @return		对象
	 * @throws SqlException Sql异常
	 */
	public T get(String hql, List<Object> param) throws SqlException;

	/**
	 * 根据条件获取一个对象
	 * @param hql	查询HQL语句
	 * @param param	查询参数
	 * @return		对象
	 * @throws SqlException Sql异常
	 */
	public T get(String hql, Map<String, Object> param) throws SqlException;
	
	
	/**
	 * 查询列表
	 * @param hql	查询HQL
	 * @return		列表
	 * @throws SqlException Sql异常
	 */
	public List<T> find(String hql) throws SqlException;

	/**
	 * 查询列表
	 * @param hql	查询HQL
	 * @param param 查询参数
	 * @return		列表
	 * @throws SqlException Sql异常
	 */
	public List<T> find(String hql, Object[] param) throws SqlException;

	/**
	 * 查询列表
	 * @param hql	查询HQL
	 * @param param 查询参数
	 * @return		列表
	 * @throws SqlException Sql异常
	 */
	public List<T> find(String hql, List<Object> param) throws SqlException;

	/**
	 * 查询列表
	 * @param hql	查询HQL
	 * @param param 查询参数
	 * @return		列表
	 * @throws SqlException Sql异常
	 */
	public List<T> find(String hql, Map<String, Object> param) throws SqlException;
	
	
	/**
	 * 查询列表(分页)
	 * @param hql	查询HQL
	 * @param param 查询参数
	 * @param page	第几页
	 * @param rows	每页条数
	 * @return		列表
	 * @throws SqlException Sql异常
	 */
	public List<T> find(String hql, Object[] param, Integer page, Integer rows) throws SqlException;

	/**
	 * 查询列表(分页)
	 * @param hql	查询HQL
	 * @param param 查询参数
	 * @param page	第几页
	 * @param rows	每页条数
	 * @return		列表
	 * @throws SqlException Sql异常
	 */
	public List<T> find(String hql, List<Object> param, Integer page, Integer rows) throws SqlException;

	/**
	 * 查询列表(分页)
	 * @param hql	查询HQL
	 * @param param 查询参数
	 * @param page	第几页
	 * @param rows	每页条数
	 * @return		列表
	 * @throws SqlException Sql异常
	 */
	public List<T> find(String hql, Map<String, Object> param, Integer page, Integer rows) throws SqlException;

	
	/**
	 * 根据条件统计总数
	 * @param hql	统计HQL
	 * @return		总数
	 * @throws SqlException Sql异常
	 */
	public Long count(String hql) throws SqlException;

	/**
	 * 根据条件统计总数
	 * @param hql	统计HQL
	 * @param param	参数
	 * @return		总数
	 * @throws SqlException Sql异常
	 */
	public Long count(String hql, Object[] param) throws SqlException;

	/**
	 * 根据条件统计总数
	 * @param hql	统计HQL
	 * @param param	参数
	 * @return		总数
	 * @throws SqlException Sql异常
	 */
	public Long count(String hql, List<Object> param) throws SqlException;

	/**
	 * 根据条件统计总数
	 * @param hql	统计HQL
	 * @param param	参数
	 * @return		总数
	 * @throws SqlException Sql异常
	 */
	public Long count(String hql, Map<String, Object> param) throws SqlException;

	/**
	 * 执行更新HQL
	 * @param hql	更新HQL
	 * @return		更新记录数
	 * @throws SqlException		Sql异常
	 */
	public Integer executeHql(String hql) throws SqlException;

	/**
	 * 执行更新HQL
	 * @param hql	更新HQL
	 * @param param 参数
	 * @return		更新记录数
	 * @throws SqlException Sql异常
	 */
	public Integer executeHql(String hql, Object[] param) throws SqlException;

	/**
	 * 执行更新HQL
	 * @param hql	更新HQL
	 * @param param 参数
	 * @return		更新记录数
	 * @throws SqlException Sql异常
	 */
	public Integer executeHql(String hql, List<Object> param) throws SqlException;

	/**
	 * 执行更新HQL
	 * @param hql	更新HQL
	 * @param param 参数
	 * @return		更新记录数
	 * @throws SqlException Sql异常
	 */
	public Integer executeHql(String hql, Map<String, Object> param) throws SqlException;

	/**
	 * 执行更新SQL语句
	 * @param sql	更新SQL
	 * @param param	参数
	 * @return      更新记录数
	 * @throws SqlException	Sql异常
	 */
	public Integer executeNativeSqlForUpdate(String sql, List<Object> param) throws SqlException;

	/**
	 * 执行查询SQL语句
	 * 
	 * @param sql	查询SQL
	 * @param param	查询参数
	 * @return		查询列表
	 * @throws SqlException	Sql异常
	 */
	@SuppressWarnings({"unchecked" })
	public List executeNativeSqlForQuery(String sql, List<Object> param) throws SqlException;

	/**
	 * 根据条件统计总数
	 * @param sql	统计sql
	 * @param param	参数
	 * @return		总数
	 * @throws SqlException Sql异常
	 */
	public Long countByNativeSql(String sql, List<Object> param) throws SqlException;

	/**
	 * 执行SQL查询并返回指定对象的列表
	 * @param sql		查询SQL
	 * @param param		查询参数
	 * @param cls		返回对象类型
	 * @param page		开始页数	
	 * @param rows		每页数量
	 * @return List
	 * @throws SqlException  SQL异常
	 */
	public List<T> executeNativeSqlForQueryList(String sql, List<Object> param, Class<T> cls, Integer page, Integer rows) throws SqlException;

}
