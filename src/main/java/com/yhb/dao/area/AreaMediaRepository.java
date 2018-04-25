package com.yhb.dao.area;

import com.yhb.domain.area.Area;
import com.yhb.domain.area.AreaMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 围垦区块多媒体数据接口
 */
public interface AreaMediaRepository extends JpaRepository<AreaMedia, Long> {
    /**
     * @param fileName
     * @return
     */
    AreaMedia findByFileName(String fileName);

    /**
     * @param areaId
     * @return 根据区块id查询该区块所有的多媒体信息列表
     */
    List<AreaMedia> findByAreaIdAndAuthKeyStartsWith(Long areaId, String authKey);

    /**
     * @param mediaTypeId
     * @return 根据多媒体类型id查询该类型所有的多媒体信息列表
     */
    List<AreaMedia> findByMediaTypesId(Long mediaTypeId);

    /**
     * @param areaId
     * @param mediaTypeId
     * @return
     */
    List<AreaMedia> findByAreaIdAndMediaTypesIdAndAuthKeyStartsWith(Long areaId, Long mediaTypeId, String authKey);


    /**
     * @return 查询所有的区块多媒体信息，有数据权限限制
     */
    List<AreaMedia> findByAuthKeyStartsWith(String authKey);


    /**
     * @param areaId
     * @param mediaTypeId
     * @return 根据区块的id和多媒体类型id查询多媒体资料
     */
    List<AreaMedia> findByArea_IdAndMediaTypes_Id(Long areaId, Long mediaTypeId);
}
