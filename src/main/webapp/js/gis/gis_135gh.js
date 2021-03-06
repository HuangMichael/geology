var baseMaps = getBaseMaps();
var userPosition = getLoginUserPosition();
var view;
var bmisProvLayer, buildAreaLayer;
require(layoutArray, function (Map, SceneView, GroupLayer, FeatureLayer, UniqueValueRenderer, ExtrudeSymbol3DLayer, PolygonSymbol3D, Home, Search, BasemapToggle, Legend, geometryEngine, LabelClass, TextSymbol3DLayer, LabelSymbol3D, LayerList, on) {
    var bufferAreaSym = new PolygonSymbol3D({
        symbolLayers: [
            new ExtrudeSymbol3DLayer({
                material: {
                    color: "#a27c1a"
                }
            })
        ]
    });


    var reserveAreaSym = new PolygonSymbol3D({
        symbolLayers: [
            new ExtrudeSymbol3DLayer({
                material: {
                    color: "#FF7F7E"
                }
            })
        ]
    });

    var plan135AreaSym = new PolygonSymbol3D({
        symbolLayers: [
            new ExtrudeSymbol3DLayer({
                material: {
                    color: "#ff3e1b"
                }
            })
        ]
    });

    var experimentAreaSym = new PolygonSymbol3D({
        symbolLayers: [
            new ExtrudeSymbol3DLayer({
                material: {
                    color: "#8198d9"
                }
            })
        ]
    });


    var plan135Renderer = new UniqueValueRenderer({
        defaultSymbol: plan135AreaSym,
        defaultLabel: "十三五规划",
        field: "十三五规划",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });

    var reserveRenderer = new UniqueValueRenderer({
        defaultSymbol: reserveAreaSym,
        defaultLabel: "自然保护核心区",
        field: "自然保护核心区",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });

    var bufferRenderer = new UniqueValueRenderer({
        defaultSymbol: bufferAreaSym,
        defaultLabel: "自然保护缓冲区",
        field: "自然保护缓冲区",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });


    var experimentRenderer = new
    UniqueValueRenderer({
        defaultSymbol: experimentAreaSym,
        defaultLabel: "自然保护实验区",
        field: "自然保护实验区",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });


    var
        bufferAreaLayer,
        reserveAreaLayer,
        experimentLayer,
        coastLineLayer,
        beachLayer,

        sandLandLayer,

        borderGroupLayer,
        natureGroupLayer,
        wkGroupLayer,
        seaGroupLayer;

    var map = new Map({
        basemap: baseMaps[0],
        ground: "world-elevation",//,
        center: userPosition
    });


    view = new SceneView({
        container: "viewDiv",
        map: map
    });
    var homeBtn = new Home({
        view: view
    });
    // Add the home button to the top left corner of the view
    view.ui.add(homeBtn, "top-left");


    view
        .then(function () {

            bmisProvLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "js", "江苏省界", "white", "{loc_name}", null, true);
            buildAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, plan135Renderer, "thirteenFivePlan", "十三五规划", "yellow", "", null, true);
            var t135PlanCallOutLabel = createCalloutLabelLayer(FeatureLayer, "thirteenFivePlan", "十三五规划标注", "{memo}");
            bufferAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, bufferRenderer, "natureReserveBuffer", "自然保护缓冲区", "yellow", "{buffer_name}", null, false);
            reserveAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, reserveRenderer, "natureReserveArea", "自然保护核心区", "yellow", "{area_name}", null, false);
            experimentLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, experimentRenderer, "natureReserveExperiment", "自然保护试验区", "yellow", "{experiment_name}", null, false);
            coastLineLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "coastLine", "海岸线", "#fcaa09", "{line_name}", getCoastLinePopup(), true);
            beachLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "beach", "滩涂", "#23fcd0", "{beach_name}", getBeachPopup(), true);

            sandLandLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "sandLand", "沿海沙洲", "#23fcd0", "辐射沙脊群", getSandPopup(), true);

            borderGroupLayer = createGroupLayer(GroupLayer, "江苏行政区划", [bmisProvLayer]);
            natureGroupLayer = createGroupLayer(GroupLayer, "自然保护区主题", [experimentLayer, bufferAreaLayer, reserveAreaLayer], false);
            wkGroupLayer = createGroupLayer(GroupLayer, "围垦主题", [buildAreaLayer, t135PlanCallOutLabel]);
            seaGroupLayer = createGroupLayer(GroupLayer, "其他主题", [beachLayer, coastLineLayer, sandLandLayer]);

            map.layers.add(seaGroupLayer);
            map.layers.add(natureGroupLayer);
            map.layers.add(borderGroupLayer);
            map.layers.add(wkGroupLayer);


            var legend = new Legend({
                view: view,
                layerInfos: [{
                    layer: buildAreaLayer,
                    title: ""
                },
                    {
                        layer: bufferAreaLayer,
                        title: ""
                    },
                    {
                        layer: reserveAreaLayer,
                        title: ""
                    },
                    {
                        layer: experimentLayer,
                        title: ""
                    },
                    {
                        layer: beachLayer,
                        title: "滩涂"
                    }, {
                        layer: coastLineLayer,
                        title: "海岸线"
                    },
                    {
                        layer: sandLandLayer,
                        title: "沿海沙洲"
                    }
                ]
            });

            // Add widget to the bottom right corner of the view
            view.ui.add(legend, {
                position: "bottom-left",
                index: 1
            });


            // 1 - Create the widget
            var toggle = new BasemapToggle({
                view: view, // view that provides access to the map's 'topo' basemap
                nextBasemap: baseMaps[1]// allows for toggling to the 'hybrid' basemap
            });
            // Add widget to the top right corner of the view
            view.ui.add(toggle, {
                position: "top-left",
                index: 100
            });


            var layerList = new LayerList({
                view: view
            });
            view.ui.add(layerList, {
                position: "bottom-left",
                index: 2
            });

            view.goTo(initViewPoint(userPosition));
            initLocationSelect("#selectLocation");
            initAreaSelect("#selectArea");
            initShowInfo();


            $("#closed").click(function () {
                $("#map_info_panel").hide();
            });

            $("#selectLocation").on("change", function () {

                var locId = $(this).val();
                //根据选择的行政区划去更新区块列表
                initAreaSelectWithLocId("#selectArea", locId);
                locateLoc(locId);
                fillInfoPanelByLocId(locId);
                $("#map_info_panel").show("fast");

            });


            $("#selectArea").on("change", function () {
                var areaId = $(this).val();
                locateArea(areaId);
                fillInfoPanelByAreaId(areaId);
                $("#map_info_panel").show("fast");

            });


        })
        .otherwise(function (err) {
            console.error("SceneView rejected:", err);
        });
});


function getLocDesc(locId) {
    $.ajaxSettings.async = false;
    var url = "/thirteenFivePlan/findPlanDesc/" + locId;
    var planDesc = null;
    $.getJSON(url, function (data) {
        planDesc = data["locationDesc"];
    });
    return planDesc;
}

/**
 *
 * @returns {*}
 * 十三五规划统计数据
 */
function getThirteenFivePlanStat() {
    $.ajaxSettings.async = false;
    var url = "/thirteenFivePlan/135PlanProv";
    var dataList = null;
    $.getJSON(url, function (data) {
        dataList = data;
    });
    return dataList;
}

/**
 *
 * @returns {*}
 * 十三五规划统计数据
 */
function getThirteenFivePlanStatCity(parentId) {
    $.ajaxSettings.async = false;
    var url = "/thirteenFivePlan/135PlanCity/" + parentId;
    var dataList = null;
    $.getJSON(url, function (data) {
        dataList = data;
        console.log("dataList--", JSON.stringify(dataList));
    });
    return dataList;
}


/**
 *
 * @param dataList
 * @returns {Array}
 */
function getThirteenFivePlanCities(dataList) {
    var cities = [];
    for (var x = 0; x < dataList.length; x++) {
        cities[x] = dataList[x]["locName"];
    }
    return cities;
}


function getThirteenFivePlanCitiesValues(dataList) {
    var citiesValues = [];
    for (var x = 0; x < dataList.length; x++) {
        citiesValues[x] = {value: dataList[x]["buildSize"], name: dataList[x]["locName"]}
    }
    return citiesValues;
}


/**
 *
 * @param id
 * @returns {*}
 *
 * 根据id获取位置信息
 */
function findById(id) {
    var object = null;
    $.ajaxSettings.async = false;
    var url = "location/findById/" + id;
    $.get(url, function (data) {
        object = data;
    });
    return object;
}


/**
 *
 * @param locId位置id
 * 根据位置id填充信息面板
 */
function fillInfoPanelByLocId(locId) {
    //填充围垦描述内容
    //根据位置id查询出围垦描述 将围垦描述显示在内容中
    showWkdesc(locId, "#stat_desc");
    showDataTable(locId, "#stat_tablelistBody");
    showChart(locId, "stat_chart");
}


/**
 *
 * 根据区块id填充信息面板
 * @param areaId
 */
function fillInfoPanelByAreaId(areaId) {
    //填充围垦描述内容
    //根据位置id查询出围垦描述 将围垦描述显示在内容中
    showAreadesc(areaId, "#stat_desc");
    showAreaDataList(areaId, "#stat_tablelistBody");
    showAreasChart(areaId, "stat_chart");
}


/**
 *
 * @param locId
 */
function showWkdesc(locId, container) {
    var locDesc = getLocDesc(locId);
    $(container).html(locDesc);
}


/**
 *
 * @param locId
 * @param dataList
 * @param container
 */
function showDataTable(locId, container) {
    var locObject = findById(locId);
    var dataList = (!locObject["locLevel"]) ? getThirteenFivePlanStat() : getThirteenFivePlanStatCity(locId);
    var html = "";
    for (var x = 0; x < dataList.length; x++) {
        html += "<tr><td width='20%'>" + parseInt(x + 1) + "</td><td width='30%'>" + dataList[x]["locName"] + "</td><td width='50%'>" + dataList[x]["buildSize"] + "</td></tr>";
    }
    $(container).empty().html(html);
}


/**
 *
 * @param locId
 * @param dataList
 * @param container
 */
function showChart(locId, container) {
    var locObject = findById(locId);
    var locDesc = getLocDesc(locId);
    $("#locDesc").html(locDesc + "<br>");
    //根据loc_level判断调用的接口  取数据统计
    var locLevel = locObject["locLevel"];
    console.log("locLevel-----------" + locLevel);
    var dataList = (!locObject["locLevel"]) ? getThirteenFivePlanStat() : getThirteenFivePlanStatCity(locId);
    var cities = getThirteenFivePlanCities(dataList);
    var cityValues = getThirteenFivePlanCitiesValues(dataList);
    //loc_level 为0时取全省三城市的数据统
    //loc_level为1时取该市所属县的统计分析
    var myChart = echarts.init(document.getElementById(container));
    var option = {
        title: {
            text: "十三五规划统计",
            x: 'center',
            textStyle: {
                color: "white"
            },
            subtext: locObject["locName"],
            subtextStyle: {
                color: "white"
            }
        },
        // color: pieColor,
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}万亩 ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: cities
        },
        calculable: true,
        series: [
            {
                name: '规划面积',
                type: 'pie',
                radius: '55%',
                center: ['62%', '60%'],
                data: cityValues
            }
        ],
        itemStyle: {
            emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    };
    myChart.setOption(option);
}


/**
 * 初始化显示地图统计面板信息
 */
function initShowInfo() {
    console.log("初始化显示地图统计面板信息----initShowInfo");
    var locId = $("#selectLocation").val();
    fillInfoPanelByLocId(locId);
    $("#map_info_panel").show("fast");
}

////////////////////********以上属于行政区划的信息*********/////////////////////////
/////////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////
////////////////////********以下属于区块的信息*********/////////////////////////

// /**
//  *初始化选择区块信息
//  */
// function initAreaSelect() {
//     var areas = findAllAreas4Sel();
//     var html = "";
//     // $("#selectArea").html(html);
//     for (var x = 0; x < areas.length; x++) {
//         html += "<option value =" + areas[x]['id'] + "  >" + areas[x]['areaNo'] + areas[x]['areaName'] + "</option>";
//     }
//     $("#selectArea").append(html);
//
//
// }
/**
 *
 * @returns {*} 根据区块的id获取区块十三五规划的描述信息
 * @param areaId 区块id信息
 */
function getArea135PlanDesc(areaId) {
    $.ajaxSettings.async = false;
    var url = "/stThirteenFivePlan/findByAreaId/" + areaId;
    var areaDesc = null;
    $.getJSON(url, function (data) {
        areaDesc = data["planDesc"];
    });
    return areaDesc;
}

/**
 *
 * @param areaId 区块id
 * @param container 将区块的描述放置在指定的容器中
 */
function showAreadesc(areaId, container) {
    var areaDesc = getArea135PlanDesc(areaId);
    if(areaDesc){
        $(container).html(areaDesc);
    }else {
        $(container).html("无信息！");
    }

}

/**
 *
 * @param areaId
 * @param container 并且放置在指定的容器中
 */
function showAreaDataList(areaId, container) {
    var landType = ["农业用地", "建设用地", "生态用地"];
    var landTypeParams = ["nyBuildSize", "jsBuildSize", "stBuildSize"];
    var areaThirteenFivePlan = findAreaThirteenFivePlan(areaId);
    if (areaThirteenFivePlan) {
        var html = "";
        for (var x = 0; x < landType.length; x++) {
            html += "<tr><td width='20%'>" + parseInt(x + 1) + "</td><td width='30%'>" + landType[x] + "</td><td width='50%'>" + areaThirteenFivePlan[landTypeParams[x]] + "</td></tr>";
        }
        $(container).empty().html(html);
    } else {
        $(container).empty().html("");
    }

}


/**
 *
 * @param areaId
 * @returns {*}
 */
function findAreaThirteenFivePlan(areaId) {
    var url = "/stThirteenFivePlan/findByAreaId/" + areaId;
    $.ajaxSettings.async = false;
    var stat = null;
    $.getJSON(url, function (data) {
        stat = data;
    });
    return stat;
}

/**
 *
 * @param areaId区块id
 * 按照用地类型统计分析区块
 */
function showAreasChart(areaId) {
    var stat = findAreaThirteenFivePlan(areaId);
    var area = findObjById("area", areaId);
    var types = ["农业用地", "建设用地", "生态用地"];
    var buildValues = [];//统计图表的数据
    if (stat) {
        buildValues = [{
            name: types[0], value: stat["nyBuildSize"] ? stat["nyBuildSize"] : 0
        }, {
            name: types[1], value: stat["jsBuildSize"] ? stat["jsBuildSize"] : 0
        }, {
            name: types[2], value: stat["stBuildSize"] ? stat["stBuildSize"] : 0
        }];
    }

    var myChart = echarts.init(document.getElementById('stat_chart'));
    var option = {

        title: {
            text: "用地类型统计",
            x: 'center',
            textStyle: {
                color: "white"
            },
            subtext: area["areaNo"] + area["areaName"],
            subtextStyle: {
                color: "white"
            }
        },
        color: pieColor,
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}万亩 ({d}%)"
        },
        legend: {
            type: "scroll",
            show: true,
            orient: 'vertical',
            x: 'left',
            data: types
        },
        calculable: true,
        series: [
            {
                name: '规划面积',
                type: 'pie',
                radius: '55%',
                center: ['62%', '60%'],
                data: buildValues
            }
        ]
    };
    myChart.setOption(option);
}

////////////////////********以上属于区块的信息*********//////////////////////////
//////////////////////////////////////////////////////////////////////////////


/**
 *
 * @param areaId
 */
function locateLoc(locId) {
    var query = bmisProvLayer.createQuery();
    query.where = "id=" + locId;
    bmisProvLayer.queryFeatures(query).then(
        function (result) {
            var feature = result.features[0];
            highlightFeature(bmisProvLayer, feature);
            locateFeatureWithZoom(view, feature, 9);
        });
}


/**
 *
 * @param areaId
 */
function locateArea(areaId) {
    var query = buildAreaLayer.createQuery();
    query.where = "area_id=" + areaId;
    buildAreaLayer.queryFeatures(query).then(
        function (result) {
            var feature = result.features[0];
            highlightFeature(buildAreaLayer, feature);
            locateFeatureWithZoom(view, feature, 10);
        });

}