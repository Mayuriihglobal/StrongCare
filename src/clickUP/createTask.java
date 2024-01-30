package clickUP;

import org.apache.http.util.EntityUtils;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.json.JSONArray;

public class createTask {
	public void createClickUpTask(String taskName, String taskDescription, String listId, String status) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();

		// Check if the task with the same name already exists
		String existingTaskId = getTaskId(taskName, listId);
		if (!existingTaskId.equals("TaskNotFound")) {

			System.out.println("Task with the same name already exists. Skipping task creation.");

			return;
		}

		HttpPost request = new HttpPost("https://api.clickup.com/api/v2/list/" + listId + "/task?custom_task_ids=true");
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Authorization", "pk_88800087_F2NBC795ZOSXU5G4FEH8MD1X0VM6A9SE");

		JSONObject payload = new JSONObject();
		payload.put("name", taskName);
		payload.put("description", taskDescription);
		payload.put("status", status);
		payload.put("priority", 1);

		JSONArray assigneesArray = new JSONArray();
		assigneesArray.put(88800087);
		payload.put("assignees", assigneesArray);

		payload.put("due_date", JSONObject.NULL);
		payload.put("due_date_time", false);
		payload.put("time_estimate", JSONObject.NULL);
		payload.put("start_date", JSONObject.NULL);
		payload.put("start_date_time", false);
		payload.put("notify_all", true);
		payload.put("parent", JSONObject.NULL);
		payload.put("links_to", JSONObject.NULL);
		payload.put("check_required_custom_fields", true);

		request.setEntity(new StringEntity(payload.toString()));

		try {
			String response = EntityUtils.toString(httpClient.execute(request).getEntity());
			System.out.println("ClickUp Task created with response: " + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTask(String subTaskId, String mainTaskID) throws java.io.IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();

		String taskId = subTaskId;
		HttpPut request = new HttpPut("https://api.clickup.com/api/v2/task/" + taskId);
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Authorization", "pk_88800087_F2NBC795ZOSXU5G4FEH8MD1X0VM6A9SE");
		JSONObject payload = new JSONObject();
		payload.put("priority", 1);
		payload.put("parent", mainTaskID);
		request.setEntity(new StringEntity(payload.toString()));
		String response = EntityUtils.toString(httpClient.execute(request).getEntity());

		System.out.println(response);

	}

	protected String getTaskId(String taskName, String listId) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();

		// String listId = "901600971152";
		HttpGet request = new HttpGet(
				"https://api.clickup.com/api/v2/list/" + listId + "/task?Content-Type=application/json");
		request.setHeader("Authorization", "pk_88800087_F2NBC795ZOSXU5G4FEH8MD1X0VM6A9SE");
		String response = EntityUtils.toString(httpClient.execute(request).getEntity());

		// Extract and return task ID from the response
		// Modify this part based on your response structure
		return extractTaskId(response, taskName);
	}

	private String extractTaskId(String response, String taskName) {
		JSONObject jsonResponse = new JSONObject(response);

		if (jsonResponse.has("tasks")) {
			JSONArray tasks = jsonResponse.getJSONArray("tasks");

			for (int i = 0; i < tasks.length(); i++) {
				JSONObject task = tasks.getJSONObject(i);
				if (task.has("name") && task.getString("name").equals(taskName)) {
					return task.getString("id");
				}
			}
		}
		return "TaskNotFound";
	}
}