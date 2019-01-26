/**
 * Function execute when page loaded.
 */
$(document).ready(function () {
    getCars();
});

/**
 * Function get array of Car object form server.
 */
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
    window.location.href = "/update.html";
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
