package com.yhb.dao.department;

import com.yhb.domain.department.VDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
public interface  VDepartmentRepository extends JpaRepository<VDepartment, Long>{
    /**
     * @param depName 根据部门名称查询
     * @return
     */
    List<VDepartment> findByDepNameContainsAndIdIn(String depName, List<Long> selectedIds);

    /**
     * @param depName  据部门名称查询
     * @param pageable 可分页参数
     * @return
     */
    Page<VDepartment> findByDepNameContains(String depName, Pageable pageable);
}
