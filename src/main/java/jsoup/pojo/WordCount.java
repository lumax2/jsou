package jsoup.pojo;

public class WordCount implements Comparable<WordCount>{

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
		return  word + ":" + count;
	}
	
	
}

