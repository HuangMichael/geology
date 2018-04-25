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
    <link rel="stylesheet" href="/css/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/js/viewer/viewer.min.css">
</head>

<body class="dashboard">

<%@include file="../common/common-title.jsp"%>
<%@include file="../common/common-topMenu.jsp"%>

<body class="dashboard">

<div class="content-kfly">
    <img src="img/title-bg.jpg" alt="" style="position:absolute;left:0px;top:0px;width:100%;height:100%;z-Index:-1;">
    <div class="timeline-kfly">
        <div class="row-fluid">
            <div class="containerHeadline tableHeadline">
                <i class="icon-table"></i>
                <h2>项目管理-项目资料</h2>
            </div>
            <div class="span2">
                <div class="floatingBox-tree">
                    <div class="container-fluid">
                        <%--<div id="areaTree" style="height: 750px;"><img src="img/areaTree1.png"/></div>--%>
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
                        <%--<%@include file="../common/common-searchBox.jsp" %>--%>
                        <%--<input type="text" class="form-control" id="projectName" placeholder="项目名称">--%>
                        <select type="text" class="form-control" id="selectProject" style="width: 20%">
                            <option value="">--请选择项目名称--</option>
                            <template v-for="c in projectsArray">
                                <option :value="c.text">{{c.text}}</option>
                            </template>
                        </select>
                        <input type="text" class="form-control" id="fileName" placeholder="文件名称">
                        <input type="text" class="form-control" id="userName " placeholder="上传用户">
                        <input type="text" class="form-control" id="uploadDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowToday:false})" placeholder="上传时间">
                        <button type="button" class="btn-button" onclick="showAllFile(mediaComplexSearch(media))"><i class="icon-search"></i></button>
                    </div>
                </form>
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#projectDocuments">文档</a></li>
                    <li><a href="#projectPictures">图片</a></li>
                    <li><a href="#projectMedias">视频</a></li>
                </ul>
                <div class="floatingBox media-floatingBox">
                    <div class="containerHeadline tableHeadline">
                        <!-- ==================== TAB NAVIGATION ==================== -->

                        <!-- ==================== END OF TAB NAVIGATIION ==================== -->
                        <div class="container-fluid">

                            <!-- ==================== FIRST TAB CONTENT ==================== -->
                            <ul class="chartContainers tabContent" id="projectDocuments">

                            </ul>
                            <!-- ==================== END OF FIRST TAB CONTENT ==================== -->

                            <!-- ==================== SECOND TAB CONTENT ==================== -->
                            <ul class="chartContainers tabContent" id="projectPictures" style="display: none">

                            </ul>
                            <!-- ==================== END OF SECOND TAB CONTENT ==================== -->
                            <!-- ==================== Third TAB CONTENT ==================== -->
                            <ul class="chartContainers tabContent" id="projectMedias" style="display: none">

                            </ul>
                            <!-- ==================== END OF third TAB CONTENT ==================== -->
                        </div>

                    </div>
                </div>

            </div>

        <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
    </div>
</div>
</div>
<div class="modal fade password_modal" id="media_modal">

</div>

<%@include file="../common/common-footer.jsp"%>
<script type="text/javascript" src="/js/viewer/viewer.js"></script>

<script type="text/javascript" src="/js/dropzone/dropzone.js"></script>
<script type="text/javascript" src="/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"> </script>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script src="/js/app/projectMediaTree.js"></script>
<script>
    authStatus = "已审核";
    mediaComplexSearch("projectMedia");
</script>
</body>


</html>
