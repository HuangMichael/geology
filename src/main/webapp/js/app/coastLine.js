/**
 * Created by Administrator on 2017/5/22 0022.
 */
dataTableName = "#dataTableList";
docName = "系统管理-地理信息管理-海岸线信息";
mainObject = "vCoastLine";
deleteObject = "coastLine";
appName = "海岸线信息";
formId = "#coastLineForm";
selectArray = ["#id","#locationSelect","#typeNameSelect"];

$(function () {
    initMenus("topMenu");

    //编辑或者新增界面的行政区划选择下拉列表
    var locLevelUrl = "location/findByLocLevel/0";//返回值是一个列表，查找江苏省的信息
    var locs = findByIdUrl(locLevelUrl);
    console.log("jiangSu-------------" + JSON.stringify(locs));
    if (locs[0]["id"]) {
        var url = "location/findLocsByParentId/" + locs[0]["id"];
        var cities = getSelectedData(url, "locName");
        // console.log("cities-------------" + JSON.stringify(cities));
        initSelectData("#locationSelect", {locationsArray: cities});
    }


    //编辑或者新增界面的海岸线类型选择下拉列表
    var url = "/coastLineType/findAll";
    var typeNames = getSelectedData(url, "typeName");
    initSelectData("#typeNameSelect", {typeNameArray: typeNames});


    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("coastLineForm");
        console.log("obj-------------" + obj);
        var object = JSON.parse(obj);

        // console.log("obj-------------" + JSON.stringify(object));
        var url = "coastLine/save";
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
        url: "/vCoastLine/data",
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
    $("#confirm_modal").modal("show");
    var url = "coastLine/findById/"+id;
    var coastLine = findByIdUrl(url);
    console.log("coastLine=========" + JSON.stringify(coastLine));
    for (var key in coastLine) {
        if (coastLine[key]) {
            if (key == "location") {
                var locationId = coastLine["location"].id;
                // console.log("=====locationid=====" + JSON.stringify(locationId));
                $("#coastLineForm #locationSelect").val(locationId);
            } else if (key == "coastLineType") {
                var typeNameId = coastLine["coastLineType"].id;
                // console.log("=====coastLineType=====" + JSON.stringify(typeNameId));
                $("#coastLineForm #typeNameSelect").val(typeNameId);
            }
            else {
                $("#coastLineForm #" + key).val(coastLine[key]);
            }

        }
    }
    $("#locationSelect").select2({'width':'61%'});
    $("#typeNameSelect").select2({'width':'61%'});
}




/**
 * 用户选择多条数据，进行批量审核
 */
function authorize() {
    var selectedIds = getCheckedValues();
    if (!selectedIds) {
        showMessageBox("danger", "请选择要审核的海岸线信息！");
        return;
    }
    var url = "coastLine/authorizeInBatch";
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