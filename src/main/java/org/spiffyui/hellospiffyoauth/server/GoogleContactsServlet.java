/*******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.spiffyui.hellospiffyoauth.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * This is the REST service.  It assumes you have already been
 * granted access and will be getting the oauth_verifier and oauth_token
 * as request parameters.  It uses the oauth_token as the key to get
 * the OAuthService and full request Token, obtains an access token,
 * signs the request, then sends the body of the "oauth'ed" response (which is JSON)
 * back as part of the HttpServletResponse. 
 * 
 * This uses Scribe (https://github.com/fernandezpablo85/scribe-java) libraries,
 * and you may recognize comments and System.out.printlns from 
 * https://github.com/fernandezpablo85/scribe-java/blob/master/src/test/java/org/scribe/examples/GoogleExample.java
 */
public class GoogleContactsServlet extends HttpServlet
{
    private static final String OAUTH_VERIFIER = "oauth_verifier";
    private static final String OAUTH_TOKEN = "oauth_token";
    
    //Google Contacts
    private static final String PROTECTED_RESOURCE_URL = "https://www.google.com/m8/feeds/contacts/default/full?alt=json";

    private static final long serialVersionUID = -1L;
    

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String oauthVerifier = request.getParameter(OAUTH_VERIFIER);
        String oauthToken = request.getParameter(OAUTH_TOKEN);
        /*
         * Note the oauthToken is the token part of the request token
         * and was used as the unique ID for the TokenManager in AuthGoogle.
         */
        
        OAuthService service = TokenManager.getService(oauthToken);
        
        Verifier verifier = new Verifier(oauthVerifier);
        
        // Trade the Request Token and Verifier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = TokenManager.getAccessToken(oauthToken);
        if (accessToken == null) {
            Token requestToken = TokenManager.getRequestToken(oauthToken);        
            accessToken = service.getAccessToken(requestToken, verifier);
            TokenManager.putAccessToken(oauthToken, accessToken);
        }
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken + " )");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        OAuthRequest oauthrequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, oauthrequest);
        oauthrequest.addHeader("GData-Version", "3.0");
        Response oauthresponse = oauthrequest.send();
        System.out.println("Got it!  Sending it through to the http response...");

        /*
         * TODO handle error conditions
         */
        response.setStatus(oauthresponse.getCode());
        response.setContentType("application/json");
        ServletOutputStream out = response.getOutputStream();
        out.println(oauthresponse.getBody());
        out.close();
    }

}
