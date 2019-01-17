$(document).ready(function () {
    getCarBodies();
    getCarEngines();
    getCarTransmissions();
});

function getCarBodies() {
    var bodies = document.getElementById("body");
    $.ajax({
        url: "body",
        type: "get",
        dataType: "json",
        success: function (data) {
            console.log(data);
            bodies.innerHTML = "";
            var query = "";
            for (var i = 0; i < data.length; i++) {
                query += "<option name='body' value=" + data[i].id + ">" + data[i].type + "</option>";
            }
            bodies.innerHTML = query;
        }
    })
}

function getCarEngines() {
    var engines = document.getElementById("engine");
    $.ajax({
        url: "engine",
        type: "get",
        dataType: "json",
        success: function (data) {
            console.log(data);
            engines.innerHTML = "";
            var query = "";
            for (var i = 0; i < data.length; i++) {
                query += "<option name='engine' value=" + data[i].id + ">" + data[i].type + "</option>";
            }
            engines.innerHTML = query;
        }
    })
}

function getCarTransmissions() {
    var tr = document.getElementById("transmission");
    $.ajax({
        url: "transmission",
        type: "get",
        dataType: "json",
        success: function (data) {
            console.log(data);
            tr.innerHTML = "";
            var query = "";
            for (var i = 0; i < data.length; i++) {
                query += "<option name='transmission' value=" + data[i].id + ">" + data[i].type + "</option>";
            }
            tr.innerHTML = query;
        }
    })
}


/**
 * Function for converting array to json.
 * @param arr array of objects.
 * @returns json string data.
 */
function arrayToJson(arr) {
    var result = {};
    for (var i = 0; i < arr.length; i++) {
        result[arr[i]['name']] = arr[i]['value'];
    }
    result = JSON.stringify(result);
    return result;
}