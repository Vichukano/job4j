$(document).ready(function () {
    getCustomers();
    setInterval('getCustomers()', 10000);
    $('#hallBtn').click(function () {
        window.location.href = "/";
    });
});

function getCustomers() {
    var table = document.getElementById("customersTableBody");
    $.ajax({
        url: "customers",
        type: "post",
        dataType: "json",
        success: function (data) {
            console.log(data);
            table.innerHTML = "";
            for (var i = 0; i < data.length; i++) {
                var row = document.createElement("tr");
                var query = "<td>" + data[i].id + "</td>"
                    + "<td>" + data[i].name + "</td>"
                    + "<td>" + data[i].phone + "</td>"
                    + "<td>" + "row: " + data[i].row + "</td>"
                    + "<td>" + " col: " + data[i].col + "</td>"
                    + "<button type=\"button\" name='delBtn' data-id =" + data[i].id + " class=\"btn btn-danger text-center\" onclick='deleteCustomer()'>Delete</button>";
                row.innerHTML = query;
                table.appendChild(row);
            }
        }
    });
}

function deleteCustomer() {
    var id = $("button[name='delBtn']").data("id");//Возвращает undefined нужен другой механизм
    $.ajax({
        url: "delete",
        type: "post",
        data: JSON.stringify(id),
        success: function () {
            console.log("Customer with id: " + id + "deleted");
        },
        complete: function () {
            console.log(id);
        }
    });
}