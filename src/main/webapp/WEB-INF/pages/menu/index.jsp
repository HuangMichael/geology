<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<!--[if lt IE 7]>
<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>
<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>
<html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
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
    <div class="modal-body">菜单信息</div>
    <form class="form-horizontal" id="menuForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="menuName" class="col-md-2 control-label">菜单名称：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id"/>
                    <input type="text" class="form-control" id="menuName" name="menuName" placeholder="菜单名称">
                    <div class="menuName_error"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="menuDesc" class="col-md-2 control-label">菜单描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="menuDesc" name="menuDesc" placeholder="菜单名称">
                </div>
            </div>

            <div class="form-group ">
                <label for="menuDesc" class="col-md-2 control-label">菜单图标：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="icon" name="icon" placeholder="菜单图标">
                </div>
            </div>

            <div class="form-group ">
                <label for="menuDesc" class="col-md-2 control-label">菜单事件：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="event" name="icon" placeholder="菜单事件">
                </div>
            </div>

            <div class="form-group ">
                <label for="menuDesc" class="col-md-2 control-label">菜单类型：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="menuType" name="menuType" placeholder="菜单类型">
                </div>
            </div>

            <div class="form-group ">
                <label for="menuDesc" class="col-md-2 control-label">菜单级别：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="menuLevel" name="menuLevel" placeholder="菜单级别">
                </div>
            </div>


            <div class="form-group ">
                <label for="menuDesc" class="col-md-2 control-label">排序：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="sortNo" name="sortNo" placeholder="排序">
                </div>
            </div>

            <div class="form-group ">
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select class="form-control" id="status" name="status">
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
                    <h2>系统管理-用户授权管理-菜单管理</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="menuName" placeholder="菜单名称"/>
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table  table-bordered table-hover "
                                   style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-type="numeric" data-width="5%" data-identifier="true">序号</th>
                                    <th data-column-id="menuName" data-width="15%">菜单名称</th>
                                    <th data-column-id="menuDesc" data-width="15%">菜单描述</th>
                                    <th data-column-id="icon" data-width="10%">菜单图标</th>
                                    <th data-column-id="event" data-width="20%">菜单事件</th>
                                    <th data-column-id="menuType" data-width="10%">菜单类型</th>
                                    <th data-column-id="menuLevel" data-width="5%">菜单级别</th>
                                    <th data-column-id="sortNo" data-width="5%">菜单排序</th>
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
        </div>ss
    </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/terebentina-sco/js/sco.modal.js"></script>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="/js/app/resource.js"></script>
</body>
</html>