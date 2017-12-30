package jsoup.controller;

import java.sql.SQLException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jsoup.service.SpidderService;

@Controller
public class JsoupController {

	@Autowired
	private SpidderService spidderService;
	
	@RequestMapping("/test")
	public String run() throws SQLException{
		
		String allCatUrl = "https://www.jd.com/allSort.aspx";
		try {
			spidderService.go(allCatUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//JDUtil.getItemCatLevel3(allCatUrl);
		
//		String catUrl = "https://list.jd.com/list.html?cat=737,794,798";
//		Integer pageNum = JDUtil.getPageNum(catUrl);
//		System.out.println(pageNum);
		
		//JDUtil.getAllPageUrl(allCatUrl);
		
//		String pageUrl = "https://list.jd.com/list.html?cat=737,794,798&page=1";
//		JDUtil.getItemUrl(pageUrl);
		
//		String itemUrl = "http://item.jd.com/4341091.html";
//		JDUtil.getItem(itemUrl);
		
		
		return "search";
	}

}
