package com.yhb.dao.natureReserve;

import com.yhb.domain.natureReserve.VNatureReserveBuffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
public interface VNatureReserveBufferRepository extends JpaRepository<VNatureReserveBuffer, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VNatureReserveBuffer a ")
    List<Long> selectAllIds();

    /**
     * @param bufferNo
     * @param bufferName
     * @param selectedIds
     * @return 根据多条件复合查询沿海沙洲视图信息
     */
    List<VNatureReserveBuffer> findByBufferNoContainsAndBufferNameContainsAndIdIn(String bufferNo, String bufferName, List<Long> selectedIds);

    /**
     * @param bufferNo
     * @param bufferName
     * @param pageable
     * @return 根据多条件复合查询沿海沙洲视图信息
     */
    Page<VNatureReserveBuffer> findByBufferNoContainsAndBufferNameContains(String bufferNo, String bufferName, Pageable pageable);

}