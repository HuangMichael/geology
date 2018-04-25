package com.yhb.dao.user;

import com.yhb.domain.location.Location;
import com.yhb.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 用户信息数据接口
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @param userName
     * @return
     */
    List<User> findByUserNameContainsAndIdIn(String userName, List<Long> selectedIds);


    /**
     * @param userName 根据用户名查询
     * @return
     */
    User findByUserName(String userName);


    /**
     * @param userName 用户名
     * @param password 密码密文
     * @param status   用户状态
     * @return
     */
    User findByUserNameAndPasswordAndStatus(String userName, String password, String status);


    /**
     * @param userName
     * @param pageable 可分页参数
     * @return
     */
    Page<User> findByUserNameContains(String userName, Pageable pageable);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from User a ")
    List<Long> selectAllIds();

    /**
     * @param id
     * @return 查询不是该id的其他ld列表
     */
    @Query("select a.id from User a where a.id<>:id")
    List<Long> selectOtherIds(@Param("id") Long id);

    /**
     * @param userName
     * @param otherUserIds
     * @return
     */
    User findByUserNameAndIdIn(String userName, List<Long> otherUserIds);

    /**
     * @param userIds
     * @return 查询不属于该用户id列表的用户列表
     */
    List<User> findByIdNotInOrderById(List<Long> userIds);


    /**
     * @param locationId
     * @return 根据位置查询人员
     */
    List<User> findByLocationIdOrderById(Long locationId);


    /**
     * @param locationId
     * @return 查询不在该位置下的人员
     */

    @Query(value = "select u  from User u where (u.location.id <>:locationId or u.location.id is null) and u.status ='1'")
    List<User> findByLocationIsNotOrLocationIsNull(@Param("locationId") Long locationId);

    /**
     * @param locationId
     * @param userName
     * @return 根据位置id和用户名查询用户列表
     */
    List<User> findByLocationIdAndUserNameContainsOrderById(Long locationId,String userName);
}
