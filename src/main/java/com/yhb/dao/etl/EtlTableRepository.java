package com.yhb.dao.etl;

import com.yhb.domain.etl.EtlTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface EtlTableRepository extends JpaRepository<EtlTable, Long>,CrudRepository<EtlTable, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from EtlTable a ")
    List<Long> selectAllIds();

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    List<EtlTable> findByBaseTableNameContainsAndIdIn(String searchPhrase,List<Long> selectedIds);

    /**
     * @param searchPhrase
     * @param pageable
     * @return 根据多条件关键字进行查询
     */
    Page<EtlTable> findByBaseTableNameContains(String searchPhrase, Pageable pageable);


    /**
     * @param serviceTableName
     * @return 根据业务表名称查询EtlTable
     */
    EtlTable findByServiceTableName(String serviceTableName);
}
