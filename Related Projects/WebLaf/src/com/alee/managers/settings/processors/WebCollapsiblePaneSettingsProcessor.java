/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.managers.settings.processors;

import com.alee.extended.panel.CollapsiblePaneAdapter;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.managers.settings.SettingsManager;
import com.alee.managers.settings.SettingsProcessor;
import com.alee.managers.settings.SettingsProcessorData;

/**
 * Custom SettingsProcessor for WebCollapsiblePane component.
 *
 * @author Mikle Garin
 */

public class WebCollapsiblePaneSettingsProcessor extends SettingsProcessor<WebCollapsiblePane, Boolean>
{
    /**
     * Expand and collapse events listener.
     */
    private CollapsiblePaneAdapter collapsiblePaneAdapter;

    /**
     * Constructs SettingsProcessor using the specified SettingsProcessorData.
     *
     * @param data SettingsProcessorData
     */
    public WebCollapsiblePaneSettingsProcessor ( SettingsProcessorData data )
    {
        super ( data );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getDefaultValue ()
    {
        Boolean defaultValue = super.getDefaultValue ();
        if ( defaultValue == null )
        {
            defaultValue = true;
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doInit ( WebCollapsiblePane collapsiblePane )
    {
        collapsiblePaneAdapter = new CollapsiblePaneAdapter ()
        {
            @Override
            public void expanding ( WebCollapsiblePane pane )
            {
                if ( SettingsManager.isSaveOnChange () )
                {
                    save ();
                }
            }

            @Override
            public void collapsing ( WebCollapsiblePane pane )
            {
                if ( SettingsManager.isSaveOnChange () )
                {
                    save ();
                }
            }
        };
        collapsiblePane.addCollapsiblePaneListener ( collapsiblePaneAdapter );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDestroy ( WebCollapsiblePane collapsiblePane )
    {
        collapsiblePane.removeCollapsiblePaneListener ( collapsiblePaneAdapter );
        collapsiblePaneAdapter = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoad ( WebCollapsiblePane collapsiblePane )
    {
        collapsiblePane.setExpanded ( loadValue () );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSave ( WebCollapsiblePane collapsiblePane )
    {
        saveValue ( collapsiblePane.isExpanded () );
    }
}