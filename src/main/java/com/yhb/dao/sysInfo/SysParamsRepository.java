package com.yhb.dao.sysInfo;

import com.yhb.domain.sysInfo.SysParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2017-8-28 14:02:35
 * 系统参数信息数据接口
 */
public interface SysParamsRepository extends JpaRepository<SysParams, Long>, CrudRepository<SysParams, Long> {


    /**
     * @param paramsDesc 参数描述
     * @return 根据参数描述模糊查询
     */
    List<SysParams> findByParamDescContainsAndIdIn(String paramsDesc, List<Long> selectedIds);


    /**
     * @param paramsDesc 参数描述
     * @return 根据参数描述模糊查询
     */
    Page<SysParams> findByParamDescContains(String paramsDesc, Pageable pageable);


    /**
     * @param paramName 参数名称
     * @return 根据参数名称查询对象
     */
    SysParams findByParamName(String paramName);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from SysParams a ")
    List<Long> selectAllIds();
}
