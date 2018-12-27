$(document).ready(function () {
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

/**
 * Function for validating fields in payment.html page.
 * @returns {boolean} true if all field filled, else false.
 */
function validate() {
    var result = false;
    if (document.getElementById("username").value !== ''
        && document.getElementById("phone").value !== '') {
        result = true;
    }
    else {
        result = false;
    }
    return result;
}

/**
 * Function for sending customer in JSON format to server.
 */
function sendCustomer() {
    if (validate()) {
        document.getElementById("nameGroup").classList.remove("has-error");
        document.getElementById("phoneGroup").classList.remove("has-error");
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
        });
    }
    else {
        document.getElementById("nameGroup").classList.add("has-error");
        document.getElementById("phoneGroup").classList.add("has-error");
    }
}