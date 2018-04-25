dataTableName = "#dataTableList";
var usersNotInLocationTable = "#usersNotInLocation";
docName = "位置信息";
mainObject = "location";
topValue = "18%";
var currentLocation;    //当前点击的城市

var setting = {
    data: {
        key: {
            title: "t"
        },
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: onClick
    }
};

/**
 * 点击某个位置节点的事件
 * @param event
 * @param treeId
 * @param treeNode
 * @param clickFlag
 */
var locAuthorizedUsersGrid;//点击某个位置后显示该位置已授权的用户列表
function onClick(event, treeId, treeNode, clickFlag) {
    var locId = treeNode.id;//获取位置id
    setFormValue(locId);
    //先销毁上一次生成的bootgrid，然后重新获取数据绑定
    if (locAuthorizedUsersGrid != null) {
        $(dataTableName).bootgrid("destroy");
    }
    locAuthorizedUsersGrid = $(dataTableName).bootgrid({
        templates: {
            actionButton: ""
        },
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
                locId: locId
            };
        },
        url: "/user/getLocationUsersMyPage",
        formatters: {
            "commands": function (column, row) {
                return "<a class='btn btn-danger' title='取消授权' onclick='revokeLoc(" + row.id + ")'>取消授权</a>"
            }
        }
    })
}


function initZTree() {
    var zNodes = [];
    //使用ajax的get方法获取所有的行政区划：json数组
    $.get("location/findTree", function (data) {
        for (var x = 0; x < data.length; x++) {
            zNodes[x] = {
                id: data[x]["id"],
                pId: data[x]["pid"],
                name: data[x]["name"],
                t: data[x]["t"],
                open: true
            }
        }
        $.fn.zTree.init($("#tree"), setting, zNodes);
    });

}

$(function () {
    initMenus("topMenu");
    highLight();
    initZTree();
    setFormValue(15);

    $(dataTableName).bootgrid({
        templates: {
            actionButton: ""
        },
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
                locId: 15
            };
        },
        url: "/user/getLocationUsersMyPage",
        formatters: {
            "commands": function (column, row) {
                return "<a class='btn btn-danger' title='取消授权' onclick='revokeLoc(" + row.id + ")'>取消授权</a>"
            }
        }
    })
});


/**
 * 根据id查询位置详细信息
 */
function findLocById(id) {
    var location = null;
    $.ajaxSettings.async = false;
    var url = "location/findById/" + id;
    $.get(url, function (data) {
        location = data;
    });
    return location;
}

/**
 *
 * @param locId 位置id
 * 设置表单显示属性
 */
function setFormValue(locId) {
    console.log("setFormValue=========" + locId);
    currentLocation = findLocById(locId);//获取当前点击的位置
    for (var key in currentLocation) {
        if (currentLocation[key]) {
            $("#locForm #" + key).val(currentLocation[key]);
        }
    }
    setFormReadStatus("#locForm", true);
}

/**
 * 获取选中的复选框的值:用户id列表
 * @returns
 */
function getCheckedValuesForUsers() {
    var selectedIds = $(usersNotInLocationTable).bootgrid("getSelectedRows");  //选择所有name="'test'"的对象，返回数组
    //取到id数组后，组装为字符串
    var result = selectedIds.length > 0 ? selectedIds.join(",") : "";
    console.log("usersNotInLocationTable === selectedIds---------------" + result);
    return result;
}

/**
 * 选择好用户列表后，点击保存按钮进行数据授权
 */
function grantDataAuth() {
    var locationId = getSelectedNode().id;
    var userIds = getCheckedValuesForUsers();
    if (!userIds) {
        showMessageBox("danger", "请选择要数据授权的用户！");
        return;
    }
    //ajax将选中的用户进行与位置进行关联
    var url = "dataAuth/grantDataAuth";
    var data = {
        locationId: locationId,
        userIds: userIds
    };
    $.post(url, data, function (data) {
        if (data.result) {
            $("#userListModal").modal("hide");
            $(dataTableName).bootgrid("reload");
            showMessageBox("info", data["resultDesc"]);
        } else {
            showMessageBox("danger", data["resultDesc"]);
        }
    });
}


/**
 *  点击一个位置后，点击新增授权按钮，弹出未授权的用户列表
 */
var locNotAuthorizedUsersGrid;//点击某个位置后显示该位置还未进行授权的用户列表
function grant() {
    var selectNode = getSelectedNode();
    if (!selectNode) {
        showMessageBox("danger", "请选择位置，再进行数据授权!");
        return;
    }
    var locationId = selectNode["id"];
    //先销毁上一次生成的bootgrid，然后重新获取数据绑定
    if (locNotAuthorizedUsersGrid != null) {
        $(usersNotInLocationTable).bootgrid("destroy");
    }
    locNotAuthorizedUsersGrid = $(usersNotInLocationTable).bootgrid({
        templates: {
            actionButton: ""
        },
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
                locId: locationId
            };
        },
        url: "/user/popUsersMyPage"
    });
    $("#userListModal").modal("show");
}

// var selectedUsersId = [];
//
// /**
//  * 弹出不在该位置下的用户信息列表框  选择后  授权给当前位置
//  */
// function loadUserData(locationId) {
//     $("#usersDiv").html("");
//     //弹出选择用户框
//     var url = "user/popUsers/" + locationId;
//     var html = "";
//     $.ajaxSettings.async = false;
//     $.getJSON(url, function (data) {
//         console.log(data);
//         for (var x in data) {
//             if (data[x]["id"]) {
//                 if (data[x]['person'] == null) {
//                     html += "<tr class='gradeX'>";
//                     html += "<td>" + data[x]['id'] + "</td>";
//                     html += "<td>" + data[x]['id'] + "</td>";
//                     html += "<td>" + data[x]['userName'] + "</td>";
//                     html += "<td></td>";
//                     html += "<td>" + data[x]['status'] + "</td>";
//                     html += "</tr>";
//                 } else {
//                     html += "<tr class='gradeX'>";
//                     html += "<td>" + data[x]['id'] + "</td>";
//                     html += "<td>" + data[x]['id'] + "</td>";
//                     html += "<td>" + data[x]['userName'] + "</td>";
//                     html += "<td>" + data[x]['person']['personName'] + "</td>";
//                     html += "<td>" + data[x]['status'] + "</td>";
//                     html += "</tr>";
//                 }
//
//             }
//         }
//         $("#usersDiv").html(html);
//         $("#usersNotInLocation").bootgrid({
//             selection: true,
//             multiSelect: true,
//             rowSelect: false,
//             keepSelection: true
//         }).on("selected.rs.jquery.bootgrid", function (e, rows) {
//             for (var x in rows) {
//                 if (rows[x]["id"]) {
//                     selectedUsersId.push(rows[x]["id"]);
//                 }
//             }
//         }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
//             for (var x in rows) {
//                 selectedUsersId.remove(rows[x]["id"]);
//             }
//         });
//     });
// }


/**
 *
 * @param userId
 */
function revokeLoc(userId) {
    //从当前位置中移除该用户
    var url = "dataAuth/revokeLoc";
    var params = {
        userId: userId
    };
    $.post(url, params, function (data) {
        if (data.result) {
            showMessageBox("info", data["resultDesc"]);
            $(dataTableName).bootgrid("reload");
        } else {
            showMessageBox("danger", data["resultDesc"])
        }
    });
}


