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
            addCarsToTable(data);
        }
    })
}

function addCarsToTable(data) {
    var table = document.getElementById("tbody");
    table.innerHTML = "";
    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("tr");
        var query = "<input type='hidden' value=" + data[i].id + ">"
            + "<td><img src="+ data[i].images[i].url+" width='50' height='50' alt='error'></td>"
            + "<td>" + data[i].name + "</td>"
            + "<td>" + data[i].color + "</td>"
            + "<td>" + data[i].body.type + "</td>"
            + "<td>" + data[i].engine.type + "</td>"
            + "<td>" + data[i].transmission.type + "</td>"
            + "<td>" + data[i].price + "</td>";
        row.innerHTML = query;
        table.appendChild(row)
    }

}

function send() {
    window.location.href = "/order";
}
