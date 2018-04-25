/**
 * Created by Administrator on 2017/7/19.
 */
dataTableName = "#dataTableList";
docName = "查询统计-基础数据查询-围垦进度";
mainObject = "sysMngAreaBuilding";
authStatus = "已审核";
$(function () {
    initMenus("topMenu");

    //搜索条件中市县查询
     city_District();

    setFormReadStatus("#areaForm", true);

});

