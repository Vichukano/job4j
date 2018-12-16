function getCountries() {
    var countries = document.getElementById("selectCountry");
    $.ajax({
        url: "cities",
        type: "get",
        dataType: "json",
        success: function (data) {
            console.log(data);
            var query = "<option value='' disabled selected>Choose...</option>";
            for (var i = 0; i < data.length; i++) {
                query += "<option name='country' value=" + data[i].id + ">" + data[i].name + "</option>";
            }
            countries.innerHTML = query;
        }
    });
}

function getCities() {
    $('#selectCountry').on('change', function () {
        $.ajax({
                url: "cities",
                type: "post",
                data: $(this).val(),
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    var select = document.getElementById("selectCity");
                    select.innerHTML = "";
                    for (var i = 0; i < data.length; i++) {
                        select.innerHTML += "<option name= 'city' value=" + data[i].id + ">" + data[i].name + "</option>";
                    }
                }
            }
        );
    });
}

function validate() {
    var result = true;
    var login = document.getElementById('login').value;
    var email = document.getElementById('email').value;
    var pass = document.getElementById('pass').value;
    if (login === "" && email === "" && pass === "") {
        result = false;
        document.getElementById('warning').innerHTML = "<h3 class='text-danger text-center'>Fill all fields!</h3>";
    }
    return result;
}

$(document).ready(function () {
    getCountries();
    getCities();
    validate();
});
