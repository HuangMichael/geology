package com.yhb.dao.location;

import com.yhb.domain.location.LocationBuilding;
import com.yhb.domain.person.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lulimin on 2017/5/17 0004.
 * 位置围垦描述信息数据接口
 */
public interface LocationBuildingRepository extends JpaRepository<LocationBuilding, Long> {


    /**
     * @param id  位置id
     * @return  根据位置id查询位置围垦描述信息
     */
     @Query(value = "select lb from LocationBuilding lb where lb.location.id =:id")
     List<LocationBuilding> findByLocation_Id(@Param("id") Long id);

}
