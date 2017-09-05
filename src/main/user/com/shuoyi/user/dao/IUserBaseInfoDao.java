package com.shuoyi.user.dao;

import com.shuoyi.common.dao.ICommomBaseDao;
import com.shuoyi.user.po.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhaosy-c on 2017/8/23.
 */
public interface IUserBaseInfoDao<UserBaseInfo> extends ICommomBaseDao<UserBaseInfo>{

        public UserBaseInfo findOneByUserId(int userId);
        public UserBaseInfo findOneByGldUserId(String gldUserId);
        public UserBaseInfo findAdminOneByCompanyId(Integer comapnyId);
        /**
         * companyId对应所有的用户(未离职)
         *  @param companyId
         *  @return
         **/
        public List<UserBaseInfo> findListByCompanyId(int companyId);

        /**
         * companyId对应所有的用户(包含已离职)
         *  @param companyId
         *  @return
         **/
        public List<UserBaseInfo> findAllListByCompanyId(int companyId);

        /**
         * 	gldUsrId和CompanyID查找用户(未离职)
         *  @param gldUserId
         *  @param companyId
         *  @return
         **/
        public UserBaseInfo findOneByCompanyId(String gldUserId,int companyId);

        /**
         *  通过广联云id集合返回UserBaseInfo集合
         *  @param gldUserIdList
         *  @return
         **/
        public List<UserBaseInfo> getListByGldUserId(List<String> gldUserIdList);

        /**
         *	gldUsrId和CompanyID查找用户(未包含已离职)
         *  @param gldUserId
         *  @param companyId
         *  @return
         **/
        public UserBaseInfo nfindOneByCompanyId(String gldUserId,int companyId);
        /**
         * 	creator和CompanyID查找用户(未离职)
         *  @param creator
         *  @param companyId
         *  @return
         **/
        public UserBaseInfo findOneByCompanyId(int creator,int companyId);
        //逻辑删除用户
        /**
         * 通过UserID逻辑删除用户
         *  @param userId
         **/
        public boolean LogicDelUser(int userId);
        /**
         * 通过UserID逻辑还原用户
         *  @param userId
         **/
        public boolean RecoverDelUser(int userId);

        /**
         * companyId对应所有的离职用户
         *  @param companyId
         *  @return
         **/
        public List<UserBaseInfo> getDelUserByCompanyId(int companyId);
        /**
         *  通过UserType和CompanyID查找用户(未离职)
         *  @param userType
         *  @param companyId
         *  @return
         **/
        public List<UserBaseInfo> findOneByUserType(int userType,int companyId);
        /**
         * 	通过gldUserIds集合和CompanyID查找用户(未离职)
         *  @param gldUserIds
         *  @param companyId
         *  @return
         **/
        public List<UserBaseInfo> findListByGldUserIds(List<String> gldUserIds,int companyId);
        /**
         * 	通过creator集合和CompanyID查找用户(未离职)
         *  @param userIds
         *  @return
         **/
        public List<UserBaseInfo> findListByUserIds(List<Integer> userIds);
        /**
         * 	通过roleId集合和CompanyID查找用户(未离职)
         *  @param companyId
         *  @param roleId
         *  @return
         **/
        public List<UserBaseInfo> getListByRoleId(int companyId,int roleId);

        /**
         * 三表关联查库
         * @param companyId
         * @param roleId
         * @return
         */
        public List<User> getUsersByRoleId(int companyId, int roleId);
        //查询离职用户
        /**
         * 	通过companyId集合和gldUserId查找离职用户
         *  @param companyId
         *  @param gldUserId
         *  @return
         **/
        public UserBaseInfo getRemoveUser(int companyId,String gldUserId);

        /**
         * 通过userId查找离职用户
         *  @param userId
         *  @return
         **/
        public UserBaseInfo getRemoveUserById(int userId);

        public  List<Map<String, Object>> getUserNamesByUserIdAndCompanyId(Integer companyid, Set<Integer> userIds);

        public List<HashMap<String, Object>> getUsersWithRole(Integer companyID);

        /**
         * 通过companyId查找公司下所有用户
         * @param companyID
         * @return
         */
        public List<User> findUserListByCompanyId(Integer companyID);

        /**
         * 通过userId查找用户信息
         * @param userId
         * @return
         */
        public User findOneUserByUserId(int userId);

        /**
         * 通过companyId查找admin用户信息
         * @param comapnyId
         * @return
         */
        public User findAdminOneUserByCompanyId(Integer comapnyId);

        /**
         * 根据userId查找离职用户信息
         * @param userId
         * @return
         */
        public User getRemoveUserInfoById(int userId);

        /**
         * 根据account查找用户信息
         * @param account
         * @return
         */
        public User findOneUserByAccount(String account);

        /**
         *	gldUsrId和CompanyID查找用户(未包含已离职)
         *  @param gldUserId
         *  @param companyId
         *  @return
         **/
        public User nfindOneUserByCompanyId(String gldUserId,int companyId);

        /**
         *
         * @param gldUserId
         * @return
         */
        public User findOneUserByGldUserId(String gldUserId);

        /**
         * 	通过companyId集合和gldUserId查找离职用户
         *  @param companyId
         *  @param gldUserId
         *  @return
         **/
        public User getRemoveUserInfo(int companyId,String gldUserId);

        /**
         * 	gldUsrId和CompanyID查找用户(未离职)
         *  @param gldUserId
         *  @param companyId
         *  @return
         **/
        public User findOneUserByCompanyId(String gldUserId,int companyId);



}
