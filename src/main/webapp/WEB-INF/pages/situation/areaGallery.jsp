<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/17
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="areaGallery" class="carousel slide" style="padding:10px;" data-ride="carousel">
    <!-- 轮播（Carousel）指标 -->
    <ol class="carousel-indicators" id="areaGallery_ol">
    </ol>
    <!-- 轮播（Carousel）项目 -->
    <div class="carousel-inner" id="areaGallery_inner">

    </div>
    <!-- 轮播（Carousel）导航 -->
    <a class="carousel-control left" href="#areaGallery"
       data-slide="prev">&lsaquo;</a>
    <a class="carousel-control right" href="#areaGallery"
       data-slide="next">&rsaquo;</a>
</div>
<script type="text/javascript">

    $(function () {

        $('#areaGallery').carousel();
    });

</script>