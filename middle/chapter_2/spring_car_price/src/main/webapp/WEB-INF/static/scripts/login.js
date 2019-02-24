/**
 * Function for validate input fields.
 * @returns {boolean} true if validate, else false.
 */
function validate() {
    var result = false;
    var login = document.getElementById("login").value;
    var pass = document.getElementById("pass").value;
    var warn = document.getElementById("warn");
    if (login !== "" && pass !== "") {
        result = true;
        warn.innerText = "";
        sessionStorage.setItem("login", login);
    } else {
        var warn = document.getElementById("warn");
        warn.innerText = "Fill all fields!"
    }
    return result;
}
