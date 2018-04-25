// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
define("esri/arcade/functions/topo","require exports ../../geometry/Geometry ../languageUtils ../../geometry/geometryEngine ../kernel ../Feature".split(" "),function(t,m,h,f,e,k,n){function p(b,d,g,c){try{if(d===b.length)return g;var l=b[d];if(null===l)return p(b,d+1,g,c);var r=e.planarArea(l,c);return p(b,d+1,g+r,c)}catch(a){throw a;}}function q(b,d,g,c){try{if(d===b.length)return g;var l=b[d];if(null===l)return q(b,d+1,g,c);var r=e.planarLength(l,c);return q(b,d+1,g+r,c)}catch(a){throw a;}}Object.defineProperty(m,
"__esModule",{value:!0});m.spatialrelationFunction=function(b,d,g,c){return c(b,d,function(c,b,a){f.pcCheck(a,2,2);if(a[0]instanceof h)if(a[1]instanceof h)switch(g){case "esriSpatialRelEnvelopeIntersects":return e.intersects(k.shapeExtent(a[0]),k.shapeExtent(a[1]));case "esriSpatialRelIntersects":return e.intersects(a[0],a[1]);case "esriSpatialRelContains":return e.contains(a[0],a[1]);case "esriSpatialRelOverlaps":return e.overlaps(a[0],a[1]);case "esriSpatialRelWithin":return e.within(a[0],a[1]);
case "esriSpatialRelTouches":return e.touches(a[0],a[1]);case "esriSpatialRelCrosses":return e.crosses(a[0],a[1]);default:throw Error("Unrecognised Relationship");}else{if(null===a[1])return!1;throw Error("Spatial Relation cannot accept this parameter type");}else if(null===a[0]){if(a[1]instanceof h||null===a[1])return!1}else throw Error("Spatial Relation cannot accept this parameter type");})};m.spatialTopoFunction=function(b,d,g,c){return c(b,d,function(c,b,a){f.pcCheck(a,2,3);if(a[0]instanceof
h){if(void 0===a[1]&&"union"===g)return a[0];if(a[1]instanceof h){c=f.defaultUndefined(a[2],!0);b=null;switch(g){case "difference":b=e.difference(a[0],a[1]);break;case "intersection":b=e.intersect(a[0],a[1]);break;case "symmetricdifference":b=e.symmetricDifference(a[0],a[1]);break;case "union":b=e.union([a[0],a[1]]);break;default:throw Error("Unrecognised Relationship");}c&&null!==b&&b.type!==a[0].type&&(b=null);return b}if(null===a[1])switch(g){case "difference":return a[0];case "intersection":return null;
case "symmetricdifference":return a[0];case "union":return a[0];default:throw Error("Unrecognised Relationship");}else throw Error("Spatial Topo cannot accept this parameter type");}else if(null===a[0])if(a[1]instanceof h)switch(g){case "difference":return null;case "intersection":return null;case "symmetricdifference":return a[1];case "union":return a[1];default:throw Error("Unrecognised Relationship");}else{if(null===a[1])return null;throw Error("Spatial Topo cannot accept this parameter type");
}else throw Error("Spatial Topo cannot accept this parameter type");})};m.registerFunctions=function(b,d){b.buffer=function(b,c){return d(b,c,function(b,c,a){f.pcCheck(a,2,4);if(a[0]instanceof h)return e.buffer(a[0],a[1],k.convertLinearUnitsToCode(a[2]),f.defaultUndefined(a[3],!1));if(a[0]instanceof n)return b=new n(a[0]),b.geometry=e.buffer(b.geometry,a[1],k.convertLinearUnitsToCode(a[2]),f.defaultUndefined(a[3],!1)),b;throw Error("Buffer cannot accept this parameter type");})};b.sumarea=function(b,
c){return d(b,c,function(b,c,a){f.pcCheck(a,1,2);b=k.convertSquareUnitsToCode(a[1]);if(f.isArray(a[0]))return p(a[0],0,0,b);if(a[0]instanceof h)return e.planarArea(a[0],b);if(f.isImmutableArray(a[0]))return p(a[0].toArray(),0,0,b);if(a[0]instanceof n)return null===a[0].geometry?0:e.planarArea(a[0].geometry,b);if(null===a[0])return 0;throw Error("Invalid Parameters for Area");})};b.area=b.sumarea;b.sumlength=function(b,c){return d(b,c,function(b,c,a){f.pcCheck(a,1,2);b=k.convertLinearUnitsToCode(a[1]);
if(f.isArray(a[0]))return q(a[0],0,0,b);if(f.isImmutableArray(a[0]))return q(a[0].toArray(),0,0,b);if(a[0]instanceof h)return e.planarLength(a[0],b);if(a[0]instanceof n)return null===a[0].geometry?0:e.planarLength(a[0].geometry,b);if(null===a[0])return 0;throw Error("Invalid Parameters for Length");})};b.length=b.sumlength;b.intersects=function(b,c){return this.spatialrelationFunction(b,c,"esriSpatialRelIntersects",d)};b.envelopeintersects=function(b,c){return this.spatialrelationFunction(b,c,"esriSpatialRelEnvelopeIntersects",
d)};b.contains=function(b,c){return this.spatialrelationFunction(b,c,"esriSpatialRelContains",d)};b.overlaps=function(b,c){return this.spatialrelationFunction(b,c,"esriSpatialRelOverlaps",d)};b.within=function(b,c){return this.spatialrelationFunction(b,c,"esriSpatialRelWithin",d)};b.touches=function(b,c){return this.spatialrelationFunction(b,c,"esriSpatialRelTouches",d)};b.crosses=function(b,c){return this.spatialrelationFunction(b,c,"esriSpatialRelCrosses",d)};b.equals=function(b,c){return d(b,c,
function(b,c,a){f.pcCheck(a,2,2);return a[0]===a[1]?!0:a[0]instanceof h&&a[1]instanceof h?e.equals(a[0],a[1]):f.isDate(a[0])&&f.isDate(a[1])?a[0].getTime()===a[1].getTime():!1})};b.intersection=function(b,c){return this.spatialTopoFunction(b,c,"intersection",d)};b.difference=function(b,c){return this.spatialTopoFunction(b,c,"difference",d)};b.symmetricdifference=function(b,c){return this.spatialTopoFunction(b,c,"symmetricdifference",d)};b.union=function(b,c){return this.spatialTopoFunction(b,c,"union",
d)}}});