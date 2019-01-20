$(document).ready(function () {
    getCars();
});

function getCars() {
    $.ajax({
        url: "cars",
        type: "get",
        dataType: "json",
        success: function (data) {
            console.log(data);
            addCarsLikeDiv(data);
        }
    })
}

function addCarsLikeDiv(data) {
    var container = document.getElementById("main-div");
    container.innerHTML = "";
    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("div");
        row.classList.add("row");
        var query = "<input type='hidden' id='carId' value=" + data[i].id + ">"
            + "<input type='hidden' id='userId' value=" + data[i].user.id + ">"
            + "<input type='hidden' id='userLogin' value=" + data[i].user.login + ">"
            + "<div class='col-md-2'><img src=" + "/image/" + data[i].image.url.replace('/upload/images/', '') + " width='150' height='150' alt='error'></div>"
            + "<div class='col-md-2'><label>Model</label><br><span>" + data[i].name + "</span></div>"
            + "<div class='col-md-3'><label>Characteristic</label><br><span>Color: " + data[i].color + "</span><br>"
            + "<span>Body: " + data[i].body.type + "</span><br>"
            + "<span>Engine: " + data[i].engine.type + "</span><br>"
            + "<span>Transmission: " + data[i].transmission.type + "</span></div>"
            + "<div class='col-md-2'><label>Mileage</label><br><span>" + data[i].mileage + "</span></div>"
            + "<div class='col-md-2'><label>Price</label><br><span>" + data[i].price + "</span></div>"
            + "<div class='col-md-1'><label>Sold</label><br><span>" + data[i].sold + "</span></div>"
            + "<div class='col-md-9 col-md-offset-2 text-center'><br><br><label>Description</label><br><div class='panel panel-default'><div class='panel-body text-left'><span>" + data[i].description + "</span></div></div></div>";
        var login = sessionStorage.getItem("login");
        if (login === data[i].user.login) {
            query += "<div class='col-md-1'><input type=\"button\" class=\"btn btn-info\" value=\"Update\" onclick=\"update()\"></div>";
        }
        row.innerHTML = query;
        container.appendChild(row);
    }
}

function update() {
    var carId = document.getElementById("carId").value;
    sessionStorage.setItem("carId", carId);
    window.location.href = "/update";
}

function send() {
    window.location.href = "/order";
}
