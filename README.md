[Spiffy UI](http://www.spiffyui.org) - GWT made simple
==================================================

Hello Spiffy OAuth is an application created from the [Spiffy UI Framework](http://www.spiffyui.org) project creator, that builds a simple REST application with Apache Maven and uses [Scribe](https://github.com/fernandezpablo85/scribe-java) by [Pablo Fernandez](http://www.linkedin.com/in/fernandezpablo85) to access [Google Contacts data API](http://code.google.com/apis/contacts/docs/1.0/developers_guide_js.html) through [OAuth](http://oauth.net/).


Building and Running HelloSpiffyOAuth
--------------------------------------

This project is built with [Apache Maven](http://maven.apache.org/).  
    
Go to your project's root directory and run the following command:

    mvn package jetty:run
        
This will download the required libraries, including Scribe, build your project, and run it with an embedded Jetty web server.  You can access the running application here:

    http://localhost:8080
    

How it works
--------------------------------------
[OAuth](http://oauth.net/) is an open protocol to allow secure API authorization in a standard way without sharing usernames or passwords.  A user can logs in to a service provider (such as Google) and grant restricted access to a third-party app (such as this sample project) to your private data (such as your Google contacts). [Scribe](https://github.com/fernandezpablo85/scribe-java) is an OAuth library for Java and supports all major OAuth APIs out of the box, including Google, Facebok, Yahoo, LinkedIn, Twitter and more.

The client for this project is a simple UI that has two buttons: 1. Request access from Google and 2. Get contacts.

When you click the first button, the browser is directed to [AuthGoogle](https://github.com/spiffyui/HelloSpiffyOAuth/blob/master/src/main/java/org/spiffyui/hellospiffyoauth/server/AuthGoogle.java), the first of two servlets that use the [Scribe](https://github.com/fernandezpablo85/scribe-java) library.  AuthGoogle will build the [OAuthService](https://github.com/fernandezpablo85/scribe-java/blob/master/src/main/java/org/scribe/oauth/OAuthService.java) and fetch a request token from Google.  The request token has a visible as well as secret component.  The service and full request token are stored in-memory using the visible part of the request token as the key.  The servlet will then redirect to Google to allow the user to login and grant access.  Because the OAuthService sent a callback to Google, Google will redirect back to this application with the visible part of the request token and a verifier as URL parameters.  

When you are returned to this application, the client recognizes that it now has URL parameters and assumes this means you have been granted access.  You can then click the second button to get your Google contacts.  Using [Spiffy UI's REST framework](http://www.spiffyui.org/?rest), the client will hit the GoogleContactsServlet passing along the URL parameters received from Google.  GoogleContactsServlet retrieves the service and full request token using the request token URL parameter as the key from the in-memory storage, then the service will trade the request token and verifier for an access token.  [Scribe](https://github.com/fernandezpablo85/scribe-java) signs the request to access the [Google Contacts data API](http://code.google.com/apis/contacts/docs/1.0/developers_guide_js.html) for your top 200 contacts as JSON.  The client then displays them on the page.


Debugging through Eclipse
--------------------------------------

See [Spiffy UI's GWT Dev Mode page](http://www.spiffyui.org/#!hostedMode) for more information.


License
--------------------------------------

Spiffy UI is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
