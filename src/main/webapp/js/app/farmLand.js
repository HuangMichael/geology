/**
 * Created by Administrator on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
docName = "系统管理-地理信息管理-耕地信息";
mainObject = "vFarmLand";
deleteObject = "farmLand";
appName = "耕地信息";
formId = "#farmLandForm";
selectArray = ["#id"];


$(function () {
    initMenus("topMenu");

    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("farmLandForm");

        var object = JSON.parse(obj);
        object["landSize"] = object["landSize"] * 1;
        console.log("obj-------------" + obj);
        var url = "farmLand/save";
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
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/vFarmLand/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        }
    })


});


//编辑记录
function edit(id) {
    cleanData();  //清空下拉列表的默认选项

    var layer = findByIdDB(id);
    for (var key in layer) {
        $("#farmLandForm #" + key).val(layer[key]);
    }
}


/**
 * 用户选择多条数据，进行批量审核
 */
function authorize() {
    var selectedIds = getCheckedValues();
    if (!selectedIds) {
        showMessageBox("danger", "请选择要审核的耕地信息！");
        return;
    }
    var url = "farmLand/authorizeInBatch";
    var requestData = {
        selectedIds: selectedIds
    };
    $.post(url, requestData, function (data) {
        if (data.result) {
            showMessageBox("info", data["resultDesc"]);
            $(dataTableName).bootgrid("reload");
        } else {
            showMessageBox("danger", data["resultDesc"]);
        }
    });
}


