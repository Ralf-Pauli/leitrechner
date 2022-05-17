let mysql = require('mysql');
let connection = mysql.createConnection({
    host: 'devel1',
    user: 'root',
    password: 'the27',
    database: 'Carlos'
});

getData(null, 2, null, "fertig")

var count = 0;


function getData(id = null, produkt_id = null, produkt_anzahl = null, status = null) {
    connection.connect(function (error) {
        if (error) throw error;
        var query = "SELECT id, produkt_id, produkt_anzahl, status FROM Auftrag";

        if (id !== null) {
            if (count == 0) {
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

        console.log(query);
        connection.query(query, function (err, result, fields) {
            if (err) throw err;
            console.log(result);
            connection.end();
        });
    });
}

function check(query) {
    if (count == 0) {
        query += " Where"
    } else {
        query += " and"
    }
    return query;
}