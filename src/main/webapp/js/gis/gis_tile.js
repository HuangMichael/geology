var baseMaps = getBaseMaps();
var userPosition = getLoginUserPosition();

var view;

require([
    "esri/Map",
    "esri/config",
    "esri/request",
    "esri/Color",
    "esri/views/SceneView",
    "esri/widgets/LayerList",
    "dojo/domReady!"
], function (Map, esriConfig, esriRequest, Color, SceneView, LayerList) {

    var map = new Map();

    // create a new scene view and add the map
    var view = new SceneView({
        basemap: "osm",
        container: "viewDiv",
        map: map,
        center: [0, 30],
        zoom: 3
    });

    // create a layer list widget
    var layerList = new LayerList({
        view: view,
    });
    view.ui.add(layerList, "top-right");
    view
        .then(function () {
            view.goTo(initViewPoint(userPosition));
        })
        .otherwise(function (err) {
            console.error("SceneView rejected:", err);
        });
});

