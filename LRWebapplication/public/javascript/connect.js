const { json } = require('express');
let mysql = require('mysql');
let connection = mysql.createConnection({
    host: 'devel1',
    user: 'root',
    password: 'the27',
    database: 'Carlos'
});


var data = undefined;
getData(null, 2, null, "offen", printJson);

async function convertToJson(data){
    return Object.values(JSON.parse(JSON.stringify(data)));
}

function printJson() {
    console.log(convertToJson(data));
}

let count = 0;

function getData(id = null, produkt_id = null, produkt_anzahl = null, status = null, callback) {
    //return new Promise(data => {
        connection.connect(function (error) {
            if (error) throw error;
            let query = "SELECT id, produkt_id, produkt_anzahl, status FROM Auftrag";

        if (id !== null) {
            if (count === 0) {
                query += " Where";
            }
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
                    data = result;
                    //data(result);
                    callback();
                } catch (error) {
                    //data({});
                    throw error;
                }
                connection.end();
            });
        });
    //});
}

function check(query) {
    if (count === 0) {
        query += " Where"
    } else {
        query += " and"
    }
    return query;
}

module.exports = { getData };