function lonLat2Mercator(lonLat) {
    var x = lonLat.X * 20037508.34 / 180;
    var y = Math.log(Math.tan((90 + lonLat.Y) * Math.PI / 360)) / (Math.PI / 180);
    y = y * 20037508.34 / 180;

    var mercator = {
        x: x,
        y: y
    }
    return mercator;
} //墨卡托转经纬度
function Mercator2lonLat(mercator) {

    var x = mercator.X / 20037508.34 * 180;
    var y = mercator.Y / 20037508.34 * 180;
    y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
    var lonLat = {
        x: x,
        y: y
    }
    return lonLat;
}