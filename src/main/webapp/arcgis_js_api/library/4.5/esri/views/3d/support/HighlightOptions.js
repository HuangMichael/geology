// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.5/esri/copyright.txt for details.
//>>built
define("require exports ../../../core/tsSupport/declareExtendsHelper ../../../core/tsSupport/decorateHelper ../../../core/accessorSupport/decorators ../../../core/Accessor ../../../Color".split(" "),function(k,l,g,d,c,h,e){return function(f){function b(){var a=null!==f&&f.apply(this,arguments)||this;a.color=new e([0,255,255]);a.haloOpacity=1;a.fillOpacity=.25;return a}g(b,f);b.toEngineOptions=function(a){return{color:new Float32Array(e.toUnitRGBA(a.color)),haloOpacity:a.haloOpacity,haloOpacityOccluded:.25*
a.haloOpacity,fillOpacity:a.fillOpacity,fillOpacityOccluded:.25*a.fillOpacity}};d([c.property({type:e})],b.prototype,"color",void 0);d([c.property()],b.prototype,"haloOpacity",void 0);d([c.property()],b.prototype,"fillOpacity",void 0);return b=d([c.subclass()],b)}(c.declared(h))});