<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/20 0020
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
</head>
<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>


<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">海岸线信息</div>
    <form class="form-horizontal" id="coastLineForm">

        <div class="row-fluid">
            <div class="form-group " >

                <label for="lineNo" class="col-md-2 control-label">海岸线编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" />
                    <input type="text" class="form-control" id="lineNo" name="lineNo" placeholder="海岸线编号">
                </div>
            </div>

            <div class="form-group " >
                <label for="lineName" class="col-md-2 control-label">海岸线名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="lineName" name="lineName" placeholder="海岸线名称">
                </div>
            </div>

            <div class="form-group " >
                <label for="locationSelect" class="col-md-2 control-label">所属行政区划：</label>
                <div class="col-md-3">
                    <select class="form-control" id="locationSelect" name="locationId" >
                        <option value="">--请选择行政区划--</option>
                        <template v-for="l in locationsArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                    <div class="locationSelect_error"></div>
                </div>
            </div>

            <div class="form-group " >
                <label for="lineLength" class="col-md-2 control-label">海岸线长度：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="lineLength" name="lineLength" placeholder="海岸线长度">
                </div>
            </div>

            <div class="form-group " >
                <label for="typeNameSelect" class="col-md-2 control-label">海岸线类型：</label>
                <div class="col-md-3">
                    <select class="form-control" id="typeNameSelect" name="coastLineTypeId" >
                        <option value="">--请选择海岸线类型--</option>
                        <template v-for="l in typeNameArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group " >
                <label for="startPoint" class="col-md-2 control-label">起点所在地：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="startPoint" name="startPoint" placeholder="起点所在地">
                </div>
            </div>

            <div class="form-group " >
                <label for="endPoint" class="col-md-2 control-label">终点所在地：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="endPoint" name="endPoint" placeholder="终点所在地">
                </div>
            </div>

            <div class="form-group " hidden>
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="status" name="status">
                        <option value="1">启用</option>
                        <option value="0">禁用</option>
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
                    <h2>系统管理-地理信息管理-海岸线信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="lineNo" placeholder="海岸线编号">
                                <input type="text" class="form-control" id="lineName" placeholder="海岸线名称">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table  table-bordered table-hover" style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type = "numeric" data-identifier="true">序号</th>
                                    <th data-column-id="lineNo" data-width="10%">海岸线编号</th>
                                    <th data-column-id="lineName" data-width="15%">海岸线名称</th>
                                    <th data-column-id="locName" data-width="10%">行政区划</th>
                                    <th data-column-id="lineLength" data-width="15%">海岸线长度(公里)</th>
                                    <th data-column-id="coastLineType" data-width="15%">海岸线类型</th>
                                    <th data-column-id="startPoint" data-width="15%">起点所在地</th>
                                    <th data-column-id="endPoint" data-width="15%">终点所在地</th>
                                    <th data-column-id="status" data-width="8%">状态</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false" data-width="10%">操作</th>
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
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script src="/js/app/coastLine.js"></script>
</body>

</html>