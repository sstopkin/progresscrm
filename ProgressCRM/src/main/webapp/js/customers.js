function getCustomersListPage() {
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
        $.ajax({
            type: "GET",
            url: "api/customers/getallcustomer",
            success: function(data) {
                $("#errorBlock").css("display", "none");
                var array = JSON.parse(data);
                var str = "<table class=\"table table-bordered\">";
                str += "<thead>";
                str += "<tr>";
                str += "<th>#</th>";
                str += "<th>ФИО</th>";
                str += "<th>Телефон</th>";
                str += "<th>Дата рождения</th>";
                str += "<th>E-mail</th>";
                str += "<th>Пол</th>";
                if (permissions == "3") {
                    str += "<th>Редактировать</th>";
                    str += "<th>Удалить</th>";
                }
                str += "</tr>";
                str += "</thead>";
                str += "<tbody>";
                array.forEach(function(entry) {
                    str += "<tr>";
                    str += "<td><a href=\"#customers/view/" + entry.id + "\" class=\"btn btn-default\"><b>" + entry.id + "</b></a></td>";
                    str += "<td>" + entry.customersFname + " " + entry.customersMname + " " + entry.customersLname + "</td>";
                    str += "<td>" + entry.customersPhone + "</td>";
                    str += "<td>" + entry.customersDayOfBirthday + "-" + entry.customersMonthOfBirthday + "-" + entry.customersYearOfBirthday + "</td>";
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
                    if (permissions == "3") {
                        str += "<td><a href=\"#customers/edit/" + entry.id + "\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-pencil\"></span></a></td>";
                        str += "<td>" + "<button type=\"button\" onclick=\"customersDeleteById(" + entry.id + ");\" class=\"btn btn-default\"><span class=\"glyphicon glyphicon-remove\"></span></button>" + "</td>";
                    }
                    str += "</tr>";
                });
                str += "</tbody>";
                $("#divCustomersList").html(str);
            },
            error: function(data) {
                showDanger(data.responseText);
                return false;
            }
        });
    });
}

function addCustomer() {
    $('#customersAddMoadl').modal('toggle');
//    if (
//                ($('#TypeOfSales').val() == "")
//                || ($('#Price').val() == "")
//                || ($('#CityDistrict').val() == "")
//                || ($('#Floor').val() == "")
//                || ($('#Floors').val() == "")
//                || ($('#RoomNumber').val() == "")
//                || ($('#Material').val() == "")
//                || ($('#SizeApartament').val() == "")
//                || ($('#SizeLiving').val() == "")
//                || ($('#SizeKitchen').val() == "")
//                || ($('#Balcony').val() == "")
//                || ($('#Loggia').val() == "")
//                || ($('#YearOfConstruction').val() == "")
//                || ($('#Description').val() == "")
//                || ($('#ClientPhone').val() == "")
//                ) {
//            $("#errorBlock").addClass("alert-danger");
//            $("#errorMessage").html("Не все поля заполнены");
//            $("#errorBlock").css("display", "block");
//            return false;
//        }
    $.ajax({
        type: "POST",
        url: "api/customers/addcustomer",
        data: ({
            customersFname: $('#customersFname').val(),
            customersMname: $('#customersMname').val(),
            customersLname: $('#customersLname').val(),
            customersYearOfBirthday: $('#customersYearOfBirthday').val(),
            customersMonthOfBirthday: $('#customersMonthOfBirthday').val(),
            customersDayOfBirthday: $('#customersDayOfBirthday').val(),
            customersSex: $('#customersSex').val(),
            customersEmail: $('#customersEmail').val(),
            customersPhone: $('#customersPhone').val(),
            customersAddress: $('#customersAddress').val(),
            customersExtra: $('#customersExtra').val()
        }),
        success: function(data) {
            location.reload();//FIXME
            $("#errorBlock").css("display", "none");
            getCustomersListPage();
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
    console.log("customersDeleteById " + customersId);
    $.ajax({
        type: "POST",
        url: "api/customer/remove",
        data: ({id: customersId}),
        success: function(data) {
            getCustomersListPage();
        },
        error: function(data) {
            $("#errorBlock").addClass("alert-danger");
            $("#errorMessage").html(data.responseText);
            $("#errorBlock").css("display", "block");
            checkStatus();
            return false;
        }
    });
    return false;
}
