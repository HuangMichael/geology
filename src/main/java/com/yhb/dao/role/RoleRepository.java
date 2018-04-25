package com.yhb.dao.role;

import com.yhb.domain.role.Role;
import com.yhb.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 角色信息数据接口
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * @param roleName
     * @return
     */
    List<Role> findByRoleNameContainsAndIdIn(String roleName,List<Long> selectedIds);


    /**
     * @param roleName
     * @param pageable 可分页参数
     * @return
     */
    Page<Role> findByRoleNameContains(String roleName, Pageable pageable);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from Role a ")
    List<Long> selectAllIds();

    /**
     * @param id
     * @return 查询不是该id的其他ld列表
     */
    @Query("select a.id from Role a where a.id<>:id")
    List<Long> selectOtherIds(@Param("id") Long id);

    /**
     * @param roleName
     * @param otherRoleIds
     * @return
     */
    Role findByRoleNameAndIdIn(String roleName, List<Long> otherRoleIds);

    /**
     * @param roleId 角色id
     * @return 查询不是该角色用户的用户列表
     */
    List<User> findUserListById(@Param("roleId") Long roleId);

    /**
     * @param roleName
     * @return
     */
    Role findByRoleName(String roleName);

}
