package com.ecrops.controller;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.ecrops.repo.ActiveSeasonRepository;
import com.ecrops.repo.FarmerBookingDetailsPartitions;
import com.ecrops.repo.FarmerBookingDetailsRepo;
import com.ecrops.repo.NormalAreasMwiseMaoRepo;



@Controller
public class MaoRibbonController {
	
	@Autowired NormalAreasMwiseMaoRepo normalAreasMwiseMaoRepo;	
	@Autowired FarmerBookingDetailsRepo farmerBookingDetailsRepo;
	
	@Autowired FarmerBookingDetailsPartitions  partion;
	
	@Autowired  ActiveSeasonRepository  activerepo;


	
	 
	@GetMapping("/normal")
	public String getNormalAreaList(HttpSession httpSession,Model model  ){
		String dcode=(String) httpSession.getAttribute("dcode");
		String mcode=(String) httpSession.getAttribute("mcode");
		 
	
	System.out.println("dcode==================="+dcode);
	System.out.println("mcode=============="+mcode);
	model.addAttribute("dcode","dcode");
	model.addAttribute("mcode","mcode");
	return "normalareasmao";
	}
	
	@GetMapping("/crop")
	public String getCropDetails(Model model ){
//	model.addAttribute("crp",crp);
	return "cropBookingDetailsMAO";
	}
	
	@GetMapping("/farmerdetails")
	public String getFarmerDetails(HttpSession httpSession){
	return "farmerbookingDetails";
	}
	
	
	
	
	@GetMapping("/rofr")
	public String roftbkdext( Model model) {
	return "rofr-extentbooked"	;
	}

	@GetMapping("/ekycmao")
	public String MaoEkyc(Model model) {
	return "maovaaVroEkyc"	;
	}
	@GetMapping("/rbksnomapping")
	public String rSnoMap(Model model , HttpSession session) {
		
		 String userid=(String)session.getAttribute("userid");
		 int wbdcode=(int)session.getAttribute("wbdcode");
		  int mcode=(int)session.getAttribute("mcodee");
		 
		 System.out.println("userid---->"+userid);
		 System.out.println("wbdcode---->"+wbdcode);
		 System.out.println("mcode---->"+mcode);
		 
			model.addAttribute("userid", userid);
			model.addAttribute("wbdcode",wbdcode );
			model.addAttribute("mcode",mcode );
			
	return "RbkSurveyNoMapping"	;
	}
	@GetMapping("/allocatedSnoMap")
	public String allSnoMap(Model model) {
	return "Allocated_SurveyNo_Mapping"	;
	}
@GetMapping("/datasrc")
public String getDataSrc(Model model) {
	return "DataSourceWiseExt";
	
}

@GetMapping("/efishRofr")
public String getEfishRofr(Model model) {
	return "EfishRofrDetails";
	
}
@GetMapping("/farmdet")
public String getFarmerDet(Model model) {
return "FarmerDetails";

}
@GetMapping("/allcrops")
public String getSeasonAllCrops(Model model) {
return "SeasonCropBookedExtent";

}
}
