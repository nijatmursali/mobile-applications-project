var apiTable;
const urlParams = new URLSearchParams(window.location.search);

$(document).ready(function () {
  var api = urlParams.get("id");
  getApiList();
  getApiDetails(api);
});

/**
 *
 * LIST
 *
 */

function getApiList() {
  $.ajax({
    type: "GET",
    url: "http://161.35.55.28:5000/api/questions",
    dataType: "json",
    success: function (response) {
      console.log(response);
      generateTable(response, "#api-table");
    },
  });
}

function generateTable(jsonArray, idTable) {
  var dataArray = new Array();
  for (var i = 0; jsonArray[i]; i++) {
    api = new Api(jsonArray[i]);
    dataArray.push(api.toTableRow());
  }

  apiTable = $(idTable).DataTable({
    data: dataArray,
    columns: Api.toTableCol(),
  });
}

function askDeleteConfirm(target) {
  $("#delete-api-confirmation-confirm-btn").prop("value", target);
  $("#delete-api-confirmation").modal("show");
}

function deleteReportConfirmed(apiID) {
  var strUrl = "http://161.35.55.28:5000/api/questions";
  var reqUrl = `${strUrl}/${apiID}`;
  $.ajax({
    type: "DELETE",
    url: reqUrl,
    dataType: "json",
    success: function (response) {
      console.log(response);
      notifyMessage("Deleted", "", "success", "fa fa-trash");
      window.location.href = `index.html`;
    },
  });
}

$("#delete-api-confirmation-confirm-btn").click(function (e) {
  $("#delete-api-confirmation").modal("hide");
  deleteReportConfirmed(e["currentTarget"]["value"]);
});

/**
 *
 *
 * ADD
 *
 *
 */

$("#submit-new-api").click(function (t) {
  api = new Object();

  if ($("#validated-form").valid()) {
    api.image = $("#apiImage").val();
    api.description = $("#reportDescription").val();
    api.choices = $("#apiChoices").val();
    api.category = $("#apiCategory").val();
    createnewApi(api);
  } else {
    console.log("form validation failed");
    notifyMessage("Failed", "", "danger", "");
  }
});

function createnewApi(api) {
  $.ajax({
    type: "POST",
    contentType: "application/json",
    url: "http://161.35.55.28:5000/api/questions",
    data: JSON.stringify(api),
    dataType: "json",
    success: function (response) {
      notifyMessage("Added", "", "success", "fa fa-plus");
      window.location.href = `index.html`;
    },
  });
}

/**
 *
 * DETAILS
 *
 */

function getApiDetails(id) {
  var reqUrl = "http://161.35.55.28:5000/api/questions";
  var reqUrl = `${reqUrl}/${id}`;
  $.ajax({
    type: "GET",
    url: reqUrl,
    dataType: "json",
    success: function (response) {
      loadApiInformation(response);
      loadApiForm(response);
    },
  });
}

function loadApiInformation(apiJson) {
  api = new Api(apiJson);
  $("#apiid").append(`${api._id}`);
  $("#apiDescription").append(`${api.description}`);
  $(".picture").attr("src", api.image);
  $("#apiCategory").append(`${api.category}`);
  $("#apiChoices").append(`${api.choices[0]}`);
}

/**
 *
 * EDIT
 *
 */

function loadApiForm(apiJson) {
  api = new Api(apiJson);
  $("#apiid").prop("value", `${api._id}`);
  $("#apiDescription").prop("value", `${api.description}`);
  $("#apiImage").attr("src", api.image);
  $("#apiCategory").prop("value", `${api.category}`);
  $("#apiChoices").prop("value", `${api.choices}`);
}
