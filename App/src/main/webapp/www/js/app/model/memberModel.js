define(['backbone'], function (Backbone) {
"use strict"; // jshint ;_;

    var MemberModel = Backbone.Model.extend({
        
        initialize: function() {
            
        },

        defaults: {
            'members':[]
        }



    });    
    return new MemberModel();
});
