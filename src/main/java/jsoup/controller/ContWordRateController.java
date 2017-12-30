package jsoup.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jsoup.pojo.WordCount;


@Controller
public class ContWordRateController {

	@RequestMapping("/countword")
	public String countWord(HttpServletRequest request){
		
		File file = new File("C:\\Users\\Hao\\Desktop\\alice2.txt");
		TreeMap<String,Integer> treeMap = new TreeMap<String,Integer>();
		List<WordCount> list = new ArrayList<WordCount>();
		
		BufferedReader br =null;
		//拆分一行文本 为单个单词或字的表达式
		String regex="[^a-z]+";
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			InputStreamReader isr = new InputStreamReader(bis);
			br=new BufferedReader(isr);
			String str;
			while((str =br.readLine())!=null){
				String[] strs =str.toLowerCase().split(regex);
				for(String s:strs){
					if(s.length()!=0){
						if(treeMap.get(s)==null){
							treeMap.put(s, 1);
						}else{
							int value=treeMap.get(s);
							treeMap.put(s,value+1);
						}
					}
				}
			}
		//将TreeMap中的单词提出放到Set中
		Set<String> set = treeMap.keySet();
		Iterator<String> it = set.iterator();	
		while(it.hasNext()){
			String word =it.next();
			list.add(new WordCount(word,treeMap.get(word)));
		}
		//排序
		Collections.sort(list);
		List<WordCount> subList=list.subList(0, 20);
		
		//构造前端反馈Json
		String listStr1 =subList.toString();
		String listStr2 =listStr1.substring(1,listStr1.length()-1);
		String listStr ="{charts: {"+listStr2+"},components: {"+listStr2+"}}";
		String listStr3="{"+listStr2+"}";
		request.setAttribute("list", listStr);
		request.setAttribute("list1", listStr3);
		
		//System.out.println(listStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//关流
			try {
				if(br!=null){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		
		return "showIndex";
	}
	
}
