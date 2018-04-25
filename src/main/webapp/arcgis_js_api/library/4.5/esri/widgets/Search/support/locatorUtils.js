// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.5/esri/copyright.txt for details.
//>>built
define(["require","exports","../../../Graphic","../../../core/promiseUtils","./geometryUtils"],function(z,k,v,m,n){function w(a){var b=a.source,d=a.spatialReference,c=a.location,e=a.distance,p=a.sourceIndex,h=a.view;a=b.locator;var g=b.zoomScale,f=h&&h.scale;d&&(a.outSpatialReference=d);return a.locationToAddress(c,e).then(function(a){return q([a],p,h,f,g)})}function r(a,b){var d=a.filter,c=a.searchExtent;a=a.withinViewEnabled;var e=b&&b.extent;return(b=n.createExtentFromGeometry(d&&d.geometry,b,
b&&b.scale))||c||(a&&e?e:void 0)}function x(a){var b=a.source,d=a.suggestResult,c=a.spatialReference,e=a.view,p=a.maxResults,h=a.sourceIndex,g=b&&b.zoomScale;a=d.text.trim();if(!a)return m.resolve();a=""+(!d.key&&b.prefix?b.prefix:"")+a+(!d.key&&b.suffix?b.suffix:"");var f={},l=b.locator,t=e&&e.scale,k=r(b,e);if(!l)return m.resolve();b.categories&&(f.categories=b.categories);c&&(l.outSpatialReference=c);if(c=u(e,b,t))f.location=c.location,f.distance=c.distance;f.maxLocations=p;k&&(f.searchExtent=
k);b.countryCode&&(f.countryCode=b.countryCode);d.key&&(f.magicKey=d.key);f.address={};f.address[b.singleLineFieldName||"Single Line Input"]=a;b.outFields&&(f.outFields=b.outFields);return l.addressToLocations(f).then(function(a){return q(a,h,e,t,g)})}function y(a,b){return a.map(function(a){return{text:a.text,key:a.magicKey,sourceIndex:b}})}function q(a,b,d,c,e){return a.map(function(a){var h=a.extent,g=a.location,f=a.address;a=new v({geometry:g,attributes:a.attributes});h=n.createExtentFromGeometry(h||
g,d,c);h=e?n.scaleExtent(h,d,e):h;g=g?g.x+","+g.y:null;return{extent:h,feature:a,name:f||g,sourceIndex:b}})}function u(a,b,d){var c=b.localSearchOptions;if(a&&c&&c.hasOwnProperty("distance")&&c.hasOwnProperty("minScale")&&(b=c.minScale,c=c.distance,!b||d&&d<=b))return{location:a.get("extent.center"),distance:c}}Object.defineProperty(k,"__esModule",{value:!0});k.getResults=function(a){return a.location?w(a):x(a)};k.getSuggestions=function(a){var b=a.source,d=a.spatialReference,c=a.view,e=a.suggestTerm,
k=a.maxSuggestions,h=a.sourceIndex;a={};var g=b.locator,f=c&&c.scale,l=r(b,c);if(!g)return m.resolve();b.categories&&(a.categories=b.categories);g.outSpatialReference=d;if(d=u(c,b,f))a.location=d.location,a.distance=d.distance;e=e.trim();if(!e)return m.resolve();d=b.prefix;c=b.suffix;a.text=""+(void 0===d?"":d)+e+(void 0===c?"":c);l&&(a.searchExtent=l);a.maxSuggestions=k;b.countryCode&&(a.countryCode=b.countryCode);return g.suggestLocations(a).then(function(a){return y(a,h)})}});