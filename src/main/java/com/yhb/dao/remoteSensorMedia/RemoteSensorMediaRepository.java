package com.yhb.dao.remoteSensorMedia;

import com.yhb.domain.remoteSensorMedia.RemoteSensorMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
public interface RemoteSensorMediaRepository extends JpaRepository<RemoteSensorMedia, Long> {

    /**
     * @return 根据数据权限查询所有的记录
     */
    List<RemoteSensorMedia> findByAuthKeyStartsWith(String authKey);

    /**
     * @param locationId
     * @return 根据位置id查询该位置的所有的遥感多媒体信息列表
     */
    List<RemoteSensorMedia> findByLocationIdAndAuthKeyStartsWith(Long locationId, String authKey);

    /**
     * @param mediaTypeId
     * @return 根据多媒体类型id查询该类型所有的多媒体信息列表
     */
    List<RemoteSensorMedia> findByMediaTypesId(Long mediaTypeId);

    /**
     * @param locationId
     * @param mediaTypeId
     * @return 根据位置id以及多媒体类型id查询该属于该位置的该多媒体类型的信息列表
     */
    List<RemoteSensorMedia> findByLocationIdAndMediaTypesIdAndAuthKeyStartsWith(Long locationId, Long mediaTypeId, String authKey);
}
