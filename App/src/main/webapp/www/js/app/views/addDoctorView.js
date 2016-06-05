/*jslint browser:true*/
/*global define*/
define(['jquery', 'underscore', 'backbone', 'util/util', 'util/validation', 'memberModel', 'hbs!tpl/addDoctor'], 
    function($,_, backbone,util,validate,memberModel,addDoctorScreen) {
    'use strict';

    var addDoctorView = Backbone.View.extend({
    	el : '#addDoctorView',
        userToken : '',

        events : {
            'click .addDocUser': 'registerUser',
            'click .addUserAsDoc': 'registerUserAsDoctor',
            'click .addDocLoc': 'addDocLocation',
            'blur #docPhone': 'validatePhone',
            'blur #docSecPhone': 'validatePhone',
            'blur #locPrimaryContact': 'validatePhone',
            'blur #secondaryContact': 'validatePhone',
            'blur #docName': 'validateName',
            'blur #docEmail': 'validateEmail',
            'blur #docDob': 'validateDOB',
            'blur #docSex': 'validateSex',
            'blur #speciality': 'validateSpeciality',
            'blur .mandateField': 'mandateField',
            'click .addAnotherDoc' : 'addAnotherDoc',
            'change #practLoc' : 'locationSelect'
    	},
    	
    	initialize: function () {
            try{
                
            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },

        render: function(){
            this.$el.html(addDoctorScreen());
            util.setMaxDate('dob');
            util.hideLoader();
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

        validateName: function(evt){
            validate.validateName(evt);    
        },

        validateSex: function(evt){
            validate.validateSex(evt);
        },

        validateEmail: function(evt){
            validate.validateEmail(evt);
        },

        validateDOB: function(evt){
            validate.validateDOB(evt);
        },

        registerUser: function(){
            try{
                util.showLoader();
                $('.userReg input').each(function(){
                    $(this).filter(':visible').blur();
                });

                $('.userReg select').blur();

                if($('.userReg .inputError').length > 0){
                    util.hideLoader();
                    $(window).scrollTop($('.userReg .inputError').first().offset().top); 
                    return true;
                }

                var registerJson = util.serializeObject($('form[name=register]'));
                var pwd = (registerJson.phone).substring(0,2) + ((registerJson.name).replace(' ', '')).substring(0,4) + (registerJson.dob).substring(0,4);
                registerJson.inletType = 'SELF';
                registerJson.phone = '+91' + registerJson.phone;
                registerJson.password = pwd;
                delete registerJson['userRePassword'];
                var url = config.root + '/register';
                util.jqueryPost(url , registerJson , this.registerUserCallback , this.registerUserErrorCb , this);

            }catch(e){
                console.log('error in registerUser -->' + e)
            }
        },

        registerUserCallback: function(response , input , obj){
            try{
                var self = obj;
                self.userToken = response.token;
                self.userId = response.userId;
                $('#addUserScreen').hide();
                $('#addDocScreen').show();
                $('#primaryContact').html(input.phone);
                util.hideLoader();
            }catch(e){

            } 
        },

        registerUserErrorCb: function(response , input , obj){
            if(response.status === 409){
                obj.$el.find('.addUserError').html("User already exist.");
                obj.$el.find('.addUserError').show();
                util.hideLoader();
            }else{
                obj.$el.find('.addUserError').html("Your Registration didn't went through, please try again. Inconvinience Regereted");
                obj.$el.find('.addUserError').show();
                util.hideLoader();
            }
        },

        registerUserAsDoctor: function(){
            try{
                var self = this;
                util.showLoader();
                $('.practReg input').each(function(){
                    $(this).filter(':visible').blur();
                });
                if($('.practReg .inputError').length > 0){
                    util.hideLoader();
                    $(window).scrollTop($('.practReg .inputError').first().offset().top); 
                    return true;
                }

                var registerJson = util.serializeObject($('form[name=addPract]'));
                registerJson.primaryContact = $('#primaryContact').html();
                var url = config.root + '/practitioner';
                $.support.cors = true;
                jQuery.ajax ({
                    url: url,
                    type: "POST",
                    crossDomain: true,
                    data: JSON.stringify(registerJson),
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    beforeSend: function (xhr){ 
                        xhr.setRequestHeader('Auth-Token', self.userToken);
                    },
                    success: function(response){
                        self.regDocSuccess(response, registerJson , self);
                    },
                    error: function(response){
                        self.regDocError(response, registerJson , self);
                    }
                });
            }catch(e){
                console.log('error in registerUserAsDoctor -->' + e)
            }
        },

        regDocSuccess: function(response , regInput , obj){
            try{
                var self = obj;
                //self.userPractId = response.id;
                var url = config.root + '/location';
                util.jqueryGet(url , this.populateLocationCallback , this.populateLocationErrorCb , this);

                
            }catch(e){

            } 
        },

        populateLocationCallback: function(response , obj){
            var html = '<option value="">Select Location</option><option value="addNew">Add New Location</option>';
            _.each(response, function(location) {
                html = html + '<option value="'+location.locationId+'">'+location.name+'-'+location.location+'</option>'
            });
            $('#practLoc').html(html);
            $('#addUserScreen').hide();
            $('#addDocScreen').hide();
            $('#addDocLocScreen').show();
            util.hideLoader();
        },

        regDocError: function(response , obj){
            var html = '<option value="">Select Location</option><option value="addNew">Add New Location</option>';
            $('#practLoc').html(html);
            $('#addUserScreen').hide();
            $('#addDocScreen').hide();
            $('#addDocLocScreen').show();
            util.hideLoader();
        },

        locationSelect: function(e){
            var val = $('#practLoc').val();
            if(val === 'addNew'){
                $('#newLocForm').show();
            }else{
                $('#newLocForm').hide();
            }
        },

        addDocLocation: function(){
            var val = $('#practLoc').val();
            if(val === 'addNew'){
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
                        self.addDocLocation2(registerLocJson);
                    } else {
                        registerLocJson.latitude = 0.00;
                        registerLocJson.longitude = 0.00;
                        self.addDocLocation2(registerLocJson);
                    }
                });
            }else{
                self.addDocToLoc(val);
            }
        },

        addDocLocation2: function(registerLocJson){
            registerLocJson.primaryContact = '+91'+registerLocJson.primaryContact;
            registerLocJson.secondaryContact = '+91'+registerLocJson.secondaryContact;
            registerLocJson.zip = parseInt(registerLocJson.zip);
            registerLocJson.workingHours = parseInt(registerLocJson.workingHours);
            registerLocJson.workingDays = parseInt(registerLocJson.workingDays);
            var self = this;
            var url = config.root + '/practitioner/' + self.userPractId + '/location';
            $.support.cors = true;
            jQuery.ajax ({
                url: url,
                type: "POST",
                crossDomain: true,
                data: JSON.stringify(registerLocJson),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                beforeSend: function (xhr){ 
                    xhr.setRequestHeader('Auth-Token', self.userToken);
                },
                success: function(response){
                    self.addDocToLocInt(response, registerLocJson, self);
                },
                error: function(response){
                    self.regDocLocError(response, registerLocJson, self);
                }
            });
        },

        addDocToLocInt: function(response , registerLocJson, obj){
            var self = obj;
            self.locationId = response.id;
            self.addDocToLoc(self.locationId);
        },

        addDocToLoc: function(locationId){
            var url = config.root + '/location/' + locationId + '/users';
            var data = [];
            var user = {};
            user.userId = self.userId;
            data.push(user);
            $.support.cors = true;
            jQuery.ajax ({
                url: url,
                type: "PUT",
                crossDomain: true,
                data: JSON.stringify(data),
                dataType: "text",
                contentType: "application/json; charset=utf-8",
                beforeSend: function (xhr){ 
                    xhr.setRequestHeader('Auth-Token', self.userToken);
                },
                success: function(response){
                    self.regDocLocSuccess(response, registerLocJson, self);
                },
                error: function(response){
                    self.regDocLocError(response, registerLocJson, self);
                }
            });
        },

        regDocLocSuccess: function(response , registerLocJson, obj){
            try{
                var self = obj;
                $('#addUserScreen').hide();
                $('#addDocScreen').hide();
                $('#addDocLocScreen').hide();
                $('#finalScreen').show();
                util.hideLoader();
            }catch(e){

            } 
        },

        regDocLocError: function(response , registerLocJson, obj){
            obj.$el.find('.addDocLocError').html("We are unable to add this user to said location. Please try again Later.");
            obj.$el.find('.addDocLocError').show();
            util.hideLoader();
        },

        addAnotherDoc: function(){
            $('#addUserScreen').show();
            $('#addDocScreen').hide();
            $('#addDocLocScreen').hide();
            $('#finalScreen').hide();
        }
    });
    return addDoctorView;
});