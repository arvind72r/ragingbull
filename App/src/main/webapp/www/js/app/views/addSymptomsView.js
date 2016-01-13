define(function(require) {
    'use strict';    
    var $ = require("jquery"),
        _ = require("underscore"),
        Backbone = require("backbone"),
        getConsultationModel = require("getConsultationModel"),
        util = require("util/util");

    var addSymptomsScreen  =  require("hbs!tpl/addSymptoms");
    var prescRowPartial = require("hbs!tpl/partials/prescriptionRow");
    
    var AddSymptomsView = Backbone.View.extend({
    	el : '#page-content-wrapper',

        events : {
            'click .backToDash': 'backToDash',
            'click .symptomsSave': 'addSymptoms',
            'click .prescSave' : 'addPrescription',
            'click .diagnosisSave': 'addDiagnosis',
            'click .consTab' : 'hideLeftNav',
            'focus .addPrescRowOnFocus' : 'addPrescRowOnFocus',
            'click .prescriptionSave' : 'addPrescription'
    	},
    	
    	initialize: function () {
            try{
                this.consultationId = '';
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },

        backToDash: function(){
            $('body').removeClass('hideLeftNav');
            window.location.hash = 'dashboard';
        },

        hideLeftNav: function(evt){
            if($(evt.target).hasClass('prescTab')){
                $('body').addClass('hideLeftNav');
            }else{
                $('body').removeClass('hideLeftNav');
            }
        },

        addDiagnosis: function(evt){
            util.showLoader();
            var text = $('#diagnosis').val();
            var url = config.root + '/consultation/'+this.consultationId+'/notes/DIAGNOSIS';
            util.jqueryPost(url , text , this.addDiagnosisSCB , this.addDiagnosisECB , this);
        },

        addDiagnosisSCB: function(response , input , obj){
            util.hideLoader();
            return;
        },

        addSymptoms: function(evt){
            util.showLoader();
            var text = $('#symptoms').val();
            var url = config.root + '/consultation/'+this.consultationId+'/notes/SYMPTOMS';
            util.jqueryPost(url , text , this.addSymptomsSCB , this.addSymptomsECB , this);
        },

        addSymptomsSCB: function(response , input , obj){
            util.hideLoader();
            return;
        },

        addPrescription: function(){
            
        },

        addPrescRowOnFocus: function(evt){
            var rowNumber = parseInt($(evt.target).parents('.prescriptionRow').attr('data-prop'));
            var totalRow = getConsultationModel.attributes.prescRow;
            if(rowNumber === (totalRow - 1)){
                this.addPrescRow();
            }
        },

        addPrescRow: function(){
            this.$el.find('#prescriptionTab').find('.prescriptionBody').append(prescRowPartial(getConsultationModel.attributes));
            getConsultationModel.attributes.prescRow++;
        },
        
        render : function(data){
            try{
                this.consultationId = data.cons.id;
                getConsultationModel.set(data);
            	this.$el.html(addSymptomsScreen(getConsultationModel.attributes));
            	util.hideLoader();
                getConsultationModel.attributes.prescRow++;
                this.addPrescRow();
            }catch(e){
                console.log('error in render --> ' + e);    
            }
        }
    });
    return AddSymptomsView;
});