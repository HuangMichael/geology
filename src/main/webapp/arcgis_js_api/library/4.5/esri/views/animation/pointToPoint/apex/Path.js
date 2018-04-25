// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.5/esri/copyright.txt for details.
//>>built
define("require exports ../../../../core/tsSupport/extendsHelper ../../easing ../Path ../Segment ./planning".split(" "),function(d,h,m,l,n,k,p){Object.defineProperty(h,"__esModule",{value:!0});d=function(d){function e(b,c){var a=d.call(this)||this;a._preallocSegments=[new k.default,new k.default,new k.default];a.update(b,c);return a}m(e,d);e.prototype.update=function(b,c){if(b){this.definition?this.definition.copyFrom(b):this.definition=b.clone();var a=null;c&&c.apex&&(a=p.optimalDistance(b,c.apex));
this.segments.length=0;this._descensionSegment=this._ascensionSegment=null;null==a?this._updateWithoutApex():this._updateWithApex(a,c.apex)}};e.prototype.segmentInterpolateComponentsAt=function(b,c,a){a=b.interpolateComponentsAt(c,a);b===this._ascensionSegment?a.zoom=l.outQuad(a.zoom):b===this._descensionSegment&&(a.zoom=l.inQuad(a.zoom));return a};e.prototype._updateWithApex=function(b,c){var a=this._preallocSegments,g=a[0],f=a[1],a=a[2],d=null!=c.ascensionFactor?c.ascensionFactor:.5;c=Math.min(1-
d,null!=c.ascensionFactor?c.descensionFactor:.5);var e=1-d-c;g.definition?g.definition.copyFrom(this.definition):g.definition=this.definition.clone();g.definition.compared.targetZoom=b;g.definition.compared.pan=this.definition.compared.pan*d;g.definition.compared.rotate=this.definition.compared.rotate*d;g.update();this._ascensionSegment=g;this.segments.push(g);0<e&&(f.definition?f.definition.copyFrom(this.definition):f.definition=this.definition.clone(),f.definition.copyFrom(this.definition),f.definition.compared.sourceZoom=
b,f.definition.compared.targetZoom=b,f.definition.compared.pan=this.definition.compared.pan*e,f.definition.compared.rotate=this.definition.compared.rotate*e,f.update(),this.segments.push(f));a.definition?a.definition.copyFrom(this.definition):a.definition=this.definition.clone();a.definition.compared.sourceZoom=b;a.definition.compared.pan=this.definition.compared.pan*c;a.definition.compared.rotate=this.definition.compared.rotate*c;a.update();this._descensionSegment=a;this.segments.push(a)};e.prototype._updateWithoutApex=
function(){var b=this._preallocSegments[0];b.update(this.definition);this.segments.push(b)};return e}(n.default);h.Path=d;h.default=d});