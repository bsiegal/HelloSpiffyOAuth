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

import java.util.HashMap;
import java.util.Map;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * This is a simple manager for keeping track of services and tokens
 */
public class TokenManager
{
    private static Map<String, OAuthService> SERVICE_MANAGER = new HashMap<String, OAuthService>();
    private static Map<String, Token> REQUEST_TOKEN_MANAGER = new HashMap<String, Token>();
    private static Map<String, Token> ACCESS_TOKEN_MANAGER = new HashMap<String, Token>();
    
    private TokenManager()
    {
        //do not instantiate
    }
    
    /**
     * Add a service
     * @param id - a unique ID
     * @param service - OAuthService
     */
    public static void putService(String id, OAuthService service)
    {
        SERVICE_MANAGER.put(id, service);
    }
 
    /**
     * Add a request token
     * @param id - a unique ID
     * @param requestToken - Token
     */
    public static void putRequestToken(String id, Token requestToken)
    {
        REQUEST_TOKEN_MANAGER.put(id, requestToken);
    }
    
    /**
     * Add the access token
     * @param id - a unique ID
     * @param accessToken - Token
     */
    public static void putAccessToken(String id, Token accessToken)
    {
        ACCESS_TOKEN_MANAGER.put(id, accessToken);
    }
    
    /**
     * Get the service
     * @param id - the unique ID
     * @return the OAuthService
     */
    public static OAuthService getService(String id)
    {
        return SERVICE_MANAGER.get(id);
    }
 
    /**
     * Get the request token
     * @param id - the unique ID
     * @return the request Token
     */
    public static Token getRequestToken(String id)
    {
        return REQUEST_TOKEN_MANAGER.get(id);
    }
    
    /**
     * Get the access token
     * @param id - the unique ID
     * @return the access Token
     */
    public static Token getAccessToken(String id)
    {
        return ACCESS_TOKEN_MANAGER.get(id);
    }
    
    
}
