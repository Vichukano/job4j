/*
Function for validating input in form.
 */
function validate() {
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var nameContainer = document.getElementById("name-group");
    var surnameContainer = document.getElementById("surname-group");
    if (name != '' && surname != ''){
        nameContainer.classList.remove("has-error");
        surnameContainer.classList.remove("has-error");
        nameContainer.classList.add("has-success");
        surnameContainer.classList.add("has-success");
        addRow();
    } else {
        nameContainer.classList.remove("has-success");
        surnameContainer.classList.remove("has-success");
        nameContainer.classList.add("has-error");
        surnameContainer.classList.add("has-error");
        alert("Заполните поля!");
    }
}
/*
Function for adding row to table.
 */
function addRow() {
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var sex;
    var description = document.getElementById("description").value;
    if (document.getElementById("maleRadio").checked) {
        sex = document.getElementById("maleRadio").value;
    }
    if (document.getElementById("femRadio").checked) {
        sex = document.getElementById("femRadio").value;
    }
    var tbody = document.getElementById("table1").getElementsByTagName("tbody")[0];
    var row = document.createElement("tr");
    var query = "<td>" + name +"</td>"
        + "<td>" + surname +"</td>"
        + "<td>" + sex +"</td>"
        + "<td>" + description + "</td>";
    row.innerHTML = query;
    tbody.appendChild(row);
}
