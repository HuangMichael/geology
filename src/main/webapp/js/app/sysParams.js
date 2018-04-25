/**
 * Created by Administrator on 2017/5/22 0022.
 */
dataTableName = "#dataTableList";
docName = "系统管理-系统监控管理-系统参数";
mainObject = "sysParams";
deleteObject = "sysParams";
appName = "系统参数";

//每个界面，在公共方法里面清空
formId = "#paramsForm";
selectArray = ["#id"];

tableId = 5;
$(function () {
    initMenus("topMenu");


    /**
     * 保存新增数据
     */
    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("paramsForm");
        var object = JSON.parse(obj);
        var url = "sysParams/save";
        $.post(url, object, function (data) {
            if (data.result) {
                $(dataTableName).bootgrid("reload");
                $("#confirm_modal").modal("hide");
                showMessageBox("info", data["resultDesc"]);
            } else {
                showMessageBox("danger", data["resultDesc"]);
            }
        })
    });


    var grid = $(dataTableName).bootgrid({
        ajax: true,
        selection: true,
        multiSelect: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/sysParams/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑'  onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        }

    });



});


function edit(id) {
    var params = findByIdDB(id);
    console.log(params);
    for (var key in params) {
        $("#paramsForm #" + key).val(params[key]);
    }
    $("#confirm_modal").modal("show");
}