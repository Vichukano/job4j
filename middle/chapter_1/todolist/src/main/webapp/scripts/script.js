$(document).ready(function () {
    getItems();
});

/**
 * Function for getting all items from server.
 */
function getItems() {
    $.ajax({
        url: "items",
        type: "get",
        dataType: "json",
        success: function (data) {
            console.log(data);
            addRows(data);
        }
    });
}

/**
 * Function for sending description of item to server in json format.
 */
function send() {
    var desc = JSON.stringify(document.getElementById("desc").value);
    console.log(desc);
    if (!validate()) {
        alert("Empty description field!");
        console.log("Empty description field!");
    } else {
        $.ajax({
            url: "items",
            type: "post",
            data: desc,
            dataType: "json",
            success: function (data) {
                console.log(data);
                addRow(data);
            }
        });
    }
}

function validate() {
    var result = false;
    var desc = document.getElementById("desc").value;
    if (desc !== '') {
        result = true;
    }
    return result;
}

/**
 * Function for generating table with items on index.html page.
 * @param data array of items objects.
 */
function addRows(data) {
    var tbody = document.getElementById("table").getElementsByTagName("tbody")[0];
    tbody.innerHTML = "";
    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("tr");
        var input = "";
        if (data[i].done) {
            input = "<input type='checkbox' class='done' name='done' onchange='doneTask(this)' data-id = "+ data[i].id +" checked value=" + data[i].done + ">";
        } else {
            input = "<input type='checkbox' class='done' name='done' onchange='doneTask(this)' data-id ="+ data[i].id +" value=" + data[i].done + ">";
        }
        var query = "<td>" + data[i].id + "</td>"
            + "<td>" + data[i].desc + "</td>"
            + "<td>" + new Date(data[i].created) + "</td>"
            + "<td>" + input + "</td>";
        row.innerHTML = query;
        tbody.appendChild(row);
    }
}

/**
 * Function for adding item to table in index.html page.
 * @param data item object.
 */
function addRow(data) {
    var tbody = document.getElementById("table").getElementsByTagName("tbody")[0];
    var row = document.createElement("tr");
    var input = "";
    if (data.done) {
        input = "<input type='checkbox' class='done' checked name='done' data-id ="+ data.id +" value=" + data.done + ">";
    } else {
        input = "<input type='checkbox' class='done' name='done' data-id ="+ data.id +"  value=" + data.done + ">";
    }
    var query = "<td>" + data.id + "</td>"
        + "<td>" + data.desc + "</td>"
        + "<td>" + new Date(data.created) + "</td>"
        + "<td>" + input + "</td>";
    row.innerHTML = query;
    tbody.appendChild(row);
}

/**
 * Function for sending id and done - value in json format to server.
 * @param e changed input of item in table.
 */
function doneTask(e) {
    var id = e.getAttribute("data-id");
    var done = e.getAttribute("value");
    console.log(id + " " + done);
    if (done === "false") {
        done = "true";
    } else {
        done = "false";
    }
    console.log(id +" " + done);
    $.ajax({
        url: "done",
        type: "post",
        data: JSON.stringify({"id": id, "done": done}),
        dataType: "json",
        success: function (data) {
            console.log(data);
            getItems();
        }
    });
}

/**
 * Function for getting array of item objects with done = true parameter from server.
 */
function showCompleted() {
    if (document.getElementById("check").checked) {
        $.ajax({
            url: "done",
            type: "get",
            dataType: "json",
            success: function (data) {
                console.log(data);
                addRows(data);
            }
        });
    } else {
        getItems();
    }
}
