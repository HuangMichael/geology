package com.yhb.dao.areaProject;


import com.yhb.domain.area.AreaMedia;
import com.yhb.domain.areaProject.ProjectMedia;
import com.yhb.domain.mediaCatalog.MediaTree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
public interface ProjectMediaRepository extends JpaRepository<ProjectMedia, Long> {

    /**
     * @param mediaTypeId
     * @return 根据多媒体类型id查询该类型所有的多媒体信息列表
     */
    List<ProjectMedia> findByMediaTypesId(Long mediaTypeId);

    /**
     * @param areaProjectId
     * @param mediaTypeId
     * @return
     */
    List<ProjectMedia> findByAreaProjectIdAndMediaTypesIdAndAuthKeyStartsWith(Long areaProjectId, Long mediaTypeId, String authKey);


    /**
     * @param mediaTree 媒体树
     * @return
     */
    List<ProjectMedia> findByMediaTree(MediaTree mediaTree);


    /**
     * @param areaId
     * @param mediaTypeId
     * @return 根据项目的id和多媒体类型id查询多媒体资料
     */
    List<ProjectMedia> findByAreaProject_IdAndMediaTypes_Id(Long areaId, Long mediaTypeId);

}
