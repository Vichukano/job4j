$(document).ready(function () {
    getCustomers();
    setInterval('getCustomers()', 30000);
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
                    + "<td>" + "row: " + data[i].row + " " + "col: " + data[i].col + "</td>"
                    + "<td class='text-center'><button type=\"button\" name='delBtn' data-id =" + data[i].id + " class=\"btn btn-danger text-center\" onclick='deleteCustomer()'>Delete</button></td>";
                row.innerHTML = query;
                table.appendChild(row);
            }
        }
    });
}

function deleteCustomer() {
    var id = $("button[name='delBtn']").data("id");
    $.ajax({
        url: "delete",
        type: "post",
        data: JSON.stringify(id),
        success: function () {
            console.log("Customer with id: " + id + " deleted");
            getCustomers();
        }
    });
}
