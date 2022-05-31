const { json } = require('express');
const script = require('./script')
let mysql = require('mysql');
let connection = mysql.createConnection({
    host: 'devel1',
    user: 'root',
    password: 'the27',
    database: 'Carlos'
});


var data = undefined;
//getData(151, null, null, "offen", convertToJson());

function convertToJson(string) {
    return Object.values(JSON.parse(JSON.stringify(string)));
}

function printJson() {
    console.log(convertToJson(data));
}

let count = 0;

let getJsonData = function getData(id = null, produkt_id = null, produkt_anzahl = null, status = null) {
    return new Promise((resolve, reject) => {
//        connection.connect(function (error) {
            let query = "SELECT id, produkt_id, produkt_anzahl, status FROM Auftrag";

            if (id !== null) {
                check(query)
                query += " id = " + mysql.escape(id);
                count++;
            }

            if (produkt_id !== null) {
                query = check(query);
                query += " produkt_id = " + mysql.escape(produkt_id);
                count++;
            }

            if (produkt_anzahl !== null) {
                query = check(query);
                query += " produkt_anzahl = " + mysql.escape(produkt_anzahl);
                count++;
            }

            if (status !== null) {
                query = check(query);
                query += " status = " + mysql.escape(status);
            }

            connection.query(query, function (err, result, fields) {
                if (err) throw err;
                try {
                    resolve(convertToJson(result));
                } catch (error) {
                    throw error;
                }
                connection.end();
            });
        });
//    });
}

function check(query) {
    if (count === 0) {
        query += " Where"
    } else {
        query += " and"
    }
    return query;
}

module.exports = { getJsonData };