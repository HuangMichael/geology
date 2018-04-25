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
    <link rel="stylesheet" href="/js/bootstrapvalidator/dist/css/bootstrapValidator.css"/>

</head>
<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>

<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">部门信息</div>
    <form class="form-horizontal" id="depForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="depNo" class="col-md-2 control-label">部门编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="department.id"/>
                    <input type="text" class="form-control" id="depNo" name="depNo" placeholder="部门编号">
                </div>
            </div>

            <div class="form-group ">
                <label for="depName" class="col-md-2 control-label">部门名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="depName" name="depName" placeholder="部门名称">
                    <div class="depName_error tips"></div>
                </div>
            </div>

            <div class="form-group " >
                <label for="depDesc" class="col-md-2 control-label">部门描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="depDesc" name="depDesc" placeholder="部门描述">
                </div>
            </div>

            <div class="form-group " >
                <label for="location" class="col-md-2 control-label">所在市：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="location" name="locationId" placeholder="所在市">
                        <option value="">--请选择所在市--</option>
                        <template v-for="l in locationsArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                    <div class="locationSelect_error"></div>
                </div>
            </div>

            <div class="form-group " >
                <label for="parent" class="col-md-2 control-label">上级部门：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="parent" name="parentId" placeholder="上级部门">
                        <option value="">--请选择上级部门--</option>
                        <template v-for="l in departmentsArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group " >
                <label for="depDesc" class="col-md-2 control-label">部门负责人：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="manager" name="manager" placeholder="部门负责人">
                </div>
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
                    <h2>系统管理-用户授权管理-部门信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="depName" placeholder="部门名称">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table  table-bordered table-hover table-condensed " style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-identifier="true" data-type="numeric" data-width="5%">序号</th>
                                    <th data-column-id="depNo" data-width="5%">部门编号</th>
                                    <th data-column-id="depName" data-width="20%">部门名称</th>
                                    <th data-column-id="depDesc" data-width="20%">部门描述</th>
                                    <th data-column-id="locName" data-width="10%">所在市</th>
                                    <th data-column-id="manager" data-width="10%">部门负责人</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false" data-width="10%">编辑 删除</th>
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
<script type="text/javascript" src="/js/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script type="text/javascript" src="/js/app/department.js"></script>
<script type="text/javascript" src="/js/common/common-export.js"></script>
</body>
</html>