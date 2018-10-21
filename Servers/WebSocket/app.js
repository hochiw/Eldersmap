
// Required modules
var WebSocketServer = require('ws').Server
var url = require('url');
var express = require("express")
var http = require("http")

// Connection list
var connections = {};
var app = express()

// Port for the server
var port = process.env.PORT || 8080

// Run the server
var server = http.createServer(app)
server.listen(port)

console.log("Server running on %d",port);

// Create a websocket server based on the running server
var wsServer = new WebSocketServer({server:server})
console.log("Websocket initiated")

/**
 * Check if the server is alive
 */
function heartbeat() {
    this.isAlive = true;
}

/**
 * Empty function used to ping
 */
function noop() {};

/**
 * Function that is called when a websocket client is connected
 */
wsServer.on('connection',function connection(ws,req) {
    // Set the status of the client to be alive
    ws.isAlive = true;

    // Make sure to pong back when the client pings
    ws.on('pong', heartbeat);

    // Attempt to parse the url query
    const queries = url.parse(req.url,true).query;

    // Check the user type
    switch(queries.type) {
        // If the user is a client
        case "client":
            // Assign "client" to the type variable
            ws.type = "client";

            // Check if there is any admin available
            if (Object.keys(connections).length != 0) {
                // Iterate the connection list and find an available admin
                for (var key in connections) {
                    if (connections[key].client == null) {
                        // Assign the id of the admin to the client so that they have the same id
                        ws.id = key;
                        // Pair the client with the admin
                        connections[key].client = ws;

                        // Send a message to the client telling them their id
                        var o = '{"type":"6","content":"' + ws.id + '"}';
                        console.log(ws.type + ws.id + " connected");
                        ws.send(o);

                        // Send a message to the client telling them they are connected
                        var connected = '{"type":"2","content": "Connected with Admin' + ws.id + '"}';
                        ws.send(connected);

                        // Send a message to the admin telling them they are connected
                        var adminConnected = '{"type":"2","content": "Connected with Client ' + ws.id + '"}';
                        connections[key].admin.send(adminConnected);
                        break;
                    }
                }
            } else {
                // Send a message to the client if no available admin is online
                ws.send('{"type":"2","content":"No available admin at the moment, please try later"}');
            }
            break;
        // if the connected user is an admin
        case "admin":
            // Assign "admin" to the type variable
            ws.type = "admin";

            // Generate a random id
            ws.id = Math.floor(Math.random() * Math.floor(99999999));

            // Send a message to the admin telling them their id
            var o = '{"type":"6","content":"' + ws.id + '"}';
            ws.send(o);

            // Append the admin to the waiting list
            connections[ws.id] = {"admin":ws,"client":null};

            // Send a message to the admin telling them they are connected
            var connected = '{"type":"2","content": "Welcome, Your id is ' + ws.id + '"}';
            ws.send(connected);
            break;
    }

    /**
     * Function to handle the message sent by the clients
     */
    ws.on("message",function incoming(message) {
        // Make sure the user has an ID
        if (ws.id != null) {
            // Iterate through the waiting list and find the user
            for (var key in connections) {
                if (key == ws.id) {
                    // Check the type of the user
                    switch (ws.type) {
                        // If the user is an admin then send the message to their paired client and vice versa
                        case "admin":
                            if (connections[key].client != null) {
                                connections[key].client.send(message);
                            }
                            break;
                        case "client":
                            if (connections[key].admin != null) {
                                connections[key].admin.send(message);
                            }
                            break;
                    }
                }
            }
        }
    });

    /**
     * Handles disconnections
     */
    ws.on("close", function() {
        // Check the type of the user
        switch(ws.type) {
            // Remove the entire dictionary if the admin is disconnected
            case "admin":
                delete connections[ws.id];
                break;
            // Set the client variable of the pair to null so that the admin is available again
            case "client":
                if (connections[ws.id] != null) {
                    connections[ws.id].client = null;
                }
                break;
        }
        console.log("Disconnected");
    })
});

/**
 * A function that pings the client to check if they are still connected every 5 seconds
 */
setInterval(function ping() {
    // Iterate through the client list
    wsServer.clients.forEach(function each(ws) {
        // Terminate the connection between the client if they are disconnected
        if (ws.isAlive === false) return ws.terminate();

        ws.isAlive = false;
        ws.ping(noop);
    });
}, 5000);



