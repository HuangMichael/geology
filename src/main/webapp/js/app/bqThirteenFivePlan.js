/**
 * Created by Administrator on 2017/7/17.
 */
dataTableName = "#dataTableList";
docName = "查询统计-基础数据查询-十三五规划";
mainObject = "bqThirteenFivePlan";
authStatus = "已审核";
$(function () {
    initMenus("topMenu");

    //导航高亮显示
    highLight();

    setFormReadStatus("#areaForm", true);
});


