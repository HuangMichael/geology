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
<div class="modal fade confirm_modal subfield " id="confirm_modal">
    <div class="modal-body">项目验收基本信息</div>
    <form class="form-horizontal subfield " id="projectAcceptForm">

        <div class="row-fluid">
            <div class="form-group ">
                <div class="pull-left">
                    <label for="projectNo" class="col-md-2 control-label">项目编号：</label>
                    <div class="col-md-3">
                        <input type="hidden" id="id" name="id"/>
                        <input type="text" class="form-control" id="projectNo" name="projectNo" placeholder="项目编号">
                        <div class="projectNo_error"></div>
                    </div>
                </div>
                <div class="pull-left">
                    <label for="projectName" class="col-md-2 control-label">项目名称：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="projectName" name="projectName" placeholder="项目名称">
                        <div class="projectName_error"></div>
                    </div>
                </div>
            </div>
            <div class="form-group ">
                <div class="pull-left">
                    <label for="projectSize" class="col-md-2 control-label">项目面积(万亩)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="projectSize" name="projectName" placeholder="项目名称">
                        <div class="projectName_error"></div>
                    </div>
                </div>
                <div class="pull-left">
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
            </div>
            <div class="form-group ">
                <div class="pull-left">
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
                <div class="pull-left">
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
            </div>
            <div class="form-group ">

                <div class="pull-left">
                    <label for="beginDate" class="col-md-2 control-label">实施开始时间：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="beginDate" name="beginDate" placeholder="实施开始时间"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="endDate" class="col-md-2 control-label">实施结束时间：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="endDate" name="endDate" placeholder="实施结束时间"
                               onClick="WdatePicker({dateFmt:'yyyy-MM',isShowToday:false})">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="mainPurpose" class="col-md-2 control-label">主要用途：</label>
                    <div class="col-md-3">
                    <textarea type="text" class="form-control" id="mainPurpose" name="mainPurpose"
                              placeholder="主要用途" style="width: 57%;resize: none"></textarea>
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="landUsingType" class="col-md-2 control-label">用地类型：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="landUsingType" name="landUsingTypeId"
                                placeholder="用地类型">
                            <option value="">---请选择用地类型---</option>
                            <template v-for="a in landUsingTypeArray">
                                <option :value="a.id">{{a.text}}</option>
                            </template>
                        </select>
                    </div>
                </div>
                <div class="pull-left">
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

            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="budget" class="col-md-2 control-label">预算金额(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="budget" name="budget" placeholder="预算金额">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="investedSum" class="col-md-2 control-label">已投资金额(亿元)：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="investedSum" name="investedSum" placeholder="已投资金额">
                    </div>
                </div>

            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="consUnit" class="col-md-2 control-label">建设单位：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="consUnit" name="consUnit" placeholder="建设单位">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="monitorUnit" class="col-md-2 control-label">监管单位：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="monitorUnit" name="monitorUnit" placeholder="监管单位">
                    </div>
                </div>

            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="acceptUnit" class="col-md-2 control-label">验收单位：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="acceptUnit" name="acceptUnit" placeholder="验收单位">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="projectLeader" class="col-md-2 control-label">项目负责人：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="projectLeader" name="projectLeader"
                               placeholder="项目负责人">
                    </div>
                </div>

            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="leaderContact" class="col-md-2 control-label">负责人电话：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="leaderContact" name="leaderContact"
                               placeholder="负责人电话">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="taskProgress" class="col-md-2 control-label">投资进度：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="taskProgress" name="taskProgress"
                               placeholder="投资进度">
                    </div>
                </div>

            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="expert" class="col-md-2 control-label">专家组长：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="expert" name="expert" placeholder="专家组长">
                    </div>
                </div>
                <div class="pull-left">
                    <label for="conclusion" class="col-md-2 control-label">验收结论：</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control" id="conclusion" name="conclusion" placeholder="验收结论">
                    </div>
                </div>
            </div>

            <div class="form-group ">
                <div class="pull-left">
                    <label for="important" class="col-md-2 control-label">是否重大项目</label>
                    <div class="col-md-3">
                        <select class="form-control" id="important" name="important">
                            <option value="0" selected="true">普通项目</option>
                            <option value="1">重大项目</option>
                        </select>
                    </div>
                </div>
                <div class="pull-left" hidden>
                    <label for="status" class="col-md-2 control-label">状态：</label>
                    <div class="col-md-3">
                        <select type="text" class="form-control" id="status" name="status" placeholder="状态">
                            <option value="0" selected="true">未审核</option>
                            <option value="1">已审核</option>
                        </select>
                    </div>
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
                    <h2>项目管理-项目建设-项目验收基本信息</h2>
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
                            <table id="dataTableList" class="table table-hover table-bordered"
                                   style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="3%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="projectNo" data-width="4%">项目编号</th>
                                    <th data-column-id="projectName" data-width="8%">项目名称</th>
                                    <th data-column-id="areaName" data-width="7%">所属区块</th>
                                    <th data-column-id="cityName" data-width="4%">所属市</th>
                                    <th data-column-id="districtName" data-width="4%">所属县区</th>
                                    <th data-column-id="projectSize" data-width="6%" data-visible="false">项目面积(万亩)</th>
                                    <th data-column-id="beginDate" data-converter="datetime" data-width="4%">开始年份</th>
                                    <th data-column-id="endDate" data-converter="datetime" data-width="4%">结束年份</th>
                                    <th data-column-id="mainPurpose" data-width="8%">主要用途</th>
                                    <th data-column-id="engineeringType" data-width="5%" data-visible="false">工程类型</th>
                                    <th data-column-id="landUsingType" data-width="5%" data-visible="false">用地类型</th>
                                    <th data-column-id="budget" data-visible="false">预算(亿元)</th>
                                    <th data-column-id="investedSum" data-visible="false">投资金额(亿元)</th>
                                    <th data-column-id="projectLeader" data-visible="false">项目负责人</th>
                                    <th data-column-id="leaderContact" data-visible="false">负责人联系方式</th>
                                    <th data-column-id="consUnit" data-width="5%" data-visible="false">建设单位</th>
                                    <th data-column-id="monitorUnit" data-width="5%" data-visible="false">监管单位</th>
                                    <th data-column-id="acceptUnit" data-width="5%">验收单位</th>
                                    <th data-column-id="taskProgress" data-width="4%" data-visible="false">任务进度</th>
                                    <th data-column-id="expert" data-width="4%">专家组长</th>
                                    <th data-column-id="conclusion" data-width="4%">验收结论</th>
                                    <th data-column-id="important" data-width="4%" data-visible="false">是否重大项目</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false"
                                        data-width="4%">查看详情
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
<script type="text/javascript" src="/js/select2-4.0.3/dist/js/select2.full.min.js"></script>
<script src="/js/app/projectAccept.js"></script>
</body>

</html>