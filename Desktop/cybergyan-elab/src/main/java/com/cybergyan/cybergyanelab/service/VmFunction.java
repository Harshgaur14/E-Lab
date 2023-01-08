package com.cybergyan.cybergyanelab.service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.io.File;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.JsonPath;
@Service
public class VmFunction {
	public void clonevmwindow(String string,String auth) throws IOException, InterruptedException
	{
		try {
		HttpRequest request = HttpRequest.newBuilder()
			    //.uri(URI.create("https://nmegh02.prd.dcservices.in/api/nutanix/v3/vms/287447eb-cbc5-49c4-a1e3-25b5086de66d/clone"))
				.uri(URI.create("https://nmegh02.prd.dcservices.in/api/nutanix/v3/vms/98f9da5c-ba3c-4664-a93c-d5eb2659ee83/clone"))
				.header("Content-Type", "application/json")
			    .header("Authorization", "Basic "+auth+"")
			    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"override_spec\": {\n    \"name\": \""+string+"\"\n   }\n   }"))
			    .build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	    
		}
	
	
	public void clonevmlinux(String string,String auth) throws IOException, InterruptedException
	{
		try {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://nmegh02.prd.dcservices.in/api/nutanix/v3/vms/287447eb-cbc5-49c4-a1e3-25b5086de66d/clone"))
				//.uri(URI.create("https://nmegh02.prd.dcservices.in/api/nutanix/v3/vms/bdf97ad2-ddb5-41b1-aa2a-d988fb20b07d/clone"))
			    .header("Content-Type", "application/json")
			    .header("Authorization", "Basic "+auth+"")
			    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"override_spec\": {\n    \"name\": \""+string+"\"\n   }\n   }"))
			    .build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	public String searchuuid(String string,String auth) throws IOException, InterruptedException
	{
		String uuid = null;
		try {
			System.out.println(string);
			System.out.println(auth);
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create("https://nmegh02.prd.dcservices.in/api/nutanix/v3/vms/list"))
			    .header("Content-Type", "application/json")
			    .header("Authorization", "Basic "+auth+"")
			    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"kind\": \"vm\",\n  \"offset\": 0,\n  \"length\": 123,\n  \"filter\": \"vm_name=="+string+"\",\n  \"sort_order\": \"ASCENDING\",\n  \"sort_attribute\": \"ASCENDING\"\n}"))
			    .build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			
			 String a=response.body();
			 uuid = JsonPath.read(a, "$.entities[0].metadata.uuid");
			//String ipaddress = JsonPath.read(a, "$.entities[0].status.resources.nic_list[0].ip_endpoint_list.[0].ip");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
			return uuid;
	}
	
	
	public String searchip(String string,String auth) throws IOException, InterruptedException
	{
		String ipaddress=null;
		try {
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create("https://nmegh02.prd.dcservices.in/api/nutanix/v3/vms/list"))
			    .header("Content-Type", "application/json")
			    .header("Authorization", "Basic "+auth+"")
			    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"kind\": \"vm\",\n  \"offset\": 0,\n  \"length\": 123,\n  \"filter\": \"vm_name=="+string+"\",\n  \"sort_order\": \"ASCENDING\",\n  \"sort_attribute\": \"ASCENDING\"\n}"))
			    .build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			
			 String a=response.body();
			//String uuid = JsonPath.read(a, "$.entities[0].metadata.uuid");
			 	ipaddress = JsonPath.read(a, "$.entities[0].status.resources.nic_list[0].ip_endpoint_list.[0].ip");
			//String vmname = JsonPath.read(a, "$.entities[0].status.name");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
			return ipaddress;
	}
	
	
	//Vmname to show
	public String searchvmname(String string,String auth) throws IOException, InterruptedException {
		String vmnameshow=null;
		try {
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create("https://nmegh02.prd.dcservices.in/api/nutanix/v3/vms/list"))
			    .header("Content-Type", "application/json")
			    .header("Authorization", "Basic "+auth+"")
			    .method("POST", HttpRequest.BodyPublishers.ofString("{\n  \"kind\": \"vm\",\n  \"offset\": 0,\n  \"length\": 123,\n  \"filter\": \"vm_name=="+string+"\",\n  \"sort_order\": \"ASCENDING\",\n  \"sort_attribute\": \"ASCENDING\"\n}"))
			    .build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

			 String a=response.body();
			//String uuid = JsonPath.read(a, "$.entities[0].metadata.uuid");
			//String ipaddress = JsonPath.read(a, "$.entities[0].status.resources.nic_list[0].ip_endpoint_list.[0].ip");
			 vmnameshow = JsonPath.read(a, "$.entities[0].status.name");
		}
		catch(Exception ex) {
			ex.printStackTrace();
	}
			return vmnameshow;
	}
	
	
	
	
	public void poweron(String string,String auth) throws IOException, InterruptedException
	{
		try {
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create("https://nmegh02.prd.dcservices.in/api/nutanix/v0.8/vms/set_power_state/fanout"))
			    .header("Content-Type", "application/json")
			    .header("Authorization", "Basic YWRtaW46Q2RhY0AxMjMhQCM=")
			    .method("POST", HttpRequest.BodyPublishers.ofString("[{\"generic_dto\":{\"transition\":\"on\",\"uuid\":\""+string+"\"},\"cluster_uuid\":\"0005a833-66d0-008c-62e3-08f1ea7d7714\"}]"))
			    .build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		
			System.out.println(response.body());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}	
	
	public void deletevm(String vmuuid) throws IOException, InterruptedException
	{
		try {
		HttpRequest request = HttpRequest.newBuilder()
		        .uri(URI.create("https://nmegh02.prd.dcservices.in/api/nutanix/v3/vms/"+vmuuid+""))
		        .header("Content-Type", "application/json")
		        .header("Authorization", "Basic YWRtaW46Q2RhY0AxMjMhQCM=")
		        .method("DELETE", HttpRequest.BodyPublishers.noBody())
		        .build();
		    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		    System.out.println(response.body());
		    System.out.println("Deleted Vm with uuid "+vmuuid);
	
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
//	public void launchrdp() {
//		try {
//			
//			String os=System.getProperty("os.name");
//			System.out.println(os);
//			if(os.toLowerCase().contains("window")) {
//			
//			System.out.println("Executing the RDP.exe");
//			  Process process = Runtime.getRuntime().exec("mstsc.exe", null);
//			  	System.out.println("RDP should now open.");
//			}else if(os.toLowerCase().contains("linux"))
//			{
//				Process process=Runtime.getRuntime().exec("remmina");
//				System.out.println("remmina should now open");
//						
//			}
//			
//		}
//		catch(Exception ex) {
//			ex.printStackTrace();
//		}
//	}
	
	
	public int checkvm(String stringwin,String stringlinux,String auth) throws IOException, InterruptedException
	{
		try {
		if(stringwin.equals(searchvmname(stringwin, auth)) && stringlinux.equals(searchvmname(stringlinux, auth)) )
		{
			System.out.println("1");
			return 1;
		}
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		return -1;
	}
	
//	public String getPassword() {
//		
//        Object principal = SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();
//        String username = principal.toString();
//        String passwrd=principal.toString();
//        if (principal instanceof UserDetails) {
//        	
//            username = ((UserDetails) principal).getUsername();
//            passwrd=((UserDetails) principal).getPassword();
//            System.out.println("username: " + username);
//            System.out.println("password: "
//                    + ((UserDetails) principal).getPassword());
//            	
//}
//		return passwrd;
//	
//	}
	
}
