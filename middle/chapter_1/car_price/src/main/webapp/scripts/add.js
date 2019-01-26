/**
 * Functions execute when page loaded.
 */
$(document).ready(function () {
    getCarBodies();
    getCarEngines();
    getCarTransmissions();
    getCarId();
});

/**
 * Function for getting array with CarBody objects
 * and setting parameters to the page.
 */
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

/**
 * Function for getting array with Engine objects
 * and setting parameters to the page.
 */
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

/**
 * Function for getting array with Transmission objects
 * and setting parameters to the page.
 */
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
 * Function for setting car id parameter to hidden
 * input field.
 */
function getCarId() {
    var carId = document.getElementById("carId");
    carId.value = sessionStorage.getItem("carId");
}

/**
 * Function for validating input forms.
 * @returns {boolean} true or false.
 */
function validate() {
    var result = false;
    var model = document.getElementById("name").value;
    var price = document.getElementById("price").value;
    var color = document.getElementById("color").value;
    var mileage = document.getElementById("mileage").value;
    var desc = document.getElementById("desc").value;
    var warn = document.getElementById("warn");
    if (model !== ""
        && price !== ""
        && color !== ""
        && mileage !== ""
        && desc !== ""
    ) {
        result = true;
        warn.innerText = "";
    } else {
        warn.innerText = "Fill all fields!"
    }
    return result;
}