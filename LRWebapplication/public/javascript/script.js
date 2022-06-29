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

    // If table exists -> Delete | Same with InfoBox
    let tDelete = document.getElementById("datatable");
    tDelete != null ? tDelete.parentNode.removeChild(tDelete) : null
    let iBDelete = document.getElementById('i-box');
    iBDelete != null ? iBDelete.parentNode.removeChild(iBDelete):null;

    if (json.length === 0){
        let infoBox = document.createElement('div');
        infoBox.setAttribute('id', 'i-box');
        infoBox.appendChild(document.createTextNode("Nichts gefunden"));
        body.appendChild(infoBox);
        infoBox.classList.add('zoomed');
        requestAnimationFrame(() => {
            infoBox.classList.remove('zoomed')
        })
    }

    // Table
    let table = document.createElement("table");
    table.setAttribute("id", "datatable")
    table.setAttribute("class", "styled-table")
    table.classList.add('zoomed');

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
    requestAnimationFrame(() => {
       table.classList.remove('zoomed')
    });
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