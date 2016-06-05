/* global requirejs */
requirejs.config({
    baseUrl: 'www/js',
    paths: {
        //library definitions
        'facebook': 'lib/social/facebook',
        'google': 'lib/social/google',
        'gpl': 'lib/social/gpl',
        'backbone': 'lib/backbone/backbone',
        'underscore': 'lib/underscore/underscore',
        'jquery': 'lib/jquery/jquery-2.1.3',
        'bootstrap': 'lib/bootstrap/bootstrap',
        'jqueryui': 'lib/jquery-ui/jquery-ui',
        
        //hbs dependency is not to worry
        //why? - https://github.com/SlexAxton/require-handlebars-plugin#so-many-dependencies-in-the-hbs-plugin
        //DONOT explicitly require handlbars anywhere
        //the following plugin will take the apropriate handlebar library, the minimal runtime or the full lib 
        //based on built code or not
        'hbs': 'lib/require-handlebars-plugin/hbs',
        'tpl': '../template',
        'templates': '../template',
       	'handlebars': 'lib/handlebars/handlebars-v3.0.3',
       	'text'  : 'lib/requirejs/text',
        
        //async plugin to download the google maps api
        'async'  : 'lib/requirejs/async',

       	//app specific definitions
        //page definitions
        'app': 'app/endpoints/app',
        'util': 'app/utils',
        
        //routers
        'appRouter': 'app/routers/appRouter',
        
        //utils
        
        
        //global views
        
        
        //User Managment views
        'loginView': 'app/views/loginView',
        'registerView': 'app/views/registerView',
        'createConsultationView': 'app/views/createConsultationView',
        'addMemberView': 'app/views/addMemberView',
        'myMemberView': 'app/views/myMemberView',
        'myProfileView': 'app/views/myProfileView',
        'dashboardView': 'app/views/dashboardView',
        'addSymptomsView': 'app/views/addSymptomsView',
        'summaryView': 'app/views/summaryView',
        'navigationView': 'app/views/navigationView',
        'myPrescriptionView': 'app/views/myPrescriptionView',
        'genericModalView': 'app/views/genericModalView',
        'addDoctorView': 'app/views/addDoctorView',
        'addLocationView': 'app/views/addLocationView',
        
        //Model
        'userModel' : 'app/model/userModel',
        'userDetailModel' : 'app/model/userDetailModel',
        'memberModel' : 'app/model/memberModel',
        'cartModel' : 'app/model/cartModel',
        'addressModel' : 'app/model/addressModel',
        'getConsultationModel' : 'app/model/getConsultationModel',
        'allConsultationModel' : 'app/model/allConsultationModel',
        'fb' : 'app/social/fb',
        'gp' : 'app/social/gp',
        
    },
    //handlebars-requirejs integration config
    hbs: { // optional
        helpers: true,            // default: true
        i18n: false,              // default: false
        templateExtension: 'hbs', // default: 'hbs'
        partialsUrl: '',           // default: ''
        compileOptions: {
          minify: true
        }
    },
    pragmasOnSave: {
        //removes Handlebars.Parser code (used to compile template strings) set
        //it to `false` if you need to parse template strings even after build
        excludeHbsParser : true,
        // kills the entire plugin set once it's built.
        excludeHbs: true,
        // removes i18n precompiler, handlebars and json2
        excludeAfterBuild: true
    },
    waitSeconds: 0,
    shim: {
        'facebook' : {
            exports: 'FB'
        },
        underscore: {
            exports: '_'
        },
        jquery: {
            exports: '$'
        },
        backbone: {
            deps: ['underscore', 'jquery'],
            exports: 'backbone'
        },
        bootstrap: {
  		  deps: ['jquery']
  		},
        jqueryui: {
            deps: ['jquery']
        },
        jqueryuitouch: {
            deps: ['jqueryui', 'jquery']
        },
  		handlebars : {
  			exports: 'Handlebars'
  		},
  		tpl : {
  			deps: ['handlebars']
  		}
    }
});