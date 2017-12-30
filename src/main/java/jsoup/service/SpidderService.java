package jsoup.service;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jsoup.JDUtil;
import jsoup.mapper.ItemMapper;
import jsoup.pojo.Item;

@Service
public class SpidderService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	private int i;
	
	public  void go(String allCatUrl) throws IOException{
		
		List<String> pageUrls = JDUtil.getAllPageUrl(allCatUrl);
		//获取到某个商品分类下的分页页面
		for(String pageUrl : pageUrls){
			List<String> itemUrls = JDUtil.getItemUrl(pageUrl);
			//获取某个商品的链接
			for(String itemUrl :itemUrls){
				Item item = JDUtil.getItem(itemUrl);
				i++;
				System.out.println(i);
				itemMapper.insertIntoDB(item);
			}
		}
	}
	
	@Test
	public void run(){
		String allCatUrl = "https://www.jd.com/allSort.aspx";
		try {
			this.go(allCatUrl);
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
		
		
		
	}

}
