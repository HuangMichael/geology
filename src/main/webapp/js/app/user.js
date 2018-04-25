/**
 * Created by Administrator on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
docName = "用户信息";
mainObject = "vUser";
deleteObject = "user";
formName = "#dataTableList";
appName = "用户信息";
formId = "#userForm";
selectArray = ["#id", "#gender"];
topValue = "20%";
var userName_ok = false;
var userName_reg = /^[A-Za-z]{4,20}$/;
var currentId = null;
var url = "";
var obj = {};

$(function () {
    initMenus("topMenu");
    initAutoComplete("userNameSearch", "/user/findAll", "userName");


    $('.backgroundBox').find('img').each(function () {
        $(this).on('click', function () {
            console.log("hhhhhhhhh--------------");
            var url = this.src;
            $(".content").css("background",'url('+ url +') no-repeat');
            $(".content").css("background-size",'cover');
        });
    });
    /**
     * 保存用户信息
     */
    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/" + mainObject + "/data",
        converters: {//转换器，转换器由两种方法组成:将字符串转换为所需类型，或者将一个类型转换为一个具象性的字符串。
            datetime: {
                to: function (value) {
                    return transformYMD(value);
                }
            }
        },
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            },
            "upload": function (column, row) {
                return "<button class='btn btn-xm command-upload' title='上传'><i class='icon-upload'></i></button>"
            }
        }
    });


    upLoadData("/etl/uploadAndSave", 12);

    $("#saveBtn").on("click", function () {
        //新增用户的正则验证
        var $userName_error = $(".userName_error");
        if ($("#userName").val() == "") {
            $userName_error.css("color", "red");
            $userName_error.html("请输入用户名");
            userName_ok = false;
        } else if ($("#userName").val().length < 4 || $(this).val().length > 20) {
            // 长度不对
            $userName_error.html("长度只能在4-20个字符之间");
            $userName_error.css("color", "red");
            userName_ok = false;
        } else if (!$("#userName").val().match(userName_reg)) {
            // 有特殊字符
            $userName_error.html("格式错误, 仅支持大小写字母");
            $userName_error.css("color", "red");
            userName_ok = false;
        } else {
            //验证新增用户是否已存在
            if (currentId == null) {
                var userName = $("#userName").val();
                url = "user/findByUserName/";
                obj = {userName: userName};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----新增data--------" + data);
                        $userName_error.html("用户名已存在");
                        $userName_error.css("color", "red");
                        userName_ok = false;
                    } else {
                        $userName_error.html("");
                        userName_ok = true;
                    }
                })
            } else {
                var userName = $("#userName").val();
                url = "user/findUserDuplicateByUserNameAndId";
                obj = {userName: userName, id: currentId};

                $.post(url, obj, function (data) {
                    if (data) {
                        console.log("-----编辑data--------" + data);
                        $userName_error.html("用户名已存在");
                        $userName_error.css("color", "red");
                        userName_ok = false;
                    } else {
                        $userName_error.html("");
                        userName_ok = true;
                    }
                })
            }

        }
        //新增用户验证通过之后保存
        if (userName_ok) {
            var obj = getFormJsonData("userForm");
            var object = JSON.parse(obj);
            console.log("user/save-----------------------" + JSON.stringify(object));
            var url = "user/save";
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

});


/**
 * 编辑用户信息，根据id查询用户并显示在表单上
 * @param id
 */
function edit(id) {
    $(".userName_error").html("");
    currentId = id;
    cleanData();
    var url = "/user/findById/" + id;
    var userObject = findByIdUrl(url);
    // console.log("edit userObject-------------"+JSON.stringify(userObject));
    for (var key in userObject) {
        if (userObject[key]) {
            if (key == "gender") {
                var genderId = userObject["gender"].id;//获取此用户的性别
                $(formId + " #gender").val(genderId);//设置此用户性别的下拉列表的默认选项
            } else {
                $(formId + " #" + key).val(userObject[key]);
            }
        }
    }
}

function addNewData() {
    currentId = null;
    console.log(currentId);
    $(".userName_error").html("");
    cleanData();
}
