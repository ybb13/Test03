package test;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import lotus.domino.Database;
import lotus.domino.NotesFactory;
import lotus.domino.Session;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;
import lotus.domino.Document;
import lotus.domino.NotesException;
import org.omg.CORBA.UserException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ConnectDomino {

	public static void main(String[] args) {
		
			String host = "192.168.1.230";
			Session s;
			try {
				s = NotesFactory.createSession(host, "admin", "password");
				Database db = s.getDatabase("", "weboa/system/test.nsf");

				if (db.isOpen()) {
					//JsonParse(getData(), db);
					System.out.println(getPath(s));
				}
			} catch (UserException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				//System.out.println(e.id);
				//e.printStackTrace();
			}
		
	}

	public static String getData() {

		// String wsldUrl = "http://192.168.1.230:8080/usercenter/SystemRolePort?wsdl";
		// String wsldUrl =
		// "http://192.168.1.230/weboa/system/userreg.nsf/getUserMail?wsdl";
		
		String wsldUrl = "http://192.168.1.230:8080/usercenter/UserCenterPort?wsdl";

		Service service = new Service();
		Call call;
		String et = "";
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(wsldUrl));

			call.setOperationName(new QName("http://service.flysoft.usercenter/", "getAllCompany"));
			// call.setOperationName(new
			// QName("http://192.168.1.230/weboa/system/userreg.nsf/getUserMail?openWebService","getMailUrl"));
			// call.addParameter("arg0", XMLType.XSD_STRING, ParameterMode.IN);
			// call.addParameter("arg1", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(org.apache.axis.Constants.XSD_STRING);
			et = (String) call.invoke(new Object[] {});

			return et;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return et;
	}

	public static void JsonParse(String str, Database db) {

		if (str == "")
			return;
		
		JSONArray jsonArr = JSONArray.fromObject("[" + str + "]");
		
		try {

			String deptname = "";

			for (int i = 0; i < jsonArr.length(); i++) {

				JSONObject jsonObj = jsonArr.getJSONObject(i);
				deptname = (String) jsonObj.get("name");

				Document doc = db.createDocument();
				doc.appendItemValue("Form", "ManagerProfile");
				doc.appendItemValue("number", i + 1);
				doc.appendItemValue("rolename", deptname);
				doc.save();
			}

		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	
	public static String getPath(Session session) {
		Database db = null;
		View view = null;
		ViewEntryCollection vec = null;
		ViewEntry ve = null;
		Document doc =null;
		String reslut = "";
		try {
			db = session.getDatabase("", "weboa/system/test.nsf");
			view = db.getView("ManagerProfile");
			
			vec = view.getAllEntries();
			ve = vec.getFirstEntry();
			
			int num ;
			String name = "";
			
			while(ve!=null) {
				doc = ve.getDocument();
				
				num = doc.getItemValueInteger("number");
				name = doc.getItemValueString("rolename");
				
				reslut = reslut + "{"+num+":"+name+"},";
				
				ve = vec.getNextEntry(ve);
			}
			
			
			
		} catch (NotesException e) {
			e.printStackTrace();
		}
		
		
		return reslut;
	}

}
