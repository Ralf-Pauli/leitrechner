const path = require("path")
const express = require("express")
const app = express()
const livereload = require("livereload")
const liveReloadServer = livereload.createServer()
liveReloadServer.watch(path.join(__dirname, 'public'))
const connectLivereload = require("connect-livereload")
app.use(connectLivereload())
liveReloadServer.server.once("connection", () => {
    setTimeout(() => {
        liveReloadServer.refresh("/");
    }, 100);
});

app.use(express.static('public'));

app.get("/", (req, res) => {
    res.render('index')
})

app.get("/print", (req, res) => {
    res.send("Alright")
})

app.set('view engine', 'pug')

app.listen(80)
