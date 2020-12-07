var apiTable;
const urlParams = new URLSearchParams(window.location.search);
var apiParamID;
$(document).ready(function () {
  apiParamID = urlParams.get("id");
  getApiList();
});

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

function deleteAPIConfirmed(apiID) {
  var strUrl = "http://161.35.55.28:5000/api/questions";
  var reqUrl = `${strUrl}/${apiID}`;
  $.ajax({
    type: "DELETE",
    url: reqUrl,
    dataType: "json",
    success: function (response) {
      window.location.href = `index.html`;
    },
  });
}
