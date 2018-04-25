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
    <link rel="stylesheet" href="/js/bootstrapvalidator/dist/css/bootstrapValidator.css"/>
</head>

<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>
<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">位置信息</div>
    <form class="form-horizontal" id="locationForm">

        <div class="row-fluid">
            <div class="form-group" >

                <label for="locName" class="col-md-2 control-label">位置名称：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="id"/>
                    <input type="text" class="form-control" id="locName" name="locName" placeholder="位置名称">
                    <div class="locName_error"></div>
                </div>
            </div>

            <div class="form-group " >
                <label for="locDesc" class="col-md-2 control-label">位置描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="locDesc" name="locDesc" placeholder="位置描述">
                </div>
            </div>

            <div class="form-group ">
                <label for="parentSelect" class="control-label">上级位置：</label>
                <div class="col-md-3">
                    <select class="form-control" id="parentSelect" name="parent.id">
                        <option value="">--请选择上级位置--</option>
                        <template v-for="p in parentsArray">
                            <option :value="p.id">{{p.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group " >
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select  class="form-control" id="status" name="status">
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
        <img src="img/title-bg.jpg"  style="position:absolute;left:0px;top:0px;width:100%;height:100%;z-Index:-1;">
        <div class="timeline-kfly">
        <div class="row-fluid">
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
                <!-- ==================== TABLE HEADLINE ==================== -->
                <div class="containerHeadline tableHeadline">
                    <i class="icon-table"></i>
                    <h2>系统管理-用户授权管理-位置管理</h2>
                </div>
                        <div class="span2">
                            <div class="floatingBox-tree">
                                <div class="content_wrap" style="width:20%;float: left;">
                                    <div class="zTreeDemoBackground left">
                                        <ul id="treeDemo" class="ztree"></ul>
                                    </div>
                                </div>
                            </div>
                         </div>
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

                                    <div class="form-group ">
                                        <label for="parent" class="control-label">上级位置：</label>
                                        <input type="text" class="form-control" id="parent" name="parent" placeholder="上级位置">
                                    </div>
                                </form>
                            </div>

                            <div>
                                <form class="form-inline" style="float: left">
                                    <div class="form-group" id="searchBox">
                                        <input type="text" class="form-control" id="locName1" placeholder="位置名称">
                                        <button type="button" class="btn-button" onclick="complexSearch()"><i class="icon-search"></i></button>
                                        <button type="button" class="btn-button" id="submit" onclick="addNewData()"><i class="icon-plus"></i></button>
                                    </div>
                                </form>
                            </div>

                            <div id="table" >
                                <table id="dataTableList" class="table table-condensed table-bordered table-hover"  style="margin-top:10px;">
                                    <thead>
                                    <tr>
                                        <th data-column-id="id" data-type="numeric" data-width="5%" data-identifier="true">序号</th>
                                        <th data-column-id="locName" data-width="20%">位置名称</th>
                                        <th data-column-id="locDesc" data-width="40%">位置描述</th>
                                        <th data-column-id="commands" data-formatter="commands" data-sortable="false" data-width="10%"> 编辑 删除</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>

                        </div>
                        <!-- ==================== END OF HOVER TABLE FLOATING BOX ==================== -->

            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
        </div>
        </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script type="text/javascript" src="/js/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/js/app/location.js"></script>
</body>
</html>
