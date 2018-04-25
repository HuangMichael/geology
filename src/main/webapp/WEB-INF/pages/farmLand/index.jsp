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
    <div class="modal-body">耕地信息</div>
    <form class="form-horizontal" id="farmLandForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="landNo" class="col-md-2 control-label">耕地编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id"/>
                    <input type="text" class="form-control" id="landNo" name="landNo" placeholder="耕地编号">
                </div>
            </div>

            <div class="form-group ">
                <label for="landName" class="col-md-2 control-label">耕地名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="landName" name="landName" placeholder="耕地名称">
                </div>
            </div>

            <div class="form-group ">
                <label for="landSize" class="col-md-2 control-label">耕地面积：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="landSize" name="landSize" placeholder="耕地面积">
                </div>
            </div>

            <div class="form-group ">
                <label for="landType" class="col-md-2 control-label">土壤类型：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="landType" name="landType" placeholder="土壤类型">
                </div>
            </div>

            <div class="form-group " hidden>
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
                    <h2>系统管理-地理信息管理-耕地信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="landNo" placeholder="耕地编号">
                                <input type="text" class="form-control" id="landName" placeholder="耕地名称">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList"
                                   class="table table-bordered table-hover table-condensed bootgrid-table"
                                   style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="landNo" data-width="10%">耕地编号</th>
                                    <th data-column-id="landName" data-width="20%">耕地名称</th>
                                    <th data-column-id="landSize" data-width="50%">耕地面积(亩)</th>
                                    <th data-column-id="landType" data-width="50%">土壤类型</th>
                                    <th data-column-id="status" data-width="8%">状态</th>
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
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>

<script src="js/app/map_common.js"></script>
<script type="text/javascript" src="js/app/gis_config.js"></script>
<%--<script src="https://js.arcgis.com/3.21/"></script>--%>

<script src="/js/app/farmLand.js"></script>
</body>

</html>