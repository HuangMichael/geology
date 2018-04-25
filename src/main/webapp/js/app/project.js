/**
 * Created by Administrator on 2017/5/22 0022.
 */
dataTableName = "#dataTableList";
docName = "系统管理-基础数据管理-项目信息";
mainObject = "project";
deleteObject = "project";
appName = "项目信息";
formId = "#projectForm";
selectArray = ["#id", "#area", "#city", "#district", "#landUsingType", "#engineeringType"];
topValue = "18%";
authStatus = "";
var projectNo_ok = false;
var projectName_ok = false;
var numberValue_ok = false; //所有涉及到面积、金额、长度等信息，均不能为空，且其值>=0
var contact_ok = false;     //联系方式验证结果

var projectNo_reg = /^[A-Za-z0-9]{2,20}$/;
var projectName_reg = /^[\u4e00-\u9fa5-]{2,20}$/;
var mobileContact_reg = /^1\d{10}$/;//手机号码验证表达式
var phoneContact_reg = /^0\d{2,3}-?\d{7,8}$/;//电话号码验证表达式

var currentId = null;
var url = "";
var obj = {};

$(function () {
    initMenus("topMenu");

    //初始化项目名称下拉列表
    var projectUrl = "project/findVByAuthKeyAndStatus";
    var data = {authStatus: authStatus};
    var project = getMediaSelectedData(projectUrl, data, "projectName");
    initSelectData("#project", {projectsArray: project});
    $("#project").select2({'width': '180px'});


    //搜索条件中市县查询
    city_District();

    //根据市名称一个条件查询项目名称
    searchProjectNameBycityOrDistrict("#cityName", authStatus);
    //根据市县名称两个条件查询项目名称
    searchProjectNameBycityOrDistrict("#districtName", authStatus);


    //初始化所属区块下拉列表
    var areaUrl = "/area/findAll";
    var area = getSelectedArea(areaUrl, "areaNo", "areaName");
    initSelectData("#area", {areaArray: area});

    //新增或者编辑界面中，用地类型下拉列表
    var url = "/landUsingType/findAll";
    var landUsingTypes = getSelectedData(url, "landUsingType");
    initSelectData("#landUsingType", {landUsingTypeArray: landUsingTypes});

    //新增或者编辑界面中，工程类型下拉列表
    var url = "/engineeringType/findAll";
    var engineeringTypes = getSelectedData(url, "engineeringType");
    initSelectData("#engineeringType", {engineeringTypeArray: engineeringTypes});

    $("#saveBtn").on("click", function () {
        //新增和编辑项目编号验证
        var $projectNo_error = $(".projectNo_error");
        if ($("#projectNo").val() == "") {
            $projectNo_error.css("color", "red");
            $projectNo_error.html("请输入项目编号");
            projectNo_ok = false;
        } else if (!$("#projectNo").val().match(projectNo_reg)) {
            // 有特殊字符
            $projectNo_error.html("格式错误");
            $projectNo_error.css("color", "red");
            projectNo_ok = false;
        } else {
            //新增和编辑时验证区块编号
            if (currentId == null) {
                var projectNo = $("#projectNo").val();
                url = "project/findByProjectNo";
                obj = {projectNo: projectNo};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----新增data--------" + data);
                        $projectNo_error.html("项目编号已存在");
                        $projectNo_error.css("color", "red");
                        projectNo_ok = false;
                    } else {
                        $projectNo_error.html("");
                        projectNo_ok = true;
                    }
                })
            } else {
                var projectNo = $("#projectNo").val();
                url = "project/findProjectDuplicateByNoAndId";
                obj = {projectNo: projectNo, id: currentId};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----编辑data--------" + data);
                        $projectNo_error.html("项目编号已存在");
                        $projectNo_error.css("color", "red");
                        projectNo_ok = false;
                    } else {
                        $projectNo_error.html("");
                        projectNo_ok = true;
                    }
                })
            }
        }

        //新增和编辑时验证项目名称
        var $projectName_error = $(".projectName_error");
        if ($("#projectName").val() == "") {
            $projectName_error.css("color", "red");
            $projectName_error.html("请输入项目名称");
            projectName_ok = false;
        }
        // else if (!$("#projectName").val().match(projectName_reg)) {
        //     // 有特殊字符
        //     $projectName_error.html("格式错误, 不支持数字");
        //     $projectName_error.css("color", "red");
        //     projectName_ok = false;
        // }
        else {
            //验证新增角色是否已存在
            if (currentId == null) {
                var projectName = $("#projectName").val();
                url = "project/findByProjectName";
                obj = {projectName: projectName};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----新增data--------" + data);
                        $projectName_error.html("项目名称已存在");
                        $projectName_error.css("color", "red");
                        roleName_ok = false;
                    } else {
                        $projectName_error.html("");
                        projectName_ok = true;
                    }
                })
            } else {
                var projectName = $("#projectName").val();
                url = "project/findProjectDuplicateByNameAndId";
                obj = {projectName: projectName, id: currentId};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----编辑data--------" + data);
                        $projectName_error.html("项目名称已存在");
                        $projectName_error.css("color", "red");
                        projectName_ok = false;
                    } else {
                        $projectName_error.html("");
                        projectName_ok = true;
                    }
                })
            }
        }

        //验证用户输入的数值是否正确
        numberValue_ok = verifyInputNumbers(".forVerify");
        contact_ok = verifyContact("#leaderContact");
        //新增和编辑验证通过后保存
        if (projectNo_ok && projectName_ok && numberValue_ok && contact_ok) {
            var obj = getFormJsonData("projectForm");
            var object = JSON.parse(obj);

            console.log("obj-------------" + obj);
            var url = "project/save";
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
            showMessageBox("danger", "信息有误，保存失败");
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
        url: "/project/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        },
        converters: {//转换器，转换器由两种方法组成:将字符串转换为所需类型，或者将一个类型转换为一个具象性的字符串。
            datetime: {
                to: function (value) {
                    return value.substring(0, 7);
                }
            }
        }
    })
});


//编辑记录
function edit(id) {
    currentId = id;
    $(".projectNo_error").html("");
    $(".projectName_error").html("");
    cleanData();
    var project = findByIdDB(id);
    for (var key in project) {
        if (project[key]) {
            $("#projectForm #" + key).val(project[key]);
            if (key == "area") {
                var areaId = project["area"].id;
                console.log(areaId);
                $("#projectForm #area").val(areaId);
                $("#projectForm #area").select2({'width': '61%'});
            }
            // if (key == "city") {
            //     var cityId = project["city"].id;
            //     $("#projectForm #city").val(cityId);
            //     $("#projectForm #city").select2({'width': '61%'});
            // }
            // if (key == "district") {
            //     var parentId = $("#city").val();
            //     var url = "location/findLocsByParentId/" + parentId;
            //     var district = getSelectedData(url, "locName");
            //     for (var i = 0; i < district.length; i++) {
            //         var option = new Option(district[i]["text"], district[i]["id"]);
            //         $("#projectForm #district").append(option);
            //     }
            //     var districtId = project["district"].id;
            //     $("#projectForm #district").val(districtId);
            //     $("#projectForm #district").select2({'width': '61%'});
            // }
            if (key == "beginYear" || key == "endYear") {
                var beginYear = project.beginYear.substring(0, 7);
                $("#projectForm #beginYear").val(beginYear);

                var endYear = project.endYear.substring(0, 7);
                $("#projectForm #endYear").val(endYear);
            }
            if (key == "landUsingType") {
                var landUsingType = project["landUsingType"]["id"];
                $("#projectForm #landUsingType").val(landUsingType);
                $("#projectForm #landUsingType").select2({'width': '61%'});
            }
            if (key == "engineeringType") {
                var engineeringType = project["engineeringType"]["id"];
                $("#projectForm #engineeringType").val(engineeringType);
                $("#projectForm #engineeringType").select2({'width': '61%'});
            }
        }
    }

}


/**
 * 点击新增按钮
 */
function addNewData() {
    currentId = null;
    $(".projectNo_error").html("");
    $(".projectName_error").html("");
    cleanData();
}


/**
 * 用户选择多个项目，进行批量授权
 */
function authorize() {
    var projectIds = getCheckedValues();
    if (!projectIds) {
        showMessageBox("danger", "请选择要审核的项目！");
        return;
    }
    var url = "project/authorizeInBatch";
    var requestData = {
        projectIds: projectIds
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
