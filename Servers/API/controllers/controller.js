var mongoose = require('mongoose');

// Import the database schema
var UserInfo = mongoose.model('profile');

// Export functions
var exports = module.exports = {};

/**
 * Function to delete the history
 * @param req request
 * @param res responde
 */
exports.delHistory = function(req,res) {
    // Find the user with the corresponding ID
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // Iterate through the history list
        user.history.forEach(function(element,index) {
            // Check if the current element has the same id as the requested one
            if (element.id == req.body.id) {
                // Remove the history from the array
                user.history.splice(index,1);

                // Save to the profile
                user.save(function(err) {
                    if (!err) {
                        // Success
                        res.sendStatus(200);
                    } else {
                        // Forbidden
                        res.sendStatus(403);
                    }
                })
            }
        })
    })
}

/**
 * Function to retrieve the history
 * @param req request
 * @param res response
 */
exports.getHistory = function(req,res) {
    // Find the user with the corresponding ID
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // Iterate through the history list
        user.history.forEach(function(element) {
            // Check if the current element has the same id as the requested one
            if (element.id == req.body.id) {
                // Return the history
                res.send(element);
            }
        })
    })
}

/**
 * Function to update the history
 * @param req request
 * @param res response
 */
exports.updateHistory = function(req,res) {
    // Find the user with the corresponding ID
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // Iterate through the history list
        user.history.forEach(function(element) {
            // Check if the current element has the same id as the requested one
            if (element.id == req.body.id) {
                // Modify the given key with the give value
                element[req.body.key] = req.body.value;
                // Save to the profile
                user.save(function (err) {
                    if (!err) {
                        // OK
                        res.sendStatus(202);
                    } else {
                        // Forbidden
                        res.sendStatus(403);
                    }
                })
            }
        })
    })
}

/**
 * Function to create a new history trip
 * @param req request
 * @param res response
 */
exports.createHistory = function(req,res) {
    // Find the user with the corresponding ID
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // If found
        if(!err) {

            // Create a new history variable with the given values
            var history = {
                "id":req.body.id,
                "date": {
                    "year":req.body.year,
                    "month":req.body.month,
                    "day":req.body.day
                },
                "location":{
                    "name":req.body.name,
                    "latitude":req.body.latitude,
                    "longitude":req.body.longitude,
                    "type": req.body.type
                },
                "locationRating":req.body.locationRating,
                "tripRating":req.body.tripRating
            }

            // Push the history to the user profile
            user.history.push(history);

            // Save to the user profile
            user.save(function (err) {
                if (!err) {
                    // Created
                    res.sendStatus(201);
                } else {
                    // Forbidden
                    res.sendStatus(403);
                }
            });
        } else {
            // Forbidden
            res.sendStatus(403);
        }
    });
};

/**
 * Function to retrieve all the histories from the user profile
 * @param req
 * @param res
 */
exports.getAllHistory = function(req,res) {
    // Find the user with the corresponding ID
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // Send the history list if user profile is found
        if (!err && user != null) {
            res.send(user.history);
        } else {
            // Forbidden
            res.sendStatus(403);
        }
    })
}

/**
 * Function to retrieve all scheduled trips from the user profile
 * @param req
 * @param res
 */
exports.getAllPlan = function(req,res) {
    // Find the user with the corresponding ID
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // Send the scheduled trip list if user profile is found
        if (!err && user != null) {
            res.send(user.schedule);
        } else {
            // Forbidden
            res.sendStatus(403);
        }
    })
}

/**
 * Function to create a new scheduled trip and push it to the user profile
 * @param req
 * @param res
 */
exports.createPlan = function(req,res) {
    // Find the user with the corresponding ID
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // If found
        if(!err) {
            // Create a plan variable with the given values
            var plan = {
                "id":req.body.id,
                "datetime": {
                    "year":req.body.year,
                    "month":req.body.month,
                    "day":req.body.day,
                    "hour":req.body.hour,
                    "minute":req.body.minute
                },
                "location":{
                    "name":req.body.name,
                    "latitude":req.body.latitude,
                    "longitude":req.body.longitude,
                    "type": req.body.type
                }
            }

            // Push the variable to the scheduled trip list
            user.schedule.push(plan);

            // Save it to the user profile
            user.save(function (err) {
                if (!err) {
                    // Created
                    res.sendStatus(201);
                }
            });
        } else {
            // Forbidden
            res.sendStatus(403);
        }
    });
};

/**
 * Function used to remove a schedule trip with the corresponding id from the user profile
 * @param req
 * @param res
 */
exports.delPlan = function(req,res) {
    // Find the user with the corresponding id
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // Iterate through the scheduled trip list
        user.schedule.forEach(function(element,index) {
            // Check if the id of the current element has the same id as the requested one
            if (element.id == req.body.id) {
                // Remove the element from the list
                user.schedule.splice(index,1);

                // Save the list to the user profile
                user.save(function(err) {
                    if (!err) {
                        // OK
                        res.sendStatus(200);
                    } else {
                        // Forbidden
                        res.sendStatus(403);
                    }
                })
            }
        })
    })
}

/**
 * Function to retrieve the scheduled trip with the corresponding id from the user profile
 * @param req
 * @param res
 */
exports.getPlan = function(req,res) {
    // Find the user with the corresponding id
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // Iterate through the scheduled trip list
        user.schedule.forEach(function(element) {
            // check if the current element has the same id as the requested one
            if (element.id == req.body.id) {
                // Send the current element
                res.send(element);
            }
        })
    })
}

/**
 * Function to update the values of a certain scheduled trip
 * @param req
 * @param res
 */
exports.updatePlan = function(req,res) {
    // Find the user with the corresponding id
    UserInfo.findOne({"userID": req.body.userID},function(err,user) {
        // Iterate through the scheduled trip list
        user.schedule.forEach(function(element) {
            // check if the current element has the same id as the requested one
            if (element.id == req.body.id) {
                // Modify the given key with a given value
                element[req.body.key] = req.body.value;
                // Save it to the user profile
                user.save(function (err) {
                    if (!err) {
                        // Accepted
                        res.sendStatus(202);
                    } else {
                        // Forbidden
                        res.sendStatus(403);
                    }
                })
            }
        })
    })
}

/**
 * Function to retrieve the user profile
 * @param req
 * @param res
 */
exports.getProfile = function(req,res) {
    // Find the user with the corresponding id
    UserInfo.findOne({userID:req.body.userID},function(err,user) {
        // Send the user if found
        if (!err && user != null) {
            res.send(user);
        } else {
            // Forbidden
            res.sendStatus(403);
        }
    })
}

/**
 * Function to create a user profile
 * @param req
 * @param res
 */
exports.createProfile = function(req,res) {

    // Create a variable with the given values
    var newProfile = new UserInfo({
        "userID":req.body.userID,
        "survey":{
            "textSize":req.body.textSize,
            "walking": req.body.walking,
            "userData": req.body.userData,
        }
    });

    // Attempt to find a user with the requested id
    UserInfo.findOne({userID: req.body.userID},function(err,user) {
            // If an error occurs, throw Forbidden
            if (err)  {
                res.sendStatus(403);
                return null;
            }
            // If no user is found
            if (user == null) {
                // Save the user profile to the database
                newProfile.save(function (err) {
                    if (err) {
                        // Forbidden
                        res.sendStatus(403);
                    } else {
                        // Created
                        res.sendStatus(201);
                    }
                })
            }
        })
    };

/**
 * Function to update the user profile
 * @param req
 * @param res
 */
exports.updateProfile = function(req,res) {
    // Find the user with the corresponding id
    UserInfo.findOne({userID:req.body.userID}, function(err, result) {
        // If an error occurs, throw Forbidden
        if (err) {
            res.sendStatus(403);
            return null;
        } else {
            // Update the given key in the survey list with the give value
            result["survey"][req.body.key] = req.body.value;
            // Save it to the user profile
            result.save(function (err) {
                if (!err) {
                    // Accepted
                    res.sendStatus(202);
                } else {
                    // Forbidden
                    res.sendStatus(403);
                }
            })
        }
    })
}

/**
 * Function to request a instruction list from the mapbox api
 * @param req
 * @param res
 */
exports.direction = function(req,res) {
    // Import the request module
    const request = require('request')
    // URL of the mapbox api
    var url = "https://api.mapbox.com/directions/v5/mapbox/walking/"

    // Add queries with the given values
    url += req.body.curLongitude + "," + req.body.curLatitude + ";";
    url += req.body.desLongitude + "," + req.body.desLatitude + "?";

    // API key stored in Heroku Configs
    url += "steps=true&access_token=" + process.env.mapboxapi;

    // Send the request to the mapbox server
    request(url,{json:true}, function(err,obj) {
        if (err) res.send(err);

        // Create a result list
        var result = []
        // Attempt to parse the result and append it to the list
        for (var v = 0; v < obj.body.routes.length;v++ ) {
            for (var i = 0;i < obj.body.routes[v].legs.length; i++)
            {
                for (var j =0;j< obj.body.routes[v].legs[i].steps.length; j++) {
                    result.push(obj.body.routes[v].legs[i].steps[j].maneuver);
                }
            }
        }
        // Send the result list
        res.send(result);
    })
}

/**
 * Function to search for nearby landmarks from the google api
 * @param req
 * @param res
 */
exports.search = function(req,res) {
    // import the request module
    const request = require('request')

    // URL for the google api
    var url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

    //Add queries
    url += "location=" + req.body.latitude + "," + req.body.longitude + "&";
    url += "radius=1500&";
    url += "type=" + req.body.pType + "&";
    url += "opennow=true&";

    // API key stored in Heroku Configs
    url += "key=" + process.env.gapi;

    // Send the request to the api
    request(url,{
        json:true}, function (err,obj) {
        if (err) res.send(err);

        // Create a result list
        var result = [];

        // Attempt to parse the result and append it to the list
        for (var i = 0;i < obj.body.results.length; i++) {
            if (obj.body.results[i].opening_hours.open_now == false) continue;
            result[i] = {
                "name":obj.body.results[i].name,
                "address":obj.body.results[i].vicinity,
                "location":obj.body.results[i].geometry.location,
                "rating":obj.body.results[i].rating
            }
        }

        // Store the status of the result (In case of empty result)
        var parent = {"status":obj.body.status,"results":result};

        // Send the result
        res.send(parent);
    })
};


