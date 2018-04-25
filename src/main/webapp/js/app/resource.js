/**
 * Created by Administrator on 2017/5/22 0022.
 */
dataTableName = "#dataTableList";
docName = "菜单信息";
mainObject = "menu";
deleteObject = "menu";
appName = "菜单管理";
formId = "#menuForm";
selectArray = ["#id"];

$(function () {
    initMenus("topMenu");


    $("#menuName").change(function () {
        //验证新增用户是否已存在
        var $userName_error = $(".userName_error");
        var menuName = $("#menuName").val();
        var url = "menu/findBymenuName/" + menuName;
        var obj = {
            menuName: menuName
        };

        $.post(url, obj, function (data) {
            if (data) {
                $userName_error.html("菜单名称已存在");
                $userName_error.css("color", "red");
                $userName_error.prev().css("border-color", "red");
            } else {
                $userName_error.html("");
            }
        });
    })
    $("#menuForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            menuName: {
                message: '菜单名称验证失败',
                validators: {
                    notEmpty: {
                        message: '菜单名称不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 8,
                        message: '菜单名称长度必须在2到8位之间'
                    },
                    regexp: {
                        regexp: /^[\u4e00-\u9fa5_a-zA-Z]+$/,
                        message: '菜单名称只能包含中文、大小写字母，不能包含空格下划线等'
                    }
                }
            }
        },
    })


    //点击保存按钮

    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("menuForm");
        var object = JSON.parse(obj);
        var url = "menu/save";
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
        url: "/menu/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑'  onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        }
    });

    $("#depName").select2();
});

function edit(id) {
    cleanData();  //清空下拉列表的默认选项
    var url = "/menu/findById/" + id;
    var menuObject = findByIdUrl(url);
    console.log("edit menuObject-------------" + JSON.stringify(menuObject));
    for (var key in menuObject) {
        $("#menuForm #" + key).val(menuObject[key]);
    }

    $("#departmentSelect").select2({'width':'61%'});
}


