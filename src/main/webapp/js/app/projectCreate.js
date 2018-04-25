/**
 * Created by Administrator on 2017/11/6.
 */
var path = getGisServerIp();
var main = "project";
var main2 = "projectAll";
var mainObject = "project";
var colName = "projectName";
var landusePolygonLayer = "";
var maxId = getMaxId(main);
var basemap = "osm";
var attr = {};
var attrName = "项目";
var attrNameDB = "project";//数据库中地理信息的名称。如：海岸线为line
formId = "#projectForm";
selectArray = ["#id", "#area", "#landUsingType", "#engineeringType", "#buildStatus", "#important", "#status"];
var projectNo_ok = false;
var projectName_ok = false;
var area_ok = false;
var projectNo_reg = /^[A-Za-z0-9]{2,20}$/;
var projectName_reg = /^[\u4e00-\u9fa50-9]{2,20}$/;
var currentId = null;
var obj = {};
//点击编辑按钮所弹出的表单名称
var formIdForEdit = "#projectFormForEdit";
$(function () {
    initMenus("topMenu");

    //新增项目后弹出框界面，包含 项目基本信息 以及 可研报告、项目建设任务、项目验收报告信息
    //初始化所属区块下拉列表
    var areaUrl = "/area/findAll";
    var area = getSelectedArea(areaUrl, "areaNo", "areaName");
    initSelectData(formId + " #area", {areaArray: area});
    //编辑界面中，区块下拉列表
    initSelectData(formIdForEdit + " #area", {areaArray: area});

    //新增界面中，用地类型下拉列表
    var url = "/landUsingType/findAll";
    var landUsingTypes = getSelectedData(url, "landUsingType");
    initSelectData(formId + " #landUsingType", {landUsingTypeArray: landUsingTypes});
    //编辑界面中，用地类型下拉列表
    initSelectData(formIdForEdit + " #landUsingType", {landUsingTypeArray: landUsingTypes});

    //新增界面，工程类型下拉列表
    var url = "/engineeringType/findAll";
    var engineeringTypes = getSelectedData(url, "engineeringType");
    initSelectData(formId + " #engineeringType", {engineeringTypeArray: engineeringTypes});
    //编辑界面,工程类型下拉列表
    initSelectData(formIdForEdit + " #engineeringType", {engineeringTypeArray: engineeringTypes});


    //在地图上新增项目后弹出表单的保存按钮
    $("#saveBtn").on("click", function () {
        // console.log($("#area").val());
        currentId = $(formId + " #id").val();
        //对项目编号进行验证
        projectNo_ok = verifyProjectNo(formId + " #projectNo", projectNo_reg, currentId);
        //对项目名称进行验证
        projectName_ok = verifyProjectName(formId + " #projectName", projectName_reg, currentId);

        area_ok = getAreaOk();
        //项目编号和名称验证通过后保存
        if (projectNo_ok && projectName_ok && area_ok) {
            var obj = getFormJsonData("projectForm");
            var object = JSON.parse(obj);
            console.log("obj-------------" + obj);//打印所有的字段信息。包含 项目基本信息 以及 可研报告、项目建设任务、项目验收报告信息
            //首先保存项目，只有项目保存成功后，才能保存项目相关的其他四种信息。否则不能保存其他四种信息
            var projectUrl = "project/save";
            $.post(projectUrl, object, function (data) {
                if (data.result) {
                    showMessageBox("info", data["resultDesc"]);
                    //可行性研究报告
                    var projectReport = {
                        projectId: object["id"],
                        beginDate: object["beginDateForReport"],
                        endDate: object["endDateForReport"],
                        dykeLevel: object["dykeLevel"],
                        designStandard: object["designStandard"],
                        dykeLineSetting: object["dykeLineSetting"],
                        dykeSectionalType: object["dykeSectionalType"],
                        dykeTopHeight: object["dykeTopHeight"],
                        dykeTopWidth: object["dykeTopWidth"],
                        slopeRatio: object["slopeRatio"],
                        investedSum: object["investedSumForReport"],
                        investedProvince: object["investedProvince"],
                        investedCity: object["investedCity"],
                        investedSelf: object["investedSelf"],
                        status: object["status"]
                    };
                    var projectReportUrl = "/sysMngProjectReport/save";
                    $.post(projectReportUrl, projectReport, function (data) {
                        console.log(projectReport);
                        if (data.result) {
                            console.log("可行性研究报告保存成功！===");
                            showMessageBox("info", data["resultDesc"]);
                        } else {
                            console.log("可行性研究报告保存失败===");
                            showMessageBox("danger", data["resultDesc"]);
                        }
                    });

                    //项目建设基本信息
                    var projectCons = {
                        projectId: object["id"],
                        status: object["status"]
                    };
                    var projectConsUrl = "/sysMngProjectCons/save";
                    $.post(projectConsUrl, projectCons, function (data) {
                        console.log(projectCons);
                        if (data.result) {
                            console.log("项目建设基本信息保存成功！===");
                            showMessageBox("info", data["resultDesc"]);
                        } else {
                            console.log("项目建设基本信息保存失败===");
                            showMessageBox("danger", data["resultDesc"]);
                        }
                    });

                    //项目建设任务信息
                    var projectConsTask = {
                        projectId: object["id"],
                        beginDate: object["beginDateForTask"],
                        endDate: object["endDateForTask"],
                        consTask: object["consTask"],
                        status: object["status"]
                    };
                    var projectConsTaskUrl = "/sysMngProjectConsTask/save";
                    $.post(projectConsTaskUrl, projectConsTask, function (data) {
                        console.log(projectConsTask);
                        if (data.result) {
                            console.log("项目建设任务信息保存成功！===");
                            showMessageBox("info", data["resultDesc"]);
                        } else {
                            console.log("项目建设任务信息保存失败===");
                            showMessageBox("danger", data["resultDesc"]);
                        }
                    });

                    //项目验收基本信息
                    var projectAccept = {
                        projectId: object["id"],
                        expert: object["expert"],
                        conclusion: object["conclusion"],
                        status: object["status"]
                    };
                    var projectAcceptUrl = "/sysMngProjectAccept/save";
                    $.post(projectAcceptUrl, projectAccept, function (data) {
                        console.log(projectAccept);
                        if (data.result) {
                            console.log("项目验收基本信息保存成功！===");
                            showMessageBox("info", data["resultDesc"]);
                        } else {
                            console.log("项目验收基本信息保存失败===");
                            showMessageBox("danger", data["resultDesc"]);
                        }
                    });

                    $("#confirm_modal").modal("hide");
                } else {
                    showMessageBox("danger", data["resultDesc"]);
                    return;
                }
            });
        } else {
            showMessageBox("danger", "输入信息有误，请重新输入！");
            return false;
        }

        loadFeatures(main, colName);
    });

    //点击编辑项目弹出表单的保存按钮
    $("#saveBtnForEdit").on("click", function () {
        //对项目编号进行验证
        projectNo_ok = verifyProjectNo(formIdForEdit + " #projectNo", projectNo_reg, currentId);
        //对项目名称进行验证
        projectName_ok = verifyProjectName(formIdForEdit + " #projectName", projectName_reg, currentId);

        //编辑验证通过后保存
        if (projectNo_ok && projectName_ok) {
            var obj = getFormJsonData("projectFormForEdit");
            var object = JSON.parse(obj);
            console.log("obj-------edit------" + obj);//打印所有的字段信息。包含 项目基本信息 以及 可研报告、项目建设任务、项目验收报告信息
            //首先保存项目
            var projectUrl = "project/save";
            $.post(projectUrl, object, function (data) {
                if (data.result) {
                    $("#confirm_modal_edit").modal("hide");
                    showMessageBox("info", data["resultDesc"]);
                } else {
                    showMessageBox("danger", data["resultDesc"]);
                }
            });
        } else {
            showMessageBox("danger", "信息有误，请重新输入！");
            return false;
        }

        // var projectNameSave = $("#projectName").html();
        loadFeatures(main, colName);
    });
});

//编辑项目信息
function edit(id) {
    currentId = id;
    cleanDataForEdit();
    var project = findByIdDB(id);
    console.log(project);
    for (var key in project) {
        if (project[key]) {
            $(formIdForEdit + " #" + key).val(project[key]);
            if (key == "area") {
                var areaId = project["area"].id;
                $(formIdForEdit + " #area").val(areaId);
                $(formIdForEdit + " #area").select2({'width': '61%'});
            }
            if (key == "beginYear" || key == "endYear") {
                var beginYear = project.beginYear.substring(0, 7);
                $(formIdForEdit + " #beginYear").val(beginYear);

                var endYear = project.endYear.substring(0, 7);
                $(formIdForEdit + " #endYear").val(endYear);
            }
            if (key == "landUsingType") {
                var landUsingType = project["landUsingType"]["id"];
                $(formIdForEdit + " #landUsingType").val(landUsingType);
                $(formIdForEdit + " #landUsingType").select2({'width': '61%'});
            }
            if (key == "engineeringType") {
                var engineeringType = project["engineeringType"]["id"];
                $(formIdForEdit + " #engineeringType").val(engineeringType);
                $(formIdForEdit + " #engineeringType").select2({'width': '61%'});
            }
        }
    }
}
//
// //删除项目信息
// function remove(id) {
//     console.log("删除=====" + id);
//     var url = main + "/delete/" + id;
//     bootbox.confirm({
//         message: "确定要删除该记录么?",
//         buttons: {
//             confirm: {
//                 label: '是',
//                 className: 'btn-success'
//             },
//             cancel: {
//                 label: '否',
//                 className: 'btn-danger'
//             }
//         },
//         callback: function (result) {
//             if (result) {
//                 $.ajax({
//                     type: "GET",
//                     url: url,
//                     success: function (msg) {
//                         if (msg["result"]) {
//                             showMessageBox("info", "信息删除成功!");
//                             var Lid = "#li" + id;
//                             $("#nyc_graphics").find(Lid).remove();
//                             goPage(1, 10);
//                             landusePolygonLayer.refresh();
//                         } else {
//                             showMessageBox("danger", "信息有关联数据，无法删除，请联系管理员");
//                         }
//                     },
//                     error: function (msg) {
//                         showMessageBox("danger", "信息删除失败，请联系管理员");
//                     }
//                 });
//             }
//         }
//     });
// }

commonGisMap2(basemap, main, main2, path, colName, landusePolygonLayer, attrNameDB, attrName, obj, formId);

/**
 * 此方法是专为项目创建界面的编辑功能
 */

function cleanDataForEdit() {
    $(formIdForEdit)[0].reset();
    $("#confirm_modal .tips").html("");
    // console.log($("#confirm_modal .tips"));
    var arr = getSelectArray();
    for (var i = 0; i < arr.length; i++) {
        $(formId + " " + arr[i]).val("").change();
    }
    $("#confirm_modal_edit").modal("show");
    $("#confirm_modal_edit").css("top", getTop());
}

function getAreaOk() {
    var val = $("#area").val();
    if (val == '') {
        $(".area_error").html("所属区块不能为空").css("color", "red");
        area_ok = false;

    } else {
        $(".area_error").html("");
        area_ok = true;
    }
    return area_ok;
}