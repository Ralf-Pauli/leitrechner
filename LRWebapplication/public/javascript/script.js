function generate_table(json) {
    console.log(json)
    let jsonObject = JSON.parse(JSON.stringify(json));

    // gets body element
    let body = document.getElementsByTagName("body")[0];
    // creates a <table> element and a <tbody> element
    if (document.getElementById("datatable") != null) {
        removeElement("datatable");
    }

    

    let table = document.createElement("table");
    table.setAttribute("id", "datatable")
    table.setAttribute("class", "styled-table")

    let thead = document.createElement("thead");
    let row = '<tr><th>Auftrags ID</th><th>Produkt ID</th><th>Anzahl</th><th>Status</th></tr>'
    thead.innerHTML += row;

    let tbody = document.createElement("tbody");
    
    // creating all cells
    for (let i = 0; i < jsonObject.length; i++) {

        let row = `<tr>
            <td>${jsonObject[i].id}</td>
            <td>${jsonObject[i].produkt_id}</td>
            <td>${jsonObject[i].produkt_anzahl}</td>
            <td>${jsonObject[i].status}</td>
            </tr>`
        tbody.innerHTML += row
    }
    table.appendChild(thead)
    table.appendChild(tbody)
    body.appendChild(table)
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