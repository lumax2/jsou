package jsoup;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TestJsoup {

	//@Test
	public void test1(){
		String[] array ="|DF|A3".split("|");
		System.out.println("begin");
		for(String a:array){
			System.out.println(a);
		}
		System.out.println("end");
		System.out.println(array);
		System.out.println("|DF|A3".split("|"));
		System.out.println("|DF|A3".split("|").length);
		
		String a="abcdefg";
		String b=a.substring(2,5);
		int c=a.substring(2,5).indexOf('d');
		System.out.println("substring:"+b);
		System.out.println(c);
	}
	
//	@Test	//获取一个页面
	public void page() throws IOException{
		//创建Jsoup对象
		String url = "http://finance.sina.com.cn/money/bank/bank_hydt/2017-09-15/doc-ifykywuc3636021.shtml";
		Connection cn = Jsoup.connect(url);	//创建连接
		Document doc = cn.get(); //得到页面
		Element ele = doc.body();
		String s = ele.text();
		System.out.println(s);
	}
	
	//@Test	//抓取整站，页面中的所有的url，a标签（重复）
	public void site() throws IOException{
		String url = "http://finance.sina.com.cn/money/bank/bank_hydt/2017-09-15/doc-ifykywuc3636021.shtml";
		//抓取当前页面的所有的a标签
		Elements els = Jsoup.connect(url).get().getElementsByTag("a");
		for(Element ele :els){
			url = ele.attr("href");
			System.out.println(url);
		}
	}
	
//	@Test	//抓取新闻标题，jQuery选择器，快速定位获取（id#，class.，tag）
	public void title() throws IOException{
		String url = "http://finance.sina.com.cn/money/bank/bank_hydt/2017-09-15/doc-ifykywuc3636021.shtml";
		Elements els = Jsoup.connect(url).get()
		.select(".wrap-inner .page-header h1");	//多个之间用空格隔开
		String title = els.get(0).text();
		System.out.println(title);
	}
}
