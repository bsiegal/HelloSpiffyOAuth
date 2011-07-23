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
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * This servlet redirects the browser to OAuth to google contacts
 * and provides a call back to come back to hellospiffyoauth.
 * 
 * This uses Scribe (https://github.com/fernandezpablo85/scribe-java) libraries,
 * and you may recognize comments and System.out.printlns from 
 * https://github.com/fernandezpablo85/scribe-java/blob/master/src/test/java/org/scribe/examples/GoogleExample.java
 */
public class AuthGoogle extends HttpServlet
{
    private static final String NETWORK_NAME = "Google";
    private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
    private static final String SCOPE = "https://www.google.com/m8/feeds";

    private static final long serialVersionUID = -1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String origURL = request.getRequestURL().toString(); //this is http://host:port/authgoogle
        String servletPath = request.getServletPath(); //this is /authgoogle

        String callbackURL = origURL.substring(0, origURL.indexOf(servletPath));
        
        OAuthService service = new ServiceBuilder()
        .provider(GoogleApi.class)
        .apiKey("anonymous")
        .apiSecret("anonymous")
        .callback(callbackURL)
        .scope(SCOPE)
        .build();

        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
        System.out.println();

        // Obtain the Request Token
        System.out.println("Fetching the Request Token...");
        Token requestToken = service.getRequestToken();
        
        System.out.println("Got the Request Token!");
        System.out.println("(if your curious it looks like this: " + requestToken + " )");
        System.out.println();

        System.out.println("Authorizing Scribe by redirecting with callback to:");
        System.out.println(AUTHORIZE_URL + requestToken.getToken());
        
        /*
         *  Use the token part of the request token as the unique ID
         *  because it is sent back as URL parameters when doing callback
         */
        TokenManager.putService(requestToken.getToken(), service);
        TokenManager.putRequestToken(requestToken.getToken(), requestToken);

//        Cookie cookie = new Cookie("requestToken", requestToken.getToken());
//        response.addCookie(cookie);
        response.sendRedirect(AUTHORIZE_URL + requestToken.getToken());
        
    }

}
