package com.yhb.service.role;

import com.yhb.dao.role.RoleRepository;
import com.yhb.dao.user.UserRepository;
import com.yhb.domain.role.Role;
import com.yhb.domain.user.User;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.RedisUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 角色业务类
 */
@Service
@Slf4j
public class RoleService extends BaseService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonDataService commonDataService;


    /**
     * @param role 角色信息
     * @return 保存角色信息
     */
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    /**
     * @param roleList 批量删除
     */
    public void deleteInBatch(List roleList) {
        roleRepository.deleteInBatch(roleList);
    }


    /**
     * @param id 根据ID删除角色信息
     */
    public ReturnObject delete(Long id) {
        Role role = roleRepository.findOne(id);
        if (role == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的角色不存在！");
        } else {
            List<User> users = role.getUserList();
            if (users != null && users.size() > 0) {
                return commonDataService.getReturnType(false, "", "该角色有关联信息不能删除！");
            }
            try {
                roleRepository.delete(role);
                Role role1 = roleRepository.findOne(id);
                return commonDataService.getReturnType(role1 == null, "角色信息删除成功!", "角色信息删除失败，请联系管理员!");
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
                return commonDataService.getReturnType(true, "角色信息删除成功!", "角色信息删除失败，请联系管理员!");
            }
        }
    }

    /**
     * @param id
     * @return 根据id查询角色信息
     */
    public Role findById(Long id) {
        return roleRepository.findOne(id);
    }

    /**
     * @param roleName
     * @return
     */
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    /**
     * @param id
     * @param roleName
     * @return
     */
    public Role findRoleDuplicateByRoleNameAndId(Long id, String roleName) {
        //查询不是该id的其他角色id列表
        List<Long> otherRoleIds = selectOtherIds(id);
        return roleRepository.findByRoleNameAndIdIn(roleName, otherRoleIds);
    }

    /**
     * @param id
     * @return 查询不是该id的其他ld列表
     */
    public List<Long> selectOtherIds(Long id) {
        return roleRepository.selectOtherIds(id);
    }

    /**
     * @return 查询所有的角色信息
     */

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return roleRepository.selectAllIds();
    }


    /**
     * 给某个角色添加一些用户
     *
     * @param usersId 用户id字符串 ，分割
     * @param roleId  角色id
     * @return 是否添加成功
     */
    public Boolean addUsers(String usersId, Long roleId) {
        Role role = roleRepository.findOne(roleId);
        if (role != null) {
            List<User> userList = role.getUserList();//该角色旧的用户列表
            List<User> newUserList = new ArrayList<User>();
            //先把原来的用户列表赋给新列表
            for (User u : userList) {
                newUserList.add(u);
            }
            String[] userIdArray = usersId.split(",");
            for (String userIdStr : userIdArray) {
                Long userId = Long.parseLong(userIdStr);
                User user = userRepository.findOne(userId);
                newUserList.add(user);
                Object str = RedisUtils.get("userMenus" + userId);
                if (str != null) {
                    RedisUtils.del("userMenus" + userId);
                }
            }
            role.setUserList(newUserList);
            role = roleRepository.save(role);
            return userList.size() < role.getUserList().size();
        } else {
            return false;
        }
    }

    /**
     * 给某个角色添加一些用户
     *
     * @param usersId 用户id字符串 ，分割
     * @param roleId  角色id
     * @return 是否添加成功
     */
    public Boolean removeUsers(String usersId, Long roleId) {
        Role role = roleRepository.findOne(roleId);
        boolean result = false;
        if (role != null) {
            List<User> userList = role.getUserList();//该角色旧的用户列表
            List<User> newUserList = new ArrayList<User>();
            String[] userIdArray = usersId.split(",");
            for (String userIdStr : userIdArray) {
                Long userId = Long.parseLong(userIdStr);
                User user = userRepository.findOne(userId);
                newUserList.add(user);
                Object str = RedisUtils.get("userMenus" + userId);
                if (str != null) {
                    RedisUtils.del("userMenus" + userId);
                }
            }
            result = userList.removeAll(newUserList);
            role.setUserList(userList);
            roleRepository.save(role);
        }
        return result;
    }


    /**
     * @return 查询不是该角色用户的用户列表
     */
    public List<User> findUserListNotOfRole(Long roleId) {
        List<User> userList = new ArrayList<User>();
        Role role = roleRepository.findOne(roleId);
        if (role != null) {
            List<User> oldUserList = role.getUserList();//该角色旧的用户列表
            if (oldUserList == null || oldUserList.size() == 0) {
                userList = userRepository.findAll();
            } else {
                List<Long> userIds = new ArrayList<Long>();
                for (int i = 0; i < oldUserList.size(); i++) {
                    userIds.add(oldUserList.get(i).getId());
                }
                userList = userRepository.findByIdNotInOrderById(userIds);
            }
        }
        return userList;
    }


    /**
     * @return 查询该角色用户的用户列表
     */
    public List<User> findUserListOfRole(Long roleId) {
        Role role = roleRepository.findOne(roleId);
        return role.getUserList();
    }

    /**
     * @param roleId
     * @param current
     * @param rowCount
     * @return 查询不属于该角色的用户列表，分页显示
     */
    public MyPage getOtherUsersMyPage(Long roleId, int current, Long rowCount, String searchPhrase) {
        List<User> usersList = findUserListNotOfRole(roleId);//获取不属于该角色id的用户列表
        return new PageUtils().getUserListMyPage(usersList, current, rowCount);
    }


    /**
     * @param roleId
     * @param current
     * @param rowCount
     * @return 查询不属于该角色的用户列表，分页显示
     */
    public MyPage getMyUsersMyPage(Long roleId, int current, Long rowCount, String searchPhrase) {
        List<User> usersList = findUserListOfRole(roleId);//获取不属于该角色id的用户列表
        return new PageUtils().getUserListMyPage(usersList, current, rowCount);
    }
}
