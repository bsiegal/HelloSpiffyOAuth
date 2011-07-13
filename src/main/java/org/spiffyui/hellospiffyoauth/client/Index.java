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

import org.spiffyui.client.JSONUtil;
import org.spiffyui.client.JSUtil;
import org.spiffyui.client.MainFooter;
import org.spiffyui.client.MainHeader;
import org.spiffyui.client.MessageUtil;
import org.spiffyui.client.rest.RESTCallback;
import org.spiffyui.client.rest.RESTException;
import org.spiffyui.client.rest.RESTility;
import org.spiffyui.client.widgets.LongMessage;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * This class is the main entry point for our GWT module.
 */
 public class Index implements EntryPoint
 {
     private static final SpiffyUiHtml STRINGS = (SpiffyUiHtml) GWT.create(SpiffyUiHtml.class);

     private static Index g_index;
     private LongMessage m_longMessage = new LongMessage("longMsgPanel");
     private Button m_accessButton = new Button("Request access from Google");
     private Button m_contactsButton = new Button("Get contacts");
     
     /**
      * The Index page constructor
      */
     public Index()
     {
         g_index = this;
     }


     @Override
     public void onModuleLoad()
     {
         /*
            This is where we load our module and create our dynamic controls.  The MainHeader
            displays our title bar at the top of our page.
          */
         final MainHeader header = new MainHeader();
         header.setHeaderTitle("Hello Spiffy OAuth!");
         if (!Index.userLoggedIn()) {
             header.setWelcomeString("");            
         } else {
             header.setWelcomeString("You are logged in!");
         }
         /*
            The main footer shows our message at the bottom of the page.
          */
         MainFooter footer = new MainFooter();
         footer.setFooterString("HelloSpiffyOAuth was built with the <a href=\"http://www.spiffyui.org\">Spiffy UI Framework</a>");

         /*
            This HTMLPanel holds most of our content.
            MainPanel_html was built in the HTMLProps task from MainPanel.html, which allows you to use large passages of html
            without having to string escape them.
          */
         HTMLPanel panel = new HTMLPanel(STRINGS.MainPanel_html())
         {
             @Override
             public void onLoad()
             {
                 super.onLoad();
                 m_accessButton.setFocus(true);
             }
         };

         RootPanel.get("mainContent").add(panel);

         /*
            These dynamic controls add interactivity to our page.
          */
         panel.add(m_longMessage, "longMsg");
         
         panel.add(m_accessButton, "submitButton");
         m_accessButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event)
            {
                requestAccess();
            }
        });

        panel.add(m_contactsButton, "submitButton");
        m_contactsButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event)
            {
                getContacts();
            }
        });
     }

     /**
      * Send a request to access google contacts through the google servlet.
      */
     private void requestAccess()
     {
         Window.Location.replace("./authgoogle");
     }

     private void getContacts()
     {
         /*
          * Use the URL parameters from the google callback as parameters to the REST service.
          */
         RESTility.callREST("./contacts?oauth_verifier=" + Window.Location.getParameter("oauth_verifier") +
             "&oauth_token=" + Window.Location.getParameter("oauth_token"), new RESTCallback() {
            
            @Override
            public void onSuccess(JSONValue val)
            {
                StringBuffer html = new StringBuffer();
                JSONObject resp = val.isObject();
                JSONObject feed = JSONUtil.getJSONObject(resp, "feed");
                String feedTitle = JSONUtil.getStringValue(feed, "title");

                html.append(feedTitle).append("<br/><br/>");
                
                JSONArray entries = JSONUtil.getJSONArray(feed, "entry");
                for (int i = 0, len = entries.size(); i < len; i++) {
                    JSONObject entry = entries.get(i).isObject();
                    JSONObject title = JSONUtil.getJSONObject(entry, "title");
                    String entryTitle = JSONUtil.getStringValue(title, "$t");
                
                    html.append(entryTitle).append("<br/");

                    JSONArray emails = JSONUtil.getJSONArray(entry, "gd$email");
                    if (emails != null) {
                        for (int j = 0, size = emails.size(); j < size; j++) {
                            JSONObject email = emails.get(j).isObject();
                            String address = JSONUtil.getStringValue(email, "address");
                    
                            html.append(address).append("<br/>");
                        }
                        html.append("<br/></br/>");
                    }
                }
                MessageUtil.showMessage(html.toString());
            }
            
            @Override
            public void onError(int statusCode, String errorResponse)
            {
                MessageUtil.showError(errorResponse);
            }
            
            @Override
            public void onError(RESTException e)
            {
                MessageUtil.showError(e.getReason());
            }
        });
     }
     /**
      * returns whether the  user is logged in or not
      * @return true if the user is logged in (browser cookie is there)
      */
     private static boolean userLoggedIn()
     {         
        //oauth_verifier=gNH2uiZIkSE4HEfCzHu_RA5E&oauth_token=4%2FCHiSIT-o93SjVaiRjQ83JtiprLTH
         String oauthVerifier = Window.Location.getParameter("oauth_verifier");
         String oauthToken = Window.Location.getParameter("oauth_token");
                  
         return oauthVerifier != null && !oauthVerifier.trim().equals("") && oauthToken != null && !oauthToken.trim().equals("") ;
     }

 }
