package com.yhb.dao.location;

import com.yhb.domain.location.LocationTree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
public interface LocationTreeRepository extends JpaRepository<LocationTree, Long> {

    /**
     * @param authKey
     * @return 根据授权码查询位置树
     */
    List<LocationTree> findByAuthKeyStartsWith(String authKey);
}
