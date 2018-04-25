<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/5 0005
  Time: 15:41
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
    <%--<link rel="stylesheet" href="/css/zTreeDemo.css" type="text/css">--%>
    <link rel="stylesheet" href="/css/zTreeStyle.css" type="text/css">
</head>

<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>


<div class="content-kfly">
    <%--<div class="container-fluid">--%>
    <%--<div class="divider-vertical" style="height: 20px;"></div>--%>
    <img src="img/title-bg.jpg" style="position:absolute;left:0px;top:0px;width:100%;height:100%;z-Index:-1;">
    <div class="timeline-kfly">
        <div class="row-fluid">
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
            <%--<div class="mySpan10">--%>
            <!-- ==================== TABLE HEADLINE ==================== -->
            <div class="containerHeadline tableHeadline">
                <i class="icon-table"></i>
                <h2>系统管理-用户授权管理-数据授权</h2>
            </div>
            <%--<div class="floatingBox table">--%>
            <%--<div class="container-fluid">--%>
            <div class="span2">
                <div class="floatingBox-tree">
                    <div class="content_wrap" style="width:20%;float: left;">
                        <div class="zTreeDemoBackground left">
                            <ul id="tree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <%--style="width:70%;float: left;border:1px solid #000;"--%>
            <div class="span10">

                <div class="locForm" style="overflow: hidden;background:#f2f2f2;padding:10px;margin:20px;">
                    <form class="form-inline" id="locForm">

                        <div class="form-group ">
                            <label for="locName" class="control-label">位置名称：</label>
                            <input type="text" class="form-control" id="locName" name="beginYear" placeholder="位置名称">
                        </div>
                        <div class="form-group ">
                            <label for="locDesc" class="control-label">位置描述：</label>
                            <input type="text" class="form-control" id="locDesc" name="locDesc" placeholder="位置描述">
                        </div>
                    </form>
                </div>

                <div>
                    <form class="form-inline" style="float: left">
                        <div class="form-group" id="searchBox">
                            <input type="text" class="form-control" id="userName" placeholder="用户名">
                            <button type="button" class="btn-button" onclick="complexSearch()">
                                <i class="icon-search"></i>
                            </button>
                            <button type="button" class="btn-button" onclick="grant()">
                                <i class="icon-plus"></i>
                            </button>
                        </div>
                    </form>
                </div>

                <div id="table">
                    <table id="dataTableList" class="table table-auth table-condensed table-bordered table-hover"
                           style="margin-top:10px;">
                        <thead>
                        <tr>
                            <th data-column-id="id" data-width="5%" data-type="numeric">序号</th>

                            <th data-column-id="userName" data-width="20%">用户名</th>
                            <th data-column-id="personName" data-width="20%">人员姓名</th>
                            <th data-column-id="department" data-width="40%">所属部门</th>
                            <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                data-width="10%">取消授权
                            </th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
        <%--</div>--%>
    </div>
</div>


<!-- SAMPLE BOX CONFIGURATION MODAL FORM-->
<%--style="top: 18%;"--%>
<div class="modal fade confirm_modal" id="userListModal">
    <div class="modal-content">
        <div class="modal-body" style="float: left">请选择授权要添加的用户</div>
        <div id="addUserTable">
            <table id="usersNotInLocation" class="table table-bordered table-hover table-condensed"
                   style="margin-top:10px">
                <thead>
                <tr>
                    <th data-column-id="id" data-width="10%" data-type="numeric" data-identifier="true">序号</th>
                    <th data-column-id="userName" data-width="45%">用户名</th>
                    <th data-column-id="personName" data-width="45%">人员姓名</th>
                </tr>
                </thead>
                <%--<tbody id="usersDiv">--%>
                <%--</tbody>--%>
            </table>
        </div>
        <div class="modal-footer">
            <a class="btn btn-danger" data-action="1" id="confirmBtn" onclick="grantDataAuth()">保存</a>
            <a class="btn cancel" href="#" data-dismiss="modal">取消</a>
        </div>
    </div>
</div>


<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script type="text/javascript" src="/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/js/app/dataAuth.js"></script>
</body>
</html>
