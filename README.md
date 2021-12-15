[![Release](https://jitpack.io/v/PushDevonics/push-devonics-cordova.svg)](https://jitpack.io/#PushDevonics/push-devonics-cordova)


Install Plugin:

    cordova plugin add cordova-plugin-pushdevonics

Init Push on index.js

    function call(result) {
        console.log(result);
     };
     function callError(error) {
         console.error(error);
     };
     Push.pushInit("APP_ID", call, callError);

If you need Push User ID
        
     Push.pushGetUserId(function(result) { console.log(result); }, callError);

If you want add TAG to User

     Push.pushSendTag("KEY", "VALUE", call, callError);

     
     
     
==========================================================================================================
