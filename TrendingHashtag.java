import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class TrendingHashtag {
	
	  public static void main(String[] args) throws IOException {

		
		TrendingHashtag ht = new TrendingHashtag();
		Fibo_Heap fh = new Fibo_Heap();
	    File output=new File("output_file.txt");
		output.createNewFile();
		PrintStream out = new PrintStream(new FileOutputStream("output_file.txt"));
		FileReader fr = new FileReader(args[0]);
		
		BufferedReader br = new BufferedReader(fr);
		Map<String, Node> tagMap = new HashMap<String, Node>();
		ArrayList<Node> taglist = new ArrayList<>();
		String line = null;
		while((line = br.readLine()) != null){
			 StringTokenizer s=new StringTokenizer(line, " ");
			
			if (s.countTokens()==2){	               // If the number of tokens is 2 its a hashtag with its frequency
				    String a=s.nextToken();
					String tagelem = a.substring(1);
					String p=s.nextToken();
					int frequency = Integer.parseInt(p);
					
					
					if (tagMap.containsKey(tagelem)){
						Node n=tagMap.get(tagelem);
						int x=0;
						n.frequency = frequency + n.frequency;		
						if (n.head == null){
						   if(n.frequency > fh.Head.frequency)	
								fh.Head = n;
							    x=1;
						}
						
						if(x==0)                                  
						if (n.frequency > n.head.frequency){	  // Casc_cut is called as the element has higher frequency than its head
							fh.Casc_cut(n);
						}
					}
					
					else{                                        // The element does not exist in the table
						Node n = fh.Insert(tagelem, frequency);
						tagMap.put(tagelem, n);
					} 
					
			}
			
			else {                                          // According to the input the number of tokens is 1 if either its is a query or string is 'STOP'
				int count=0;
				String p=s.nextToken();
				     if(!p.equals("STOP"))
					  count = Integer.parseInt(p);

					
					while(count > 0){                  
						taglist.add(fh.remmax());          //  The elements removed are stored for inserting them back 								
						count--;
					}
					
					for (Node n : taglist){
						if (taglist.indexOf(n) == taglist.size() - 1){
							
							out.print(n.elem);
							out.println();
						}
						else{
								
							out.print(n.elem+",");
						}
						fh.Insert_back(n);                  // Add the max node back
					}	

					
					taglist.clear();           //  remove elements from the list
			}
		}

		br.close();
		fr.close();
		 		
		

	}

}