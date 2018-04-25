/**
 * Created by Administrator on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
docName = "围垦区块信息";
mainObject = "vArea";
deleteObject = "area";
formName = "";
appName = "围垦区块";
formId = "#areaForm";
selectArray = ["#id", "#city", "#district", "#functionType", "#areaType"];
topValue = "18%";
authStatus = "";

var areaNo_ok = false;
var areaName_ok = false;
var numberValue_ok = false;//所有涉及到面积、金额、长度等信息，均不能为空，且其值>=0

var areaNo_reg = /^[A-Za-z0-9\-]{2,20}$/;
var areaName_reg = /^[\u4e00-\u9fa5-]{2,20}$/;
var currentId = null;
var url = "";
var obj = {};

$(function () {
    initMenus("topMenu");
    highLight();

    //新增或编辑界面中市县下拉列表
    cityAndDistrict();

    //搜索条件中市县查询
    city_District();


    //编辑或者新增界面的围垦区块功能类型选择下拉列表
    var areaFunctionTypeUrl = "areaFunctionType/findAll";
    var areaFunctionType = findByIdUrl(areaFunctionTypeUrl);

    var functionTypes = getSelectedData(areaFunctionTypeUrl, "functionType");
    initSelectData("#functionType", {functionTypesArray: functionTypes});


    //编辑或者新增界面的区块类型选择下拉列表
    var areaTypeUrl = "/areaType/findAll";
    var areaType = findByIdUrl(areaTypeUrl);
    // console.log("parent-------------" + JSON.stringify(parent));

    var areaTypes = getSelectedData(areaTypeUrl, "type");
    // console.log("departments-------------" + JSON.stringify(departments));
    initSelectData("#areaType", {areaTypesArray: areaTypes});

    /**
     *
     * 保存新增区块数据
     *
     * */
    $("#saveBtn").on("click", function () {
        //新增和编辑区块时验证
        var $areaNo_error = $(".areaNo_error");
        if ($("#areaNo").val() == "") {
            $areaNo_error.css("color", "red");
            $areaNo_error.html("请输入区块编号");
            areaNo_ok = false;
        } else if (!$("#areaNo").val().match(areaNo_reg)) {
            // 有特殊字符
            $areaNo_error.html("格式错误, 仅支持字母和数字！");
            $areaNo_error.css("color", "red");
            areaNo_ok = false;
        } else {
            //新增和编辑时验证区块编号
            if (currentId == null) {
                var areaNo = $("#areaNo").val();
                url = "area/findByAreaNo";
                obj = {areaNo: areaNo};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----新增data--------" + data);
                        $areaNo_error.html(data["resultDesc"]);
                        $areaNo_error.css("color", "red");
                        areaNo_ok = false;
                    } else {
                        $areaNo_error.html("");
                        areaNo_ok = true;
                    }
                })
            } else {
                var areaNo = $("#areaNo").val();
                url = "area/findAreaDuplicateByNoAndId";
                obj = {areaNo: areaNo, id: currentId};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----编辑data--------" + data);
                        $areaNo_error.html(data["resultDesc"]);
                        $areaNo_error.css("color", "red");
                        areaNo_ok = false;
                    } else {
                        $areaNo_error.html("");
                        areaNo_ok = true;
                    }
                })
            }
        }

        //新增和编辑时验证区块名称
        var $areaName_error = $(".areaName_error");
        if ($("#areaName").val() == "") {
            $areaName_error.css("color", "red");
            $areaName_error.html("请输入区块名称");
            areaName_ok = false;
        } else if (!$("#areaName").val().match(areaName_reg)) {
            // 有特殊字符
            $areaName_error.html("仅支持数字字母横杠");
            $areaName_error.css("color", "red");
            areaName_ok = false;
        } else {
            //验证新增角色是否已存在
            if (currentId == null) {
                var areaName = $("#areaName").val();
                url = "area/findByAreaName";
                obj = {areaName: areaName};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----新增data--------" + data);
                        $areaName_error.html(data["resultDesc"]);
                        $areaName_error.css("color", "red");
                        roleName_ok = false;
                    } else {
                        $areaName_error.html("");
                        areaName_ok = true;
                    }
                })
            } else {
                var areaName = $("#areaName").val();
                url = "area/findAreaDuplicateByNameAndId";
                obj = {areaName: areaName, id: currentId};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----编辑data--------" + data);
                        $areaName_error.html(data["resultDesc"]);
                        $areaName_error.css("color", "red");
                        areaName_ok = false;
                    } else {
                        $areaName_error.html("");
                        areaName_ok = true;
                    }
                })
            }
        }

        //验证用户输入的数值是否正确
        numberValue_ok = verifyInputNumbers(".forVerify");

        //新增和编辑验证通过后保存
        if (areaNo_ok && areaName_ok && numberValue_ok) {
            var obj = getFormJsonData("areaForm");
            console.log("=========保存区块数据=========" + JSON.stringify(obj));
            var object = JSON.parse(obj);
            console.log(object);
            var url = "area/save";
            $.post(url, object, function (data) {
                if (data.result) {
                    $(dataTableName).bootgrid("reload");
                    $("#confirm_modal").modal("hide");
                    showMessageBox("info", data["resultDesc"]);
                } else {
                    showMessageBox("danger", data["resultDesc"]);
                }
            })
        } else {
            showMessageBox("danger", "信息不对，保存不成功");
            return false;
        }
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
        url: "vArea/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        }
    })
});


//编辑记录
function edit(id) {
    currentId = id;
    $(".areaNo_error").html("");
    $(".areaName_error").html("");
    cleanData();  //清空下拉列表的默认选项

    var url = "area/findById/" + id;
    var area = findByIdUrl(url);
    console.log(JSON.stringify(area));
    for (var key in area) {
        $("#areaForm #" + key).val(area[key]);
        if (key == "city") {
            var cityId = area["city"]["id"];
            $("#areaForm #city").val(cityId);
            $("#areaForm #city").select2({'width': '61%'});
        } else if (key == "district") {
            //当值是区县时，获取市的id查询出所有区县，创建option，追加到区县下拉选择菜单里
            var parentId = $("#areaForm #city").val();
            var url = "/location/findLocsByParentId/" + parentId;
            $.get(url, function (data) {
                for (var x in data) {
                    // var option = new Option(data[x]["locName"],data[x]["id"]);
                    $("#areaForm #district").append("<option value='" + data[x]['id'] + "' >" + data[x]['locName'] + "</option>");
                }
            });
            var districtId2 = area["district"]["id"];
            $("#areaForm #district").val(districtId2);
            $("#areaForm #district").select2({'width': '61%'});
        } else if (key == "functionType") {
            var cityId = area["functionType"]["id"];
            $("#areaForm #functionType").val(cityId);
            $("#areaForm #functionType").select2({'width': '61%'});
        } else if (key == "areaType") {
            var cityId = area["areaType"]["id"];
            $("#areaForm #areaType").val(cityId);
            $("#areaForm #areaType").select2({'width': '61%'});
        }
    }
}


/**
 * 新增区块信息
 */
function addNewData() {
    currentId = null;
    console.log(currentId);
    $(".areaNo_error").html("");
    $(".areaName_error").html("");
    cleanData();
}

/**
 * 用户选择多个区块，进行批量授权
 */
function authorize() {
    var areaIds = getCheckedValues();
    if (!areaIds) {
        showMessageBox("danger", "请选择要审核的区块！");
        return;
    }
    var url = "area/authorizeInBatch";
    var requestData = {
        areaIds: areaIds
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


