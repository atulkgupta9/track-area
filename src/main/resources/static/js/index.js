
$(document).ready(function () {
  var set1 = {};
  let url = "http://localhost:6002/point/",
    method = "GET",
    successFx = function (data) {
      var p = data.map(point => point.x);
      var q = data.map(point => point.y);
      set1['x'] = p;
      set1['y'] = q;
      set1['mode'] = 'markers';
      set1['name'] = 'Points Covered';
      set1['marker'] = {
        color: 'rgb(0, 0, 255)',
        size: 4
      }


    };
  doAjax(url, method, undefined, successFx);
  console.log("chamda");
  var set2 = {};
  url = "http://localhost:6002/point/hull",
    method = "GET",
    successFx = function (data) {
      console.log("in hull", data);
      var c = data.map(point => point.x);
      c.push(data[0].x);
      var d = data.map(point => point.y);
      d.push(data[0].y);
       console.log(data[0])
      set2['x'] = c;
      set2['y'] = d;
      set2['mode'] = 'lines+markers';
      set2['marker'] = {
        color: 'rgb(255, 0, 0)',
        size: 8
      };
      set2['name'] = "Convex Polygon that enclosed these points";
      set2['line'] = {
        color: 'rgb(128, 0, 128)',
        width: 2
      };
      var pos = [set1, set2];
      var layout = {
        title: 'Points convered by device'
      };
      Plotly.newPlot('myDiv', pos, layout, { showSendToCloud: false })
    };

  doAjax(url, method, undefined, successFx);
  console.log("set ", set1);
  console.log("set 2 ", set2);

});