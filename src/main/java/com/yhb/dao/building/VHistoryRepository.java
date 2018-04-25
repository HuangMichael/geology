package com.yhb.dao.building;

import com.yhb.domain.history.VHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/7/12.
 * 历史沿革数据接口
 */
public interface VHistoryRepository extends JpaRepository<VHistory, Long> {
}
