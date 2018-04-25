// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.21/esri/copyright.txt for details.
//>>built
define("esri/layers/TileMap","dojo/_base/declare dojo/_base/lang dojo/_base/array dojo/Deferred dojo/io-query ../request ../urlUtils ../sniff".split(" "),function(l,k,m,n,p,q,u,r){var t=r("esri-iphone");return l(null,{declaredClass:"esri.layers.TileMap",constructor:function(a){this.layer=a;this._tileMaps={}},getTile:function(a,b,c,d,f){a={id:d,level:a,row:b,col:c};b=this._getResamplingBudget();0<b?this._process({tile:a,requestedTile:a,callback:f,resamplingBudget:b}):(f||this.callback).call(this,a,
a)},statusOf:function(a,b,c){var d=this._getResamplingBudget();a={level:a,row:b,col:c};if(0===d)return 1;for(;0<=d;){b=this._tileToTileMap(a);if(!this._tileMaps[b.uid])return-1;b=this._tileMaps[b.uid];if(!b.promise.isFulfilled())return-1;if(this._isTileAvailable(a,b))return 1;a=this._parentTile(a);if(!a)break;d--}return 0},style:function(a,b){if(a.level!==b.level||a.row!==b.row||a.col!==b.col){for(var c=this.layer.tileInfo,d=c.lods,f=c.cols,c=c.rows,e,g,h=d.length-1;!e||!g;)e||d[h].level!==a.level||
(e=d[h]),g||d[h].level!==b.level||(g=d[h]),h--;e=Math.round(e.resolution/g.resolution);g=b.col%e*-1*f;d=b.row%e*-1*c;e={width:f*e+"px",height:c*e+"px",margin:d+"px 0 0 "+g+"px","will-change":"transform"};t&&(g=0===g?0:-1*g,d=0===d?0:-1*d,e.clip="rect("+d+"px,"+(g+f)+"px,"+(d+c)+"px,"+g+"px)");return e}},_process:function(a){var b=a.tile,c=this._tileToTileMap(b),d=this._parentTile(b);this._getTileMap(c).then(k.hitch(this,function(f){c=f;this._isTileAvailable(b,c)?(a.callback||this.callback).call(this,
b,a.requestedTile):0<a.resamplingBudget&&d?(a.resamplingBudget--,a.tile=d,this._process(a)):(a.callback||this.callback).call(this,a.requestedTile,a.requestedTile)}),k.hitch(this,function(){(a.callback||this.callback).call(this,a.requestedTile,a.requestedTile)}))},_getTileMap:function(a){var b,c,d,f,e=null;this._tileMaps[a.uid]?(a=this._tileMaps[a.uid],b=a.promise):(this._tileMaps[a.uid]=a,c=new n,q({url:this._getTileMapUrl(a.level,a.row,a.col),handleAs:"json",callbackParamName:"callback",timeout:3E3,
load:function(b){k.mixin(a,b);if(a.data&&0<a.data.length){f=a.data.length;if(1===f)e=a.data[0];else for(e=a.data[0],d=1;d<f;d++)if(a.data[d]!==e){e=null;break}null!==e&&(delete a.data,a.value=e)}c.resolve(a)},error:function(a){c.reject()}}),b=a.promise=c.promise);return b},_parentTile:function(a){var b=this.layer.tileInfo.lods,c,d,f=null;m.some(b,function(b,f){return a.level===b.level?(c=b,d=f,!0):!1});0<d&&(b=b[d-1],f={id:a.id,level:b.level,row:Math.floor(a.row*c.resolution/b.resolution+.01),col:Math.floor(a.col*
c.resolution/b.resolution+.01)});return f},_tileToTileMap:function(a){var b=8*Math.floor(a.row/8),c=8*Math.floor(a.col/8);return{uid:a.level+"_"+b+"_"+c,level:a.level,row:b,col:c}},_isTileAvailable:function(a,b){var c,d;b.valid?void 0!==b.value?c=b.value:(c=b.location.left,d=b.location.top,c=(a.row-d)*b.location.width+(a.col-c),c=c<b.data.length?b.data[c]:0):c=0;return c},_getTileMapUrl:function(a,b,c){var d=this.layer,f=d.tileServers,e=d._getToken(),g=d._url.query;a=(f?f[b%f.length]:d._url.path)+
"/tilemap/"+a+"/"+b+"/"+c+"/8/8";g&&(a+="?"+p.objectToQuery(g));!e||g&&g.token||(a+=(-1===a.indexOf("?")?"?":"\x26")+"token\x3d"+e);return a=d.addTimestampToURL(a)},_getResamplingBudget:function(){var a=this.layer,b=0;a.resampling&&(b=a._resamplingTolerance,null===b||void 0===b)&&(b=a.tileInfo.lods.length);return b}})});