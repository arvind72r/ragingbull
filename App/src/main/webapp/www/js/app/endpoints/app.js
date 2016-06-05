/*jslint browser:true*/
/*global require*/
require(['jquery', 'backbone', 'appRouter', 'userModel','userDetailModel'],
       function($, Backbone, Router, userModel, userDetailModel) {
    'use strict';

    $.support.cors = true;



    var router = new Router();

	Backbone.history.start();

	if(userModel.get('userSignedIn')){
		var hash = '';
		if(window.location.hash === ''){
			if(userDetailModel.get('email') === 'admin@aredvi.com'){
                hash = 'addDoctor';    
            }else{
                hash = 'dashboard';
            }
			Backbone.history.navigate(hash,{trigger:true, replace: true});
		}
	}else{
		Backbone.history.navigate('login',{trigger:true, replace: true});
	}

});