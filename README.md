[Spiffy UI](http://www.spiffyui.org) - GWT made simple
==================================================

Hello Spiffy OAuth is an application created from the [Spiffy UI Framework](http://www.spiffyui.org) project creator, that builds a simple REST application with Apache Maven and uses [Scribe](https://github.com/fernandezpablo85/scribe-java) to access [Google Contacts data API](http://code.google.com/apis/contacts/docs/1.0/developers_guide_js.html) through [OAuth](http://oauth.net/).


Building and Running HelloSpiffyOAuth
--------------------------------------

This project is built with [Apache Maven](http://maven.apache.org/).  
    
Go to your project's root directory and run the following command:

    mvn package jetty:run
        
This will download the required libraries, including Scribe, build your project, and run it with an embedded Jetty web server.  You can access the running application here:

    http://localhost:8080
    

How it works
--------------------------------------
There are two servlets in this project which use the [Scribe](https://github.com/fernandezpablo85/scribe-java) libraries.  

The first, AuthGoogle, will build the OAuthService and fetch a request token from Google.  The service and token are stored in-memory for simplicity.  It will then redirect to Google to allow the user to login and grant access.  Google will redirect back to this application with the request token and verifier.  

The second, GoogleContactsServlet, is the REST service.  Here, [Scribe](https://github.com/fernandezpablo85/scribe-java) will trade the request token and verifier for an access token using the service retrieved from the in-memory storage, then sign the request to access the [Google Contacts data API](http://code.google.com/apis/contacts/docs/1.0/developers_guide_js.html) to get your top 200 contacts as JSON.


Debugging through Eclipse
--------------------------------------

See [Spiffy UI's GWT Dev Mode page](http://www.spiffyui.org/#!hostedMode) for more information.


License
--------------------------------------

Spiffy UI is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
