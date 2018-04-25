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
    <div class="modal-body">沿海沙洲信息</div>
    <form class="form-horizontal" id="sandLandForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="sandNo" class="col-md-2 control-label">沙洲编号：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="sandNo" name="sandNo" placeholder="沙洲编号">
                </div>
            </div>
            <div class="form-group ">
                <label for="sandName" class="col-md-2 control-label">沙洲名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="sandName" name="sandName" placeholder="沙洲名称">
                </div>
            </div>
            <div class="form-group ">
                <label for="position" class="col-md-2 control-label">所在位置：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id"/>
                    <input type="text" class="form-control" id="position" name="position" placeholder="所在位置">
                </div>
            </div>

            <div class="form-group ">
                <label for="locationSelect" class="col-md-2 control-label">所属行政区划：</label>
                <div class="col-md-3">
                    <select class="form-control" id="locationSelect" name="locationId">
                        <option value="">--请选择行政区划--</option>
                        <template v-for="l in locationsArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                    <div class="locationSelect_error"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="length" class="col-md-2 control-label">南北长（公里）：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="length" name="length" placeholder="南北长（公里）">
                </div>
            </div>

            <div class="form-group ">
                <label for="width" class="col-md-2 control-label">东西宽（公里）：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="width" name="width" placeholder="东西宽（公里）">
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
                    <h2>系统管理-地理信息管理-沿海沙洲信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="sandNo" placeholder="沙洲编号">
                                <input type="text" class="form-control" id="sandName" placeholder="沙洲名称">
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
                                    <th data-column-id="sandNo" data-width="5%">沙洲编号</th>
                                    <th data-column-id="sandName" data-width="10%">沙洲名称</th>
                                    <th data-column-id="position" data-width="30%">所在位置</th>
                                    <th data-column-id="locName" data-width="15%">所属行政区划</th>
                                    <th data-column-id="length" data-width="10%">南北长(公里)</th>
                                    <th data-column-id="width" data-width="10%">东西宽(公里)</th>
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
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script src="/js/app/sandLand.js"></script>
</body>

</html>