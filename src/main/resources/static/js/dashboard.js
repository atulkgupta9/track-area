$(document).ready(function () {
    let chamda = window.location.origin + "/";

    const getHull = (actual, data) => {

        let set1 = {};
        var p = actual.map(point => point.x);
        var q = actual.map(point => point.y);
        set1['x'] = p;
        set1['y'] = q;
        set1['mode'] = 'markers';
        set1['name'] = 'Points Covered';
        set1['marker'] = {
            color: 'rgb(0, 0, 255)',
            size: 4
        };

        var set2 = {};
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
        var d3 = Plotly.d3;
        var img_jpg= d3.select('#jpg-export');

        Plotly.newPlot('myDiv', pos, layout, { showSendToCloud: false }).then(
            function(gd)
            {
                Plotly.toImage(gd,{height:300,width:300})
                    .then(
                        function(url)
                        {
                            img_jpg.attr("src", url);
                            return Plotly.toImage(gd,{format:'jpeg',height:400,width:400});
                        }
                    )
            });
    };

    const readUploadedFileAsText = (inputFile) => {
        const temporaryFileReader = new FileReader();

        return new Promise((resolve, reject) => {

            temporaryFileReader.onload = () => {
                resolve(temporaryFileReader.result);
            };
            temporaryFileReader.readAsText(inputFile);
        });
    }

    const handleUpload = async (file) => {

        try {
            const fileContents = await readUploadedFileAsText(file)
            console.log("filecontens", fileContents);
            let arr = fileContents.split("\n");
            console.log("arr ", arr);
            let data = [];
            for (let i = 0; i < arr.length; i++) {
                console.log("line ", i + 1, arr[i]);
                let point = arr[i].split(",");
                let json = {
                    x: point[6],
                    y: point[4],
                }
                data.push(json);
            }
            let rbd = {
                    "points": data
                },
                url = chamda + "api/auth/add-points",
                successFx = function (ramadhir) {
                    console.log(ramadhir);
                    let set2 = getHull(data, ramadhir['polygon']);
                    console.log("set 2" , set2);
                    $("#area").text(ramadhir['area']);
                    $("#area-label").removeClass("d-none");
                };
            doAjax(url, "POST", rbd, successFx);
        } catch (e) {
            console.warn(e.message)
        }
    }

    $("#import").on('submit', function () {
        handleUpload($("input[type=file]").prop('files')[0]);
        return false;
    });

    $("#gpgga-button").on('click', function (event) {
        url = chamda + "api/user/point/hull/geo",
            successFx = function (ramadhir) {
                console.log(ramadhir);
                let set2 = getHull(ramadhir['polygon'], ramadhir['polygon']);
                console.log("set 2" , set2);
                $("#area-2").text(ramadhir['area']);
                $("#area-label-2").removeClass("d-none");
            };
        doAjax(url, "GET", undefined, successFx);
    })

// $("#import").on('submit', function(){
// // var form = new FormData();
// // form.append("file", $("input[type=file]")[0].files[0]);
// // console.log(form);
// var fileReader = new FileReader();
// fileReader.readAsText($("input[type=file]").prop('files')[0]);
// console.log(fileReader.result);
// console.log(fileReader.result[0]);
// let arr = fileReader.result.split("\n");
// console.log("arr ", arr);
// for(let i=0; i < arr.length; i++){
//     console.log("line ", i+1, arr[i]);
// }
//     return false;
// });

})
;