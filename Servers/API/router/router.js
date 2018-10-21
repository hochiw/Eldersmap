var express = require('express');
var bodyParser = require('body-parser');
var controller = require('../controllers/controller.js');

var router = express.Router();

// Use bodyparser module
router.use(bodyParser.json());
router.use(bodyParser.urlencoded({extended: true}));

// Basic page for the api
router.get("/api",function(req,res) {
    res.send("This is the home page for the API");
})

// All the urls and the corresponding functions for the api
router.post('/api/createProfile', controller.createProfile);

router.post('/api/getProfile', controller.getProfile);

router.post('/api/updateProfile',controller.updateProfile);

router.post('/api/createHistory', controller.createHistory);

router.post('/api/getAllHistory', controller.getAllHistory);

router.post('/api/getHistory', controller.getHistory);

router.post('/api/delHistory', controller.delHistory);

router.post('/api/updateHistory', controller.updateHistory);

router.post('/api/createPlan', controller.createPlan);

router.post('/api/getAllPlan', controller.getAllPlan);

router.post('/api/getPlan', controller.getPlan);

router.post('/api/delPlan', controller.delPlan);

router.post('/api/updatePlan', controller.updatePlan);

router.post('/api/search',controller.search);

router.post('/api/route',controller.direction);

module.exports = router;