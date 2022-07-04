const sql = require('../public/javascript/connect.js')
const express = require("express")
const app = express()
app.use(express.static('public'));

app.get("/", (req, res) => {
    res.render('index')
})

app.get("/getOrders", (req, res) => {
    const id = req.query.id;
    const produkt_id = req.query.pId;
    const produkt_anzahl = req.query.pAnz;
    const status = req.query.status;
    sql.getJsonData(id, produkt_id, produkt_anzahl, status).then((result) => {
        res.send(result)
    })
})

app.set('view engine', 'pug')

app.listen(3000)