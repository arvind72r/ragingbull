define(['hbs/handlebars'], function ( Handlebars ) {
    function ifEqual(value1 , value2 , options){
        if(typeof value1 == 'string'){
            if(value1.toLowerCase() == value2.toLowerCase()){
                return options.fn(this);
            }
            return options.inverse(this);
        } else{
            if(value1 == value2){
                return options.fn(this);
            }
            return options.inverse(this);
        }
    }
    Handlebars.registerHelper('ifEqual' , ifEqual);
    return ifEqual;
});