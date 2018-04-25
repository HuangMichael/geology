/**
 * Created by Administrator on 2017/5/22 0022.
 */
dataTableName = "#dataTableList";
docName = "人员信息";
mainObject = "vPerson";
deleteObject = "person";
appName = "人员管理";
formId = "#personForm";
selectArray = ["#id", "#departmentSelect", "#gender"];
tableId = 5;

$(function () {
    initMenus("topMenu");

    var url = "department/findAll";
    var departments = getSelectedData(url, "depName");
    initSelectData("#depName", {departmentsArray: departments});
    initSelectData("#departmentSelect", {departmentsArray: departments});

    var url = "/person/findGenders";
    var genders = getSelectedData(url, "value");
    initSelectData("#gender", {genderArray: genders});

    //调用表单验证
    formValidator();


    //点击保存按钮
    $("#saveBtn").on("click", function () {
        console.log(" before save person------------");
        //获取表单对象
        var bootstrapValidator = $("#personForm").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        //如果验证成功就保存数据
        if (bootstrapValidator.isValid()) {
            var obj = getFormJsonData("personForm");
            var object = JSON.parse(obj);
            console.log(" before save person------------" + JSON.stringify(object));
            var url = "person/save";
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
    $('#confirm_modal').on('hidden.bs.modal', function () {
        $("#personForm").data('bootstrapValidator').destroy();
        $('#personForm').data('bootstrapValidator', null);
        formValidator();
    });


    var grid = $(dataTableName).bootgrid({
        ajax: true,
        selection: true,
        multiSelect: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/" + mainObject + "/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑'  onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        }

    });

    $("#depName").select2();
});


//表单验证
function formValidator() {
    $("#personForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            personName: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '人员姓名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 8,
                        message: '用户名长度必须在2到8位之间'
                    },
                    regexp: {
                        regexp: /^[\u4e00-\u9fa5_a-zA-Z]+$/,
                        message: '用户名不能包含数字'
                    }
                }
            },
            birthDate: {
                message: '出生年月验证失败',
                validators: {
                    notEmpty: {
                        message: '出生年月不能为空'
                    }
                }
            }
        }
    });
}


function edit(id) {
    cleanData();  //清空下拉列表的默认选项
    var url = "/person/findById/" + id;
    var personObject = findByIdUrl(url);
    for (var key in personObject) {
        if (personObject[key]) {
            if (key == "department") {
                var departmentId = personObject["department"].id;//获取此所属部门的id
                $("#personForm #departmentSelect").val(departmentId);//设置所属部门下拉列表的默认选项
            } else if (key == "gender") {
                var genderId = personObject["gender"].id;//获取此所属部门的id
                $("#personForm #gender").val(genderId);//设置所属部门下拉列表的默认选项
            } else {
                $("#personForm #" + key).val(personObject[key]);
            }
        }
    }
    $("#gender").select2({'width': '61%'});
    $("#departmentSelect").select2({'width': '61%'});
}

Dropzone.options.myDropzone = {
    url: "/etl/uploadAndSave",
    paramName: "file",
    maxFilesize: 10.0,// MB
    maxFiles: 1,
    acceptedFiles: ".xls,.xlsx",//预判断，限制文件类型，用户只能选择excel文件
    autoProcessQueue: false,//是否立马上传
    addRemoveLinks: true,//是否添加删除功能
    parallelUploads: 1, //最大并行处理量
    init: function () {
        myDropzone = this;
        this.on("addedfile", function (file) {
            //上传文件时触发的事件
            var fileName = file.name;
            //再次判断用户选择的文件类型，如果不是excel文件则警告
            if (fileName.indexOf(".xls") < 0 && fileName.indexOf(".xlsx") < 0) {
                showMessageBox("danger", "文件类型错误！请上传Excel文件！");
                return;
            }
        });
        this.on('sending', function (file, xhr, formData) {
            //传递参数时在sending事件中formData，需要在前端代码加enctype="multipart/form-data"属性
            formData.append('tableId', currentTableId);
        });
        $("#button_upload").on("click", function () {
            myDropzone.processQueue();
        });
        this.on("queuecomplete", function (file) {
        });
        this.on("removedfile", function (file) {
            console.log("删除一个文件");
        });
        this.on("success", function (file, data) {
            $("#import_modal").modal("hide");
            //上传完成后触发的方法
            console.log(data);
            if (data.result) {
                showMessageBox("info", data["resultDesc"]);
            } else {
                showMessageBox("danger", data["resultDesc"]);
            }

        });
    }
}
