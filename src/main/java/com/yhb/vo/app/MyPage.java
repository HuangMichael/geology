package com.yhb.vo.app;
import lombok.Data;
import java.util.List;
/**
 * 自定义的分页对象
 *
 * @author
 * @create 2017年5月9日10:17:42
 **/

@Data
public class MyPage {
    private int current; //当前页
    private Long rowCount; //每页行数
    private List rows; //当前页记录集合
    private long total;//记录总数
    private String[] sort; //排序
}
