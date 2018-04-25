/**
 * Created by Administrator on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
docName = "系统管理-基础数据管理-项目建设基本信息";
mainObject = "sysMngProjectCons";
deleteObject = "sysMngProjectCons";
formId = "#projectConsForm";
selectArray = ["#id", "#project"];
topValue = "18%";
appName = "项目建设基本信息";
authStatus = "";//系统管理里显示所有审核状态的数据，包括已审核和未审核

$(function () {
    initMenus("topMenu");

    //搜索条件中市县查询
    city_District();

    //初始化项目名称下拉列表
    var projectUrl ="project/findVByAuthKeyAndStatus";
    var data = {authStatus: authStatus};
    var project = getMediaSelectedData(projectUrl, data, "projectName");
    initSelectData("#projectName", {projectsArray: project});
    $("#projectName").select2({'width': '180px'});

    initSelectData("#project", {projectsArray: project});
    $("#project").select2({'width': '180px'});


    //根据市名称一个条件查询项目名称
    searchProjectNameBycityOrDistrict("#cityName",authStatus);
    //根据市县名称两个条件查询项目名称
    searchProjectNameBycityOrDistrict("#districtName",authStatus);

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
        url: "/sysMngProjectCons/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='查看' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
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
    });

    formValidator();
    //保存
    $("#saveBtn").on("click", function () {
        //获取表单对象
        var bootstrapValidator = $("#projectConsForm").data('bootstrapValidator');
        // //手动触发验证
        bootstrapValidator.validate();
        //如果验证成功就保存数据
        if(bootstrapValidator.isValid()){
            var obj = getFormJsonData("projectConsForm");
            var object = JSON.parse(obj);
            console.log("obj-------------" + obj);
            var url = "sysMngProjectCons/save";
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


    //弹出框关闭后清除验证内容再实例化
    $('#confirm_modal').on('hidden.bs.modal', function() {
        $("#projectConsForm").data('bootstrapValidator').destroy();
        $('#projectConsForm').data('bootstrapValidator', null);
        formValidator();
    });


});


//编辑记录
function edit(id) {
    cleanData();  //清空下拉列表的默认选项
    console.log("id====" + id);
    var area = findByIdDB(id);
    console.log(area);
    for (var key in area["project"]) {
        $(formId + " #" + key).val(area["project"][key]);

        if (key == "beginYear" || "endDate" || "area") {
            var beginDate = area["project"]["beginYear"].substring(0, 7);
            $(formId + " #beginDate").val(beginDate);

            var endDate = area["project"]["endYear"].substring(0, 7);
            $(formId + " #endDate").val(endDate);

            var areaId = area["project"].id;
            $(formId + " #projectName").val(areaId);
            $(formId + " #projectName").select2({"width":"61%"});
        }
    }
}





//表单验证
function formValidator() {
    $(formId).bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            project: {
                message: '所属项目不能为空',
                validators: {
                    notEmpty: {
                        message: '所属项目不能为空'
                    }
                }
            },
            beginDate: {
                message: '项目开始时间',
                validators: {
                    notEmpty: {
                        message: '项目开始时间不能为空'
                    }
                }
            },
            endDate: {
                message: '项目结束时间',
                validators: {
                    notEmpty: {
                        message: '农业用地面积不能为空'
                    }
                }
            }
        }
    })
}

/**
 * 用户选择多个项目建设基本信息，进行批量授权
 */
function authorize() {
    var selectedIds = getCheckedValues();
    if (!selectedIds) {
        showMessageBox("danger", "请选择要审核的项目建设基本信息！");
        return;
    }
    var url = "sysMngProjectCons/authorizeInBatch";
    var requestData = {
        projectConsIds: selectedIds
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