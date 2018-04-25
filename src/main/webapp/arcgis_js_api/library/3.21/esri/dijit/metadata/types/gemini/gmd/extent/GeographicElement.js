// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
require({cache:{"url:esri/dijit/metadata/types/gemini/gmd/extent/templates/GeographicElement.html":'\x3cdiv data-dojo-attach-point\x3d"containerNode"\x3e\r\n  \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/ObjectReference"\r\n    data-dojo-props\x3d"target:\'gmd:geographicElement\',minOccurs:1,maxOccurs:\'unbounded\',\r\n      label:\'${i18nIso.EX_Extent.geographicElement}\',\r\n      matchTopNode: [\r\n        {\r\n           qPath: \'gmd:EX_GeographicBoundingBox\',\r\n          qValue: null,\r\n          qMode: \'must\'\r\n        }  \r\n      ]"\x3e\r\n    \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/AbstractObject"\r\n      data-dojo-props\x3d"target:\'gmd:EX_GeographicBoundingBox\',minOccurs:0"\x3e\r\n      \x3cdiv class\x3d"gxeGeoExtentSection"\x3e\r\n        \x3cdiv class\x3d"gxeGeoExtentCoordinates"\x3e\r\n          \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Element"\r\n            data-dojo-props\x3d"target:\'gmd:westBoundLongitude\',\r\n              label:\'${i18nIso.EX_GeographicBoundingBox.westBoundLongitude}\'"\x3e\r\n            \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/GcoElement"\r\n              data-dojo-props\x3d"target:\'gco:Decimal\',value:-180"\x3e\r\n              \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/InputNumber"\r\n                data-dojo-props\x3d"hint:\'${i18nBase.hints.longitude}\'"\x3e\r\n                \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/GeoExtentTool"\r\n                  data-dojo-props\x3d"label:\'${i18nBase.geoExtent.button}\'"\x3e\x3c/div\x3e\r\n              \x3c/div\x3e\r\n            \x3c/div\x3e\r\n          \x3c/div\x3e\r\n          \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Element"\r\n            data-dojo-props\x3d"target:\'gmd:eastBoundLongitude\',\r\n              label:\'${i18nIso.EX_GeographicBoundingBox.eastBoundLongitude}\'"\x3e\r\n            \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/GcoElement"\r\n              data-dojo-props\x3d"target:\'gco:Decimal\',value:180"\x3e\r\n              \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/InputNumber"\r\n                data-dojo-props\x3d"hint:\'${i18nBase.hints.longitude}\'"\x3e\x3c/div\x3e\r\n            \x3c/div\x3e\r\n          \x3c/div\x3e\r\n          \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Element"\r\n            data-dojo-props\x3d"target:\'gmd:southBoundLatitude\',\r\n              label:\'${i18nIso.EX_GeographicBoundingBox.southBoundLatitude}\'"\x3e\r\n            \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/GcoElement"\r\n              data-dojo-props\x3d"target:\'gco:Decimal\',value:-90"\x3e\r\n              \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/InputNumber"\r\n                data-dojo-props\x3d"hint:\'${i18nBase.hints.latitude}\'"\x3e\x3c/div\x3e\r\n            \x3c/div\x3e\r\n          \x3c/div\x3e\r\n          \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Element"\r\n            data-dojo-props\x3d"target:\'gmd:northBoundLatitude\',\r\n              label:\'${i18nIso.EX_GeographicBoundingBox.northBoundLatitude}\'"\x3e\r\n            \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/GcoElement"\r\n              data-dojo-props\x3d"target:\'gco:Decimal\',value:90"\x3e\r\n              \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/InputNumber"\r\n                data-dojo-props\x3d"hint:\'${i18nBase.hints.latitude}\'"\x3e\x3c/div\x3e\r\n            \x3c/div\x3e\r\n          \x3c/div\x3e\r\n        \x3c/div\x3e\r\n        \x3cdiv class\x3d"gxeGeoExtentViewSection gxeViewOnly"\x3e\x3c/div\x3e\r\n        \x3cdiv class\x3d"gxeGeoExtentBottom"\x3e\x3c/div\x3e\r\n      \x3c/div\x3e\r\n    \x3c/div\x3e\r\n  \x3c/div\x3e\r\n\x3c/div\x3e'}});
define("esri/dijit/metadata/types/gemini/gmd/extent/GeographicElement","dojo/_base/declare dojo/_base/lang dojo/has ../../../../base/Descriptor ../../../../form/Element ../../../../form/InputNumber ../../../../form/iso/AbstractObject ../../../../form/iso/GcoElement ../../../../form/iso/GeoExtentTool ../../../../form/iso/ObjectReference dojo/text!./templates/GeographicElement.html ../../../../../../kernel".split(" "),function(a,b,c,d,g,h,k,l,m,n,e,f){a=a(d,{templateString:e});c("extend-esri")&&b.setObject("dijit.metadata.types.gemini.gmd.extent.GeographicElement",
a,f);return a});