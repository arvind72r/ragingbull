/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'util/util', 'util/validation', 'memberModel', 'hbs!tpl/addLocation'], 
    function($, backbone,util,validate,memberModel,addLocationScreen) {
    'use strict';

    var addLocationView = Backbone.View.extend({
    	el : '#addLocationView',

        events : {
            'click .addLoc': 'addLocation',
            'blur #locPrimaryContact': 'validatePhone',
            'blur #secondaryContact': 'validatePhone',
            'blur #speciality': 'validateSpeciality',
            'blur .mandateField': 'mandateField',
            'click .addAnotherLoc': 'addAnotherLoc'
        },
    	
    	initialize: function () {
            try{
                
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },

        validateSpeciality: function(evt){
            var value = $(evt.target).val();
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).parent().addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else{
                $(evt.target).parent().removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        mandateField: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $(evt.target).prev().addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else{
                $(evt.target).removeClass('inputError');
                $(evt.target).prev().removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validatePhone: function(evt){
            validate.validatePhone(evt);
        },

        addLocation: function(){
            var self = this;
            util.showLoader();
            $('.practLogReg input').each(function(){
                $(this).filter(':visible').blur();
            });
            if($('.practLogReg .inputError').length > 0){
                util.hideLoader();
                $(window).scrollTop($('.practLogReg .inputError').first().offset().top); 
                return true;
            }
            var registerLocJson = util.serializeObject($('form[name=addPractLoc]'));
            registerLocJson.country = 'India';
            var address = registerLocJson.address1 + ' ' + registerLocJson.address2 + ' ' + registerLocJson.city + ' ' + registerLocJson.state + ' ' + registerLocJson.zip + ' ' + registerLocJson.country;
            config.geocoder.geocode( { 'address': address}, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    registerLocJson.latitude = results[0].geometry.location.lat();
                    registerLocJson.longitude = results[0].geometry.location.lng();
                    self.addLocation2(registerLocJson);
                } else {
                    registerLocJson.latitude = 0.00;
                    registerLocJson.longitude = 0.00;
                    self.addLocation2(registerLocJson);
                }
            });
        },

        addLocation2: function(registerLocJson){
            registerLocJson.primaryContact = '+91'+registerLocJson.primaryContact;
            registerLocJson.secondaryContact = '+91'+registerLocJson.secondaryContact;
            registerLocJson.zip = parseInt(registerLocJson.zip);
            registerLocJson.workingHours = parseInt(registerLocJson.workingHours);
            registerLocJson.workingDays = parseInt(registerLocJson.workingDays);
            var url = config.root + '/practitioner/' + self.userPractId + '/location';
            util.jqueryPost(url, registerLocJson, this.addLocSuccess, this.addLocError, this);
        },

        addLocSuccess: function(){
            $('#finalScreen').show();
            $('#addDocLocScreen').hide();
            util.hideLoader();
        },

        addLocError: function(response , registerLocJson, obj){
            obj.$el.find('.addDocLocError').html("We are unable to add this user to said location. Please try again Later.");
            obj.$el.find('.addDocLocError').show();
            util.hideLoader();
        },

        addAnotherLoc: function(){
            $('#finalScreen').hide();
            $('#addDocLocScreen').show();
        },

        render:function(){
            var url = config.root + '/practitioner/me';
            util.jqueryGet(url, this.renderPage, this.renderPageError, this);
        },

        renderPage: function(response , obj){
            var self = obj;
            self.practId = response.id;
            self.$el.html(addLocationScreen());
            util.hideLoader();
        },

        renderPageError: function(){

        }
    });
    return addLocationView;
});