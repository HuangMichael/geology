package com.yhb.dao.etl;

import com.yhb.domain.etl.EtlTableConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface EtlRepository extends JpaRepository<EtlTableConfig, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from EtlTableConfig a ")
    List<Long> selectAllIds();

    /**
     * @param baseColDesc
     * @param selectedIds
     * @return 根据多条件关键字进行查询
     */
    List<EtlTableConfig> findByBaseColDescContainsAndIdIn(String baseColDesc,List<Long> selectedIds);

    /**
     * @param baseColDesc
     * @param pageable
     * @return 根据多条件关键字进行查询
     */
    Page<EtlTableConfig> findByBaseColDescContains(String baseColDesc, Pageable pageable);

    /**
     * @param etlTableId
     * @return 根据etlTableId查询该表的所有属性信息EtlTableConfig
     */
    List<EtlTableConfig> findByTableIdAndStatusOrderBySortNo(Long etlTableId,String status);
}
