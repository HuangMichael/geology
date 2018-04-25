var map, toolbar, symbol, geomTask;
require([
    "esri/map",
    "esri/toolbars/draw",
    "esri/layers/FeatureLayer",
    "esri/graphic",
    "esri/symbols/SimpleMarkerSymbol",
    "esri/symbols/SimpleLineSymbol",
    "esri/symbols/SimpleFillSymbol",
    "dojo/parser", "dijit/registry",
    "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
    "dijit/form/Button", "dijit/WidgetSet", "dojo/domReady!"
], function (Map, Draw, FeatureLayer, Graphic, SimpleMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol, parser, registry) {
    parser.parse();

    map = new Map("map", {
        basemap: "osm",
        center: [121, 35],
        zoom: 8
    });
    var layerUrl = "http://192.168.0.108:6080/arcgis/rest/services/projects_all/FeatureServer/0";
    var projectLayer = new FeatureLayer(layerUrl);
    projectLayer.editable = true;
    map.on("load", createToolbar);


    map.addLayer(projectLayer);
    registry.forEach(function (d) {
        // d is a reference to a dijit
        // could be a layout container or a button
        if (d.declaredClass === "dijit.form.Button") {
            d.on("click", activateTool);
        }
    });

    function activateTool() {
        var tool = $(this).attr("name");
        toolbar.activate(Draw[tool]);
        map.hideZoomSlider();
    }

    function createToolbar() {
        toolbar = new Draw(map);
        toolbar.on("draw-end", addToMap);
    }

    function addToMap(evt) {
        var symbol;
        toolbar.deactivate();
        map.showZoomSlider();
        switch (evt.geometry.type) {
            case "point":
            case "multipoint":
                symbol = new SimpleMarkerSymbol();
                break;
            case "polyline":
                symbol = new SimpleLineSymbol();
                break;
            default:
                symbol = new SimpleFillSymbol();
                break;
        }


        var currentGraphciNo = projectLayer.graphics.length;


        console.log("currentGraphciNo-------------" + currentGraphciNo);
        //获取最后一个要素的OBJECTID，并转为数值类型

        // console.log("currentObj-------------" + JSON.stringify(projectLayer.graphics[currentGraphciNo - 1].attributes));


        var currentObj = projectLayer.graphics[currentGraphciNo - 1].attributes['objectid'];
        console.log("currentObj-------------" + JSON.stringify(currentObj));
        //设置新增Graphic的属性，OBJECTID必须设置，其余可以设为NULL
        var attr = {
            "objectid": currentObj + 1
        };


        console.log("attr-------------" + JSON.stringify(attr));
        //产生新的Graphic
        var newGraphic = new Graphic(evt.geometry, symbol, attr);
        //使用applyEdits函数将Graphic保存到FeatureLayer中
        map.graphics.add(newGraphic);
        projectLayer.applyEdits([newGraphic], null, null);
    }
});