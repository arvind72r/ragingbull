/*jslint browser:true*/
/*global require*/
require(['jquery', 'backbone', 'appRouter', 'userModel'],
       function($, Backbone, Router, userModel) {
    'use strict';

    $.support.cors = true;



    var router = new Router();

	Backbone.history.start();

	if(userModel.get('userSignedIn')){
		var hash = '';
		if(window.location.hash === ''){
			hash = 'dashboard';
			Backbone.history.navigate(hash,{trigger:true, replace: true});
		}
	}else{
		Backbone.history.navigate('login',{trigger:true, replace: true});
	}

});