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
<div class="modal fade confirm_modal" id="addUser_confirm_modal">
    <div class="modal-body" style="float: left">请选择角色要添加的用户</div>
    <div id="addUserTable">
        <table id="addUserTableList" class="table table-bordered table-hover table-condensed" style="margin-top:10px">
            <thead>
            <tr>
                <th data-column-id="id" data-width="15%" data-type="numeric" data-identifier="true">序号</th>
                <th data-column-id="userName" data-width="35%">用户名</th>
                <th data-column-id="personName" data-width="35%">人员姓名</th>
            </tr>
            </thead>
        </table>
    </div>
    <div class="modal-footer">
        <a class="btn btn-danger" data-action="1" id="addUserSaveBtn">保存</a>
        <a class="btn cancel" href="#" data-dismiss="modal">取消</a>
    </div>
</div>


<div class="modal fade confirm_modal" id="removeUser_confirm_modal">
    <div class="modal-body">请选择角色要删除的用户</div>
    <div id="removeUserTable">
        <table id="removeUserTableList" class="table table-bordered table-hover table-condensed"
               style="margin-top:10px">
            <thead>
            <tr>
                <th data-column-id="id" data-width="15%" data-type="numeric" data-identifier="true">序号</th>
                <th data-column-id="userName" data-width="35%">用户名</th>
                <th data-column-id="personName" data-width="35%">人员姓名</th>
            </tr>
            </thead>
        </table>
    </div>
    <div class="modal-footer">
        <a class="btn btn-danger" data-action="1" id="removeUserSaveBtn">保存</a>
        <a class="btn cancel" href="#" data-dismiss="modal">取消</a>
    </div>
</div>


<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">角色授权</div>
    <form class="form-horizontal" id="roleForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="roleName" class="col-md-2 control-label">角色名称：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="role.id"/>
                    <input type="text" class="form-control" id="roleName" name="roleName" placeholder="角色名称">
                    <div class="roleName_error tips"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="roleDesc" class="col-md-2 control-label">角色描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="roleDesc" name="roleDesc" placeholder="角色描述">
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
                    <h2>系统管理-用户授权管理-角色授权</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="roleNameSearch" placeholder="角色名称">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table  table-bordered table-hover table-condensed"
                                   style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="roleName" data-width="25%">角色名称</th>
                                    <th data-column-id="roleDesc" data-width="50%">角色描述</th>
                                    <th data-column-id="addUser" data-formatter="addUser" data-width="5%">添加用户</th>
                                    <th data-column-id="removeUser" data-formatter="removeUser" data-width="5%">删除用户
                                    </th>
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
<script type="text/javascript" src="/js/terebentina-sco/js/sco.modal.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/app/role.js"></script>
</body>
</html>