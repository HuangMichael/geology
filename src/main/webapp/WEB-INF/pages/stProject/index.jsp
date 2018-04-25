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

<div id="layer" class="Absolute-Center" style="height: 149%;">
    <div class="div1" style="width: 932px;height: 600px;margin:421px  auto 0;padding: 0">
        <a class="off" style="float: right;"><img src="img/off.png" alt=""></a>
        <div id="chart" style="height: 540px;width: 900px"></div>
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
                    <h2>查询统计-统计分析-项目信息统计分析</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" style="display: inline" id="searchBox">
                                <%@include file="../common/common-searchBoxProjectWithDate.jsp" %>
                                <%@include file="../common/common-buttonSearchGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table class="table table-hover table-bordered" id="dataTableList" style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="3%" data-type="numeric" data-identifier="true">
                                        序号
                                    </th>
                                    <th data-column-id="projectNo" data-width="4%">项目编号</th>
                                    <th data-column-id="projectName" data-width="8%">项目名称</th>
                                    <th data-column-id="areaName" data-width="7%">所属区块</th>
                                    <th data-column-id="cityName" data-width="5%">所属市</th>
                                    <th data-column-id="districtName" data-width="5%">所属县区</th>
                                    <th data-column-id="beginYear" data-converter="datetime" data-width="5%">开始年份</th>
                                    <th data-column-id="endYear" data-converter="datetime" data-width="5%">结束年份</th>
                                    <th data-column-id="projectSize" data-width="5%">占地面积(万亩)</th>
                                    <th data-column-id="mainPurpose" data-width="8%" data-visible="false">主要用途</th>
                                    <th data-column-id="projectLeader" data-width="8%" data-visible="false">项目负责人</th>
                                    <th data-column-id="leaderContact" data-width="8%" data-visible="false">负责人电话</th>
                                    <th data-column-id="consUnit" data-width="8%" data-visible="false">施工单位</th>
                                    <th data-column-id="monitorUnit" data-width="8%" data-visible="false">监管单位</th>
                                    <th data-column-id="acceptUnit" data-width="8%" data-visible="false">验收单位</th>
                                    <th data-column-id="landUsingType" data-width="4%">用地类型</th>
                                    <th data-column-id="engineeringType" data-width="4%">工程类型</th>
                                    <th data-column-id="buildStatus" data-width="4%">建设状态</th>
                                    <th data-column-id="budget" data-width="4%" data-visible="false">预算金额(亿元)</th>
                                    <th data-column-id="investedSum" data-width="4%" data-visible="false">已投资金额(亿元)</th>
                                    <th data-column-id="taskProgress" data-width="4%" data-visible="false">投资进度</th>
                                    <th data-column-id="important" data-width="4%" data-visible="false">是否重大项目</th>
                                    <th data-column-id="commands" data-formatter="commands" data-width="5%">查看统计图</th>
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
<script type="text/javascript" src="/js/app/stProject.js"></script>
</body>
</html>