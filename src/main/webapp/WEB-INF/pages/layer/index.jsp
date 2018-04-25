<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/20 0020
  Time: 16:10
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
</head>
<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>

<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">图层信息</div>
    <form class="form-horizontal" id="layerForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="layerName" class="col-md-2 control-label">图层名称：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="geoLayer.id"/>
                    <input type="text" class="form-control" id="layerName" name="layerName" placeholder="图层名称">
                </div>
            </div>

            <div class="form-group ">
                <label for="layerDesc" class="col-md-2 control-label">图层描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="layerDesc" name="layerDesc" placeholder="图层描述">
                </div>
            </div>

            <div class="form-group ">
                <label for="serviceUrl" class="col-md-2 control-label">服务地址：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="serviceUrl" name="serviceUrl" placeholder="服务地址">
                </div>
            </div>

            <div class="form-group ">
                <label for="fillColor" class="col-md-2 control-label">填充色：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="fillColor" name="fillColor" placeholder="填充色">
                </div>
            </div>

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
<div class="content">
    <div class="container-fluid">
        <div class="divider-vertical" style="height: 20px;"></div>
        <div class="row-fluid">
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
            <div class="mySpan10">
                <!-- ==================== TABLE HEADLINE ==================== -->
                <div class="containerHeadline tableHeadline">
                    <i class="icon-table"></i>
                    <!-- <div class="controlButton pull-right"><i class="icon-remove removeElement"></i></div>
                    <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div> -->
                    <h2>系统管理-地理信息管理-图层信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="layerName" placeholder="图层名称">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table class="table table-hover table-bordered" id="dataTableList">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-identifier="true" data-type="numeric">序号</th>
                                    <th data-column-id="layerName" data-width="10%">图层名称</th>
                                    <th data-column-id="layerDesc" data-width="30%">图层描述</th>
                                    <th data-column-id="serviceUrl" data-width="40%">服务地址</th>
                                    <th data-column-id="fillColor" data-width="20%">填充颜色</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                        data-width="10%">编辑 删除
                                    </th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                        <!-- ==================== END OF HOVER TABLE FLOATING BOX ==================== -->

                    </div>
                </div>
            </div>
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
        </div>
    </div>
</div>
<%@include file="../common/common-footer.jsp" %>
<%--<script type="text/javascript" src="/js/app/projectConsTask.js"></script>--%>
<script type="text/javascript" src="/js/app/layer.js"></script>
</body>
</html>