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
    <link rel="stylesheet" href="/js/terebentina-sco/css/scojs.css"/>
    <link href="/js/bootstrapvalidator/dist/css/bootstrapValidator.css" rel="stylesheet"/>
</head>
<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>

<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">新增系统参数</div>
    <form class="form-horizontal" id="paramsForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="paramName" class="col-md-2 control-label">系统参数名称：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="department.id"/>
                    <input type="text" class="form-control" id="paramName" name="paramName" placeholder="系统参数名称">
                </div>
            </div>

            <div class="form-group ">
                <label for="paramDesc" class="col-md-2 control-label">系统参数描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="paramDesc" name="paramDesc" placeholder="系统参数描述">
                </div>
            </div>

            <div class="form-group " >
                <label for="paramValue" class="col-md-2 control-label">系统参数值：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="paramValue" name="paramValue" placeholder="系统参数值">
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
<div class="content">
    <div class="container-fluid">
        <div class="divider-vertical" style="height: 20px;"></div>
        <div class="row-fluid">
            <!-- ==================== END OF WIDGETS CONTAINER ==================== -->
            <div class="mySpan10">
                <!-- ==================== TABLE HEADLINE ==================== -->
                <div class="containerHeadline tableHeadline">
                    <i class="icon-table"></i>
                    <h2>系统管理-系统监控管理-系统参数</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="paramDesc" placeholder="系统参数描述"/>
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table  table-bordered table-hover table-condensed "
                                   style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-type="numeric" data-width="5%" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="paramName" data-width="10%">系统参数名称</th>
                                    <th data-column-id="paramDesc" data-width="10%">系统参数描述</th>
                                    <th data-column-id="paramValue" data-width="20%">系统参数值</th>
                                    <th data-column-id="status" data-width="5%">状态</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                        data-width="5%">编辑 删除
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

<script type="text/javascript" src="/js/terebentina-sco/js/sco.modal.js"></script>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="/js/app/sysParams.js"></script>
</body>
</html>