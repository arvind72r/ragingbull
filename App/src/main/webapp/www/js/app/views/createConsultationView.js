/*jslint browser:true*/
/*global define*/
define(['jquery', 'underscore', 'backbone', 'util/util', 'memberModel', 'userDetailModel', 'getConsultationModel', 'hbs!tpl/createConsulatationScreen'], 
    function($, _, backbone,util,memberModel,userDetailModel,getConsultationModel,createConsulatationScreen) {
    'use strict';

    var createConsulatationView = Backbone.View.extend({
    	el : '#createConsultationView',

        events : {
            'click .addNewMember': 'addMemberScreen',
            'change #patient' : 'changePatientInfo',
            'click .consultation': 'createConsultation'
    	},
    	
    	initialize: function () {
            try{
                
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },
        
        render : function(){
            try{
            	this.getMember();
            }catch(e){
                console.log('error in render --> ' + e);    
            }
        },

        createConsultation: function(evt){
            util.showLoader();
            var consInput = {};
            consInput.userId = $('#patient').val();
            consInput.practitionerId = $('#selectDoc option:selected').attr('data-prop');
            var locationId = $('#selectDoc').val();

            var url = config.root + '/location/'+locationId+'/consultation/';
            util.jqueryPost(url , consInput , this.createConsultationSCB , this.createConsultationECB , this);
        },

        createConsultationSCB: function(response , input , obj){
            var consultationId = response.id;
            var locationId = response.locationId;
            var url = config.root + '/consultation/'+consultationId;
            util.jqueryGet(url , obj.getConsultation , obj.getConsultationECB , obj);
        },

        getConsultation: function(response , obj){
            var data = {};
            data.cons = response;
            data.setting = {};
            data.setting.userView = false;
            data.setting.practitionerView = false;
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
            getConsultationModel.set(data);
            Backbone.history.navigate('editCons',{trigger:true, replace: true});
        },

        changePatientInfo: function(evt){
            var type = $(evt.target);
            var option = $('option:selected', type).attr('data-prop');
            if(option === 'self'){
                $('.patientAge').html(userDetailModel.get('age') + ' Year(s)');
                $('.patientSex').html(userDetailModel.get('sex'));
            }else{
                var obj = memberModel.attributes.members[option];
                $('.patientAge').html(obj.age + ' Year(s)');
                $('.patientSex').html(obj.sex);
            }
        },

        getMember: function(){
            util.jqueryGet(config.root+'/user/me/member' , this.getMemberList , this.getMemberECB , this);
        },

        getMemberList: function(response , obj){
            memberModel.set('members' , response);
            obj.populateMembers();
        },

        getMemberECB: function(response , obj){
            if((memberModel.get('members')).length > 0){
                obj.populateMembers();
            }else{
                localStorage.removeItem('memberForCons');
                obj.populateMembers();
            }
        },

        populateMembers: function(){
            var patient = localStorage.getItem('memberForCons');
            config.memberForCons = '';
            var dataPoint = {};
            var displayPatient = '';
            if(patient === '' || patient === null){
                displayPatient = userDetailModel.attributes;
            }else{
                displayPatient = _.find(memberModel.attributes.members, function(obj) { return obj.name === patient })
            }
            dataPoint.user = userDetailModel.attributes;
            dataPoint.members = memberModel.attributes.members;
            dataPoint.selectedUser = displayPatient; 
            this.$el.html(createConsulatationScreen(dataPoint));
            this.getDoctor();
            util.hideLoader();
        },

        getDoctor: function(){
            util.jqueryGet(config.root + '/practitioner/' , this.getDoctorList , this.getDoctorECB , this);
        },

        getDoctorList: function(response , obj){
            var docList = '<option value="">Select a Doctor</option>';
            _.each(response , function(docLocList){
                docList = docList + '<option value="'+docLocList.locationId+'" data-prop="'+docLocList.practitionerId+'">'+docLocList.name+'-'+docLocList.location+'</option>';
            });
            $('#selectDoc').html(docList);
        },

        getDoctorECB: function(response,obj){
            var docList = '<option value="">Select a Doctor</option>';
            $('#selectDoc').html(docList);
            obj.$el.find('.globalError').html('Currently we are unable to fetch the doctor\'s list. Plese try later to create consultation');
            obj.$el.find('.consultation').addClass('disabled');
            obj.$el.find('.addNewMember').addClass('disabled');
        },

        addMemberScreen: function(){
            config.nextPage = 'createConsultation';
            Backbone.history.navigate('addMember',{trigger:true, replace: false});
        }
    });
    return createConsulatationView;
});