let mysql = require('mysql');
let connection = mysql.createConnection({
    host: 'devel1',
    user: 'root',
    password: 'the27',
    database: 'Carlos'
});

function convertToJson(string) {
    return Object.values(JSON.parse(JSON.stringify(string)));
}

let getJsonData = function getData(id = null, produkt_id = null, produkt_anzahl = null, status = null) {
    let count = 0;
    return new Promise((resolve) => {
        let query = "SELECT id, produkt_id, produkt_anzahl, status FROM Auftrag";

        if (id !== null) {
            query = check(query, count);
            query += " id = " + mysql.escape(id);
            count++;
        }

        if (produkt_id !== null) {
            query = check(query, count);
            query += " produkt_id = " + mysql.escape(produkt_id);
            count++;
        }

        if (produkt_anzahl !== null) {
            query = check(query, count);
            query += " produkt_anzahl = " + mysql.escape(produkt_anzahl);
            count++;
        }

        if (status !== null) {
            query = check(query, count);
            query += " status = " + mysql.escape(status);
        }
        connection.query(query, function (err, result) {
            try {
                console.log(result);
                resolve(convertToJson(result));
            } catch (error) {
                console.log(err);
            }
        });
    });
}

function check(query, count) {
    if (count === 0) {
        query += " Where"
    } else {
        query += " and"
    }
    return query;
}

module.exports = {getJsonData};