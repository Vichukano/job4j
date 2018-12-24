$(document).ready(function () {
    $('#payBtn').click(function () {
        var customer = JSON.parse(arrayToJson($('#customerForm').serializeArray()));
        console.log(customer);
        var place = JSON.parse(sessionStorage.getItem("data"));
        console.log(place);
        $.ajax({
            url: "order",
            type: "post",
            data: JSON.stringify({
                "name": customer.name,
                "phone": customer.phone,
                "placeId": place.id
            }),
            success: function () {
                window.location.href = "/customers";
            }
        })
    });
    var data = JSON.parse(sessionStorage.getItem("data"));
    console.log(data);
    document.getElementById("payInfo").innerHTML =
        "You chose row: "
        + data.row
        + " col: "
        + data.col
        + ". Payment: "
        + data.cost;
});

/**
 * Function for converting array to json.
 * @param arr array of objects.
 * @returns json string data.
 */
function arrayToJson(arr) {
    var result = {};
    for (var i = 0; i < arr.length; i++) {
        result[arr[i]['name']] = arr[i]['value'];
    }
    result = JSON.stringify(result);
    return result;
}