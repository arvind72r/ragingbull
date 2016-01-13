/*jslint browser:true*/
/*global require*/
require(['jquery', 'backbone', 'appRouter'],
       function($, Backbone, Router) {
    'use strict';

    $.support.cors = true;



    var router = new Router();

	Backbone.history.start();
});