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
<div class="content-kfly" style="position:relative;">
    <img src="img/title-bg.jpg"  style="position:absolute;left:0px;top:0px;width:100%;height:100%;z-Index:-1;">
    <div class="timeline-kfly" style="padding:0px">
        <div class="row-fluid">
            <div class="containerHeadline tableHeadline">
            <i class="icon-table"></i>
            <h2>开发利用-开发成果</h2>
            </div>
            <div class="span2">
                <div class="floatingBox-tree">
                    <div id="devUsingTree">

                    </div>
                </div>
            </div>

            <div class="span10">
                <div id="kfcgContainer" >
                </div>
            </div>
        </div>

    </div>

</div>
</div>
<script type="text/javascript" src="js/vendor/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/vue-2.2.6.js"></script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script type="text/javascript" src="js/vendor/bootstrap.min.js"></script>
<script type="text/javascript" src="js/app/menu.js"></script>
<script type="text/javascript" src="/js/common/common-utils.js"></script>
<script type="text/javascript" src="/js/common/common-crud.js"></script>
<script type="text/javascript" src="/js/common/common-title.js"></script>
<script type="text/javascript" src="/js/app/kfly_kfcg.js"></script>
</body>
</html>
