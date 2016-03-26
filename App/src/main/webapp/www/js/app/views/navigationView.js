/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'userDetailModel', 'userModel', 'memberModel', 'allConsultationModel', 'getConsultationModel', 'hbs!tpl/navigation', 'hbs!tpl/accountConfirmationModal'],
    function($, backbone,userDetailModel,userModel,memberModel,allConsultationModel,getConsultationModel,navigation,accountConfirmationModal) {
    'use strict';
    
    var NavigationView = Backbone.View.extend({
    	el : '#container',

        events : {
            'click #sidebar-toggle' : 'toggleSidebar',
            'click .verifyPhone' : 'verifyPhone',
            'click #logout' : 'logout',
            'click .navLink': 'route'
    	},

        route: function(evt){
            var hash = $(evt.target).attr('data-prop');
            var oldHash = (window.location.hash).split('#')[1];
            if(hash === oldHash){
                return;
            }
            if(hash === 'dashboard'){
                if(oldHash === 'addMember' || !config.manageHistory){
                    window.history.go(-2);
                }else{
                    window.history.back();
                }
            }
            if(oldHash === 'addMember'){
                config.manageHistory = false;
            }else{
                config.manageHistory = true;
            }
            if(oldHash === 'dashboard'){
                Backbone.history.navigate(hash,{trigger:true, replace: false});
            }else{
                Backbone.history.navigate(hash,{trigger:true, replace: true});
            }
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
            }catch(e){
            }
        }
    });
    return NavigationView;
});