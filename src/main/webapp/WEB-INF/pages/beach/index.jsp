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
    <div class="modal-body">滩涂信息</div>
    <form class="form-horizontal" id="beachForm">

        <div class="row-fluid">

            <div class="form-group ">
                <label for="beachNo" class="col-md-2 control-label">滩涂编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id"/>
                    <input type="text" class="form-control" id="beachNo" name="beachNo" placeholder="滩涂名称"/>
                </div>
            </div>
            <div class="form-group ">
                <label for="beachName" class="col-md-2 control-label">滩涂名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="beachName" name="beachName" placeholder="滩涂名称"/>
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
                <label for="seaLevelType" class="col-md-2 control-label">潮位类型：</label>
                <div class="col-md-3">
                    <select class="form-control" id="seaLevelType" name="seaLevelTypeId" >
                        <option value="">--请选择潮位类型--</option>
                        <template v-for="l in typeNameArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group " >
                <label for="measureDate" class="col-md-2 control-label">测算时间：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="measureDate" name="measureDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowToday:false})" placeholder="测算时间">
                </div>
            </div>

            <div class="form-group " >
                <label for="inningStatus" class="col-md-2 control-label">围垦状态：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="inningStatus" name="inningStatus" >
                        <option value="1">已围全部</option>
                        <option value="2">已围部分</option>
                        <option value="3">未围</option>
                    </select>
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
                    <h2>系统管理-地理信息管理-滩涂信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="beachNo" placeholder="滩涂编号">
                                <input type="text" class="form-control" id="beachName" placeholder="滩涂名称">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table  table-bordered table-hover" style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type = "numeric" data-identifier="true">序号</th>
                                    <th data-column-id="beachNo" data-width="15%">滩涂编号</th>
                                    <th data-column-id="beachName" data-width="15%">滩涂名称</th>
                                    <th data-column-id="locName" data-width="15%">所属行政区划</th>
                                    <th data-column-id="seaLevelType" data-width="15%">潮位类型</th>
                                    <th data-column-id="measureDate" data-width="15%">测算时间</th>
                                    <th data-column-id="inningStatus" data-width="15%">围垦状态</th>
                                    <th data-column-id="status" data-width="8%">状态</th>
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
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script src="/js/app/beach.js"></script>
</body>

</html>