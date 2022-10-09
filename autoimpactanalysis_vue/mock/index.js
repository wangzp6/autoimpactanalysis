const express = require("express");
const app = express();
const userLogin = require("./data/user_login.json");
const adminLogin = require("./data/admin_login.json");
const adminPermission = require("./data/admin_permission.json");
const userPermission = require("./data/user_permission.json");
const url = require("url");

app.get("/autoimpactanalysis/login", (req, res) => {
    const user = url.parse(req.url, true).query.user;
    if (user === 'admin') {
        res.send(adminLogin)
    } else {
        res.send(userLogin)
    }
})

app.get("/autoimpactanalysis/permission", (req, res) => {
    const user = url.parse(req.url, true).query.user;
    if (user === 'admin') {
        res.send(adminPermission)
    } else {
        res.send(userPermission)
    }
})


app.listen(3300, () => {
    console.log('服务器运行在3300');
})