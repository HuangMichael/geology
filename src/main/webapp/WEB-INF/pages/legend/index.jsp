<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/20 0020
  Time: 16:10
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
    <div class="modal-body">图例信息</div>
    <form class="form-horizontal" id="legendForm">

        <div class="row-fluid">
            <div class="form-group " >

                <label for="legendName" class="col-md-2 control-label">图例名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="legendName" name="legendName" placeholder="图例名称">
                </div>
            </div>

            <div class="form-group " >
                <label for="legendDesc" class="col-md-2 control-label">图例描述：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="legendDesc" name="legendDesc" placeholder="图例描述">
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
                    <!-- <div class="controlButton pull-right"><i class="icon-remove removeElement"></i></div>
                    <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div> -->
                    <h2>系统管理-地理信息管理-图例信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group">
                                <input type="text" class="form-control" id="searchStr" placeholder="图例名称">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table class="table table-bordered table-hover table-condensed bootgrid-table" id="dataTableList">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%">序号</th>
                                    <th data-column-id="legendName" data-width="25%">图例名称</th>
                                    <th data-column-id="legendDes" data-width="30%">图例描述</th>
                                    <th data-column-id="legendUrl" data-width="30%">图例样式</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false" data-width="10%">编辑 删除</th>
                                </tr>
                                </thead>
                                <tbody style="overflow-y:scroll ">
                                <tr>
                                    <td>1</td>
                                    <td>已围面积</td>
                                    <td>包括路况，交通，居民点等图层</td>
                                    <td><img src="/img/marker/legend06.png"></td>
                                    <td>
                                        <button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>
                                        <button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>规划面积</td>
                                    <td>显示规划面积信息</td>
                                    <td><img src="/img/marker/legend02.png"></td>
                                    <td>
                                        <button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>
                                        <button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>
                                    </td>

                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>农业用地</td>
                                    <td>显示对应区域内的农业用地信息</td>
                                    <td><img src="/img/marker/legend01.png"></td>
                                    <td>
                                        <button class='btn btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>
                                        <button class='btn btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>
                                    </td>
                                </tr>
                                </tbody>
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
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/app/legend.js"></script>
</body>
</html>