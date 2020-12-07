class Api {
  constructor(json = undefined) {
    (this._id = ""),
      (this.description = ""),
      (this.category = ""),
      (this.image = ""),
      (this.choices = {
        isCorrect: false,
        text: "",
      });

    if (json) {
      for (var attr in this) {
        if (json.hasOwnProperty(attr) && json[attr]) this[attr] = json[attr];
      }
    }
  }

  toTableRow() {
    var array = new Array();
    array.push(this.category);
    array.push(this.description);
    //Actions
    var actions = "";
    actions += `<a class="user-list-action" href='details.html?id=${this._id}'>
        <i title=details class="fa fa-eye"></i>
        </a>`;
    actions += `<a class="user-list-action" href='edit.html?id=${this._id}'>
        <i title=edit class="fa fa-edit"></i>
        </a>`;

    actions += `<a class="user-list-action" href="" onclick="deleteAPIConfirmed('${this._id}');return false;">
        <i title=delete class="fa fa-trash"></i>
        </a>`;

    array.push(actions);
    array.push(this._id);
    array.push(this.image);
    array.push(this.choices);

    return array;
  }

  static toTableCol() {
    var array = new Array();
    array.push({ title: "Category" });
    array.push({ title: "Description" });
    array.push({ title: "Actions", sortable: false });
    return array;
  }
}
