function generate_table(json) {
    let body = document.getElementsByTagName("body")[0];

    if (document.getElementById("datatable") != null) {
        removeElement("datatable");
    }
    let table = document.createElement("table");
    table.setAttribute("class", "styled-table")

    // Table Header
    let thead = table.createTHead();
    let row = thead.insertRow();

    for (let jsonElementKey in json[0]) {
        let cell = row.insertCell();
        cell.appendChild(document.createTextNode(jsonElementKey))
    }

    // Table Data
    let tbody = table.createTBody();

    json.forEach(obj => {
        let row = tbody.insertRow();
        Object.entries(obj).forEach(([key, value]) => {
            let cell = row.insertCell();
            cell.appendChild(document.createTextNode(value.toString()));
        })
    });
    body.appendChild(table);
}

function removeElement(id) {
    let elem = document.getElementById(id);
    return elem.parentNode.removeChild(elem);
}

function getOrders() {
    let params = new URLSearchParams();
    getValue("id")!=""?params.append("id", getValue("id")):null
    getValue("pId")!=""?params.append("pId", getValue("pId")):null
    getValue("pAnz")!=""?params.append("pAnz", getValue("pAnz")):null
    getValue("status")!=""?params.append("status", getValue("status")):null
    console.log(params)
    fetch('http://localhost/getOrders?' + params)
        .then(response => response.json())
        .then(data => generate_table(data));

}

function getValue(id) {
    return document.getElementById(id).value
}

module.exports = { generate_table, getOrders }