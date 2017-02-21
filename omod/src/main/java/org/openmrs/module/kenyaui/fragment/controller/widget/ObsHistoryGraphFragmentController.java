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

package org.openmrs.module.kenyaui.fragment.controller.widget;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for the obsGraphByDate fragment
 */
public class ObsHistoryGraphFragmentController {

	public void controller(
			@FragmentParam("patient") Patient patient,
			@FragmentParam("concepts") List<Concept> concepts,
			@RequestParam(required = false, value = "startDate") String startDate,
			@RequestParam(required = false, value = "endDate") String endDate,
			FragmentModel model, @SpringBean KenyaUiUtils kenyaUi) {
		if (concepts.size() < 1) {
			throw new IllegalArgumentException("Concept list must be non-empty");
		}

		if (endDate != "" && startDate != "") {
			Date sd = null, ed = null;
			DateFormat formatter;
			formatter = new SimpleDateFormat("dd-MMM-yyyy");
			try {
				sd = (Date) formatter.parse(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ed = (Date) formatter.parse(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Date startOfDay = OpenmrsUtil.firstSecondOfDay(sd);
			Date endOfDay = OpenmrsUtil.getLastMomentOfDay(ed);
			model.addAttribute("data",
					getObsAsSeries(patient, concepts, startOfDay, endOfDay));
		} else {
			model.addAttribute("data", getObsAsSeries(patient, concepts));
		}

		model.addAttribute("concepts", concepts);

	}

	/**
	 * Loads the obs for each of the specified concepts for the given person
	 * 
	 * @param person
	 *            the person
	 * @param concepts
	 *            the concepts
	 * @return the map of concepts to lists of obs
	 */
	private Map<Concept, List<Obs>> getObsAsSeries(Person person,
			List<Concept> concepts) {
		Map<Concept, List<Obs>> series = new HashMap<Concept, List<Obs>>();

		for (Concept concept : concepts) {
			List<Obs> obss = Context.getObsService()
					.getObservationsByPersonAndConcept(person, concept);
			series.put(concept, obss);

		}
		return series;
	}

	private Map<Concept, List<Obs>> getObsAsSeries(Person person,
			List<Concept> concepts, Date startOfDay, Date endOfDay) {
		Map<Concept, List<Obs>> series = new HashMap<Concept, List<Obs>>();
		for (Concept concept : concepts) {
			List<Obs> obss = Context.getObsService()
					.getObservationsByPersonAndConcept(person, concept);
			List<Obs> obssByDate = new LinkedList<Obs>() ;
			for (Obs obs : obss) {
				if (obs.getEncounter().getVisit().getStartDatetime()
						.after(startOfDay)
						&& obs.getEncounter().getVisit().getStartDatetime()
								.before(endOfDay)) {
					obssByDate.add(obs);
				}
			}
			series.put(concept, obssByDate);
			
		}
		return series;
	}
}