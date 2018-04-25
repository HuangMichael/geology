/**
 * Created by 路丽民 on 2017/6/18 0018.
 */
mainObject = "projectMedia";

var areaProjectId = "";
var projectName = "";
var classUrl = "";
var areaId = "";
var src = "";
var statusClassName = "";
var statusTitle = "";
var media = "projectMedia";
authStatus = "";//审核状态，不设置时查询所有的资料（包括已审核和未审核）

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
    return (treeNode.click != false);
}

//点击左侧不同项目，显示当前项目文件
function onClick(event, treeId, treeNode, clickFlag) {
    isClicked = true;
    $(".floatingBox .container-fluid ul").html('');
    areaProjectId = treeNode["id"];
    projectName = treeNode["name"];

    var dataRes = null;
    var url = "/projectMedia/findByProjectNameAndAuthKeyAndStatus";
    $.post(url, {projectName: projectName, authStatus: authStatus}, function (data) {
        dataRes = data;
    });

    showAllFile(dataRes);

    $("#searchBox :input").val("");
    $("#searchBox select").val(projectName).select2({'width': '20%'});

    videoPlay();

}


$(function () {
    initMenus("topMenu");

    ZTree("projectResource/findProjectTree");

    //初始化时查询所有文件，显示在页面中
    showAllFile(mediaComplexSearch("projectMedia"));

    //初始化项目名称下拉列表
    var projectUrl = "/projectResource/findProjectTree";
    var data = {authStatus: authStatus}
    var project = getMediaSelectedData(projectUrl, data, "name");
    initSelectData("#projectName", {projectsArray: project});
    $("#projectName").select2({'width': '20%'});

    Dropzone.options.myDropzone = {
        url: "/projectMedia/uploadAndSave",
        paramName: "file",
        maxFilesize: 10.0,// MB
        maxFiles: 100000,
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
                formData.append('areaProjectId', areaProjectId);
                formData.append('userName', userName);
            });
            $("#button_upload").on("click", function () {
                myDropzone.processQueue();
            });
            this.on("success", function (file, data) {
                //上传完成后触发的方法
                console.log("---102---"+JSON.stringify(data));
                $("#import_modal").modal("hide");
                var url = data.fileRelativeUrl;
                var title = data.fileName.replace(/\s/g,'');
                var mediaType = data["mediaTypes"]["typeName"];
                var status = data.status;
                var id = data.id;

                console.log("status status", status);
                if (status == 0) {
                    statusClassName = "icon-ban-circle";
                    statusTitle = "待审核";
                } else if (status == 1) {
                    statusClassName = "icon-ok";
                    statusTitle = "已审核";
                }
                if (mediaType == "图片") {
                    src = url;
                } else {
                    classUrl = url;
                    // var suffix = classUrl.split(".")[1];
                    var suffix = classUrl.split(".")[classUrl.split(".").length-1];
                    src = "img/" + suffix + ".png";
                };
                var dl = '<dl>' +
                    '<dt class = "' + classUrl + '">' +
                    '<img class="thumbnail" src=' + src + ' >' +
                    '<div>' +
                    '<a title="' + statusTitle + ' "><i class=' + statusClassName + '></i></a>' +
                    '<a href=' + url + '  id="download" download="" title="下载"><i class="icon-download"></i></a>' +
                    '<a><input type="checkbox" value=' + id + ' name="check"></a>' +
                    '</div>' +
                    '</dt><dd>' + title +
                    '</dd>' +
                    '</dl>';
                if (mediaType == "图片") {
                    $(".floatingBox .container-fluid #projectPictures").append(dl);
                } else if (mediaType == "文档") {
                    $(".floatingBox .container-fluid #projectDocuments").append(dl);
                } else {
                    $(".floatingBox .container-fluid #projectMedias").append(dl);
                }

            });
        }
    };
    // autoplay='autoplay'

    videoPlay();

});


//通过复选框批量删除
$("#del").on("click", function () {
    var delUrl = "/projectMedia/deleteInBatch";
    var message = "确定删除这些文件么?";
    delAllFile_changeStatus(delUrl, message);
});

//通过复选框批量审核
$("#approve").on("click", function () {
    var statusUrl = "/projectMedia/authorizeInBatch";
    var message = "确定要将这些文件设为审核通过么?";
    delAllFile_changeStatus(statusUrl, message);
});



