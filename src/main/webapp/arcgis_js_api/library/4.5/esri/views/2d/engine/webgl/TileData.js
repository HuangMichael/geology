// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.5/esri/copyright.txt for details.
//>>built
define("require exports ../../../../core/ObjectPool ./TileDisplayData ./TileBufferData ./WGLDisplayRecord ./enums".split(" "),function(u,v,t,m,q,n,h){return function(){function e(){this.tileBufferData=this.tileDisplayData=null}e.prototype.release=function(){m.pool.release(this.tileDisplayData);this.tileDisplayData=null;q.pool.release(this.tileBufferData);this.tileBufferData=null};e.prototype.serialize=function(){var b=this.tileBufferData.serialize(),c=this.tileBufferData.getBuffers(),a=m.serialize(this.tileDisplayData,
null,0),a=new Uint8Array(a);m.serialize(this.tileDisplayData,a,0);c.push(a.buffer);return{data:{displayData:a.buffer,bufferData:b},buffers:c}};e.deserialize=function(b){var c={tileDisplayData:null};m.deserialize(c,new Uint8Array(b.displayData),0);c=c.tileDisplayData;b=q.deserialize(b.bufferData);var a=new e;a.tileDisplayData=c;a.tileBufferData=b;return a};e.prototype.deserializeMeshData=function(){var b=this;this.tileDisplayData.displayObjectRegistry.forEach(function(c,a){c=0;for(a=b.tileDisplayData.displayObjectRegistry.get(a).displayRecords;c<
a.length;c++){var d=a[c],e=d.geometryType;d.readMeshDataFromBuffers(b.tileBufferData.geometries[e].vertexBuffer,b.tileBufferData.geometries[e].indexBuffer)}})};e.bind=function(b,c){var a=new e;a.tileDisplayData=b;a.tileBufferData=c;return a};e.create=function(b,c){var a=new e;a.tileDisplayData=new m;for(var d=[0,0,0,0],r=[0,0,0,0],k=0;k<b.length;k++){var l=b[k];a.tileDisplayData.displayObjectRegistry.set(l.id,l);for(var f=0,l=l.displayRecords;f<l.length;f++){var g=l[f];switch(g.geometryType){case h.WGLGeometryType.FILL:a.tileDisplayData.fillDisplayRecords.push(g);
break;case h.WGLGeometryType.LINE:a.tileDisplayData.lineDisplayRecords.push(g);break;case h.WGLGeometryType.MARKER:a.tileDisplayData.iconDisplayRecords.push(g);break;case h.WGLGeometryType.TEXT:a.tileDisplayData.textDisplayRecords.push(g)}d[g.geometryType]+=g.meshData.vertexCount;r[g.geometryType]+=g.meshData.indexData.length}}b=function(a,b){return a.sortKey-b.sortKey};a.tileDisplayData.fillDisplayRecords.sort(b);a.tileDisplayData.lineDisplayRecords.sort(b);a.tileDisplayData.iconDisplayRecords.sort(b);
a.tileDisplayData.textDisplayRecords.sort(b);c=[c.fill||{},c.line||{},c.icon||{},c.text||{}];b=new q;for(f=0;4>f;f++){b.geometries[f].indexBuffer=new Uint32Array(r[f]);b.geometries[f].vertexBuffer={};var k=c[f],p;for(p in k)b.geometries[f].vertexBuffer[p]={data:new Uint32Array(d[f]*k[p]/4),stride:k[p]}}a.tileBufferData=b;(d=a.tileBufferData.geometries[h.WGLGeometryType.FILL])&&n.writeAllMeshDataToBuffers(a.tileDisplayData.fillDisplayRecords,d.vertexBuffer,d.indexBuffer);(d=a.tileBufferData.geometries[h.WGLGeometryType.LINE])&&
n.writeAllMeshDataToBuffers(a.tileDisplayData.lineDisplayRecords,d.vertexBuffer,d.indexBuffer);(d=a.tileBufferData.geometries[h.WGLGeometryType.MARKER])&&n.writeAllMeshDataToBuffers(a.tileDisplayData.iconDisplayRecords,d.vertexBuffer,d.indexBuffer);(d=a.tileBufferData.geometries[h.WGLGeometryType.TEXT])&&n.writeAllMeshDataToBuffers(a.tileDisplayData.textDisplayRecords,d.vertexBuffer,d.indexBuffer);a.tileDisplayData.displayList.addToList(a.tileDisplayData.fillDisplayRecords);a.tileDisplayData.displayList.addToList(a.tileDisplayData.lineDisplayRecords);
a.tileDisplayData.displayList.addToList(a.tileDisplayData.iconDisplayRecords);a.tileDisplayData.displayList.addToList(a.tileDisplayData.textDisplayRecords);return a};e.pool=new t(e);return e}()});