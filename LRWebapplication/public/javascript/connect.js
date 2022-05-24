const { json } = require('express');
let mysql = require('mysql');
let connection = mysql.createConnection({
    host: 'devel1',
    user: 'root',
    password: 'the27',
    database: 'Carlos'
});


//-------------

function resolveAfter2Seconds(x) {
    return x
  }
  
  async function f1() {
    var x = await getData(120, null, null, null, convertToJson);
    console.log(x);
  }
  
  async function f2() {
    await f1();
    let x = 3;
    console.log(x);
  };
  
  f2();

//-------------

//haha();

function getJsonString() {
    console.log('JSONString of getjson');
    console.log(jsonString)
    return jsonString;
}

async function convertToJson(stringToConvert) {
    jsonString = Object.values(JSON.parse(JSON.stringify(stringToConvert)));
    console.log(jsonString);
}

async function haha() {
    await shit(120, null, null, null, getJsonString);
    console.log(jsonString)
}

async function shit(id = null, produkt_id = null, produkt_anzahl = null, status = null, callback) {
    await getData(id, produkt_id, produkt_anzahl, status, convertToJson);
    console.log('after?');
}

var jsonString = null;

let count = 0;

function getData(id = null, produkt_id = null, produkt_anzahl = null, status = null, callback) {
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
        var data;
        connection.query(query, function (err, result, fields) {
            if (err) throw err;
            try {
                callback(result);
                data = result;
            } catch (error) {
                throw error;
            }
            connection.end();
        });
        return data;
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

module.exports = { getData };