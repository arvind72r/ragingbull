define(['hbs/handlebars'], function ( Handlebars ) {
    function formatFreq(value , options){
        var freq = value+'';
        if(freq.length == 2){
            freq = '0'+freq;
        }
        if(freq.length == 1){
            freq = '00'+freq;
        }
        freq = freq.split('');
        return freq[0] + ' - ' + freq[1] + ' - ' + freq[2];
    }
    Handlebars.registerHelper('formatFreq' , formatFreq);
    return formatFreq;
});