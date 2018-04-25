/**
 *
 * @returns {string}
 * 获取gis服务器地址
 */
function getGisServerIp() {
    $.ajaxSettings.async = false;
    var gis_server_url = null;
    var url = "/sysParams/findByParamName";
    var obj = {
        paramName: "GIS_SERVER_IP"
    }
    $.post(url, obj, function (data) {
        gis_server_url = data["paramValue"];
    });
    return gis_server_url;
}


/**
 *
 * @returns {string}
 * 获取gis服务器地址
 */
function getGisTileServerIp() {
    $.ajaxSettings.async = false;
    var gis_server_url = null;
    var url = "/sysParams/findByParamName";
    var obj = {
        paramName: "GIS_TILE_SERVER_IP"
    }
    $.post(url, obj, function (data) {
        gis_server_url = data["paramValue"];
    });
    return gis_server_url;
}



/**
 *
 * @returns {string}
 * 获取gis服务器地址
 */
function getTileBaseMaps() {
    $.ajaxSettings.async = false;
    var baseMaps = new Array();
    var url = "/sysParams/findByParamName";
    var obj = {
        paramName: "BASE_MAPS"
    }
    $.post(url, obj, function (data) {
        baseMaps = data["paramValue"].split(",");
    });
    return baseMaps;
}


var server_url = getGisServerIp(); //必须修改为ip
/**
 * 根据区块的id获取相册路径的列表
 * @param id
 * @return {string}
 */
function getAreaDescById(id) {
    $.ajaxSettings.async = false;
    var areaDesc = "";
    var url = "/area/findById/" + id;
    $.get(url, function (data) {
        areaDesc = data["areaDesc"];
    });
    return areaDesc;
}

/**
 *根据查询返回对象
 * */
function findListByUrlWithParam(url, param) {
    $.ajaxSettings.async = false;
    var object = [];
    $.post(url, param, function (data) {
        object = data;
    });
    return object;
}


var getAreaGalleryById = function (id) {
    var url = "/areaMedia/findByAreaIdAndMediaTypeId";
    var gallery = [];
    var obj = {
        areaId: id,
        mediaTypeId: 2
    };
    var media = findListByUrlWithParam(url, obj);
    for (var i = 0; i < media.length; i++) {
        console.log("media" + JSON.stringify("/" + media[i]["fileRelativeUrl"]));
        gallery[i] = "/" + media[i]["fileRelativeUrl"]
    }
    return gallery;
}


/**
 *
 * @param id
 */
var getSubAreaStatistics = function (colName0, colName1, id) {
    var objs = {
        type: "fields",
        fieldInfos: [{
            fieldName: colName0,
            visible: false,
            label: "行政区划",
            format: {
                places: 0,
                digitSeparator: true
            }
        },
            {
                fieldName: colName1,
                visible: false,
                label: "已围面积(万亩)",
                format: {
                    places: 0,
                    digitSeparator: true
                }

            }
        ]
    };


    return objs;

}


/**
 * 获取多媒体显示数据
 */
var getMediaInfos = function () {
    var gallery = getAreaGalleryById(1);
    return gallery;

}


var pieColor = ["#10AFC7", "#8BC73C", "#F35829", "#FFF101", "#922791"];
var
    situationRequireArray = [
        "esri/Map",
        "esri/views/SceneView",
        "esri/layers/ElevationLayer",
        "esri/layers/GroupLayer",
        "esri/layers/FeatureLayer",
        "esri/renderers/UniqueValueRenderer",
        "esri/symbols/ExtrudeSymbol3DLayer",
        "esri/symbols/PolygonSymbol3D",
        "esri/widgets/Home",
        "esri/widgets/Search",
        "esri/geometry/geometryEngine", //测量
        "esri/layers/support/LabelClass",
        "esri/symbols/TextSymbol3DLayer",
        "esri/symbols/LabelSymbol3D",
        "esri/widgets/LayerList",
        "esri/widgets/BasemapToggle",
        "dojo/domReady!"
    ];

//加载模块组
var layoutArray = [
    "esri/Map",
    "esri/views/SceneView",//SceneView是三维地图，MapView是二维地图
    "esri/layers/GroupLayer",
    "esri/layers/FeatureLayer",
    "esri/renderers/UniqueValueRenderer",
    "esri/symbols/ExtrudeSymbol3DLayer",
    "esri/symbols/PolygonSymbol3D",
    "esri/widgets/Home",
    "esri/widgets/Search",
    "esri/widgets/BasemapToggle",
    "esri/widgets/Legend",
    "esri/geometry/geometryEngine", //测量
    "esri/layers/support/LabelClass",
    "esri/symbols/TextSymbol3DLayer",
    "esri/symbols/LabelSymbol3D",
    "esri/widgets/LayerList",
    "dojo/on",
    "dojo/domReady!"
];


var planArray = [
    "esri/Map",
    "esri/views/SceneView",
    "esri/layers/GroupLayer",
    "esri/layers/FeatureLayer",
    "esri/widgets/LayerList",
    "esri/renderers/UniqueValueRenderer",
    "esri/symbols/ExtrudeSymbol3DLayer",
    "esri/symbols/PolygonSymbol3D",
    "esri/widgets/Home",
    "esri/widgets/Search",
    "esri/widgets/BasemapToggle",
    "esri/widgets/Legend",
    "esri/geometry/geometryEngine", //测量
    "esri/layers/support/LabelClass",
    "esri/symbols/TextSymbol3DLayer",
    "esri/symbols/LabelSymbol3D",
    "dojo/on",
    "dojo/domReady!"
];


var projectPlanArray = [
    "esri/Map",
    "esri/views/SceneView",
    "esri/layers/GroupLayer",
    "esri/layers/FeatureLayer",
    "esri/widgets/LayerList",
    "esri/renderers/UniqueValueRenderer",
    "esri/symbols/ExtrudeSymbol3DLayer",
    "esri/symbols/PolygonSymbol3D",
    "esri/widgets/Home",
    "esri/widgets/Search",
    "esri/widgets/BasemapToggle",
    "esri/widgets/Legend",
    "esri/geometry/geometryEngine", //测量
    "esri/layers/support/LabelClass",
    "esri/symbols/TextSymbol3DLayer",
    "esri/symbols/LabelSymbol3D",
    "dojo/on",
    "dojo/domReady!"
];


var gisQueryArray = [
    "esri/Map",
    "esri/views/SceneView",
    "esri/layers/GroupLayer",
    "esri/layers/FeatureLayer",
    "esri/tasks/QueryTask",
    "esri/tasks/support/Query",
    "esri/renderers/UniqueValueRenderer",
    "esri/symbols/ExtrudeSymbol3DLayer",
    "esri/symbols/PolygonSymbol3D",
    "esri/widgets/Home",
    "esri/widgets/Search",
    "esri/widgets/BasemapToggle",
    "esri/widgets/Legend",
    "esri/geometry/geometryEngine", //测量
    "esri/layers/support/LabelClass",
    "esri/symbols/TextSymbol3DLayer",
    "esri/symbols/LabelSymbol3D",
    "esri/widgets/LayerList",
    "dojo/dom",
    "dojo/on",
    "dojo/domReady!"
];

/**
 *
 * @param serviceName 服务名称
 * @returns {string} 根据服务名称获取服务地址
 */
function getEntireServiceUrlByServiceName(serviceName) {
    return server_url + "/arcgis/rest/services/" + serviceName + "/MapServer/0";

}


/**
 *
 * @param serviceName 服务名称
 * @returns {string} 根据服务名称获取服务地址
 */
function getFinalServiceUrlByServiceName(serviceName) {

    var url = server_url + "/arcgis/rest/services/" + serviceName + "/FeatureServer/0";

    console.log(" feature url", url);

    return url;

}


/**
 *
 * @returns {*}
 * @param FeatureLayer
 * @param LabelSymbol3D
 * @param TextSymbol3DLayer
 * @param serviceName 服务名称
 * @param labelColor
 * @param labelParamName 包含括号的参数
 * @param popUp
 */
// function create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, renderer, serviceName, titleName, labelColor, labelParamName, popUp, visible) {
//     var featureLayer = new FeatureLayer({
//         title: titleName,
//         url: getFinalServiceUrlByServiceName(serviceName),
//         outFields: ["*"],
//         labelsVisible: true,
//         popupTemplate: popUp,
//         render: renderer,
//         labelingInfo: [{
//             symbol: new LabelSymbol3D({
//                 symbolLayers: [new TextSymbol3DLayer({
//                     material: {color: labelColor},
//                     size: 12
//                 })]
//             }),
//             maxScale: 0,
//             minScale: 0,
//             labelPlacement: "above-center",
//             labelExpressionInfo: {
//                 value: labelParamName
//             }
//         }],
//         dockEnabled: true,
//         dockOptions: {
//             buttonEnabled: false,
//             breakpoint: false,
//         },
//         visible: visible ? visible : false
//     });
//
//
//     return featureLayer;
// }


/**
 *
 * @returns {*}
 * @param FeatureLayer
 * @param LabelSymbol3D
 * @param TextSymbol3DLayer
 * @param serviceName 服务名称
 * @param labelColor
 * @param labelParamName 包含括号的参数
 * @param popUp
 */
function create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, renderer, serviceName, titleName, labelColor, labelParamName, popUp, visible) {
    var featureLayer = new FeatureLayer({
        title: titleName,
        url: getFinalServiceUrlByServiceName(serviceName),
        outFields: ["*"],
        renderer: renderer,
        popupTemplate: popUp,
        labelsVisible: true,
        labelingInfo: [{
            symbol: new LabelSymbol3D({
                symbolLayers: [new TextSymbol3DLayer({
                    material: {color: labelColor},
                    size: 12
                })]
            }),
            maxScale: 0,
            minScale: 0,
            labelPlacement: "above-right",
            labelExpressionInfo: {
                value: labelParamName
            }
        }],
        dockEnabled: true,
        dockOptions: {
            buttonEnabled: false,
            breakpoint: false
        },
        visible: visible ? visible : true
    });
    return featureLayer;
}


/**
 *
 * @returns {*}
 * @param FeatureLayer
 * @param LabelSymbol3D
 * @param TextSymbol3DLayer
 * @param serviceName 服务名称
 * @param labelColor
 * @param labelParamName 包含括号的参数
 * @param popUp
 */
function createCallOutFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, renderer, serviceName, titleName, labelColor, labelParamName, popUp, visible) {
    var featureLayer = new FeatureLayer({
        title: titleName,
        url: getFinalServiceUrlByServiceName(serviceName),
        outFields: ["*"],
        elevationInfo: {
            mode: "relative-to-ground"
        },
        renderer: renderer,
        popupTemplate: popUp,
        labelsVisible: true,
        labelingInfo: [{
            symbol: {
                type: "label-3d", // autocasts as new LabelSymbol3D()
                symbolLayers: [{
                    type: "text", // autocasts as new TextSymbol3DLayer()
                    material: {
                        color: "black"
                    },
                    halo: {
                        color: [255, 255, 255, 1],
                        size: 2
                    },
                    size: 10
                }],
                // Labels need a small vertical offset that will be used by the callout
                verticalOffset: {
                    screenLength: 500,
                    maxWorldLength: 50000,
                    minWorldLength: 30
                },
                callout: {
                    type: "line",
                    size: 0.5,
                    color: [255, 255, 255],
                    border: {
                        color: [255, 255, 255, 0.7]
                    }
                }
            },
            maxScale: 0,
            minScale: 0,
            labelPlacement: "above-center",
            labelExpressionInfo: {
                value: labelParamName
            }
        }],
        dockEnabled: true,
        dockOptions: {
            buttonEnabled: false,
            breakpoint: false,
        },
        visible: visible ? visible : true
    });
    return featureLayer;
}

/**
 *
 * @returns {*}
 * @param FeatureLayer
 * @param LabelSymbol3D
 * @param TextSymbol3DLayer
 * @param serviceName 服务名称
 * @param labelColor
 * @param labelParamName 包含括号的参数
 * @param popUp
 */
function create2DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, renderer, serviceName, titleName, labelColor, labelParamName, popUp, visible) {
    var featureLayer = new FeatureLayer({
        title: titleName,
        url: getFinalServiceUrlByServiceName(serviceName),
        outFields: ["*"],
        renderer: renderer,
        popupTemplate: popUp,
        labelsVisible: true,
        labelingInfo: [{
            symbol: new LabelSymbol3D({
                symbolLayers: [new TextSymbol3DLayer({
                    material: {color: labelColor},
                    size: 12
                })]
            }),
            maxScale: 0,
            minScale: 0,
            labelPlacement: "above-center",
            labelExpressionInfo: {
                value: labelParamName
            }
        }],
        dockEnabled: true,
        dockOptions: {
            buttonEnabled: false,
            breakpoint: false,
        },
        visible: visible ? visible : true
    });
    return featureLayer;
}


/**
 *
 * @returns {*}
 * @param FeatureLayer
 * @param LabelSymbol3D
 * @param TextSymbol3DLayer
 * @param serviceName 服务名称
 * @param labelColor
 * @param labelParamName 包含括号的参数
 * @param popUp
 */
function createFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, serviceName, titleName, labelColor, labelParamName, popUp, visible) {

    var featureLayer = new FeatureLayer({
        title: titleName,
        url: getFinalServiceUrlByServiceName(serviceName),
        outFields: ["*"],
        labelsVisible: true,
        popupTemplate: popUp,
        labelingInfo: [{
            symbol: new LabelSymbol3D({
                symbolLayers: [new TextSymbol3DLayer({
                    material: {color: labelColor},
                    size: 12
                })]
            }),
            maxScale: 0,
            minScale: 0,
            labelPlacement: "always-horizontal",
            labelExpressionInfo: {
                value: labelParamName
            }
        }],
        visible: visible ? visible : false
    });
    return featureLayer;
}


/**
 *
 * @returns {*}
 * @param FeatureLayer
 * @param LabelSymbol3D
 * @param TextSymbol3DLayer
 * @param serviceName 服务名称
 * @param labelColor
 * @param labelParamName 包含括号的参数
 * @param popUp
 */
function createFeatureLayerWithFilter(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, serviceName, titleName, labelColor, labelParamName, popUp, visible, filter) {


    var featureLayer = new FeatureLayer({
        title: titleName,
        url: getFinalServiceUrlByServiceName(serviceName),
        outFields: ["*"],
        labelsVisible: true,
        popupTemplate: popUp,
        labelingInfo: [{
            symbol: new LabelSymbol3D({
                symbolLayers: [new TextSymbol3DLayer({
                    material: {color: labelColor},
                    size: 12
                })]
            }),
            maxScale: 0,
            minScale: 0,
            labelPlacement: "above-center",
            labelExpressionInfo: {
                value: labelParamName
            }
        }],
        dockEnabled: true,
        dockOptions: {
            buttonEnabled: false,
            breakpoint: false,
        },
        visible: visible ? visible : false,
        definitionExpression: filter
    });


    console.log("url", featureLayer.url);
    return featureLayer;
}


/**
 *
 * @returns {*}
 * @param FeatureLayer
 * @param LabelSymbol3D
 * @param TextSymbol3DLayer
 * @param serviceName 服务名称
 * @param labelColor
 * @param labelParamName 包含括号的参数
 * @param popUp
 */
function createPointFeatureLayerWithFilter(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, render, serviceName, titleName, labelColor, labelParamName, popUp, visible, filter) {
    var featureLayer = new FeatureLayer({
        title: titleName,
        url: getFinalServiceUrlByServiceName(serviceName),
        outFields: ["*"],
        labelsVisible: true,
        popupTemplate: popUp,
        render: render,
        dockEnabled: true,
        dockOptions: {
            buttonEnabled: false,
            breakpoint: false,
        },
        visible: visible ? visible : false,
        definitionExpression: filter
    });
    return featureLayer;
}

/**
 *
 * @param url 地图服务路径
 * @returns {*}
 */
function createBaseMapLayer(url) {
    var baseMap = null;
    require(["esri/layers/BaseTileLayer"], function (BaseTileLayer) {
        baseMap = new BaseTileLayer({
            url: url
        });
    });

    return baseMap;

}

/**
 *  初始化组件
 * @param Home
 * @param position
 */
function initHome(Home, view, position, index) {
    var homeBtn = new Home({
        view: view
    });
    view.ui.add(homeBtn, {
        position: position,
        index: index
    });
}


/**
 *  初始化组件
 * @param Home
 * @param position
 */
function initLayerList(LayerList, view, position, index) {
    var layerList = new LayerList({
        view: view
    });
    view.ui.add(layerList, {
        position: position,
        index: index
    });
}


function initBasemapToggle(BasemapToggle, view, nextBasemap, index) {
    // 1 - Create the widget
    var toggle = new BasemapToggle({
        view: view, // view that provides access to the map's 'topo' basemap
        nextBasemap: nextBasemap// allows for toggling to the 'hybrid' basemap
    });
// Add widget to the top right corner of the view
    view.ui.add(toggle, {
        position: "top-left",
        index: index
    });

}


/**
 *
 * @param GroupLayer  图层组
 * @param groupName 图层组名称
 * @param layers 图层列表数组
 */
function createGroupLayer(GroupLayer, groupName, layers, visible) {
    var borderGroupLayer = new GroupLayer({
        title: groupName,
        visibility: visible,
        visibilityMode: "independent",
        layers: layers,
        opacity: 1
    });
    return borderGroupLayer;
}

/**
 *
 * @param GroupLayer  图层组
 * @param groupName 图层组名称
 * @param layers 图层列表数组
 */
function createGroupLayer(GroupLayer, groupName, layers, visible) {
    var borderGroupLayer = new GroupLayer({
        title: groupName,
        visibility: visible,
        visibilityMode: "independent",
        layers: layers,
        opacity: 1
    });
    return borderGroupLayer;
}


/**
 *
 * @param locId 位置id
 *
 * 根据位置id获取围垦描述
 */
function getWkDescByLocId(locId) {
    var wkDesc = "";
    $.ajaxSettings.async = false;
    var url = "/situation/getWkDescByLocId/" + locId;
    $.get(url, function (data) {
        wkDesc = data["description"];
    });
    return wkDesc;
}


/**
 * 创建弹出模板
 */
function createPopupTemplate(id, name, appName) {
    $.ajaxSettings.async = false;
    var buildDesc = "";
    var url = "stAreaBuilding/getLocationBuilding/" + 15;
    $.get(url, function (data) {
        buildDesc = data["description"];
    });
    appName = appName ? appName : "";
    var cityBoundery = {
        title: name + appName,
        content: [{
            type: "text",
            text: buildDesc
        }, {
            type: "text",
            text: createMapTable(id)
        }],
        highlightEnabled: true
    };
    return cityBoundery;
}


/**
 * 根据行政区划级别查询数据
 * @returns {string}
 * @param dataList 数据统计列表
 */
function createMapTable(id) {
    $.ajaxSettings.async = false;
    //根据位置id查询围垦现状统计情况
    var table = "<table id='dataListTable' style='color: white;align-content: center;border: 1px solid yellow'>";
    table += "<caption>江苏省沿海围垦面积统计</caption>";
    table += "<tr><td>行政区划</td><td>围垦面积(万亩)</td></tr>";
    var url = "stAreaBuilding/sumByCity";
    $.get(url, function (data) {
        for (var x in data) {
            console.log("data[x]" + JSON.stringify(data[x]));
            table += "<tr><td>" + data[x]['cityName'] + "</td><td>" + data[x]['buildSize'].toFixed(2) + "</td></tr>";
        }
    });
    table += "</table>";
    return table;
}

function createMapChart() {
    return '<div id="chart" style="width:100%;height:180px;border: 1px solid yellow"></div>';
}


function getWkxzBycityData() {
    $.ajaxSettings.async = false;
    var dataList = [];
    var url = "stAreaBuilding/sumByCity";
    $.get(url, function (data) {
        dataList = data;
    });

    console.log("sumByCity---------" + JSON.stringify(dataList));
    return dataList;
}

function getWkxzCityNames(dataList) {
    var cityNames = [];
    for (var x in dataList) {
        cityNames[x] = dataList[x]["cityName"];
    }
    return cityNames;

}

function getWkxzBuildSize(dataList) {
    var buildSizes = [];
    for (var x in dataList) {
        buildSizes[x] = dataList[x]["buildSize"];
    }
    return buildSizes;

}

/**
 * 获取系统的底图列表
 * @returns {Array}
 */
function getBaseMaps() {
    $.ajaxSettings.async = false;
    var baseMaps = [];
    var url = "geoBaseMap/findAll";
    $.getJSON(url, function (data) {
        for (var x in data) {
            baseMaps[x] = data[x]["serviceUrl"]
        }
    });
    console.log("baseMaps----" + JSON.stringify(baseMaps));
    return baseMaps;
}


/**
 * 获取地理信息系统三维标签颜色
 * @returns {Array}
 */
function get3DLabelColor() {
    $.ajaxSettings.async = false;
    var labelColor = null;
    var url = "/sysParams/findByParamName";
    var obj = {
        paramName: "3D_LABEL_COLOR"
    }
    $.post(url, obj, function (data) {
        labelColor = data["paramValue"];
    });
    return labelColor;
}


/**
 * 获取高程图层地址
 * @returns {Array}
 */
function getElevationLayer() {
    $.ajaxSettings.async = false;
    var evelationUrl = [];
    var url = "sysParams/findByParamName";
    var params = {
        paramName: "ELEVATION_URL"
    }
    $.post(url, params, function (data) {
        evelationUrl = data["paramValue"]
    });
    return evelationUrl;

}


/**
 * 初始化高程层
 */
function initEvelationLayer(ElevationLayer, map) {
    // Create elevation layer and add to the map
    var elevationLayer = new ElevationLayer({
        url: getElevationLayer(),
        visible: false
    });
    map.ground.layers.add(elevationLayer);
}


/**
 *
 * @param view 视图
 * @param feature 要素
 *
 * 定位到要素位置
 */
function locateFeature(view, feature) {
    view.goTo({
            target: feature,
            tilt: 45,
            duration: 2000,
            easing: "in-out-expo",
            zoom: 11,
            spatialReference: 32649
        },
        {
            animate: true
        }
    );
}


/**
 *
 * @param view 视图
 * @param feature 要素
 *
 * 定位到要素位置
 */
function locateFeatureWithZoom(view, feature, zoom) {

    view.goTo({
        target: feature,
        tilt: 70,
        duration: 1000,
        easing: "in-out-expo",
        zoom: zoom
    });
}

/**
 *
 * @param view 视图
 * @param feature 要素
 *
 * 定位到用户当前位置
 */
function locateUserRegion(view, feature) {
    view.goTo({
        target: feature,
        tilt: 70,
        duration: 1000,
        easing: "in-out-expo",
        zoom: 10
    });
}

/**
 *
 * @param target
 * @param positionLayer
 * @returns {*}
 */
function findUserLocation(positionLayer, locId) {
    var location = null;
    var query = positionLayer.createQuery();
    query.where = "id=" + locId;
    positionLayer.queryFeatures(query).then(
        function (result) {
            location = result.features[0];
        });
    console.log("location-----------------" + location);
    return location;
}


/**
 *获取最大的id
 */
function getMaxGeoId(controllerName) {
    var newId = 1;
    var url = "/" + controllerName + "/findMaxId";
    $.ajaxSettings.async = false;
    $.getJSON(url, function (data) {
        newId = data;
    });
    return newId;
}

/**
 *
 * @returns {*}
 */
function findAllFeatures(featureLayer) {
    var features = null;
    featureLayer.queryFeatures().then(
        function (result) {
            features = result.features;
        });
    console.log("features-----------------" + JSON.stringify(features));
    return features;


}


/**
 *
 * @returns {*}
 */
function findAllGeoFeatures(entityName) {
    var url = "/" + entityName + "/findAll";
    var features = [];
    $.getJSON(url, function (data) {
        features = data;
    })
    return features;
}


/**
 *
 * @param id
 * @returns {Array}
 * 根据项目id查询项目多媒体信息
 */
var getProjectGalleryById = function (id) {
    console.log("---------------请求图");
    var url = "/projectMedia/findByAreaProjectId/" + id;
    $.ajaxSettings.async = false;
    var urls = [];
    $.getJSON(url, function (data) {
        for (var i = 0; i < data.length; i++) {
            urls[i] = "/" + data[i]["fileRelativeUrl"];
        }
    });

    if (!urls.length) {
        console.log("---------------no pic");
        urls[0] = "/img/nopic.jpg";
    }
    var gallery = [];
    for (var x in urls) {
        gallery[x] = {
            type: "image",
            value: {
                sourceURL: urls[x]
            }
        }
    }
    return gallery;
}


/**
 * 获取当前登录用户的地理坐标
 */
function getLoginUserPosition() {
    var center = [121.13, 33.38];
    return center;
}


/**
 *
 * @param userPosition 初始化用户视角
 * @returns {{center: *, zoom: number, heading: number, tilt: number, duration: number, easing: string}}
 */
function initViewPoint(userPosition) {
    var viewPoint = {
        center: userPosition,
        heading: 0,
        tilt: 40,
        zoom: 8,
        duration: 2000,
        easing: "in-out-expo"

    };

    return viewPoint;
}


function showList(view) {
    var visible = view.popup;
    //如果弹出框  关闭后再显示列表
    if (visible) {
        view.popup.close();
        // $("#panelSide").show();
        $("#map_info_panel").hide();
        $("#panelSide").toggle("slow");
    }
}


/**
 * 切换全屏
 */
function toggleFullScreen() {
    console.log("变大变大变大----------全屏全屏全屏");
    $("#contentWindow").addClass("active");
    $("#contentWindow .toggleScreen").html("<span class='pull-right' onclick=\"toggleScreen()\"><i class=\"esri-icon-zoom-out-fixed \"></i>退出全屏</span>")
}

function toggleScreen() {
    console.log("----------bubuuuuuuuuuuuuuuuuu全屏全屏全屏");
    $("#contentWindow").removeClass("active");
    $("#contentWindow .toggleScreen").html("<span class='pull-right' onclick=\"toggleFullScreen()\"><i class=\"esri-icon-zoom-out-fixed \"></i>显示全屏</span>")
}

/**
 *
 * @param FeatureLayer
 * @param serviceName
 * @param layerName
 * @param labelName
 * @returns {*}
 */
function createCalloutLabelLayer(FeatureLayer, serviceName, layerName, labelName) {
    var url = getEntireServiceUrlByServiceName(serviceName);
    var peaks = new FeatureLayer({
        url: url,
        elevationInfo: {
            mode: "relative-to-ground"
        },
        title: layerName,
        renderer: {
            type: "simple", // autocasts as new SimpleRenderer()
            symbol: {
                type: "point-3d", // autocasts as new PointSymbol3D()
                symbolLayers: [{                    type: "icon", // autocasts as new IconSymbol3DLayer()
                    resource: {
                        primitive: "circle"
                    },
                    material: {
                        color: "black"
                    },
                    size: 0
                }]
            }
        }
        ,
        outFields: ["*"],
        screenSizePerspectiveEnabled: true,
        featureReduction: false,
        // Add labels with callouts of type line to the icons
        labelingInfo: [{
            labelPlacement: "above-center",
            labelExpressionInfo: {
                value: labelName
            },
            symbol: {
                type: "label-3d", // autocasts as new LabelSymbol3D()
                symbolLayers: [{
                    type: "text", // autocasts as new TextSymbol3DLayer()
                    material: {
                        color: get3DLabelColor()//文本
                    },
                    size: 10
                }],
                verticalOffset: {
                    screenLength: 500,
                    maxWorldLength: 10000,
                    minWorldLength: 30
                },
                callout: {
                    type: "line", // autocasts as new LineCallout3D()
                    size: 0.5,
                    color: "#d2bdd9" //引线
                }
            }
        }],
        labelsVisible: true
    });
    return peaks;
}


// Function that automatically creates the symbol for the points of interest
function getUniqueValueSymbol(name, color) {

    // verticalOffset shifts the symbol vertically
    var verticalOffset = {
        screenLength: 500,
        maxWorldLength: 3000,
        minWorldLength: 30
    };


    return {
        type: "point-3d", // autocasts as new PointSymbol3D()
        symbolLayers: [{
            type: "icon", // autocasts as new IconSymbol3DLayer()
            resource: {
                href: name
            },
            size: 25,
            outline: {
                color: "white",
                size: 2
            }
        }],
        verticalOffset: verticalOffset,
        callout: {
            type: "line", // autocasts as new LineCallout3D()
            color: color,
            size: 0.5,
            border: {
                color: color
            }
        }
    };
}


/**
 *
 * @param FeatureLayer
 * @param serviceName
 * @param pointsRenderer
 * @param labelName
 * @returns {*}项目
 */
function createPointCallOutLabel(FeatureLayer, serviceName, labelName, pointsRenderer, layerName) {
    var url = getEntireServiceUrlByServiceName(serviceName);
    var pointsLayer = new FeatureLayer({
            url: url,
            title: layerName,
            renderer: pointsRenderer,
            outFields: ["*"],
            featureReduction: {
                type: "selection"
            },
            labelingInfo: [
                {
                    labelExpressionInfo: {
                        value: "{" + labelName + "}"
                    },
                    symbol: {
                        type: "label-3d",
                        symbolLayers: [{
                            type: "text",
                            material: {
                                color: get3DLabelColor()
                            },
                            size: 10
                        }]
                    }
                }],
            labelsVisible: true
        })
    ;
    return pointsLayer;
}


/**
 *
 * @param FeatureLayer
 * @param serviceName
 * @param pointsRenderer
 * @param labelName
 * @returns {*}项目
 */
function createPointCallOutLabelWithPopup(FeatureLayer, serviceName, labelName, pointsRenderer, layerName, popup) {
    var url = getEntireServiceUrlByServiceName(serviceName);
    var pointsLayer = new FeatureLayer({
            url: url,
            title: layerName,
            renderer: pointsRenderer,
            outFields: ["*"],
            featureReduction: {
                type: "selection"
            },
            labelingInfo: [
                {
                    labelExpressionInfo: {
                        value: "{" + labelName + "}"
                    },
                    symbol: {
                        type: "label-3d",
                        symbolLayers: [{
                            type: "text",
                            material: {
                                color: get3DLabelColor()
                            },
                            size: 10
                        }]
                    }
                }],
            labelsVisible: true,
            popupTemplate: popup
        })
    ;
    return pointsLayer;
}

/**
 * 根据locLevel获取江苏省的id
 */
function findJiangSuId() {
    var jsId = 0;//设置默认值为0
    $.ajaxSettings.async = false;
    var url = "location/findByLocLevel/0";
    $.getJSON(url, function (data) {
        jsId = data[0]["id"];
    });
    return jsId;
}
/**
 * 查询所有的区块信息
 */
function findAllAreas4Sel() {
    var areas4sel = [];
    $.ajaxSettings.async = false;
    var url = "area/findAll4Sel";
    $.getJSON(url, function (data) {
        areas4sel = data;
    });
    return areas4sel;
}

/**
 * 根据某个行政区划id查询所有的区块信息
 */
function findAreas4SelByLocId(locId) {
    var areas4sel = [];
    $.ajaxSettings.async = false;
    //如果locId等于江苏省的id，则查询所有的区块；否则查询该市的区块
    var jsId = findJiangSuId();//获取江苏省的id。
    var url = (locId == jsId ) ? "area/findAll4Sel" : ("area/findAll4SelByLocId/" + locId);
    $.getJSON(url, function (data) {
        areas4sel = data;
    });
    return areas4sel;
}


/**
 * 查询所有的位置信息
 */
function findAllLocations4Sel() {
    var areas4sel = [];
    $.ajaxSettings.async = false;
    var url = "location/findAll4Sel";
    $.getJSON(url, function (data) {
        areas4sel = data;
    });
    return areas4sel;
}


/**
 * 查询所有的在建或已建项目信息
 */
function findAllProjects4Sel() {
    var projects4sel = [];
    $.ajaxSettings.async = false;
    var url = "project/findAll4Sel";
    $.getJSON(url, function (data) {
        projects4sel = data;
    });
    return projects4sel;
}

/**
 * 根据某个行政区划id查询所有的在建或已建项目信息
 */
function findAllProjects4SelByLocId(locId) {
    var projects4sel = [];
    $.ajaxSettings.async = false;
    //如果locId等于江苏省的id，则查询所有的区块；否则查询该市的区块
    var jsId = findJiangSuId();//获取江苏省的id。
    var url = (locId == jsId ) ? "project/findAll4Sel" : ("project/findAll4SelByLocId/" + locId);
    $.getJSON(url, function (data) {
        projects4sel = data;
    });
    return projects4sel;
}

/**
 * 查询所有的规划项目信息
 */
function findPlanProjectAll4Sel() {
    var projects4sel = [];
    $.ajaxSettings.async = false;
    var url = "project/findPlanProjectAll4Sel";
    $.getJSON(url, function (data) {
        projects4sel = data;
    });
    return projects4sel;
}

/**
 * 根据某个行政区划id查询所有的规划项目信息
 */
function findPlanProject4SelByLocId(locId) {
    var projects4sel = [];
    $.ajaxSettings.async = false;
    //如果locId等于江苏省的id，则查询所有的规划项目；否则查询该市的规划项目
    var jsId = findJiangSuId();//获取江苏省的id。
    var url = (locId == jsId ) ? "project/findPlanProjectAll4Sel" : ("project/findPlanProjectAll4SelByLocId/" + locId);
    $.getJSON(url, function (data) {
        projects4sel = data;
    });
    return projects4sel;
}

/**
 *
 * @param id
 * @returns {*}
 *
 * 根据id获取位置信息
 */
function findObjById(objName, id) {
    var object = null;
    $.ajaxSettings.async = false;
    var url = objName + "/findById/" + id;
    $.get(url, function (data) {
        object = data;
    });
    return object;
}


/**
 *
 * @param dataList
 * @returns {Array}
 */
function getStatCities(dataList, paramName) {
    var cities = [];
    var city;
    for (var x = 0; x < dataList.length; x++) {
        city = dataList[x][paramName];
        if (!cities.containObj(city)) {
            cities[x] = city;
        }
    }
    console.log("cities", JSON.stringify(cities));
    return cities;
}


/**
 *
 * @param dataList
 * @param paramName
 * @param paramValue
 * @returns {Array}
 */
function getStatCitiesValues(dataList) {
    var citiesValues = [];
    var value;
    var name;
    for (var x in dataList) {
        name = x;
        value = dataList[x];
        citiesValues.push({value: value, name: name});

    }
    console.log("citiesValues", JSON.stringify(citiesValues));
    return citiesValues;
}


/**
 *初始化选择区块信息
 */
function initAreaSelect(container) {
    var areas = findAllAreas4Sel();
    var html = "";
    // $(container).html(html);
    for (var x = 0; x < areas.length; x++) {
        html += "<option value =" + areas[x]['id'] + "  >" + areas[x]['areaNo'] + areas[x]['areaName'] + "</option>";
    }
    $(container).append(html);
    // initSelect();
}

/**
 *选择行政区划后更新区块列表，如选择连云港市，则区块下拉列表仅显示该市的所有区块
 */
function initAreaSelectWithLocId(container, locId) {
    $(container).html("<option value =\"\" >" + "请选择区块信息" + "</option>");//首先恢复 未选择 状态
    var areas = findAreas4SelByLocId(locId);
    var html = "";
    for (var x = 0; x < areas.length; x++) {
        html += "<option value =" + areas[x]['id'] + " >" + areas[x]['areaNo'] + areas[x]['areaName'] + "</option>";
    }
    $(container).append(html);
    // initSelect();
}

/**
 *初始化选择地理信息
 */
function initLocationSelect(container) {
    var areas = findAllLocations4Sel();
    var html = "";
    $(container).html(html);
    for (var x = 0; x < areas.length; x++) {
        html += "<option value =" + areas[x]['id'] + "  >" + areas[x]['locName'] + "</option>";
    }
    $(container).html(html);
}


/**
 *初始化选择规划项目信息
 */
function initPlanedProjectSelect(container) {
    var planedProjects = findPlanProjectAll4Sel();
    var html = "";
    // $(container).html(html);
    for (var x = 0; x < planedProjects.length; x++) {
        html += "<option value =" + planedProjects[x]['id'] + "  >" + planedProjects[x]['projectName'] + "</option>";
    }
    $(container).append(html);
}


/**
 *选择行政区划后更新规划项目列表，如选择连云港市，则项目下拉列表仅显示该市的所有规划项目
 */
function initPlanedProjectSelectWithLocId(container, locId) {
    $(container).html("<option value =\"\" >" + "请选择项目信息" + "</option>");//首先恢复 未选择 状态
    var planedProjects = findPlanProject4SelByLocId(locId);
    var html = "";
    for (var x = 0; x < planedProjects.length; x++) {
        html += "<option value =" + planedProjects[x]['id'] + "  >" + planedProjects[x]['projectName'] + "</option>";
    }
    $(container).append(html);
}


/**
 *初始化项目下拉列表信息，显示所有的项目
 */
function initProjectSelect() {
    var areas = findAllProjects4Sel();
    var html = "";
    // $("#selectProject").html(html);
    for (var x = 0; x < areas.length; x++) {
        html += "<option value =" + areas[x]['id'] + "  >" + areas[x]['projectName'] + "</option>";
    }
    $("#selectProject").append(html);
}


/**
 *根据位置id查询并更新项目下拉列表信息
 */
function initProjectSelectWithLocId(container, locId) {
    $(container).html("<option value =\"\" >" + "请选择项目信息" + "</option>");//首先恢复 未选择 状态
    var areas = findAllProjects4SelByLocId(locId);
    var html = "";
    // $("#selectProject").html(html);
    for (var x = 0; x < areas.length; x++) {
        html += "<option value =" + areas[x]['id'] + "  >" + areas[x]['projectName'] + "</option>";
    }
    $("#selectProject").append(html);
}


/**
 *重置地理位置和区块或项目下拉选择
 */
function selectReset() {
    $("#selectLocation").val("15");
    $("#selectAreaBox select").val("");
    fillInfoPanelByLocId(15);
    view.goTo(initViewPoint(userPosition));
}


/**
 *
 * @param app  判断应用类型（区块area|项目project）
 * @param id
 */
function createGalleryById(media, container) {
    //根据获取到的多媒体列表信息  将多媒体信息加入到对应的相册中
    var a = "";
    var inner = "";
    var active = "active";
    var length = media.length > 5 ? 5 : media.length;
    for (var x = 0; x < length; x++) {
        console.log(media);
        var suffix = media[x].fileRelativeUrl;
        if (suffix.endsWith(".tif")) {
            continue;
        }
        active = (x == 0) ? "active" : "";
        a += '<li data-target="' + container + '" data-slide-to="' + x + '"  class="' + active + '" ></li>';
        inner += '<div class="item ' + active + '">';
        // inner += '<img src="' + media[x]["thumbNailUrl"] + '" width="100%" height="100%"  alt="' + media[x].mediaTree.treeName + '">';
        inner += '<img src="' + suffix + '" width="300px" height="300px"  alt="' + media[x].mediaTree.treeName + '">';
        inner += '<div class = "carousel-caption">' + media[x].mediaTree.treeName + '</div>';
        inner += "</div>";
    }

    if (length == 0) {
        active = (x == 0) ? "active" : "";
        a += '<li data-target="' + container + '" data-slide-to="' + x + '"  class="' + active + '" ></li>';
        inner += '<div class="item ' + active + '">';
        inner += '<img src="/img/default.png" width="100%" height="100%"  alt="默认图片">';
        inner += '<div class = "carousel-caption">暂无图片</div>';
        inner += "</div>";

    }

    console.log("a", a);
    console.log("inner", inner);
    $(container + "_ol").html(a);
    $(container + "_inner").html(inner);
}


//定义海岸线信息弹出层
var coastLinePopup = { // autocasts as new PopupTemplate()
    title: "{LINE_NAME}",
    content: "<p>海岸线详情：</p>" +
    "<ul><li>海岸线编号：{LINE_NO}</li>" +
    "<li>海岸线名称：{LINE_NAME}</li>" +
    "<li>海岸线长度：{LINE_LENGTH}公里</li>" +
    "<li>起始点：{START_POINT}</li>" +
    "<li>终止点：{END_POINT}</li></ul>",
    dockEnabled: true,
    dockOptions: {
        position: "center"
    }
};

//定义滩涂信息弹出层
var beachPopup = { // autocasts as new PopupTemplate()
    title: "{BEACH_NAME}",
    content: "<p>滩涂详情：</p>" +
    "<ul><li>滩涂编号：{BEACH_NO}</li>" +
    "<li>滩涂名称：{BEACH_NAME}</li></ul>",
    dockEnabled: true,
    dockOptions: {
        position: "center"
    }
};

//定义沿海沙洲信息弹出层
var sandPopup = { // autocasts as new PopupTemplate()
    title: "{SAND_NAME}",
    content: "<p>沿海沙洲详情：</p>" +
    "<ul><li>编号：{SAND_NO}</li>" +
    "<li>名称：{SAND_NAME}</li>" +
    "<li>南北长：{LENGTH}公里</li>" +
    "<li>东西宽：{WIDTH}公里</li>" +
    "<li>所在位置：{POSITION}</li></ul>",
    dockEnabled: true,
    dockOptions: {
        position: "center"
    }
};

function getCoastLinePopup() {
    return coastLinePopup;
}

function getBeachPopup() {
    return beachPopup;
}

function getSandPopup() {
    return sandPopup;
}


var highlightSelect;

/**
 *
 * @param layer
 * @param feature
 *
 * 要素高亮显示
 */
function highlightFeature(layer, feature) {
    view.whenLayerView(layer)
        .then(function (lyrView) {
            if (highlightSelect) {
                highlightSelect.remove();
            }
            highlightSelect = lyrView.highlight(feature);
        });
}