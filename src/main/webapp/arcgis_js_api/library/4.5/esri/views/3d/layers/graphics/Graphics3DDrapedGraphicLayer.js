// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.5/esri/copyright.txt for details.
//>>built
define("require exports ../../../../core/tsSupport/extendsHelper ../../../../core/tsSupport/generatorHelper ../../../../core/tsSupport/awaiterHelper ./ElevationInfo ../../support/aaBoundingBox ../../support/aaBoundingRect ../../webgl-engine/Stage".split(" "),function(n,v,w,q,r,t,g,u,h){n=function(){function c(a,b,e,c,f,d){this.needsElevationUpdates=!1;this.graphics3DSymbolLayer=a;this.renderGeometries=b;this.uniqueMaterials=e;this.uniqueTextures=c;this.boundingBox=f;this.elevationInfo=new t(d);this.stage=
null;this._visible=!1}c.prototype.initialize=function(a,b){this.stage=b;if(this.uniqueMaterials)for(a=0;a<this.uniqueMaterials.length;a++)b.add(h.ModelContentType.MATERIAL,this.uniqueMaterials[a]);if(this.uniqueTextures)for(a=0;a<this.uniqueTextures.length;a++)b.add(h.ModelContentType.TEXTURE,this.uniqueTextures[a])};c.prototype.isDraped=function(){return!0};c.prototype.setVisibility=function(a){if(null!=this.stage)return this._visible!==a?((this._visible=a)?this.stage.getTextureGraphicsRenderer().addRenderGeometries(this.renderGeometries):
this.stage.getTextureGraphicsRenderer().removeRenderGeometries(this.renderGeometries),!0):!1};c.prototype.destroy=function(){if(this.stage){var a=this.stage;this._visible&&a.getTextureGraphicsRenderer().removeRenderGeometries(this.renderGeometries);this._visible=!1;if(this.uniqueMaterials)for(var b=0;b<this.uniqueMaterials.length;b++)a.remove(h.ModelContentType.MATERIAL,this.uniqueMaterials[b].getId());if(this.uniqueTextures)for(b=0;b<this.uniqueTextures.length;b++)a.remove(h.ModelContentType.TEXTURE,
this.uniqueTextures[b].getId());this.stage=null}};c.prototype.alignWithElevation=function(a,b,e){};c.prototype.setDrawOrder=function(a,b,e){var c=this;this.uniqueMaterials&&this.uniqueMaterials.forEach(function(f){f.setRenderPriority(a);c._visible&&(b[f.getId()]=!0)})};c.prototype.getBSRadius=function(){return this.renderGeometries.reduce(function(a,b){return Math.max(a,b.bsRadius)},0)};c.prototype.getCenterObjectSpace=function(){return[0,0,0]};c.prototype.addHighlight=function(a,b){var c=this.stage.getTextureGraphicsRenderer();
this.renderGeometries.forEach(function(e){var f=c.addRenderGeometryHighlight(e,b);a.addRenderGeometry(e,c,f)})};c.prototype.removeHighlight=function(a){this.renderGeometries.forEach(function(b){a.removeRenderGeometry(b)})};c.prototype.getProjectedBoundingBox=function(a,b,c,d){return r(this,void 0,void 0,function(){var f,e,k;return q(this,function(l){switch(l.label){case 0:g.set(c,g.NEGATIVE_INFINITY);for(f=0;f<this.renderGeometries.length;f++)e=this.renderGeometries[f],this._getRenderGeometryProjectedBoundingRect(e,
p,a,d),g.expand(c,p);if(!b)return[3,5];g.center(c,m);k=void 0;l.label=1;case 1:return l.trys.push([1,3,,4]),[4,b.queryElevation(m[0],m[1])];case 2:return k=l.sent(),[3,4];case 3:return l.sent(),k=null,[3,4];case 4:null!=k&&(c[2]=Math.min(c[2],k),c[5]=Math.max(c[5],k)),l.label=5;case 5:return[2,c]}})})};c.prototype._getRenderGeometryProjectedBoundingRect=function(a,c,e,h){if(this.boundingBox)g.set(d,this.boundingBox);else{var b=a.center;a=a.bsRadius;d[0]=b[0]-a;d[1]=b[1]-a;d[2]=b[2]-a;d[3]=b[0]+a;
d[4]=b[1]+a;d[5]=b[2]+a}e(d,0,2);this.calculateRelativeScreenBounds&&h.push({location:g.center(d),screenSpaceBoundingRect:this.calculateRelativeScreenBounds()});return g.toRect(d,c)};return c}();var p=u.create(),d=[0,0,0,0,0,0],m=[0,0,0];return n});