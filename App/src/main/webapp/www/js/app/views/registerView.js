define(function(require) {
    'use strict';    
    var $ = require("jquery"),
        _ = require("underscore"),
        Backbone = require("backbone"),
        userModel = require("userModel"),
        userDetailModel = require("userDetailModel"),
        util = require("util/util");

    var registerScreen  =  require("hbs!tpl/registerScreen");
    var accountConfirmationModal = require("hbs!tpl/accountConfirmationModal");
    
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
                
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },
        
        render : function(){
            try{
            	this.$el.html(registerScreen());
                this.setMaxDate();
            }catch(e){
                console.log('error in render --> ' + e);    
            }
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
            $('#dob').attr('max' , maxDateString);
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

        validateName: function(evt){
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

        validateSex: function(evt){
            var value = $(evt.target).val();
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).parent().addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else{
                $(evt.target).parent().removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validatePassword: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else if(value.length < 8){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Minimum 8 characters');
            }else{
                $(evt.target).removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validateRePassword: function(evt){
            var value = $.trim($(evt.target).val());
            var orignalValue = $.trim($('#password').val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $(evt.target).prev().addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else if(orignalValue !== value){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Password not matching');
            }else{
                $(evt.target).removeClass('inputError');
                $(evt.target).prev().removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validateEmail: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value !== ''){
                if(!(/^([\w-+]+(?:\.[\w-+]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/.test(value))){
                    $(evt.target).addClass('inputError');
                    $('#'+errorContainer).html('Invalid Email');
                }else{
                    $(evt.target).removeClass('inputError');
                    $('#'+errorContainer).html('');
                }
            }else{
                $(evt.target).removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validateDOB: function(evt){
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

                $('#modalScreen').html(accountConfirmationModal());
                $('#accountConfirmationModal').modal('show');

                $('#accountConfirmationModal .skipNow').unbind('click').bind('click' , function(){
                    util.showLoader();
                    $('#accountConfirmationModal').modal('hide');
                    util.jqueryGet( config.root + '/user/me?hydrated=true' , self.getUser , self.getUserError , self);
                });
                $('#accountConfirmationModal .verifyNow').unbind('click').bind('click' , function(){
                    util.showLoader();
                    var verifyId = $('#accountConfirmationModal .verificationToken').val();
                    var url = config.root + '/register/'+verifyId+'/verifyId';
                    util.jqueryPost(url , '' , self.proceed , self.invalidVerification , self);
                });
            }catch(e){

            } 
        },

        registerUserErrorCb: function(response , input , obj){
            obj.$el.find('.regError').html("Your Registration didn't went through, please try again. Inconvinience Regereted");
            obj.$el.find('.regError').show();
        },

        proceed: function(response , obj){
            $('#accountConfirmationModal').modal('hide');
            userModel.set('isUserVerified' , true);
            window.localStorage.setItem('userModelLocal' , JSON.stringify(userModel.attributes));
            util.jqueryGet( config.root + '/user/me?hydrated=true' , obj.getUser , obj.getUserError , obj);
        },

        getUser:function(response , obj){
            userDetailModel.set(response);
            config.userSession = true;
            window.localStorage.setItem('userDetailModelLocal' , JSON.stringify(userDetailModel.attributes));
            window.location.hash = 'dashboard';
        },

        invalidVerification: function(response , obj){
            $('#avError .errorText').html('Seems you have entered wrong verification code');    
            $('#avError').show();
        },

        backtoSign: function(){
            window.location.hash = 'login';
        }
    });
    return RegisterView;
});