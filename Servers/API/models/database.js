var mongoose = require('mongoose');

var db_url = process.env.dburl; // database url
var db_account = process.env.dbac; // database account
var db_password= process.env.dbpw; // database password
var url = 'mongodb://'+db_account+':'+db_password+'@'+ db_url + db_account;

// Attempt to connect to the database
mongoose.connect(url,{useNewUrlParser: true}).then(
    () => {console.log("Connected to database")},
    err => {console.log("Error: Failed to connect to database")});

// Use the schema
require('./user.js');