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

function sendCar() {
    var model = document.getElementById("name").value;
    var price = document.getElementById("price").value;
    var color = document.getElementById("color").value;
    var body = document.getElementById("body").value;
    var engine = document.getElementById("engine").value;
    var tr = document.getElementById("transmission").value;
    $.ajax({
        url: "cars",
        type: "post",
        data: JSON.stringify({
            "name" : model,
            "price" : price,
            "color" : color,
            "body" : body,
            "engine" : engine,
            "transmission" : tr
        }),
        complete: function () {
            console.log("Car successfully added");
            window.location.href = "/";
        }
    })
}



function send() {
    var arr = $('#myForm').serializeArray();
    var data = arrayToJson(arr);
    $.ajax({
        url: "cars",
        type: "post",
        data: data,
        success: function () {
            console.log(data);
            alert("Car successfully added");
            window.location.href = "";
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