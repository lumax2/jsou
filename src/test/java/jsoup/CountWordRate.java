package jsoup;

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

import org.junit.Test;

public class CountWordRate {

	@Test
	public void test1(){
		
		File file = new File("C:\\Users\\Hao\\Desktop\\alice.txt");
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
						System.out.println(s);
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
			if(word!=null){
			list.add(new WordCount(word,treeMap.get(word)));
			}
		}
		Collections.sort(list);
		List<WordCount> list1=list.subList(0, 20);
		
		
		System.out.println(list1);
		System.out.println(list);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(br!=null){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
class WordCount implements Comparable<WordCount>{

	private String word;
	private int count;
	
	public WordCount(String word,Integer count){
		this.word=word;
		this.count=count;
	}
	//和O比较，正的说明你大，负的说明你笑
	@Override
	public int compareTo(WordCount o) {
		return  o.count-count;
	}

	public boolean equals(WordCount o) {
		return word.equals(o.word);
	}
	@Override
	public String toString() {
		return "{word:" + word + ", count:" + count+"}" ;
	}
	
	
}

