$().ready(function () {
    getSingle();
});

function getSingle() {
    $.ajax({
        url: "/api",
        type: "GET",
        dataType: "json",
        success: function (data) {
            console.log(data);
        }
    })
}

function redirect() {
    window.location.href = "/all";
}