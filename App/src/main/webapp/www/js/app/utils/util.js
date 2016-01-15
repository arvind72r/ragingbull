define(['jquery'], function($) {
    'use strict';

    var util = {
		jqueryPost : function (url , inputData , callBackSuccess , callBackFailure , callBackParam) {
            try{
                $.support.cors = true;
	            var data = '';
                var dataType = 'json';
	            if(typeof inputData === 'object'){
	                data = JSON.stringify(inputData);
	            }else{
	                data = inputData;
	            }

                if(url.indexOf('user/me/member') > -1 || url.indexOf('verifyId') > -1 || url.indexOf('/notes/DIAGNOSIS') > -1 || url.indexOf('/notes/SYMPTOMS') > -1){
                    dataType = 'text';
                }

	            jQuery.ajax ({
	                url: url,
	                type: "POST",
                    crossDomain: true,
	                data: data,
	                dataType: dataType,
	                contentType: "application/json; charset=utf-8",
                    beforeSend: function (xhr){ 
                        xhr.setRequestHeader('Auth-Token', localStorage.getItem('authToken'));
                    },
	                success: function(response){
	                    callBackSuccess(response , inputData , callBackParam);
	                },
	                error: function(){
	                    callBackFailure(null , inputData , callBackParam);
	                }
	            });
            }catch(e){
                console.log('jquery post' +e);
            }
        },

        jqueryPut : function (url , inputData , callBackSuccess , callBackFailure , callBackParam) {
            try{
                $.support.cors = true;
                var data = '';
                if(typeof inputData === 'object'){
                    data = JSON.stringify(inputData);
                }else{
                    data = inputData;
                }

                jQuery.ajax ({
                    url: url,
                    type: "PUT",
                    crossDomain: true,
                    data: data,
                    dataType: "text",
                    contentType: "application/json; charset=utf-8",
                    beforeSend: function (xhr){ 
                        xhr.setRequestHeader('Auth-Token', localStorage.getItem('authToken'));
                    },
                    success: function(response){
                        callBackSuccess(response , inputData , callBackParam);
                    },
                    error: function(){
                        callBackFailure(null , inputData , callBackParam);
                    }
                });
            }catch(e){

            }
        },

        jqueryGet : function (url , callBackSuccess , callBackFailure , callBackParam) {
            try{
                $.support.cors = true;
                jQuery.ajax ({
                    url: url,
                    type: "GET",
                    crossDomain: true,
                    contentType: "application/json; charset=utf-8",
                    beforeSend: function (xhr){ 
                        xhr.setRequestHeader('Auth-Token', localStorage.getItem('authToken')); 
                    },
                    success: function(response){
                        callBackSuccess(response , callBackParam);
                    },
                    error: function(){
                        callBackFailure(null , callBackParam);
                    }
                });
            }catch(e){

            }
        },

        jqueryAuthPost : function(url , inputData , callBackSuccess , callBackFailure , callBackParam) {
            try{
                $.support.cors = true;
                var header = this.make_base_auth(inputData.username , inputData.password);
                localStorage.setItem('header' , header);
                $.ajax({
                    type: "POST",
                    url: url,
                    dataType: 'json',
                    //async: false,
                    crossDomain: true,
                    beforeSend: function (xhr){ 
                        xhr.setRequestHeader('Authorization', header); 
                    },
                    success: function (response){
                        callBackSuccess(response , callBackParam); 
                    },
                    error: function (response){
                        callBackFailure(response , callBackParam);
                    }
                });
            }catch(e){

            }
        },

        make_base_auth: function(user , password){
            var tok = user + ':' + password;
            var hash = btoa(tok);
            return "Basic " + hash;
        },

        serializeObject: function(form) {
    		var o = Object.create(null),
        	elementMapper = function(element) {
            	element.name = $.camelCase(element.name);
            	return element;
        	},
        	appendToResult = function(i, element) {
            	var node = o[element.name];

            	if ('undefined' != typeof node && node !== null) {
                	o[element.name] = node.push ? node.push(element.value) : [node, element.value];
            	} else {
                	o[element.name] = element.value;
            	}
        	};

    		$.each($.map(form.serializeArray(), elementMapper), appendToResult);
    		return o;
		},

		showLoader: function(){
		    $('#overlay').show();
		},

		hideLoader: function(){
		    $('#overlay').hide();
		}
    };
    return util;
});