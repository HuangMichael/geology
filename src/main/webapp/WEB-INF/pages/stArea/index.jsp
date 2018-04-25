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

<div class="content">
    <div id="layer" class="Absolute-Center" style="height: 100%;">
        <div class="div1" style="width: 932px;height: 536px;margin:270px  auto 0;padding: 0">
            <a class="off" style="float: right;"><img src="img/off.png" alt=""></a>
            <div id="chart" style="height: 500px;width: 900px"></div>
        </div>
    </div>
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
                    <h2>查询统计-统计分析报表-围垦区块统计分析</h2>
                </div>
                <div class="floatingBox table">
                    <div class="container-fluid">
                        <form class="form-inline pull-left">
                            <div class="form-group" id="searchBox" style="display: inline">
                                <%@include file="../common/common-searchBox.jsp" %>
                                <%@include file="../common/common-buttonSearchGroup.jsp" %>
                            </div>
                        </form>
                        <div id="table">
                            <table class="table table-hover table-bordered" id="dataTableList" style="margin-top:10px">
                                <thead>
                                <tr>
                                    <th data-column-id="id" data-width="5%" data-type = "numeric" data-identifier="true">序号</th>
                                    <th data-column-id="areaNo" data-width="5%">区块编号</th>
                                    <th data-column-id="areaName" data-width="12%">区块名称</th>
                                    <th data-column-id="cityName" data-width="8%">所在市</th>
                                    <th data-column-id="districtName" data-width="8%">所在区县</th>
                                    <th data-column-id="buildSize" data-width="8%">规划面积(万亩)</th>
                                    <th data-column-id="inningSize" data-width="8%">已围面积(万亩)</th>
                                    <th data-column-id="functionType" data-width="8%">区块功能类型</th>
                                    <th data-column-id="locDesc" data-width="10%">位置描述</th>
                                    <th data-column-id="mainPurpose" data-width="15%">主要用途</th>
                                    <th data-column-id="inningStatus" data-width="5%">围垦状态</th>
                                    <th data-column-id="areaType" data-width="8%" data-visible="false">区块类型</th>
                                    <th data-column-id="avgHeight" data-width="8%" data-visible="false">平均起围高程(米)</th>
                                    <th data-column-id="areaDesc" data-width="10%" data-visible="false">区块描述</th>
                                    <th data-column-id="commands" data-formatter="commands" data-width="6%">查看统计图</th>
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
<script type="text/javascript" src="/js/app/stArea.js"></script>
</body>
</html>