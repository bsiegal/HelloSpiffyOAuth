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


/**
 * Bean representing an email entry
 */
public class Email
{
    private boolean m_primary;
    private String m_address;
    /**
     * @return Returns the primary.
     */
    public boolean isPrimary()
    {
        return m_primary;
    }
    /**
     * @param primary The primary to set.
     */
    public void setPrimary(boolean primary)
    {
        m_primary = primary;
    }
    /**
     * @return Returns the address.
     */
    public String getAddress()
    {
        return m_address;
    }
    /**
     * @param address The address to set.
     */
    public void setAddress(String address)
    {
        m_address = address;
    }
    
}