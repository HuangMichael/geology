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
    <link rel="stylesheet" href="/js/dropzone/dropzone.css">
</head>
<body class="dashboard">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>

<div class="modal fade confirm_modal" id="confirm_modal">
    <div class="modal-body">人员信息</div>
    <form class="form-horizontal" id="personForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="personName" class="col-md-2 control-label">姓名：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id"/>
                    <input type="text" class="form-control" id="personName" name="personName" placeholder="姓名">
                    <div class="userName_error"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="gender" class="col-md-2 control-label">性别：</label>
                <div class="col-md-3">
                    <%--<select name="gender" id="gender" class="form-control">--%>
                        <%--<option value="1">男</option>--%>
                        <%--<option value="2">女</option>--%>
                    <%--</select>--%>

                    <select name="gender" id="gender" class="form-control">
                        <option value="">--请选择性别--</option>
                        <template v-for="g in genderArray">
                            <option :value="g.id">{{g.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group ">
                <label for="birthDate" class="col-md-2 control-label">出生年月：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="birthDate" name="birthDate"
                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="出生年月">
                </div>
            </div>

            <div class="form-group ">
                <label for="departmentSelect" class="col-md-2 control-label">所属部门：</label>
                <div class="col-md-3">
                    <select class="form-control" id="departmentSelect" name="departmentId">
                        <option value="">--请选择部门--</option>
                        <template v-for="d in departmentsArray">
                            <option :value="d.id">{{d.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group ">
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select class="form-control" id="status" name="status">
                        <option value="1">在职</option>
                        <option value="0">离职</option>
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
                    <h2>系统管理-用户授权管理-人员信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <select class="form-control" id="depName" name="department.id" style="width:260px">
                                    <option value="">--请选择部门--</option>
                                    <template v-for="d in departmentsArray">
                                        <option :value="d.text">{{d.text}}</option>
                                    </template>
                                </select>
                                <input type="text" class="form-control" id="personName" placeholder="姓名"/>
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
                                    <th data-column-id="personName" data-width="10%">姓名</th>
                                    <th data-column-id="gender" data-width="10%">性别</th>
                                    <th data-column-id="birthDate" data-width="20%">出生年月</th>
                                    <th data-column-id="depName" data-width="20%">部门</th>
                                    <th data-column-id="status" data-width="20%">状态</th>
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
                <form action="/etl/uploadAndSave" class="dropzone" id="myDropzone" enctype="multipart/form-data"
                      method="post">
                    <div class="fallback">
                        <input name="file" type="file" multiple=""/>
                    </div>
                </form>
                <button id="button_upload" action="/etl/uploadAndSave">上传</button>
            </div>
        </div>
    </div>
</div>



<!-- ==================== END OF PAGE CONTENT ==================== -->
<%@include file="../common/common-footer.jsp" %>

<script type="text/javascript" src="/js/terebentina-sco/js/sco.modal.js"></script>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.min.js"></script>
<script type="text/javascript" src="/js/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="/js/dropzone/dropzone.js"></script>
<script type="text/javascript" src="/js/app/person.js"></script>
<script type="text/javascript" src="/js/common/common-export.js"></script>
</body>
</html>