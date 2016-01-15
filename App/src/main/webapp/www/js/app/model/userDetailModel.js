define(['underscore','backbone'], function (_,Backbone) {
"use strict"; // jshint ;_;

    var UserDetailModel = Backbone.Model.extend({
        
        initialize: function() {
            
        },

        defaults: {
            "id": "",
            "name": "",
            "email": "",
            "phone": "",
            "inletType": "",
            "verified": false,
            "active": false,
            "sex": "",
            "dob": 0,
            "age": 0
        }



    });    
    return new UserDetailModel();
});
