// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
define("esri/layers/ArcGISTiledMapServiceLayer","dojo/_base/kernel dojo/_base/declare dojo/_base/lang dojo/_base/array dojo/_base/json dojo/has dojo/io-query ../kernel ../urlUtils ../SpatialReference ./TiledMapServiceLayer ./ArcGISMapServiceLayer ./TileInfo ./TimeInfo ./TileMap".split(" "),function(m,e,f,y,n,p,k,q,h,r,t,u,v,w,x){e=e([t,u],{declaredClass:"esri.layers.ArcGISTiledMapServiceLayer",_agolAttrs:"Canvas/World_Dark_Gray_Base Canvas/World_Dark_Gray_Reference Canvas/World_Light_Gray_Base Canvas/World_Light_Gray_Reference Elevation/World_Hillshade Ocean/World_Ocean_Base Ocean/World_Ocean_Reference Ocean_Basemap Reference/World_Boundaries_and_Places Reference/World_Boundaries_and_Places_Alternate Reference/World_Transportation World_Imagery World_Street_Map World_Topo_Map".split(" "),
_isReference:!1,_referenceLayers:"Canvas/World_Dark_Gray_Reference Canvas/World_Light_Gray_Reference Ocean/World_Ocean_Reference Reference/World_Boundaries_and_Places Reference/World_Boundaries_and_Places_Alternate Reference/World_Reference_Overlay Reference/World_Transportation".split(" "),constructor:function(a,c){c&&(c.roundrobin&&(m.deprecated(this.declaredClass+" : Constructor option 'roundrobin' deprecated. Use option 'tileServers'."),c.tileServers=c.roundrobin),this._setTileServers(c.tileServers),
this._loadCallback=c.loadCallback);this._params=f.mixin({},this._url.query);this._initLayer=f.hitch(this,this._initLayer);var b=c&&c.resourceInfo;b?this._initLayer(b):(this._load=f.hitch(this,this._load),this._load())},_TILE_FORMATS:{PNG:"png",PNG8:"png",PNG24:"png",PNG32:"png",JPG:"jpg",JPEG:"jpg",GIF:"gif"},_setTileServers:function(a){if(a&&0<a.length){this.tileServers=a;var c,b=a.length;for(c=0;c<b;c++)a[c]=h.urlToObject(a[c]).path}},_initLayer:function(a,c){this.inherited(arguments);this.resourceInfo=
n.toJson(a);this.tileInfo=new v(a.tileInfo);!1!==this.resampling&&(a.capabilities&&-1<a.capabilities.indexOf("Tilemap")?(this.resampling=!0,this.tileMap=new x(this)):this.resampling=!!a.resampling);!this.spatialReference&&this.tileInfo.spatialReference&&(this.spatialReference=new r(this.tileInfo.spatialReference.toJson()));this.isPNG32="PNG24"===this.tileInfo.format||"PNG32"===this.tileInfo.format;a.timeInfo&&(this.timeInfo=new w(a.timeInfo));a.mensurationCapabilities&&(this.mensurationCapabilities=
a.mensurationCapabilities);var b=this._url.path,l=this._loadCallback,e=h.getProtocolForWebResource(!0),d=b.match(/^https?\:\/\/(server|services)\.arcgisonline\.com\/arcgis\/rest\/services\/([^\/]+(\/[^\/]+)*)\/mapserver/i),d=d&&d[2];if(!this.tileServers)if(a.tileServers)this._setTileServers(a.tileServers);else{var g=-1!==b.search(/^https?\:\/\/server\.arcgisonline\.com/i),f=-1!==b.search(/^https?\:\/\/services\.arcgisonline\.com/i);(g||f)&&this._setTileServers([b,b.replace(g?/server\.arcgisonline/i:
/services\.arcgisonline/i,g?"services.arcgisonline":"server.arcgisonline")])}if(d){d=d.toLowerCase();for(b=0;b<this._agolAttrs.length;b++)if(g=this._agolAttrs[b],g.toLowerCase()===d){this.hasAttributionData=!0;this.attributionDataUrl=this.attributionDataUrl||e+"//static.arcgis.com/attribution/"+g;break}for(b=0;b<this._referenceLayers.length;b++)if(g=this._referenceLayers[b],g.toLowerCase()===d){this._isReference=!0;break}}this.loaded=!0;this.onLoad(this);l&&(delete this._loadCallback,l(this))},getTileUrl:function(a,
c,b){var e=this.tileServers,f=this._getToken(),d=this._url.query;a=(e?e[c%e.length]:this._url.path)+"/tile/"+a+"/"+c+"/"+b;a=h.upgradeToHTTPS(a);this.resampling&&!this.tileMap&&(a+="?blankTile\x3dfalse");d&&(a=this.resampling&&!this.tileMap?a+("\x26"+k.objectToQuery(d)):a+("?"+k.objectToQuery(d)));!f||d&&d.token||(a+=(-1===a.indexOf("?")?"?":"\x26")+"token\x3d"+f);a=this.addTimestampToURL(a);return h.addProxy(a)}});p("extend-esri")&&f.setObject("layers.ArcGISTiledMapServiceLayer",e,q);return e});