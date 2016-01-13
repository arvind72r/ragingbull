define(function(require) {
    'use strict';    
    var $ = require("jquery"),
        _ = require("underscore"),
        Backbone = require("backbone"),
        userDetailModel = require("userDetailModel"),
        userModel = require("userModel"),
        memberModel = require("memberModel"),
        allConsultationModel = require("allConsultationModel"),
        getConsultationModel = require("getConsultationModel");


    var navigation  =  require("hbs!tpl/navigation");
    var accountConfirmationModal = require("hbs!tpl/accountConfirmationModal");
    
    var NavigationView = Backbone.View.extend({
    	el : '#container',

        events : {
            'click #sidebar-toggle' : 'toggleSidebar',
            'click .verifyPhone' : 'verifyPhone',
            'click #logout' : 'logout'

    	},
    	
    	initialize: function () {
            try{
            }catch(e){
            }
        },

        logout: function(){
            localStorage.removeItem('authToken');
            localStorage.removeItem('header');
            localStorage.removeItem('userDetailModelLocal');
            localStorage.removeItem('userModelLocal');
            userModel.set(userModel.defaults);
            userDetailModel.set(userDetailModel.defaults);
            memberModel.set(memberModel.defaults);
            allConsultationModel.set(allConsultationModel.defaults);
            getConsultationModel.set(getConsultationModel.defaults);
            config.userSession = false;
            window.location.reload();
        },

        toggleSidebar: function(e){
            this.$el.find('#wrapper').toggleClass("toggled");
        },

        hideSidebar: function(){
            if($('#wrapper').hasClass('toggled')){
                this.$el.find('#wrapper').toggleClass("toggled");
            }
        },

        verifyPhone: function(){
            var self = this;
            $('#modalScreen').html(accountConfirmationModal());
            $('#accountConfirmationModal').modal('show');

            $('#accountConfirmationModal .skipNow').unbind('click').bind('click' , function(){
                $('#accountConfirmationModal').modal('hide');
            });
            $('#accountConfirmationModal .verifyNow').unbind('click').bind('click' , function(){
                var verifyId = $('#accountConfirmationModal .verificationToken').val();
                var url = config.root + '/register/'+verifyId+'/approve';
                util.jqueryPost(url , '' , self.proceed , self.invalidVerification , self);
            });
        },

        proceed: function(response , obj){
            $('#accountConfirmationModal').modal('hide');
            userModel.set('isUserVerified' , true);
            window.localStorage.setItem('userModelLocal' , JSON.stringify(userModel.attributes));
        },

        selectMenuItem : function(menuItem) {
            try{
                $('.sidebar-nav li').removeClass('active');
                if (menuItem) {
                    $('.sidebar-nav li.' + menuItem).addClass('active');
                }
                this.hideSidebar();
            }catch(e){
            }
        },
        
        render : function(){
            try{
                var obj = {};
                obj.name = userDetailModel.get('name');
                obj.userVerified = userModel.get('isUserVerified')
            	this.$el.html(navigation(obj));
                $('#wrapper').height($(window).height() - 56);
            }catch(e){
            }
        }
    });
    return NavigationView;
});