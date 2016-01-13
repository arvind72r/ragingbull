define(['underscore','backbone'], function (_,Backbone) {
"use strict"; // jshint ;_;

    var UserModel = Backbone.Model.extend({
        
        initialize: function() {
            
        },

        defaults: {
            "token": '',
            "userEmail": '',
            "isUserVerified": false,
            "expiry": 1449513000000,
            "userSignedIn": false
        }



    });    
    return new UserModel();
});
