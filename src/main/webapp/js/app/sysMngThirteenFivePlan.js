/**
 * Created by Administrator on 2017/7/25.
 */
dataTableName = "#dataTableList";
docName = "系统管理-基础数据管理-十三五规划信息";
mainObject = "sysMngThirteenFivePlan";
deleteObject = "sysMngThirteenFivePlan";
appName = "十三五规划";
formId = "#areaForm";
selectArray = ["#id","#area","#city","#district"];
topValue = "18%";
authStatus = "";

$(function () {
    initMenus("topMenu");

    //初始化所属区块下拉列表
    var areaUrl = "/area/findAll";
    var area = getSelectedArea(areaUrl, "areaNo", "areaName");
    initSelectData("#area", {areaArray: area});

    //初始化搜索条件市区下拉列表
    city_District();

    //新增或编辑界面中市县下拉列表
    cityAndDistrict();

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
        url: "/sysMngThirteenFivePlan/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        },
        converters: {//转换器，转换器由两种方法组成:将字符串转换为所需类型，或者将一个类型转换为一个具象性的字符串。
            datetime: {
                // to: function (value) {return moment(value).format('YYYY-MM');}
                to: function (value) {
                    return value.substring(0, 7);
                }
            }
        }
    })


    formValidator();
    //保存
    $("#saveBtn").on("click", function () {
        //获取表单对象
        var bootstrapValidator = $("#areaForm").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        //如果验证成功就保存数据
        if(bootstrapValidator.isValid()){
            var obj = getFormJsonData("areaForm");
            var object = JSON.parse(obj);
            object["memo"]= $("#areaForm #area option:selected").text();
            console.log("obj-with-memo-----------" + JSON.stringify(object));
            var url = "sysMngThirteenFivePlan/save";
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
});

//编辑记录
function edit(id) {
    cleanData();  //清空下拉列表的默认选项

    var area = findByIdDB(id);
    console.log(JSON.stringify(area));
    for (var key in area) {
        $("#areaForm #" + key).val(area[key]);
        if (key == "city") {
            var cityId = area["city"].id;
            $("#areaForm #city").val(cityId);
            $("#areaForm #city").select2({'width':'61%'});
        }
        if (key == "district") {
            var parentId = $("#city").val();
            var url = "location/findLocsByParentId/" + parentId;
            var district = getSelectedData(url, "locName");
            // console.log("----------"+JSON.stringify(district));
            for (var i = 0; i < district.length; i++) {
                var option = new Option(district[i]["text"], district[i]["id"]);
                $("#district").append(option);
            }
            ;
            var districtId = area["district"].id;
            $("#areaForm #district").val(districtId);
            $("#areaForm #district").select2({'width':'61%'});

        }

        if (key == "beginYear" || "endYear") {
            var beginYear = area.beginYear.substring(0, 7);
            $("#areaForm #beginYear").val(beginYear);

            var endYear = area.endYear.substring(0, 7);
            $("#areaForm #endYear").val(endYear);
        }

        if (key == "area") {
            var areaId = area["area"].id;
            console.log(areaId);
            $("#areaForm #area").val(areaId);
            $("#areaForm #area").select2({'width':'61%'});
        }
    }


}




/**
 * 用户选择多个十三五规划，进行批量授权
 */
function authorize() {
    var thirteenFivePlanIds = getCheckedValues();
    if (!thirteenFivePlanIds) {
        showMessageBox("danger", "请选择要审核的十三五规划！");
        return;
    }
    var url = "sysMngThirteenFivePlan/authorizeInBatch";
    var requestData = {
        thirteenFivePlanIds:thirteenFivePlanIds
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

/**
 * 表单验证
 */
function formValidator() {
    $("#areaForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            reportSize: {
                message: '上报面积验证失败',
                validators: {
                    notEmpty: {
                        message: '上报面积不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]\d*(\.\d*[0-9])?)|(0\.\d*[1-9])$/,
                        message: '不能输入小于0的数字'
                    }
                }
            },
            buildSize: {
                message: '总规划面积验证失败',
                validators: {
                    notEmpty: {
                        message: '总规划面积不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]\d*(\.\d*[0-9])?)|(0\.\d*[1-9])$/,
                        message: '不能输入小于0的数字'
                    }
                }
            },
            nyBuildSize: {
                message: '农业用地面积验证失败',
                validators: {
                    notEmpty: {
                        message: '农业用地面积不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]\d*(\.\d*[0-9])?)|(0\.\d*[1-9])$/,
                        message: '不能输入小于0的数字'
                    }
                }
            },
            stBuildSize: {
                message: '生态用地面积验证失败',
                validators: {
                    notEmpty: {
                        message: '生态用地面积不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]\d*(\.\d*[0-9])?)|(0\.\d*[1-9])$/,
                        message: '不能输入小于0的数字'
                    }
                }
            },
            jsBuildSize: {
                message: '建设用地面积验证失败',
                validators: {
                    notEmpty: {
                        message: '建设用地面积不能为空'
                    },
                    regexp: {
                        regexp: /^([0-9]\d*(\.\d*[0-9])?)|(0\.\d*[1-9])$/,
                        message: '不能输入小于0的数字'
                    }
                }
            }
        }
    });
}




