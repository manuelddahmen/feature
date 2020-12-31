package one.empty3.feature;

import org.json.*;


public class ParseJSon {
   public void parse(String jsonString) {
       //assign your JSON String here
       JSONObject obj = new JSONObject(jsonString);
       String classname = "";
       JSONArray arr = obj.getJSONArray("filters");
       for (int i = 0; i < arr.length(); i++) {
           String filterName = arr.getJSONObject(i).getString("classname");
           String properties = arr.getJSONObject(i).getString("properties");
           JSONObject propertiesA = new JSONObject(propertiesA);
           Set<String> keys = propertiesA.keySet();
           for (String key : keys) {
               String value = propertiesA.getString(key);
               if(key.equals("classname"))
                   classname = value;
               String properties = propertiesO.getS(j);
       }
   }
}
