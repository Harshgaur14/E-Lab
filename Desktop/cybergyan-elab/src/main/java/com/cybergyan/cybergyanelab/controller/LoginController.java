package com.cybergyan.cybergyanelab.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cybergyan.cybergyanelab.dto.MyUser;
import com.cybergyan.cybergyanelab.entities.User;
import com.cybergyan.cybergyanelab.service.CustomUserDetailsService;
import com.cybergyan.cybergyanelab.service.VmFunction;

//import com.cybergyan.cybergyanelab.dto.LoginDto;


@Controller
//@RestController
public class LoginController implements ErrorController {

	@Autowired
	private CustomUserDetailsService userService;
	@Autowired
	private VmFunction vmfunction;
	
	private String hcipassword;
	private String baseauth;
	private String userpass;
	private String username;
	private String vmuuidwin;
	private String vmipwin;
	private String vmnamewin;
	private String usernamewin;
	private String usernamelinux;
	private String vmuuidlinux;
	private String vmiplinux;
	private String vmnamelinux;
	private String showContent="falsedcf";
	int ans=0;
	
	
	@GetMapping(value = "/login")
    public String loginView() {
        return "login";
    }
//
////    @PostMapping(value = "/mylogin")
////    public String login(@RequestBody LoginDto loginDto) {
////    	System.out.println(loginDto.getMyPassword());
////
////    	return "index.html";
////    }
	
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(@ModelAttribute User user) {
		
		return "/home";
	}
	
	
	
	
	
	
	    @GetMapping("/home")
	    public ModelAndView home(ModelAndView modelandview) throws IOException, InterruptedException {
	    	try {
	    	
	    	
	    	
	    	modelandview.setViewName("index.html");
    		
			System.out.println("\n\nFrom MyUser: Username: "+ MyUser.getUsername()+"\nPass: "+MyUser.getMyPassword());
			username=MyUser.getUsername();
    		hcipassword=MyUser.getMyPassword();
    		userpass= Base64.getEncoder().encodeToString((username + ":" + hcipassword).getBytes());
    		System.out.println(userpass);
    		//creating vm
    		usernamewin=username.concat("-window");
        	usernamelinux=username.concat("-linux");
	    	int ans=vmfunction.checkvm(usernamewin,usernamelinux,userpass);
        	System.out.println(ans);
	    	if(ans==1)
	    	{
	    		showContent="true";
	    		modelandview.addObject("showContent", showContent);
	    		username=MyUser.getUsername();
	    		System.out.println(username);
	    		
	    		hcipassword=MyUser.getMyPassword();
	    		System.out.println(hcipassword);
	    		userpass= Base64.getEncoder().encodeToString((username + ":" + hcipassword).getBytes());
	    		System.out.println(userpass);
	    		System.out.println(usernamewin);
	    		System.out.println(userpass);
	    		 vmuuidwin=vmfunction.searchuuid(usernamewin, userpass);
		   		 vmipwin=vmfunction.searchip(usernamewin, userpass);
		   		vmnamewin=vmfunction.searchvmname(usernamewin, userpass);
		   		System.out.println(vmuuidwin);
		   		System.out.println(vmipwin);
		   		System.out.println(vmnamewin);
		   		modelandview.addObject("ipaddresswin", vmipwin);
		   		modelandview.addObject("vmnameshowwin", vmnamewin);
		   	    vmfunction.poweron(vmuuidwin, userpass);
		   	
		   	    //for linux
		   	    vmuuidlinux=vmfunction.searchuuid(usernamelinux, userpass);
		   		 vmiplinux=vmfunction.searchip(usernamelinux, userpass);
		   		vmnamelinux=vmfunction.searchvmname(usernamelinux, userpass);
		   		System.out.println(vmuuidlinux);
		   		System.out.println(vmiplinux);
		   		System.out.println(vmnamelinux);
		   		modelandview.addObject("ipaddresslinux", vmiplinux);
		   		modelandview.addObject("vmnameshowlinux", vmnamelinux);
		   	    vmfunction.poweron(vmuuidlinux, userpass);
	    	}
	    	}catch(Exception ex) {
				ex.printStackTrace();
			}
		    
	    	
	    	
	    	return modelandview;
	    }
	    
	    @GetMapping("/createlab")
	    public ModelAndView cloneVm(ModelAndView modelandview, @ModelAttribute User user) throws IOException, InterruptedException {
	    	try {  
//	    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    		if (!(authentication instanceof AnonymousAuthenticationToken)) {
//		    	    username = authentication.getName();
//		    	    userpass=(String) authentication.getCredentials();
//		    	    System.out.println(username);
//		    	    System.out.println(userpass);
//		
//		    	}
	    		username=MyUser.getUsername();
	    		System.out.println(username);
	    		
	    		hcipassword=MyUser.getMyPassword();
	    		System.out.println(hcipassword);
	    		userpass= Base64.getEncoder().encodeToString((username + ":" + hcipassword).getBytes());
	    		System.out.println(userpass);
	    		//creating vm
	    		usernamewin=username.concat("-window");
	        	usernamelinux=username.concat("-linux");
	        	ans=vmfunction.checkvm(usernamewin,usernamelinux,userpass);
	        	System.out.println(ans);
	        	if(ans==-1)
	    		{
	    			vmfunction.clonevmwindow(usernamewin,userpass);
	    			vmfunction.clonevmlinux(usernamelinux, userpass);
	    			TimeUnit.SECONDS.sleep(10);
	    			}
	        	
	        	
	        	//for window VM
	   		 vmuuidwin=vmfunction.searchuuid(usernamewin, userpass);
	   		 vmipwin=vmfunction.searchip(usernamewin, userpass);
	   		vmnamewin=vmfunction.searchvmname(usernamewin, userpass);
	   		System.out.println(vmuuidwin);
	   		System.out.println(vmipwin);
	   		System.out.println(vmnamewin);
	   		modelandview.addObject("ipaddresswin", vmipwin);
	   		modelandview.addObject("vmnameshowwin", vmnamewin);
	   	    vmfunction.poweron(vmuuidwin, userpass);
	   	
	   	    //for linux
	   	    vmuuidlinux=vmfunction.searchuuid(usernamelinux, userpass);
	   		 vmiplinux=vmfunction.searchip(usernamelinux, userpass);
	   		vmnamelinux=vmfunction.searchvmname(usernamelinux, userpass);
	   		System.out.println(vmuuidlinux);
	   		System.out.println(vmiplinux);
	   		System.out.println(vmnamelinux);
	   		modelandview.addObject("ipaddresslinux", vmiplinux);
	   		modelandview.addObject("vmnameshowlinux", vmnamelinux);
	   	    vmfunction.poweron(vmuuidlinux, userpass);
	    		
	    	modelandview.setViewName("index2.html");
	  		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	    	return modelandview;
	    }
	    
	    
//	    @GetMapping("/launchconsole")
//	    public ModelAndView launchrdp(ModelAndView modelandview) throws IOException, InterruptedException {
//	    	try {
//	    	modelandview.setViewName("index2.html");
//	    	
//	    	modelandview.addObject("ipaddresswin", vmipwin);
//			modelandview.addObject("vmnameshowwin", vmnamewin);
//			
//			modelandview.addObject("ipaddresslinux", vmiplinux);
//			modelandview.addObject("vmnameshowlinux", vmnamelinux);
//			
//			
//			
//	    	vmfunction.launchrdp();
//	    	}
//	    	catch (Exception ex) {
//	    		ex.printStackTrace();
//			}
//	    	return modelandview;
//	    }
	    
	    
	    @PostMapping("/deletevm")
	    public ModelAndView deletevm(ModelAndView modelandview) throws IOException, InterruptedException {
	    	try {
	    	modelandview.setViewName("index.html");
	    	
	    	modelandview.addObject("ipaddresswin", vmipwin);
			modelandview.addObject("vmnameshowwin", vmnamewin);
			
			modelandview.addObject("ipaddresslinux", vmiplinux);
			modelandview.addObject("vmnameshowlinux", vmnamelinux);
			//deleting vm
			
			vmfunction.deletevm(vmuuidlinux);
			vmfunction.deletevm(vmuuidwin);
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
	    	return modelandview;
	    }
	    
	    @GetMapping("/createlab2")
	    public ModelAndView lab2(ModelAndView modelandview) throws IOException, InterruptedException
	    {
	    	try {
	    	modelandview.setViewName("index2.html");
	    	//vmfunction.clonevm(username,userpass);
	    	//TimeUnit.SECONDS.sleep(10);
			//vmfunction.poweron(vmuuid, userpass);
			System.out.println(vmuuidwin);
			System.out.println(vmipwin);
			System.out.println(vmnamewin);
			modelandview.addObject("ipaddresswin", vmipwin);
			modelandview.addObject("vmnameshowwin", vmnamewin);
			
			
			//vmfunction.poweron(vmuuid, userpass);
					System.out.println(vmuuidlinux);
					System.out.println(vmiplinux);
					System.out.println(vmnamelinux);
					modelandview.addObject("ipaddresslinux", vmiplinux);
					modelandview.addObject("vmnameshowlinux", vmnamelinux);
	    	}catch(Exception ex) {
				ex.printStackTrace();
			}
	    	
	    	return modelandview;
	    }
	   
	    
	    
	    
	    
	    @RequestMapping("/error")
	    public ModelAndView handleError(HttpServletResponse response)
	    {
	        ModelAndView modelAndView = new ModelAndView();
	 
	        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
	            modelAndView.setViewName("error-404");
	        }
	        else if (response.getStatus() == HttpStatus.FORBIDDEN.value()) {
	            modelAndView.setViewName("error-403");
	        }
	        else if (response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            modelAndView.setViewName("error-500");
	        }
	        else {
	            modelAndView.setViewName("error");
	        }
	 
	        return modelAndView;
	    }
	 
	    
	    public String getErrorPath() {
	        return "/error";
	    }
	    

	}
	    
	    

