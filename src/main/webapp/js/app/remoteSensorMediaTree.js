/**
 * Created by 路丽民 on 2017/6/18 0018.
 */
mainObject = "remoteSensorMedia";

var areaProjectId = "";
var projectName = "";
var classUrl = "";
var areaId = "";
var src = "";
var statusClassName = "";
var statusTitle = "";
var media = "remoteSensorMedia";
var treeNodeId = null;
var treeNodeCode = null;
authStatus = "";//审核状态，不设置时查询所有的资料（包括已审核和未审核）
var zNodes = [];
var zTree = null;
var downLoadUrl = "";
var setting = {

    check: {
        enable: true,
        autoCheckTrigger: true
    },
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false
    },
    edit: {
        enable: true,
        editNameSelectAll: true,
        showRemoveBtn: false,
        showRenameBtn: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeEditName: beforeEditName,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        // onRemove: onRemove,
        onRename: onRename,
        beforeClick: beforeClick,
        onClick: onClick
    }
};


var log, className = "dark";

function beforeClick(treeId, treeNode, clickFlag) {
    className = (className === "dark" ? "" : "dark");
    return (treeNode.click != false);
}

//点击左侧不同项目，显示当前项目文件
function onClick(event, treeId, treeNode, clickFlag) {
    isClicked = true;
    $(".floatingBox .container-fluid ul").html('');
    treeNodeId = treeNode["id"];
    projectName = treeNode["name"];
    treeNodeCode = treeNode["code"];
    var dataRes = null;
    var url = "/remoteSensorMedia/findByTreeNodeCodeAndAuthKeyAndStatus";
    $.post(url, {treeNodeCode: treeNodeCode, authStatus: authStatus}, function (data) {
        dataRes = data;
        console.log("dataRes", JSON.stringify(dataRes));
    });

    showAllFile(dataRes);
    $("#searchBox :input").val("");
    $("#searchBox select").val(projectName).select2({'width': '20%'});

    videoPlay();

}


var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
    return false;
}
function beforeEditName(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    setTimeout(function () {
        setTimeout(function () {
            zTree.editName(treeNode);
        }, 0);
    }, 0);
    return false;
}
function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    var obj = {nodeId: treeNode.id};
    var url = "/mediaTree/delNode";
    bootbox.confirm({
        message: "确定要删除该目录么?",
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
                $.post(url, obj, function (msg) {
                    if (msg["result"]) {
                        showMessageBox("info", msg["resultDesc"]);
                    } else {
                        showMessageBox("danger", msg["resultDesc"]);
                    }
                });
            }
        }
    });
}
function onRemove(e, treeId, treeNode) {

    console.log("onRemove-------------------------");

}
function beforeRename(treeId, treeNode, newName, isCancel) {
    className = (className === "dark" ? "" : "dark");
    if (newName.length == 0) {
        setTimeout(function () {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.cancelEditName();
            alert("节点名称不能为空.");
        }, 0);
        return false;
    }
    return true;
}
/**
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @param isCancel
 */
function onRename(e, treeId, treeNode, isCancel) {
    console.log("isCancel", isCancel);
    updateNodeName(treeNode.id, treeNode.name);

}


var newCount = 1;
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='新建节点' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_" + treeNode.tId);
    if (btn) btn.bind("click", function () {
        addNode('新建文件夹', treeNode.id);
        zTree = $.fn.zTree.getZTreeObj("treeDemo");
        //计算当前节点的最大id

        var maxId = getMaxIdByPid("mediaTree", treeNode.id);
        // findMaxId by pid
        zTree.addNodes(treeNode, {id: maxId, pId: treeNode.id, name: "新建文件夹" + (newCount++)});

        return false;
    });
};
function removeHoverDom(treeId, treeNode) {


    console.log("remove btn--------------");
    $("#addBtn_" + treeNode.tId).unbind().remove();
};
function selectAll() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}
/**
 * 获取选中的区块id
 */
function getSelectAreaId() {
    selectAreaId = $("#selectArea").val();
}

$(function () {
    initMenus("topMenu");
    $("#selectAll").bind("click", selectAll);
    var url = "remoteSensorMediaTree/findMediaTree";
    createMedia(url);
    // $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    //初始化时查询所有文件，显示在页面中
    showAllFile(mediaComplexSearch("remoteSensorMedia"));

    //回车键实现搜索功能
    mediaOnkeydown();

    //初始化项目名称下拉列表
    var projectUrl = "/projectResource/findProjectTree";
    var data = {authStatus: authStatus}
    var project = getMediaSelectedData(projectUrl, data, "name");
    initSelectData("#projectName", {projectsArray: project});
    $("#projectName").select2({'width': '20%'});

    //初始化区块名称下拉列表
    var areaUrl = "/area/findAreaTreeByAuthKeyAndStatus";
    var data = {authStatus: authStatus};
    var area = getMediaSelectedData(areaUrl, data, "name");
    initSelectData("#selectArea", {areasArray: area});
    $("#selectArea").select2({'width': '30%'});


    Dropzone.options.myDropzone = {
        url: "/remoteSensorMedia/uploadFileAndSavePath",
        paramName: "file",
        maxFilesize: 100.0,// MB
        maxFiles: 10000,
        acceptedFiles: null,
        autoProcessQueue: false,//是否立马上传
        addRemoveLinks: true,//是否添加删除功能
        parallelUploads: 10000, //最大并行处理量
        init: function () {
            myDropzone = this;
            this.on("addedfile", function (file) {
                //上传文件时触发的事件
                $(".dz-message").css("display", "none");
            });
            this.on('sending', function (file, xhr, formData) {
                //传递参数时在sending事件中formData，需要在前端代码加enctype="multipart/form-data"属性
                formData.append("treeNodeId", treeNodeId);
                console.log("remoteSensorMedia  treeNodeId====" + treeNodeId);
                formData.append("areaId", selectAreaId);
                formData.append('userName', userName);
            });
            $("#button_upload").on("click", function () {
                myDropzone.processQueue();
            });
            this.on("success", function (file, data) {
                //上传完成后触发的方法
                console.log("---102---" + JSON.stringify(data));
                $("#import_modal").modal("hide");
                var url = data.thumbNailUrl ? data.thumbNailUrl : data.fileRelativeUrl;
                var extend = url.substring(url.lastIndexOf(".") + 1);
                var downLoadUrl = data.fileRelativeUrl;
                var title = data.fileName;
                var mediaType = data["mediaTypes"]["typeName"];
                var status = data.status;
                var id = data.id;
                if (status == 0) {
                    statusClassName = "icon-ban-circle";
                    statusTitle = "待审核";
                } else if (status == 1) {
                    statusClassName = "icon-ok";
                    statusTitle = "已审核";
                }
                if (mediaType == "图片" && extend !== "tif") {
                    src = url;
                } else {
                    classUrl = url;
                    var suffix = classUrl.split(".")[classUrl.split(".").length - 1];
                    src = "img/" + suffix + ".png";
                }
                ;
                var dl = '<dl>' +
                    '<dt class = "' + downLoadUrl + '">' +
                    '<img class="thumbnail" src=' + src + ' >' +
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
                    $(".floatingBox .container-fluid #remoteSensorMedias").append(dl);
                }

            });
        }
    };

    videoPlay();

});


//通过复选框批量删除
$("#del").on("click", function () {
    var delUrl = "/remoteSensorMedia/deleteInBatch";
    var message = "确定删除这些文件么?";
    delAllFile_changeStatus(delUrl, message);
});

//通过复选框批量审核
$("#approve").on("click", function () {
    var statusUrl = "/remoteSensorMedia/authorizeInBatch";
    var message = "确定要将这些文件设为审核通过么?";
    delAllFile_changeStatus(statusUrl, message);
});


/**
 *
 * @param treeId 树id
 * @param nodeName 节点新名称
 */
function updateNodeName(treeId, nodeName) {
    var url = "mediaTree/updateNode";
    var obj = {
        treeId: treeId,
        nodeName: nodeName
    }
    $.post(url, obj, function (data) {
        console.log("data", JSON.stringify(data));
    });
}


/**
 *
 * @param treeId 树id
 * @param nodeName 节点新名称
 */
function addNode(nodeName, pid) {
    var url = "mediaTree/addNode";
    var obj = {
        treeName: nodeName,
        parentId: pid
    }
    $.post(url, obj, function (data) {
        console.log("data", JSON.stringify(data));
    });
}


