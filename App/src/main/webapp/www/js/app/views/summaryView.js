/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'util/util', 'userDetailModel', 'cartModel', 'getConsultationModel', 'hbs!tpl/summary'],
    function($, backbone, util, userDetailModel,cartModel,getConsultationModel,summaryScreen) {
    'use strict';

    var SummaryView = Backbone.View.extend({
    	el : '#consSummary',

        events : {
            'click .printPresc': 'printPage',
            'click .orderPresc': 'prepareToOrder',
            'click .checkAllToggle': 'checkAllToggle',
            'change .drugCheck': 'toggleCheckLink'
    	},
    	
    	initialize: function () {
            try{
            }catch(e){
            }
        },

        toggleCheckLink: function(){
            var totalCheckBox = this.$el.find('input').length;
            var totalSelectedBox = this.$el.find('input:checked').length;
            if(totalCheckBox === totalSelectedBox){
                this.$el.find('.checkAllToggle').attr('data-prop','check');
                this.$el.find('.checkAllToggle').html('Uncheck All');
            }else{
                this.$el.find('.checkAllToggle').attr('data-prop','uncheck');
                this.$el.find('.checkAllToggle').html('Check All');
            }
        },

        checkAllToggle: function(evt){
            var currentState = $(evt.target).attr('data-prop');
            if(currentState === 'check'){
                $(evt.target).attr('data-prop','uncheck');
                $(evt.target).html('Check All');
                this.$el.find('input').each(function(index){
                    $(this).prop('checked' , false);
                });
            }else{
                $(evt.target).attr('data-prop','check');
                $(evt.target).html('Uncheck All');
                this.$el.find('input').each(function(index){
                    $(this).prop('checked' , true);
                });
            }
        },

        formatFrequency: function(frequency){
            var freq = frequency+'';
            if(freq.length == 2){
                return '0'+freq;
            }
            if(freq.length == 1){
                return '00'+freq;
            }
            return freq+'';
        },

        prepareToOrder: function(){
            var drugs = getConsultationModel.attributes.cons.prescription.drugs;
            var drug = {};
            var selectedDrugs = cartModel.get('drugs');
            var self = this;
            this.$el.find('input:checked').each(function(index){
                var id = $(this).attr('data-prop');
                drug = drugs[id];
                var freq = (self.formatFrequency(drug.frequency)).split('');
                var dosePerDay = parseInt(freq[0]) + parseInt(freq[1]) + parseInt(freq[2]);
                if(drug.unit === 'tab'){
                    drug.quantity = parseInt(drug.dose) * parseInt(dosePerDay) * parseInt(drug.days); 
                }else{
                    drug.quantity = 1;
                }
                selectedDrugs.push(drug.id , drug);
                drug = {};
            }),
            console.log(selectedDrugs);
            cartModel.set('drugs' , selectedDrugs);
        },

        printPage: function(evt){
            window.print();
        },

        render: function(){
            this.$el.html(summaryScreen(getConsultationModel.attributes.cons));
            util.hideLoader();
        }
    });
    return SummaryView;
});