/**
 * Created by Administrator on 2017/10/18.
 */

dataTableName = "#dataTableList";
docName = "系统管理-基础数据管理-项目可行性研究报告";
mainObject = "sysMngProjectReport";
deleteObject = "sysMngProjectReport";
formId = "#projectReportForm";
selectArray = ["#id", "#projectName"];
appName = "可行性研究报告";
// topValue = "30%";
authStatus = "";//系统管理里显示所有审核状态的数据，包括已审核和未审核

$(function () {
    initMenus("topMenu");

    //搜索条件中市县查询
    city_District();
    //新增或编辑界面中市县下拉列表
    cityAndDistrict();

    //初始化项目名称下拉列表
    var projectUrl = "project/findVByAuthKeyAndStatus";
    var data = {authStatus: authStatus};
    var project = getMediaSelectedData(projectUrl, data, "projectName");
    initSelectData("#projectName", {projectsArray: project});
    $("#projectName").select2({'width': '61%'});

    initSelectData("#project", {projectsArray: project});
    $("#project").select2({'width': '180px'});

    //根据市名称一个条件查询项目名称
    searchProjectNameBycityOrDistrict("#cityName",authStatus);
    //根据市县名称两个条件查询项目名称
    searchProjectNameBycityOrDistrict("#districtName",authStatus);



    //初始化表格数据
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
        url: "/sysMngProjectReport/data",
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
    });


    formValidator();
    //保存
    $("#saveBtn").on("click", function () {
        //获取表单对象
        var bootstrapValidator = $("#projectReportForm").data('bootstrapValidator');
        // //手动触发验证
        bootstrapValidator.validate();
        //如果验证成功就保存数据
        if (bootstrapValidator.isValid()) {
            var obj = getFormJsonData("projectReportForm");
            var object = JSON.parse(obj);
            console.log("obj----save---------" + obj);
            var url = "sysMngProjectReport/save";
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
            // return false;
        }
    });


    //弹出框关闭后清除验证内容再实例化
    $('#confirm_modal').on('hidden.bs.modal', function () {
        $("#projectReportForm").data('bootstrapValidator').destroy();
        $('#projectReportForm').data('bootstrapValidator', null);
        formValidator();
    });


});


//编辑记录
function edit(id) {
    cleanData();  //清空下拉列表的默认选项

    var area = findByIdDB(id);
    console.log("项目可行性研究报告-------------" + JSON.stringify(area));
    for (var key in area) {
        $(formId + " #" + key).val(area[key]);
        if (key == "project") {
            var projectId = area["project"].id;
            $(formId + " #projectName").val(projectId);
            $(formId + " #projectName").select2({'width': '61%'});

            var projectNo = area["project"].projectNo;
            $(formId + " #projectNo").val(projectNo);
        }

        if (key == "beginDate" || "endDate") {
            var beginDate = area["beginDate"].substring(0, 7);
            $(formId + " #beginDate").val(beginDate);

            var endDate = area["endDate"].substring(0, 7);
            $(formId + " #endDate").val(endDate);
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
            investedSum: {
                message: '投资金额验证失败',
                validators: {
                    notEmpty: {
                        message: '投资金额不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]\d*(\.\d*[0-9])?)|(0\.\d*[1-9])$/,
                        message: '不能输入小于0的数字'
                    }
                }
            },
            investedProvince: {
                message: '省级补助资金验证失败',
                validators: {
                    notEmpty: {
                        message: '省级补助资金不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]\d*(\.\d*[0-9])?)|(0\.\d*[1-9])$/,
                        message: '不能输入小于0的数字'
                    }
                }
            },
            investedCity:{
                message: '市县补助资金验证失败',
                validators: {
                    notEmpty: {
                        message: '市县补助资金不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]\d*(\.\d*[0-9])?)|(0\.\d*[1-9])$/,
                        message: '不能输入小于0的数字'
                    }
                }
            },
            investedSelf:{
                message: '自筹资金验证失败',
                validators: {
                    notEmpty: {
                        message: '自筹资金不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]\d*(\.\d*[0-9])?)|(0\.\d*[1-9])$/,
                        message: '不能输入小于0的数字'
                    }
                }
            }
        }
    })
}
/**
 * 用户选择多个可行性研究报告，进行批量授权
 */
function authorize() {
    var selectedIds = getCheckedValues();
    if (!selectedIds) {
        showMessageBox("danger", "请选择要审核的可行性研究报告！");
        return;
    }
    var url = "sysMngProjectReport/authorizeInBatch";
    var requestData = {
        projectReportIds: selectedIds
    };
    $.post(url, requestData, function (data) {
        if (data.result) {
            showMessageBox("info", data["resultDesc"]);
            $(dataTableName).bootgrid("reload");
        } else {
            showMessageBox("danger", data["resultDesc"]);
        }
    });
};