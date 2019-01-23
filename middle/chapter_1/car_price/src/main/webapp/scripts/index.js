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
            + "<div class='col-md-2'><img src=" + "/image/" + data[i].image.url.replace('/upload/images/', '') + " width='150' height='150' alt='No image'></div>"
            + "<div class='col-md-2'><label>Model</label><br><span>" + data[i].name + "</span></div>"
            + "<div class='col-md-3'><label>Characteristic</label><br><span>Color: " + data[i].color + "</span><br>"
            + "<span>Body: " + data[i].body.type + "</span><br>"
            + "<span>Engine: " + data[i].engine.type + "</span><br>"
            + "<span>Transmission: " + data[i].transmission.type + "</span></div>"
            + "<div class='col-md-1'><label>Mileage</label><br><span>" + data[i].mileage + "</span></div>"
            + "<div class='col-md-1'><label>Price</label><br><span>" + data[i].price + "</span></div>"
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
    }
}

function isSold(sold) {
    if (!sold) {
        return "<div class='col-md-1'><label>Status</label><br><span>on sale</span></div>"
    } else {
        return "<div class='col-md-1'><label>Status</label><br><span>sold</span></div>"
    }
}

//Баг. Берет первое значение!!!
function update(value) {
    var carId = value;
    sessionStorage.setItem("carId", carId);
    window.location.href = "/update";
}

function send() {
    window.location.href = "/order";
}

function logout() {
    window.location.href = "/login";
}
