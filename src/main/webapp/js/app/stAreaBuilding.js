dataTableName = "#dataTableList";
docName = "查询统计-统计分析-围垦信息统计分析";
mainObject = "stAreaBuilding";
authStatus = "已审核";
$(function () {
    initMenus("topMenu");

    //初始化搜索条件市区下拉列表
    city_District();

    //点击新增按钮
    $("#submit").on("click", function () {
        var url = "stAreaBuilding/area/typeSizeGroupByAreaNo";
        var searchParams = getSearchParams();
        var planDesc = searchParams.replace(/,/g, "");
        var plan = planDesc.replace(/已审核/g, "");
        drawPlanBarChart(plan + "围垦信息统计分析", "chart", url);

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
        url: "/stAreaBuilding/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='查看统计图' onclick='check(" + row.id + ")'><i class='icon-zoom-in'></i></button>";
            }
        },
        converters: {//转换器，转换器由两种方法组成:将字符串转换为所需类型，或者将一个类型转换为一个具象性的字符串。
            datetime: {
                // to: function (value) {return moment(value).format('YYYY-MM');}
                to: function (value) {
                    return value.substring(0, 7);
                }
            }
        }
    }).on("selected.rs.jquery.bootgrid", function (e, rows) {
        var rowIds = [];
        for (var i = 0; i < rows.length; i++) {
            rowIds.push(rows[i].id);
        }
    }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
        var rowIds = [];
        for (var i = 0; i < rows.length; i++) {
            rowIds.push(rows[i].id);
        }
    });
});


/**
 * 获取项目信息
 */
function getProjects(url) {
    var projects = [];
    $.ajaxSettings.async = false;
    $.getJSON(url, function (data) {
        projects = data;
    });
    return projects;
}


// 点击查看统计图时生成图表信息
function check(id) {

    $("#layer").css("display", "block");
    var url = "/stAreaBuilding/findVById/" + id;
    var AreaBuilding = getProjects(url);
    console.log(AreaBuilding);
    var areaNo = AreaBuilding.areaNo;
    var areaName = AreaBuilding.areaName;

    var beginYear = AreaBuilding.beginYear.substring(0, 7);
    var endYear = AreaBuilding.endYear.substring(0, 7);
    areaNo = areaNo + areaName + "区块从" + beginYear + "到" + endYear;
    var nyData = AreaBuilding.nyBuildSize == null ? 0 : AreaBuilding.nyBuildSize;
    var stData = AreaBuilding.stBuildSize == null ? 0 : AreaBuilding.stBuildSize;
    var jsData = AreaBuilding.jsBuildSize == null ? 0 : AreaBuilding.jsBuildSize;

    drawPieChart("chart", areaNo, "围垦信息", nyData, stData, jsData);
}