package com.yhb.dao.person;

import com.yhb.domain.person.VPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by huangbin on 2017/5/17 0004.
 * 人员信息数据接口
 */
public interface VPersonRepository extends PagingAndSortingRepository<VPerson, Long> {

    /**
     * @param personName 根据部门名称、人员姓名、状态查询
     * @return
     */
    List<VPerson> findByDepNameContainsAndPersonNameContainsAndIdIn(String depName, String personName, List<Long> selectedIds);

    /**
     * @param personName 根据部门名称、人员姓名、状态查询
     * @param pageable   可分页参数
     * @return
     */
    Page<VPerson> findByDepNameContainsAndPersonNameContains(String depName, String personName, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VPerson a ")
    List<Long> selectAllIds();


}
