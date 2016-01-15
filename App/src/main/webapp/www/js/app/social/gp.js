define(['google'], function(){
  
  // gapi.auth2.init({
  //   client_id : '665618557585-eimognveu2lunulgv8ff1810qrc8ifqf.apps.googleusercontent.com',
  //   'scope': 'profile email'
  // });

  gapi.load('auth2', function() {
      gapi.auth2.init({
        client_id : '665618557585-eimognveu2lunulgv8ff1810qrc8ifqf.apps.googleusercontent.com',
        fetch_basic_profile: false,
        scope:'https://www.googleapis.com/auth/plus.login'
      });
  });

});