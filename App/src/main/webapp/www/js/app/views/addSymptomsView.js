/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'jqueryui', 'util/util', 'getConsultationModel', 'userDetailModel', 'hbs!tpl/addSymptoms', 'hbs!tpl/partials/prescriptionRow', 'hbs!tpl/confirmModal'], 
    function($, backbone,jqueryui,util,getConsultationModel,userDetailModel,addSymptomsScreen,prescRowPartial,confirmationModal) {
    'use strict';
    
    var AddSymptomsView = Backbone.View.extend({
        el : '#editConsultation',

        events : {
            'click .backToDash': 'backToDash',
            'click .symptomsSave': 'addSymptoms',
            'click .presSubmit': 'preCheckForLock',
            'click .consSave': 'saveConsultation',
            'focus .addPrescRowOnFocus': 'addPrescRowOnFocus',
            'click .consTab': 'showProperButton'
        },
        
        initialize: function () {
            try{
                this.consultationId = '';
                this.drugs = [];
                this.consulatationData = {};
                this.lockData = {};
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },

        showProperButton: function(evt){
            var hash = $(evt.target).attr('href');
            hash = hash.split('#')[1];
            if(hash === 'prescriptionTab'){
                $('body').addClass('hideLeftNav');
                this.$el.find('.presSubmit').show();
            }else{
                $('body').removeClass('hideLeftNav');
                this.$el.find('.presSubmit').hide();
            }
        },

        backToDash: function(){
            $('body').removeClass('hideLeftNav');
            window.history.back();
        },

        preCheckForLock: function(evt){
            util.showLoader();
            var self = this;
            this.lockData.symptoms = $('#symptoms').val();
            this.lockData.diagnosis = $('#diagnosis').val();
            this.lockData.drugs = this.getDrugObject();
            if(this.lockData.drugs){
                if($.trim(this.lockData.symptoms) === '' || $.trim(this.lockData.diagnosis) === '' || (this.lockData.drugs).length === 0){
                    var data = {};
                    data.title = 'Confirm Submit';
                    data.message = 'Either one or all sections(Symptoms, Diagnosis or Prescription) are not entered. Are you sure you want to submit the consultation without entering the information. Please note once submit you will not be able to edit the same.';
                    $('#modalScreen').html(confirmationModal(data));
                    $('#confirmationModal').modal('show');
                    util.hideLoader();
                    $('#confirmationModal .confirmSubmit').unbind('click').bind('click' , function(e){
                        util.showLoader();
                        $('#confirmationModal').modal('hide');
                        self.lockConsultation(evt);
                    });
                }else{
                    this.lockConsultation(evt);
                }
            }else{
                return;
            }
        },

        lockConsultation: function(evt){
            var url = config.root + '/consultation/'+this.consultationId+'/notes/SYMPTOMS';
            util.jqueryPost(url , this.lockData.symptoms , this.proceedWithLockD , this.throwLockError , this);
        },

        proceedWithLockD: function(response , input , obj){
            var url = config.root + '/consultation/'+obj.consultationId+'/notes/DIAGNOSIS';
            util.jqueryPost(url , obj.lockData.diagnosis , obj.proceedWithLock , obj.throwLockError , obj);
        },

        proceedWithLock: function(response , input , obj){
            if( (obj.lockData.drugs).length > 0 ){
                var userId = userDetailModel.get('id');
                var prescription = {};
                prescription.userId = userId;
                prescription.drugs = obj.lockData.drugs;
                var url = config.root + '/consultation/' + obj.consultationId + '/prescription';
                util.jqueryPost(url , prescription , obj.completeLock , obj.throwLockError , obj);
            }else{
                obj.completeLock(response , input , obj);
            }
        },

        completeLock: function(response , input , obj){
            var url = config.root + '/consultation/'+obj.consultationId+'/lock';
            util.jqueryPost(url , '' , obj.locSCB , obj.throwLockError , obj);
        },

        locSCB: function(response, input, obj){
            window.localStorage.removeItem(obj.consultationId);
            obj.lockData = {};
            var data = {};
            data.cons = response;
            getConsultationModel.set(data);
            Backbone.history.navigate('summaryCons',{trigger:true, replace: true});
        },

        throwLockError: function(response , input , obj){
            util.hideLoader();
            var data = {};
            data.title = 'Error';
            data.message = 'We are facing some technical issues while saving your prescription at this moment. Please try again later or contact our customer care.';
            $('#modalScreen').html(confirmationModal(data));
            $('#confirmationModal').modal('show');
            util.hideLoader();
            $('#confirmationModal .confirmSubmit').unbind('click').bind('click' , function(e){
                $('#confirmationModal').modal('hide');
            });
        },

        saveConsultation: function(evt){
            util.showLoader();
            var symptoms = $('#symptoms').val();
            var diagnosis = $('#diagnosis').val();
            var chkLoc = this.getLocalObject();
            var drugs = this.getDrugObject();
            this.consulatationData.diagnosis = diagnosis;
            this.consulatationData.symptoms = symptoms;
            if(drugs){
                this.consulatationData.drugs = drugs;
            }else{
                this.$el.find('.prescTab').trigger('click');
            }
            window.localStorage.setItem(this.consultationId , JSON.stringify(this.consulatationData));
            util.hideLoader();
            var data = {};
            data.title = 'Success';
            data.message = 'Prescription saved succesfullly';
            $('#modalScreen').html(confirmationModal(data));
            $('#modalScreen .modal-footer').hide();
            $('#confirmationModal').modal('show');
            setTimeout(function(){ $('#confirmationModal').modal('hide'); }, 1500);
        },

        getLocalObject: function(){
            var consData = window.localStorage.getItem(this.consultationId);
            if(consData === null || typeof consData === 'undefined'){
                this.consulatationData = {};
                return false;
            }else{
                this.consulatationData = JSON.parse(consData);
                return true;
            }
        },

        addSymptoms: function(evt){
            util.showLoader();
            var text = $('#symptoms').val();
            var url = config.root + '/consultation/'+this.consultationId+'/notes/SYMPTOMS';
            util.jqueryPost(url , text , this.addSymptomsSCB , this.addSymptomsECB , this);
        },

        addSymptomsSCB: function(response , input , obj){
            util.hideLoader();
            var data = {};
            data.title = 'Success';
            data.message = 'Prescription saved succesfullly';
            $('#modalScreen').html(confirmationModal(data));
            $('#modalScreen .modal-footer').hide();
            $('#confirmationModal').modal('show');
            setTimeout(function(){ $('#confirmationModal').modal('hide'); }, 1500);
        },

        addSymptomsECB: function(response , input , obj){
            util.hideLoader();
            var data = {};
            data.title = 'Error';
            data.message = 'We are facing some technical issues while adding symptoms at this moment. Please try again later or contact our customer care.';
            $('#modalScreen').html(confirmationModal(data));
            $('#confirmationModal').modal('show');
            util.hideLoader();
            $('#confirmationModal .confirmSubmit').unbind('click').bind('click' , function(e){
                $('#confirmationModal').modal('hide');
            });

        },

        getDrugObject: function(){
            var obj = this;
            var drugs = [];
            var drug = {};
            var valid = true;
            $('.prescriptionRow').each(function(index){
                drug = {};
                var _this = this;
                var frequency = '';
                if( $.trim($('input[name="drug"]' , _this).val()) != ''){
                    drug.name = $.trim($('input[name="drug"]' , _this).val());
                    drug.schedule = $('select[name="schedule"]' , _this).val();
                    drug.unit = $('select[name="unit"]' , _this).val();
                    if( $.trim($('input[name="dose"]' , _this).val()) == ''){
                        valid = false;
                        $('input[name="dose"]' , _this).addClass('inputError');
                    }else{
                        drug.dose = parseInt($.trim($('input[name="dose"]' , _this).val()));
                        $('input[name="dose"]' , _this).removeClass('inputError');
                    }
                    if( $.trim($('input[name="days"]' , _this).val()) == ''){
                        valid = false;
                        $('input[name="days"]' , _this).addClass('inputError');
                    }else{
                        drug.days = parseInt($.trim($('input[name="days"]' , _this).val()));
                        $('input[name="days"]' , _this).removeClass('inputError');
                    }
                    if( $('input[type=checkbox]:checked' , _this).length === 0){
                        valid = false;
                        $('.frequency' , _this).addClass('inputError');
                    }else{
                        $('.frequency' , _this).removeClass('inputError');
                        $('input[type=checkbox]' , _this).each(function(){
                            var val = $(this).is(':checked') ? 1 : 0; 
                            frequency = frequency + val;
                        });
                        drug.frequency = parseInt(frequency);
                    }

                    drugs.push(drug);
                }
            });
            if(!valid){
                util.hideLoader();
                return false;
            }
            return drugs;
            
        },

        addPrescRowOnFocus: function(evt){
            var rowNumber = parseInt($(evt.target).parents('.prescriptionRow').attr('data-prop'));
            var totalRow = getConsultationModel.attributes.prescRow;
            if(rowNumber === (totalRow - 1)){
                if(this.validatePreviousRow(rowNumber)){
                    this.addPrescRow();
                }
            }
        },

        validatePreviousRow: function(rowNumber){
            var row = rowNumber - 1;
            var drugRow = $('#prescRow'+row);
            if( $.trim($('input[name="drug"]' , drugRow).val()) == ''){
                return false;
            }
            if( $.trim($('input[name="dose"]' , drugRow).val()) == ''){
                return false;
            }
            if( $.trim($('input[name="days"]' , drugRow).val()) == ''){
                return false;
            }
            if( $('input[type=checkbox]:checked' , drugRow).length === 0){
                return false;
            }
            return true;
        },

        addPrescRow: function(){
            var data = {};
            data.prescRow = getConsultationModel.attributes.prescRow;
            this.$el.find('#prescriptionTab').find('.prescriptionBody').append(prescRowPartial(data));
            getConsultationModel.attributes.prescRow++;
        },

        populatePrescRow: function(){
            var diagnosis = this.consulatationData.diagnosis;
            var symptoms = this.consulatationData.symptoms;
            var drugs = this.consulatationData.drugs;
            var obj = this;
            var data = {};
            $('#diagnosis').text(diagnosis);
            $('#symptoms').text(symptoms);
            if(drugs.length === 0){
                this.addPrescRow();
                this.addPrescRow();
                return;
            }
            _.each(drugs , function(drug){
                data={};
                data.prescRow = getConsultationModel.attributes.prescRow;
                data.drug = drug;
                var freq = data.drug.frequency+'';
                if(freq.length == 2){
                    freq = '0'+freq;
                }
                if(freq.length == 1){
                    freq = '00'+freq;
                }
                freq = freq.split('');
                data.drug.frequency = freq;
                obj.$el.find('#prescriptionTab').find('.prescriptionBody').append(prescRowPartial(data));
                getConsultationModel.attributes.prescRow++;
            });
            this.addPrescRow();
            this.addPrescRow();
        },
        
        render : function(){
            try{
                var data = getConsultationModel.get('cons');
                getConsultationModel.set('prescRow' , 0);
                this.consultationId = data.id;
                this.consulatationData = {};
                this.$el.html(addSymptomsScreen(getConsultationModel.attributes));
                if(this.getLocalObject()){
                    this.populatePrescRow();    
                }else{
                    this.addPrescRow();
                    this.addPrescRow();                    
                }
                $( ".drugBox" ).autocomplete({
                  source: medicines
                });
                util.hideLoader();
                return this;
            }catch(e){
                console.log('error in render --> ' + e);    
            }
        }
    });
    return AddSymptomsView;
});