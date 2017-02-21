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

import org.openmrs.OpenmrsObject;
import org.openmrs.ui.framework.SimpleObject;

/**
 * Abstract simplifier for OpenmrsObject subclasses
 */
public abstract class AbstractObjectSimplifier<T extends OpenmrsObject> extends AbstractSimplifier<T> {

	/**
	 * @see AbstractSimplifier#simplify(Object)
	 */
	protected SimpleObject simplify(T obj) {
		SimpleObject ret = new SimpleObject();
		ret.put("id", obj.getId());
		ret.put("uuid", obj.getUuid());
		return ret;
	}
}