// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.5/esri/copyright.txt for details.
//>>built
define("require exports dojo/has ./PerformanceTimer ./gl-matrix ./Object3D".split(" "),function(m,t,u,v,c,n){var q=c.vec3d.create(),r=c.vec3d.create();m=function(){function a(k,b,a,e,h,f,g,d){void 0===d&&(d=!1);this.dir=c.vec3d.create();this.normalDir=null;this.minResult=new p;this.maxResult=new p;this.transform=c.mat4d.create();this._transformInverse=new l({value:this.transform},c.mat4d.inverse,c.mat4d.create);this._transformInverseTranspose=new l(this._transformInverse,c.mat4d.transpose,c.mat4d.create);
this._transformTranspose=new l({value:this.transform},c.mat4d.transpose,c.mat4d.create);this._transformInverseRotation=new l({value:this.transform},c.mat4d.toInverseMat3,c.mat3d.create);this.enableTerrain=this.enableHUDSelection=!0;this.enableInvisibleTerrain=!1;this.enableBackfacesTerrain=!0;this.performanceInfo={queryDuration:0,numObjectsTested:0};this.viewingMode=k||"global";this.intersectObject=this.intersectObject.bind(this);this.init(b,a,e,h,f,g,d)}a.prototype.init=function(k,b,a,e,h,f,g){b&&
a&&c.vec3d.subtract(a,b,this.dir);this.minResult.init(b,a);this.maxResult.init(b,a);this.numObjectsTested=0;this.point=e;this.camera=h;this.isSelection=g;this.layers=k;this.p0=b;this.p1=a;this.hudResults=[];null==f&&(f=1E-5);this.tolerance=f;if(this.layers)for(k=0;k<this.layers.length;++k)if(b=this.layers[k],a=b.getSpatialQueryAccelerator?b.getSpatialQueryAccelerator():void 0)a.forEachAlongRay(this.p0,this.dir,this.intersectObject);else for(b=b.getObjects(),a=0;a<b.length;++a)this.intersectObject(b[a])};
Object.defineProperty(a.prototype,"transformInverse",{get:function(){return this._transformInverse.value},enumerable:!0,configurable:!0});Object.defineProperty(a.prototype,"transformInverseTranspose",{get:function(){return this._transformInverseTranspose.value},enumerable:!0,configurable:!0});Object.defineProperty(a.prototype,"transformInverseRotation",{get:function(){return this._transformInverseRotation.value},enumerable:!0,configurable:!0});Object.defineProperty(a.prototype,"transformTranspose",
{get:function(){return this._transformTranspose.value},enumerable:!0,configurable:!0});a.prototype.getDirection=function(){this.normalDir||(this.normalDir=c.vec3d.create(),c.vec3d.normalize(this.dir,this.normalDir));return this.normalDir};a.prototype.intersectObject=function(a){var b=this;this.numObjectsTested++;for(var k=a.getId(),e=a.getGeometryRecords(),h=a.objectTransformation,f,g=0;g<e.length;g++){var d=e[g],l=d.geometry,m=d.materials,n=d.instanceParameters;f=l.getId();c.mat4d.set(h,this.transform);
c.mat4d.multiply(this.transform,d.getShaderTransformation());this._transformInverse.invalidate();this._transformInverseTranspose.invalidate();this._transformTranspose.invalidate();this._transformInverseRotation.invalidate();c.mat4d.multiplyVec3(this.transformInverse,this.p0,q);c.mat4d.multiplyVec3(this.transformInverse,this.p1,r);m[0].intersect(l,n,this.transform,this,q,r,function(c,d,g,e,h,l){0<=c&&(h?(h=new p,h.set(a,k,c,d,e,l,f,g),b.hudResults.push(h)):((null==b.minResult.priority||e>=b.minResult.priority)&&
(null==b.minResult.dist||c<b.minResult.dist)&&b.minResult.set(a,k,c,d,e,null,f,g),(null==b.maxResult.priority||e>=b.maxResult.priority)&&(null==b.maxResult.dist||c>b.maxResult.dist)&&b.maxResult.set(a,k,c,d,e,null,f,g)))},d.customTransformation)}};a.prototype.getMinResult=function(){return this.minResult};a.prototype.getMaxResult=function(){return this.maxResult};a.prototype.getHudResults=function(){return this.hudResults};a.DEFAULT_TOLERANCE=1E-5;return a}();var p=function(){function a(a,b){this.normal=
c.vec3d.create();this.init(a,b)}a.prototype.getIntersectionPoint=function(a){if(null==this.dist)return!1;c.vec3d.lerp(this.p0,this.p1,this.dist,a);return!0};a.prototype.set=function(a,b,l,e,h,f,g,d){this.dist=l;c.vec3d.set(e,this.normal);this.targetType=a?a instanceof n?"StageObject":"StagePoint":"None";this.target=a;this.name=b;this.priority=h;this.center=f?c.vec3d.create(f):null;this.geometryId=g;this.triangleNr=d};a.prototype.setIntersector=function(a){this.intersector=a};a.prototype.init=function(a,
b){this.dist=void 0;this.targetType="None";this.priority=this.name=this.target=void 0;this.triangleNr=this.geometryId=this.center=null;this.intersector="stage";this.p0=a;this.p1=b};return a}(),l=function(){function a(a,b,c){this.original=a;this.update=b;this.dirty=!0;this.transform=c()}a.prototype.invalidate=function(){this.dirty=!0};Object.defineProperty(a.prototype,"value",{get:function(){this.dirty&&(this.update(this.original.value,this.transform),this.dirty=!1);return this.transform},enumerable:!0,
configurable:!0});return a}();return m});