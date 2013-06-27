package Common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import Model.Employee;

public class Tools {
	public static  Model.Employee GetEmployee(InputStream inStream,String EmpNo) throws Exception
	{
		String soap=readSoapFile(inStream,EmpNo);
		byte[] data=soap.getBytes();
		URL url=new URL("http://172.18.19.121:100/HR4PWS.asmx/");
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5*1000);
		conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
		conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if(conn.getResponseCode()==200){
			Model.Employee xml= parseResponseXML(conn.getInputStream());
			conn.disconnect();
			return xml;
		}
		conn.disconnect();
		return null;
	
		
	}

	private static Model.Employee  parseResponseXML(InputStream inputStream) throws Exception {
		// TODO Auto-generated method stub
		
		Model.Employee employee=new Model.Employee();
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		int eventType=parser.getEventType();
		while(eventType!=XmlPullParser.END_DOCUMENT)
		{
			switch(eventType)
			{
				case XmlPullParser.START_TAG:String name=parser.getName();
				if("EmpNo".equals(name))
				{
					employee.EmpNo=parser.nextText();
				}
				if("Name".equals(name))
				{
					employee.Name=parser.nextText();
				}
				if("PhoneNumber".equals(name))
				{
					employee.PhoneNumber=parser.nextText();
				}
				if("OfficeNumber".equals(name))
				{
					employee.OfficeNumber=parser.nextText();
				}
				if("Duty".equals(name))
				{
					employee.Duty=parser.nextText();
				}	
				if("Email".equals(name))
				{
					employee.Email=parser.nextText();
				}
				break;
			}
			eventType=parser.next();
		}
		return employee;
	}

	private static String readSoapFile(InputStream inStream, String empNo) throws Exception {
		// TODO Auto-generated method stub
		byte[] data=StreamTool.inputStream2Byte(inStream);
		String soapxml=new String(data);
		Map<String,String> params=new HashMap<String,String>();
		params.put("EmpNo",empNo);
		return replace(soapxml,params);
	}

	private static String replace(String soapxml, Map<String, String> params) {
		String result=soapxml;
		if(params!=null&&!params.isEmpty())
		{
			for(Map.Entry<String, String>entry:params.entrySet())
			{
				String name="\\$"+entry.getKey();
				Pattern pattern=Pattern.compile(name);
				Matcher matcher=pattern.matcher(result);
				if(matcher.find())
				{
					result=matcher.replaceAll(entry.getValue());
				}
			}
		}
		return result;
		
	}

}
