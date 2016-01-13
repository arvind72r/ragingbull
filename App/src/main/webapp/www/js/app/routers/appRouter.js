/* jslint browser : true */
/* global define */
define(['jquery','underscore','backbone','bootstrap','loginView','registerView','navigationView','createConsultationView','addMemberView','myMemberView','myProfileView','dashboardView','addSymptomsView','userModel','userDetailModel','memberModel','getConsultationModel','allConsultationModel'], 
    function ($,_,Backbone,Bootstrap,loginView,registerView,navigationView,createConsultationView,addMemberView,myMemberView,myProfileView,dashboardView,addSymptomsView,userModel,userDetailModel,memberModel,getConsultationModel,allConsultationModel) {
    'use strict';

    var bdView = {};
    bdView.stopExecution = false;
    var AppRouter = Backbone.Router.extend({
        routes: {
            "" : "login",
            "login" : "login",
            "register" : "register",
            "createConsultation" : "createConsultation",
            "addMember" : "addMember",
            "myMember": "myMember",
            "dashboard" : "dashboard",
            "addSymptoms" : "addSymptoms",
            "profile" : "profile",
            "consultation" : "consultation",
            //"logout" : "logout"

        },

        //execute before every route and than call its corresponding callback
        execute: function(callback, args) {
            if (callback) callback.apply(this, args);
        },

        initialize: function () {
            this.menuRendered = false;
            var um = window.localStorage.getItem('userModelLocal'); 
            if( um === '' || um === null || typeof um === 'undefined' ){
                config.userSession = false;
            }else{
                userModel.set( JSON.parse( window.localStorage.getItem('userModelLocal') ) );
                userDetailModel.set( JSON.parse( window.localStorage.getItem('userDetailModelLocal') ) );
                config.userSession = true;
            }
        },

        login: function() {
            $(window).scrollTop(0);
            //dUtil.showLoader();
            document.title = 'Login Screen';
            if(!bdView.loginView){
                bdView.loginView = new loginView();
            }
            bdView.loginView.render();
        },

        register: function(){
            $(window).scrollTop(0);
            //dUtil.showLoader();
            document.title = 'Registration Screen';
            if(!bdView.registerView){
                bdView.registerView = new registerView();
            }
            bdView.registerView.render();  
        },

        renderMenu: function(){
            if(!bdView.navigationView){
                bdView.navigationView = new navigationView();
            }
            bdView.navigationView.render();
            this.menuRendered = true;
        },

        createConsultation: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                if(!this.menuRendered){
                    this.renderMenu();
                }
                //dUtil.showLoader();
                document.title = 'Create Consultation';
                $('h3.header').html('Create Consultation');
                if(!bdView.createConsultationView){
                    bdView.createConsultationView = new createConsultationView();
                }
                bdView.createConsultationView.render();
                bdView.navigationView.selectMenuItem('createConsultation');
            }else{
                window.location.hash = 'login';
            }
        },

        profile: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                if(!this.menuRendered){
                    this.renderMenu();
                }
                //dUtil.showLoader();
                document.title = 'My Profile';
                $('h3.header').html('My Profile');
                if(!bdView.myProfileView){
                    bdView.myProfileView = new myProfileView();
                }
                bdView.myProfileView.render();
                bdView.navigationView.selectMenuItem('profile');
            }else{
                window.location.hash = 'login';
            }
        },

        consultation: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                if(!this.menuRendered){
                    this.renderMenu();
                }
                //dUtil.showLoader();
                document.title = 'Consultation';
                $('h3.header').html('Consultation');
                if(!bdView.myConsultationView){
                    bdView.myConsultationView = new myConsultationView();
                }
                bdView.myConsultationView.render();
                bdView.navigationView.selectMenuItem('consultation');
            }else{
                window.location.hash = 'login';
            }
        },

        addMember: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                //dUtil.showLoader();
                if(!this.menuRendered){
                    this.renderMenu();
                }
                document.title = 'Add Member';
                $('h3.header').html('Add Member');
                if(!bdView.addMemberView){
                    bdView.addMemberView = new addMemberView();
                }
                bdView.addMemberView.render();
            }else{
                window.location.hash = 'login';
            } 
        },

        myMember: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                //dUtil.showLoader();
                if(!this.menuRendered){
                    this.renderMenu();
                }
                document.title = 'My Member';
                $('h3.header').html('My Member');
                if(!bdView.myMemberView){
                    bdView.myMemberView = new myMemberView();
                }
                bdView.myMemberView.render();
                bdView.navigationView.selectMenuItem('myMember');
            }else{
                window.location.hash = 'login';
            }
        },

        dashboard: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                if(!this.menuRendered){
                    this.renderMenu();
                }
                document.title = 'My Dashboard';
                $('h3.header').html('My Dashboard');
                if(!bdView.dashboardView){
                    bdView.dashboardView = new dashboardView();
                }
                bdView.dashboardView.render();
                bdView.navigationView.selectMenuItem('dashboard');
            }else{
                window.location.hash = 'login';
            }
        }


        // addSymptoms: function(){
        //     if(config.userSession){
        //         $(window).scrollTop(0);
        //         if(this.menuRendered){
        //             this.renderMenu();
        //         }
        //         document.title = 'Add Symptoms';
        //         $('h3.header').html('Add Symptoms');
        //         if(!bdView.addSymptomsView){
        //             bdView.addSymptomsView = new addSymptomsView();
        //         }
        //         bdView.addSymptomsView.render('');
        //     }else{
        //         window.location.hash = 'login';
        //     }
        // }
        
        
    });

    return AppRouter;
});