package com.yhb.controller.mediaTree;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by huangbin on 2017/5/22 0004.
 * 空间分析控制器
 */
@Controller
@RequestMapping("/remoteSensorMediaTree")
@Slf4j
@Data
public class RemoteSensorMediaTreeController extends  MediaTreeController {

    protected String treeType = "R";
}
