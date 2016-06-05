/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'util/util', 'util/validation', 'util/component', 'userModel', 'userDetailModel'], 
    function($, backbone,util,validate,component,userModel,userDetailModel) {
    'use strict';

    var GenericModalView = Backbone.View.extend({
    	el : '#modalScreen',

    	events : {
            'click .miUpdate': 'updateMissingInfo',
            'blur #miPhone': 'validatePhone',
            'blur #miDob': 'validateDOB',
            'click .fpSend': 'sendResetPwdLink',
            'blur #fpPhone': 'validatePhone',
            'click .skipNow': 'skipVerificationNow',
            'click .verifyNow': 'doVerification'
    	},

    	skipVerificationNow: function(){
    		util.showLoader();
            $('#accountConfirmationModal').modal('hide');
            if(window.location.hash === '#register'){
                component.fetchUser();
            }else{
                util.hideLoader();
            }
    	},

    	doVerification: function(){
    		util.showLoader();
            var verifyId = $('#accountConfirmationModal .verificationToken').val();
            var url = config.root + '/register/'+verifyId+'/verifyId';
            util.jqueryPost(url , '' , this.proceed , this.invalidVerification , this);
    	},

    	proceed: function(response , obj){
            $('#accountConfirmationModal').modal('hide');
            userModel.set('isUserVerified' , true);
            window.localStorage.setItem('userModelLocal' , JSON.stringify(userModel.attributes));
            component.fetchUser();
        },

        invalidVerification: function(response , obj){
            $('#avError .errorText').html('Seems you have entered wrong verification code');    
            $('#avError').show();
        },

    	validatePhone: function(evt){
    		validate.validatePhone(evt);
    	},

    	validateDOB: function(evt){
    		validate.validateDOB(evt);
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
            util.jqueryPut(url , input , this.updatedPhone , this.updatedMIError , this);
        },

        updatedPhone: function(response , input , obj){
            userModel.set('phone',input.value);
            var input = {};
            input.value = $('#miDob').val();

            var url = config.root + '/user/me/modify/dob';
            util.jqueryPut(url , input , obj.updatedDob , obj.updatedMIError , obj);
        },

        updatedMIError: function(response , input, obj){
            $('#avError .errorText').html('We are Facing some technical issue please try again.');
            $('#avError').show();
            util.hideLoader();
        },

        updatedDob: function(response , input , obj){
            $('#missingInfoModal').modal('hide');
            component.fetchUser();
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

            util.jqueryPost(url , '', this.sendResetPwdLinkSCB , this.sendResetPwdLinkECB , this);
        },

        sendResetPwdLinkSCB: function(response, input , obj){
            if(response.id === -1 ){
            	$('#forgotPasswordModal #fpError').show();
            	$('#forgotPasswordModal #fpError').find('.errorText').html('Phone number does not exist in our system.');	
            	return false;
            }
            $('#forgotPasswordModal .modal-body').html('We have succesfully sent the reset password link to your mobile. Please follow the instruction and reset your password');
            $('#forgotPasswordModal .modal-footer .fpSend').html('Ok');//.attr('data-dismiss','modal');
        },

        sendResetPwdLinkECB: function(response, input, obj){
            $('#forgotPasswordModal #fpError').show();
            $('#forgotPasswordModal #fpError').find('.errorText').html('We are Facing some technical issue please try again.');
        },

    	render: function(template , container){
    		this.$el.html(template());
    		this.$el.find('#'+container).modal('show');
    		if(container === 'missingInfoModal'){
    			util.setMaxDate('miDob');
    		}
    	}
	});

	return GenericModalView;
});