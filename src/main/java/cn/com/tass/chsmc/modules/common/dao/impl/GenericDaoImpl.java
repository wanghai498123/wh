package cn.com.tass.chsmc.modules.common.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.modules.common.dao.GenericDao;

/**
 * 标题: 通用DAO访问基类实现
 * <p>
 * 描述: 通用DAO访问基类实现
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-22 下午11:08:47
 * @version 1.0
 */
@Repository
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

	@PersistenceContext
	private EntityManager entityManager;

	protected Class<?> daoType;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		this.daoType = (Class) pt.getActualTypeArguments()[0];
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void save(T o) throws SqlException {
		try {

			this.getEntityManager().persist(o);
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public void delete(T o) throws SqlException {
		try {
			this.getEntityManager().remove(o);
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public void update(T o) throws SqlException {
		try {
			this.getEntityManager().merge(o);
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public void refresh(T o) throws SqlException {
		try {
			this.getEntityManager().refresh(o);
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public T get(int id) throws SqlException {
		try {
			return (T) this.getEntityManager().find(this.daoType, id);
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public T get(String hql, Object[] param) throws SqlException {
		try {
			List<T> l = this.find(hql, param, 1, 1);
			if (l != null && l.size() > 0) {
				return l.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public T get(String hql, List<Object> param) throws SqlException {
		try {
			List<T> l = this.find(hql, param, 1, 1);
			if (l != null && l.size() > 0) {
				return l.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public T get(String hql, Map<String, Object> param) throws SqlException {
		try {
			List<T> l = this.find(hql, param, 1, 1);
			if (l != null && l.size() > 0) {
				return l.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql) throws SqlException {
		try {
			return this.getEntityManager().createQuery(hql).getResultList();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object[] param) throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					q.setParameter(i, param[i]);
				}
			}
			return q.getResultList();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, List<Object> param) throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					q.setParameter(i + 1, param.get(i));
				}
			}
			return q.getResultList();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Map<String, Object> param)
			throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.size() > 0) {
				for (Map.Entry<String, Object> m : param.entrySet()) {
					q.setParameter(m.getKey(), m.getValue());
				}
			}
			return q.getResultList();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object[] param, Integer page, Integer rows)
			throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					q.setParameter(i + 1, param[i]);
				}
			}
			
			if (page != null && rows != null && page == 0 && rows == 0) {
				return q.getResultList();
			} else {
				if (page == null || page < 1) {
					page = 1;
				}
				if (rows == null || rows < 1) {
					rows = 10;
				}
				return q.setFirstResult((page - 1) * rows).setMaxResults(rows)
						.getResultList();
			}
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, List<Object> param, Integer page,
			Integer rows) throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					q.setParameter(i + 1, param.get(i));
				}
			}
			
			if (page != null && rows != null && page == 0 && rows == 0) {
				return q.getResultList();
			} else {
				if (page == null || page < 1) {
					page = 1;
				}
				if (rows == null || rows < 1) {
					rows = 10;
				}
				return q.setFirstResult((page - 1) * rows).setMaxResults(rows)
						.getResultList();
			}
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Map<String, Object> param, Integer page,
			Integer rows) throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.size() > 0) {
				for (Map.Entry<String, Object> m : param.entrySet()) {
					q.setParameter(m.getKey(), m.getValue());
				}
			}
			if (page != null && rows != null && page == 0 && rows == 0) {
				return q.getResultList();
			} else {
				if (page == null || page < 1) {
					page = 1;
				}
				if (rows == null || rows < 1) {
					rows = 10;
				}
				return q.setFirstResult((page - 1) * rows).setMaxResults(rows)
						.getResultList();
			}
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public Long count(String hql) throws SqlException {
		try {
			return (Long) this.getEntityManager().createQuery(hql)
					.getSingleResult();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public Long count(String hql, Object[] param) throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					q.setParameter(i + 1, param[i]);
				}
			}
			return (Long) q.getSingleResult();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public Long count(String hql, List<Object> param) throws SqlException {

		return this.count(hql, param.toArray());
	}

	public Long count(String hql, Map<String, Object> param)
			throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.size() > 0) {

				for (Map.Entry<String, Object> m : param.entrySet()) {
					q.setParameter(m.getKey(), m.getValue());
				}
			}
			return (Long) q.getSingleResult();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public Integer executeHql(String hql) throws SqlException {
		try {
			return this.getEntityManager().createQuery(hql).executeUpdate();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public Integer executeHql(String hql, Object[] param) throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					q.setParameter(i + 1, param[i]);
				}
			}
			return q.executeUpdate();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public Integer executeHql(String hql, List<Object> param)
			throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					q.setParameter(i + 1, param.get(i));
				}
			}
			return q.executeUpdate();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public Integer executeHql(String hql, Map<String, Object> param)
			throws SqlException {
		try {
			Query q = this.getEntityManager().createQuery(hql);
			if (param != null && param.size() > 0) {
				for (Map.Entry<String, Object> m : param.entrySet()) {
					q.setParameter(m.getKey(), m.getValue());
				}
			}
			return q.executeUpdate();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public Long countByNativeSql(String sql, List<Object> param)
			throws SqlException {
		try {
			Query q = this.getEntityManager().createNativeQuery(sql);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					q.setParameter(i + 1, param.get(i));
				}
			}
			return Long.parseLong(String.valueOf((BigInteger) q
					.getSingleResult()));
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	public Integer executeNativeSqlForUpdate(String sql, List<Object> param)
			throws SqlException {
		try {
			Query q = this.getEntityManager().createNativeQuery(sql);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					q.setParameter(i + 1, param.get(i));
				}
			}

			return q.executeUpdate();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List executeNativeSqlForQuery(String sql, List<Object> param)
			throws SqlException {
		try {
			Query q = this.getEntityManager().createNativeQuery(sql);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					q.setParameter(i + 1, param.get(i));
				}
			}
			return q.getResultList();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> executeNativeSqlForQueryList(String sql, List<Object> param,
			Class<T> cls, Integer page, Integer rows) throws SqlException {
		try {
			Query q = this.getEntityManager().createNativeQuery(sql, cls);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					q.setParameter(i + 1, param.get(i));
				}
			}
			
			if (page != null && rows != null && page == 0 && rows == 0) {
				return q.getResultList();
			} else {
				if (page == null || page < 1) {
					page = 1;
				}
				if (rows == null || rows < 1) {
					rows = 10;
				}
				return q.setFirstResult((page - 1) * rows).setMaxResults(rows)
						.getResultList();
			}
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}
}
