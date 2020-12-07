var apiTable;
const urlParams = new URLSearchParams(window.location.search);
var apiParamID;
$(document).ready(function () {
  apiParamID = urlParams.get("id");
  getApiDetails(apiParamID);
});

function getApiDetails(id) {
  var reqUrl = "http://161.35.55.28:5000/api/questions";
  var reqUrl = `${reqUrl}/${id}`;
  $.ajax({
    type: "GET",
    url: reqUrl,
    dataType: "json",
    success: function (response) {
      loadApiInformation(response);
    },
  });
}

function loadApiInformation(apiJson) {
  api = new Api(apiJson);
  $("#apiid").append(`${api._id}`);
  $("#apiDescription").append(`${api.description}`);
  $(".picture").attr("src", api.image);
  $("#apiCategory").append(`${api.category}`);

  var apiChoice = "";
  for (let i = 0; i < api.choices.length; i++) {
    apiChoice +=
      "<tr><td>" +
      api.choices[i]._id +
      "</td>" +
      "<td>" +
      api.choices[i].isCorrect +
      "</td>" +
      "<td>" +
      api.choices[i].text +
      "</td></tr>";
  }

  $("#addChoices").append(apiChoice);

  console.log(apiChoice);
}
