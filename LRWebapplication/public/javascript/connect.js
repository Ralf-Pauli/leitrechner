let mysql = require('mysql');
let connection = mysql.createConnection({
    host: 'devel1',
    user: 'root',
    password: 'the27',
    database: 'Carlos'
});

let count = 0;
getData(null,2,null,"fertig");

function getData(id = null, produkt_id = null, produkt_anzahl = null, status = null) {
   let results = "";
    connection.connect(function (error) {
        if (error) throw error;
        let query = "SELECT id, produkt_id, produkt_anzahl, status FROM Auftrag";

        if (id !== null) {
            if (count === 0) {
                query += " Where"
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
            connection.end();
            console.log(result);
        });
    });
}

function check(query) {
    if (count === 0) {
        query += " Where"
    } else {
        query += " and"
    }
    return query;
}

module.exports = {getData};