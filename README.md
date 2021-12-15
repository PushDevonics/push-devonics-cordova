[![Release](https://jitpack.io/v/PushDevonics/push-devonics-cordova.svg)](https://jitpack.io/#PushDevonics/push-devonics-cordova)


Install Plugin:

    cordova plugin add cordova-plugin-pushdevonics

Init Push on index.js

    function initPush(result) {
        console.log(result);
     };
     function initError(error) {
         console.error(error);
     };
     Push.pushInit(initPush, initError);
     
     
=====================================================     


Add it to you settings.gradle in repositories:

    repositories {
            google()
            mavenCentral()
            maven { url 'https://jitpack.io' }
    }
and:

    dependencies {
        implementation platform('com.google.firebase:firebase-bom:28.3.1')
        implementation 'com.google.firebase:firebase-messaging-ktx'
        implementation 'com.github.PushDevonics:push-devonics-cordova:latest version'
    }

Java:

MainActivity:

    private PushDevonics pushDevonics;
    
MainActivity in onCreate():

    pushDevonics = new PushDevonics(getApplicationContext(), "appId");

    // If you need internalId
    String internalId = pushDevonics.getInternalId();
    
    // If you want add tag type String
    pushDevonics.setTags("key", "value");
    
MainActivity in onResume():

    pushDevonics.startSession();
    pushDevonics.sendIntent(getIntent());
        
MainActivity in onDestroy():

    pushDevonics.stopSession();
