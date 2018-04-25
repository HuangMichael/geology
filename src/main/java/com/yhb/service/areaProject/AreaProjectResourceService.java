package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.ProjectTreeRepository;
import com.yhb.domain.areaProject.ProjectTree;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.DataFilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/6/18 0018.
 * 项目资料信息业务类
 */
@Service
public class AreaProjectResourceService extends BaseService {

    @Autowired
    ProjectTreeRepository projectTreeRepository;

    /**
     * @return 查询项目树，按省市分类
     */
    public List<ProjectTree> findProjectTreeByAuthKeyAndStatus(HttpSession session, String status) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return projectTreeRepository.findByAuthKeyStartsWithAndStatusContains(authKey,status);
    }
}
