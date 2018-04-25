/**
 * Created by Administrator on 2017/8/1.
 */
dataTableName = "#dataTableList";
docName = "系统管理-地理信息管理-沿海沙洲信息";
mainObject = "vSandLand";
deleteObject = "sandLand";
appName = "沿海沙洲信息";
formId = "#sandLandForm";
selectArray = ["#id", "#locationSelect"];
var locationSelect_error = $(".locationSelect_error");

$(function () {
    initMenus("topMenu");
    //编辑或者新增界面的行政区划选择下拉列表
    var locLevelUrl = "location/findByLocLevel/0";//返回值是一个列表，查找江苏省的信息
    var locs = findByIdUrl(locLevelUrl);
    console.log("jiangSu-------------" + JSON.stringify(locs));
    if (locs[0]["id"]) {
        var url = "location/findLocsByParentId/" + locs[0]["id"];
        var cities = getSelectedData(url, "locName");
        console.log("cities-------------" + JSON.stringify(cities));
        initSelectData("#locationSelect", {locationsArray: cities});
    }
    //提示错误信息
    locationSelect_error.html("");
    $("#locationSelect").change(
        function () {
            if (!$("#locationSelect").val()) {
                locationSelect_error.html("请选择有效的行政区划！");
                locationSelect_error.css("color", "red");
                // locationSelect_error.prev().css("border-color", "red");
            } else {
                locationSelect_error.html("");
            }
        }
    );

    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("sandLandForm");
        var object = JSON.parse(obj);
        console.log("=====sandLand===saveBtn=====" + JSON.stringify(object));


        promptMessage();

        var url = "sandLand/save";
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
        url: "/vSandLand/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        }
    })


});


//编辑某一条记录
function edit(id) {
    console.log("js里的编辑");
    cleanData();  //清空下拉列表的默认选项

    var url = "sandLand/findById/" + id;
    var sandLand = findByIdUrl(url);
    console.log(sandLand);
    for (var key in sandLand) {
        if (sandLand[key]) {
            if (key == "location") {
                var locationId = sandLand["location"].id;
                console.log("=====locationid=====" + JSON.stringify(locationId));
                $("#sandLandForm #locationSelect").val(locationId);
            } else {
                $("#sandLandForm #" + key).val(sandLand[key]);
            }
        }
    }
    $("#locationSelect").select2({'width': '61%'});

    //提示错误信息
    promptMessage();

}


function promptMessage() {
    //提示错误信息
    locationSelect_error.html("");

    if (!$("#locationSelect").val()) {
        locationSelect_error.html("请选择有效的行政区划！");
        locationSelect_error.css("color", "red");
    } else {
        locationSelect_error.html("");
    }
}


/**
 * 用户选择多条数据，进行批量审核
 */
function authorize() {
    var selectedIds = getCheckedValues();
    if (!selectedIds) {
        showMessageBox("danger", "请选择要审核的沿海沙洲信息！");
        return;
    }
    var url = "sandLand/authorizeInBatch";
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