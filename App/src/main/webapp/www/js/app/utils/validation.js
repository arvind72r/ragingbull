define(['jquery'], function($) {
    'use strict';

    var validation = {
    	validatePhone: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $(evt.target).prev().addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else if(!(/^\d{10}$/.test(value))){
                $(evt.target).addClass('inputError');
                $(evt.target).prev().addClass('inputError');
                $('#'+errorContainer).html('Invalid Mobile#');
            }else{
                $(evt.target).removeClass('inputError');
                $(evt.target).prev().removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validateName: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else{
                $(evt.target).removeClass('inputError');
                $('#'+errorContainer).html('');
            }    
        },

        validatePassword: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else if(value.length < 8){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Minimum 8 characters');
            }else{
                $(evt.target).removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validateRePassword: function(evt , container){
            var value = $.trim($(evt.target).val());
            var orignalValue = $.trim($('#'+container).val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $(evt.target).prev().addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else if(orignalValue !== value){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Password not matching');
            }else{
                $(evt.target).removeClass('inputError');
                $(evt.target).prev().removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validateSex: function(evt){
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

        validateEmail: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value !== ''){
                if(!(/^([\w-+]+(?:\.[\w-+]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/.test(value))){
                    $(evt.target).addClass('inputError');
                    $('#'+errorContainer).html('Invalid Email');
                }else{
                    $(evt.target).removeClass('inputError');
                    $('#'+errorContainer).html('');
                }
            }else{
                $(evt.target).removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        },

        validateDOB: function(evt){
            var value = $.trim($(evt.target).val());
            var errorContainer = $(evt.target).attr('error');
            if(value === ''){
                $(evt.target).addClass('inputError');
                $('#'+errorContainer).html('Mandatory Field');
            }else{
                $(evt.target).removeClass('inputError');
                $('#'+errorContainer).html('');
            }
        }
	};
    return validation;
});