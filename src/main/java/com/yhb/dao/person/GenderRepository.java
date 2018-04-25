package com.yhb.dao.person;

import com.yhb.domain.person.Gender;
import com.yhb.domain.person.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2017/5/17 0004.
 * 性别信息数据接口
 */
public interface GenderRepository extends JpaRepository<Gender, Long>, CrudRepository<Gender, Long> {


}
