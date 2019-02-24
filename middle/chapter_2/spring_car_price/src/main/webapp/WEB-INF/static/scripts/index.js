/**
 * Function execute when page loaded.
 */
$(document).ready(function () {
    getCars();
    getCarBodies();
    getCarEngines();
    getCarTransmissions();
});

/**
 * Function get array of Car object form server.
 */
function getCars() {
    $.ajax({
        url: "api/cars",
        type: "get",
        data: "action=all",
        dataType: "json",
        success: function (data) {
            console.log(data);
            addCarsLikeDiv(data);
        }
    })
}

function showLastDay() {
    $.ajax({
        url: "api/cars",
        type: "get",
        data: "action=last",
        dataType: "json",
        success: function (data) {
            console.log(data);
            addCarsLikeDiv(data);
        }
    })
}

function showWithImage() {
    $.ajax({
        url: "api/cars",
        type: "get",
        data: "action=image",
        dataType: "json",
        success: function (data) {
            console.log(data);
            addCarsLikeDiv(data);
        }
    })
}

function getByBody() {
    var e = document.getElementById("body");
    var type = e.options[e.selectedIndex].text;
    $.ajax({
        url: "api/cars",
        type: "get",
        data: "query=findCarByBody&type=" + type,
        dataType: "json",
        success: function (data) {
            console.log(data);
            addCarsLikeDiv(data);
        }
    })
}

function getByEngine() {
    var e = document.getElementById("engine");
    var type = e.options[e.selectedIndex].text;
    $.ajax({
        url: "api/cars",
        type: "get",
        data: "query=findCarByEngine&type=" + type,
        dataType: "json",
        success: function (data) {
            console.log(data);
            addCarsLikeDiv(data);
        }
    })
}

function getByTr() {
    var e = document.getElementById("transmission");
    var type = e.options[e.selectedIndex].text;
    $.ajax({
        url: "api/cars",
        type: "get",
        data: "query=findCarByTransmission&type=" + type,
        dataType: "json",
        success: function (data) {
            console.log(data);
            addCarsLikeDiv(data);
        }
    })
}

function getRelevant() {
    $.ajax({
        url: "api/cars",
        type: "get",
        data: "action=relevant",
        dataType: "json",
        success: function (data) {
            console.log(data);
            addCarsLikeDiv(data);
        }
    })
}

/**
 * Function for adding car object parameters to index.html page.
 * @param data - array with car objects.
 */
function addCarsLikeDiv(data) {
    var container = document.getElementById("main-div");
    container.innerHTML = "";
    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("div");
        var br =document.createElement("br");
        row.classList.add("row");
        row.style.backgroundColor = "#f2f2ff";
        var query = "<input type='hidden' id='carId' value=" + data[i].id + ">"
            + "<input type='hidden' id='userId' value=" + data[i].user.id + ">"
            + "<input type='hidden' id='userLogin' value=" + data[i].user.login + ">"
            + "<div class='col-md-2'><img src=" + "/image/" + data[i].image.url.replace('/upload/images/', '') + " width='150' height='150' alt='No image'></div>"
            + "<div class='col-md-1'><label>Model</label><br><span>" + data[i].name + "</span></div>"
            + "<div class='col-md-2'><label>Characteristic</label><br><span>Color: " + data[i].color + "</span><br>"
            + "<span>Body: " + data[i].body.type + "</span><br>"
            + "<span>Engine: " + data[i].engine.type + "</span><br>"
            + "<span>Transmission: " + data[i].transmission.type + "</span></div>"
            + "<div class='col-md-2'><label>Mileage</label><br><span>" + data[i].mileage + "</span></div>"
            + "<div class='col-md-2'><label>Price</label><br><span>" + data[i].price + "</span></div>"
            + isSold(data[i].sold)
            + "<div class='col-md-1'><label>Created</label><br><span>" + new Date(data[i].createDate).toDateString("ru") + "</span></div>"
            + "<div class='col-md-9 col-md-offset-2 text-center'><label>Description</label><br><div class='panel panel-default'><div class='panel-body text-left'><span>" + data[i].description + "</span></div></div></div>";
        var login = sessionStorage.getItem("login");
        if (login === data[i].user.login) {
            query += "<div class='col-md-1'><br>"
                + "<br><button type='button' class='btn btn-info' value=" + data[i].id + " name='updBtn' onclick='update(this.value)'>Update</button></div>";
        }
        row.innerHTML = query;
        container.appendChild(row);
        container.appendChild(br);
    }
}

/**
 * Function for setting sold parameter.
 * @param sold - boolean
 * @returns {string} 'sale' if sold = true, else 'on sale'.
 */
function isSold(sold) {
    if (!sold) {
        return "<div class='col-md-2'><label>Status</label><br><span>on sale</span></div>"
    } else {
        return "<div class='col-md-2'><label>Status</label><br><span>sold</span></div>"
    }
}

/**
 * Function for getting car id for update.
 * Set id to sessionStorage and redirect to update.html page.
 * @param value car id stored in clicked field.
 */
function update(value) {
    var carId = value;
    sessionStorage.setItem("carId", carId);
    window.location.href = "/update";
}

/**
 * Function for redirecting to add.html page.
 */
function send() {
    window.location.href = "/add.html";
}

/**
 * Function for logout.
 * Send request to LoginController.
 */
function logout() {
    window.location.href = "/login";
}

function getCarBodies() {
    var bodies = document.getElementById("body");
    $.ajax({
        url: "api/body",
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
        url: "api/engine",
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
        url: "api/transmission",
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