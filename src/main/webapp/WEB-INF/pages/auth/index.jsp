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
    <%--<link rel="stylesheet" href="/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">--%>
</head>

<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>
<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">授权管理</div>
    <form class="form-horizontal" id="authForm">
        <div class="row-fluid">
            <div class="form-group">
                <label for="locName" class="col-md-2 control-label">菜单编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="id"/>
                    <input type="text" class="form-control" id="locName" name="locName" placeholder="菜单编号">
                    <div class="locName_error"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="locDesc" class="col-md-2 control-label">菜单名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="locDesc" name="locDesc" placeholder="菜单名称">
                </div>
            </div>

            <div class="form-group ">
                <label for="locDesc" class="col-md-2 control-label">菜单描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="locDesc" name="locDesc" placeholder="菜单描述">
                </div>
            </div>

            <%--<div class="form-group ">--%>
                <%--<label for="parentSelect" class="control-label">上级位置：</label>--%>
                <%--<div class="col-md-3">--%>
                    <%--<select class="form-control" id="parentSelect" name="parent.id">--%>
                        <%--<option value="">--请选择上级位置--</option>--%>
                        <%--<template v-for="p in parentsArray">--%>
                            <%--<option :value="p.id">{{p.text}}</option>--%>
                        <%--</template>--%>
                    <%--</select>--%>
                <%--</div>--%>
            <%--</div>--%>

            <div class="form-group ">
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select class="form-control" id="status" name="status">
                        <option value="0">禁用</option>
                        <option value="1" selected="true">启用</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
    <div class="modal-footer">
        <a class="btn btn-danger" data-action="1" id="saveBtn">保存</a>
        <a class="btn cancel" href="#" data-dismiss="modal">取消</a>
    </div>
</div>

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
                <h2>系统管理-用户授权管理-授权管理</h2>
            </div>
            <div class="span2">
                <div class="floatingBox-tree">
                    <div class="container-fluid">
                        <div class="content_wrap" style="width:100%;float: left;">
                            <div class="zTreeDemoBackground left">
                                <select class="select2" style="width: 100%" id="roleSelect">
                                </select>
                                <ul id="treeDemo" class="ztree"></ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="span10">
                <div class="locForm" style="overflow: hidden;background:#f2f2f2;padding:10px;margin:20px 0;">
                    <form class="form-inline" id="locForm">
                        <div class="form-group ">
                            <label for="menuName" class="control-label">菜单名称：</label>
                            <input type="text" class="form-control" id="menuName" name="menuName" placeholder="菜单名称">
                        </div>
                        <div class="form-group ">
                            <label for="menuDesc" class="control-label">菜单描述：</label>
                            <input type="text" class="form-control" id="menuDesc" name="menuDesc" placeholder="菜单描述">
                        </div>

                        <div class="form-group ">
                            <label for="menuDesc" class="control-label">菜单类型：</label>
                            <select class="form-control" id="menuType" name="menuType">
                                <option value=""></option>
                                <option value="SYSTEM">应用</option>
                                <option value="MODULE">模块</option>
                                <option value="APP">应用</option>
                                <option value="MENU">菜单</option>
                            </select>
                        </div>

                        <div class="form-group ">

                            <input class="form-control btn " type="button" id="authBtn" name="authBtn" value="授权"
                                   onclick="auth4Role()"/>
                        </div>
                    </form>
                </div>

                <div>
                    <form class="form-inline" style="float: left">
                        <div class="form-group" id="searchBox">
                            <input type="text" class="form-control" id="locName1" placeholder="菜单名称">
                            <%@include file="../common/common-buttonCrudGroup.jsp" %>
                        </div>
                    </form>
                </div>

                <div id="table">
                    <table id="dataTableList" class="table  table-bordered table-hover table-condensed"
                           style="margin-top:10px;">
                        <thead>
                        <tr>
                            <th data-column-id="id" data-type="numeric" data-width="5%" data-identifier="true">序号</th>
                            <th data-column-id="menuNo" data-width="20%">菜单编号</th>
                            <th data-column-id="menuName" data-width="20%">菜单名称</th>
                            <th data-column-id="menuDesc" data-width="20%">菜单描述</th>
                            <th data-column-id="sortNo" data-width="10%">菜单排序</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script type="text/javascript" src="/js/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/js/zTree_v3/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="/js/common/common-utils.js"></script>
<script type="text/javascript" src="/js/app/auth.js"></script>
</body>
</html>
