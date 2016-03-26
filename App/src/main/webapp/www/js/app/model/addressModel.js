define(['underscore','backbone'], function (_,Backbone) {
"use strict"; // jshint ;_;

    var AddressModel = Backbone.Model.extend({
        
        initialize: function() {
            
        },

        defaults: {
            'address':[]
        }



    });    
    return new AddressModel();
});
