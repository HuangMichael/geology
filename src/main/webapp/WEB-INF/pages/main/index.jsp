<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<!--[if lt IE 7]>
<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>
<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>
<html class="no-js lt-ie9"> <![endif]-->

<!--[if gt IE 8]><!-->
<html class="no-js" xmlns: xmlns:v-bind="http://www.w3.org/1999/xhtml">
<!--<![endif]-->

<head>
    <%@include file="../common/common-header.jsp" %>
</head>

<body class="dashboard">

<%@include file="../common/common-title.jsp" %>
<%@include file="../common/common-topMenu.jsp"%>

<div class="content" id="contentContainer" style="position:relative;">
    <img style="position:absolute;left:0px;top:0px;width:100%;height:100%;z-Index:-1;" src="img/title-bg.jpg" />
    <ul class="menu" id="navMenu">
        <template v-for="menu in menus">
            <li tabindex="1">
                <span class="title">{{menu.menuName}}</span>
                <div class="layer"></div>
                <div class="content">
                    <span class="title">{{menu.menuName}}</span>
                    <template v-for="m in menu.menus">
                        <div id="menu11" class="navSubMenu" v-bind:data-url='m.event'>{{m.menuName}}</div>
                    </template>

                </div>
            </li>
        </template>
    </ul>
</div>

<!--</div>-->
<!-- ==================== END OF PAGE CONTENT ==================== -->
<script src="js/vendor/jquery-1.9.1.min.js"></script>
<script src="js/vue-2.2.6.js"></script>
<script src="js/vendor/bootstrap-slider.js"></script>
<!-- bootstrap slider plugin -->
<script src="js/vendor/jquery.sparkline.min.js"></script>
<!-- small charts plugin -->
<script src="js/vendor/jquery.flot.min.js"></script>
<!-- charts plugin -->
<script src="js/vendor/jquery.flot.resize.min.js"></script>
<!-- charts plugin / resizing extension -->
<script src="js/vendor/jquery.flot.pie.min.js"></script>
<!-- charts plugin / pie chart extension -->
<script src="js/vendor/wysihtml5-0.3.0_rc2.min.js"></script>
<!-- wysiwyg plugin -->
<script src="js/vendor/bootstrap-wysihtml5-0.0.2.min.js"></script>
<!-- wysiwyg plugin / bootstrap extension -->
<script src="js/vendor/bootstrap-tags.js"></script>
<!-- multivalue input tags -->
<script src="js/vendor/jquery.tablesorter.min.js"></script>
<!-- tablesorter plugin -->
<script src="js/vendor/jquery.tablesorter.widgets.min.js"></script>
<!-- tablesorter plugin / widgets extension -->
<script src="js/vendor/jquery.tablesorter.pager.min.js"></script>
<!-- tablesorter plugin / pager extension -->
<script src="js/vendor/raphael.2.1.0.min.js"></script>
<!-- vector graphic plugin / for justgage.js -->
<script src="js/vendor/justgage.js"></script>
<!-- justgage plugin -->
<script src="js/vendor/bootstrap-multiselect.js"></script>
<!-- multiselect plugin -->
<script src="js/vendor/bootstrap-datepicker.js"></script>
<!-- datepicker plugin -->
<script src="js/vendor/bootstrap-colorpicker.js"></script>
<!-- colorpicker plugin -->
<script src="js/vendor/parsley.min.js"></script>
<!-- parsley validator plugin -->
<script src="js/vendor/formToWizard.js"></script>
<!-- form wizard plugin -->
<!--<script src="js/vendor/bootstrap-editable.min.js"></script>-->
<!-- editable fields plugin -->
<script src="js/thekamarel.min.js"></script>
<script type="text/javascript" src="js/vendor/bootstrap.min.js"></script>
<script type="text/javascript" src="js/app/getData.js"></script>
<script type="text/javascript" src="/js/app/menu.js"></script>
<script type="text/javascript" src="/js/app/index.js"></script>

<script src="/js/bootgrid-1.3.1/jquery.bootgrid.js"></script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script src="/js/common/common-crud.js"></script>
<script src="/js/common/common-title.js"></script>
</body>
</html>