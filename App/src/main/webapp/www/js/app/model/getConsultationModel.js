define(['underscore','backbone'], function (_,Backbone) {
"use strict"; // jshint ;_;

    var GetConsultationModel = Backbone.Model.extend({
        
        initialize: function() {
            
        },

        defaults: {
            "cons": {},
            "setting": {},
            "prescRow": 0
        }



    });    
    return new GetConsultationModel();
});
