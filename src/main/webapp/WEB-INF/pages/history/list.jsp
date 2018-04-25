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
<html class="no-js">
<!--<![endif]-->
<head>
    <%@include file="../common/common-header.jsp" %>
    <link rel="stylesheet" href="/js/dropzone/dropzone.css">
</head>
<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>
<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">历史沿革信息</div>
    <form class="form-horizontal" id="historyForm">
        <div class="row-fluid">
            <div class="form-group ">
                <label for="title" class="col-md-2 control-label">标题：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="history.id"/>
                    <input type="text" class="form-control" id="title" name="title" placeholder="标题">
                </div>
            </div>

            <div class="form-group ">
                <label for="subTitle" class="col-md-2 control-label">子标题：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="subTitle" name="subTitle" placeholder="子标题">
                </div>
            </div>

            <div class="form-group ">
                <label for="historyDesc" class="col-md-2 control-label">描述信息：</label>
                <div class="col-md-3">
                    <textarea class="form-control" id="historyDesc" name="historyDesc" placeholder="描述信息" cols="80"
                              rows="8" style="width:57%;resize: none"></textarea>
                </div>
            </div>

            <%--知道了上级历史沿革是谁，就可以计算出该历史沿革的level、sortNo属性--%>
            <div class="form-group ">
                <label for="historySelect" class="col-md-2 control-label">上级历史沿革：</label>
                <div class="col-md-3">
                    <select class="form-control" id="historySelect" name="parentId">
                        <option value="">--请选择上级历史沿革--</option>
                        <template v-for="p in parentsArray">
                            <option :value="p.id">{{p.text}}</option>
                        </template>
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
                    <!-- <div class="controlButton pull-right"><i class="icon-remove removeElement"></i></div>
                    <div class="controlButton pull-right"><i class="icon-caret-down minimizeElement"></i></div> -->
                    <h2>系统管理-基础数据管理-历史沿革信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <input type="text" class="form-control" id="titleSearch" placeholder="标题">
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table table-bordered table-hover"
                                   style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="4%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="title" data-width="10%">标题</th>
                                    <th data-column-id="subTitle" data-width="15%">子标题</th>
                                    <%--<th data-column-id="fileRelativeUrl" data-width="20%">多媒体资料路径</th>--%>
                                    <th data-column-id="historyDesc" data-width="30%" data-visible="false">历史沿革描述</th>
                                    <th data-column-id="sortNo" data-width="5%" data-sortable="true">排序</th>
                                    <th data-column-id="upload" data-formatter="upload" data-sortable="false"
                                        data-width="4%">上传
                                    </th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                        data-width="6%">编辑 删除
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

<div class="modal fade " id="import_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">请选择文件</h4>
            </div>
            <div class="modal-body">
                <form action="" class="dropzone" id="myDropzone" enctype="multipart/form-data" method="post">
                    <div class="fallback">
                        <input name="file" type="file" multiple=""/>
                    </div>
                </form>
                <button id="button_upload" action="">上传</button>
            </div>
        </div>
    </div>
</div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/dropzone/dropzone.js"></script>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script type="text/javascript" src="/js/app/historyList.js"></script>
</body>
</html>