$().ready(function () {
    getAll();
});

function getAll() {
    $.ajax({
        url: "/api/all",
        type: "GET",
        dataType: "json",
        success: function (data) {
            console.log(data);
            fillTable(data);
        }
    })
}

function fillTable(data) {
    var table = document.getElementById("body");
    table.innerHTML = "";
    for (var i = 0; i < data.length; i++) {
        var tr = document.createElement("tr");
        var query =
            "<td>" + data[i].name + "</td>"
            + "<td>" + data[i].age + "</td>";
        tr.innerHTML = query;
        table.appendChild(tr);
    }
}