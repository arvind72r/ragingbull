define(['facebook'], function(){
  
  FB.init({
    appId      : '668873236586026',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.4'
  });
  
  // FB.getLoginStatus(function(response) {
  //                   console.log(response);
  //               });

});