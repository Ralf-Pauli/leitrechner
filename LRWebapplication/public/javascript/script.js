function getOrders() {
    let params = new URLSearchParams();
    const pIdArr = ['id', 'pId', 'pAnz', 'status'];
    makeParams(params, pIdArr);

    fetch('http://localhost/getOrders?' + params)
        .then(response => response.json())
        .then(data => generate_table(data));
}

function generate_table(json) {
    let body = document.getElementsByTagName("body")[0];

    // If table exists -> Delete
    let tDelete = document.getElementById("datatable");
    tDelete != null ? tDelete.parentNode.removeChild(tDelete) : null

    // Table
    let table = document.createElement("table");
    table.setAttribute("id", "datatable")
    table.setAttribute("class", "styled-table")

    // Table Header
    let thead = table.createTHead();
    let row = thead.insertRow();
    for (let jsonElementKey in json[0]) {
        let cell = row.insertCell();
        cell.appendChild(document.createTextNode((jsonElementKey[0].toUpperCase() + jsonElementKey.substring(1))
            .replace("_", "-")))
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

function makeParams(params, ids) {
    for (let pos in ids) {
        let value = getValue(ids[pos]);
        value !== "" ? params.append(ids[pos], value) : null;
    }
}

function getValue(id) {
    return document.getElementById(id).value
}