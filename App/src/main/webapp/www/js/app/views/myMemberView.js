define(function(require) {
    'use strict';    
    var $ = require("jquery"),
        _ = require("underscore"),
        Backbone = require("backbone"),
        memberModel = require("memberModel"),
        util = require("util/util");

    var myMemberScreen  =  require("hbs!tpl/myMember");
    
    var addMemberView = Backbone.View.extend({
    	el : '#page-content-wrapper',

        events : {
            'click .addNewMember' : 'addMember'
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

        getMember: function(){
            util.jqueryGet( config.root + '/user/me/member' , this.getMemberList , this.getMemberECB , this);
        },

        getMemberList: function(response , obj){
            memberModel.set('members' , response);
            obj.$el.html(myMemberScreen(memberModel.attributes));
            util.hideLoader();
        },

        renderMyMember: function(){
           this.$el.html(myMemberScreen(memberModel.attributes)); 
        },

        addMember: function(){
            config.nextPage = 'myMember';
            window.location.hash = 'addMember';
        }
    });
    return addMemberView;
});