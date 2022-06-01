const json = "[{id: 30,produkt_id: 2,produkt_anzahl: 2,status: 'fertig'}, {id: 45,produkt_id: 2,produkt_anzahl: 3,status: 'fertig'}]";
//const obj = JSON.parse(json);
//console.log(obj.id)

function generate_table(json) {
    // get the reference for the body
    let auftrag = JSON.parse(JSON.stringify(json));

    let body = document.getElementsByTagName("body")[0];
    // creates a <table> element and a <tbody> element
    if (document.getElementById("datatable") != null) {
        removeElement("datatable");
    }

    let tbl = document.createElement("table");
    tbl.setAttribute("id", "datatable")
    // let tblBody = document.createElement("tbody");
    let row = '<tr><td>Auftrags-ID</td><td></td></tr>'

    // creating all cells
    for (let i = 0; i < auftrag.length; i++) {
        let row = `<tr>
                        <td>${auftrag[i].id}</td>
                        <td>${auftrag[i].produkt_id}</td>
                        <td>${auftrag[i].produkt_anzahl}</td>
                        <td>${auftrag[i].status}</td>
                    </tr>`
        tbl.innerHTML += row
        /*
        // creates a table row
        let row = document.createElement("tr");

        for (let j = 0; j < 2; j++) {
            // Create a <td> element and a text node, make the text
            // node the contents of the <td>, and put the <td> at
            // the end of the table row

            if (i == 0) {
                var cell = document.createElement("th");
            } else {
                var cell = document.createElement("td");
            }
            let cellText = document.createTextNode("cell in row " + i + ", column " + j);
            cell.appendChild(cellText);
            row.appendChild(cell);
        }
*/

        // add the row to the end of the table body
        // tblBody.appendChild(row);
    }

    // put the <tbody> in the <table>
    // tbl.appendChild(tblBody);
    // appends <table> into <body>
    body.appendChild(tbl);
    // sets the border attribute of tbl to 2;
    // tbl.setAttribute("border", "2");
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