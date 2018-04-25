/**
 * Created by Administrator on 2017/8/1.
 */
dataTableName = "#dataTableList";
docName = "系统管理-地理信息管理-滩涂信息";
mainObject = "vBeach";
deleteObject = "beach";
appName = "滩涂信息";
formId = "#beachForm";
selectArray = ["#id","#seaLevelType","#locationSelect"];

$(function () {
    initMenus("topMenu");

    //编辑或者新增界面的行政区划选择下拉列表
    var locLevelUrl = "location/findByLocLevel/0";//返回值是一个列表，查找江苏省的信息
    var locs = findByIdUrl(locLevelUrl);
    console.log("jiangSu-------------" + JSON.stringify(locs));
    if (locs[0]["id"]) {
        var url1 = "location/findLocsByParentId/" + locs[0]["id"];
        var url0 = "location/findByLocLevel/0";
        var cities0 = getSelectedData(url0, "locName");
        var cities = getSelectedData(url1, "locName");
        console.log("cities-------------" + JSON.stringify(cities0));
        console.log("cities-------------" + JSON.stringify(cities));
        initSelectData("#locationSelect", {locationsArray: cities});
    }

    //编辑或者新增界面的潮位类型选择下拉列表
    var url = "/seaLevelType/findAll";
    var typeNames = getSelectedData(url, "typeName");
    initSelectData("#seaLevelType", {typeNameArray: typeNames});

    //保存功能
    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("beachForm");
        console.log("obj-------------" + obj);
        var object = JSON.parse(obj);
        console.log("object-------------" + object);

        var url = "beach/save";
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
    //从后台获取表格中的数据
    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/vBeach/data",
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

    var url = "beach/findById/" + id;
    var beach = findByIdUrl(url);
    console.log(beach);
    for (var key in beach) {
        if (beach[key]) {
            if (key == "location") {
                var locationId = beach["location"].id;
                console.log("=====locationid=====" + JSON.stringify(locationId));
                $("#beachForm #locationSelect").val(locationId);
            } else if (key == "seaLevelType") {
                var typeNameId = beach["seaLevelType"].id;
                console.log("=====seaLevelType=====" + JSON.stringify(typeNameId));
                $("#beachForm #seaLevelType").val(typeNameId);
            }
            else {
                $("#beachForm #" + key).val(beach[key]);
            }

        }
    }
    $("#beachForm #locationSelect").select2({'width':'61%'});
    $("#beachForm #seaLevelType").select2({'width':'61%'});
}



/**
 * 用户选择多条数据，进行批量审核
 */
function authorize() {
    var selectedIds = getCheckedValues();
    if (!selectedIds) {
        showMessageBox("danger", "请选择要审核的滩涂信息！");
        return;
    }
    var url = "beach/authorizeInBatch";
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



