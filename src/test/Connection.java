package test;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Connection {

	public static void main(String[] args) {
		//String wsldUrl = "http://192.168.1.230:8080/usercenter/SystemRolePort?wsdl";
		//String wsldUrl = "http://192.168.1.230:8080/usercenter/UserCenterPort?wsdl";
		//String wsldUrl = "http://192.168.1.230/weboa/system/userreg.nsf/getUserMail?wsdl";
		String wsldUrl = "http://localhost:8088/Webs/services/HelloService?wsdl";
	      Service service = new Service();
	     // String returnValue = "";
	      Call call;
	      String et = "";
	      try {
			call = (Call) service.createCall();
	        call.setTargetEndpointAddress(new java.net.URL(wsldUrl));
 
	        //call.setOperationName(new QName("http://service.flysoft.usercenter/","getNewUserRole"));
	        //call.setOperationName(new QName("http://service.flysoft.usercenter/","say"));http://explore
	        call.setOperationName(new QName("http://explore","say"));
	        //call.setOperationName(new QName("http://192.168.1.230/weboa/system/userreg.nsf/getUserMail?openWebService","getMailUrl"));
	        call.addParameter("arg0", XMLType.XSD_STRING, ParameterMode.IN);
	       // call.addParameter("arg1", XMLType.XSD_STRING, ParameterMode.IN);
	        call.setReturnType(org.apache.axis.Constants.XSD_STRING); 
	        
	        // et = (String) call.invoke(new Object[] {"OA办公系统"});
	        et = (String) call.invoke(new Object[] {"hello service"});
	         System.out.println(et);
	         //JsonParse(et);
	        //System.out.println(et);
	      } catch (Exception e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	      }

	}
	
	public static void JsonParse(String str) {
		
		
	      JSONObject jsonObject = JSONObject.fromObject(str);
	      String name = jsonObject.getString("name");
	      String userId = jsonObject.getString("userId");
	      String role = jsonObject.getString("role");
	      System.out.println("姓名:" + name);
	      System.out.println("用户ID:" + userId);
	      System.out.println("角色Arr:" + role);
	      JSONArray jsonArr = jsonObject.getJSONArray("role");
	      for (int i = 0; i < jsonArr.length(); i++) { 
	              JSONObject jsonObj = jsonArr.getJSONObject(i); 
	              System.out.println("角色名称=" + jsonObj.get("rolename"));
	              System.out.println("角色ID=" + jsonObj.get("roleId")); 
	      } 

		
	}


}

