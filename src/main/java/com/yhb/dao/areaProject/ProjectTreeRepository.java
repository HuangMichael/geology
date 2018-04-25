package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.ProjectTree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/18 0018.
 */
public interface ProjectTreeRepository extends JpaRepository<ProjectTree, Long> {

    /**
     * @param authKey
     * @return 根据授权码查询项目树
     */
    List<ProjectTree> findByAuthKeyStartsWithAndStatusContains(String authKey,String status);
}
