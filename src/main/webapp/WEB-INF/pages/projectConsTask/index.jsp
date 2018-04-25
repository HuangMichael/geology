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
    <div class="modal-body">项目建设任务信息</div>
    <form class="form-horizontal" id="projectConsTaskForm">

        <div class="row-fluid">
            <div class="form-group ">
                <label for="projectNo" class="col-md-2 control-label">项目编号：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id"/>
                    <input type="text" class="form-control" id="projectNo" name="projectNo" placeholder="项目编号">
                    <div class="projectNo_error"></div>
                </div>
            </div>
            <div class="form-group ">
                <label for="projectName" class="col-md-2 control-label">项目名称：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="projectName" name="projectName" placeholder="项目名称">
                    <div class="projectName_error"></div>
                </div>
            </div>
            <div class="form-group ">
                <label for="area" class="col-md-2 control-label">所属区块：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="area" name="areaId" placeholder="所属区块">
                        <option value="">---请选择所属区块---</option>
                        <template v-for="a in areaArray">
                            <option :value="a.id">{{a.text}}</option>
                        </template>
                    </select>
                </div>
            </div>
            <div class="form-group ">
                <label for="city" class="col-md-2 control-label">所属市：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="city" name="cityId" placeholder="所属市">
                        <option value="">---请选择所属市---</option>
                        <template v-for="l in citiesArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                </div>
            </div>
            <div class="form-group ">
                <label for="district" class="col-md-2 control-label">所属县区：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="district" name="districtId" placeholder="所属县区">
                        <option value="">---请选择所属县区---</option>
                        <template v-for="l in districtArray">
                            <option :value="l.id">{{l.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group ">
                <label for="beginDate" class="col-md-2 control-label">实施开始时间：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="beginDate" name="beginDate" placeholder="实施开始时间"
                           onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                </div>
            </div>

            <div class="form-group ">
                <label for="endDate" class="col-md-2 control-label">实施结束时间：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="endDate" name="endDate" placeholder="实施结束时间"
                           onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                </div>
            </div>

            <div class="form-group ">
                <label for="mainPurpose" class="col-md-2 control-label">主要用途：</label>
                <div class="col-md-3">
                    <textarea type="text" class="form-control" id="mainPurpose" name="mainPurpose" placeholder="主要用途"
                              style="width: 58%;resize: none"></textarea>
                </div>
            </div>

            <div class="form-group ">
                <label for="engineeringType" class="col-md-2 control-label">工程类型：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="engineeringType" name="engineeringTypeId"
                            placeholder="工程类型">
                        <option value="">---请选择工程类型---</option>
                        <template v-for="a in engineeringTypeArray">
                            <option :value="a.id">{{a.text}}</option>
                        </template>
                    </select>
                </div>
            </div>

            <div class="form-group ">
                <label for="consTask" class="col-md-2 control-label">建设任务：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control" id="consTask" name="consTask" placeholder="建设任务">
                </div>
            </div>

            <div class="form-group ">
                <label for="important" class="col-md-2 control-label">是否重大项目</label>
                <div class="col-md-3">
                    <select class="form-control" id="important" name="important">
                        <option value="0" selected="true">普通项目</option>
                        <option value="1">重大项目</option>
                    </select>
                </div>
            </div>

            <div class="form-group " hidden>
                <label for="status" class="col-md-2 control-label">状态：</label>
                <div class="col-md-3">
                    <select type="text" class="form-control" id="status" name="status" placeholder="状态">
                        <option value="0" selected="true">未审核</option>
                        <option value="1">已审核</option>
                    </select>
                </div>
            </div>

        </div>
    </form>
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
                    <h2>项目管理-项目建设-项目建设任务信息</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox">
                                <%@include file="../common/common-searchBoxProjectWithDate.jsp" %>
                                <%@include file="../common/common-buttonCrudGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table id="dataTableList" class="table table-bordered table-hover" style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="4%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="projectNo" data-width="5%">项目编号</th>
                                    <th data-column-id="projectName" data-width="8%">项目名称</th>
                                    <th data-column-id="areaName" data-width="7%">所属区块</th>
                                    <th data-column-id="cityName" data-width="5%">所属市</th>
                                    <th data-column-id="districtName" data-width="5%">所属县区</th>
                                    <th data-column-id="beginDate" data-converter="datetime" data-width="5%">实施开始时间</th>
                                    <th data-column-id="endDate" data-converter="datetime" data-width="5%">实施结束时间</th>
                                    <th data-column-id="mainPurpose" data-width="10%">主要用途</th>
                                    <th data-column-id="engineeringType" data-width="5%">工程类型</th>
                                    <th data-column-id="consTask" data-width="18%">建设任务</th>
                                    <th data-column-id="important" data-width="4%" data-visible="false">是否重大项目</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                        data-width="5%">查看详情
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
<%@include file="../common/common-footer.jsp" %>
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.full.min.js"></script>
<script type="text/javascript" src="/js/app/projectConsTask.js"></script>
</body>
</html>