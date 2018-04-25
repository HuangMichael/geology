package com.yhb.service.person;

import com.yhb.dao.department.DepartmentRepository;
import com.yhb.dao.person.GenderRepository;
import com.yhb.dao.person.PersonRepository;
import com.yhb.domain.person.Gender;
import com.yhb.domain.person.Person;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.etl.EtlTableService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2017/5/17 0004.
 * 人员信息业务类
 */
@Service
@CacheConfig
public class PersonService extends BaseService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CommonDataService commonDataService;


    @Autowired
    GenderRepository genderRepository;

    @Autowired
    EtlTableService  etlTableService;

    /**
     * @param person 人员信息
     * @return 保存人员信息
     */
    public Person save(Person person) {
        return personRepository.save(person);

    }

    /**
     * @param id
     * @param personName 人员名称
     * @param gender     性别
     * @param birthDate  出生日期
     * @param depId      部门id
     * @param status     人员状态
     * @return 保存人员信息
     */

    public Person save(Long id, String personName, Long gender, String birthDate, Long depId, String status) {
        try {
            Person person = new Person();
            person.setId(id);
            person.setPersonName(personName);
            person.setGender(genderRepository.findOne(gender));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(birthDate);
            person.setBirthDate(date);
            person.setDepartment(departmentRepository.findOne(depId));
            person.setStatus(status);
            return personRepository.save(person);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @param personList 批量删除
     */
    public void deleteInBatch(List personList) {
        personRepository.deleteInBatch(personList);
    }

    /**
     * @param id 根据ID删除人员信息信息
     */
    @CacheEvict(value = "person", key = "'person#id'")
    public ReturnObject delete(Long id) {
        //有子节点不能删除
        Person person = personRepository.findOne(id);
        if (person == null) {
            return commonDataService.getReturnType(person != null, "", "id为" + id + "的人员不存在,请确认！");
        } else {
            personRepository.delete(person);
            //再查询一次查看是否删除
            Person p = personRepository.findOne(id);
            return commonDataService.getReturnType(p == null, "人员信息删除成功", "人员信息无法删除，请联系管理员!");
        }
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public Person findById(Long id) {
        return personRepository.findOne(id);

    }

    /**
     * @param personName 人员名称
     * @return
     */
    public Person findByPersonName(String personName) {
        return personRepository.findByPersonName(personName);
    }

    /**
     * @return 查询所有的人员信息
     */
    public List<Person> findAll() {
        return personRepository.findAll();

    }

    /**
     * @return 查询所有ID
     */
    public List<Long> selectAllIds() {
        return personRepository.selectAllIds();
    }


    /**
     * @return 查询性别
     */
    public List<Gender> findGenders() {
        return genderRepository.findAll();
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
