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

import org.spiffyui.client.MainFooter;
import org.spiffyui.client.MainHeader;
import org.spiffyui.client.MessageUtil;
import org.spiffyui.client.rest.RESTException;
import org.spiffyui.client.rest.RESTObjectCallBack;
import org.spiffyui.client.widgets.LongMessage;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * This class is the main entry point for our GWT module.
 */
 public class Index implements EntryPoint
 {
     public static final String OAUTH_VERIFIER = "oauth_verifier";
     public static final String OAUTH_TOKEN = "oauth_token";
     
     private static final SpiffyUiHtml STRINGS = (SpiffyUiHtml) GWT.create(SpiffyUiHtml.class);

     private static Index g_index;
     private LongMessage m_longMessage = new LongMessage("longMsgPanel");
     private Button m_accessButton = new Button("1. Request access from Google");
     private Button m_contactsButton = new Button("2. Get contacts");
     
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
         HTMLPanel panel = new HTMLPanel(STRINGS.MainPanel_html());

         RootPanel.get("mainContent").add(panel);

         /*
            These dynamic controls add interactivity to our page.
          */
         panel.add(m_longMessage, "longMsg");
         
         panel.add(m_accessButton, "AuthButton");
         m_accessButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event)
            {
                requestAccess();
            }
        });

        panel.add(m_contactsButton, "GetButton");
        m_contactsButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event)
            {
                getContacts();
            }
        });
        
        if (!Index.userLoggedIn()) {
            header.setWelcomeString("");            
            m_accessButton.setFocus(true);
        } else {
            header.setWelcomeString("You are logged in!");
            m_contactsButton.setFocus(true);
        }

     }

     /**
      * Send a request to access google contacts through the google servlet.
      */
     private void requestAccess()
     {
         Window.Location.replace("/authgoogle");
     }

     private void getContacts()
     {
         String oauthVerifier = Window.Location.getParameter(OAUTH_VERIFIER);
         String oauthToken = Window.Location.getParameter(OAUTH_TOKEN);
         
         Contacts.loadContacts(oauthVerifier, oauthToken, new RESTObjectCallBack<Contacts>() {
            
            @Override
            public void success(Contacts o)
            {
                showContacts(o);
            }
            
            @Override
            public void error(RESTException e)
            {
                MessageUtil.showError(e.getReason());
            }
            
            @Override
            public void error(String message)
            {
                MessageUtil.showError(message);
            }
        });
     }
     
    private void showContacts(Contacts contacts)
    {
        RootPanel rootPanel = RootPanel.get("Contacts");
        for (Contact c : contacts.getContacts()) {
            StringBuffer html = new StringBuffer();
            html.append("<div class=\"contactName\">").append(c.getTitle()).append("</div>");
            for (Email e : c.getEmails()) {
                if (e.isPrimary()) {
                    html.append("<div class=\"email primary\">").append(e.getAddress()).append("</div>");
                } else {
                    html.append("<div class=\"email\">").append(e.getAddress()).append("</div>");
                }
            }
            
            rootPanel.add(new ContactLi(html.toString()));
        }
        
    }


    /**
      * returns whether the  user is logged in or not
      * @return true if the user is logged in (browser cookie is there)
      */
     private static boolean userLoggedIn()
     {         
         String oauthVerifier = Window.Location.getParameter(OAUTH_VERIFIER);
         String oauthToken = Window.Location.getParameter(OAUTH_TOKEN);
                  
         return oauthVerifier != null && !oauthVerifier.trim().equals("") && oauthToken != null && !oauthToken.trim().equals("") ;
     }

     /**
      * A Contact list item to add to the "contacts" UL tag within MainPanel.html
      */
     private class ContactLi extends HTMLPanel
     {

        public ContactLi(String html)
        {
            super("li", html);
        }
         
     }
 }
