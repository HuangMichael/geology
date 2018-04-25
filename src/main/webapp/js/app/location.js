dataTableName = "#dataTableList";
docName = "位置信息";
mainObject = "location";
deleteObject = "location";
var currentLocation;    //当前点击的城市
appName = "位置信息";
formId = "#locationForm";
// selectArray = ["#id"];


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
function onClick(event, treeId, treeNode, clickFlag) {
    var id = treeNode.id;
    currentLocation = findLocById(id);//获取当前点击的位置
    console.log("-------click node ----------" + JSON.stringify(currentLocation));
    //更新右上部的位置基本信息表
    for (var key in currentLocation) {
        if (currentLocation[key]) {
            $("#locForm #" + key).val(currentLocation[key]);
        }
    }
    //获取此位置的上级位置，在右上部的位置基本信息表设置父级位置
    var pLoc = findParentByLocCode(currentLocation["locCode"]);
    if (pLoc != null) {
        $("#locForm #parent").val(pLoc["locName"]);//设置上级位置
    } else {
        $("#locForm #parent").val("");//没有上级位置则显示空
    }


    //更新右下部的位置列表，显示该位置所有的子孙节点，并且添加编辑删除按钮
    // $(dataTableName).innerHTML = "";


    var url = "/location/findLocsByLocCode/" + currentLocation["locCode"];
    // console
    // var grid = $(dataTableName).bootgrid({
    //     ajax: true,
    //     post: function () {
    //         return {
    //             id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
    //         };
    //     },
    //     url: "/location/findLocsByLocCode/" + currentLocation["locCode"],
    //     formatters: {
    //         "commands": function (column, row) {
    //             return "<button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
    //                 "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></a>";
    //         }
    //     }
    // });
    //
    // console.log("-------click----------");
}


function initZTree() {
    var zNodes = [];
    //使用ajax的get方法获取所有的行政区划：json数组
    $.get("location/findTree", function (data) {
        for (var x in data) {
            zNodes[x] = {
                id: data[x]["id"],
                pId: data[x]["pid"],
                name: data[x]["name"],
                t: data[x]["t"],
                open: true
            }
        }
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });

}

$(function () {
    initMenus("topMenu");
    highLight();


    //查找江苏省所有的城市和区县
    var url = "location/findAll";
    var locs = getSelectedData(url, "locName");
    initSelectData("#parentSelect", {parentsArray: locs});

    initZTree();

    /**
     * 当位置名称失去焦点时，验证位置名称有效性
     */
    formValidator();



    //点击保存按钮，保存新增位置数据或者保存当前位置的数据
    $("#saveBtn").on("click", function () {
        //获取表单对象
        var bootstrapValidator = $("#locationForm").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        //如果验证成功就保存数据
        if(bootstrapValidator.isValid()){
            var locStr = getFormJsonData("locationForm");
            var locObject = JSON.parse(locStr);
            //如果位置名称无效，不能保存
            // var isLocNameValid = locNameValidation("#locName");
            // if (isLocNameValid == false) {
            //     return;
            // }
            var parentId = $("#parentSelect").val();
            var locLevelUrl = "location/findByLocLevel/0";//返回值是一个列表，查找江苏省的信息
            var locs = findByIdUrl(locLevelUrl);
            var jiangsuId = locs[0]["id"];

            //如果非江苏省的位置的父节点无效，不能保存;江苏省不能有父节点
            if ((locObject["id"] != jiangsuId && parentId == "") || (locObject["id"] == jiangsuId && parentId != "")) {
                showMessageBox("danger", "请选择有效的上级位置！");
                return;
            }

            if (locObject["id"]) {//此时包含id、locCode、locLevel属性，为编辑数据后保存
                var cLoc = findLocById(locObject["id"]);
                var parentLoc = findParentByLocCode(cLoc["locCode"]);//原来的父级位置
                console.log("====edit===cLoc========" + JSON.stringify(cLoc) + "====edit===parentLoc========" + JSON.stringify(parentLoc));
                //如果是江苏省 或者 不是江苏省且没有修改父级位置，则原封不动保存位置编码和位置等级
                if (locObject["id"] == jiangsuId || (locObject["id"] != jiangsuId && parentId == parentLoc["id"])) {
                    if (cLoc["locCode"]) {
                        locObject["locCode"] = cLoc["locCode"];
                    }
                    locObject["locLevel"] = cLoc["locLevel"];
                }
                //选择了新的上级位置，就需要重新计算该位置的位置编码和位置等级
                else {
                    //保存的时候要生成新增位置的locCode信息：根据上级位置的locCode计算出新增位置的locCode。
                    //如果上级位置没有子位置，则新增位置的locCode直接加1.如果上级位置有子位置，则找出最大值后加1.
                    var pLoc = findLocById(parentId);//新选择的父级位置
                    var subLocsList = findLocsByParentId(parentId);//上级位置的子位置列表
                    console.log("=======pLocCode========" + pLoc["locCode"] + "------------subLocsList------------" + JSON.stringify(subLocsList));
                    if (subLocsList == null || subLocsList.length == 0) {
                        locObject.locCode = pLoc["locCode"] + "01";//上级位置的位置编码后面+01
                    } else {//subLocsList子位置列表按照位置编码升序排列
                        for (var x = subLocsList.length - 1; x >= 0; x--) {
                            if (subLocsList[x].locCode) {
                                locObject.locCode = locCodeIncrease1(subLocsList[x].locCode);
                                break;
                            }
                        }
                    }
                    locObject["locLevel"] = pLoc["locLevel"] + 1;
                }
                console.log("last----------edit------------" + JSON.stringify(locObject));
            }
            else {//此时不包含id、locCode、locLevel属性，为新增数据后保存
                var pLoc = findLocById(parentId);
                var subLocsList = findLocsByParentId(parentId);//上级位置的子位置列表
                console.log("=======pLocCode========" + pLoc["locCode"] + "------------subLocsList------------" + JSON.stringify(subLocsList));
                if (subLocsList == null || subLocsList.length == 0) {
                    locObject.locCode = pLoc["locCode"] + "01";//上级位置的位置编码后面+01
                } else {//subLocsList子位置列表按照位置编码升序排列
                    for (var x = subLocsList.length - 1; x >= 0; x--) {
                        if (subLocsList[x].locCode) {
                            locObject.locCode = locCodeIncrease1(subLocsList[x].locCode);
                            break;
                        }
                    }
                }
                locObject["locLevel"] = pLoc["locLevel"] + 1;
                console.log("last----------new------------" + JSON.stringify(locObject));
            }

            var url = "location/save";
            $.post(url, locObject, function (data) {
                if (data.result) {
                    //保存成功就更新ztree
                    initZTree();

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
        }
    );

//初始化位置列表，并且添加编辑删除按钮
    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/location/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        }
    })


    //弹出框关闭后清除验证内容再实例化
    $('#confirm_modal').on('hidden.bs.modal', function() {
        $("#locationForm").data('bootstrapValidator').destroy();
        $('#locationForm').data('bootstrapValidator', null);
        formValidator();
    });

})
;


/**
 * 对位置编码最后两位加一。如“010104”加一后变成“010105”
 * @param locCode
 */
function locCodeIncrease1(locCode) {
    if (locCode && locCode.length >= 2) {
        var len = locCode.length;
        var leftStr = locCode.substring(0, len - 2);
        var rightStr = locCode.substring(len - 2);
        var rightInt = parseInt(rightStr) + 1;
        if (rightInt < 10) {
            rightStr = "0" + rightInt;
        } else {
            rightStr = rightInt.toString();
        }
    }
    return leftStr + rightStr;
}

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
 * 根据id查询该位置的父节点的详细信息
 */
function findParentByLocCode(locCode) {
    var location = null;
    $.ajaxSettings.async = false;
    var url = "location/findParentByLocCode/" + locCode;
    $.get(url, function (data) {
        location = data;
    });
    return location;
}


/**
 * 根据id查询该位置的子节点位置列表
 */
function findLocsByParentId(id) {
    var locations = null;
    $.ajaxSettings.async = false;
    var url = "location/findLocsByParentId/" + id;
    $.get(url, function (data) {
        locations = data;
    });
    return locations;
}

/**
 * 根据id编辑记录
 * @param id
 */

function edit(id) {
    cleanData();  //清空下拉列表的默认选项
    var currentLoc = findByIdDB(id);
    console.log("edit Object-------------" + JSON.stringify(currentLoc));
    //设置不包含上级位置的其他信息
    //如{"id":4,"locCode":"0103","locName":"南通市","locLevel":1,"locDesc":"南通市","status":"1","handler":{},"hibernateLazyInitializer":{}}
    for (var key in currentLoc) {
        if (currentLoc[key]) {
            $("#locationForm #" + key).val(currentLoc[key]);
        }
    }
    //设置上级位置信息
    var currentLocCode = currentLoc["locCode"];
    if (currentLocCode != null && currentLocCode != "") {
        var pLoc = findParentByLocCode(currentLocCode);
        if (pLoc != null && pLoc.id != null) {//江苏省的父节点为null
            $("#locationForm #parentSelect").val(pLoc.id);
        }
    }

    $("#locationForm #parentSelect").select2({'width':'61%'});
}

/**
 * 根据id删除记录
 * @param id
 */
function del(id) {
    var url = "location/delete/" + id;
    console.log("del url-------------" + url);
    //if (bid) {
    bootbox.confirm({
        message: "确定要删除该记录么?",
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
                $.ajax({
                    type: "GET",
                    url: url,
                    success: function (msg) {
                        console.log("del msg-------------" + JSON.stringify(msg));
                        if (msg["result"]) {
                            //删除成功就更新ztree
                            initZTree();

                            $(dataTableName).bootgrid("reload");
                            showMessageBox("info", "信息删除成功!");
                        } else {
                            showMessageBox("danger", "信息有关联数据，无法删除，请联系管理员");
                        }
                    },
                    error: function (msg) {
                        console.log("del msg-------------" + JSON.stringify(msg));
                        showMessageBox("danger", "信息删除失败，请联系管理员");
                    }
                });
            }
        }
    });
}

/**
 * 点击新增按钮
 */
function addNewData() {
    $('#locationForm')[0].reset();
    $("#locationForm #id").val("").change();
    //判断当前用户点击选择的是哪个城市，新增位置时默认显示该位置为上级位置
    if (currentLocation != null) {
        $("#locationForm #parentSelect").val(currentLocation["id"]).change();
    }
    else {
        $("#locationForm #parentSelect").val("").change();//设置父级位置的下拉列表的默认选项:--请选择上级位置--
    }
    $("#confirm_modal").modal("show");
}



//表单验证
function formValidator() {
    $("#locationForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            locName: {
                message: '位置名称验证失败',
                validators: {
                    notEmpty: {
                        message: '位置名称不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: '位置名称长度在2到30之间'
                    },
                    regexp: {
                        regexp: /^[\u4e00-\u9fa5_a-zA-Z]+$/,
                        message: '用户名支持中文大小写字母'
                    }
                }
            },
            remote: {
                url: 'location/findByLocName/',
                message: '位置名称已存在',
                type: 'POST',
                data: function() {
                    return {
                        locName: $("#locName").val()
                    };
                }
            }
        }
    });
}

