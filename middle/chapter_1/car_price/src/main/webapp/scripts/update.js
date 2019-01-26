/**
 * Function execute when page loaded.
 */
$(document).ready(function () {
    getCarParameters();

});

/**
 * Function for getting parameters of car for update.
 * Parameters setting to input fields in update.html page.
 */
function getCarParameters() {
    var id = document.getElementById("carId").value;
    console.log(id);
    $.ajax({
        url: "update",
        type: "GET",
        data: "id=" + id,
        dataType: "JSON",
        success: function (data) {
            console.log(data);
            document.getElementById("name").value = data.name;
            document.getElementById("price").value = data.price;
            document.getElementById("color").value = data.color;
            document.getElementById("mileage").value = data.mileage;
            document.getElementById("desc").innerText = data.description;
        }
    })
}