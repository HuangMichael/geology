package com.yhb.controller.mediaTree;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by huangbin on 2017/10/21 0004.
 * 区块多媒体树控制器
 */
@Controller
@RequestMapping("/areaMediaTree")
@Slf4j
@Data
public class AreaMediaTreeController extends MediaTreeController {

    protected String treeType = "A";

}
