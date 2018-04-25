package com.yhb.dao.etl;

import com.yhb.domain.etl.EtlMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/20.
 */
public interface EtlMediaRepository extends JpaRepository<EtlMedia, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from EtlMedia a ")
    List<Long> selectAllIds();
}
