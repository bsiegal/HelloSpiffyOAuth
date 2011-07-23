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

import java.util.List;

/**
 * Bean to represent a single contact.
 * Only a tiny fraction of the actual possible values are included as properties for this sample
 */
public class Contact
{
    private List<Email> m_emails;
    private String m_title;
    /**
     * @return Returns the emails.
     */
    public List<Email> getEmails()
    {
        return m_emails;
    }
    /**
     * @param emails The emails to set.
     */
    public void setEmails(List<Email> emails)
    {
        m_emails = emails;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return m_title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        m_title = title;
    }
    
}