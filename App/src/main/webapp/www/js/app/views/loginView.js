/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'util/util', 'util/validation', 'util/component', 'userModel', 'userDetailModel', 'fb', 'facebook', 'google', 'genericModalView', 'hbs!tpl/loginScreen', 'hbs!tpl/fpModal', 'hbs!tpl/socialMissingInfoModal'], 
    function($, backbone,util,validate,component,userModel,userDetailModel,fb,fbs,gpl,genericModalView,loginScreen,fpModal,missingInfoModal) {
    'use strict';
    
    var self;
    
    var LoginView = Backbone.View.extend({
    	el : '#container',

        events : {
            'click .signInAction': 'signIn' ,
            'click .fpLink': 'forgorPassword',
            'click .signup-fb': 'facebookLogin',
            'click .signup-g': 'googleLogin'
    	},
    	
    	initialize: function () {
            try{
                self = this;
                this.genericModalView = new genericModalView();
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },

        facebookLogin: function(e){
            e.preventDefault();
            var that = this;
            if(window.location.protocol === 'http:' || window.location.protocol === 'https:'){
                fbs.login(function(response){
                    that.handleFBLogin(response);
                },{scope: 'public_profile,email'});
            }else{
                facebookConnectPlugin.login( ["public_profile,email"],
                    function (response) { that.handleFBLogin(response); },
                    function (response) { console.log(JSON.stringify(response)) });
            }
        },

        googleLogin: function(e){
            e.preventDefault();
            var that = this;
            if(window.location.protocol === 'http:' || window.location.protocol === 'https:'){
                var clientId = '665618557585-eimognveu2lunulgv8ff1810qrc8ifqf.apps.googleusercontent.com';
                var apiKey = 'AIzaSyD4hT_S83gLFXoHKXG4ftClxZWRra9Fq6Q';
                var scopes = 'email';
                gapi.client.setApiKey(apiKey);
                gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, self.handleAuthResult);
            }else{
                window.plugins.googleplus.login( {},
                    function (obj) {
                        self.googleUserRegister(obj);
                    },
                    function (msg) {
                        //document.querySelector("#feedback").innerHTML = "error: " + msg;
                });
            }
        },

        handleAuthResult: function(authResult){
            if (authResult && !authResult.error) {
                self.makeApiCall();
            } else {
                if(authResult.error === 'access_denied'){
                    return false;
                }
                self.googleAuth()
            }
        },

        googleAuth: function(){
            var clientId = '665618557585-eimognveu2lunulgv8ff1810qrc8ifqf.apps.googleusercontent.com';
            var scopes = 'email';
            gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: false}, self.handleAuthResult);    
        },

        makeApiCall: function() {
            gapi.client.load('plus', 'v1' , self.test );
            gapi.client.load('plus', 'v1').then(function() {
                var request = gapi.client.plus.people.get({
                    'userId': 'me',
                    'collection': 'visible'
                });
                request.then(function(resp) {
                    self.registerGoogleUser(resp);
                }, function(reason) {
                    console.log('Error: ' + reason.result.error.message);
                });
            });
        },

        googleUserRegister: function(resp){
            util.showLoader();
            var regInput = {};
            regInput.name = resp.displayName;
            regInput.password = "DummyPassword";
            regInput.email = resp.email;
            regInput.phone = "DummyPhoneNo";
            regInput.inletType = "google";
            regInput.sex = resp.gender;
            regInput.dob = "1901-1-1";

            var url = config.root + '/register/oauth';
            util.jqueryPost(url , regInput , this.registerUserCallback , this.registerUserErrorCb , this);
        },

        registerGoogleUser: function(resp){
            util.showLoader();
            var regInput = {};
            var primaryEmail;
            for (var i=0; i < resp.result.emails.length; i++) {
                if (resp.result.emails[i].type === 'account') primaryEmail = resp.result.emails[i].value;
            }
            regInput.name = resp.result.displayName;
            regInput.password = "DummyPassword";
            regInput.email = primaryEmail;
            regInput.phone = "DummyPhoneNo";
            regInput.inletType = "google";
            regInput.sex = resp.result.gender;
            regInput.dob = "1901-1-1";

            var url = config.root + '/register/oauth';
            util.jqueryPost(url , regInput , this.registerUserCallback , this.registerUserErrorCb , this);
        },



        handleFBLogin: function(response){
            if(response.status === "unknown"){return false;}

            var that = this;
            if(window.location.protocol === 'http:' || window.location.protocol === 'https:'){
                fbs.api('/me', 'get', { fields: 'id,name,email,cover,gender' }, function(response) {
                    that.registerFacebookUser(response);
                });
            }else{
                facebookConnectPlugin.api( "me/?fields=id,name,email,cover,gender", ["user_birthday"],
                    function (response) { that.registerFacebookUser(response); },
                    function (response) { alert(JSON.stringify(response)) });
            }
        },

        registerFacebookUser: function(response){
            util.showLoader();
            var regInput = {};
            regInput.name = response.name;
            regInput.password = "DummyPassword";
            regInput.email = response.email;
            regInput.phone = "DummyPhoneNo";
            regInput.inletType = "facebook";
            regInput.sex = response.gender;
            regInput.dob = "1901-1-1";

            var url = config.root + '/register/oauth';
            util.jqueryPost(url , regInput , this.registerUserCallback , this.registerUserErrorCb , this);


        },

        registerUserCallback: function(response , input , obj){
            userModel.set(response);
            userModel.set('userSignedIn' , true);
            window.localStorage.setItem('authToken' , userModel.get('token'));
            window.localStorage.setItem('userModelLocal' , JSON.stringify(userModel.attributes));
            if(response.phone === 'DummyPhoneNo' || response.phone === null){
                util.hideLoader();
                obj.genericModalView.render(missingInfoModal , 'missingInfoModal');
            }else{
                component.fetchUser();
            }
        },

        registerUserErrorCb: function(response , input , obj){
            obj.$el.find('.signup-other-error').html("Currently we are unable to proceed with social sign-on please try again later.");
            obj.$el.find('.signup-other-error').show();
            util.hideLoader();
        },

        signIn: function(){
            util.showLoader();
            var loginObj = {};
            loginObj.username = $.trim($('#userId').val());
            loginObj.password = $.trim($('#password').val());
            var url = config.root + '/auth/login';
            util.jqueryAuthPost(url , loginObj , this.signInCB , this.signInErrorCB , this);
        },

        signInCB: function(response , obj){
            userModel.set(response);
            userModel.set('userSignedIn' , true);
            window.localStorage.setItem('authToken' , userModel.get('token'));
            window.localStorage.setItem('userModelLocal' , JSON.stringify(userModel.attributes));
            component.fetchUser();
        },

        signInErrorCB: function(response , obj){
            util.hideLoader();
            $('.userDetails .globalError').html('Invalid Credentials');
        },


        forgorPassword: function(){
            this.genericModalView.render(fpModal , 'forgotPasswordModal')
        },

        render : function(){
            try{
                if(userModel.get('userSignedIn')){
            	   window.location.hash = 'dashboard';
                }else{
                    var data={};
                    if(localStorage.getItem('getUserError')){
                        localStorage.removeItem('getUserError');
                        data.errorMessage = 'We are facing some technical issue please login again.'
                    }
                    this.$el.html(loginScreen(data));
                }
            }catch(e){
                console.log('error in render --> ' + e);    
            }
        }
    });
    return LoginView;
});