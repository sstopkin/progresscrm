function getCustomersListPage(status, statusText) {
    $.get("customerslist.html", function(data) {
        var permissions = $.ajax({
            type: "GET",
            url: "api/auth/validate",
            async: false
        }).responseText;
        if ((permissions == "3") || (permissions == "2")) {
            $("#addApartamentBtn").css("display", "block");
            $("#genApartamentsPriceBtn").css("display", "block");
        } else {
            $("#addApartamentBtn").css("display", "none");
            $("#genApartamentsPriceBtn").css("display", "none");
        }
        $("#mainContainer").html(data);
        $("#customersListHeaderText").html(statusText);
        $.ajax({
            type: "GET",
            url: "api/customers/getallcustomer?status=" + status,
            success: function(data) {
                drawCustomersListTable(data);
            },
            error: function(data) {
                showDanger(data.responseText);
                return false;
            }
        });
    });
}

function drawCustomersListTable(data) {
    $("#errorBlock").css("display", "none");
    var array = JSON.parse(data);
    var str = '<table class="table table-striped table-bordered" cellspacing="0" width="100%" id="customersListTable">';
    str += "<thead>";
    str += "<tr>";
    str += "<th>#</th>";
    str += "<th>ФИО</th>";
    str += "<th>Телефон</th>";
    str += "<th>Дата рождения</th>";
    str += "<th>E-mail</th>";
    str += "<th>Пол</th>";
    str += "</tr>";
    str += "</thead>";
    str += "<tbody>";
    array.forEach(function(entry) {
        str += "<tr>";
        str += "<td><a href=\"#customers/view/" + entry.id + "\" class=\"btn btn-primary\"><b>" + entry.id + "</b></a></td>";
        str += "<td>" + entry.customersLname + " " + entry.customersFname + " " + entry.customersMname + "</td>";
        str += "<td>" + entry.customersPhone + "</td>";
        str += "<td>" + entry.customersDateOfBirthday + "</td>";
        str += "<td>" + entry.customersEmail + "</td>";
        str += "<td>";
        switch (entry.customersSex) {
            case 1:
                str += "Мужской";
                break
            case 2:
                str += "Женский";
                break
            case 0:
                str += "Не указан";
                break
            default:
                str += "Не указан";
                break
        }
        str += "</td>";
        str += "</tr>";
    });
    str += "</tbody>";
    $("#divCustomersList").html(str);
    $('#customersListTable').dataTable();
}

function addCustomer() {
    $('#customersAddMoadl').modal('toggle');
    if ((($('#customersFname').val() == "")
            || ($('#customersMname').val() == "")
            || ($('#customersLname').val() == ""))
            && ($('#customersSex').val() == "")
            && ($('#customersPhone').val() == "")) {
        showDanger("Не все поля заполнены")
        return false;
    }
    $.ajax({
        type: "POST",
        url: "api/customers/addcustomer",
        data: ({
            customersFname: $('#customersFname').val(),
            customersMname: $('#customersMname').val(),
            customersLname: $('#customersLname').val(),
            customersDateOfBirthday: $('#customersDateOfBirthday').val(),
            customersSex: $('#customersSex').val(),
            customersEmail: $('#customersEmail').val(),
            customersPhone: $('#customersPhone').val(),
            customersAddress: $('#customersAddress').val(),
            customersExtra: $('#customersExtra').val()
        }),
        success: function(data) {
            alert("ok");
            document.location.href = "#customers/list/current";
        },
        error: function(data) {
            showDanger(data.responseText);
        }
    });
}

function customersEditById(customersId) {
    alert("editCustomerById " + customersId);
}

function customersDeleteById(customersId) {
    $.ajax({
        type: "POST",
        url: "api/customers/remove",
        data: ({id: customersId}),
        success: function(data) {
            document.location.href = "#customers/list/current";
        },
        error: function(data) {
            showDanger(data.responseText);
            checkStatus();
            return false;
        }
    });
    return false;
}
