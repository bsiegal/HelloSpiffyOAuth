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
package org.spiffyui.hellospiffyoauth.client;

import java.util.ArrayList;
import java.util.List;

import org.spiffyui.client.JSONUtil;
import org.spiffyui.client.MessageUtil;
import org.spiffyui.client.rest.RESTCallback;
import org.spiffyui.client.rest.RESTException;
import org.spiffyui.client.rest.RESTObjectCallBack;
import org.spiffyui.client.rest.RESTility;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

/**
 * This is the REST bean.  It handles making the REST request
 * as well as converting the response from JSON to a Java bean. 
 */
public class Contacts
{    
    private List<Contact> m_contacts;
    
    public Contacts(List<Contact> contacts)
    {
        m_contacts = contacts; 
    }
    
    /**
     * @return Returns the m_contacts.
     */
    public List<Contact> getContacts()
    {
        return m_contacts;
    }

    /**
     * Static method to load the contacts
     * and return them in the callback
     * @param oauthVerifier - the oauth verifier string
     * @param oauthToken - the oauth request token
     * @param callback - the RESTObjectCallBack to handle the response
     */
    public static void loadContacts(final String oauthVerifier, final String oauthToken, final RESTObjectCallBack<Contacts> callback)
    {
       /*
        * Use the URL parameters from the google callback as parameters to the REST service.
        */
       RESTility.callREST("./contacts?" + Index.OAUTH_VERIFIER + "=" + oauthVerifier + "&" +
           Index.OAUTH_TOKEN + "=" + oauthToken, new RESTCallback() {
          
          @Override
          public void onSuccess(JSONValue val)
          {
              JSONObject resp = val.isObject();
              JSONObject feed = JSONUtil.getJSONObject(resp, "feed");
              
              List<Contact> contactList = new ArrayList<Contact>();
              
              JSONArray entries = JSONUtil.getJSONArray(feed, "entry");
              for (int i = 0, len = entries.size(); i < len; i++) {
                  Contact contact = new Contact();
                  JSONObject entry = entries.get(i).isObject();
                  JSONObject title = JSONUtil.getJSONObject(entry, "title");
                  String entryTitle = JSONUtil.getStringValue(title, "$t");
              
                  contact.setTitle(entryTitle);
                  List<Email> emailList = new ArrayList<Email>();
                  
                  JSONArray emails = JSONUtil.getJSONArray(entry, "gd$email");
                  if (emails != null) {
                      for (int j = 0, size = emails.size(); j < size; j++) {
                          Email email = new Email();
                              
                          JSONObject emailJson = emails.get(j).isObject();
                          String address = JSONUtil.getStringValue(emailJson, "address");
                          
                          email.setPrimary(JSONUtil.getBooleanValue(emailJson, "primary"));
                          email.setAddress(address);
                          emailList.add(email);
                      }
                  }
                  contact.setEmails(emailList);
                  contactList.add(contact);
              }
              callback.success(new Contacts(contactList));
          }
          
          @Override
          public void onError(int statusCode, String errorResponse)
          {
              callback.error(errorResponse);
          }
          
          @Override
          public void onError(RESTException e)
          {
              callback.error(e);
          }
      });
    }

}
