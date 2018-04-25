package com.yhb.dao.department;

import com.yhb.domain.department.Department;
import com.yhb.vo.ReturnObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2017/5/17 0004.
 * 部门信息数据接口
 */
public interface DepartmentRepository extends JpaRepository<Department, Long>, CrudRepository<Department, Long> {

    /**
     * @param depName 根据部门名称查询
     * @return
     */
    List<Department> findByDepNameContainsAndIdIn(String depName, List<Long> selectedIds);

    /**
     * @param depName 根据部门名称查询
     * @return
     */
    Department findByDepName(String depName);

    /**
     * @param depName  据部门名称查询
     * @param pageable 可分页参数
     * @return
     */
    Page<Department> findByDepNameContains(String depName, Pageable pageable);


    /**
     * @param locName 按照位置的位置名称模糊查询
     * @return
     */
    List<Department> findByLocation_LocNameContains(String locName);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from Department a ")
    List<Long> selectAllIds();

    /**
     * @param pId
     * @return 根据父id查询它的孩子部门列表
     */
    List<Department> findByParentId(Long pId);
}
