package com.yhb.dao.area;

import com.yhb.domain.area.AreaTree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6.
 */
public interface AreaTreeRepository extends JpaRepository<AreaTree, Long> {

    /**
     * @param authKey
     * @return 根据权限获取所有的区块树
     */
    List<AreaTree> findByAuthKeyStartsWith(String authKey);

    /**
     * @param authKey
     * @param status
     * @return  查询区块树, 而且根据授权码和审核状态查询
     */
    List<AreaTree> findByAuthKeyStartsWithAndStatusContains(String authKey, String status);
}
