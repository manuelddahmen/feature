public class JsonProps {
    public void defProcess (String file) {
    

String jsonString = file.getContents(); //assign your JSON String here
JSONObject obj = new JSONObject(jsonString);
if(String in = obj.getJSONObject("in")) {
      JSONObject filters = obj.getJSONArray("filter");
for (int i = 0; i < filter.length(); i++)
{
    String classname = arr.getJSONObject(i).getString("class");
    for (int i=0;i<filter.get(i); i++);
         {
for(int j = 0; i<filter.get(i).names().length(); j++){
    System.out.println(i+" "+j "key = " + filters.get(i).names().getString(j) + " value = " + filter.get(jobject.names().getString(i)));
}
         
       }
      


}
