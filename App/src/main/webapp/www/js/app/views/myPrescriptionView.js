/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'util/util', 'memberModel', 'hbs!tpl/myPrescription'],
    function($, backbone,util,memberModel,myPrescriptionScreen) {
    'use strict';

    var myPrescriptionView = Backbone.View.extend({
    	el : '#myPrescription',

        events : {
            'click .cameraUpload': 'cameraUpload',
            'click .galleryUpload': 'galleryUpload'
    	},

    	initialize: function () {
            try{

            }catch(e){
                console.log('error in initialize --> ' + e);
            }
        },

        cameraUpload: function(){
            navigator.camera.getPicture(this.displayAndSend, this.onError, { quality: 50,
                destinationType: Camera.DestinationType.DATA_URL,
                sourceType: Camera.PictureSourceType.CAMERA
            });
        },

        galleryUpload: function(){
            navigator.camera.getPicture(this.displayAndSend, this.onError, { quality: 50,
                destinationType: Camera.DestinationType.DATA_URL,
                sourceType: Camera.PictureSourceType.PHOTOLIBRARY
            });
        },

        displayAndSend: function(imageData){
            //alert(imageData);
            $('#image').show();
            var image = $('#imageTag');
            image.attr('src' , "data:image/jpeg;base64," + imageData);
        },

        render : function(){
            try{
            	this.getPrescription();
            }catch(e){
                console.log('error in render --> ' + e);
            }
        },

        getPrescription: function(){
            //util.jqueryGet( config.root + '/user/me/member' , this.getMemberList , this.getMemberECB , this);
            this.getPrescriptionList('' , this);
        },

        getPrescriptionList: function(response , obj){
            //memberModel.set('members' , response);
            var data = {};
            data.staticPath = config.staticPath;
            data.isDevice = !(window.location.protocol === 'http:' || window.location.protocol === 'https:');
            obj.$el.html(myPrescriptionScreen(data));
            util.hideLoader();
        }
    });
    return myPrescriptionView;
});