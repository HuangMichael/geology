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
<div class="content">
    <div class="container-fluid">
        <div class="divider-vertical" style="height: 20px;"></div>
        <div class="row-fluid">
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
            <div class="mySpan10">
                <!-- ==================== TABLE HEADLINE ==================== -->
                <div class="containerHeadline tableHeadline">
                    <i class="icon-table"></i>
                    <h2>档案管理-借阅申请</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <%@include file="../common/common-searchBox.jsp" %>
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table  table-bordered table-hover" style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="archiveNo" data-width="5%">档案编号</th>
                                    <th data-column-id="shortName" data-width="10%">档案简称</th>
                                    <th data-column-id="name" data-width="20%">档案名称</th>
                                    <th data-column-id="createdBy" data-width="5%">创建人</th>
                                    <th data-column-id="createDate" data-width="5%">创建时间</th>
                                    <th data-column-id="status" data-width="6%" data-converter='showStatus'>状态</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                        data-width="5%">借阅
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

<div class="modal fade confirm_modal subfield" id="apply_modal">
    <div class="modal-body">借阅申请</div>
    <form class="form-horizontal subfield" id="form">
        <div class="row-fluid">
            <div class="form-group ">
                <div class="pull-left">
                    <label for="archives" class="col-md-2 control-label">档案名称：</label>
                    <div class="col-md-3">
                        <input  class="form-control" id="archives" name="archives" placeholder="档案名称">
                        <div class="areaName_error"></div>
                    </div>
                </div>

                <div class="pull-left">
                    <label for="reason" class="col-md-2 control-label">借阅原因：</label>
                    <div class="col-md-3">
                        <input  class="form-control" id="reason" name="reason" placeholder="借阅原因" required>
                        <div class="number_error"></div>
                    </div>
                </div>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="beginDate" class="col-md-2 control-label">开始日期：</label>
                    <div class="col-md-3">
                        <input class="form-control " id="beginDate" name="beginDate" placeholder="开始日期" required>
                        <div class="number_error"></div>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="endDate" class="col-md-2 control-label">结束日期：</label>
                    <div class="col-md-3">
                        <input  class="form-control " id="endDate" name="endDate" placeholder="结束日期" required>
                        <div class="number_error"></div>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div class="modal-footer">
        <a class="btn btn-danger" data-action="1" id="saveBtn">申请</a>
        <a class="btn cancel" href="#" data-dismiss="modal">取消</a>
    </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script src="/js/app/archivesBorrowApply/archivesBorrowApply.js"></script>
</body>
</html>