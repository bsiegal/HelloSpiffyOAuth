[Hello Spiffy OAuth](http://www.spiffyui.org) - GWT made simple
==================================================

This is an application created from the [Spiffy UI Framework](http://www.spiffyui.org) project creator, which builds a simple REST application with Apache Maven and uses [Scribe](https://github.com/fernandezpablo85/scribe-java) to access [Google Contacts data API](http://code.google.com/apis/contacts/docs/1.0/developers_guide_js.html) through [OAuth](http://oauth.net/)


Building and Running HelloSpiffyOAuth
--------------------------------------

This project is built with [Apache Maven](http://maven.apache.org/).  
    
Go to your project's root directory and run the following command:

    mvn package jetty:run
        
This will download the required libraries, including Scribe, build your project, and run it.  You can access the running application here:

    http://localhost:8080
    

Debugging through Eclipse
--------------------------------------

See [Spiffy UI's GWT Dev Mode page](http://www.spiffyui.org/#!hostedMode) for more information.


License
--------------------------------------

Spiffy UI is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
