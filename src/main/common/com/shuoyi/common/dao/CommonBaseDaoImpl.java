package com.shuoyi.common.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaosy-c on 2017/8/22.
 */
public class CommonBaseDaoImpl<T> implements ICommomBaseDao<T> {

    private SessionFactory sessionFactory;
    private Class<T> entityClass;
    private String pkname;

    public CommonBaseDaoImpl() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        getPkname();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> querylist(String sHql, HashMap<String, Object> oParams) {
        Query oQuery = getSession().createQuery(sHql);
        if (null != oParams && oParams.isEmpty() == false) {
            Set<String> keys = oParams.keySet();
            for (String string : keys) {
                oQuery.setParameter(string, oParams.get(string));
            }
        }
        return oQuery.list();
    }

    @Override
    public List queryObjectList(String sHql, HashMap<String, Object> oParams) {
        Query oQuery = getSession().createQuery(sHql);

        if (null != oParams && oParams.isEmpty() == false) {
            Set<String> keys = oParams.keySet();
            for (String string : keys) {
                oQuery.setParameter(string, oParams.get(string));
            }
        }
        return oQuery.list();
    }

    @Override
    public List queryObjectListWithLimt(String sHql, HashMap<String, Object> oParams, int limitCount) {
        Query oQuery = getSession().createQuery(sHql);
        oQuery.setFirstResult(0);
        oQuery.setMaxResults(limitCount);

        if (null != oParams && oParams.isEmpty() == false) {
            Set<String> keys = oParams.keySet();
            for (String string : keys) {
                oQuery.setParameter(string, oParams.get(string));
            }
        }
        return oQuery.list();
    }

    @Override
    public void save(T object) {
        getSession().save(object);
    }

    @Override
    public void update(T object) {
        getSession().update(object);
    }

    /**
     * 有则更新无则修改（hibernate根据id是否为null进行判断）
     *
     * @param object
     */
    public void saveOrUpdate(T object) {
        getSession().saveOrUpdate(object);
    }

    @Override
    public void delete(T object) {
        getSession().delete(object);
    }

    @Override
    public int executeUpdate(String sHql, HashMap<String, Object> oParams) {
        Query oQuery = getSession().createQuery(sHql);
        if (null != oParams && oParams.isEmpty() == false) {
            Set<String> keys = oParams.keySet();
            for (String string : keys) {
                oQuery.setParameter(string, oParams.get(string));
            }
        }
        return oQuery.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T find(Integer id) {
        return (T) getSession().get(entityClass, id);
    }

    @Override
    public int getCount(String sHql, HashMap<String, Object> oParams) {
        Query oQuery = getSession().createQuery(sHql);
        if (null != oParams && oParams.isEmpty() == false) {
            Set<String> keys = oParams.keySet();
            for (String sKey : keys) {
                oQuery.setParameter(sKey, oParams.get(sKey));
            }
        }
        List resultList = oQuery.list();
        for (Object object : resultList) {
            Long oCount = (Long) object;
            if (oCount > 0) {
                return Integer.parseInt(oCount + "");
            }
        }
        return 0;
    }

    @Override
    public Object getSingleResult(String sql, HashMap<String, Object> paramsMap) {
        Query oQuery = getSession().createQuery(sql);
        if (null != paramsMap && paramsMap.isEmpty() == false) {
            Set<String> oKeysSet = paramsMap.keySet();
            for (String sKey : oKeysSet) {
                oQuery.setParameter(sKey, paramsMap.get(sKey));
            }
        }
        oQuery.setMaxResults(1);
        List resultList = oQuery.list();
        for (Object object : resultList) {
            return object;
        }
        return null;
    }

    /**
     * 获取主键名称
     *
     * @return
     */
    public String getPkname() {
        Field[] fields = this.entityClass.getDeclaredFields();// 反射类字段
        for (Field field : fields) {
            field.isAnnotationPresent(Id.class);
            this.pkname = field.getName();
            break;
        }
        return pkname;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(Serializable id) {
        return (T) getSession().get(entityClass, id);
    }

    @Override
    public int getCountsBySql(String sql, HashMap<String, Object> oParams) {
        Query oQuery = getSession().createSQLQuery(sql);
        if (null != oParams && oParams.isEmpty() == false) {
            Set<String> keys = oParams.keySet();
            for (String sKey : keys) {
                oQuery.setParameter(sKey, oParams.get(sKey));
            }
        }
        List resultList = oQuery.list();
        for (Object object : resultList) {
            BigInteger oCount = (BigInteger) object;
            if (oCount.intValue() > 0) {
                return oCount.intValue();
            }
        }
        return 0;
    }

    @Override
    public Page getPageBySql(String countSql, String resultSql, int pageNo, int pageSize,
                             HashMap<String, Object> oParams) {
        int nCount = getCountsBySql(countSql, oParams);
        Page oPage = new Page(nCount, pageNo, pageSize);
        if (nCount > 0) {
            Query oQuery = getSession().createSQLQuery(resultSql);
            if (null != oParams && oParams.isEmpty() == false) {
                Set<String> oKeys = oParams.keySet();
                for (String sKey : oKeys) {
                    oQuery.setParameter(sKey, oParams.get(sKey));
                }
            }
            if (nCount > pageSize) {
                oQuery.setFirstResult(oPage.getStartNo()).setMaxResults(oPage.getPageSize());
            }
            oQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List resultList = oQuery.list();
            oPage.setResult(resultList);
        }
        return oPage;
    }

    @Override
    public List<?> queryObjectListBySql(String sql, HashMap<String, Object> oParams, Class<?> beanClsses) {
        Query oQuery = getSession().createSQLQuery(sql);
        if (null != oParams && oParams.isEmpty() == false) {
            Set<String> keys = oParams.keySet();
            for (String string : keys) {
                oQuery.setParameter(string, oParams.get(string));
            }
        }
        if (null != beanClsses) {
            oQuery.setResultTransformer(Transformers.aliasToBean(beanClsses));
        }
        return oQuery.list();
    }

    @Override
    public Page getPageBySqlClass(String countSql, String resultSql, int pageNo, int pageSize,
                                  HashMap<String, Object> oParams, Class<?> beanClsses) {

        int nCount = getCountsBySql(countSql, oParams);
        Page oPage = new Page(nCount, pageNo, pageSize);
        if (nCount > 0) {
            Query oQuery = getSession().createSQLQuery(resultSql);
            if (null != oParams && oParams.isEmpty() == false) {
                Set<String> oKeys = oParams.keySet();
                for (String sKey : oKeys) {
                    oQuery.setParameter(sKey, oParams.get(sKey));
                }
            }
            if (nCount > pageSize) {
                oQuery.setFirstResult(oPage.getStartNo()).setMaxResults(oPage.getPageSize());
            }
            if (null != beanClsses) {
                oQuery.setResultTransformer(Transformers.aliasToBean(beanClsses));
            }
            List<?> resultList = oQuery.list();
            oPage.setResult(resultList);
        }
        return oPage;
    }
}
