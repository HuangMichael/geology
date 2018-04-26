/**
 * Created by Administrator on 2016/11/4.
 */
var isClicked//是否点击ztree
var authStatus = "已审核";
var mainObject = "";
var deleteObject = "";
var exportObject = mainObject;
var vdm = null; //定义form数据模型
var formName = "";
var selectedIds = [];
var pointer = null;
var dataTableName = "";
var ids = [];//所有的ID的集合
var docName = "";
var formTab = null;
var locs = [];
var eqClasses = [];
var eqs = [];
var lines = [];
var stations = [];
var units = []; //外委单位信息
var searchModel = [];
var expiredCount = 0;

var formReadOnly = true;

var appName = "";
// var areaId = "";
var src = "";
var statusClassName = "";
var statusTitle = "";
var n = 0;
var formId = "";
var selectArray = [];
var topValue = "";
var classUrl = "";
var message = "";
$(function () {

    //查询统计与系统管理  回车键实现搜索功能
    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];

        if (e && e.keyCode == 13) {
            complexSearch();
            return false;//阻止默认行为，搜索之后不能自动刷新
        }
    };
})

//多媒体管理 回车键实现搜索功能
function mediaOnkeydown() {
    //查询统计与系统管理  回车键实现搜索功能
    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];

        if (e && e.keyCode == 13) {
            showAllFile(mediaComplexSearch(media));
        }
    };
}

/**
 *
 * @returns {string}
 * 获取主对象
 */
function getMainObject() {
    return mainObject;
}

/**
 * @returns {string}
 * 获取删除对象
 */

function getDeleteObject() {
    return deleteObject;
}

function getDataTableName() {
    return dataTableName;
}

/**
 *
 * @returns {string}
 * 获取表单名称
 */
function getFormName() {
    return formName;
}

/**
 *
 * @param validationConfig
 */
function validateForm(validationConfig) {
    $(formName)
        .bootstrapValidator(validationConfig)
        .on('success.form.bv', function (e) {
            e.preventDefault();

            console.log("formName----------------" + formName);
            saveMainObject(formName);
        });
}

/**
 *保存或者更新 后刷新数据列表
 * */
function saveMainObject(formName) {
    var objStr = getFormDataAsJSON(formName);
    var object = JSON.parse(objStr);
    console.log("save" + JSON.stringify(object));
    var url = getMainObject() + "/save";
    $.post(url, object, function (data) {
        if (data.result) {
            showMessageBox("info", data["resultDesc"]);
            setFormReadStatus(formName, true);
            formTab.data("status", "saved");
            $(dataTableName).bootgrid("reload");
        } else {
            showMessageBox("danger", data["resultDesc"]);
            setFormReadStatus(formName, false);
        }
    });
}


function saveTree(formName, childZNode) {
    var objStr = getFormJsonData(formName);
    var object = JSON.parse(objStr);
    var url = mainObject + "/save";

    $.ajax({
        type: "POST", url: url, data: object, dataType: "JSON", success: function (obj) {

            if (object.id) {
                updateNode(null, childZNode);
                showMessageBox("info", "信息更新成功");
            } else {
                addNodeAfterChangeOperation(null, childZNode, parent);
                showMessageBox("info", "信息添加成功")
            }
        }, error: function (msg) {
            if (object.id) {
                showMessageBox("danger", "信息更新失败")
            } else {
                showMessageBox("danger", "信息添加失败")
            }
        }
    })
}


/**
 *根据id查询返回对象
 * */
function findById(id) {
    var mainObject = getMainObject();
    var object = null;
    if (!id) {
        id = selectedIds[pointer];
    }
    var url = mainObject + "/findById/" + id;
    $.getJSON(url, function (data) {
        object = data;
    });
    return object;
}

/**
 *根据id查询返回对象
 * */
function findByIdDB(id) {
    $.ajaxSettings.async = false;
    var mainObject = getMainObject();
    var object = null;
    var url = mainObject + "/findById/" + id;
    $.getJSON(url, function (data) {
        object = data;
    });
    return object;
}


/**
 *根据id查询返回对象
 * */
function findByIdUrl(url) {
    $.ajaxSettings.async = false;
    var object = null;
    $.getJSON(url, function (data) {
        object = data;
    });
    return object;
}


/**
 *根据查询返回对象
 * */
function findListByUrl(url) {
    $.ajaxSettings.async = false;
    var object = [];
    $.getJSON(url, function (data) {
        object = data;
    });
    return object;
}


/**
 *根据查询返回对象
 * */
function findListByUrlWithParam(url, param) {
    $.ajaxSettings.async = false;
    var object = [];
    $.post(url, param, function (data) {
        object = data;
    });
    return object;
}

/**
 *  上一条 记录
 */
function backwards() {
    pointer = pointer ? pointer : 0;
    if (pointer <= 0) {
        showMessageBoxCenter("danger", "center", "当前记录是第一条");
    } else {
        pointer = pointer - 1;
        //判断当前指针位置
        showDetail(selectedIds[pointer]);
    }
}

/**
 *  下一条记录
 */
function forwards() {

    pointer = pointer ? pointer : 0;
    if (pointer >= selectedIds.length - 1) {
        showMessageBoxCenter("danger", "center", "当前记录是最后一条");

    } else {
        pointer = pointer + 1;
        showDetail(selectedIds[pointer]);
    }
}


/**
 * 新增预防性维修计划
 */
function add() {
    setFormReadStatus(formName, false);
    vdm.$set(mainObject, null);
    //设置设备状态和运行状态默认值;
    formTab.tab('show');

}

/**
 * 编辑记录 使文本框可编辑
 */


/**
 * 保存设备信息
 */
function save() {
    $("#saveBtn").trigger("click");
}


/**
 * 删除记录
 */
function del(id) {
    // //删除时判断当前form的状态
    // var status = formTab.data("status");
    // if (status == "add") {
    //     showMessageBoxCenter("danger", "center", "新建记录未保存，无需删除该记录!");
    //     return;
    // }
    // //判断选中的tab
    // var bid = selectedIds[0];
    // if (!bid) {
    //     showMessageBoxCenter("danger", "center", "请选中一条记录再操作");+ bid
    //     return;
    // }
    // var id = $(this).parent().parent().children().eq(0).text();
    // console.log(id)
    var url = getDeleteObject() + "/delete/" + id;

    console.log("删除---------" + url);
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
                            showMessageBox("info", msg["resultDesc"]);
                            $(dataTableName).bootgrid("reload");
                        } else {
                            showMessageBox("danger", msg["resultDesc"]);
                        }
                    },
                    error: function (msg) {
                        console.log("del msg-------------" + JSON.stringify(msg));
                        showMessageBox("danger", msg["resultDesc"]);
                    }
                });
            }
        }
    });
    // }
}


/**
 * 显示明细信息
 */
function showDetail(id) {
    var object = findById(id);
    vdm.$set(getMainObject(), object);
    setFormReadStatus(formName, true);
}


/**
 *
 * @param formId 设置form为只读
 */
function setFormReadStatus(formId, formLocked) {
    if (formLocked) {
        $(formId + " input ").attr("readonly", "readonly");
        $(formId + " textarea ").attr("readonly", "readonly");
        $(formId + " select").attr("disabled", "disabled");
    } else {
        $(formId + " input").attr("readonly", "readonly").removeAttr("readonly");
        $(formId + " select").attr("disabled", "disabled").removeAttr("disabled");
        $(formId + " textarea").attr("readonly", "readonly").removeAttr("readonly");

    }
}

/**
 *查询所有的id
 * */
function findAllRecordId() {
    $.ajaxSettings.async = false;
    var url = getMainObject() + "/findAllIds";
    $.getJSON(url, function (data) {
        ids = data;
    });
    console.log("ids----------" + JSON.stringify(ids));
    return ids;
}


/**
 *查询所有的id
 * */
function selectAllIds() {
    $.ajaxSettings.async = false;
    var url = getMainObject() + "/selectAllIds";
    $.getJSON(url, function (data) {
        ids = data;
    });
    return ids;
}

/**
 *导出excel
 */
function exportExcel() {
    var selectedIds = $(dataTableName).bootgrid("getSelectedRows");//这里返回的是一个数组array
    console.log("selectedIds---------------" + selectedIds);
    if (selectedIds == null || selectedIds.length == 0) {
        selectedIds = selectAllIds();
        console.log("selectedIds------------没有选择默认为选择了全部id----------" + selectedIds);
    }
    var param = $(dataTableName).bootgrid("getSearchPhrase");
    if (!param) {
        param = ",,,,,,,,";//现在共有最多8个查询条件，故设置为8个空元素，包含授权码以及审核状态
    }
    var columnSettings = $(dataTableName).bootgrid("getColumnSettings");
    var titles = [];
    var colNames = [];
    for (var x = 0; x < columnSettings.length; x++) {
        if (columnSettings[x]["text"] && columnSettings[x]["id"] && !columnSettings[x]["formatter"]) {
            titles[x] = columnSettings[x]["text"];
            colNames[x] = columnSettings[x]["id"];
        }
    }
    docName = (docName) ? (docName) : ("导出数据");
    var url = getMainObject() + "/exportExcel?param=" + param + "&docName=" + docName + "&titles=" + titles + "&colNames=" + colNames;
    if (selectedIds) {
        url += "&selectedIds= " + selectedIds.join(",");
    }
    url = url.trim();
    console.log("------exportExcel------url--------" + url);
    bootbox.confirm({
        message: "确定导出查询结果记录么?",
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
                window.location.href = url;
            }
        }
    });

}


/**
 *  初始化下拉选择组件
 */
function initSelect() {
    $("select").select2({
        theme: "bootstrap",
        tags: "true",
        placeholder: "请选择...",
        allowClear: true
    });
}


/**
 * 初始化bootgrid表格 并监听选择事件
 */
function initBootGrid(dataTableName) {
    var config = {
        selection: true,
        multiSelect: true,
        sorting: true
    };
    //初始化加载列表
    $(dataTableName).bootgrid(config).on("selected.rs.jquery.bootgrid", function (e, rows) {
        //如果默认全部选中
        var selected = $(dataTableName).bootgrid("getSelectedRows");
        pointer = 0;
        if (selected.length === 0) {
            selectedIds.clear();
            selectedIds = findAllRecordId();
        } else {
            selectedIds = selected.sort(function (a, b) {
                return a - b
            });
        }

    }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
        var selected = $(dataTableName).bootgrid("getSelectedRows");
        pointer = 0;
        selectedIds = selected.sort(function (a, b) {
            return a - b
        });
    });
}


/**
 * 初始化bootgrid表格 并监听选择事件
 */
function initBootGridNoExport(dataTableName) {
    var config = {
        selection: true,
        multiSelect: true,
        sorting: true,
        templates: {
            actionButton: ""
        }
    };
    //初始化加载列表
    $(dataTableName).bootgrid(config).on("selected.rs.jquery.bootgrid", function (e, rows) {
        //如果默认全部选中
        var selected = $(dataTableName).bootgrid("getSelectedRows");
        pointer = 0;
        if (selected.length === 0) {
            selectedIds.clear();
            selectedIds = findAllRecordId();
        } else {
            selectedIds = selected.sort(function (a, b) {
                return a - b
            });
        }

    }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
        var selected = $(dataTableName).bootgrid("getSelectedRows");
        pointer = 0;
        selectedIds = selected.sort(function (a, b) {
            return a - b
        });
    });
}


/**
 * 初始化bootgrid表格 并监听选择事件
 */
function initBootGridMenu(dataTableName, config) {

    if (!config) {
        config = {
            selection: true,
            multiSelect: true,
            sorting: true,
            rowSelect: true,
            keepSelection: true,
            navigation: 0

        }
    }
    //初始化加载列表
    $(dataTableName).bootgrid(config).on("selected.rs.jquery.bootgrid", function (e, rows) {
        //如果默认全部选中
        var selected = $(dataTableName).bootgrid("getSelectedRows");
        pointer = 0;
        if (selected.length === 0) {
            selectedIds.clear();
            selectedIds = findAllRecordId();
        } else {
            selectedIds = selected.sort(function (a, b) {
                return a - b
            });
        }

    }).on("deselected.rs.jquery.bootgrid", function (e, rows) {
        var selected = $(dataTableName).bootgrid("getSelectedRows");
        pointer = 0;
        selectedIds = selected.sort(function (a, b) {
            return a - b
        });
    });
}


/**
 * 获取审核状态，系统管理显示所有的数据，而前台界面仅显示已审核的数据
 */
function getAuthStatus() {
    return authStatus;
}

/**
 * 获取复合查询条件的拼接字符串
 */
function getSearchParams() {
    //组装模型
    var params = $("#searchBox :input");
    var searchParams = "";
    $.each(params, function (i, p) {
        var id = $(p).attr("id");
        if (!$(p).is(":button")) {
            var value = ($(p).val()) ? $(p).val().trim() : "";
            searchParams += value + ",";
        }
    });
    searchParams = searchParams + getAuthStatus() + ",";//搜索条件里加上审核状态，根据审核状态查询
    console.log("dataTableName------searchParams------:" + searchParams);
    return searchParams;
}

/**
 * 复合条件查询
 */
function complexSearch() {
    var searchPhrase = getSearchParams();
    $(dataTableName).bootgrid("setSearchPhrase", searchPhrase).bootgrid("reload");
}

/**
 * 初始化查询起始日期
 */
function initSearchDate() {
    $("#beginDate ").val(addMonthToday(-3, new Date()));
    $("#endDate").val(transformYMD(new Date().getTime()));
}

//
// $(function () {
//
//
//     //初始化加载设备分类  设备位置
//
//
//     locs = findMyLoc();
//     eqClasses = findEqClass();
//
//
//     //取消异步加载
//     $.ajaxSettings.async = false;
//     //监听回车查询
//     $(document).keypress(function (e) {
//         // 回车键事件
//         if (e.which == 13) {
//             $("#searchBtn").click();
//         }
//     });
//
//
//     $(formTab).on("click", function () {
//         selectedIds = findAllRecordId();
//         pointer = 0;
//         var obj = findById(selectedIds[pointer]);
//         vdm.$set(getMainObject(), obj);
//         setFormReadStatus(formName, true);
//     })
//
// });

/**
 * 返回主界面
 */
function refreshPage() {
    console.log("----------------返回主界面------------------");
    window.location.href = "/main/index";
}


/**
 *路丽民 20170615
 * 在下拉列表框selectId中显示数据data，且显示默认选中项
 * @param selectId
 * @param data
 * @param defaultId 默认选中项的id
 * @param defaultText  默认选中项的文本信息
 */
function initSelectDataWithDefault(selectId, data, defaultId, defaultText) {
    var vueSelect = new Vue({
        el: selectId,
        data: data
    });
    $(selectId).select2("val", defaultId);
}


/**
 *在下拉列表框selectId中显示数据data
 * @param selectId
 * @param data
 */

var vueSelect;

function initSelectData(selectId, data) {
    vueSelect = new Vue({
        el: selectId,
        data: data
    });
    $(selectId).select2();
}


/**
 *
 * @param url
 * @param colName
 * @returns {Array}
 */
function getSelectedData(url, colName) {
    $.ajaxSettings.async = false;
    var array = [];
    $.getJSON(url, function (data) {
        for (var x in data) {
            if (data[x]) {
                array[x] = {id: data[x]["id"], text: data[x][colName]};
            }
        }
    });
    return array;
}

function getRemoteSensorMediaSelectedData(url, colName) {
    $.ajaxSettings.async = false;
    var array = [];
    $.getJSON(url, function (data) {
        for (var x in data) {
            if (data[x]) {
                array[x] = {id: data[x]["id"], text: data[x][colName], authKey: data[x]["authKey"]};
            }
        }
    });
    return array;
}

function getMediaSelectedData(url, data, colName) {
    $.ajaxSettings.async = false;
    var array = [];


    console.log("url---", JSON.stringify(array));

    $.post(url, data, function (data) {
        for (var x in data) {
            if (data[x]) {
                array[x] = {id: data[x]["id"], text: data[x][colName]};
            }
        }
    });


    // console.log("projects---", JSON.stringify(array));
    return array;
}

function getSelectedData2(url, colName) {
    $.ajaxSettings.async = false;
    var array = [];
    $.getJSON(url, function (data) {
        for (var x in data) {
            if (data[x]) {
                array[x] = data[x][colName];
            }
        }
    });
    return array;
}

/**
 * 获取所属区块
 */
function getSelectedArea(url, colNo, colName) {
    $.ajaxSettings.async = false;
    var array = [];
    $.getJSON(url, function (data) {
        for (var x in data) {
            if (data[x]) {
                array[x] = {id: data[x]["id"], text: data[x][colNo] + data[x][colName]};
            }
        }
    });
    return array;
}

/**
 * 检查网络状态
 */
function checkNetwork() {
    var online = true;
    if (!navigator.onLine) {
        online = false;
    }
}


/**
 * 系统管理多媒体管理
 * 通过url查询
 *
 * 初始化时显示文件
 * 点击区块显示文件
 *
 */
function showAllFile(data) {
    console.log("showAllFile------------");
    if (data == null) {
        return;
    } else {
        for (var x = 0; x < data.length; x++) {
            // console.log("初始化时的data数据--------------"+JSON.stringify(data));
            var url = data[x]["thumbNailUrl"] ? data[x]["thumbNailUrl"] : data[x]["fileRelativeUrl"];
            var extend = url.substring(url.lastIndexOf(".") + 1);
            var imgId = data[x]["fileRelativeUrl"];
            // console.log("url"+url);
            var downLoadUrl = data[x]["fileRelativeUrl"];
            var id = data[x].id;
            // var title = data[x].fileName.replace(/\s/g, '');
            var title = data[x].fileName;
            var mediaType = data[x]["mediaTypesName"];
            var status = data[x].status;
            if (status == "未审核") {
                statusClassName = "icon-ban-circle";
                statusTitle = "待审核";
            } else if (status == "已审核") {
                statusClassName = "icon-ok";
                statusTitle = "已审核";
            }
            if (mediaType == "图片" && extend !== "tif") {
                src = url;
                // console.log("图片"+src);
            } else {
                classUrl = url;
                // var suffix = classUrl.split(".")[1];
                var suffix = classUrl.split(".")[classUrl.split(".").length - 1];
                src = "img/" + suffix + ".png";
                // console.log("其他"+src);
            }


            var dl = '<dl>' +
                '<dt class =' + downLoadUrl + '>' +
                '<img id=' + imgId + ' class="thumbnail" src=' + src + ' >' +
                '<div>' +
                '<a title="' + statusTitle + ' "><i class=' + statusClassName + '></i></a>' +
                '<a href=' + downLoadUrl + '  id="download" download="" title="下载"><i class="icon-download"></i></a>' +
                '<a><input type="checkbox" value=' + id + ' name="check"></a>' +
                '</div>' +
                '</dt><dd title="' + title + ' ">' + title +
                '</dd>' +
                '</dl>';
            if (mediaType == "图片") {
                $(".floatingBox .container-fluid #projectPictures").append(dl);
            } else if (mediaType == "文档") {
                $(".floatingBox .container-fluid #projectDocuments").append(dl);
            } else {
                $(".floatingBox .container-fluid #projectMedias").append(dl);
            }
        }
    }
    $('#projectPictures').viewer();
}


function videoPlay() {
    //点击视频缩略图弹出视频播放弹窗
    $("#projectMedias .thumbnail").on("click", function () {
        $("#media_modal").html("");
        $("#media_modal").modal("show");
        var url = $(this).parent().attr("class");
        var video = "<video src='" + url + "' style='width:1000px;height:560px' controls='controls' autoplay='autoplay'></video>";
        $("#media_modal").append(video);
    });

    $('#media_modal').on('hide.bs.modal', function () {
        $("#media_modal").children('video').remove();
    })
}

/**
 * 获取已经选择的菜单id集合
 * @returns {Array}
 */
function getSelectedMenuIds() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    console.log(treeObj);
    var nodes = treeObj.getCheckedNodes();
    var menuIds = [];
    for (var x in nodes) {
        menuIds[x] = nodes[x]["id"];
    }
    return menuIds;
}

/**
 *
 * 多媒体库管理点击上传弹出框
 */
function mediaUpload() {
    if (!isClicked) {
        showMessageBox("danger", "请勾选左侧区块或位置！");
        return;
    } else {
        $("#import_modal").modal("show");
        $(".dz-complete").css("display", "none");
        $(".dz-message").css("display", "block");
    }
}

/**
 * 删除单个文件
 */
function delResource(id, url) {
    console.log("点击删除");
    url += id;
    $.get(url, function (data) {
        console.log(data);
        if (data["result"]) {
            showMessageBox("info", data["resultDesc"]);
        } else {
            showMessageBox("danger", data["resultDesc"]);
        }
        ;
    });
    setInterval(function () {
        location.reload(false);
    }, 2000)


}


//多媒体库管理批量删除或者批量审核
function delAllFile_changeStatus(url, message) {
    var cks = document.getElementsByName("check");
    console.log(cks);
    var str = "";
    var temp = 0;
    for (var i = 0; i < cks.length; i++) {
        if (cks[i].checked) {
            temp += 1;
            str += cks[i].value + ",";
        }
    }
    ;
    if (temp == 0) {
        bootbox.alert({
            buttons: {
                ok: {
                    label: '是',
                    className: 'btn-success'
                }
            },
            message: "请选择文件之后操作!",
            callback: function () {
                // alert('关闭了alert');
            }
        });
    } else {
        console.log(str);
        // }

        //去掉字符串末尾的‘,'
        str = str.substring(0, str.length - 1);
        console.log("---------------" + str);


        bootbox.confirm({
            message: message,
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
                // var resultDesc = "";
                if (result) {
                    $.ajax({
                        type: "POST",
                        url: url,
                        data: {
                            idsList: str
                        },
                        success: function (msg) {
                            // console.log("del msg-------------"+JSON.stringify(msg));
                            for (var i = 0; i < msg.length; i++) {
                                if (msg[i].result = true) {
                                    n += 1;
                                }
                            }
                            ;
                            // console.log(n+ "看看n和msg.length相等不想等" + msg.length);
                            if (n == msg.length || 1) {
                                console.log("del msg-------------" + JSON.stringify(msg));
                                showMessageBox("info", "文件操作成功");
                                // setInterval(function () {
                                //     location.reload();
                                // }, 1000)
                                showAllFile(mediaComplexSearch(media));
                            } else {
                                console.log("del msg-------------" + JSON.stringify(msg));
                                showMessageBox("danger", "部分文件操作成功");
                                showAllFile(mediaComplexSearch(media));
                            }
                            ;
                        },
                        error: function (msg) {
                            console.log("del msg-------------" + JSON.stringify(msg));
                            showMessageBox("danger", ":");
                        }
                    });
                }
            }
        });
    }
}

/**
 * 清空formId的表单数据
 */
function cleanData() {
    $(getFormId())[0].reset();
    $("#confirm_modal .tips").html("");
    var arr = getSelectArray();
    for (var i = 0; i < arr.length; i++) {
        $(formId + " " + arr[i]).val("").change();
        // console.log("清空====" + arr[i])
    }
    $("#confirm_modal").modal("show");
    $("#confirm_modal").css("top", getTop());
}


/**
 * 清空地理信息录入时的formId的表单数据
 */
function cleanDataForCreateFeature() {
    $(getFormId())[0].reset();
    $("#confirm_modal .tips").html("");
    var arr = getSelectArray();
    for (var i = 0; i < arr.length; i++) {
        $(formId + " " + arr[i]).val("").change();
    }
    $("#confirm_modal").css("top", getTop());
}

/**
 * 点击新增按钮，在公共方法里面清空各个下拉列表以及id属性
 */
function addNewData() {
    cleanData();
}

/**
 * 点击新增按钮，获取表单id
 */
function getFormId() {
    return formId;
}

/**
 * 点击新增按钮，获取需要清空的下拉列表id以及绑定的id属性
 */
function getSelectArray() {
    return selectArray;
}

/**
 * 点击新增或编辑按钮，获取弹出框top值
 */
function getTop() {
    return topValue;
}

/**
 *
 * 搜索条件中市县查询
 */
function city_District() {
    var locLevelUrl = "location/findByLocLevel/0";//返回值是一个列表，查找江苏省的信息
    var locs = findByIdUrl(locLevelUrl);
    if (locs[0]["id"]) {
        var url = "location/findLocsByParentId/" + locs[0]["id"];
        var cities = getSelectedData(url, "locName");
        initSelectData("#cityName", {citiesArray: cities});
    }

    //查询条件的所在区县选择下拉列表
    $("#cityName").change(function () {
        //先清空区县下拉列表
        $("#districtName").html("<option value=\"\">---请选择区县---</option>");

        var pLocName = $("#cityName").val();
        if (pLocName == "") {
            $("#districtName").html("<option value=\"\">---请选择区县---</option>");
        }
        //根据所在市的名字查询区县子节点
        var url = "/location/findLocsByPLocName/" + pLocName;
        var district = getSelectedData2(url, "locName");
        //循环创建option插入区县中。避免使用vue绑定带来的重复bug
        for (var i = 0; i < district.length; i++) {
            var option = new Option(district[i], district[i]);
            $("#districtName").append(option);
        }
        ;
        $("#districtName").select2();
    });
}


/**
 *
 * 新增或编辑界面中市县下拉列表
 */
function cityAndDistrict() {

    //新增或编辑界面中市县下拉列表
    var locLevelUrl = "location/findByLocLevel/0";//返回值是一个列表，查找江苏省的信息
    var locs = findByIdUrl(locLevelUrl);
    if (locs[0]["id"]) {
        var url = "location/findLocsByParentId/" + locs[0]["id"];
        var cities = getSelectedData(url, "locName");
        initSelectData("#city", {citiesArray: cities});
    }

    //编辑或者新增界面的所在区县选择下拉列表
    $("#city").change(function () {
        $("#district").html("<option value=\"\">---请选择区县---</option>");
        var parentId = $("#city").val();
        var url = "location/findLocsByParentId/" + parentId;
        var district = getSelectedData(url, "locName");
        //循环创建option插入区县中。避免使用vue绑定带来的重复bug
        for (var i = 0; i < district.length; i++) {
            var option = new Option(district[i]["text"], district[i]["id"]);
            $("#district").append(option);
        }
        $("#district").select2();
    });

}

/**
 *
 * 根据市县名称查询项目名称
 */
function searchProjectNameBycityOrDistrict(cityOrDistrict, authStatus) {
    $(cityOrDistrict).change(function () {

        //先清空项目名称下拉列表
        $("#project").html("<option value=\"\">--请选择项目名称--</option>");
        var cityName = $("#cityName").val();
        var districtName = $("#districtName").val();
        if (cityOrDistrict !== "") {
            var url = "project/findVByAuthKeyAndCityNameAndDistrictNameAndStatus";
            var data = {
                cityName: cityName,
                districtName: districtName,
                authStatus: authStatus
            };
            var res = getMediaSelectedData(url, data, "projectName");
            for (var i = 0; i < res.length; i++) {
                console.log(res[i]);
                var option = new Option(res[i]["text"], res[i]["text"]);
                $("#project").append(option);
            }
            ;
            $("#project").select2();
        }
    });
}

function exportTemplate() {
    var url = "/" + mainObject + "/createOneExcel";
    var param = {tableId: 5};
    $.post(url, param, function (data) {
        if (data.result) {
            showMessageBox("info", data["resultDesc"]);
        } else {
            showMessageBox("danger", data["resultDesc"]);
        }
    })
}


/**
 *
 * @param ctrlSuffix
 * @param tableId
 * @returns {{ctrlSuffix: *, tableId: *}}
 */

var tableId;

function exportConfigSetting() {
    var exportSetting =
        {
            tableId: tableId
        }
    return exportSetting;
}


function ZTree(url) {
    var zNodes = [];
    //使用ajax的get方法获取所有的区块树：json数组
    $.post(url, {authStatus: authStatus}, function (data) {
        // console.log("=====data===" + JSON.stringify(data));
        //所有区块只有一个公共的父节点,：江苏省，id=0，pId=""，name=江苏省，t=江苏省，其他区块的pId全部设置为0
        zNodes[0] = {
            id: 0,//我们定义的虚假值
            pId: null,//我们定义的虚假值
            name: "江苏省",
            t: "江苏省",
            open: true
        };
        for (var x = 0; x < data.length; x++) {
            if (!isNaN(data[x]["id"])) {
                zNodes[x + 1] = {
                    id: data[x]["id"],
                    pId: 0,     //其他区块的pId全部设置为0
                    name: data[x]["name"],
                    t: data[x]["t"],
                    open: true
                }
            }
        }
        // console.log("====zNodes====" + JSON.stringify(zNodes));
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });
}


function createMedia(url) {

    //使用ajax的get方法获取所有的区块树：json数组
    $.post(url, function (data) {
        for (var x = 0; x < data.length; x++) {
            // console.log("data[x]", JSON.stringify(data[x]));
            if (!isNaN(data[x]["id"])) {
                zNodes[x] = {
                    id: data[x]["id"],
                    pId: data[x]["parent"] ? data[x]["parent"]["id"] : 0,     //其他区块的pId全部设置为0
                    name: data[x]["treeName"],
                    code: data[x]["treeNodeCode"],
                    t: data[x]["treeDesc"],
                    // open: false
                }
            }
        }
        // console.log("====zNodes====" + JSON.stringify(zNodes));
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });


    $.fn.zTree.init($("#treeDemo"), setting, zNodes);

    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.getNodes();
    for (var i = 0; i < nodes.length; i++) { //设置节点展开
        treeObj.expandNode(nodes[i], true, false, true);
    }
}

/**
 * 获取选中的复选框的值:id列表
 * @returns
 */
function getCheckedValues() {
    var selectedIds = $(dataTableName).bootgrid("getSelectedRows");  //选择所有name="'test'"的对象，返回数组
    //取到id数组后，组装为字符串
    var result = selectedIds.length > 0 ? selectedIds.join(",") : "";
    return result;
}


/**
 * 多媒体文件复合条件查询
 */
function mediaComplexSearch(media) {
    $(".floatingBox .container-fluid ul").html("");
    var dataRes = null;
    var searchPhrase = getSearchParams();
    var url = media + "/complexSearchByConditions?searchPhrase=" + searchPhrase;
    // var url = mediaUrl + searchPhrase;
    $.get(url, function (data) {
        dataRes = data;
    });
    return dataRes;
}


/**
 *获取最大的id
 */
function getMaxIdByPid(controllerName, pid) {
    var newId = 1;
    var url = "/" + controllerName + "/findMaxIdByPid/" + pid;
    $.ajaxSettings.async = false;
    $.getJSON(url, function (data) {
        newId = data;
    });
    return newId;
}


/**
 *
 * @param inputId 绑定的input的id
 * @param url ajax取值的路径
 * @param value 取值返回的字段
 */
function initAutoComplete(inputId, url, value) {
    var availableTags = [];
    $.get(url, function (data) {
        for (var x in data) {
            availableTags[x] = data[x][value]
        }
    });
    $("#" + inputId).autocomplete({
        source: availableTags
    });

}

