package com.yhb.dao.mediaTree;

import com.yhb.domain.mediaCatalog.MediaTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2017/10/20 0004.
 * 多媒体树数据接口
 */
public interface MediaTreeRepository extends JpaRepository<MediaTree, Long> {


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from MediaTree a ")
    List<Long> selectAllIds();


    /**
     * @return 查询所有多媒体树
     */
    List<MediaTree> findAll();


    /**
     * @param type 类别
     * @param authKey
     * @param status
     * @return 根据类别查询多媒体树信息
     */
    List<MediaTree> findByTreeTypeAndAuthKeyStartingWithAndStatus(String type,String authKey,String status);


    /**
     * @return 查询当前节点子节点最大id
     */
    @Query("select count(a.id) from MediaTree a where a.parent.id = :pid ")
    Long findMaxId(@Param("pid") Long pid);


}



