define(function(require) {
    'use strict';    
    var $ = require("jquery"),
        _ = require("underscore"),
        Backbone = require("backbone"),
        memberModel = require("memberModel"),
        userDetailModel = require("userDetailModel"),
        addSymptomsView = require("addSymptomsView"),
        util = require("util/util");

    var createConsulatationScreen  =  require("hbs!tpl/createConsulatationScreen");
    
    var createConsulatationView = Backbone.View.extend({
    	el : '#page-content-wrapper',

        events : {
            'click .addNewMember': 'addMemberScreen',
            'change #patient' : 'changePatientInfo',
            'click .consultation': 'createConsultation'
    	},
    	
    	initialize: function () {
            try{
                this.addSymptomsView = new addSymptomsView();
                
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
            var patient = config.memberForCons;
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
            obj.$el.html(createConsulatationScreen(dataPoint));
            obj.getDoctor();
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

        addMemberScreen: function(){
            config.nextPage = 'createConsultation';
            window.location.hash = 'addMember';
        }
    });
    return createConsulatationView;
});