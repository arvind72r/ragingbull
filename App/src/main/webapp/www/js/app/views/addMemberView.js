/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'util/util', 'util/validation', 'memberModel', 'hbs!tpl/addMember'], 
    function($, backbone,util,validate,memberModel,addMemberScreen) {
    'use strict';

    var addMemberView = Backbone.View.extend({
    	el : '#addMemberView',

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

        validateEmail: function(evt){
            validate.validateEmail(evt);
        },

        validateDOB: function(evt){
            validate.validateDOB(evt);
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
                localStorage.setItem('memberForCons' , input.name);
            }
            (memberModel.attributes.members).push(JSON.parse(response));
            window.history.back();
        },

        addMemberErrorCb: function(response, input, obj){
            if(response.status === 409){
                obj.$el.find('.regError').html("User already exist.");
                obj.$el.find('.regError').show();
                util.hideLoader();
            }else{
                obj.$el.find('.regError').html("We are unable to add member, please try again later. Inconvinience Regereted");
                obj.$el.find('.regError').show();
                util.hideLoader();
            }
        },

        cancelAddMember: function(){
            var nextPage = config.nextPage;
            memberModel.set('newMemberAdded' , false);
            window.history.back();
        }
    });
    return addMemberView;
});