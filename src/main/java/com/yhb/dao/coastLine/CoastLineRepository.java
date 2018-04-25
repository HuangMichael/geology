package com.yhb.dao.coastLine;

import com.yhb.domain.coastline.CoastLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huangbin on 2017/5/20 0004.
 * 海岸线数据接口
 */
public interface CoastLineRepository extends JpaRepository<CoastLine, Long> {
    /**
     * @param lineNo
     * @param lineName
     * @param pageable 可分页参数
     * @return
     */
    Page<CoastLine> findByLineNoContainsAndLineNameContains(String lineNo, String lineName, Pageable pageable);



    /**
     * @param lineNo
     * @param lineName
     * @return
     */
    List<CoastLine> findByLineNoContainsAndLineNameContainsAndIdIn(String lineNo, String lineName,List<Long> selectedIds);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from CoastLine a ")
    List<Long> selectAllIds();


    /**
     * @return 查询所有海岸线
     */
    List<CoastLine> findAll();



    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from CoastLine  b")
    Long findMaxId();

}



