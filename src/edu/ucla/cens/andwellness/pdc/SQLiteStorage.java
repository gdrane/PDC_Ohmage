package edu.ucla.cens.andwellness.pdc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.ucla.cens.andwellness.db.Campaign;
import edu.ucla.cens.andwellness.db.Response;
import edu.ucla.cens.andwellness.pdc.db.StreamDbHelper;
import edu.ucla.cens.andwellness.pdc.libpdc.OhmageApplication;
import edu.ucla.cens.andwellness.service.SurveyGeotagService;
import edu.ucla.cens.pdc.libpdc.datastructures.DataRecord;
import edu.ucla.cens.pdc.libpdc.exceptions.PDCDatabaseException;
import edu.ucla.cens.pdc.libpdc.stream.Storage;

public class SQLiteStorage extends Storage {

	public SQLiteStorage(OhmageApplication app, String data_stream_id) {
		super(app, data_stream_id);
		_app = app;
		_data_stream_id = data_stream_id;
	}

	@Override
	public String getLastEntry() throws PDCDatabaseException {
		StreamDbHelper streamDbHelper = 
				new StreamDbHelper(_app.getApplicationContext());
		Response response = 
				streamDbHelper.getLastSurveyResponse();
		Campaign campaign = streamDbHelper.getCampaign(
				response.campaignUrn);
		JSONObject responseJson = new JSONObject();
		if (response != null) {
			try {
				responseJson.put("header_c", campaign.NAME);
				responseJson.put("header_u", response.username);
				responseJson.put("header_ci", "android");
				responseJson.put("date", response.date);
				responseJson.put("time", response.time);
				responseJson.put("timezone", response.timezone);
				responseJson.put("location_status", response.locationStatus);
				if (!response.locationStatus
						.equals(SurveyGeotagService.LOCATION_UNAVAILABLE)) {
					JSONObject locationJson = new JSONObject();
					locationJson.put("latitude", response.locationLatitude);
					locationJson.put("longitude", response.locationLongitude);
					locationJson.put("provider", response.locationProvider);
					locationJson.put("accuracy", response.locationAccuracy);
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String locationTimestamp = dateFormat.format(new Date(
							response.locationTime));
					locationJson.put("timestamp", locationTimestamp);
					responseJson.put("location", locationJson);
				}
				responseJson.put("survey_id", response.surveyId);
				responseJson.put("survey_launch_context", new JSONObject(
						response.surveyLaunchContext));
				responseJson.put("responses", new JSONArray(response.response));
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		} 
		return responseJson.toString();
	}

	@Override
	public List<String> getRangeIds() throws PDCDatabaseException {
		return getRangeIds(null, null);
	}

	@Override
	public List<String> getRangeIds(String start) throws PDCDatabaseException {
		return getRangeIds(start, null);
	}

	@Override
	public List<String> getRangeIds(String start, String end)
			throws PDCDatabaseException {
		StreamDbHelper streamDbHelper = 
				new StreamDbHelper(_app.getApplicationContext());
		List<Response> responses;
		if( start != null && end !=  null) {
			responses =
					streamDbHelper.getRangeSurveyResponse(
							Integer.parseInt(start), Integer.parseInt(end));
		} else if(start != null) {
			responses =
					streamDbHelper.getRangeSurveyResponse(
							Integer.parseInt(start), -1);
		} else {
			responses =
					streamDbHelper.getRangeSurveyResponse(-1, -1);
		}
		Campaign campaign = streamDbHelper.getCampaign(Response.CAMPAIGN_URN);
		ArrayList<String> responseStrArr = new ArrayList<String>();
		for (Response response : responses) {
			try {
				JSONObject responseJson = new JSONObject();
				responseJson.put("header_c", campaign.NAME);
				responseJson.put("header_u", response.username);
				responseJson.put("header_ci", "android");
				responseJson.put("date", response.date);
				responseJson.put("time", response.time);
				responseJson.put("timezone", response.timezone);
				responseJson.put("location_status", response.locationStatus);
				if (!response.locationStatus
						.equals(SurveyGeotagService.LOCATION_UNAVAILABLE)) {
					JSONObject locationJson = new JSONObject();
					locationJson.put("latitude", response.locationLatitude);
					locationJson.put("longitude", response.locationLongitude);
					locationJson.put("provider", response.locationProvider);
					locationJson.put("accuracy", response.locationAccuracy);
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String locationTimestamp = dateFormat.format(new Date(
							response.locationTime));
					locationJson.put("timestamp", locationTimestamp);
					responseJson.put("location", locationJson);
				}
				responseJson.put("survey_id", response.surveyId);
				responseJson.put("survey_launch_context", new JSONObject(
						response.surveyLaunchContext));
				responseJson.put("responses", new JSONArray(response.response));
				responseStrArr.add(responseJson.toString());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		} 
		if (responses.isEmpty()) {
			throw new PDCDatabaseException("Could not find data record");
		}
		return responseStrArr;
	}

	@Override
	/* 
	 * parameters:
	 * id : Id of the record which differs from stream to stream
	 */
	public DataRecord getRecord(String id) throws PDCDatabaseException { 
		StreamDbHelper streamDbHelper = 
				new StreamDbHelper(_app.getApplicationContext());
		Response response = 
				streamDbHelper.getSurveyResponse(Integer.parseInt(id));
		Campaign campaign = streamDbHelper.getCampaign(
				response.campaignUrn);
		HashMap<String, String> responseJson = new HashMap<String, String>();
		if (response != null) {
			try {
				responseJson.put("header_c", campaign.NAME);
				responseJson.put("header_u", response.username);
				responseJson.put("header_ci", "android");
				responseJson.put("date", response.date);
				responseJson.put("time", response.time + "");
				responseJson.put("timezone", response.timezone);
				responseJson.put("location_status", response.locationStatus);
				if (!response.locationStatus
						.equals(SurveyGeotagService.LOCATION_UNAVAILABLE)) {
					JSONObject locationJson = new JSONObject();
					locationJson.put("latitude", response.locationLatitude);
					locationJson.put("longitude", response.locationLongitude);
					locationJson.put("provider", response.locationProvider);
					locationJson.put("accuracy", response.locationAccuracy);
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String locationTimestamp = dateFormat.format(new Date(
							response.locationTime));
					locationJson.put("timestamp", locationTimestamp);
					responseJson.put("location", locationJson.toString());
				}
				responseJson.put("survey_id", response.surveyId);
				// responseJson.put("survey_launch_context", new JSONObject(
				//		response.surveyLaunchContext));
				// responseJson.put("responses", new JSONArray(response.response));
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		} 
		DataRecord dr = new DataRecord();
		dr.putAll(responseJson);
		return dr;
	}

	@Override
	public boolean insertRecord(DataRecord record) throws PDCDatabaseException {
		return false;
	}
	
	// Application Object of the associated app
	private OhmageApplication _app;
	
	// Id of the associated DataStream
	private String _data_stream_id;
	
}
