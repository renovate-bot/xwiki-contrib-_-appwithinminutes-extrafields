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

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;

import com.xpn.xwiki.internal.objects.classes.PropertyClassProvider;
import com.xpn.xwiki.internal.objects.meta.PropertyMetaClassInterface;
import com.xpn.xwiki.objects.BaseElement;
import com.xpn.xwiki.objects.classes.PropertyClassInterface;
import com.xpn.xwiki.objects.classes.StringClass;
import com.xpn.xwiki.objects.meta.PropertyMetaClass;

/**
 * Provider for {@link ExternalImageClass}.
 * 
 * @version $Id$
 * @since 1.0
 */
@Component
// Note that the component hint matches the name of the property class without the "Class" suffix. The reason is that
// the component hint must match the value returned by the #getClassType() method of your property class, which by
// default strips the "Class" suffix from the Java class name of your property class. If you want to use a different
// hint that doesn't follow this naming convention you need to override #getClassType().
@Named("ExternalImage")
@Singleton
public class ExternalImageClassProvider implements PropertyClassProvider
{
    @Override
    public PropertyClassInterface getInstance()
    {
        return new ExternalImageClass();
    }

    @Override
    public PropertyMetaClassInterface getDefinition()
    {
        PropertyMetaClass definition = new PropertyMetaClass();
        // This text will appear in the drop down list of property types to choose from in the class editor.
        definition.setPrettyName("External Image");
        ((BaseElement<?>) definition).setName(getClass().getAnnotation(Named.class).value());

        // Add a meta property that will allows us to specify a CSS class name for the image HTML element.
        // NOTE: We define meta properties using XClass property types. This means for instance that you can define meta
        // properties of External Image type or whatever XClass property type you create.
        StringClass styleName = new StringClass();
        styleName.setName("styleName");
        styleName.setPrettyName("Style Name");
        definition.safeput(styleName.getName(), styleName);

        // The alternative text is required for a valid image HTML element so we add a meta property for it.
        StringClass placeholder = new StringClass();
        placeholder.setName("placeholder");
        placeholder.setPrettyName("Alternative Text");
        definition.safeput(placeholder.getName(), placeholder);

        // Add more meta properties here.

        return definition;
    }
}
