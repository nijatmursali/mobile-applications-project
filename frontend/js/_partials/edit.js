var apiTable;
const urlParams = new URLSearchParams(window.location.search);
var apiParamID;
$(document).ready(function () {
  apiParamID = urlParams.get("id");
  getApiDetails(apiParamID);
});

$("#submit-edit-api").click(function (t) {
  api = new Object();
  apiArray = new Array();

  api._id = apiParamID;
  api.image = $("#apiImageText").val();
  api.description = $("#apiDescription").val();
  api.category = $("#apiCategory").val();

  $(".isCorrect").each(function (index) {
    apiArray.push({
      isCorrect: $(".isCorrect").eq(index).val(),
      text: $(".text").eq(index).val(),
    });
  });

  api.choices = apiArray;

  console.log(api);

  editApi(api);
});

function editApi(api) {
  var reqUrl = url;
  var reqUrl = `${reqUrl}/${apiParamID}`;
  $.ajax({
    type: "PUT",
    contentType: "application/json",
    url: reqUrl,
    data: JSON.stringify(api),
    dataType: "json",
    success: function (response) {
      window.location.href = `index.html`;
    },
  });
}

function getApiDetails(id) {
  var reqUrl = url;
  var reqUrl = `${reqUrl}/${id}`;
  $.ajax({
    type: "GET",
    url: reqUrl,
    dataType: "json",
    success: function (response) {
      loadApiForm(response);
    },
  });
}
function loadApiForm(apiJson) {
  api = new Api(apiJson);
  $("#apiid").prop("value", `${api._id}`);
  $("#apiDescription").prop("value", `${api.description}`);
  $("#apiImageText").prop("value", `${api.image}`);
  $("#apiImage").attr("src", api.image);
  $("#apiCategory").prop("value", `${api.category}`);
  size = api.choices.length;
  console.log(size);
  addOptions();

  if (size) {
    for (let index = 0; index < size; index++) {
      $("#addquery").trigger("click");
    }
    $.each(api.choices, function (index, value) {
      $(".isCorrect").eq(index).prop("value", `${value.isCorrect}`);
      $(".text").eq(index).prop("value", `${value.text}`);
    });
  }
}
var queriesCnt;

function addOptions() {
  $("#addquery").on("click", function () {
    var newRow = $("<tr>");
    var cols = "";
    cols += "<div class='row'>";
    //cols += `<div class="col-2 mt-2"><td><input type="text" class="form-control id"/></td></div>`;
    cols += `<div class="col-3 mt-2"><td><input type="text" class="form-control isCorrect""/></td></div>`;
    cols += `<div class="col-9 mt-2"><td><input type="text" class="form-control text""/></td></div>`;
    cols += "</div>";
    newRow.append(cols);
    $("table.order-list").append(newRow);
    queriesCnt++;
    console.log(queriesCnt);
  });
}
