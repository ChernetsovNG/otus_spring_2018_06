var express = require("express");

var app = express();

app.use("/node_modules", express.static("/usr/src/library-info-app/node_modules"));
app.use("/", express.static("/usr/src/library-info-app/app"));

app.listen(3000, function () {
    console.log("HTTP Server running on port 3000");
});
