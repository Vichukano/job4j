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
    } else {
        nameContainer.classList.remove("has-success");
        surnameContainer.classList.remove("has-success");
        nameContainer.classList.add("has-error");
        surnameContainer.classList.add("has-error");
        alert("Заполните поля!");
    }
}