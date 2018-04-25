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
    <link href="css/lsyg_styles.css" media="all" rel="stylesheet">
</head>

<body class="dashboard" style="position:relative;">
<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp" %>
<div class="timeline" style="">
    <img style="position:absolute;left:0px;top:0px;width:100%;height:100%;z-Index:-1;" src="/img/title-bg.jpg"/>
    <div class="timeline-content">
        <div class="containerHeadline tableHeadline">
            <i class="icon-table"></i>
            <h2>围垦概况-历史沿革</h2>
        </div>
        <div class="timeline-tree" id="treeContainer0">

        </div>
        <div class="timeline-animated" id="treeContainer1">
            <div class="timeline-row">
                <div class="panel timeline-content">

                    <div class="panel-body">
                        <div id="media"></div>
                        <h2 id="title"></h2>
                        <p id="historyDescDiv"></p>
                    </div>
                    <div id="loader" class="pageload-overlay" data-opening="M 40,-65 145,80 -65,80 40,-65" data-closing="m 40,-65 0,0 L -65,80 40,-65">
                        <svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" viewBox="0 0 80 60" preserveAspectRatio="none">
                            <%--<path d="M 40,-65 145,80 40,-65"/>--%>
                        </svg>
                    </div>
                </div>

            </div>

        </div>
    </div>
</div>
<div class="clear"></div>
<!-- ==================== END OF PAGE CONTENT ==================== -->
<script src="js/vendor/jquery-1.9.1.min.js"></script>
<script>
    function x() {
         var arr = ["M40,30 c 0,0 0,0 0,0 0,0 0,0 0,0 0,0 0,0 0,0 0,0 0,0 0,0 Z",
             "M 0,0 c 0,0 -16.5,43.5 0,60 16.5,16.5 80,0 80,0 L 0,60 Z",
             "M 0,0 0,60 80,60 80,0 Z M 80,0 80,60 0,60 0,0 Z",
             "M 40,100 150,0 l 0,0 z",
             "m 0,60 80,0 0,0 -80,0",
             "M 0,0 0,60 80,60 80,0 Z M 80,0 80,60 0,60 0,0 Z",
             "M 0,70 80,60 80,80 0,80 0,70",
             "M 40,-65 145,80 40,-65",
             "m -5,-5 0,70 90,0 0,-70 z m 5,5 c 0,0 7.9843788,0 40,0 35,0 40,0 40,0 l 0,60 c 0,0 -3.944487,0 -40,0 -30,0 -40,0 -40,0 z",
             "m -10,-10 0,80 100,0 0,-80 z M 40,-40.5 120,30 40,100 -40,30 z",
             "m 75,-80 155,0 0,225 C 90,85 100,30 75,-80 z"];
         var len = arr.length;
         var i =  Math.floor(Math.random() * len + 1)-1;
        var path = "<path d='"+arr[i]+"'/>";
        $("#loader svg").html(path);
    }
    x();

    setInterval(function(){
        x();
    },2000);

</script>
<script src="js/vue-2.2.6.js"></script>
<!-- editable fields plugin -->
<script type="text/javascript" src="js/vendor/bootstrap.min.js"></script>
<script type="text/javascript" src="js/app/menu.js"></script>
<script type="text/javascript" src="/js/common/common-crud.js"></script>
<script type="text/javascript" src="/js/common/common-title.js"></script>
<script type="text/javascript" src="/js/svg/snap.svg-min.js"></script>
<script type="text/javascript" src="/js/svg/classie.js"></script>
<script type="text/javascript" src="/js/svg/svgLoader.js"></script>
<script type="text/javascript" src="/js/app/wkgk_lsyg.js"></script>
</body>

</html>