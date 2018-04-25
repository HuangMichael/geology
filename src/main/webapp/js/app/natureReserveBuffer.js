/**
 * Created by Administrator on 2017/5/22 0022.
 */
/**
 * Created by Administrator on 2017/5/22 0022.
 */


dataTableName = "#dataTableList";
docName = "系统管理-基础数据管理-自然保护缓冲区信息";
mainObject = "vNatureReserveBuffer";
deleteObject = "natureReserveBuffer";
appName = "自然保护缓冲区信息";
formId = "#natureReserveBufferForm";
selectArray = ["#id"];


$(function () {
    initMenus("topMenu");

    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("natureReserveBufferForm");
        var object = JSON.parse(obj);

        console.log("obj-------------" + obj);
        var url = "natureReserveBuffer/save";
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
        url: "/vNatureReserveBuffer/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        }
    })


})


//编辑记录
function edit(id) {
    cleanData();  //清空下拉列表的默认选项

    var natureReserveBuffer = findByIdDB(id);
    for (var key in natureReserveBuffer) {
        if (natureReserveBuffer[key]) {
            $("#natureReserveBufferForm #" + key).val(natureReserveBuffer[key]);
        }
    }
}



/**
 * 用户选择多条数据，进行批量审核
 */
function authorize() {
    var selectedIds = getCheckedValues();
    if (!selectedIds) {
        showMessageBox("danger", "请选择要审核的自然保护缓冲区！");
        return;
    }
    var url = "natureReserveBuffer/authorizeInBatch";
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