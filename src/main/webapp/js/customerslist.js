function getCustomersListPage(status, statusText) {
    $.get("customerslist.html", function (data) {
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
            success: function (data) {
                drawCustomersListTable(data);
            },
            error: function (data) {
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
    array.forEach(function (entry) {
        str += "<tr>";
        str += "<td><a href=\"#customers/view/" + entry.id + "\" class=\"btn btn-primary\"><b>" + entry.id + "</b></a></td>";
        str += "<td>" + entry.customersLname + " " + entry.customersFname + " " + entry.customersMname + "</td>";
        str += "<td>" + entry.customersPhone + "</td>";
        str += "<td>" + timeConverter(entry.customersDateOfBirthday,'human-short') + "</td>";
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
    $.get("/templates/modal_customers.html", function (some_html) {
        var box = bootbox.dialog({
            show: false,
            title: "<h4 class=\"modal-title\">Добавить клиента</h4></div>",
            message: some_html,
            buttons: {
                success: {
                    label: "Добавить клиента",
                    className: "btn-success",
                    callback: function () {
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
                            success: function () {
                                $("#errorBlock").css("display", "none");
                                location.reload();//FIXME
                            },
                            error: function (data) {
                                showDanger(data.responseText);
                            }
                        });
                    }
                },
                danger: {
                    label: "Отмена",
                    className: "btn-danger",
                    callback: function () {
                    }
                }
            }
        });

        box.on("shown.bs.modal", function () {
            $('#customersDateOfBirthday').datepicker({
                format: "yyyy-mm-dd",
                todayBtn: "linked",
                language: "ru",
                autoclose: true,
                todayHighlight: true
            });
            $('#customersDateOfBirthday').val(timeConverter(new Date().getTime(), 'short'));
        });
        box.modal('show');
    }, 'html');
}

function customersEditById(customersId) {
    var array;
    $.get("/templates/modal_customers.html", function (some_html) {
        var box = bootbox.dialog({
            show: false,
            title: "<h4 class=\"modal-title\">Редактировать клиента</h4></div>",
            message: some_html,
            buttons: {
                success: {
                    label: "Редактировать клиента",
                    className: "btn-success",
                    callback: function () {
                        $.ajax({
                            type: "POST",
                            url: "api/customers/editcustomer",
                            data: ({
                                customersId: customersId,
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
                            success: function () {
                                $("#errorBlock").css("display", "none");
                                location.reload();//FIXME
                            },
                            error: function (data) {
                                showDanger(data.responseText);
                            }
                        });
                    }
                },
                danger: {
                    label: "Отмена",
                    className: "btn-danger",
                    callback: function () {
                    }
                }
            }
        });

        box.on("shown.bs.modal", function () {
            $('#customersDateOfBirthday').datepicker({
                format: "yyyy-mm-dd",
                todayBtn: "linked",
                language: "ru",
                autoclose: true,
                todayHighlight: true
            });
            $.ajax({
                type: "GET",
                url: "api/customers/getcustomer?id=" + customersId,
                success: function (data) {
                    array = JSON.parse(data);
                    $('#customersFname').val(array.customersFname);
                    $('#customersMname').val(array.customersMname);
                    $('#customersLname').val(array.customersLname);
                    $('#customersDateOfBirthday').val(array.customersDateOfBirthday);
                    $('#customersSex').val(array.customersSex);
                    $('#customersEmail').val(array.customersEmail);
                    $('#customersPhone').val(array.customersPhone);
                    $('#customersAddress').val(array.customersAddress);
                    $('#customersExtra').val(array.customersExtra);
                },
                error: function (data) {
                    showDanger(data.responseText);
                    return false;
                }
            });
        });
        box.modal('show');
    }, 'html');
}

function customersDeleteById(customersId) {
    $.ajax({
        type: "POST",
        url: "api/customers/remove",
        data: ({id: customersId}),
        success: function (data) {
            document.location.href = "#customers/list/current";
        },
        error: function (data) {
            showDanger(data.responseText);
            checkStatus();
            return false;
        }
    });
    return false;
}
