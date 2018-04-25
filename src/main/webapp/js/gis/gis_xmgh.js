var baseMaps = getBaseMaps();
var userPosition = getLoginUserPosition();
var view;
var projectPointLayer, bmisProvLayer;
require(layoutArray, function (Map, SceneView, GroupLayer, FeatureLayer, UniqueValueRenderer, ExtrudeSymbol3DLayer, PolygonSymbol3D, Home, Search, BasemapToggle, Legend, geometryEngine, LabelClass, TextSymbol3DLayer, LabelSymbol3D, LayerList, on) {
    var listNode = document.getElementById("nyc_graphics");


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


    // var bmisCityLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "city", "江苏市界", "white", "{loc_name}", null, true);
    // var bmisCountyLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "county", "江苏县界", "white", "{loc_name}", null, true);


    var pointsRenderer = {
        type: "unique-value", // autocasts as new UniqueValueRenderer()
        field: "engineering_type_id",
        valueExpressionTitle: "工程类型",
        uniqueValueInfos: [{
            value: "1",
            symbol: getUniqueValueSymbol("/img/project/kw.png", "red"),
            label: "匡围工程"
        }, {
            value: "3",
            symbol: getUniqueValueSymbol("/img/project/dl.png", "green"),
            label: "道路工程"
        }, {
            value: "2",
            symbol: getUniqueValueSymbol("/img/project/sl.png", "blue"),
            label: "水利工程"
        }]
    };

    bmisProvLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "js", "江苏省界", "white", "{loc_name}", null, true);
    projectPointLayer = createPointCallOutLabelWithPopup(FeatureLayer, "projectPlan", "project_name", pointsRenderer, "项目规划信息", null);

    var bufferAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, bufferRenderer, "natureReserveBuffer", "自然保护缓冲区", "yellow", "{buffer_name}", null, false);
    var reserveAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, reserveRenderer, "natureReserveArea", "自然保护核心区", "yellow", "{area_name}", null, false);
    var experimentLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, experimentRenderer, "natureReserveExperiment", "自然保护试验区", "yellow", "{experiment_name}", null, false);
    var coastLineLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "coastLine", "海岸线", "#fcaa09", "{line_name}", getCoastLinePopup(), true);
    var beachLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "beach", "滩涂", "#23fcd0", "{beach_name}", getBeachPopup(), true);
    var sandLandLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "sandLand", "沿海沙洲", "#23fcd0", "辐射沙脊群", getSandPopup(), true);

    var borderGroupLayer = createGroupLayer(GroupLayer, "江苏行政区划", [bmisProvLayer]);
    var projectGroupLayer = createGroupLayer(GroupLayer, "项目规划信息", [projectPointLayer]);
    var natureGroupLayer = createGroupLayer(GroupLayer, "自然保护区主题", [experimentLayer, bufferAreaLayer, reserveAreaLayer], false);
    var seaGroupLayer = createGroupLayer(GroupLayer, "其他主题", [beachLayer, coastLineLayer, sandLandLayer]);


    var map = new Map({
        basemap: baseMaps[0],
        ground: "world-elevation",
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


    map.layers.add(seaGroupLayer);
    map.layers.add(natureGroupLayer);
    map.layers.add(borderGroupLayer);
    map.layers.add(projectGroupLayer);

    // 1 - Create the widget
    var toggle = new BasemapToggle({
        view: view, // view that provides access to the map's 'topo' basemap
        nextBasemap: baseMaps[1]// allows for toggling to the 'hybrid' basemap
    });
    // Add widget to the top right corner of the view
    view.ui.add(toggle, {
        position: "top-left",
        index: 5
    });


    var legend = new Legend({
        view: view,
        layerInfos: [
            {
                layer: projectPointLayer,
                title: "项目位置"
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


    var layerList = new LayerList({
        view: view
    });
    view.ui.add(layerList, {
        position: "bottom-left",
        index: 2
    });


    view
        .then(function () {

            view.goTo(initViewPoint(userPosition));
            initLocationSelect("#selectLocation");
            initPlanedProjectSelect("#selectProject");
            initShowInfo();

            $("#closed").click(function () {
                $("#map_info_panel").hide();
            });

            $("#selectLocation").on("change", function () {
                $("#stat_charts a").html("<i class=\"icon-exchange\" style=\"color: #fff\"></i>");
                var locId = $(this).val();
                //根据位置id查询规划中项目，更新项目下拉列表
                initPlanedProjectSelectWithLocId("#selectProject", locId);
                locateLoc(locId);
                fillInfoPanelByLocId(locId);
                $(".panel-side").hide();
                $("#map_info_panel").show("fast");

            });


            $("#selectProject").on("change", function () {
                var projectId = $(this).val();
                locateProject(projectId);
                fillInfoPanelByProjectId(projectId);
                $(".panel-side").hide();
                $("#map_info_panel").show("fast");
                $("#stat_charts a").html("");
            });


            var type = "a";
            $("#toggleChart").on("click", function () {
                    //获取当前的统计方式   获取当前的选择的  进行切换
                    if (type == "a") {
                        var locId = $("#selectLocation").val();
                        var locObj = findObjById("location", locId);
                        var locLevel = locObj['locLevel'];
                        var dataList = getProjectByEne(locLevel, locId);
                        showEneDataTable("#stat_datalist", dataList);
                        showEneChart(locObj, "stat_chart", dataList);
                        type = "b";
                    } else if (type == "b") {
                        var locId = $("#selectLocation").val();
                        fillInfoPanelByLocId(locId);
                        type = "a";
                    }
                }
            );
        })
        .otherwise(function (err) {
            console.error("SceneView rejected:", err);
        });


    var graphics;
    view.whenLayerView(projectPointLayer).then(function (lyrView) {
        lyrView.watch("updating", function (val) {
            if (!val) {
                lyrView.queryFeatures().then(function (results) {
                    graphics = results;
                    var fragment = document.createDocumentFragment();
                    results.forEach(function (result, index) {
                        var attributes = result.attributes;
                        var name = attributes['project_name'];
                        var li = document.createElement("li");
                        li.classList.add("panel-result");
                        li.tabIndex = 0;
                        li.setAttribute("data-result-id", index);
                        li.setAttribute("objectid", attributes["objectid"]);
                        li.textContent = name;
                        var eneType = attributes['engineering_type_id'];
                        var innerContent = li.innerHTML;
                        li.innerHTML = "<img src='/img/project/" + eneType + ".png' width='25px'>" + innerContent;
                        fragment.appendChild(li);
                    });
                    listNode.innerHTML = "";
                    listNode.appendChild(fragment);
                });
            }
            goPage(1, 10);
        });
    });

    listNode.addEventListener("click", onListClickHandler);


    function onListClickHandler(event) {
        var target = event.target;
        var oid = target.getAttribute("objectid");
        //通过oid查询对应的要素  设置居中  高光
        var query = projectPointLayer.createQuery();
        query.where = "objectid=" + oid;
        projectPointLayer.queryFeatures(query).then(
            function (result) {
                var feature = result.features[0];
                highlightFeature(projectPointLayer, feature);
                locateFeature(view, feature);
                $("#panelSide").hide("fast");
            });
    }


});


/**
 * 根据位置id查询项目总体布局描述信息
 */
function getPlanDescByLocId(locId) {

    var locationDesc = "";
    var url = "/projectPlan/planDesc/" + locId;
    $.ajaxSettings.async = false;

    $.getJSON(url, function (data) {
        locationDesc = data["locationDesc"];
    });
    console.log("locationDesc-------------" + locationDesc);
    return locationDesc;
}

/**
 *
 * @param cityName
 * @returns {*}
 */
function getProjectProvPlanStat(locLevel, locId) {
    var url = "projectPlan/findProjectPlanStat";
    var params = {
        locLevel: locLevel,
        locId: locId
    }
    var dataList = findListByUrlWithParam(url, params);
    if (!dataList) {
        dataList = {"没有数据": 0};
    }
    console.log("dataList----------" + JSON.stringify(dataList));
    return dataList;
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

    var locObject = findById(locId);
    //根据loc_level判断调用的接口  取数据统计
    var locLevel = locObject["locLevel"];
    var dataList = getProjectProvPlanStat(locLevel, locId);

    //填充围垦描述内容
    //根据位置id查询出围垦描述 将围垦描述显示在内容中
    showWkdesc(locId, "#stat_desc");
    showDataTable("#stat_datalist", dataList);
    showChart(locObject, "stat_chart", dataList);
}


/**
 *
 * @param locId位置id
 * 根据位置id填充信息面板
 */
function fillInfoPanelByProjectId(projectId) {
    //填充围垦描述内容
    //根据位置id查询出围垦描述 将围垦描述显示在内容中
    showProjectDesc(projectId, "#stat_desc");
    showProjectGallery(projectId, "#stat_datalist");
    showProjectChart(projectId, "stat_chart");
}


/**
 *
 * @param areaId
 * @param container 并且放置在指定的容器中
 */
function showProjectGallery(projectId, container) {

    $("#stat_datalist").html("");
    var url = "/project/showGisGallery";
    $(container).load(url, function () {
        var media = queryProjectPicsByProjectId(projectId);
        createGalleryById(media, "#projectGallery");
    });
}


/**
 *
 * @param areaId 根据项目的id获取到区块的相册信息
 */

function queryProjectPicsByProjectId(projectId) {
    var media = [];
    $.ajaxSettings.async = false;
    var param = {
        projectId: projectId,
        mediaTypeId: 2
    }
    var url = "/projectMedia/findPicsByProjectIdAndMediaTypeId";
    $.post(url, param, function (data) {
        media = data;
    });
    return media;
//根据区块的id查询区块多媒体中的相册 显示缩略图

}


/**
 *
 * @param areaId 区块id
 * @param container 将区块的描述放置在指定的容器中
 */
function showProjectDesc(projectId, container) {
    var projectDesc = getProjectDesc(projectId);
    $(container).html(projectDesc);
}


/**
 *
 * @returns {*} 根据区块的id获取区块的描述信息
 * @param areaId 区块id信息
 */
function getProjectDesc(projectId) {
    $.ajaxSettings.async = false;
    var url = "/project/findById/" + projectId;
    var projectDesc = null;
    $.getJSON(url, function (data) {
        projectDesc = data["mainPurpose"];
    });
    return projectDesc;
}


/**
 *
 * @param locId
 */
function showWkdesc(locId, container) {
    var locDesc = getPlanDescByLocId(locId);
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
    html += "<tr><td width='50%'>行政区划</td> <td width='50%'>项目数量(单位：个)</td></tr>";
    html += '<tbody id="stat_tablelistBody">';
    $("#stat_datalist").html(html);
    $(container).html("");
    for (var x in dataList) {
        html += "<tr><td width='50%'>" + x + "</td><td width='50%'>" + dataList[x] + "</td></tr>";
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
function showEneDataTable(container, dataList) {
    var html = '<table id="stat_tablelist">';
    html += "<tr><td width='50%'>工程类型</td> <td width='50%'>项目数量(单位：个)</td> </tr>";
    html += '<tbody id="stat_tablelistBody">';
    $("#stat_datalist").html(html);
    $(container).html("");
    for (var key in dataList) {
        html += "<tr><td width='50%'>" + key + "</td><td width='50%'>" + dataList[key] + "</td></tr>";
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
function showChart(locObject, container, dataList) {


    //根据loc_level判断调用的接口  取数据统计
    var cities = getCities(dataList);
    var cityValues = getCitiesValues(dataList);
    //loc_level 为0时取全省三城市的数据统
    //loc_level为1时取该市所属县的统计分析
    var myChart = echarts.init(document.getElementById(container));
    var option = {
        title: {
            text: "规划项目统计",
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
            formatter: "{a} <br/>{b} : {c}个 ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: cities
        },
        calculable: true,
        series: [
            {
                name: '项目数量',
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
 *
 * @param dataList
 * @returns {Array}
 */
function getCities(dataList) {
    var cities = [];
    for (var x in dataList) {
        cities.push(x);
    }
    return cities;
}


/**
 *
 * @param dataList
 * @returns {Array}
 */
function getCitiesValues(dataList) {
    var citiesValues = [];
    for (var x in dataList) {
        citiesValues.push({
            name: x,
            value: dataList[x]


        });
    }
    return citiesValues;
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

/**
 *
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
 * @param areaId
 * @param container 并且放置在指定的容器中
 */
function showAreaDataList(areaId, container) {
    var landType = ["农业用地", "建设用地", "生态用地"];
    var landTypeParams = ["nyBuildSize", "jsBuildSize", "stBuildSize"];
    var areaTotalPlan = findAreaTotalPlan(areaId);
    var html = "";
    for (var x = 0; x < landType.length; x++) {
        html += "<tr><td width='20%'>" + parseInt(x + 1) + "</td><td width='30%'>" + landType[x] + "</td><td width='50%'>" + areaTotalPlan[landTypeParams[x]] + "</td></tr>";
    }
    $(container).empty().html(html);
}


/**
 *
 * @param areaId
 * @returns {*}
 */
function findAreaTotalPlan(areaId) {
    var url = "/totalPlan/findByAreaId/" + areaId;
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
    var stat = findAreaTotalPlan(areaId);
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
                center: ['50%', '60%'],
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
function locateProject(projectId) {
    var query = projectPointLayer.createQuery();
    query.where = "id=" + projectId;
    projectPointLayer.queryFeatures(query).then(
        function (result) {
            var feature = result.features[0];
            highlightFeature(projectPointLayer, feature);
            locateFeatureWithZoom(view, feature, 10);
        });

}


/**
 *
 * @param areaId区块id
 * 按照用地类型统计分析区块
 */
function showProjectChart(projectId) {

    var stat = findObjById("project", projectId);
    var types = ["已投金额", "未投金额"];
    var buildValues = [{
        name: types[0], value: stat["investedSum"] ? stat["investedSum"] : 0
    }, {
        name: types[1],
        value: stat["budget"] - stat["investedSum"] ? (stat["budget"] - stat["investedSum"]) : 0
    }
    ];
    var myChart = echarts.init(document.getElementById('stat_chart'));
    var option = {

        title: {
            text: "项目投资统计",
            x: 'center',
            textStyle: {
                color: "white"
            },
            subtext: stat["projectName"],
            subtextStyle: {
                color: "white"
            }
        },
        color: pieColor,
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}亿元({d}%)"
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
                name: '金额',
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
 * @returns {*}
 * 省项目总体布局统计数据
 */
function getProjectByEne(locLevel, locId) {
    $.ajaxSettings.async = false;
    var url = "/projectPlan/findProjectPlanEneStat";
    var dataList = null;
    var params = {
        locLevel: locLevel,
        locId: locId
    }
    $.post(url, params, function (data) {
        dataList = data;
    });

    if (dataList.length == 0) {
        dataList = [{"没有数据": 0}];
    }
    return dataList;
}


/**
 *
 * @param locId
 * @param dataList
 * @param container
 */
function showEneChart(locObject, container, dataList) {
    var eneTypes = getStatEneTypes(dataList);
    var eneTypesValues = getStatEneTypesValues(dataList);
    var myChart = echarts.init(document.getElementById(container));
    var option = {
        title: {
            text: "工程类型统计",
            x: 'center',
            textStyle: {
                color: "white"
            }, subtext: locObject["locName"],
            subtextStyle: {
                color: "white"
            }

        },
        // color: pieColor,
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: eneTypes
        },
        calculable: true,
        series: [
            {
                name: '规划项目数量',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: eneTypesValues
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
 *
 * @param dataList
 * @returns {Array}
 */
function getStatEneTypes(dataList) {
    var eneTypes = [];
    for (var x in dataList) {
        eneTypes.push(x);
    }

    if (!eneTypes) {

        eneTypes = ["没有数据"];
    }
    console.log("eneTypes", JSON.stringify(eneTypes));
    return eneTypes;
}

/**
 *
 * @param dataList
 * @param paramName
 * @param paramValue
 * @returns {Array}
 */
function getStatEneTypesValues(dataList) {

    var values = [];
    for (var i in dataList) {
        values.push({
            name: i,
            value: dataList[i]
        });

        if (!dataList) {
            values.push({
                name: "没有数据",
                value: 0
            });
        }
    }
    return values;
}