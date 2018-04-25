package com.yhb.dao.natureReserve;


import com.yhb.domain.natureReserve.NatureReserveExperiment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Created by lijina on 2017/6/22.
 * 自然保护试验区数据接口
 */
public interface NatureReserveExperimentRepository extends JpaRepository<NatureReserveExperiment, Long>{
    /**
     * @param areaNo
     * @param areaName
     * @return
     */
    List<NatureReserveExperiment> findByExperimentNoContainsAndExperimentNameContainsAndIdIn(String areaNo, String areaName,List<Long> selectedIds);


    /**
     * @param areaNo
     * @param areaName
     * @param pageable 可分页参数
     * @return
     */
    Page<NatureReserveExperiment> findByExperimentNoContainsAndExperimentNameContains(String areaNo, String areaName, Pageable pageable);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from NatureReserveExperiment a ")
    List<Long> selectAllIds();


    /**
     * @param areaNo
     * @return 根据自然保护缓冲区编号查询
     */
    List<NatureReserveExperiment> findByExperimentNo(String areaNo);



    /**
     * @param id
     * @return 根据自然保护缓冲区id查询区块信息
     */
    List<NatureReserveExperiment> findById(Long id);//List<Area> getOne(Long areaId)




    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from NatureReserveExperiment  b")
    Long findMaxId();

}
