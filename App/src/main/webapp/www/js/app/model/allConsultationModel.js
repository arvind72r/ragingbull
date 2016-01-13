define(['underscore','backbone'], function (_,Backbone) {
"use strict"; // jshint ;_;

    var AllConsultationModel = Backbone.Model.extend({
        
        initialize: function() {
            
        },

        defaults: {
            "memberCurrent": {},
            "memberPast": {},
            "practitionerCurrent": {},
            "practitionerPast": {},
            "userCurrent": {},
            "userPast": {}
        }



    });    
    return new AllConsultationModel();
});
