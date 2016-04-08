/* jslint browser : true */
/* global define */
define(['jquery','underscore','backbone','bootstrap','loginView','registerView','navigationView','createConsultationView','addMemberView','myMemberView','myProfileView','dashboardView','addSymptomsView','summaryView','myPrescriptionView','userModel','userDetailModel','memberModel','cartModel','addressModel','getConsultationModel','allConsultationModel'],
    function ($,_,Backbone,Bootstrap,loginView,registerView,navigationView,createConsultationView,addMemberView,myMemberView,myProfileView,dashboardView,addSymptomsView,summaryView,myPrescriptionView,userModel,userDetailModel,memberModel,cartModel,addressModel,getConsultationModel,allConsultationModel) {
    'use strict';

    var bdView = {};
    bdView.stopExecution = false;
    var AppRouter = Backbone.Router.extend({
        routes: {
            //"" : "login",
            "login" : "login",
            "register" : "register",
            "createConsultation" : "createConsultation",
            "addMember" : "addMember",
            "myMember": "myMember",
            "dashboard" : "dashboard",
            "addSymptoms" : "addSymptoms",
            "profile" : "profile",
            "consultation" : "consultation",
            "myPrescription" : "myPrescription",
            "allConsultation": "allConsultation",
            "editCons": "editCons",
            "summaryCons": "summaryCons",
            "test": "test"
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
                $('.mainView').attr('id' , 'createConsultationView');
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
                $('.mainView').attr('id' , 'profileView');
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

        addMember: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                //dUtil.showLoader();
                if(!this.menuRendered){
                    this.renderMenu();
                }
                $('.mainView').attr('id' , 'addMemberView');
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
            if(!config.manageHistory){
                config.manageHistory = true;
                window.history.back();
            }
            if(config.userSession){
                $(window).scrollTop(0);
                //dUtil.showLoader();
                if(!this.menuRendered){
                    this.renderMenu();
                }
                $('.mainView').attr('id' , 'myMemberView');
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

        myPrescription: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                //dUtil.showLoader();
                if(!this.menuRendered){
                    this.renderMenu();
                }
                $('.mainView').attr('id' , 'myPrescription');
                document.title = 'My Prescription';
                $('h3.header').html('My Prescription');
                if(!bdView.myPrescriptionView){
                    bdView.myPrescriptionView = new myPrescriptionView();
                }
                bdView.myPrescriptionView.render();
                bdView.navigationView.selectMenuItem('myPrescription');
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
                $('.mainView').attr('id' , 'dashboardView');
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
        },

        allConsultation: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                if(!this.menuRendered){
                    this.renderMenu();
                }
                $('.mainView').attr('id' , 'dashboardView');
                document.title = 'All Consultation';
                $('h3.header').html('All Consultation');
                if(!bdView.dashboardView){
                    bdView.dashboardView = new dashboardView();
                }
                bdView.dashboardView.renderAll();
                bdView.navigationView.selectMenuItem('allConsultation');
            }else{
                window.location.hash = 'login';
            }    
        },

        test: function(){
            if(config.userSession){
                $(window).scrollTop(0);
                if(!this.menuRendered){
                    this.renderMenu();
                }
                document.title = 'Test';
                $('h3.header').html('Test');
                if(!bdView.dashboardView){
                    bdView.dashboardView = new dashboardView();
                }
                bdView.dashboardView.renderTest();
                bdView.navigationView.selectMenuItem('allConsultation');
            }else{
                window.location.hash = 'login';
            }      
        },

        editCons: function(){
            if(config.userSession){
                $('.mainView').attr('id' , 'editConsultation');
                $(window).scrollTop(0);
                if(!this.menuRendered){
                    this.renderMenu();
                }
                document.title = 'Edit Consultation';
                $('h3.header').html('Edit Consultation');
                if(!bdView.addSymptomsView){
                    bdView.addSymptomsView = new addSymptomsView();
                }
                bdView.addSymptomsView.render();
                bdView.navigationView.selectMenuItem('editCons');
            }else{
                window.location.hash = 'login';
            }
        },
        
        summaryCons: function(){
            if(config.userSession){
                $('.mainView').attr('id' , 'consSummary');
                $(window).scrollTop(0);
                if(!this.menuRendered){
                    this.renderMenu();
                }
                document.title = 'Medical Summary';
                $('h3.header').html('Medical Summary');
                if(!bdView.summaryView){
                    bdView.summaryView = new summaryView();
                }
                bdView.summaryView.render();
                bdView.navigationView.selectMenuItem('summaryCons');
            }else{
                window.location.hash = 'login';
            }
        }
        
    });

    return AppRouter;
});