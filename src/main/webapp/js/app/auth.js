dataTableName = "#dataTableList";
docName = "授权管理-菜单信息";
mainObject = "auth";
appName = "授权管理";
var currentMenu;    //当前点击的城市
formId = "#authForm";
selectArray = ["#id", "#parentSelect"];

var setting = {
    check: {
        enable: true
    },

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
function onClick(event, treeId, treeNode, clickFlag) {
    var id = treeNode.id;
    currentMenu = findMenuById(id);//获取当前点击的位置
    console.log("-------click node ----------" + JSON.stringify(currentMenu));
    //更新右上部的位置基本信息表
    for (var key in currentMenu) {
        if (currentMenu[key]) {
            $("#locForm #" + key).val(currentMenu[key]);
        }
    }
    var pLoc = findParentByLocCode(currentMenu["locCode"]);
    if (pLoc != null) {
        $("#locForm #parent").val(pLoc["locName"]);//设置上级位置
    } else {
        $("#locForm #parent").val("");//没有上级位置则显示空
    }
}


function initZTree() {
    var zNodes = [];
    //使用ajax的get方法获取所有的行政区划：json数组
    $.get("menu/findMenuTree", function (data) {
        for (var x in data) {
            var open = (data[x]["code"] + "").length < 6;
            zNodes[x] = {
                id: data[x]["id"],
                pId: data[x]["pid"],
                name: data[x]["name"],
                t: data[x]["t"],
                open: open
            }
        }
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });

}

$(function () {
    initMenus("topMenu");
    highLight();
    initRoleSelectBox("#roleSelect");

    initZTree();

    //初始化菜单列表
    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/menu/data"
    })
});



/**
 * 根据id查询位置详细信息
 */
function findMenuById(id) {
    var menu = null;
    $.ajaxSettings.async = false;
    var url = "menu/findById/" + id;
    $.get(url, function (data) {
        menu = data;
    });
    return menu;
}


/**
 * 根据id查询该位置的父节点的详细信息
 */
function findParentByLocCode(menuNo) {
    var menu = null;
    $.ajaxSettings.async = false;
    var url = "menu/findParentByMenuNo/" + menuNo;
    $.get(url, function (data) {
        menu = data;
    });
    return menu;
}


/**
 * 根据id查询该位置的子节点位置列表
 */
function findSubMenusByParentId(id) {
    var menus = null;
    $.ajaxSettings.async = false;
    var url = "menu/findSubMenusByParentId/" + id;
    $.get(url, function (data) {
        menus = data;
    });
    return menus;
}

/**
 * 点击新增按钮
 */
function addNewData() {
    $('#authForm')[0].reset();
    //判断当前用户点击选择的是哪个城市，新增位置时在该位置下面添加子位置，默认显示该位置为上级位置
    if (currentMenu != null) {
        $("#authForm #parentSelect").val(currentMenu["id"]).change();
    }
    else {
        $("#authForm #parentSelect").val("");//设置父级位置的下拉列表的默认选项:--请选择上级位置--
    }
    $("#confirm_modal").modal("show");
}


/**
 * 初始化角色下拉选择
 */
function initRoleSelectBox(roleBoxId) {
    $.ajaxSettings.async = false;
    var url = "role/findAll";
    $.get(url, function (data) {
        for (var x in data) {
            if (data[x]["id"] && data[x]["roleName"]) {
                $(roleBoxId).append("<option value='" + data[x]['id'] + "' >" + data[x]['roleName'] + "</option>");
            }
        }
    });
}


/**
 * 将已经选择的菜单授权给选择的角色
 */
function auth4Role() {

    $.ajaxSettings.async = false;
    var roleId = $("#roleSelect option:selected").val();
    var menusIds = getSelectedMenuIds().join(",");

    if (!roleId) {
        showMessageBox("danger", "请在下拉列表中选择要授权的角色!");
        return;
    }
    if (!(getSelectedMenuIds().length > 0)) {
        showMessageBox("danger", "请在树形菜单中选择要授权的菜单!");
        return;
    }

    bootbox.confirm({
        message: "确定将选择的菜单授权给该角色么?",
        buttons: {
            confirm: {
                label: '是',
                className: 'btn-success'
            },
            cancel: {
                label: '否',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result) {
                var url = "auth/auth4Role";
                var params = {roleId: roleId, menusIds: menusIds};
                $.post(url, params, function (data) {
                    showMessageBox(data.result ? "info" : "danger", data["resultDesc"]);
                });
            }
        }
    });
}



