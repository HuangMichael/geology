/**
 * Created by Administrator on 2017/5/22 0022.
 */


dataTableName = "#dataTableList";
docName = "图层信息";
mainObject = "layer";
deleteObject = "layer";
appName = "图层信息";
formId = "#layerForm";
selectArray = ["#id"];


$(function () {
    initMenus("topMenu");

    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("layerForm");
        var object = JSON.parse(obj);
        var url = "layer/save";
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
        url: "/layer/data",
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
    console.log("layer_________"+JSON.stringify(layer));
    for (var key in layer) {
        if (layer[key]) {
            $("#layerForm #" + key).val(layer[key]);
        }
    }
}



