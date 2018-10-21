var express = require('express');
var bodyParser = require('body-parser');
var app = express();


// Require the database connection
require("./models/database.js");

// Redirect the connections to the router
var router = require('./router/router.js');

app.use('/',router);
app.use(express.static(__dirname));
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))


// Start the server
const PORT = process.env.PORT || 4100;
app.listen(PORT, () => console.log(`Listening on ${ PORT }`));

