package com.yhb.dao.area;

import com.yhb.domain.area.AreaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
public interface AreaTypeRepository  extends JpaRepository<AreaType, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AreaType a ")
    List<Long> selectAllIds();
}