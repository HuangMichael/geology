dataTableName = "#dataTableList";
docName = "查询统计-统计分析-十三五规划统计分析";
mainObject = "stThirteenFivePlan";
authStatus = "已审核";
$(function () {
    initMenus("topMenu");


    //初始化搜索条件市区下拉列表
    city_District();


    //点击新增按钮
    $("#submit").on("click", function () {
        var url = "stThirteenFivePlan/area/typeSizeGroupByAreaNo";
        var searchParams = getSearchParams();
        var planDesc = searchParams.replace(/,/g, "");
        var plan = planDesc.replace(/已审核/g, "");
        drawPlanBarChart(plan + "十三五规划", "chart", url);



        $("#layer").css("display", "block");
        return false;
    })
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
        url: "/stThirteenFivePlan/data",
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

    })


});


// 根据id获取农业建设生态三种面积数据
function getAreaTypeSizeTotal(id) {

    $("#layer").css("display", "block");
    var areaTypeSizeTotal = null;
    $.ajaxSettings.async = false;
    var url = "/stThirteenFivePlan/findVById/" + id;//根据id查询视图信息
    $.get(url, function (data) {
        console.log(JSON.stringify(data));
        areaTypeSizeTotal = data;
    });
    return areaTypeSizeTotal;
}

// 点击查看统计图时生成图表信息
function check(id) {

    var areaTypeSizeTotal = getAreaTypeSizeTotal(id);
    var areaNo = areaTypeSizeTotal.areaNo;
    var areaName = areaTypeSizeTotal.areaName;
    var beginYear = areaTypeSizeTotal.beginYear.substring(0, 7);
    var endYear = areaTypeSizeTotal.endYear.substring(0, 7);
    areaNo = areaNo + areaName + "区块从" + beginYear + "到" + endYear;

    var nyData = areaTypeSizeTotal.nyBuildSize == null ? 0 : areaTypeSizeTotal.nyBuildSize;
    var stData = areaTypeSizeTotal.stBuildSize == null ? 0 : areaTypeSizeTotal.stBuildSize;
    var jsData = areaTypeSizeTotal.jsBuildSize == null ? 0 : areaTypeSizeTotal.jsBuildSize;

    drawPieChart("chart", areaNo, "十三五规划", nyData, stData, jsData);
}
