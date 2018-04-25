/**
 * Created by Administrator on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
docName = "角色信息";
mainObject = "role";
deleteObject = "role";
formName = "roleForm";
var userIdsString = "";
var currentRoleId;
appName = "角色授权";
formId = "#roleForm";
selectArray = ["#id"];
var roleName_reg = /^[a-zA-Z\u4e00-\u9fa5]+$/;
var roleName_ok = false;
var currentId = null;
var url = "";
var obj = {};
/**
 * 查询不是该角色用户的用户列表
 * @param id
 * @returns {*}
 */
function getOtherUsersByRoleId(id) {
    $.ajaxSettings.async = false;
    var object = null;
    var url = "/" + mainObject + "/getOtherUsers/" + id;
    $.getJSON(url, function (data) {
        object = data;
    });
    return object;
}

$(function () {
    initMenus("topMenu");
    initAutoComplete("roleNameSearch", "role/findAll", "roleName");
    //新增和编辑角色时的保存按钮
    $("#saveBtn").on("click", function () {
        //新增和编辑角色时验证
        var $roleName_error = $(".roleName_error");
        if ($("#roleName").val() == "") {
            $roleName_error.css("color", "red");
            $roleName_error.html("请输入角色名");
            roleName_ok = false;
        } else if (!$("#roleName").val().match(roleName_reg)) {
            // 有特殊字符
            $roleName_error.html("格式错误, 不支持数字");
            $roleName_error.css("color", "red");
            roleName_ok = false;
        } else {
            //验证新增角色是否已存在
            if (currentId == null) {
                var roleName = $("#roleName").val();
                url = "role/findByRoleName";
                obj = {roleName: roleName};

                $.post(url, obj, function (data) {
                    if (data) {
                        $roleName_error.html("用户名已存在");
                        $roleName_error.css("color", "red");
                        roleName_ok = false;
                    } else {
                        $roleName_error.html("");
                        roleName_ok = true;
                    }
                })
            } else {
                var roleName = $("#roleName").val();
                url = "role/findRoleDuplicateByRoleNameAndId";
                obj = {roleName: roleName, id: currentId};

                $.post(url, obj, function (data) {
                    if (data) {
                        $roleName_error.html("用户名已存在");
                        $roleName_error.css("color", "red");
                        roleName_ok = false;
                    } else {
                        $roleName_error.html("");
                        roleName_ok = true;
                    }
                })
            }
        }

        //新增和编辑角色验证通过后保存
        if (roleName_ok) {
            var obj = getFormJsonData("roleForm");
            var object = JSON.parse(obj);
            var url = "role/save";
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


    //添加用户时的保存按钮
    $("#addUserSaveBtn").on("click", function () {
        userIdsString = getCheckValues("addUserTableList");
        if (userIdsString != null && userIdsString.length > 0) {
            var url = "role/addUsers";
            var object = {userIds: userIdsString, roleId: currentRoleId};
            $.post(url, object, function (data) {
                if (data.result) {
                    $("#addUser_confirm_modal").modal("hide");
                    showMessageBox("info", data["resultDesc"]);
                } else {
                    showMessageBox("danger", data["resultDesc"]);
                }
            })
        } else {
            showMessageBox("danger", "请选择用户！");
            return;
        }
    });


    //添加用户时的保存按钮
    $("#removeUserSaveBtn").on("click", function () {
        userIdsString = getCheckValues("removeUserTableList");
        if (userIdsString != null && userIdsString.length > 0) {
            var url = "role/removeUsers";
            var object = {userIds: userIdsString, roleId: currentRoleId};
            $.post(url, object, function (data) {
                if (data.result) {
                    $("#removeUser_confirm_modal").modal("hide");
                    showMessageBox("info", data["resultDesc"]);
                } else {
                    showMessageBox("danger", data["resultDesc"]);
                }
            })
        } else {
            showMessageBox("danger", "请选择用户！");
            return;
        }
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
        url: "/" + mainObject + "/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            },
            "addUser": function (column, row) {
                return "<button class='btn btn-xm command-addUser' title='添加' onclick='addUser(" + row.id + ")'><i class='icon-plus'></i></button>"
            },
            "removeUser": function (column, row) {
                return "<button class='btn btn-xm command-removeUser' title='删除' onclick='removeUser(" + row.id + ")'><i class='icon-minus'></i></button>"
            }
        }
    })
});

/**
 * 根据id编辑角色信息
 * @param id
 */
function edit(id) {
    $(".roleName_error").html("");
    currentId = id;
    cleanData();
    var mainObject = findByIdDB(id);
    for (var key in mainObject) {
        if (mainObject[key]) {
            $("#roleForm #" + key).val(mainObject[key]);
        }
    }
    $("#confirm_modal").modal("show");
}


/**
 * 点击添加用户，弹出未添加的用户列表（不属于该角色的用户列表）
 * @param id
 */
var addUserGrid;

var removeUserGrid;

function addUser(id) {
    currentRoleId = id;
    //首先销毁上一次生成的bootgrid，然后重新获取数据绑定
    if (addUserGrid != null) {
        $("#addUserTableList").bootgrid("destroy");
    }
    //获取用户的列表信息
    addUserGrid = $("#addUserTableList").bootgrid({
        templates: {
            actionButton: ""
        },
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
                roleId: id
            };
        },
        url: "/role/getOtherUsersMyPage"
    });
    $("#addUser_confirm_modal").modal("show");
}

function removeUser(id) {
    currentRoleId = id;
    //首先销毁上一次生成的bootgrid，然后重新获取数据绑定
    if (removeUserGrid != null) {
        $("#removeUserTableList").bootgrid("destroy");
    }
    //获取用户的列表信息
    removeUserGrid = $("#removeUserTableList").bootgrid({
        templates: {
            actionButton: ""
        },
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
                roleId: id
            };
        },
        url: "/role/getMyUsersMyPage"
    });
    $("#removeUser_confirm_modal").modal("show");
}

/**
 * 获取选中的复选框的值:用户id列表
 * @returns
 */
function getCheckValues(gridName) {
    var selectedIds = $("#" + gridName).bootgrid("getSelectedRows");  //选择所有name="'test'"的对象，返回数组
    //取到id数组后，组装为字符串
    var result = selectedIds.length > 0 ? selectedIds.join(",") : "";
    return result;
}

function addNewData() {
    currentId = null;
    console.log(currentId);
    $(".roleName_error").html("");
    cleanData();
}