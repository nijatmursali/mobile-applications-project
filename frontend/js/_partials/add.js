var apiTable;
const urlParams = new URLSearchParams(window.location.search);
var apiParamID;
$(document).ready(function () {
  apiParamID = urlParams.get("id");
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
  var apiArray = new Array();
  api.image = $("#apiImage").val();
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
  createnewApi(api);
});

function createnewApi(api) {
  $.ajax({
    type: "POST",
    contentType: "application/json",
    url: "http://161.35.55.28:5000/api/questions",
    data: JSON.stringify(api),
    dataType: "json",
    success: function (response) {
      window.location.href = `index.html`;
    },
  });
}
