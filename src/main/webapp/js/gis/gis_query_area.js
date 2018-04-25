var baseMaps = getBaseMaps();
var server_url = getGisServerIp();
var userPosition = getLoginUserPosition();

var areaPopupTemplate;

var view;
require(layoutArray, function (Map, SceneView, GroupLayer, FeatureLayer, UniqueValueRenderer, ExtrudeSymbol3DLayer, PolygonSymbol3D, Home, Search, BasemapToggle, Legend, geometryEngine, LabelClass, TextSymbol3DLayer, LabelSymbol3D, LayerList, on) {
    var labelClass;


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


    var coastLineSym = new PolygonSymbol3D({
        symbolLayers: [
            new ExtrudeSymbol3DLayer({
                material: {
                    color: "#12fcf3"
                }
            })
        ]
    });


    areaBorderSym


    var areaBorderRenderer = new UniqueValueRenderer({
        defaultSymbol: areaBorderSym,
        defaultLabel: "围垦",
        field: "围垦",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });

    var buildAreaRenderer = new UniqueValueRenderer({
        defaultSymbol: buildAreaSym,
        defaultLabel: "围垦",
        field: "围垦",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });


    var reserveRenderer = new UniqueValueRenderer({
        defaultSymbol: reserveAreaSym,
        defaultLabel: "自然核心保护区",
        field: "自然核心保护区",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });

    var bufferRenderer = new UniqueValueRenderer({
        defaultSymbol: bufferAreaSym,
        defaultLabel: "自然核心缓冲区",
        field: "自然核心缓冲区",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });


    var experimentRenderer = new
    UniqueValueRenderer({
        defaultSymbol: experimentAreaSym,
        defaultLabel: "自然核心实验区",
        field: "自然核心实验区",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });

    var coastLineRenderer = new
    UniqueValueRenderer({
        defaultSymbol: coastLineSym,
        defaultLabel: "海岸线",
        field: "海岸线",
        visualVariables: [{
            type: "size",
            field: "height",
            valueUnit: "meters"
        }]
    });


    var measureThisAction = {
        title: "统计",
        id: "measure-this",
        image: "/img/button/tongji.png"
    };

    var provAction = {
        title: "统计",
        id: "provStat",
        image: "/img/button/tongji.png"
    };


    var showDetail = {
        title: "明细",
        id: "detail",
        image: "/img/button/tongji.png"
    };


    var locPopupTemplate = {
        title: "{loc_name}围垦现状",
        outFields: ["*"],
        content: [
            {
                type: "text",
                text: "<div id ='locDesc' class='text-indent'></div>"
            },
            {
                type: "text",
                text: "<div id ='chart1' style='height:200px;width:300px'></div>"
            }
        ],
        actions: [provAction]
    };


    var buildAreaLayer, wkGroupLayer;

    var map = new Map({
        basemap: baseMaps[0],
        ground: "world-elevation",//,
        center: userPosition
    });


    view = new SceneView({
        container: "viewDiv",
        map: map,
        popup: {
            dockEnabled: true,
            dockOptions: {
                buttonEnabled: true,
                breakpoint: false
            }
        }
    });
    var homeBtn = new Home({
        view: view
    });
    // Add the home button to the top left corner of the view
    view.ui.add(homeBtn, "top-left");


    view
        .then(function () {
            buildAreaLayer = create3DFeatureLayer(FeatureLayer, LabelSymbol3D, TextSymbol3DLayer, buildAreaRenderer, "area", "围垦现状", "yellow", "{area_no}{area_name}", null, true);
            wkGroupLayer = createGroupLayer(GroupLayer, "围垦主题图层", [buildAreaLayer]);
            map.layers.add(wkGroupLayer);
            var legend = new Legend({
                view: view,
                layerInfos: [{
                    layer: buildAreaLayer,
                    title: ""
                }
                ]
            });

            // Add widget to the bottom right corner of the view
            view.ui.add(legend, {
                position: "bottom-right",
                index: -100


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

            view.goTo({
                center: userPosition,
                zoom: 6,
                heading: 0,
                tilt: 50,
                duration: 2000,
                easing: "in-out-expo"
            })


        })
        .otherwise(function (err) {
            console.error("SceneView rejected:", err);
        });


    $("#doBtn").on("click", function () {


        var a0 = $("#attSelect").val();
        var a1 = $("#signSelect").val();
        var a2 = $("#valSelect").val();
        var whereStr = a0 + a1 + "'" + a2 + "'";
        console.log(whereStr);
        buildAreaLayer.definitionExpression = (whereStr);
        view.goTo({
            target: buildAreaLayer.features[0],
            zoom: 6,
            heading: 0,
            tilt: 90,
            duration: 2000,
            easing: "in-out-expo"
        })

    })
});



