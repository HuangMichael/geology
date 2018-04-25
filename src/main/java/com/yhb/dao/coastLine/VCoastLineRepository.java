package com.yhb.dao.coastLine;

import com.yhb.domain.coastline.VCoastLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
public interface VCoastLineRepository extends JpaRepository<VCoastLine, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VCoastLine a ")
    List<Long> selectAllIds();

    /**
     * @param lineNo 海岸线编号
     * @param lineName 海岸线名称
     * @return 根据海岸线编号和海岸线名称查询
     */
    List<VCoastLine> findByLineNoContainsAndLineNameContainsAndIdIn(String lineNo,String lineName,List<Long> selectedIds);

    /**
     * @param lineNo 海岸线编号
     * @param lineName 海岸线名称
     * @param pageable
     * @return 根据海岸线编号和海岸线名称查询
     */
    Page<VCoastLine> findByLineNoContainsAndLineNameContains(String lineNo,String lineName, Pageable pageable);

}
