define(['jquery', 'util/util', 'userDetailModel'], function($,util,userDetailModel) {
    'use strict';

    var component = {
        _counter: 0,
        fetchUser: function(){
            util.jqueryGet( config.root + '/user/me?hydrated=true' , this.getUser , this.getUserError , this);
        },

        getUser:function(response , obj){
            userDetailModel.set(response);
            config.userSession = true;
            window.localStorage.setItem('userDetailModelLocal' , JSON.stringify(userDetailModel.attributes));
            if(userDetailModel.get('email') === 'admin@aredvi.com'){
                window.location.hash = 'addDoctor';    
            }else{
                window.location.hash = 'dashboard';
            }
        },

        getUserError:function(response , obj){
            if(obj._counter === 0){
                obj._counter++;
                obj.fetchUser();
            }else{
                localStorage.removeItem('authToken');
                localStorage.removeItem('header');
                localStorage.removeItem('userDetailModelLocal');
                localStorage.removeItem('userModelLocal');
                userModel.set(userModel.defaults);
                userDetailModel.set(userDetailModel.defaults);
                memberModel.set(memberModel.defaults);
                allConsultationModel.set(allConsultationModel.defaults);
                getConsultationModel.set(getConsultationModel.defaults);
                config.userSession = false;
                localStorage.setItem('getUserError' , true);
                window.location.reload();
            }
        }
    	
	};
    return component;
});