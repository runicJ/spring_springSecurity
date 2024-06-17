package com.spring.springSecurity;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	//스캔해 오는 것
	@Autowired
	BCryptPasswordEncoder	passwordEncoder;  // 객체명 (servlet-context.xml)  // 선언x
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		//return "home";
		return "member/login";
	}
	
	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public String getLogin() {
		return "member/login";
	}	
	
//	@RequestMapping(value = "/logout", method = RequestMethod.POST)
//	public String getLogout() {
//		return "redirect:/";
//	}
	
	@RequestMapping(value = "/member/memberMain", method = RequestMethod.GET)
	public String getMemberMain() {
		return "member/memberMain";
	}
	
	@RequestMapping(value = "/pwdCheck", method = RequestMethod.GET)
	public String getPwdCheck(Model model, String pwd) {
		
		// 비밀번호 암호화처리(Spring Security에서 제공해주는 BCryptPasswordEncoder객체를 활용한 암호화)
		String encPwd = passwordEncoder.encode(pwd);
		
		model.addAttribute("pwd", pwd);
		model.addAttribute("encPwd", encPwd);
		
		return "member/memberMain";
	}
	
	@RequestMapping(value = "/pwdCheckOk", method = RequestMethod.POST)
	public String getPwdCheckOk(String pwd, String encPwd, Model model) {
		
//		String imsiPwd = passwordEncoder.encode(pwd);
		
//		System.out.println("원본 비번 : " + encPwd);
//		System.out.println("사용자 비번 : " + imsiPwd);
		
		//if(imsiPwd.equals(encPwd)) {
		if(passwordEncoder.matches(pwd, encPwd)) {  // matches(원본 비밀번호, 암호화된 비밀번호)
			model.addAttribute("pwdFlag", "비밀번호가 정확합니다.");
		}
		else {
			model.addAttribute("pwdFlag", "비밀번호를 확인하세요.");			
		}
		
		return "member/memberMain";
	}
}
