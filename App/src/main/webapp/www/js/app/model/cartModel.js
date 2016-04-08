define(['underscore','backbone'], function (_,Backbone) {
"use strict"; // jshint ;_;

    var CartModel = Backbone.Model.extend({
        
        initialize: function() {
            
        },

        defaults: {
            'drugs':[]
        }



    });    
    return new CartModel();
});
