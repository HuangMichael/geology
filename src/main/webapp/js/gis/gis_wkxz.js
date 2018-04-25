var baseMaps = getBaseMaps();
var userPosition = getLoginUserPosition();
var areaPopupTemplate;
var view;
var bmisProvLayer;
var buildAreaLayer;


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

    var experimentAreaSym = new PolygonSymbol3D({
        symbolLayers: [
            new ExtrudeSymbol3DLayer({
                material: {
                    color: "#8198d9"
                }
            })
        ]
    });


    var buildAreaSym = new PolygonSymbol3D({
        symbolLayers: [
            new ExtrudeSymbol3DLayer({
                material: {
                    color: "#12911f"
                }
            })
        ]
    });

    var areaBorderSym = new PolygonSymbol3D({
        symbolLayers: [
            new ExtrudeSymbol3DLayer({
                material: {
                    color: "#1fff0f"
                }
            })
        ]
    });

    var areaBorderRenderer = new UniqueValueRenderer({
        defaultSymbol: areaBorderSym,
        defaultLabel: "围垦区块边界",
        field: "围垦区块边界",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });

    var buildAreaRenderer = new UniqueValueRenderer({
        defaultSymbol: buildAreaSym,
        defaultLabel: "围垦区块",
        field: "围垦区块",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
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

    var buildAreaLabel,
        bmisCountyLayer,
        planLayer,
        areaBorderLayer,
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
            // bmisCountyLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "county", "江苏县界", "white", "{loc_name}", null, true);
            buildAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, buildAreaRenderer, "totalPlan", "围垦现状", "", "", areaPopupTemplate, true);
            buildAreaLabel = createCalloutLabelLayer(FeatureLayer, "area", "区块标注", "{area_no}{area_name}");
            areaBorderLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, areaBorderRenderer, "areaBorder", "围垦边界", "", "", null, true);
            planLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, plan135Renderer, "thirteenFivePlan", "十三五规划", "", "", null, false);
            bufferAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, bufferRenderer, "natureReserveBuffer", "自然保护缓冲区", "yellow", "{buffer_name}", null, false);
            reserveAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, reserveRenderer, "natureReserveArea", "自然保护核心区", "yellow", "{area_name}", null, false);
            experimentLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, experimentRenderer, "natureReserveExperiment", "自然保护试验区", "yellow", "{experiment_name}", null, false);
            coastLineLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "coastLine", "海岸线", "#fcaa09", "{line_name}", getCoastLinePopup(), true);
            beachLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "beach", "滩涂", "#23fcd0", "{beach_name}", getBeachPopup(), true);
            sandLandLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "sandLand", "沿海沙洲", "#23fcd0", "辐射沙脊群", getSandPopup(), true);
            borderGroupLayer = createGroupLayer(GroupLayer, "江苏行政区划", [bmisProvLayer]);
            natureGroupLayer = createGroupLayer(GroupLayer, "自然保护区主题", [experimentLayer, bufferAreaLayer, reserveAreaLayer], false);
            wkGroupLayer = createGroupLayer(GroupLayer, "围垦主题", [planLayer, buildAreaLayer, buildAreaLabel, areaBorderLayer]);
            seaGroupLayer = createGroupLayer(GroupLayer, "其他主题", [beachLayer, coastLineLayer, sandLandLayer]);
            //将图层添加在地图上时为倒序添加
            map.layers.add(seaGroupLayer);
            map.layers.add(natureGroupLayer);
            map.layers.add(borderGroupLayer);
            map.layers.add(wkGroupLayer);


            var legend = new Legend({
                view: view,
                layerInfos: [
                    {
                        layer: coastLineLayer,
                        title: "海岸线"
                    },

                    {
                        layer: beachLayer,
                        title: "滩涂"
                    },
                    {
                        layer: sandLandLayer,
                        title: "沿海沙洲"
                    },
                    {
                        layer: buildAreaLayer,
                        title: ""
                    },
                    {
                        layer: areaBorderLayer,
                        title: ""
                    },

                    {
                        layer: planLayer,
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
                    }
                ]
            });

            $("#closed").click(function () {
                $("#map_info_panel").hide();
            });

            //行政区划和区块做成联动变化
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

            // Add widget to the bottom right corner of the view
            view.ui.add(legend, {
                position: "bottom-left",
                index: 2
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
                index: 1
            });
            view.goTo(initViewPoint(userPosition));
            initLocationSelect("#selectLocation");
            //初始化加载区块下拉列表，显示所有的区块
            initAreaSelect("#selectArea");
            initShowInfo();
        })
        .otherwise(function (err) {
            console.error("SceneView rejected:", err);
        });
});


/**
 *
 * @param locId行政区划id
 * @returns {*}
 * 根据行政区划的id查询该位置的围垦现状描述
 */
function getLocDesc(locId) {
    $.ajaxSettings.async = false;
    var url = "/areaBuilding/findDescByLocationId/" + locId;
    var planDesc = null;
    $.getJSON(url, function (data) {
        planDesc = data[0]["description"];
    });
    return planDesc;
}

/**
 *
 * @returns {*}
 * 围垦现状统计数据
 */
function getBuildingProvStat() {
    $.ajaxSettings.async = false;
    var url = "/areaBuilding/buildingProv";
    var dataList = null;
    $.getJSON(url, function (data) {
        dataList = data;
    });
    return dataList;
}

/**
 *
 * @returns {*}
 * 围垦现状统计数据
 */
function getBuildingCityStat(parentId) {
    $.ajaxSettings.async = false;
    var url = "/areaBuilding/buildingCity/" + parentId;
    var dataList = null;
    $.getJSON(url, function (data) {
        dataList = data;
    });
    return dataList;
}


/**
 *
 * @param dataList
 * @returns {Array}
 */
function getCities(dataList) {
    var cities = [];
    var city;
    for (var x = 0; x < dataList.length; x++) {
        city = dataList[x]["locName"];
        if (cities.containObj(city)) {
            cities[x] = city;
        }
    }
    console.log("cities", JSON.stringify(cities));
    return cities;
}


/**
 *
 * @param dataList
 * @returns {Array}
 */
function getCitiesValues(dataList) {
    var citiesValues = [];
    var value;
    var name;
    for (var x = 0; x < dataList.length; x++) {
        name = dataList[x]["locName"];
        value = dataList[x]["buildSize"];
        citiesValues[x] = {value: value, name: name}

    }
    console.log("citiesValues", JSON.stringify(citiesValues));
    return citiesValues;
}

/**
 *
 * @param locId位置id
 * 根据位置id填充信息面板
 */
function fillInfoPanelByLocId(locId) {
    var locObject = findObjById("location", locId);
    var dataList = (!locObject["locLevel"]) ? getBuildingProvStat() : getBuildingCityStat(locId);
    //填充围垦描述内容
    //根据位置id查询出围垦描述 将围垦描述显示在内容中
    showWkdesc(locId, "#stat_desc");
    showDataTable("#stat_datalist", dataList);
    showChart(locId, "stat_chart", dataList);
}


/**
 *
 * @param locId位置id
 * 根据位置id填充信息面板
 */
function fillInfoPanelByAreaId(areaId) {
    //填充围垦描述内容
    //根据位置id查询出围垦描述 将围垦描述显示在内容中
    showAreadesc(areaId, "#stat_desc");
    showAreaGallery(areaId, "#stat_datalist");
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
function showDataTable(container, dataList) {
    var html = '<table id="stat_tablelist">';
    html += "<tr><td width='20%'>序号</td><td width='30%'>行政区划</td> <td width='50%'>已围面积(单位：万亩)</td> </tr>";
    html += '<tbody id="stat_tablelistBody">';
    $("#stat_datalist").html(html);
    $(container).html("");
    for (var x = 0; x < dataList.length; x++) {
        html += "<tr><td width='20%'>" + parseInt(x + 1) + "</td><td width='30%'>" + dataList[x]["locName"] + "</td><td width='50%'>" + dataList[x]["buildSize"] + "</td></tr>";
    }
    html += '</tbody>';
    html += '</table>';
    $(container).html(html);
}


/**
 *
 * @param locId
 * @param dataList
 * @param container
 */
function showChart(locId, container, dataList) {
    var locObject = findObjById("location", locId);
    var cities = getCities(dataList);
    var cityValues = getCitiesValues(dataList);
    //loc_level 为0时取全省三城市的数据统
    //loc_level为1时取该市所属县的统计分析
    var myChart = echarts.init(document.getElementById(container));
    var option = {
        title: {
            text: "已围面积统计分析",
            subtext: locObject["locName"],
            subtextStyle: {

                color: "white"
            },
            x: 'center',
            textStyle: {
                color: "white"
            },
        },
        // color: pieColor,
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}万亩 ({d}%)"
        },
        legend: {
            show: true,
            data: cities
        },
        calculable: true,
        series: [
            {
                name: '已围面积',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
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
    fillInfoPanelByLocId(15);
    $("#map_info_panel").show("fast");
}

////////////////////********以上属于行政区划的信息*********/////////////////////////
/////////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////
////////////////////********以下属于区块的信息*********/////////////////////////


/**
 *fillInfoPanelByLocId(15);
 * @returns {*} 根据区块的id获取区块的描述信息
 * @param areaId 区块id信息
 */
function getAreaDesc(areaId) {
    $.ajaxSettings.async = false;
    var url = "/area/findById/" + areaId;
    var areaDesc = null;
    $.getJSON(url, function (data) {
        areaDesc = data["areaDesc"];
    });
    return areaDesc;
}


/**
 *
 * @param areaId 区块id
 * @param container 将区块的描述放置在指定的容器中
 */
function showAreadesc(areaId, container) {
    var areaDesc = getAreaDesc(areaId);
    $(container).html(areaDesc);
}


/**
 *
 * @param areaId 根据区块的id获取到区块的相册信息
 */

function queryAreaPicsByAreaId(areaId) {
    var media = [];
    $.ajaxSettings.async = false;
    var param = {
        areaId: areaId,
        mediaTypeId: 2
    }
    var url = "/areaMedia/findPicsByAreaIdAndMediaTypeId";
    $.post(url, param, function (data) {
        media = data;
    });
    // console.log("media--------------" + JSON.stringify(media));
    return media;
//根据区块的id查询区块多媒体中的相册 显示缩略图

}


/**
 *
 * @param areaId
 * @param container 并且放置在指定的容器中
 */
function showAreaGallery(areaId, container) {
    var url = "/area/showGisGallery";
    $(container).load(url, function () {
        var media = queryAreaPicsByAreaId(areaId);
        createGalleryById(media, "#areaGallery");
    });


}


/**
 *
 * @param areaId区块id
 * 按照用地类型统计分析区块
 */
function showAreasChart(areaId) {

    var area = findObjById("area", areaId);
    var url = "/areaBuilding/findStatByAreaId/" + areaId;
    $.ajaxSettings.async = false;
    var stat = null;
    $.getJSON(url, function (data) {
        stat = data;
    });
    var types = ["农业用地", "建设用地", "生态用地"];
    var buildValues = [{
        name: types[0], value: stat["nyBuildSize"] ? stat["nyBuildSize"] : 0
    }, {
        name: types[1], value: stat["jsBuildSize"] ? stat["jsBuildSize"] : 0
    }, {
        name: types[2], value: stat["stBuildSize"] ? stat["stBuildSize"] : 0
    }
    ];
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
            formatter: "{a} <br/>{b} : {c}万亩({d}%)"
        },
        legend: {
            show: false,
            orient: 'vertical',
            x: 'center',
            data: types
        },
        calculable: true,
        series: [
            {
                name: '已围面积',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: buildValues
            }
        ]
    };
    myChart.setOption(option);
}


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


////////////////////********以上属于区块的信息*********//////////////////////////
//////////////////////////////////////////////////////////////////////////////


