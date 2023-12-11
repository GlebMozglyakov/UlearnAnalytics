package vkAPI;


import models.Student;
import org.json.JSONArray;
import org.json.JSONObject;
import config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;


public class VkParser {
    private final String accessToken = Config.accessToken;
    private String vkGroupId = "basicprogrammingrtf2023";

    public List<String> parseVkStudents() throws URISyntaxException, IOException {
        String requestURL = String.format("https://api.vk.com/method/groups.getMembers?group_id=%s&fields=bdate,city&access_token=%s&v=5.131", vkGroupId, accessToken);
        URL url = new URI(requestURL).toURL();
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";

        while (scanner.hasNext()) {
            result += scanner.nextLine();
        }

        JSONObject responseJson = new JSONObject(result);
        JSONArray users = responseJson.getJSONObject("response").getJSONArray("items");
        List<String> editedUsers = new ArrayList<>();

        for (int i = 0;i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String last_name = user.getString("last_name");
            String first_name = user.getString("first_name");
            String bdate = "-";
            String city = "-";

            if (user.has("bdate")) {
                bdate = user.getString("bdate");
            }
            if (user.has("city")) {
                JSONObject cityObj = user.getJSONObject("city");
                city = cityObj.getString("title");
                if (city.isEmpty()) {
                    city = "-";
                }
            }

            String editedUser = String.format("%s %s %s %s", last_name, first_name, bdate, city);
            editedUsers.add(editedUser);
        }

        return editedUsers;
    }

    public void addVkDataToStudents(List<Student> students) throws URISyntaxException, IOException {
        List<String> vkStudents = parseVkStudents();

        for (String vkStudent: vkStudents) {
            String[] vkData = vkStudent.split(" ");
            String last_name = vkData[0];
            String first_name = vkData[1];
            String bdate = vkData[2];
            String city = vkData[3];
            for (Student student: students) {
                if (student.getName().equals(first_name) && student.getSurname().equals(last_name)) {
                    if (!bdate.equals("-")) {
                        student.setBdate(bdate);
                    }
                    if (!city.equals("-")) {
                        student.setCity(city);
                    }
                    break;
                }
            }
        }
    }
}
