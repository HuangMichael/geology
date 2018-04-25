/**
 * Created by Administrator on 2017/5/22 0022.
 */


dataTableName = "#dataTableList";
docName = "元数据表信息";
mainObject = "etlTable";
deleteObject = "etlTable";
appName = "元数据表信息";

//每个界面，在公共方法里面清空
formId = "#MetadataForm";
selectArray = ["#id"];

$(function () {
    initMenus("topMenu");

    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("MetadataForm");

        var object = JSON.parse(obj);
        object["landSize"]=object["landSize"]*1;
        console.log("obj-------------" + obj);
        var url = "etlTable/save";
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
        url: "/etlTable/data",
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
    var layer = findByIdDB(id);
    for (var key in layer) {
        if (layer[key]) {
            $("#MetadataForm #" + key).val(layer[key]);
        }
    }
    $("#confirm_modal").modal("show");
}

