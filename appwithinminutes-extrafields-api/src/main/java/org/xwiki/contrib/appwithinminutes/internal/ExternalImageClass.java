/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.appwithinminutes.internal;

import java.util.Objects;

import org.xwiki.model.reference.EntityReference;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.doc.merge.MergeConfiguration;
import com.xpn.xwiki.doc.merge.MergeResult;
import com.xpn.xwiki.objects.BaseProperty;
import com.xpn.xwiki.objects.StringProperty;
import com.xpn.xwiki.objects.classes.PropertyClass;

/**
 * Property type which can be used to store URLs to external images.
 * 
 * @version $Id$
 * @since 1.0
 */
public class ExternalImageClass extends PropertyClass
{
    /**
     * Serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ExternalImageClass()
    {
        // Specify the default name and pretty name of this XClass property. They can be overwritten from the class
        // editor when adding a property of this type to an XClass.
        super("externalImage", "External Image", null);
    }

    @Override
    public BaseProperty<?> fromString(String value)
    {
        BaseProperty property = newProperty();
        // The stored value can be different than the value set by the user. You can do the needed transformations here.
        // In our case the value is an image URL so we keep it as it is. The reverse transformation, from the stored
        // value to the user friendly value, can be done in the property displayer.
        property.setValue(value);
        return property;
    }

    @Override
    public BaseProperty<?> newProperty()
    {
        // The value of this XClass property is stored as a String. You have to use raw types here like StringProperty
        // because they are mapped to the database. Adding a new raw type implies modifying the Hibernate mapping and is
        // not the subject of this tutorial.
        BaseProperty property = new StringProperty();
        property.setName(getName());
        return property;
    }

    @Override
    public <T extends EntityReference> void mergeProperty(BaseProperty<T> currentProperty,
        BaseProperty<T> previousProperty, BaseProperty<T> newProperty, MergeConfiguration configuration,
        XWikiContext context, MergeResult mergeResult)
    {
        if (!Objects.equals(previousProperty, newProperty)) {
            if (Objects.equals(previousProperty, currentProperty)) {
                currentProperty.setValue(newProperty.getValue());
            } else {
                // Found conflict
                mergeResult.getLog().error("Collision found on property [{}] current has been modified", getName());
            }
        }
    }
}
