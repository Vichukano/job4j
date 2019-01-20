function validate() {
    var result = false;
    var login = document.getElementById("login").value;
    var pass = document.getElementById("pass").value;
    var confirm = document.getElementById("confirm").value;
    var warn = document.getElementById("warn");
    if (login !== "" && pass !== "" && confirm !== "" && pass === confirm) {
        warn.innerText = "";
        sessionStorage.setItem("login", login);
        result = true;
    } else {
        warn.innerText = "Fill all fields or check password!";
    }
    return result;
}