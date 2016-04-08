/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'util/util', 'util/validation', 'util/component', 'userModel', 'userDetailModel', 'genericModalView', 'hbs!tpl/registerScreen', 'hbs!tpl/accountConfirmationModal'], 
    function($, backbone,util,validate,component,userModel,userDetailModel,genericModalView,registerScreen,accountConfirmationModal) {
    'use strict';
    
    var RegisterView = Backbone.View.extend({
    	el : '#container',

        events : {
            'click .signUp': 'registerUser',
            'click .backtoSign': 'backtoSign',
            'blur #phone': 'validatePhone',
            'blur #name': 'validateName',
            'blur #password': 'validatePassword',
            'blur #rePassword': 'validateRePassword',
            'blur #email': 'validateEmail',
            'blur #dob': 'validateDOB',
            'blur #sex': 'validateSex'
    	},
    	
    	initialize: function () {
            try{
                this.genericModalView = new genericModalView();
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },
        
        render : function(){
            try{
            	this.$el.html(registerScreen());
                util.setMaxDate('dob');
            }catch(e){
                console.log('error in render --> ' + e);    
            }
        },

        validatePhone: function(evt){
            validate.validatePhone(evt);
        },

        validateName: function(evt){
            validate.validateName(evt);    
        },

        validateSex: function(evt){
            validate.validateSex(evt);
        },

        validatePassword: function(evt){
            validate.validatePassword(evt);
        },

        validateRePassword: function(evt){
            validate.validateRePassword(evt);
        },

        validateEmail: function(evt){
            validate.validateEmail(evt);
        },

        validateDOB: function(evt){
            validate.validateDOB(evt);
        },

        registerUser: function(){
            try{
                util.showLoader();
                $('.registerSection input').each(function(){
                    $(this).filter(':visible').blur();
                });

                $('.registerSection select').blur();

                if($('.registerSection .inputError').length > 0){
                    util.hideLoader();
                    $(window).scrollTop($('.registerSection .inputError').first().offset().top); 
                    return true;
                }

                var registerJson = util.serializeObject($('form'));
                registerJson.inletType = 'SELF';
                registerJson.phone = '+91' + registerJson.phone;
                delete registerJson['userRePassword'];
                var url = config.root + '/register';
                util.jqueryPost(url , registerJson , this.registerUserCallback , this.registerUserErrorCb , this);

            }catch(e){
                console.log('error in registerUser -->' + e)
            }
        },

        registerUserCallback: function(response , input , obj){
            try{
                userModel.set(response);
                userModel.set('userSignedIn' , true);
                window.localStorage.setItem('authToken' , userModel.get('token'));
                window.localStorage.setItem('userModelLocal' , JSON.stringify(userModel.attributes));
                var self = obj;
                util.hideLoader();
                obj.genericModalView.render(accountConfirmationModal , 'accountConfirmationModal');
            }catch(e){

            } 
        },

        registerUserErrorCb: function(response , input , obj){
            if(response.status === 409){
                obj.$el.find('.regError').html("User already exist.");
                obj.$el.find('.regError').show();
                util.hideLoader();
            }else{
                obj.$el.find('.regError').html("Your Registration didn't went through, please try again. Inconvinience Regereted");
                obj.$el.find('.regError').show();
                util.hideLoader();
            }
        },

        backtoSign: function(){
            window.location.hash = 'login';
        }
    });
    return RegisterView;
});