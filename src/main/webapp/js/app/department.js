dataTableName = "#dataTableList";
docName = "部门信息";
mainObject = "department";
deleteObject = "department";
appName = "部门管理";
formId = "#depForm";
selectArray = ["#id", "#parent", "#location"];
var validator;
$(function () {
    initMenus("topMenu");



    //编辑或者新增界面的所在市选择下拉列表
    var locLevelUrl = "location/findByLocLevel/0";//返回值是一个列表，查找江苏省的信息
    var locs = findByIdUrl(locLevelUrl);
    // console.log("jiangSu-------------" + JSON.stringify(locs));
    if (locs[0]["id"]) {
        var url = "location/findLocsByParentId/" + locs[0]["id"];
        var cities = getSelectedData(url, "locName");
        // console.log("cities-------------" + JSON.stringify(cities));
        initSelectData("#location", {locationsArray: cities});
    }

    //编辑或者新增界面的上级部门选择下拉列表
    var parentUrl = "department/findAll";
    var parent = findByIdUrl(parentUrl);
    // console.log("parent-------------" + JSON.stringify(parent));

    var departments = getSelectedData(parentUrl, "depName");
    // console.log("departments-------------" + JSON.stringify(departments));
    initSelectData("#parent", {departmentsArray: departments});

    //调用表单验证
   formValidator();


    //保存新增数据
    $("#saveBtn").on("click", function () {
        // formValidator();
        //获取表单对象
        var bootstrapValidator = $("#depForm").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        //如果验证成功就保存数据
        if(bootstrapValidator.isValid()){
            var obj = getFormJsonData("depForm");
            var object = JSON.parse(obj);
            console.log(" before save person------------" + JSON.stringify(object));
            var url = "department/save";
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


    //弹出框关闭后清除验证内容再实例化
    $('#confirm_modal').on('hidden.bs.modal', function() {
        $("#depForm").data('bootstrapValidator').destroy();
        $('#depForm').data('bootstrapValidator', null);
        formValidator();
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
        url: "/" + mainObject +"/data",

        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑'  onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        }

    });

});


//表单验证
function formValidator() {
   $("#depForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            depNo: {
                message: '部门编号验证失败',
                validators: {
                    notEmpty: {
                        message: '部门编号不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 3,
                        message: '部门编号长度必须在2到10位之间'
                    },
                    regexp: {
                        regexp: /^[0-9]{2,10}$/,
                        message: '部门编号只能包含数字'
                    }
                }
            },
            depName: {
                message: '部门名称验证失败',
                validators: {
                    notEmpty: {
                        message: '部门名称不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 20,
                        message: '部门名称长度必须在2到20位之间'
                    },
                    regexp: {
                        regexp: /^[\u4e00-\u9fa5]{2,20}$/,
                        message: '部门名称只能包含中文'
                    },
                    remote: {
                        url: 'department/findByDepName/',
                        message: '此部门已存在',
                        type: 'POST',
                        data: function() {
                            return {
                                depName: $("#depName").val()
                            };
                        }
                    }
                }
            },
            depDesc: {
                message: '部门描述验证失败',
                validators: {
                    notEmpty: {
                        message: '部门描述不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 40,
                        message: '部门描述长度必须在2到40位之间'
                    },
                    regexp: {
                        regexp: /[\u4e00-\u9fa5]/,
                        message: '部门描述只能包含中文，不能包含大小写字母、空格下划线等'
                    }
                }
            },
            manager: {
                message: '部门负责人验证失败',
                validators: {
                    notEmpty: {
                        message: '部门负责人不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 5,
                        message: '部门负责人长度必须在2到5位之间'
                    },
                    regexp: {
                        regexp: /^[\u4e00-\u9fa5]{2,5}$/,
                        message: '部门负责人姓名可包含中文，大小写字母，不能包含数字，下划线等特殊字符'
                    }
                }
            },
        }
    })
}

function edit(id) {
    cleanData();  //清空下拉列表的默认选项
    var department = findByIdDB(id);
    console.log(department);

    for (var key in department) {
        if (department[key]) {
            if (key == "location") {
                var locationId = department["location"].id;
                $("#depForm #location").val(locationId);
                $("#depForm #location").select2({'width':'61%'});
            } else if (key == "parent") {
                var parentId = department["parent"].id;
                $("#depForm #parent").val(parentId);
                $("#depForm #parent").select2({'width':'61%'});
            }
            else {
                $("#depForm #" + key).val(department[key]);
            }
        }
    }
}


