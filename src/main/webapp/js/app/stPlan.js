dataTableName = "#dataTableList";
docName = "基础数据查询-总体规划信息";
mainObject = "bqTotalPlan";
$(function () {
    initMenus("topMenu");
    // initBootGrid("#dataTableList");

    //点击新增按钮
    $("#submit").on("click", function () {
        drawPlanBarChart("江苏省总体规划", "chart");

        $("#layer").css("display", "block");
        return false;
    })
    $(".div1 .off").on("click", function () {
        $("#layer").css("display", "none");
    })




    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/bqTotalPlan/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='查看统计图' onclick='edit(" + row.id + ")'><i class='icon-zoom-in'></i></button>";
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

