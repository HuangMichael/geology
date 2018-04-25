var baseMaps = getBaseMaps();
var userPosition = getLoginUserPosition();
var view;
var bmisProvLayer;
var projects;
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


    var coastLineSym = new PolygonSymbol3D({
        symbolLayers: [
            new ExtrudeSymbol3DLayer({
                material: {
                    color: "#12fcf3"
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
        defaultLabel: "自然保护试验区",
        field: "自然保护试验区",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });


    bmisProvLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "js", "江苏省界", "white", "{loc_name}", null, true);
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


    projects = createPointCallOutLabelWithPopup(FeatureLayer, "projectBuild", "project_name", pointsRenderer, "项目信息", null);

    var bufferAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, bufferRenderer, "natureReserveBuffer", "自然核心缓冲区", "yellow", "{buffer_name}", null, false);
    var reserveAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, reserveRenderer, "natureReserveArea", "自然保护核心区", "yellow", "{area_name}", null, false);
    var experimentLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, experimentRenderer, "natureReserveExperiment", "自然保护试验区", "yellow", "{experiment_name}", null, false);
    var coastLineLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "coastLine", "海岸线", "#fcaa09", "{line_name}", getCoastLinePopup(), true);
    var beachLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "beach", "滩涂", "#23fcd0", "{beach_name}", getBeachPopup(), true);
    var sandLandLayer = createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, "sandLand", "沿海沙洲", "#23fcd0", "辐射沙脊群", getSandPopup(), true);

    var borderGroupLayer = createGroupLayer(GroupLayer, "江苏行政区划", [bmisProvLayer]);
    var projectGroupLayer = createGroupLayer(GroupLayer, "项目信息", [projects]);
    var natureGroupLayer = createGroupLayer(GroupLayer, "自然保护区主题", [experimentLayer, bufferAreaLayer, reserveAreaLayer], false);
    var seaGroupLayer = createGroupLayer(GroupLayer, "其他主题", [beachLayer, coastLineLayer, sandLandLayer]);


    var map = new Map({
        basemap: baseMaps[0],
        ground: "world-elevation",
        center: userPosition
    });


    map.layers.add(seaGroupLayer);
    map.layers.add(natureGroupLayer);
    map.layers.add(borderGroupLayer);
    map.layers.add(projectGroupLayer);

    view = new SceneView({
        container: "viewDiv",
        map: map
    });
    var homeBtn = new Home({
        view: view
    });
    // Add the home button to the top left corner of the view
    view.ui.add(homeBtn, "top-left");


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
                layer: projects,
                title: "工程类型"
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
            // {
            //     layer: projectWkLayer,
            //     title: "匡围工程"
            // }, {
            //     layer: projectDlLayer,
            //     title: "道路工程"
            // },
            // {
            //     layer: projectSlLayer,
            //     title: "水利工程"
            // },
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
            initProjectSelect("#selectProject");
            initShowInfo();

            $("#closed").click(function () {
                $("#map_info_panel").hide();
            });

            $("#selectLocation").on("change", function () {
                $("#stat_charts a").html("<i class=\"icon-exchange\" style=\"color: #fff\"></i>");
                var locId = $(this).val();
                //根据位置id查询在建或已建的项目，更新项目下拉列表
                initProjectSelectWithLocId("#selectProject", locId);
                locateLoc(locId);
                fillInfoPanelByLocId(locId);
                $(".panel-side").hide();
                $("#map_info_panel").show("fast");
            });


            $("#selectProject").on("change", function () {
                // $("#map_info_panel").html("");
                var projectId = $(this).val();
                fillInfoPanelByProjectId(projectId);
                locateProject(projectId);
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
    var listNode = document.getElementById("nyc_graphics");
    view.whenLayerView(projects).then(function (lyrView) {
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
        var query = projects.createQuery();
        query.where = "objectid=" + oid;

        projects.queryFeatures(query).then(
            function (result) {
                var feature = result.features[0];
                highlightFeature(projects, feature);
                view.goTo({
                        target: feature,
                        tilt: 45,
                        duration: 2000,
                        easing: "in-out-expo",
                        zoom: 12,
                        spatialReference: 32649
                    },
                    {
                        animate: true
                    }
                );
            });
    }
})
;

/**
 * 根据位置id查询项目总体布局描述信息
 */
function getLayoutDescByLocId(locId) {

    var locationDesc = "";
    var url = "projectTotalLayout/layoutDesc/" + locId;
    $.ajaxSettings.async = false;

    $.getJSON(url, function (data) {
        locationDesc = data["locationDesc"];

    })
    return locationDesc;

}


/**
 *
 * @returns {*}
 * 总体规划统计数据
 */
function getProvProject(locLevel, locId) {
    $.ajaxSettings.async = false;
    var url = "/project/findProjectStat";
    var dataList = null;

    var params = {
        locLevel: locLevel,
        locId: locId


    }
    $.post(url, params, function (data) {
        dataList = data;
    });
    return dataList;
}


/**
 *
 * @returns {*}
 * 省项目总体布局统计数据
 */
function getProjectByEne(locLevel, locId) {
    $.ajaxSettings.async = false;
    var url = "/project/findProjectEneStat";
    var dataList = null;
    var params = {
        locLevel: locLevel,
        locId: locId

    }
    $.post(url, params, function (data) {
        dataList = data;
    });
    return dataList;
}


/**
 * 初始化显示地图统计面板信息
 */
function initShowInfo() {
    var locId = $("#selectLocation").val();
    fillInfoPanelByLocId(locId);
    $("#map_info_panel").show("fast");
}


/**
 *
 * @param locId位置id
 * 根据位置id填充信息面板
 */
function fillInfoPanelByLocId(locId) {

    var locObject = findObjById("location", locId);

    var locLevel = locObject["locLevel"];
    var dataList = getProvProject(locLevel, locId);
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
 * @param locId
 */
function showWkdesc(locId, container) {
    var locDesc = getLayoutDescByLocId(locId);
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
    html += "<tr><td width='50%'>行政区划</td> <td width='50%'>项目数量(单位：个)</td> </tr>";
    html += '<tbody id="stat_tablelistBody">';
    $("#stat_datalist").html(html);
    $(container).html("");
    for (var x in  dataList) {
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
    var cities = getStatCities(dataList);
    var cityValues = getStatCitiesValues(dataList);
    var myChart = echarts.init(document.getElementById(container));
    var option = {
        title: {
            x: 'center',
            text: "项目数量统计",
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
            formatter: "{a} <br/>{b} : {c}个({d}%)"
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
 * @param locId
 * @param dataList
 * @param container
 */
function showEneChart(locObj, container, dataList) {
    var eneTypes = getStatEneTypes(dataList);
    var eneTypesValues = getStatEneTypesValues(dataList);
    var myChart = echarts.init(document.getElementById(container));
    var option = {
        title: {
            text: "工程类型统计",
            x: 'center',
            textStyle: {
                color: "white"
            },
            subtext: locObj["locName"],
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
                name: '项目数量',
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
function getStatCities(dataList) {
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
function getStatEneTypes(dataList) {
    var eneTypes = [];
    for (var x in dataList) {
        eneTypes.push(x);
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
    }
    return values;
}


/**
 *
 * @param areaId
 * @param container 并且放置在指定的容器中
 */
function showProjectGallery(projectId, container) {
    var url = "/projectTotalLayout/showGisGallery";
    $(container).load(url, function () {
        var media = queryAreaPicsByAreaId(projectId);
        createGalleryById(media, "#projectGallery");
    });


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
        projectDesc = data["projectDesc"];
    });
    return projectDesc;
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
 * @param areaId 区块id
 * @param container 将区块的描述放置在指定的容器中
 */
function showProjectDesc(projectId, container) {
    var projectDesc = getProjectDesc(projectId);
    $(container).html(projectDesc);
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
 * @param projectId
 */
function locateProject(projectId) {
    var query = projects.createQuery();
    query.where = "id=" + projectId;
    projects.queryFeatures(query).then(
        function (result) {
            var feature = result.features[0];
            highlightFeature(projects, feature);
            locateFeatureWithZoom(view, feature, 11);
        });

}
