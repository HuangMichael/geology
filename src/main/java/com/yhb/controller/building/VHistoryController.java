package com.yhb.controller.building;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.history.VHistory;
import com.yhb.service.building.VHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
@Controller
@RequestMapping("/vHistory")
public class VHistoryController extends BaseController {
    @Autowired
    VHistoryService vHistoryService;

    /**
     * @return 查找所有的历史沿革记录的菜单主题，不包含子标题和描述等详细信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<VHistory> findAll() {
        return vHistoryService.findAll();
    }
}
