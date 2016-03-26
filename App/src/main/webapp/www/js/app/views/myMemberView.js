/*jslint browser:true*/
/*global define*/
define(['jquery', 'backbone', 'util/util', 'memberModel', 'hbs!tpl/myMember'],
    function($, backbone,util,memberModel,myMemberScreen) {
    'use strict';
    
    var myMemberView = Backbone.View.extend({
    	el : '#myMemberView',

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

        getMemberECB: function(response , obj){
            obj.$el.html(myMemberScreen(memberModel.attributes));
            util.hideLoader();    
        },

        addMember: function(){
            config.nextPage = 'myMember';
            Backbone.history.navigate('addMember',{trigger:true, replace: false});
        }
    });
    return myMemberView;
});