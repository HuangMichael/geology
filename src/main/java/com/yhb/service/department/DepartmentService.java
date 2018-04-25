package com.yhb.service.department;

import com.yhb.dao.department.DepartmentRepository;
import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.department.Department;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.etl.EtlTableService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by huangbin on 2017/5/17 0004.
 * 部门信息业务类
 */
@Service
public class DepartmentService extends BaseService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    EtlTableService etlTableService;

    /**
     * @param id
     * @param depNo
     * @param depName
     * @param depDesc
     * @param manager
     * @param locationId
     * @param parentId
     * @param status
     * @return 保存部门信息
     */
    public Department save(Long id, String depNo, String depName, String depDesc, String manager, Long locationId, Long parentId, String status) {
        Department department = new Department();
        department.setId(id);
        department.setDepNo(depNo);
        department.setDepName(depName);
        department.setDepDesc(depDesc);
        department.setManager(manager);
        if (locationId == null) {
            department.setLocation(null);
        } else {
            department.setLocation(locationRepository.findOne(locationId));
        }

        //根据父级id求出部门级别depLevel
        Long depLevel;
        Department parent = null;
        if (parentId == null) {
            depLevel = 0L;
        } else {
            parent = departmentRepository.getOne(parentId);
            depLevel = parent == null ? 0L : parent.getDepLevel() + 1;
        }
        department.setDepLevel(depLevel);
        department.setParent(parent);
        department.setStatus(status);
        return departmentRepository.save(department);
    }


    /**
     * @param id 根据ID删除部门信息,如果有关联数据，则提示无法删除
     */
    public ReturnObject delete(Long id) {
        Department department = departmentRepository.findOne(id);
        if (department == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的部门不存在,请确认！");
        }
        //查询该部门是否有孩子节点，有的话不能删除
        List<Department> subList = findByParentId(id);
        System.out.println("sublist=====================================" + subList);
        if (subList.size() > 0) {
            return commonDataService.getReturnType(false, "", "部门信息有关联数据，无法删除，请联系管理员！");
        }
        try {
            departmentRepository.delete(department);
//            //再查询一次查看是否删除
//            Department department1 = departmentRepository.getOne(id);
            return commonDataService.getReturnType(true, "部门信息删除成功！", "部门信息删除失败！");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "部门信息删除成功！", "部门信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public Department findById(Long id) {
        return departmentRepository.findOne(id);

    }

    /**
     * @param depName
     * @return 根据部门名称查询
     */
    public Department findByDepName(String depName) {
        return departmentRepository.findByDepName(depName);
    }

    /**
     * @return 查询所有的部门信息信息
     */

    public List<Department> findAll() {
        return departmentRepository.findAll();

    }


    /**
     * @return 查询所有ID
     */
    public List<Long> selectAllIds() {
        return departmentRepository.selectAllIds();
    }


    /**
     * @param locName 位置名称
     * @return 根据位置名称模糊查询
     */
    public List<Department> findByLocation_LocNameContains(String locName) {
        return departmentRepository.findByLocation_LocNameContains(locName);
    }

    /**
     * @param pId
     * @return 根据父id查询它的孩子部门列表
     */
    public List<Department> findByParentId(Long pId) {
        return departmentRepository.findByParentId(pId);
    }


    /**
     * @param serviceTableName
     * @param request
     * @param response
     * @return
     */
    public boolean oneKeyExport(String serviceTableName, HttpServletRequest request, HttpServletResponse response) {
        log.info("oneKeyExport export empty table ------------------" + serviceTableName);
        return etlTableService.createExcelTemplate(serviceTableName, request, response);

    }

    /**
     * @param serviceTableName
     */
    public void oneKeyImport(String serviceTableName) {
        log.info("oneKeyExport------------------" + serviceTableName);
    }
}
