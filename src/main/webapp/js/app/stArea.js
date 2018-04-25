/**
 * Created by Administrator on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
docName = "查询统计-统计分析-围垦区块统计分析";
mainObject = "stArea";
authStatus = "已审核";
$(function () {
    initMenus("topMenu");

    //初始化搜索条件市区下拉列表
    city_District();

    //点击查看统计图按钮
    $("#submit").on("click", function () {
        var url = "stArea/area/typeSizeGroupByAreaNo";
        var searchParams = getSearchParams();
        var planDesc = searchParams.replace(/,/g, "");
        var plan = planDesc.replace(/已审核/g, "");
        drawBarChartForAreaStat(plan + "围垦区块", "chart", url);

        $("#layer").css("display", "block");
        return false;
    });
    $(".div1 .off").on("click", function () {
        $("#layer").css("display", "none");
    });

    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
                searchPhrase: getSearchParams()
            };
        },
        url: "/stArea/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-cheek' title='查看统计图' onclick='check(" + row.id + ")'><i class='icon-zoom-in'></i></button>";
            }
        }
    });
});

var areaName = null;
var areaNo = null;
// 根据id获取区块编号
function findById(id) {

    $.ajaxSettings.async = false;
    var url = "stArea/findVById/" + id;
    $.get(url, function (data) {
        areaNo = data.areaNo;
        areaName = data.areaName;
    });
}

// 根据id获取农业建设生态三种面积数据
function getAreaTypeSizeTotal(id) {
    var areaTypeSizeTotal = null;//路丽民20170926
    $.ajaxSettings.async = false;
    var url = "stArea/area/typeSizeById/" + id;
    $.get(url, function (data) {
        areaTypeSizeTotal = data;
    });
    return areaTypeSizeTotal;
}

// 点击查看统计图时生成图表信息
function check(id) {
    $("#layer").css("display", "block");
    findById(id);
    var areaTypeSizeTotal = getAreaTypeSizeTotal(id);
    // console.log(JSON.stringify(areaTypeSizeTotal));

    var nyData = areaTypeSizeTotal["nyBuildSize"];
    var jsData = areaTypeSizeTotal["jsBuildSize"];
    var stData = areaTypeSizeTotal["stBuildSize"];
    areaNo = areaNo + "区块";
    $("#chart").html("");
    drawPieChart("chart", areaNo, "围垦现状", nyData, stData, jsData);
}
