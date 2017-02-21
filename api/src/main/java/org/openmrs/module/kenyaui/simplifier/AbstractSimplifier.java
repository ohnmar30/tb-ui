/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaui.simplifier;

import org.openmrs.ui.framework.SimpleObject;
import org.springframework.core.convert.converter.Converter;

/**
 * Abstract converter to a simple object
 */
public abstract class AbstractSimplifier<T> implements Converter<T, SimpleObject> {

	/**
	 * @see org.springframework.core.convert.converter.Converter#convert(Object)
	 */
	@Override
	public SimpleObject convert(T obj) {
		return simplify(obj);
	}

	/**
	 * Simplify the given object
	 * @param obj the object
	 * @return the simple object
	 */
	protected abstract SimpleObject simplify(T obj);
}