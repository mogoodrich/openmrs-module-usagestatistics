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
package org.openmrs.module.usagestatistics.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.api.context.Context;
import org.openmrs.module.usagestatistics.UsageStatisticsService;
import org.openmrs.module.usagestatistics.util.StatsUtils;

/**
 * Controller for the summary page
 */
public class SummaryController extends ExportableStatsQueryController {

	/**
	 * @see ExportableStatsQueryController#augmentModel(Map, HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void augmentModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date monthAgo = StatsUtils.addDaysToDate(null, -30);
		
		UsageStatisticsService svc = Context.getService(UsageStatisticsService.class);
		
		model.put("onlineUsers", svc.getOnlineUserCount());
		model.put("recordsOpen", svc.getOpenRecordsCount());
		
		List<Object[]> locationStats = svc.getMostActiveLocations(monthAgo, 5);	
		List<Object[]> userStats = svc.getMostActiveUsers(monthAgo, 5);	
		
		model.put("locationStats", locationStats);
		model.put("userStats", userStats);
	}
}
