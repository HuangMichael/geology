/**
 * Created by Administrator on 2017/5/23 0023.
 */
var areaVue;
var finishedDataVue;
$(function () {
    initMenus("topMenu");
    var finishedDataList = getFinishedData("", "");
    finishedDataVue = new Vue({
        el: "#dataListTable",
        data: {
            finishedDataList: finishedDataList
        }
    });

    $("#distanceBtn").on("click", function () {
        startRuler1();
    });

    $("#areaBtn").on("click", function () {
        measureArea();
    });
    var areas = getAreasByCity("");
    //画出区块信息
    drawBoldAreas(areas);
    var legendArray = [{
        id: 1,
        marker: "img/marker/legend02.png",
        description: "已围"
    }];
    initLegend("#legendBody", legendArray);
    // 基于准备好的dom，初始化echarts实例：用柱状图表示已围垦的面积
    var xAxisData = []; //ECharts图x轴上的数据
    var seriesData = [];//ECharts图上的数据
    if(finishedDataList){
        for(var x in finishedDataList){
            xAxisData.push(finishedDataList[x].cityName);
            seriesData.push(finishedDataList[x].finishedData);
        }
    }
    drawEChartsOfBar("chartContainer", '江苏省各市已围垦面积' , ["已围面积"] ,"城市" ,xAxisData , "面积:万亩",[{name: '已围面积', type: 'bar',
        label: {
            normal: {
                show: true,
                position: 'outside'
            }
        }, data:seriesData}],['#3398DB']);

});
var layers = [];
var offsetCenter =[126.308154,34.41002];
var map = initMap("container", offsetCenter, areaZoomSize);

var ruler1, ruler2;
//地图初始化
map.plugin(["AMap.RangingTool"], function () {
    ruler1 = new AMap.RangingTool(map);
    AMap.event.addListener(ruler1, "end", function (e) {
        ruler1.turnOff();
    });
    var sMarker = {
        icon: new AMap.Icon({
            size: new AMap.Size(19, 31), //图标大小
            image: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b1.png"
        })
    };
    var eMarker = {
        icon: new AMap.Icon({
            size: new AMap.Size(19, 31), //图标大小
            image: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b2.png"
        }),
        offset: new AMap.Pixel(-9, -31)
    };
    var lOptions = {
        strokeStyle: "solid",
        strokeColor: "#FF33FF",
        strokeOpacity: 1,
        strokeWeight: 2
    };
    var rulerOptions = {
        startMarkerOptions: sMarker,
        endMarkerOptions: eMarker,
        lineOptions: lOptions
    };
    ruler2 = new AMap.RangingTool(map, rulerOptions);
});
//启用默认样式测距
function startRuler1() {
    ruler2.turnOff();
    ruler1.turnOn();
}


function exportMap() {
    alert("导出地图");
}
//测量面积
function measureArea() {
    //在地图中添加MouseTool插件
    map.plugin(["AMap.MouseTool"], function () {
        var mouseTool = new AMap.MouseTool(map);
        //鼠标工具插件添加draw事件监听
        AMap.event.addListener(mouseTool, "draw", function callback(e) {
            var eObject = e.obj; //obj属性就是鼠标事件完成所绘制的覆盖物对象。
        });
        mouseTool.measureArea(); //调用鼠标工具的面积量测功能
    });

}


addLocation();

function addLocation() {
    //加载行政区划插件
    AMap.service('AMap.DistrictSearch', function () {
        var opts = {
            subdistrict: 1, //返回下一级行政区
            extensions: 'all', //返回行政区边界坐标组等具体信息
            level: 'city' //查询行政级别为 市
        };
        //实例化DistrictSearch
        district = new AMap.DistrictSearch(opts);
        district.setLevel('city');
    });
}

var district, polygons = [],
    citycode;
var citySelect = document.getElementById('city');
//var districtSelect = document.getElementById('district');
//var areaSelect = document.getElementById('street');

//行政区划查询
var opts = {
    subdistrict: 1, //返回下一级行政区
    level: 'city',
    showbiz: false //查询行政级别为 市
};
district = new AMap.DistrictSearch(opts); //注意：需要使用插件同步下发功能才能这样直接使用
district.search('江苏省', function (status, result) {
    if (status == 'complete') {
        var cityList = [{
            "citycode": [],
            "adcode": "320000",
            "name": "江苏省",
            "center": {
                "M": 34.41002,
                "I": 126.308154,
                "lng": 126.308154,
                "lat": 34.41002
            },
            "level": "province",
            "districtList": [{
                "citycode": "0513",
                "adcode": "320600",
                "name": "南通市",
                "center": {
                    "M": 32.016212,
                    "I": 120.86460799999998,
                    "lng": 120.864608,
                    "lat": 32.016212
                },
                "level": "city"
            }, {
                "citycode": "0518",
                "adcode": "320700",
                "name": "连云港市",
                "center": {
                    "M": 34.600018,
                    "I": 119.17882099999997,
                    "lng": 119.178821,
                    "lat": 34.600018
                },
                "level": "city"
            }, {
                "citycode": "0515",
                "adcode": "320900",
                "name": "盐城市",
                "center": {
                    "M": 33.377631,
                    "I": 120.13999799999999,
                    "lng": 120.139998,
                    "lat": 33.377631
                },
                "level": "city"
            }]
        }]
        getData(cityList[0]);
    }
});

function getData(data) {
    var bounds = data.boundaries;
    if (bounds) {
        for (var i = 0, l = bounds.length; i < l; i++) {
            var polygon = new AMap.Polygon({
                map: map,
                strokeWeight: 1,
                strokeColor: '#CC66CC',
                fillColor: '#CCF3FF',
                fillOpacity: 0.5,
                path: bounds[i]
            });
            polygons.push(polygon);
        }
        map.setFitView(); //地图自适应
    }

    var subList = data.districtList;
    var level = data.level;

    //清空下一级别的下拉列表
    if (level === 'province') {
        nextLevel = 'city';
        citySelect.innerHTML = '';
        // districtSelect.innerHTML = '';
    } else if (level === 'city') {
        nextLevel = 'district';
        // districtSelect.innerHTML = '';
    } else if (level === 'district') {
        nextLevel = 'street';
    }
    if (subList) {
        var contentSub = new Option('--请选择--');
        for (var i = 0, l = subList.length; i < l; i++) {
            var name = subList[i].name;
            var levelSub = subList[i].level;
            var cityCode = subList[i].citycode;
            if (i == 0) {
                document.querySelector('#' + levelSub).add(contentSub);
            }
            contentSub = new Option(name);
            contentSub.setAttribute("value", levelSub);
            contentSub.center = subList[i].center;
            contentSub.adcode = subList[i].adcode;
            document.querySelector('#' + levelSub).add(contentSub);
        }
    }

}

function search(obj) {

    var selectedCity = $('#city option:selected').text();
    //清除地图上所有行政区划的覆盖物
    for (var i = 0, l = polygons.length; i < l; i++) {
        polygons[i].setMap(null);
    }
    var option = obj[obj.options.selectedIndex];
    district.setLevel(option.value); //行政区级别
    district.setExtensions('all');

    var areas = getAreasByCity(selectedCity);
    drawBoldAreas(areas);

    //右侧显示该城市的围垦信息
    getWkDescByCity(selectedCity);

    var table = document.getElementById("dataListTable");
    var tableHtml = "";
    //echarts显示该城市内各个区块的统计结果----用柱状图表示该市内各区块已围垦的面积
    var xAxisData = []; //ECharts图x轴上的数据
    var seriesData = [];//ECharts图上的数据
    if(areas){
        if(selectedCity === "--请选择--"){
            var finishedDataList = getFinishedData("", "");
            if(finishedDataList){
                for(var x in finishedDataList){
                    xAxisData.push(finishedDataList[x].cityName);
                    seriesData.push(finishedDataList[x].finishedData);
                }
            }
            drawEChartsOfBar("chartContainer", '江苏省各市已围垦面积' , ["已围面积"] ,"城市" ,xAxisData ,"面积:万亩",[{name: '已围面积', type: 'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'outside'
                    }
                },data:seriesData}],['#3398DB']);

            //右侧同时显示此城市内所有区块的统计表格
            if (finishedDataList) {
                tableHtml = "<thead><th>行政区划</th><th>已围面积(万亩)</th></thead>";
                for (var i = 0, len = finishedDataList.length; i < len; i++) {
                    tableHtml += "<tr>" + "<td>" + finishedDataList[i].cityName + "</td>" + "<td>" + finishedDataList[i].finishedData + "</td>" + "</tr>";
                }
            }
            table.innerHTML = tableHtml;
        }else {
            for(var x in areas){
                xAxisData.push(areas[x].areaNo);
                seriesData.push(areas[x].finishedAreaSize);
            }
            drawEChartsOfBar("chartContainer",selectedCity + '各区块已围垦面积' , ["已围面积"] ,"区块编号" ,xAxisData ,"面积:万亩",[{name: '已围面积', type: 'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'outside'
                    }
                },data:seriesData}],['#008B00']);

            //右侧同时显示此城市内所有区块的统计表格
            if (areas) {
                tableHtml = "<thead><th>区块编号</th><th>区块名称</th><th>已围面积(万亩)</th></thead>";
                for (var i = 0, len = areas.length; i < len; i++) {
                    tableHtml += "<tr>" + "<td>" + areas[i].areaNo + "</td>" + "<td>" + areas[i].areaName + "</td>" + "<td>" + areas[i].finishedAreaSize + "</td>"
                    "</tr>";
                }
            }
            table.innerHTML = tableHtml;
        }
    }
}

function setCenter(obj) {
    map.setCenter(obj[obj.options.selectedIndex].center);
}

/*
 *根据城市获取围垦描述
 * */
function getWkDescByCity(cityName) {

    var wkDesc = "<p> 江苏省沿海滩涂围垦区域主要分布在南通市、盐城市和连云港市。</p><p>从上世纪50 年代初到 70 年代末，江苏沿海滩涂的开发利用呈现多元化、快速发展之势，共新围滩涂252万亩，滩涂开发利用方式以兴海煮盐、垦荒植棉为主向农、林、牧、渔、盐综合开发利用方面发展。改革开放以后，江苏沿海滩涂开发速度加快，至“八五”期末，江苏省匡围滩涂58万亩，以粮棉生产和水产养殖为主，实行农林牧渔盐业综合开发。“九五”和“十五”期间实施了百万亩滩涂开发工程，滩涂对沿海地区的经济支持和辐射带动作用越来越大。“十一五”期间，江苏沿海围垦进入了一个以增加工业用地为主的新阶段。围垦面积主要用于港口、能源、化工、物流、城镇、生态旅游等建设用地。1951-2009年，江苏省共围垦滩涂421万亩。“十二五”期间我省进入“270万亩滩涂围垦开发”第一阶段，在条件比较成熟的区域，边滩围垦已开展大规模的实施。截至2015年12月底，完成匡围约62万亩。</p>";


    var wkDescArray = [{
        id: 1,
        content: "<p>连云港市沿海滩涂围垦区域主要分布在灌云县、连云区、赣榆区。</p><p>据连云港市上报数据，目前连云港市沿海滩涂围垦的完成情况如下： 2010年1月-2012年12月完成围垦面积5.296万亩，2013年1月-2014年6月30日完成围垦面积0.835万亩，共完成6.131万亩。据遥感资料显示，截至2014年6月连云港沿岸高滩匡围约6.32万亩。</p>",
        city: "连云港市"
    }, {
        id: 2,
        content: "<p>盐城市沿海滩涂围垦区域主要分布在东台市、大丰市、射阳县、滨海县和响水县。</p><p>据盐城市上报数据，目前盐城市沿海滩涂围垦的完成情况如下： 2010年1月-2012年12月，完成围垦面积7.6万亩；2013年1月-2014年6月30日，完成围垦面积7.75万亩；共完成15.35万亩。据遥感资料显示，截至2014年6月盐城沿岸高滩匡围约29.21万亩。     其中，大丰市的围垦面积最大，占江苏省总围垦面积的18%左右（遥感数据），而新增围垦面积主要集中在东台市。</p>",
        city: "盐城市"
    }, {
        id: 3,
        content: "<p>南通市沿海滩涂围垦区域主要分布在启东市、海门市、通州市、如东市和海安市。</p><p>据南通市上报数据，目前南通市沿海滩涂围垦的完成情况如下：2010年1月-2012年12月，完成匡围面积18.48万亩；2013年1月-2014年6月30日，完成匡围面积9.435万亩；共完成27.915万亩。据遥感资料显示，截至2014年6月南通沿岸高滩匡围约42.01万亩。其中，如东市的围垦面积占全市围垦面积的比例最大，新增围垦面积也主要集中在如东市。</p>",
        city: "南通市"
    }];

    for (var x in wkDescArray) {
        if (wkDescArray[x]["city"] == (cityName)) {
            wkDesc = wkDescArray[x]["content"];
            break;
        }
    }

    $("#wkDesc").html(wkDesc);
    return wkDesc;
}


/**
 *
 * @param containerId 容器id
 * @param level 级别  city
 * @param cityName 城市名称
 */
function getFinishedData(level, cityName) {
    var finishedDataList = [];
    if (!level) {
        finishedDataList = getFinishedAreaData();
    } else if (level == ("city")) {
        var areas = getAreasByCity(cityName);
        for (var x in areas) {
            finishedDataList.push({
                    cityName: areas[x]["areaName"],
                    finishedData: (areas[x]["finisedAreaSize"]) ? (areas[x]["finisedAreaSize"]) : 0
                }
            );
        }
    }
    return finishedDataList;


}


/**
 * 刷新页面
 */
function refreshPage() {
    window.location.href = "/main/index";
}

/**
 *
 * @param areaNo 显示区块详细信息
 */
function showAreaDetail(areaNo) {
    //控件显示围垦区块描述信息
    var desc = getAreaMsgByAreaNo(areaNo, "situationDesc");
    $("#wkDesc").html(desc);



    //加载图片信息
    //$("#wkGallery").html("<a href='javascript:void(0)'><img src='img/" + areaNo + ".png'" + " class='img-responsive img-thumbnail'/></a><a href='javascript:void(0)'><img src='img/video.png'" + " class='img-responsive img-thumbnail'/></a>");
    var $ul = $('<ul style="width:3370px"></ul><div class="guess-btn"><a href="javascript:void(0);" class="prev"><img src="img/button/btn-left.png" alt=""></a> <a href="javascript:void(0);" class="next"><img src="img/button/btn-right.png" alt=""></a> </div>');
    $('#wkGallery').html($ul);
    var arr = getAreaMsgByAreaNo(areaNo, "img");
    console.log("areaNo------------------------"+areaNo);
    console.log("arr------------------------"+JSON.stringify(arr));
    for(var i=0;i < arr.length ;i++){
        var $li = $('<li></li>');
        var $img = $("<img src='" + arr[i] + "' class='img-responsive img-thumbnail'/>");
        $img.width = 256;
        $li.append($img);
        $('#wkGallery ul').append($li);

    }





    //图片轮播
    var index = 0;
    var $aLi =  $("#wkGallery ul").find('li') ;
    var $last = $aLi.clone();
    //console.log($last)
    var $ul = $('#wkGallery ul') ;
    $ul.append($last);
    $aLi =  $("#wkGallery ul").find('li') ;
    var $aLiw=$aLi.width();
    $len = $aLi.size();

    //自动轮播
    function go(){
        index++;
        if(index == $len-4){
            index = 1;
            $ul.css('marginLeft',0);
        }
        $ul.stop().animate({
            marginLeft:-$aLiw*index
        })

    }
    setInterval(go,4000)


    //猜您喜欢部分，按钮点击轮播
    //左按钮。index--
    $(".guess-btn a.prev").on('click',function(){
        clearInterval;
        index--;
        if(index < 1){
            index =5;
            $ul.css('marginLeft',0);
        }
        $ul.stop().animate({
            marginLeft:-$aLiw*index
        })

    })
    //右按钮。index++
    $(".guess-btn a.next").on('click',function(){
        clearInterval;
        go()

    })



    // 判断右边区块信息是否显示
    if($(".row-fluid .span3").css("left","500px")){
        $(".row-fluid .span3").css("left","0px")
        $("#btn_m").css("display","block");
        $("#btn_b").css("display","none");
        $(".row-fluid .span9").css("width","74.4681%");
    }

    drawPieChart("chartContainer",areaNo,"已围面积");

}

