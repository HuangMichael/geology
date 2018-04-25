package com.yhb.dao.area;

import com.yhb.domain.area.AreaFunctionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */
public interface AreaFunctionTypeRepository extends JpaRepository<AreaFunctionType, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AreaFunctionType a ")
    List<Long> selectAllIds();
}
