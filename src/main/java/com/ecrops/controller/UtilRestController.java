package com.ecrops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecrops.entity.AllocatedSurveyNoMapping;
import com.ecrops.entity.DataSourceWiseBookingReport;
import com.ecrops.entity.FarmerBookingDetails;
import com.ecrops.entity.MaoAuthVaaVroekyc;
import com.ecrops.entity.NormalAreasMwiseMao;
import com.ecrops.entity.RbkSurveyNoMapping;
import com.ecrops.entity.RbkSurveyNoMappingDrpdwn;
import com.ecrops.entity.RofrBookedExtent;
import com.ecrops.entity.RofrBookedExtentPartitions;
import com.ecrops.entity.SeasonCropBookedExtent;
import com.ecrops.partitions.AllocatedSurveyNoMappingPartition;
import com.ecrops.partitions.DataSourceWiseBookingReportPartitions;
import com.ecrops.partitions.RbkSurveyNoMappingDrpdwnPartitions;
import com.ecrops.partitions.SeasonCropBookedExtentPartition;
import com.ecrops.projections.MasterProjections;
import com.ecrops.repo.AllocataedSurveyNoMappingRepo;
import com.ecrops.repo.FarmerBookingDetailsPartitions;
import com.ecrops.repo.FarmerBookingDetailsRepo;
import com.ecrops.repo.MaoAuthVaaVroekycPartition;
import com.ecrops.repo.MaoAuthVaaVroekycRepo;
import com.ecrops.repo.NormalAreasMwiseMaoRepo;
import com.ecrops.repo.RbkSurveyNoMappingPartition;
import com.ecrops.repo.RbkSurveyNoMappingRepo;
import com.ecrops.repo.RofrBookedExtentRepo;
import com.ecrops.repo.RofrBookedExtentRepo.EfishDetailsC;
import com.ecrops.repo.RofrBookedExtentRepo.EfishDetailsR;
import com.ecrops.repo.RofrBookedExtentRepo.FarmerDetails;
import com.ecrops.repo.SeasonCropBookedExtentRepo;

@RestController

@RequestMapping("/util")
public class UtilRestController {

	@Autowired
	private NormalAreasMwiseMaoRepo normalAreasMwiseMaoRepo;
	@Autowired
	private FarmerBookingDetailsRepo farmerBookingDetailsRepo;
	@Autowired
	private FarmerBookingDetailsPartitions partition;
	@Autowired
	private RofrBookedExtentPartitions rofrBookedExtentPartitions;
	@Autowired
	private MaoAuthVaaVroekycRepo maoAuthVaaVroekycRepo;
	@Autowired
	private MaoAuthVaaVroekycPartition maoAuthVaaVroekycPartition;
	@Autowired
	private RbkSurveyNoMappingRepo rbkSurveyNoMappingRepo;

	@Autowired
	private RbkSurveyNoMappingPartition rbkSurveyNoMappingPartition;

	@Autowired
	private AllocataedSurveyNoMappingRepo allocataedSurveyNoMappingRepo;

	@GetMapping("/getAllSeason")
	public List<MasterProjections> getAllSeasonn() {
		List<MasterProjections> list = normalAreasMwiseMaoRepo.getAllSeason();
		return list;
	}

	@GetMapping("/getAllCrop")
	public List<MasterProjections> getAllCrop() {
		List<MasterProjections> list = maoAuthVaaVroekycRepo.getAllCrops();
		return list;
	}

	
	@Autowired 
	SeasonCropBookedExtentRepo seasonCropBookedExtentRepo;
	
	@GetMapping("/getAllMandals")
	public List<MasterProjections> getMandals( Integer dcode) {
		List<MasterProjections> list = seasonCropBookedExtentRepo.getAllMandals(dcode);
		return list;
	}

	@GetMapping("/getNormarlAreaReport")
	List<NormalAreasMwiseMao> getList(String dcode, String mcode, String cropyear) {
		

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);
		 System.out.println("seasonType=>"+seasonType);
		 System.out.println("seasonYear=>"+seasonYear);

		Integer ddcode = Integer.parseInt(dcode);
		Integer mmcode = Integer.parseInt(mcode);
		
		 System.out.println("dcode=>"+dcode);
			System.out.println("mcode=>"+mcode);
		  System.out.println("cropyear=>"+cropyear);

		List<NormalAreasMwiseMao> listt = normalAreasMwiseMaoRepo.getListt(ddcode, mmcode, seasonType, seasonYear);
		 System.out.println("list size=>"+listt.size());
		 System.out.println("list =>"+listt.toString());
		return listt;
	}

// <-------------FARMER BOOKING DETAILS----------->
	@GetMapping("/getFbDetails")
	List<FarmerBookingDetails> getFarmers(String dcode, String mcode, String cropyear, HttpSession session,
			String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);

		Integer ddcode = Integer.parseInt(dcode);
		Integer mmcode = Integer.parseInt(mcode);
		String wbdcode = (String) session.getAttribute("wbdcode");

		List<FarmerBookingDetails> Farmerlist = partition.farmerBookingDetails(dcode, mcode, year, wbdcode);

		return Farmerlist;
	}

	@GetMapping("/getFbDetails1")
	List<FarmerBookingDetails> getFbDetails(String dcode, String mcode, String cropyear, String wbdcode,
			String userid) {
//		System.out.println("dcode===================>" + dcode);
//		System.out.println("mcode===================>" + mcode);
//		System.out.println("cropyear===================>" + cropyear);
//		System.out.println("wbdcode===================>" + wbdcode);

		List<FarmerBookingDetails> list = partition.farmerBookingDetails(dcode, mcode, cropyear, wbdcode);
		System.out.println("details===================>" + list.size());

		return list;
	}

	// <------------------ROFR BOOKED EXTENT----------------->//
	
	
	@GetMapping("/getRofr")
	List<RofrBookedExtent> getRbExt(String wbcode, String wbmcode, String cropyear, HttpSession session,
			String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);

		// Integer ddcode = Integer.parseInt(dcode);
		// Integer mmcode = Integer.parseInt(mcode);
		String wbdcode = (String) session.getAttribute("wbdcode");

		List<RofrBookedExtent> rofrList = rofrBookedExtentPartitions.rofrbext(wbdcode, wbmcode, year);

		return rofrList;
	}

	@GetMapping("/getRofr1")
	List<RofrBookedExtent> getRext(String wbdcode, String wbmcode, String cropyear, String userid) {
//		System.out.println("dcode===================>" + dcode);
//		System.out.println("mcode===================>" + mcode);
//		System.out.println("cropyear===================>" + cropyear);
//		System.out.println("wbdcode===================>" + wbdcode);

		List<RofrBookedExtent> rofr = rofrBookedExtentPartitions.rofrbext(wbdcode, wbmcode, cropyear);
		System.out.println("details===================>" + rofr.size());

		return rofr;
	}

	// <------------------MaoAuthVaaVroEkyc----------------->//
	@GetMapping("/getekyc")
	List<MaoAuthVaaVroekyc> getEkyc(String wbdcode, String cropyear, HttpSession session, String mcode, String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);

		// Integer ddcode = Integer.parseInt(dcode);
		// Integer mmcode = Integer.parseInt(mcode);
		// String wbdcode = (String) session.getAttribute("wbdcode");

//			List<MaoAuthVaaVroekyc> ekycList = maoAuthVaaVroekycPartition.vaaVroEkyc(wbdcode, year,mcode);

		return null;
	}

	@GetMapping("/getekyc1")
	List<MaoAuthVaaVroekyc> getEkycVaaVro(String wbdcode, String cropyear, String mcode, String userid, String cropid) {
		System.out.println("wbdcode===================>" + wbdcode);
		System.out.println("mcode===================>" + mcode);
		System.out.println("cropyear===================>" + cropyear);
		System.out.println("userid===================>" + userid);
		System.out.println("cropid===================>" + cropid);

		List<MaoAuthVaaVroekyc> ekyc = maoAuthVaaVroekycPartition.vaaVroEkyc(wbdcode, mcode, cropyear, cropid);
		System.out.println("details===================>" + ekyc.size());

		return ekyc;
	}

	// <------------------MaoRbkSurveyNoMapping----------------->//

	@GetMapping("/rbk")
	List<RbkSurveyNoMapping> getSurveyNo(String wbdcode, String cropyear, HttpSession session, String mcode,
			String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		

		// Integer ddcode = Integer.parseInt(dcode);
		// Integer mmcode = Integer.parseInt(mcode);
		// String wbdcode = (String) session.getAttribute("wbdcode");

		// List<MaoAuthVaaVroekyc> ekycList =
		// maoAuthVaaVroekycPartition.vaaVroEkyc(wbdcode, year,mcode);

		return null;
	}

	@GetMapping("/rbk1")
	List<RbkSurveyNoMapping> getRbkSno(String wbdcode, String cropyear, String mcode, String userid, String updatedby) {

		System.out.println("wbdcode===================>" + wbdcode);
		System.out.println("mcode===================>" + mcode);
		System.out.println("cropyear===================>" + cropyear);
		System.out.println("userid===================>" + userid);
		

		List<RbkSurveyNoMapping> sno = rbkSurveyNoMappingPartition.rbkSno(wbdcode, mcode, cropyear, userid, userid);
		System.out.println("details===================>" + sno.size());

		return sno;
	}

	// <------------------AllocatedSurveyNoMapping----------------->//
	@Autowired
	private AllocatedSurveyNoMappingPartition allocatedSurveyNoMappingPartition;
	@Autowired
	private RbkSurveyNoMappingDrpdwnPartitions rbkSurveyNoMappingDrpdwnPartitions;
	

	@GetMapping("/drpd")
	List<RbkSurveyNoMappingDrpdwn> getRbkdrpd(String mcode,String cropyear,String wbdcode,String rbkcode,
			HttpSession session,HttpServletRequest request) {
		System.out.println("mcode==========>"+mcode);
		System.out.println("cropyear==========>"+cropyear);
		System.out.println("wbdcode==========>"+wbdcode);
		System.out.println("rbkcode==========>"+rbkcode);
		
//		String[] season = cropyear.split("@");
//		String seasonType = season[0];
//		String year = season[1];
//		Integer seasonYear = Integer.parseInt(season[1]);
	  cropyear=	request.getParameter("cropyear");
	  //mcode= (String) session.getAttribute("mcode");
	  //wbdcode=  (String) session.getAttribute("wbdcode");
	 // rbkcode=  (String) session.getAttribute("rbkcode");


		List<RbkSurveyNoMappingDrpdwn> aldw = rbkSurveyNoMappingDrpdwnPartitions.getRBK(mcode,cropyear,wbdcode,rbkcode);
		System.out.println("details------>"+aldw.size());

		return aldw;
	}
	
	@GetMapping("/asnom")
	List<AllocatedSurveyNoMapping> getAlcSurveyNo(String wbdcode, String cropyear, HttpSession session, String mcode,
			String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);

		return null;
	}

	@GetMapping("/asnom1")
	List<AllocatedSurveyNoMapping> getAllocSnoMapping(String wbdcode, String cropyear, String mcode, String userid,String rbkid) {

		System.out.println("/asnom1");
		System.out.println("wbdcode===================>" + wbdcode);
		System.out.println("mcode===================>" + mcode);
		System.out.println("cropyear===================>" + cropyear);
		System.out.println("userid===================>" + userid);
		System.out.println("rbkid===================>" + rbkid);
		

List<AllocatedSurveyNoMapping> asno = allocatedSurveyNoMappingPartition.allocatedSnoDetails(wbdcode,  cropyear,  mcode,  userid, rbkid);
		System.out.println("details===================>" + asno.size());
		return asno;
	}
	//*******// DataSourceWiseBookingExtent//*******//
	@Autowired
	DataSourceWiseBookingReportPartitions dataSourceWiseBookingReportPartitions;
	@GetMapping("/dtsrcb")
	List<DataSourceWiseBookingReport> dataSrcDet(String wbdcode, String cropyear, HttpSession session, String wbmcode,
			String userid) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		String year = season[1];
		Integer seasonYear = Integer.parseInt(season[1]);

		return null;
	}

	@GetMapping("/dtsrcb1")
	List<DataSourceWiseBookingReport> dataSrcDet(String wbdcode, String cropyear, String wbmcode, String userid) {
		
		System.out.println("wbdcode===================>" + wbdcode);
		System.out.println("cropyear===================>" + cropyear);
		System.out.println("userid===================>" + userid);

List<DataSourceWiseBookingReport> dsb = dataSourceWiseBookingReportPartitions.dataSrcDet( wbdcode,  cropyear,  wbmcode,  userid);
		System.out.println("details===================>" + dsb.get(0));
		return dsb;
	}
	//**********************efishDetails***************************
	@Autowired  private RofrBookedExtentRepo rofrBookedExtentRepo;
	@GetMapping("/efishDetails")
	public ResponseEntity<?> efishDetails(@RequestParam("dataSrc") String dataSrc,
			
			@RequestParam("dcode") String dcode,
			@RequestParam("mcode") String mcode){
		if(dataSrc.equalsIgnoreCase("R")) {
			List<EfishDetailsR> efishDetailsR=rofrBookedExtentRepo.efishDetailsR(dataSrc, Integer.parseInt(dcode), Integer.parseInt(mcode));
			return new ResponseEntity<List<EfishDetailsR>>(efishDetailsR, HttpStatus.OK);
		}
		if(dataSrc.equalsIgnoreCase("C")) {
			List<EfishDetailsC> efishDetailsC=rofrBookedExtentRepo.efishDetailsC(Integer.parseInt(dcode), Integer.parseInt(mcode));
			return new ResponseEntity<List<EfishDetailsC>>(efishDetailsC, HttpStatus.OK);
		}
	return null;
	}
	//**************** Farmer Details****************
@GetMapping("/farmerdet")
public ResponseEntity<?> farmerdet(@RequestParam("mcode") String mcode,@RequestParam("date")String date){
	List<FarmerDetails> farmerDetails=rofrBookedExtentRepo.farmerDetails(Integer.parseInt(mcode),date);
	return new ResponseEntity<List<FarmerDetails>>(farmerDetails,HttpStatus.OK);
	}
	//**************************************************************************//
@Autowired 
SeasonCropBookedExtentPartition seasonCropBookedExtentPartition;


@GetMapping("/allcrp")
List<SeasonCropBookedExtent> getCrops(  HttpSession session,String wbmcode,String cropyear,String userid,String wbdcode) {
	String[] season = cropyear.split("@");
	String seasonType = season[0];
	String year = season[1];
	Integer seasonYear = Integer.parseInt(season[1]);

	return null;
}

@GetMapping("/allcrp1")
List<SeasonCropBookedExtent> getAllCrop(String wbmcode,String cropyear,String userid,String wbdcode) {
	
	System.out.println("wbmcode===================>" + wbmcode);
	System.out.println("cropyear===================>" + cropyear);
	System.out.println("userid===================>" + userid);

List<SeasonCropBookedExtent> scp = seasonCropBookedExtentPartition.getAllCrops(wbmcode,cropyear,userid,wbdcode);
	System.out.println("details===================>" + scp.get(0));
	return scp;
}
}






