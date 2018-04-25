package com.yhb.dao.person;

import com.yhb.domain.person.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2017/5/17 0004.
 * 人员信息数据接口
 */
public interface PersonRepository extends JpaRepository<Person, Long>, CrudRepository<Person, Long> {

    /**
     * @param personName 根据部门id、人员姓名、状态查询
     * @return
     */
    List<Person> findByDepartment_DepNameContainsAndPersonNameContainsAndIdIn(String depName, String personName, List<Long> selectedIds);

    /**
     * @param personName 根据人员姓名查询单个人员
     * @return
     */
    Person findByPersonName(String personName);

    /**
     * @param personName 根据部门id、人员姓名、状态查询
     * @param pageable   可分页参数
     * @return
     */
    Page<Person> findByDepartment_DepNameContainsAndPersonNameContains(String depName, String personName, Pageable pageable);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from Person a ")
    List<Long> selectAllIds();


}
