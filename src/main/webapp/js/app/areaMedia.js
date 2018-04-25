/**
 * Created by 路丽民 on 2017/6/18 0018.
 */

authStatus = "";//审核状态，不设置时查询所有的资料（包括已审核和未审核）
var areaId = "";
var areaName = "";
var mainObject = "areaMedia";
var src = "";
var statusClassName = "";
var statusTitle = "";
var classUrl = "";
var media = "areaMedia";
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
        beforeClick: beforeClick,
        onClick: onClick
    }
};

var log, className = "dark";

function beforeClick(treeId, treeNode, clickFlag) {
    className = (className === "dark" ? "" : "dark");
    // console.log("----------------- beforeClick-------------" + treeNode.name + "--------------"+ treeNode.t);
    return (treeNode.click != false);
}

//点击左侧不同区块时，显示当前区块的文件
function onClick(event, treeId, treeNode, clickFlag) {
    isClicked = true;
    $(".floatingBox .container-fluid .tabContent").html('');
    areaId = treeNode.id;
    areaName = treeNode.name;
    var url = "/areaMedia/findByAreaNameAndAuthKeyAndStatus";
    var dataRes = null;
    $.post(url, {areaName: areaName, authStatus: authStatus}, function (data) {
        dataRes = data;
    });

    showAllFile(dataRes);

    $("#searchBox :input").val("");
    $("#searchBox select").val(areaName).select2({'width': '20%'});

    videoPlay();
}


$(function () {
    initMenus("topMenu");

    ZTree("/area/findAreaTreeByAuthKeyAndStatus");

    //初始化区块名称下拉列表
    var areaUrl = "/area/findAreaTreeByAuthKeyAndStatus";
    var data = {authStatus: authStatus}
    var area = getMediaSelectedData(areaUrl, data, "name");
    initSelectData("#areaName", {areasArray: area});
    $("#areaName").select2({'width': '20%'});

    //初始化时查询所有文件，显示在页面中
    showAllFile(mediaComplexSearch(media));

    //查询统计与系统管理  回车键实现搜索功能
    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];

        if (e && e.keyCode == 13) {
            showAllFile(mediaComplexSearch(media));
            // e.preventDefault();
            //return false;//阻止默认行为，搜索之后不能自动刷新
        }
    };
    Dropzone.options.myDropzone = {
        url: "/areaMedia/uploadAndSave",
        paramName: "file",
        maxFilesize: 10.0,// MB
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
            this.on("sending", function (file, xhr, formData) {
                //传递参数时在sending事件中formData，需要在前端代码加enctype="multipart/form-data"属性
                formData.append('areaId', areaId);
                formData.append('userName', userName);
            });
            $("#button_upload").on("click", function () {
                myDropzone.processQueue();
            });
            this.on("success", function (file, data) {
                //上传完成后触发的方法
                $("#import_modal").modal("hide");

                var url = data.fileRelativeUrl;
                var title = file.name.replace(/\s/g,'');
                var mediaTypeId = data["mediaTypes"]["id"];
                var status = data.status;
                if (status == 0) {
                    statusClassName = "icon-ban-circle";
                    statusTitle = "待审核";
                } else if (status == 1) {
                    statusClassName = "icon-ok";
                    statusTitle = "已审核";
                };
                if (mediaTypeId == 2) {
                    src = url;
                } else {
                    classUrl = url;
                    // var suffix = classUrl.split(".")[1];
                    var suffix = classUrl.split(".")[classUrl.split(".").length-1];
                    src = "img/" + suffix + ".png";
                }
                var dl = '<dl>' +
                    '<dt class =' + classUrl + '>' +
                    '<img class="thumbnail" src=' + src + ' >' +
                    '<div>' +
                    '<a title="' + statusTitle + ' "><i class=' + statusClassName + '></i></a>' +
                    '<a href=' + url + '  id="download" download="" title="下载"><i class="icon-download"></i></a>' +
                    '<a><input type="checkbox" value=' + data.id + ' name="check"></a>' +
                    '</div>' +
                    '</dt><dd>' + title +
                    '</dd>' +
                    '</dl>';
                if (mediaTypeId == 2) {
                    $(".floatingBox .container-fluid #projectPictures").append(dl);
                } else if (mediaTypeId == 1) {
                    $(".floatingBox .container-fluid #projectDocuments").append(dl);
                } else {
                    $(".floatingBox .container-fluid #projectMedias").append(dl);
                }
            });
        }
    };


    //点击视频缩略图弹出视频播放弹窗
    videoPlay();

});


//通过复选框批量删除
$("#del").on("click", function () {
    var delUrl = "/areaMedia/deleteInBatch";
    var message = "确定删除这些文件么?";
    delAllFile_changeStatus(delUrl, message);
});

//通过复选框批量审核
$("#approve").on("click", function () {
    var statusUrl = "/areaMedia/authorizeInBatch";
    var message = "确定要将这些文件设为审核通过么?";
    delAllFile_changeStatus(statusUrl, message);
});

