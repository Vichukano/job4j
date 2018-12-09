/**
 * Function for validating input in form.
 * @returns {boolean} - true if validate, else false.
 */
function validate() {
    var result = false;
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var nameContainer = document.getElementById("name-group");
    var surnameContainer = document.getElementById("surname-group");
    if (name !== '' && surname !== '') {
        nameContainer.classList.remove("has-error");
        surnameContainer.classList.remove("has-error");
        nameContainer.classList.add("has-success");
        surnameContainer.classList.add("has-success");
        result = true;
    } else {
        nameContainer.classList.remove("has-success");
        surnameContainer.classList.remove("has-success");
        nameContainer.classList.add("has-error");
        surnameContainer.classList.add("has-error");
        alert("Заполните поля!");
    }
    return result;
}

/**
 * Function for adding row to table.
 * @param resp - json data from the server.
 */
function addRow(resp) {
    //var data = JSON.parse(resp); Если dataType: json то парсить не нужно.
    var tbody = document.getElementById("table1").getElementsByTagName("tbody")[0];
    var row = document.createElement("tr");
    var query = "<td>" + resp.name + "</td>"
        + "<td>" + resp.surname + "</td>"
        + "<td>" + resp.sex + "</td>"
        + "<td>" + resp.desc + "</td>";
    row.innerHTML = query;
    tbody.appendChild(row);
}

/**
 * Ajax function for sending data to server.
 */
function send() {
    if (validate()) {
        var arr = $('#myForm').serializeArray();//Создает массив объектов. В форме должно быть поле name.
        var data = arrayToJson(arr);
        $.ajax("/controller", {
            type: "post",
            data: data,
            dataType: "json",
            success: function (resp) {
                console.log(resp);
                addRow(resp)
            }
        });
    }
}

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
