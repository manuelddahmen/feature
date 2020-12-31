package one.empty3;feature;

import org.json.*;


public class ParseJSon {
public void parse(String jsonString) {
 //assign your JSON String here
JSONObject obj = new JSONObject(jsonString);

JSONArray arr = obj.getJSONArray("filters");
for (int i = 0; i < arr.length(); i++)
{
    String filterName = arr.getJSONObject(i).getString("classname");
    String properties = arr.gerJSONObject(i).getString("property");
}
}
