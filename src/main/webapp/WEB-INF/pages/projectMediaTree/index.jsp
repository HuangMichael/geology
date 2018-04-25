<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/22 0022
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--[if lt IE 7]>
<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>
<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>
<html class="no-js lt-ie9"> <![endif]-->

<!--[if gt IE 8]><!-->
<html class="no-js" xmlns:v-bind="http://www.w3.org/1999/xhtml">
<!--<![endif]-->

<head>
    <%@include file="../common/common-header.jsp" %>
    <link rel="stylesheet" href="css/tabStyle.css">
    <link rel="stylesheet" href="/css/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/js/dropzone/dropzone.css">
    <link rel="stylesheet" href="/js/viewer/viewer.min.css">
</head>

<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>

<div class="modal fade " id="import_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title pull-left" id="myModalLabel2">请选择文件</h4>
                <select type="text" class="form-control" id="selectProject" onchange="getProjectId()">
                    <option value="">--请选择所属项目--</option>
                    <template v-for="c in projectsArray">
                        <option :value="c.id">{{c.text}}</option>
                    </template>
                </select>
            </div>
            <div class="modal-body">
                <form action="" class="dropzone" id="myDropzone" enctype="multipart/form-data" method="post">
                    <div class="fallback">
                        <input name="file" type="file" multiple="multiple"/>
                    </div>
                </form>
                <button id="button_upload" action="">上传</button>
            </div>
        </div>
    </div>
</div>


<div class="content-kfly" style="position:relative;">
    <img src="img/title-bg.jpg" alt="" style="position:absolute;left:0px;top:0px;width:100%;height:100%;z-Index:-1;">
    <div class="timeline-kfly">
        <%--<div class="divider-vertical" style="height: 20px;"></div>--%>
        <div class="row-fluid">
            <div class="containerHeadline tableHeadline">
                <i class="icon-table"></i>
                <h2>系统管理-多媒体库管理-项目多媒体信息</h2>
            </div>
            <div class="span2">
                <div class="floatingBox-tree">
                    <div class="container-fluid">
                        <div class="content_wrap" style="width:20%;float: left;">
                            <div class="zTreeDemoBackground left">
                                <ul id="treeDemo" class="ztree"></ul>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
            <div class="span10">
                <!-- ==================== TABLE HEADLINE ==================== -->
                <form class="form-inline">
                    <div class="form-group" id="searchBox">
                        <select type="text" class="form-control projectName" id="projectName">
                            <option value="">--请选择项目名称--</option>
                            <template v-for="c in projectsArray">
                                <option :value="c.text">{{c.text}}</option>
                            </template>
                        </select>
                        <input type="text" class="form-control" id="fileName" placeholder="文件名称">
                        <input type="text" class="form-control" id="userName " placeholder="上传用户">
                        <input type="text" class="form-control" id="uploadDate"
                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowToday:false})" placeholder="上传时间">
                        <button type="button" class="btn-button" onclick="showAllFile(mediaComplexSearch(media))"><i
                                class="icon-search"></i></button>
                    </div>
                </form>
                <ul class="nav nav-tabs">
                    <li class="active" id="documents"><a href="#projectDocuments">文档</a></li>
                    <li id="pictures"><a href="#projectPictures">图片</a></li>
                    <li id="medias"><a href="#projectMedias">视频</a></li>
                    <div class="upload">
                        <a title="上传" id="upload" onclick='mediaUpload(" + row.id + ")'><i
                                class="icon-plus"></i></a>
                        <a title="删除" id="del"><i class="icon-trash"></i></a>
                        <a title="审核" id="approve"><i class="icon-check"></i></a>
                        <a title="加密" id="lock"><i class="icon-lock"></i></a>
                    </div>
                </ul>
                <div class="floatingBox media-floatingBox">
                    <div class="containerHeadline tableHeadline">
                        <!-- ==================== TAB NAVIGATION ==================== -->
                        <!-- ==================== END OF TAB NAVIGATIION ==================== -->
                        <div class="container-fluid">
                            <!-- ==================== FIRST TAB CONTENT ==================== -->
                            <ul class="chartContainers tabContent" id="projectDocuments"></ul>

                            <!-- ==================== SECOND TAB CONTENT ==================== -->
                            <ul class="chartContainers tabContent" id="projectPictures" style="display: none"></ul>

                            <!-- ==================== Third TAB CONTENT ==================== -->
                            <ul class="chartContainers tabContent" id="projectMedias" style="display: none"></ul>

                            <!-- ==================== END OF third TAB CONTENT ==================== -->
                        </div>

                    </div>
                </div>

            </div>
        </div>

        <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
    </div>
</div>

<div class="modal fade password_modal" id="media_modal"></div>


<%@include file="../common/common-footer-media.jsp" %>
<script type="text/javascript" src="/js/viewer/viewer.js"></script>
<style type="text/css">
    .ztree li span.button.add {
        margin-left: 2px;
        margin-right: -1px;
        background-position: -144px 0;
        vertical-align: top;
        *vertical-align: middle
    }
</style>
<link rel="stylesheet" href="/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/js/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/js/zTree_v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="/js/zTree_v3/js/jquery.ztree.exedit.js"></script>
<script src="/js/app/projectMediaTree.js"></script>
</body>

</html>
