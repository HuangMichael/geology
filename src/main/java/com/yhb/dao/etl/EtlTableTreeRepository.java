package com.yhb.dao.etl;

import com.yhb.domain.etl.EtlTableTree;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface EtlTableTreeRepository  extends JpaRepository<EtlTableTree, Long> {
}
