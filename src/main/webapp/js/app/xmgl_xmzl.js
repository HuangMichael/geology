
var areaId = "";
var mainObject = "projectMedia";
var src = "";
var classUrl = "";
authStatus = "已审核";
var media = "projectMedia";

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
    console.log("----------------- beforeClick-------------" + treeNode.name + treeNode.t);
    return (treeNode.click != false);
}


//点击左侧不同项目，显示当前项目文件
function onClick(event, treeId, treeNode, clickFlag) {
    $(".floatingBox .container-fluid ul").html('');
    console.log(treeNode);
    areaProjectId  = treeNode["id"];
    projectName = treeNode["name"];
    var dataRes = null;
    var url = "/projectMedia/findByProjectNameAndAuthKeyAndStatus";
    $.post(url,{projectName:projectName,authStatus:authStatus},function (data) {
        console.log(data);
        dataRes = data;
    });

    showAllFile(dataRes);

    $("#searchBox :input").val("");
    $("#searchBox select").val(projectName).select2({'width':'20%'});
    videoPlay();
}


$(function () {
    //初始化时加载导航栏
    initMenus("topMenu");

    //初始化项目名称下拉列表
    var projectUrl = "/projectResource/findProjectTree";
    var data = {authStatus:authStatus}
    var project = getMediaSelectedData(projectUrl,data, "name");
    initSelectData("#projectName", {projectsArray: project});
    $("#projectName").select2({'width':'20%'});

    ZTree("projectResource/findProjectTree");

    //初始化时查询所有文件，显示在页面中
    showAllFile(mediaComplexSearch(media));

    //点击视频缩略图弹出视频播放弹窗
    videoPlay();

});


/**
 * 返回主界面
 */
function refreshPage() {
    console.log("----------------返回主界面------------------");
    window.location.href = "/main/index";
}

/**
 * 复合条件查询
 */
function complexSearch() {
    var dataRes = null;
    var searchPhrase = getSearchParams();
    var url = "/projectMedia/complexSearchByConditions?searchPhrase=" + searchPhrase;
    $.get(url,function (data) {
        dataRes = data;
    });
    return dataRes;
}

   
