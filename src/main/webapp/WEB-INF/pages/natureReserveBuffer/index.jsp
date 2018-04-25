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
    <div class="modal-body">自然保护缓冲区信息</div>
    <form class="form-horizontal" id="natureReserveBufferForm">

        <div class="row-fluid">
            <div class="form-group " >

                <label for="bufferNo" class="col-md-2 control-label">自然保护区编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="buffer.id"/>
                    <input type="text" class="form-control" id="bufferNo" name="bufferNo" placeholder="自然保护区编号">
                </div>
            </div>

            <div class="form-group " >
                <label for="bufferName" class="col-md-2 control-label">自然保护区名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="bufferName" name="bufferName" placeholder="自然保护区名称">
                </div>
            </div>

            <div class="form-group " >
                <label for="bufferDesc" class="col-md-2 control-label">自然保护区描述：</label>
                <div class="col-md-3">
                    <textarea type="text" class="form-control" id="bufferDesc" name="bufferDesc" placeholder="自然保护区描述" rows="5" style="width:58%;resize: none;"></textarea>
                </div>
            </div>

            <div class="form-group " hidden>
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="status" name="status">
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
                    <h2>系统管理-基础数据管理-自然保护缓冲区信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="bufferNo" placeholder="缓冲区编号">
                                <input type="text" class="form-control" id="bufferName" placeholder="缓冲区名称">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table  table-bordered table-hover" style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type = "numeric" data-identifier="true">序号</th>
                                    <th data-column-id="bufferNo" data-width="15%">自然保护缓冲区编号</th>
                                    <th data-column-id="bufferName" data-width="20%">自然保护缓冲区名称</th>
                                    <th data-column-id="bufferDesc" data-width="40%">自然保护缓冲区描述</th>
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
<script src="/js/app/natureReserveBuffer.js"></script>
</body>

</html>