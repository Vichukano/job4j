function getReservedPlaces() {
    var table = document.getElementById("placesTableBody");
    $.ajax({
        url: "hall",
        type: "get",
        dataType: "json",
        success: function (data) {
            if (data !== null) {
                console.log(data);
                for (var i = 0; i < data.length; i++) {
                    table.rows[data[i].row - 1].cells[data[i].col]
                        .setAttribute("style", "background-color: lightskyblue");
                    table.rows[data[i].row - 1].cells[data[i].col]
                        .getElementsByTagName("input")[0].setAttribute("disabled", "disabled");
                }
            }
        }
    });
}

$(document).ready(function () {
    getReservedPlaces();
    setInterval('getReservedPlaces()', 30000);
    $('#payBtn').click(function () {
        var value = $('input[type="radio"]:checked').val();
        $.ajax({
            url: "payment",
            type: "post",
            data: value,
            dataType: "json",
            success: function (data) {
                window.location.href = "payment";
                sessionStorage.setItem("data", JSON.stringify(data));
            }
        });
    });
    $('#listBtn').click(function () {
        window.location.href = "customers";
    });
});
