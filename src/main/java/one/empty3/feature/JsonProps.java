public class JsonProps {
    public void defProcess (String file) {
    

String jsonString = file.getContents(); //assign your JSON String here
JSONObject obj = new JSONObject(jsonString);
if(String in = obj.getJSONObject("in")) {
       if(in.getString("directory")!=null){
         File dir = new File(in.getString("directory")) ;
       }
       if(in.getString("file" )){}
        if(in.getString("json" )){}
      if(in.getString("ftpjson" )){}
     if(in.getString("ftpdirectory" )){}
} 
   
    String pageName = obj.getJSONObject("pageInfo").getString("pageName");

JSONArray filters = obj.getJSONArray("filters");
for (int i = 0; i < arr.length(); i++)
{
    String post_id = arr.getJSONObject(i).getString("class");
    
}
}
