package one.empty3.feature;

import org.json.*;


public class ParseJSon {
   public List<? extends ProcessFile> parse(String jsonString) {
      List<? extends ProcessFile> o = new ArrayList<>();
       //assign your JSON String here
       JSONObject obj = new JSONObject(jsonString);
       String classname = "";
       JSONArray arr = obj.getJSONArray("filters");
       for (int i = 0; i < arr.length(); i++) {
           String filterName = arr.getJSONObject(i).getString("classname");
          
           Class c = Class.forName(classname);
           ProcessFile pf = c.newInstance(c);
          
           String properties = arr.getJSONObject(i).getString("properties");
           JSONObject propertiesA = new JSONObject(propertiesA);
           Set<String> keys = propertiesA.keySet();
           for (String key : keys) {
               String value = propertiesA.getString(key);
               if(key.equals("classname"))
                   classname = value;
               else if(value.split(":").length==2) {
                  String[] array  = value.split(":");
                  String vType  = array[0];
                  String vValue = array[1];
                  
                  Pojo.setP(pf, key, vValue, classname);
                  
        
               }
           }
       }
   }
}
