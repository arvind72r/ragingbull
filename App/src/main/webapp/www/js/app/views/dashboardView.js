define(function(require) {
    'use strict';    
    var $ = require("jquery"),
        _ = require("underscore"),
        Backbone = require("backbone"),
        userDetailModel = require("userDetailModel"),
        allConsultationModel = require("allConsultationModel"),
        addSymptomsView = require("addSymptomsView"),
        util = require("util/util");

    var dashboardScreen  =  require("hbs!tpl/dashboard");
    
    var DashboardView = Backbone.View.extend({
    	el : '#page-content-wrapper',

        events : {
            'click .upcc-pat': 'getConsultation',
            'click .viewSwitch' : 'switchRoleView',
            'click .viewMore' : 'getFullConsList'
    	},
    	
    	initialize: function () {
            try{
                this.addSymptomsView = new addSymptomsView();
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },

        switchRoleView: function(evt){
            var desiredRole = $(evt.target).attr('data-prop');
            if(desiredRole === 'user'){
                $(evt.target).attr('data-prop','practitioner');
                $(evt.target).html('Doctor')
                this.$el.find('div[data-value=practitioner]').hide();
                this.$el.find('div[data-value=user]').show();
            }else{
                $(evt.target).attr('data-prop','user');
                $(evt.target).html('User')
                this.$el.find('div[data-value=practitioner]').show();
                this.$el.find('div[data-value=user]').hide();
            }
            $(evt.target).blur();
        },

        getFullConsList: function(evt){
            config.desiredCons = $(evt.target).attr('data-prop');
            window.location.hash = 'allConsultation';
        },

        getConsultation: function(evt){
            util.showLoader();
            var target = $(evt.target);
            var targetLi = target.closest('li');
            var consultationId = targetLi.attr('data-prop');
            var url = config.root + '/consultation/'+consultationId;
            util.jqueryGet(url , this.getConsultationSCB , this.getConsultationECB , this);

        },

        getConsultationSCB: function(response , obj){
            var data = {};
            data.cons = response;
            data.setting = {};
            data.setting.userView = false;
            data.setting.practitionerView = false;
            window.location.hash = 'addSymptoms';
            var practitionerInfo = userDetailModel.get('practitioner');
            if(practitionerInfo){
                if(practitionerInfo && practitionerInfo.id === response.practitionerId){
                    data.setting.practitionerView = true;
                }else{
                    data.setting.userView = true;
                }
            }else{
                data.setting.userView = true;
            }
            
            obj.addSymptomsView.render(data);
        },

        getData: function(){
            var url = config.root + '/user/me/dashboard';
            util.jqueryGet( url , this.renderDashboard , this.dashboardError , this);
        },

        getFewerData: function(obj){
            var formatedData = {}
            formatedData.showData = true;
            if(obj.length > 0 && obj.length < 3) {
                formatedData.consultation = obj;
                formatedData.showViewMore = false;
            }else if(obj.length >= 3 ){
                formatedData.consultation = _.first(obj , 3);
                formatedData.showViewMore = true;
            }else{
                formatedData.showData = false;
            }

            return formatedData;
        },

        renderDashboard: function(response , obj){
            allConsultationModel.set(response);
            var inputData = {};
            var role = {};

            var practitionerInfo = userDetailModel.get('practitioner');
            if(practitionerInfo){
                role.practitioner = true;
            }else{
                role.practitioner = false;
            }

            if(role.practitioner){
                inputData.practitionerRole = role.practitioner;
                inputData.practitionerCurrent = obj.getFewerData(response.practitionerCurrent);
                inputData.practitionerPast = obj.getFewerData(response.practitionerPast);
                inputData.userCurrent = obj.getFewerData(response.userCurrent);
                inputData.userPast = obj.getFewerData(response.userPast);
                inputData.memberCurrent = obj.getFewerData(response.memberCurrent);
                inputData.memberPast = obj.getFewerData(response.memberPast);
                inputData.noPractitionerCons = !(inputData.practitionerCurrent.showData || inputData.practitionerPast.showData);
                inputData.noUserCons = !(inputData.userCurrent.showData || inputData.userPast.showData || inputData.memberCurrent.showData || inputData.memberPast.showData);
            }else{
                inputData.practitionerRole = role.practitioner;
                inputData.userCurrent = obj.getFewerData(response.userCurrent);
                inputData.userPast = obj.getFewerData(response.userPast);
                inputData.memberCurrent = obj.getFewerData(response.memberCurrent);
                inputData.memberPast = obj.getFewerData(response.memberPast);
                inputData.noPractitionerCons = true;
                inputData.noUserCons = !(inputData.userCurrent.showData || inputData.userPast.showData || inputData.memberCurrent.showData || inputData.memberPast.showData);
            }

            obj.$el.html(dashboardScreen(inputData));
            util.hideLoader();
        },

        dashboardError: function(response , obj){

        },

        render : function(){
            try{
                this.getData();
            }catch(e){
                console.log('error in render --> ' + e);    
            }
        }
    });
    return DashboardView;
});