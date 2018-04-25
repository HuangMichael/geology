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
    <div class="modal-body">元数据表信息</div>
    <form class="form-horizontal" id="MetadataForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="tableDesc" class="col-md-2 control-label">表描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="tableDesc" name="tableDesc" placeholder="表描述">
                </div>
            </div>


            <div class="form-group ">
                <label for="domainName" class="col-md-2 control-label">类名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="domainName" name="domainName" placeholder="类名称">
                </div>
            </div>

            <div class="form-group ">
                <label for="baseTableName" class="col-md-2 control-label">基础表名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="baseTableName" name="baseTableName" placeholder="基础表名称">
                </div>
            </div>

            <div class="form-group ">
                <label for="serviceTableName" class="col-md-2 control-label">业务表名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="serviceTableName" name="serviceTableName"
                           placeholder="服务表名称">
                </div>
            </div>


            <div class="form-group ">
                <label for="dropStatus" class="col-md-2 control-label">能否删除：</label>
                <div class="col-md-3">
                    <select class="form-control" id="dropStatus" name="dropStatus">
                        <option value="0">否</option>
                        <option value="1" selected="true">是</option>
                    </select>
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
                    <h2>系统管理-数据ETL管理-元数据表信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="tableName" placeholder="表名称">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList"
                                   class="table table-bordered table-hover table-condensed bootgrid-table"
                                   style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-type="numeric" data-width="5%" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="tableDesc" data-width="20%">表描述</th>
                                    <th data-column-id="baseTableName" data-width="40%">基础表名称</th>
                                    <th data-column-id="serviceTableName" data-width="10%">业务表名称</th>
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
<%--<script src="/js/app/farmLand.js"></script>--%>
<script src="/js/app/etlTable.js"></script>
</body>

</html>