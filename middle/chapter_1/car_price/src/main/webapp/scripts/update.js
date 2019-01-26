$(document).ready(function () {
    getCarParameters();

});

function getCarParameters() {
    var id = document.getElementById("carId").value;
    console.log(id);
    $.ajax({
        url: "update",
        type: "GET",
        data: "id="+id,
        dataType: "JSON",
        success: function (data) {
            console.log(data);
            var model = document.getElementById("name");
            var price = document.getElementById("price");
            var color = document.getElementById("color");
            var mileage = document.getElementById("mileage");
            var desc = document.getElementById("desc");
            model.value = data.name;
            price.value = data.price;
            color.value = data.color;
            mileage.value = data.mileage;
            desc.innerText = data.description;
        }
    })
}