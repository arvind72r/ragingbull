define(function(require) {
    'use strict';    
    var $ = require("jquery"),
        _ = require("underscore"),
        Backbone = require("backbone"),
        util = require("util/util"),
        memberModel = require("memberModel");

    var addMemberScreen  =  require("hbs!tpl/addMember");
    
    var addMemberView = Backbone.View.extend({
    	el : '#page-content-wrapper',

        events : {
            'click .addMember': 'addMember',
            'blur #phone': 'validatePhone',
            'blur #name': 'validateName',
            'blur #email': 'validateEmail',
            'blur #dob': 'validateDOB',
            'blur #sex': 'validateSex',
            'click .cancelAddMember': 'cancelAddMember'
    	},
    	
    	initialize: function () {
            try{
                
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },
        
        render : function(){
            try{
            	this.$el.html(addMemberScreen());
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
            if(value !== ''){
                if(!(/^\d{10}$/.test(value))){
                    $(evt.target).addClass('inputError');
                    $(evt.target).prev().addClass('inputError');
                    $('#'+errorContainer).html('Invalid Mobile#');
                }else{
                    $(evt.target).removeClass('inputError');
                    $(evt.target).prev().removeClass('inputError');
                    $('#'+errorContainer).html('');
                }
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

        addMember: function(){
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

                var addMemberJson = util.serializeObject($('form'));
                addMemberJson.phone = '+91' + addMemberJson.phone;
                var url = config.root + '/user/me/member';
                util.jqueryPost(url , addMemberJson , this.addMemberCallback , this.addMemberErrorCb , this);

            }catch(e){
                console.log('error in registerUser -->' + e)
            }
        },

        addMemberCallback: function(response , input , obj){
            var nextPage = config.nextPage;
            if(nextPage === 'createConsultation'){
                config.memberForCons = input.name;
            }
            window.location.hash = nextPage;
        },

        addMemberErrorCb: function(){

        },

        cancelAddMember: function(){
            var nextPage = config.nextPage;
            memberModel.set('newMemberAdded' , false);
            window.location.hash = nextPage;
        }
    });
    return addMemberView;
});