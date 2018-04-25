package com.yhb.dao.natureReserve;

import com.yhb.domain.natureReserve.NatureReserveBuffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huangbin on 2017/5/20 0004.
 * 自然保护缓冲区数据接口
 */
public interface NatureReserveBufferRepository extends JpaRepository<NatureReserveBuffer, Long> {

    /**
     * @param bufferNo
     * @param bufferName
     * @param selectedIds
     * @return
     */
    List<NatureReserveBuffer> findByBufferNoContainsAndBufferNameContainsAndIdIn(String bufferNo, String bufferName,List<Long> selectedIds);


    /**
     * @param bufferNo
     * @param bufferName
     * @param pageable 可分页参数
     * @return
     */
    Page<NatureReserveBuffer> findByBufferNoContainsAndBufferNameContains(String bufferNo, String bufferName, Pageable pageable);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from NatureReserveBuffer a ")
    List<Long> selectAllIds();


    /**
     * @param areaNo
     * @return 根据自然保护缓冲区编号查询
     */
    List<NatureReserveBuffer> findByBufferNo(String areaNo);



    /**
     * @param id
     * @return 根据自然保护缓冲区id查询区块信息
     */
    List<NatureReserveBuffer> findById(Long id);//List<Area> getOne(Long areaId)




    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from NatureReserveBuffer  b")
    Long findMaxId();
}
