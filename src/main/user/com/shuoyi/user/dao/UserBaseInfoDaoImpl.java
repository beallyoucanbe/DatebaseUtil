package com.shuoyi.user.dao;

import com.shuoyi.common.dao.CommonBaseDaoImpl;
import com.shuoyi.user.po.User;
import com.shuoyi.user.po.UserBaseInfo;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhaosy-c on 2017/8/23.
 */
@Repository
public class UserBaseInfoDaoImpl extends CommonBaseDaoImpl<UserBaseInfo> implements IUserBaseInfoDao<UserBaseInfo> {

    @Autowired
    public void setSessionFactory(@Qualifier("userSessionFactory") SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public UserBaseInfo findOneByUserId(int userId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.userId=:userId ");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("userId", userId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        for (UserBaseInfo userBaseInfo : list) {
            return userBaseInfo;
        }
        return null;
    }


    @Override
    public UserBaseInfo findOneByGldUserId(String gldUserId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.GLDUserID=:gldUserId and u.deleted = 0");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("gldUserId", gldUserId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        for (UserBaseInfo userBaseInfo : list) {
            return userBaseInfo;
        }
        return null;
    }

    @Override
    public UserBaseInfo findAdminOneByCompanyId(Integer comapnyId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.companyId=:companyId and u.userType = 1");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("companyId", comapnyId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        for (UserBaseInfo userBaseInfo : list) {
            return userBaseInfo;
        }
        return null;
    }

    @Override
    public List<UserBaseInfo> findListByCompanyId(int companyId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.companyId=:companyId and u.deleted = 0");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("companyId", companyId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        return list;

    }

    //包含离职用户
    @Override
    public List<UserBaseInfo> findAllListByCompanyId(int companyId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.companyId=:companyId");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("companyId", companyId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        return list;
    }

    @Override
    public UserBaseInfo findOneByCompanyId(String gldUserId, int companyId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.GLDUserID=:gldUserId and u.companyId=:companyId and u.deleted = 0");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("gldUserId", gldUserId);
        oParams.put("companyId", companyId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        for (UserBaseInfo userBaseInfo : list) {
            return userBaseInfo;
        }
        return null;
    }

    @Override
    public UserBaseInfo findOneByCompanyId(int creator, int companyId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.userId=:creator and u.companyId=:companyId and u.deleted = 0");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("creator", creator);
        oParams.put("companyId", companyId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        for (UserBaseInfo userBaseInfo : list) {
            return userBaseInfo;
        }
        return null;
    }

    //查询时包含离职用户
    @Override
    public UserBaseInfo nfindOneByCompanyId(String gldUserId, int companyId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.GLDUserID=:gldUserId and u.companyId=:companyId ");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("gldUserId", gldUserId);
        oParams.put("companyId", companyId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        for (UserBaseInfo userBaseInfo : list) {
            return userBaseInfo;
        }
        return null;
    }

    //逻辑删除用户
    @Override
    public boolean LogicDelUser(int userId) {
        String sUpdateSql = "update UserBaseInfo u set u.deleted=1 where u.userId = :userId";
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("userId", userId);
        int nCount = super.executeUpdate(sUpdateSql, oParams);
        return nCount == 1 ? true : false;
    }

    //还原逻辑删除用户
    @Override
    public boolean RecoverDelUser(int userId) {
        String sUpdateSql = "update UserBaseInfo u set u.deleted=0 where u.userId = :userId";
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("userId", userId);
        int nCount = super.executeUpdate(sUpdateSql, oParams);
        return nCount == 1 ? true : false;
    }

    @Override
    public List<UserBaseInfo> findOneByUserType(int userType, int companyId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.userType=:userType and u.companyId=:companyId and u.deleted = 0");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("userType", userType);
        oParams.put("companyId", companyId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        return list;
    }

    @Override
    public List<UserBaseInfo> findListByGldUserIds(List<String> gldUserIds, int companyId) {
        if (null == gldUserIds || gldUserIds.size() == 0) {
            return null;
        }
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.GLDUserID in(:gldUserIds) and u.companyId=:companyId and u.deleted = 0");
        Query query = getSession().createQuery(oHql.toString());
        query.setParameter("companyId", companyId);
        query.setParameterList("gldUserIds", gldUserIds);
        return query.list();
    }

    @Override
    public List<UserBaseInfo> getListByGldUserId(List<String> gldUserIdList) {
        if (null == gldUserIdList || gldUserIdList.size() == 0) {
            return null;
        }
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.GLDUserID in(:gldUserIds) and u.deleted = 0");
        Query query = getSession().createQuery(oHql.toString());
        query.setParameterList("gldUserIds", gldUserIdList);
        return query.list();
    }


    @Override
    public List<UserBaseInfo> findListByUserIds(List<Integer> userIds) {
        if (null == userIds || userIds.size() == 0) {
            return null;
        }
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.userId in(:userIds) and u.deleted = 0");
        Query query = getSession().createQuery(oHql.toString());
        query.setParameterList("userIds", userIds);
        return query.list();
    }

    @Override
    public List<UserBaseInfo> getListByRoleId(int companyId, int roleId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("select u from UserBaseInfo u , Rolerf role where u.userId =role.userId and u.companyId=:companyId and u.deleted = 0 and role.roleId=:roleId");
        Query query = getSession().createQuery(oHql.toString());
        query.setParameter("companyId", companyId);
        query.setParameter("roleId", roleId);
        // 类型强转
        return query.list();
//		for (int i = 0; i < oBaseInfoList.size(); i++) {
//			Object[] objectArr = (Object[]) (oBaseInfoList.get(i));
//			for (int j = 0; j < objectArr.length; j++) {
//				if (objectArr[j] instanceof UserBaseInfo) {
//					oReturnList.add((UserBaseInfo) objectArr[j]);
//				}
//			}
//		}
//		return oReturnList;
    }

    @Override
    public UserBaseInfo getRemoveUser(int companyId, String gldUserId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.GLDUserID=:gldUserId and u.companyId=:companyId and u.deleted = 1");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("gldUserId", gldUserId);
        oParams.put("companyId", companyId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        for (UserBaseInfo userBaseInfo : list) {
            return userBaseInfo;
        }
        return null;
    }

    @Override
    public UserBaseInfo getRemoveUserById(int userId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.userId=:userId ");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("userId", userId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        for (UserBaseInfo userBaseInfo : list) {
            return userBaseInfo;
        }
        return null;
    }

    @Override
    public List<UserBaseInfo> getDelUserByCompanyId(int companyId) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("from UserBaseInfo u where u.companyId=:companyId and u.deleted = 1");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("companyId", companyId);
        List<UserBaseInfo> list = super.querylist(oHql.toString(), oParams);
        return list;
    }

    @Override
    public List<Map<String, Object>> getUserNamesByUserIdAndCompanyId(Integer companyid, Set<Integer> userIds) {
        String sHql = "select new map(u.userId as userId, dict.account as account, dict.userName as userName, u.gccpDisplayName as gccpDisplayName) from UserBaseInfo u , GldUserDict dict ";
        sHql += " where u.GLDUserID=dict.GLDUserID ";
        if (companyid > 0) {
            sHql += " and u.companyId=:companyId and u.userId in :userIds and u.deleted = 0";
        } else {
            sHql += " and u.userId in :userIds and u.deleted = 0";
        }
        Query oQuery = getSession().createQuery(sHql);
        if (companyid > 0) {
            oQuery.setParameter("companyId", companyid);
        }
        oQuery.setParameterList("userIds", userIds);
        return oQuery.list();

    }

    @Override
    public List<HashMap<String, Object>> getUsersWithRole(Integer companyID) {
        StringBuffer oHql = new StringBuffer();
        oHql.append("select new map(u.userId as userId,dict.account as account,dict.userName as userName,");
        oHql.append("dict.fullUserName as fullUserName,dict.phoneNum as phoneNum,dict.email as email,");
        oHql.append("r.roleId as roleId,u.userType as userType,u.remark as remark,u.gccpDisplayName as gccpDisplayName)");
        oHql.append("from UserBaseInfo u,Rolerf r ,GldUserDict dict where u.userId=r.userId and u.companyId=:companyID AND Deleted=0 ");
        oHql.append(" and   u.GLDUserID=dict.GLDUserID");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("companyID", companyID);
        return super.queryObjectList(oHql.toString(), oParams);
    }

    @Override
    public List<User> findUserListByCompanyId(Integer companyID) {
        StringBuffer oSql = new StringBuffer();
        oSql.append("select new com.glodon.gbq.user.po.User(us.userId,us.companyId,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append(" from UserBaseInfo us,GldUserDict gl where gl.GLDUserID = us.GLDUserID and us.companyId = :companyId and us.deleted = 0");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("companyId", companyID);
        return super.queryObjectList(oSql.toString(), oParams);
    }

    @Override
    public User findOneUserByUserId(int userId) {
        StringBuffer oSql = new StringBuffer();
        oSql.append("select new User(us.userId,us.companyID,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append(" from UserBaseInfo us,GldUserDict gl where gl.GLDUserID = us.GLDUserID and us.userId = :userId ");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("userId", userId);
        return (User) super.getSingleResult(oSql.toString(), oParams);
    }

    @Override
    public User findAdminOneUserByCompanyId(Integer comapnyId) {
        StringBuffer oSql = new StringBuffer();
        oSql.append("select new com.glodon.gbq.user.po.User(us.userId,us.companyId,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append(" from UserBaseInfo us,GldUserDict gl where gl.GLDUserID = us.GLDUserID and us.companyId = :companyId and us.userType = 1");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("companyId", comapnyId);
        return (User) super.getSingleResult(oSql.toString(), oParams);
    }

    @Override
    public User getRemoveUserInfoById(int userId) {
        StringBuffer oSql = new StringBuffer();
        oSql.append("select new com.glodon.gbq.user.po.User(us.userId,us.companyId,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append(" from UserBaseInfo us,GldUserDict gl where gl.GLDUserID = us.GLDUserID and us.userId = :userId");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("userId", userId);
        List<Map<String, Object>> list = super.queryObjectList(oSql.toString(), oParams);
        return (User) super.getSingleResult(oSql.toString(), oParams);
    }

    @Override
    public User findOneUserByAccount(String account) {
        StringBuffer oSql = new StringBuffer();
        oSql.append("select new com.glodon.gbq.user.po.User(us.userId,us.companyId,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append(" from UserBaseInfo us,GldUserDict gl where gl.GLDUserID = us.GLDUserID and gl.account = :account");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("account", account);
        return (User) super.getSingleResult(oSql.toString(), oParams);
    }

    @Override
    public User nfindOneUserByCompanyId(String gldUserId, int companyId) {
        StringBuffer oSql = new StringBuffer();
        oSql.append("select new com.glodon.gbq.user.po.User(us.userId,us.companyId,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append(" from UserBaseInfo us,GldUserDict gl where gl.GLDUserID = us.GLDUserID and us.GLDUserID = :GLDUserID and us.companyId = :companyId");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("GLDUserID", gldUserId);
        oParams.put("companyId", companyId);
        return (User) super.getSingleResult(oSql.toString(), oParams);
    }

    @Override
    public User findOneUserByGldUserId(String gldUserId) {
        StringBuffer oSql = new StringBuffer();
        oSql.append("select new com.glodon.gbq.user.po.User(us.userId,us.companyId,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append(" from UserBaseInfo us,GldUserDict gl where gl.GLDUserID = us.GLDUserID and us.GLDUserID = :GLDUserID  and us.deleted = 0");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("GLDUserID", gldUserId);
        return (User) super.getSingleResult(oSql.toString(), oParams);
    }

    @Override
    public User getRemoveUserInfo(int companyId, String gldUserId) {
        StringBuffer oSql = new StringBuffer();
        oSql.append("select new com.glodon.gbq.user.po.User(us.userId,us.companyId,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append("  from UserBaseInfo us,GldUserDict gl where gl.GLDUserID = us.GLDUserID and us.GLDUserID = :GLDUserID and us.companyId = :companyId  and us.deleted = 1");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("GLDUserID", gldUserId);
        oParams.put("companyId", companyId);
        return (User) super.getSingleResult(oSql.toString(), oParams);
    }

    @Override
    public User findOneUserByCompanyId(String gldUserId, int companyId) {
        StringBuffer oSql = new StringBuffer();
        oSql.append("select new com.glodon.gbq.user.po.User(us.userId,us.companyId,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append(" from UserBaseInfo us,GldUserDict gl where gl.GLDUserID = us.GLDUserID and us.GLDUserID = :GLDUserID and us.companyId = :companyId and us.deleted = 0");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("GLDUserID", gldUserId);
        oParams.put("companyId", companyId);
        return (User) super.getSingleResult(oSql.toString(), oParams);
    }

    @Override
    public List<User> getUsersByRoleId(int companyId, int roleId) {
        StringBuffer oSql = new StringBuffer();
//		Integer userId, Integer companyId, String gLDUserID, String account, String gccpDisplayName,
//		String userName, String fullUserName, String phoneNum, String passwordMobile, String email, us.deleted,us.userType, us.remark
        oSql.append("select new com.glodon.gbq.user.po.User(us.userId,us.companyId,us.GLDUserID,gl.account,us.gccpDisplayName,gl.userName,gl.fullUserName,gl.phoneNum,gl.passwordMobile,gl.email,us.deleted,us.userType, us.remark)")
                .append(" from UserBaseInfo us,GldUserDict gl,Rolerf role where gl.GLDUserID = us.GLDUserID and us.userId =role.userId and us.companyId = :companyId and us.deleted = 0  and role.roleId=:roleId");
        HashMap<String, Object> oParams = new HashMap<String, Object>();
        oParams.put("companyId", companyId);
        oParams.put("roleId", roleId);
        return super.queryObjectList(oSql.toString(), oParams);
    }

}
