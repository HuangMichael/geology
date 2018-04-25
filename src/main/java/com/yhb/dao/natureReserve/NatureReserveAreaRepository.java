package com.yhb.dao.natureReserve;

import com.yhb.domain.natureReserve.NatureReserveArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huangbin on 2017/5/20 0004.
 * 自然保护核心区块数据接口
 */
public interface NatureReserveAreaRepository extends JpaRepository<NatureReserveArea, Long> {

    /**
     * @param areaNo
     * @param areaName
     * @return
     */
    List<NatureReserveArea> findByAreaNoContainsAndAreaNameContainsAndIdIn(String areaNo, String areaName, List<Long> selectedIds);


    /**
     * @param areaNo
     * @param areaName
     * @param pageable 可分页参数
     * @return
     */
    Page<NatureReserveArea> findByAreaNoContainsAndAreaNameContains(String areaNo, String areaName, Pageable pageable);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from NatureReserveArea a ")
    List<Long> selectAllIds();


    /**
     * @param areaNo
     * @return 根据区块编号查询
     */
    List<NatureReserveArea> findByAreaNo(String areaNo);



    /**
     * @param id
     * @return 根据区块id查询区块信息
     */
    List<NatureReserveArea> findById(Long id);//List<Area> getOne(Long areaId)



    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from NatureReserveArea  b")
    Long findMaxId();
}
