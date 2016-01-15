define(function(require) {
    'use strict';    
    var $ = require("jquery"),
        _ = require("underscore"),
        Backbone = require("backbone"),
        util = require("util/util"),
        userModel = require("userModel"),
        userDetailModel = require("userDetailModel"),
        fb = require("fb"),
        fbs = require("facebook"),
        gpl = require("google");


    var loginScreen  =  require("hbs!tpl/loginScreen");
    var fpModal = require("hbs!tpl/fpModal");
    var missingInfo = require("hbs!tpl/socialMissingInfoModal");
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
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },

        facebookLogin: function(e){
            e.preventDefault();
            var that = this;
            if(window.location.protocol === 'http' || window.location.protocol === 'https'){
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
            if(window.location.protocol === 'http' || window.location.protocol === 'https'){
                var clientId = '665618557585-eimognveu2lunulgv8ff1810qrc8ifqf.apps.googleusercontent.com';
                var apiKey = 'AIzaSyD4hT_S83gLFXoHKXG4ftClxZWRra9Fq6Q';
                var scopes = 'email';
                gapi.client.setApiKey(apiKey);
                gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, self.handleAuthResult);
            }else{
                window.plugins.googleplus.login( {},
                    function (obj) {
                        //alert(JSON.stringify(obj));
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
            var that = this;
            if(window.location.protocol === 'http' || window.location.protocol === 'https'){
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
                $('#modalScreen').html(missingInfo());
                $('#missingInfoModal').modal('show');
                obj.bindMissingInfoEvent();
            }else{
                util.jqueryGet( config.root + '/user/me?hydrated=true' , obj.getUser , obj.getUserError , obj);
            }
        },

        bindMissingInfoEvent: function(){
            $('#missingInfoModal .miUpdate').bind('click' , this.updateMissingInfo);
            $('#missingInfoModal #miPhone').bind('blur' , this.validatePhone);
            $('#missingInfoModal #miDob').bind('blur', this.validateDob);
            this.setMaxDate();
        },

        setMaxDate: function(){
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            if (month < 10) {
                month = '0' + month;
            }
            var date = date.getDate();
            var maxDateString = year + '-' + month + '-' + date;
            $('#missingInfoModal #miDob').attr('max' , maxDateString);
        },

        validatePhone: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $(evt.target).prev().addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else if(!(/^\d{10}$/.test(value))){
                $(evt.target).addClass('inputError');
                $(evt.target).prev().addClass('inputError');
                $('#'+errorContainer).html('Invalid Mobile#');
            }else{
                $(evt.target).removeClass('inputError');
                $(evt.target).prev().removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validateDob: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else{
                $(evt.target).removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        updateMissingInfo: function(evt){
            util.showLoader();
            $('#missingInfoModal input').each(function(){
                $(this).filter(':visible').blur();
            });

            if($('#missingInfoModal .inputError').length > 0){
                util.hideLoader();
                return true;
            }

            var input = {};
            input.value = '+91' + $.trim($('#miPhone').val());

            var url = config.root + '/user/me/modify/phone';
            util.jqueryPut(url , input , self.updatedPhone , self.updatedPhoneErr , self);
        },

        updatedPhone: function(response , input , obj){
            userDetailModel.set('phone' , input.value);
            var input = {};
            input.value = $('#miDob').val();

            var url = config.root + '/user/me/modify/dob';
            util.jqueryPut(url , input , obj.updatedDob , obj.updatedDobErr , obj);
        },

        updatedDob: function(response , input , obj){
            userDetailModel.set('dob' , input.value);
            $('#missingInfoModal').modal('hide');
            util.jqueryGet( config.root + '/user/me?hydrated=true' , obj.getUser , obj.getUserError , obj);
        },

        getUser:function(response , obj){
            userDetailModel.set(response);
            config.userSession = true;
            window.localStorage.setItem('userDetailModelLocal' , JSON.stringify(userDetailModel.attributes));
            window.location.hash = 'dashboard';
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
            util.jqueryGet( config.root + '/user/me?hydrated=true' , obj.getUser , obj.getUserError , obj);
        },

        getUser:function(response , obj){
            userDetailModel.set(response);
            config.userSession = true;
            window.localStorage.setItem('userDetailModelLocal' , JSON.stringify(userDetailModel.attributes));
            window.location.hash = 'dashboard';
        },

        signInErrorCB: function(response , obj){
            util.hideLoader();
            $('.userDetails .globalError').html('Invalid Credentials');
        },


        forgorPassword: function(){
            $('#modalScreen').html(fpModal());
            $('#forgotPasswordModal').modal('show');
            this.bindFpEvent();
        },

        bindFpEvent: function(){
            $('#forgotPasswordModal .fpSend').bind('click' , this.sendResetPwdLink);
            $('#forgotPasswordModal #fpPhone').bind('blur' , this.validatePhone);
        },

        sendResetPwdLink: function(){
            if($('#forgotPasswordModal input').length == 0){
                $('#forgotPasswordModal').modal('hide');
                return true;
            }

            $('#forgotPasswordModal input').each(function(){
                $(this).filter(':visible').blur();
            });

            if($('#forgotPasswordModal .inputError').length > 0){
                return true;
            }

            var phone = $('#fpPhone').val();
            var url = config.root + '/auth/reset?id=' +phone;

            util.jqueryPost(url , '', self.sendResetPwdLinkSCB , self.sendResetPwdLinkECB , self);
        },

        sendResetPwdLinkSCB: function(response, input , obj){
            $('#forgotPasswordModal .modal-body').html('We have succesfully sent the reset password link to your mobile. Please follow the instruction and reset your password');
            $('#forgotPasswordModal .modal-footer .fpSend').html('Ok');//.attr('data-dismiss','modal');
        },

        sendResetPwdLinkECB: function(response, input, obj){
            $('#forgotPasswordModal #fpError').show();
            $('#forgotPasswordModal #fpError').find('.errorText').html('Number does not exist in our system');
        },
        
        render : function(){
            try{
                if(userModel.get('userSignedIn')){
            	   window.location.hash = 'dashboard';
                }else{
                    this.$el.html(loginScreen());
                }
            }catch(e){
                console.log('error in render --> ' + e);    
            }
        }
    });
    return LoginView;
});