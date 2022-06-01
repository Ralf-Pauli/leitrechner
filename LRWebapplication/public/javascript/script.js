function generate_table(json) {
    let jsonObject = JSON.parse(JSON.stringify(json));

    // gets body element
    let body = document.getElementsByTagName("body")[0];
    // creates a <table> element and a <tbody> element
    if (document.getElementById("datatable") != null) {
        removeElement("datatable");
    }

    (document.getElementById("loader")).style.visibility = "visible";

    let tbl = document.createElement("table");
    tbl.setAttribute("id", "datatable")
    tbl.setAttribute("class", "styled-table")
    // let tblBody = document.createElement("tbody");
    let row = '<tr><th>Auftrags ID</th><th>Produkt ID</th><th>Anzahl</th><th>Status</th></tr>'
    tbl.innerHTML += row;

    // creating all cells
    for (let i = 0; i < jsonObject.length; i++) {

        let row = `<tr>
            <td>${jsonObject[i].id}</td>
            <td>${jsonObject[i].produkt_id}</td>
            <td>${jsonObject[i].produkt_anzahl}</td>
            <td>${jsonObject[i].status}</td>
            </tr>`
        tbl.innerHTML += row
    }
    body.appendChild(tbl);
    (document.getElementById("loader")).style.visibility = "hidden";
}

function removeElement(id) {
    let elem = document.getElementById(id);
    return elem.parentNode.removeChild(elem);
}

function getOrders(id, pId, pAnz, status) {
    fetch('http://localhost/getOrders?' + new URLSearchParams({
        id: id,
        pId: pId,
        pAnz: pAnz,
        status: status
    }))
        .then(response => response.json())
        .then(data => generate_table(data));

}

module.exports = { generate_table, getOrders }