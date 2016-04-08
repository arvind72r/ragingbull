/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'util/util', 'util/validation', 'userDetailModel', 'addressModel', 'hbs!tpl/myProfile', 'hbs!tpl/changePasswordModal', 'hbs!tpl/addressView', 'hbs!tpl/addAddressModal'],
    function($, backbone,util,validate,userDetailModel,addressModel,myProfileScreen,changePwdModal,addressView,addAddressModal) {
    'use strict';

    var mySelf;
    
    var myProfileView = Backbone.View.extend({
    	el : '#profileView',

        events : {
            'click .changePwd' : 'changePwd',
            'blur #newEmail' : 'validateEmail',
            'blur #newName' : 'validateName',
            //'click .addressTab' : 'fetchUserAddress',
            'click .addAddress' : 'addAddress'
    	},
    	
    	initialize: function () {
            try{
                mySelf = this;
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },

        addAddress: function(evt){
            $('#modalScreen').html(addAddressModal());
            $('#addAddressModal').modal('show');
            $('#addAddressModal .addAddressAction').unbind('click').bind('click' , this.addUserAddress);
        },

        addUserAddress: function(){
            var valid = true;
            $('#addAddressModal input').each(function(){
                var id = $(this).attr('id');
                if( id !== 'address2' ){
                    if( $(this).val() == ''){

                    }
                }
            });
        },

        fetchUserAddress: function(evt){
            var address = addressModel.get('address');
            if(address.length === 0){
                var url = config.root + '/user/me/address';
                util.jqueryGet(url , this.fetchUserAddressSCB , this.fetchUserAddressECB , this);
            }else{
                this.fetchUserAddressSCB(address , this);
            }
        },

        fetchUserAddressSCB: function(response , obj){
            addressModel.set('address' , response);
            var data = {};
            data.address = response;
            obj.$el.find('#addressTab').html(addressView(data));
        },

        fetchUserAddressECB: function(response , obj){
            var data = {};
            data.address = [];
            obj.$el.find('#addressTab').html(data);
        },

        changePwd: function(){
            $('#modalScreen').html(changePwdModal());
            $('#changePasswordModal').modal('show');
            this.bindCPEvent();
        },

        bindCPEvent: function(){
            $('#oldPassword').bind('blur' , this.pwdCheck);
            $('#newPassword').bind('blur' , this.pwdCheck);
            $('#newConPassword').bind('blur' , this.pwdCheck);
            $('#changePasswordModal .cpCancel').bind('click' , this.cpCancel);
            $('#changePasswordModal .cpUpdate').bind('click' , this.cpUpdate);
        },

        unBindCPEvent: function(){
            $('#oldPassword').unbind('blur');
            $('#newPassword').unbind('blur');
            $('#newConPassword').unbind('blur');
            $('#changePasswordModal .cpCancel').unbind('click');
            $('#changePasswordModal .cpUpdate').unbind('click');
        },

        pwdCheck: function(evt){
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
                if($(evt.target).hasClass('confPwd')){
                    mySelf.checkConfPwd(evt);
                }
            }
        },

        checkConfPwd: function(evt){
            var value = $.trim($(evt.target).val());
            var orignalValue = $.trim($('#newPassword').val());
            var errorContainer = $(evt.target).attr('error');
            if(orignalValue !== value){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Password not matching');
            }else{
                $(evt.target).removeClass('inputError');
                $(evt.target).prev().removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        cpUpdate: function(){
            $('#changePasswordModal input').each(function(){
                $(this).filter(':visible').blur();
            });
            if($('.registerSection .inputError').length > 0){
                return true;
            }
            var input = {};
            input.password = $.trim($('#oldPassword').val());
            input.password1 = $.trim($('#newPassword').val());
            input.password2 = $.trim($('#newConPassword').val());

            var url = config.root + '/user/me/modify/password';
            util.jqueryPut(url , input , mySelf.cpUpdateSCB , mySelf.cpUpdateSCB , mySelf);
        },

        cpUpdateSCB: function(response , input , obj){
            //code for toast message
            console.log(response)
            obj.unBindCPEvent();
            $('#changePasswordModal').modal('hide');
        },

        cpCancel: function(){
            mySelf.unBindCPEvent();
            $('#changePasswordModal').modal('hide');    
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
                    if(value != userDetailModel.get('email')){
                        this.saveEmail(value);
                    }
                }
            }else{
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
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
                if(value != userDetailModel.get('name')){
                    this.saveName(value);
                }
            }    
        },

        saveName: function(newName){
            var input = {};
            input.value = newName;
            var url = config.root + '/user/me/modify/name';
            util.jqueryPut(url , input , this.saveNameSCB , this.saveNameECB , this);
        },

        saveEmail: function(newEmail){
            var input = {};
            input.value = newEmail;
            var url = config.root + '/user/me/modify/email';
            util.jqueryPut(url , input , this.saveEmailSCB , this.saveEmailECB , this);
        },

        saveEmailSCB:function(response , input , obj){
            obj.$el.find('#emailUpdated').addClass('iShow');
            userDetailModel.set('email' , input.value);
        },

        saveEmailECB:function(response , input , obj){
            obj.$el.find('#emailUpdated').removeClass('iShow');
            obj.$el.find('#emailError').html('Unable to update');
            obj.$el.find('newEmail').val(userDetailModel.get('email'));
        },

        saveNameSCB:function(response , input , obj){
            obj.$el.find('#nameUpdated').addClass('iShow');
            userDetailModel.set('name' , input.value);
        },

        saveNameECB:function(){
            obj.$el.find('#nameUpdated').removeClass('iShow');
            obj.$el.find('#emailError').html('Unable to update');
            obj.$el.find('newName').val(userDetailModel.get('name'));
        },
        
        render : function(){
            try{
                var data = userDetailModel.attributes;
                data.staticPath = config.staticPath;
                this.$el.html(myProfileScreen(userDetailModel.attributes));
            }catch(e){
                console.log('error in render --> ' + e);    
            }
        }
    });
    return myProfileView;
});