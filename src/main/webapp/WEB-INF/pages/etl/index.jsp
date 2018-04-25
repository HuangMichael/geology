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
    <link rel="stylesheet" href="/css/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/js/dropzone/dropzone.css">
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
                <h4 class="modal-title" id="myModalLabel2">请选择文件</h4>
            </div>
            <div class="modal-body">
                <form action="/etl/uploadAndSave" class="dropzone" id="myDropzone" enctype="multipart/form-data"
                      method="post">
                    <div class="fallback">
                        <input name="file" type="file" multiple=""/>
                    </div>
                </form>
                <button id="button_upload" action="/etl/uploadAndSave">上传</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">数据ETL管理</div>
    <form class="form-horizontal" id="etlConfigForm">
        <div class="row-fluid">
            <div class="form-group">
                <label for="colName" class="col-md-2 control-label">列的名称：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="id"/>
                    <input class="form-control" id="colName" name="colName" placeholder="列的名称">
                    <div class="locName_error"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="colDesc" class="col-md-2 control-label">列的描述：</label>
                <div class="col-md-3">
                    <input class="form-control" id="colDesc" name="colDesc" placeholder="列的描述">
                </div>
            </div>

            <div class="form-group ">
                <label for="dataType" class="col-md-2 control-label">该列数据类型：</label>
                <div class="col-md-3">
                    <input class="form-control" id="dataType" name="dataType" placeholder="该列的数据类型">
                </div>
            </div>

            <div class="form-group ">
                <label for="length" class="col-md-2 control-label">数据长度：</label>
                <div class="col-md-3">
                    <input class="form-control" id="length" name="length" placeholder="数据长度">
                </div>
            </div>

            <div class="form-group ">
                <label for="isNull" class="col-md-2 control-label">是否为空：</label>
                <div class="col-md-3">
                    <select class="form-control" id="isNull" name="isNull">
                        <option value="0" selected="true">不能为空</option>
                        <option value="1">可以为空</option>
                    </select>
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
                <h2>系统管理-数据ETL管理-数据ETL</h2>
            </div>
            <%--<div class="floatingBox table">--%>
            <%--<div class="container-fluid">--%>
            <div class="span2">
                <div class="floatingBox-tree">
                    <div class="content_wrap" style="width:30%;float: left;">
                        <div class="zTreeDemoBackground left">
                            <%--<select class="select2" style="width: 200px" id="roleSelect"></select>--%>
                            <ul id="treeDemo" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <%--style="width:70%;float: left;border:1px solid #000;"--%>
            <div class="span10">

                <div class="etlForm" style="overflow: hidden;background:#f2f2f2;padding:10px;margin:20px;">
                    <form class="form-inline" id="etlForm">
                        <div class="form-group ">
                            <label for="tableDesc" class="control-label">表描述：</label>
                            <input type="text" class="form-control" id="tableDesc" name="tableDesc" placeholder="表描述">
                        </div>

                        <div class="form-group ">
                            <label for="baseTableName" class="control-label">基础表名称：</label>
                            <input type="text" class="form-control" id="baseTableName" name="baseTableName" placeholder="基础表名称">
                        </div>

                        <div class="form-group ">
                            <label for="serviceTableName" class="control-label">业务表名称：</label>
                            <input type="text" class="form-control" id="serviceTableName" name="serviceTableName"
                                   placeholder="业务表名称">
                        </div>
                    </form>
                </div>

                <div>
                    <form class="form-inline" style="float: left">
                        <div class="form-group" id="searchBox">
                            <input type="text" class="form-control" id="baseColDesc" placeholder="基础表字段描述">
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
                            <th data-column-id="baseColName" data-width="15%">基础表字段名</th>
                            <th data-column-id="baseColDesc" data-width="15%">基础表字段描述</th>
                            <th data-column-id="serviceColName" data-width="15%">服务表列名称</th>
                            <th data-column-id="referenceTable" data-width="20%">参考业务表</th>
                            <th data-column-id="referenceColName" data-width="10%">参考字段</th>
                            <th data-column-id="dataType" data-width="10%">该列数据类型</th>
                            <th data-column-id="isNull" data-width="10%">是否为空</th>
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
<script type="text/javascript" src="/js/dropzone/dropzone.js"></script>
<script type="text/javascript" src="/js/app/etl.js"></script>
</body>
</html>
