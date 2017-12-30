package jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jsoup.pojo.Item;

public class JDUtil {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//抓取所有三级分类
	public static List<String> getItemCatLevel3(String url) throws IOException{
		Elements els = Jsoup.connect(url).get().select(".items .clearfix dd a");
		List<String> itemCatList = new ArrayList<String>();
		for(Element ele :els){
			//三级菜单链接 //list.jd.com/list.html?cat=737,738,745
			String href = "http:"+ele.attr("href");
			if(href.startsWith("http://list.jd.com/list.html?cat=")){
				System.out.println(href);
				
				itemCatList.add(href);
				//break;
			}
		}
		System.out.println("抓取到三级分类：" + itemCatList.size());
		return itemCatList;
	}
	
	//获取某个分类的页面总数
	public static Integer getPageNum(String catUrl){
		try {
			String s = Jsoup.connect(catUrl).get().select("#J_topPage .fp-text i").get(0).text();
			return Integer.parseInt(s);
		} catch (Exception e) {
			//写错误日志，后期再继续抓
			return 0;
		}
	}
	
	//获取所有列表页面的url
	public static List<String> getAllPageUrl(String url) throws IOException{
		List<String> pageUrlList = new ArrayList<String>();
		for(String catUrl : JDUtil.getItemCatLevel3(url)){
			//某个商品分类，拼接页数
			Integer pageNum = JDUtil.getPageNum(catUrl);
			for(int page=1;page<=pageNum;page++){
				url = catUrl+"&page="+page;
				System.out.println(url);
				
				pageUrlList.add(url);
			}
		}
		return pageUrlList;
	}
	
	//获取某个分类某页商品分类列表页面，获取其中所有的商品的链接
	public static List<String> getItemUrl(String pageUrl){
		try {
			List<String> itemList = new ArrayList<String>();
			Elements els = Jsoup.connect(pageUrl).get()
					.select(".gl-i-wrap")
					.select(".j-sku-item .p-img a");
			for(Element ele :els){
				String url = "http:"+ele.attr("href");
				System.out.println(url);
				
				itemList.add(url);
			}
			return itemList;
		} catch (Exception e) {
			return null;
		}
	}
	
	//获取标题
	public static String getTitle(String itemUrl){
		try {
			Elements els = Jsoup.connect(itemUrl).get()
					.select(".itemInfo-wrap .sku-name");	//多个之间用空格隔开
			String title = els.get(0).text();
			
			return title;
		} catch (Exception e) {
			//如果上面抓取出错，在按另外方式来抓取。根据结果进行分析的
			try {
				Elements els = Jsoup.connect(itemUrl).get()
						.select("#itemInfo #name h1");	//多个之间用空格隔开
				String title = els.get(0).text();
				
				return title;
			} catch (Exception e2) {
				System.out.println(itemUrl);
				return "[ERROR]";	//在字段上特殊标识，更加方便重新抓取
			}
		}
	}
	
	//获取卖点，返回值json数组
	public static String getSellPoint(String itemUrl){
		try {
			//以execute执行时，必须忽略请求类型
			Response response = Jsoup.connect(itemUrl).ignoreContentType(true).execute();
			String json = response.body();
			
			JsonNode jsonNode = MAPPER.readTree(json);
			String sellPoint = jsonNode.get(0).get("ad").asText();
			
			return sellPoint;
		} catch (Exception e) {
			return null;
		}
	}
	
	//获取价格
	public static Double getPrice(String itemId){
		String url = "http://p.3.cn/prices/mgets?skuIds=J_"+itemId;
		try {
			String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
			JsonNode jsonNode = MAPPER.readTree(json);
			String p = jsonNode.get(0).get("p").asText();
			
			return Double.parseDouble(p);
		} catch (Exception e) {
			return null;
		}
	}
	
	//获取图片
	public static String getImage(String itemUrl){
		try {
			String s = "";
			Elements els = Jsoup.connect(itemUrl).get().select("ul li img");
			for(Element ele :els){
				String url = "http:"+ele.attr("src");
				s+=url+",";
			}
			s = s.substring(0,s.length()-1);	//删除最后一个逗号
			return s;
		} catch (Exception e) {
			return null;
		}
	}
	
	//获取商品详情，jsonp
	public static String getDesc(String itemId){
		String url = "http://d.3.cn/desc/"+itemId;
		try {
			String jsonp = Jsoup.connect(url).ignoreContentType(true).execute().body();
			String json = jsonp.substring(jsonp.indexOf("(")+1, jsonp.length()-1);
			JsonNode jsonNode = MAPPER.readTree(json);
			String desc = jsonNode.get("content").asText();
			return desc;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	//获取商品的信息 http://item.jd.com/4341091.html
	public static Item getItem(String itemUrl){
		Item item = new Item();
		String s = itemUrl.substring(itemUrl.lastIndexOf("/")+1,itemUrl.lastIndexOf("."));
		
		item.setId(Long.parseLong(s));
		
		//获取标题
		item.setTitle(JDUtil.getTitle(itemUrl));
		
		//获取卖点 http://ad.3.cn/ads/mgets?skuids=AD_4341091
		item.setSellPoint(JDUtil.getSellPoint("http://ad.3.cn/ads/mgets?skuids=AD_"+s));
		
		//获取价格，二次请求
		item.setPrice(JDUtil.getPrice(s));
		
		//获取图片
		item.setImage(JDUtil.getImage(itemUrl));
		
		//获取描述
		item.setDesc(JDUtil.getDesc(s));
		
		//System.out.println(item);
		return item;
	}
	
	public static void go() throws IOException{
		String allCatUrl = "https://www.jd.com/allSort.aspx";
		List<String> pageUrls = JDUtil.getAllPageUrl(allCatUrl);
		//获取到某个商品分类下的分页页面
		for(String pageUrl : pageUrls){
			List<String> itemUrls = JDUtil.getItemUrl(pageUrl);
			//获取某个商品的链接
			for(String itemUrl :itemUrls){
				Item item = JDUtil.getItem(itemUrl);
				System.out.println(item);
			}
		}
	}
	
	@Test
	public void run() throws IOException{
		String allCatUrl = "https://www.jd.com/allSort.aspx";
		//JDUtil.getItemCatLevel3(allCatUrl);
		
//		String catUrl = "https://list.jd.com/list.html?cat=737,794,798";
//		Integer pageNum = JDUtil.getPageNum(catUrl);
//		System.out.println(pageNum);
		
		//JDUtil.getAllPageUrl(allCatUrl);
		
//		String pageUrl = "https://list.jd.com/list.html?cat=737,794,798&page=1";
//		JDUtil.getItemUrl(pageUrl);
		
//		String itemUrl = "http://item.jd.com/4341091.html";
//		JDUtil.getItem(itemUrl);
		
		JDUtil.go();
		
	}
}
