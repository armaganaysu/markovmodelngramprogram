import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class driver {
		static int wordNum =0;
		static String file_bilim_is_basinda ="bilim_is_basinda.txt";
		static String file_bozkirda = "bozkirda.txt";
		static String file_degisim = "degisim.txt";
		static String file_denemeler = "denemeler.txt";
		static String file_grimms_fairy_tales = "grimms-fairy-tales_P1.txt";
		
		static ArrayList<String> bilim_is_basinda = new ArrayList<>(); 
		static ArrayList<String> bozkirda = new ArrayList<>(); 
		static ArrayList<String> degisim = new ArrayList<>(); 
		static ArrayList<String> denemeler = new ArrayList<>(); 
		static ArrayList<String> grimms_fairy_tales = new ArrayList<>(); 
		static ArrayList<String> allWords = new ArrayList<>(); 
		
		public static String processFile(String fileName) throws IOException {
		    StringBuilder whole_text = new StringBuilder();
		    InputStream is = driver.class.getResourceAsStream("/resources/" + fileName);
		    BufferedReader br = new BufferedReader(new InputStreamReader(is, "ISO-8859-9"));
		    String line;
		    while ((line = br.readLine()) != null) {
		    	line = line.toLowerCase().trim().replaceAll("[^\\s\\p{L}0-9']", "");
		        String[] words = line.split("\\s+");
		        whole_text.append(" ");
		        for (String word : words) {
		            if (word.contains("'")) {
		                word = word.substring(0, word.indexOf("'"));
		            }
		            whole_text.append(word).append(" ");
		        }
		    }
		    br.close();
		    return whole_text.toString().trim();
		}
		  

		public static void extractWords(String fileName) throws IOException {
		    String wordlist = processFile(fileName);
		    String[] parts = wordlist.split(" ");
		    for (String part : parts) {
		        if (!part.equals("")) {
		            allWords.add(part);
		            
		            if(fileName.equals(file_bilim_is_basinda)) {
		            	bilim_is_basinda.add(part);
		            }
		            else if(fileName.equals(file_bozkirda)) {
		            	bozkirda.add(part);
		            }
		            else if(fileName.equals(file_degisim)) {
		            	degisim.add(part);
		            }
		            else if(fileName.equals(file_denemeler)) {
		            	denemeler.add(part);
		            }
		            else if(fileName.equals(file_grimms_fairy_tales)) {
		            	grimms_fairy_tales.add(part);
		            }
		        } 
		    }
		}
		  	  
		  static void initializeCorpus() throws IOException {
			    extractWords(file_bilim_is_basinda);
			    extractWords(file_bozkirda); 
			    extractWords(file_degisim);
			    extractWords(file_denemeler);
			    extractWords(file_grimms_fairy_tales);
			    
			    wordNum = allWords.size();
			}

		  public static Map.Entry<String, Integer>[] getWordCount(ArrayList<String> source) {
			    Map<String, Integer> wordCountMap = new HashMap<>();

			    for (String str : source) {
			        wordCountMap.put(str, wordCountMap.getOrDefault(str, 0) + 1);
			    }

			    Map.Entry<String, Integer>[] entries = wordCountMap.entrySet().toArray(new Map.Entry[0]);

			    Arrays.sort(entries, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));

			    return entries;
			}

		    public static ArrayList<String> nGramCreator(List<String> data, int n){
		    	int loop = data.size() - n + 1;
		    	ArrayList<String> nGramList = new ArrayList<>(loop);	    	
		    	StringBuilder sb = new StringBuilder();
		    	for(int i = 0; i < loop; i++) {
		    		sb.setLength(0); //clear stringbuilder
		            switch(n) {
		                case 1:
		                    sb.append(data.get(i));
		                    break;

		                case 2: 
		                    sb.append(data.get(i))
		                       .append(" ")
		                       .append(data.get(i+1));
		                    break;

		                case 3:
		                    sb.append(data.get(i))
		                       .append(" ")
		                       .append(data.get(i+1))
		                       .append(" ")
		                       .append(data.get(i+2));
		                    break;
		            }
		            
		            nGramList.add(sb.toString());
		        }
		    	return nGramList;	         
		    }
		    
		    public static void printTopWords(ArrayList<String> allWords, int n, int topN) {
		        ArrayList<String> test = nGramCreator(allWords, n);
		        Map.Entry<String, Integer>[] testMap = getWordCount(test);
		        
		        int i = 0;
		        for (Map.Entry<String, Integer> entry : testMap) {
		            System.out.println(entry.getKey() + ": " + entry.getValue());
		            i++;
		            if(i == topN)
		                break;
		        }
		    }

		   
		    
	public static void main(String[] args) throws IOException {
	   long totalRuntime_start = System.currentTimeMillis();
	   
	   long runtimeStart_FileReading = System.currentTimeMillis();
	   initializeCorpus();
	   double runtime_FileReading_InSeconds = (System.currentTimeMillis() - runtimeStart_FileReading) / 1000.0;
   
	   long runtimeStart_bilimisbasinda_unigram = System.currentTimeMillis();
	   System.out.println("\n\nBilim_is_basinda Unigrams:\n------------------------------------------------------------");
	   printTopWords(bilim_is_basinda, 1, 20);
	   double runtime_bilimisbasinda_unigram_InSeconds = (System.currentTimeMillis() - runtimeStart_bilimisbasinda_unigram) / 1000.0;
	   
	   long runtimeStart_bilimisbasinda_bigram = System.currentTimeMillis();
	   System.out.println("\n\nBilim_is_basinda Bigrams:\n------------------------------------------------------------");
	   printTopWords(bilim_is_basinda, 2, 20);
	   double runtime_bilimisbasinda_bigram_InSeconds = (System.currentTimeMillis() - runtimeStart_bilimisbasinda_bigram) / 1000.0;
	   
	   long runtimeStart_bilimisbasinda_trigram = System.currentTimeMillis();
	   System.out.println("\n\nBilim_is_basinda Trigrams:\n------------------------------------------------------------");
	   printTopWords(bilim_is_basinda, 3, 20);
	   double runtime_bilimisbasinda_trigram_InSeconds = (System.currentTimeMillis() - runtimeStart_bilimisbasinda_trigram) / 1000.0;
	   System.out.println("Running time for Bilim is Basinda Novel with 1-gram calculation: " + String.format("%.3f", runtime_bilimisbasinda_unigram_InSeconds) + " second");
	   System.out.println("Running time for Bilim is Basinda Novel with 2-gram calculation: " + String.format("%.3f", runtime_bilimisbasinda_bigram_InSeconds) + " second");
	   System.out.println("Running time for Bilim is Basinda Novel with 3-gram calculation: " + String.format("%.3f", runtime_bilimisbasinda_trigram_InSeconds) + " second");		
	   
	   
	   
	   
	   
	   long runtimeStart_bozkirda_unigram = System.currentTimeMillis();
	   System.out.println("\n\nBozkirda Unigrams:\n------------------------------------------------------------");
	   printTopWords(bozkirda, 1, 20);
	   double runtime_bozkirda_unigram_InSeconds = (System.currentTimeMillis() - runtimeStart_bozkirda_unigram) / 1000.0;
	   
	   long runtimeStart_bozkirda_bigram = System.currentTimeMillis();
	   System.out.println("\n\nBozkirda Bigrams:\n------------------------------------------------------------");
	   printTopWords(bozkirda, 2, 20);
	   double runtime_bozkirda_bigram_InSeconds = (System.currentTimeMillis() - runtimeStart_bozkirda_bigram) / 1000.0;
	   
	   long runtimeStart_bozkirda_trigram = System.currentTimeMillis();
	   System.out.println("\n\nBozkirda Trigrams:\n------------------------------------------------------------");
	   printTopWords(bozkirda, 3, 20);
	   double runtime_bozkirda_trigram_InSeconds = (System.currentTimeMillis() - runtimeStart_bozkirda_trigram) / 1000.0;
	   System.out.println("Running time for Bozkirda Novel with 1-gram calculation: " + String.format("%.3f", runtime_bozkirda_unigram_InSeconds) + " second");
	   System.out.println("Running time for Bozkirda Novel with 2-gram calculation: " + String.format("%.3f", runtime_bozkirda_bigram_InSeconds) + " second");
	   System.out.println("Running time for Bozkirda Novel with 3-gram calculation: " + String.format("%.3f", runtime_bozkirda_trigram_InSeconds) + " second");	
	   
	   
	   
	   
	   long runtimeStart_degisim_unigram = System.currentTimeMillis();
	   System.out.println("\n\nDegisim Unigrams:\n------------------------------------------------------------");
	   printTopWords(degisim, 1, 20);
	   double runtime_degisim_unigram_InSeconds = (System.currentTimeMillis() - runtimeStart_degisim_unigram) / 1000.0;
	   
	   long runtimeStart_degisim_bigram = System.currentTimeMillis();
	   System.out.println("\n\nDegisim Bigrams:\n------------------------------------------------------------");
	   printTopWords(degisim, 2, 20);
	   double runtime_degisim_bigram_InSeconds = (System.currentTimeMillis() - runtimeStart_degisim_bigram) / 1000.0;
	   
	   long runtimeStart_degisim_trigram = System.currentTimeMillis();
	   System.out.println("\n\nDegisim Trigrams:\n------------------------------------------------------------");
	   printTopWords(degisim, 3, 20);
	   double runtime_degisim_trigram_InSeconds = (System.currentTimeMillis() - runtimeStart_degisim_trigram) / 1000.0;
	   System.out.println("Running time for Degisim Novel with 1-gram calculation: " + String.format("%.3f", runtime_degisim_unigram_InSeconds) + " second");
	   System.out.println("Running time for Degisim Novel with 2-gram calculation: " + String.format("%.3f", runtime_degisim_bigram_InSeconds) + " second");
	   System.out.println("Running time for Degisim Novel with 3-gram calculation: " + String.format("%.3f", runtime_degisim_trigram_InSeconds) + " second");	
	   
	   
	   
	   
	   long runtimeStart_denemeler_unigram = System.currentTimeMillis();
	   System.out.println("\n\nDenemeler Unigrams:\n------------------------------------------------------------");
	   printTopWords(denemeler, 1, 20);
	   double runtime_denemeler_unigram_InSeconds = (System.currentTimeMillis() - runtimeStart_denemeler_unigram) / 1000.0;
	   
	   long runtimeStart_denemeler_bigram = System.currentTimeMillis();
	   System.out.println("\n\nDenemeler Bigrams:\n------------------------------------------------------------");
	   printTopWords(denemeler, 2, 20);
	   double runtime_denemeler_bigram_InSeconds = (System.currentTimeMillis() - runtimeStart_denemeler_bigram) / 1000.0;
	   
	   long runtimeStart_denemeler_trigram = System.currentTimeMillis();
	   System.out.println("\n\nDenemeler Trigrams:\n------------------------------------------------------------");
	   printTopWords(denemeler, 3, 20);
	   double runtime_denemeler_trigram_InSeconds = (System.currentTimeMillis() - runtimeStart_denemeler_trigram) / 1000.0;
	   System.out.println("Running time for Denemeler Novel with 1-gram calculation: " + String.format("%.3f", runtime_denemeler_unigram_InSeconds) + " second");
	   System.out.println("Running time for Denemeler Novel with 2-gram calculation: " + String.format("%.3f", runtime_denemeler_bigram_InSeconds) + " second");
	   System.out.println("Running time for Denemeler Novel with 3-gram calculation: " + String.format("%.3f", runtime_denemeler_trigram_InSeconds) + " second");	
	   
	   
	   long runtimeStart_grimms_fairy_tales_unigram = System.currentTimeMillis();
	   System.out.println("\n\nGrimms_fairy_tales Unigrams:\n------------------------------------------------------------");
	   printTopWords(grimms_fairy_tales, 1, 20);
	   double runtime_grimms_fairy_tales_unigram_InSeconds = (System.currentTimeMillis() - runtimeStart_grimms_fairy_tales_unigram) / 1000.0;
	   
	   long runtimeStart_grimms_fairy_tales_bigram = System.currentTimeMillis();
	   System.out.println("\n\nGrimms_fairy_tales Bigrams:\n------------------------------------------------------------");
	   printTopWords(grimms_fairy_tales, 2, 20);
	   double runtime_grimms_fairy_tales_bigram_InSeconds = (System.currentTimeMillis() - runtimeStart_grimms_fairy_tales_bigram) / 1000.0;
	   
	   long runtimeStart_grimms_fairy_tales_trigram = System.currentTimeMillis();
	   System.out.println("\n\nGrimms_fairy_tales Trigrams:\n------------------------------------------------------------");
	   printTopWords(grimms_fairy_tales, 3, 20);
	   double runtime_grimms_fairy_tales_trigram_InSeconds = (System.currentTimeMillis() - runtimeStart_grimms_fairy_tales_trigram) / 1000.0;
	   System.out.println("Running time for Denemeler Novel with 1-gram calculation: " + String.format("%.3f", runtime_grimms_fairy_tales_unigram_InSeconds) + " second");
	   System.out.println("Running time for Denemeler Novel with 2-gram calculation: " + String.format("%.3f", runtime_grimms_fairy_tales_bigram_InSeconds) + " second");
	   System.out.println("Running time for Denemeler Novel with 3-gram calculation: " + String.format("%.3f", runtime_grimms_fairy_tales_trigram_InSeconds) + " second");
	   
	   
	   
	   System.out.println("Top Unigrams:\n------------------------------------------------------------");
	   long runtimeStart_unigram = System.currentTimeMillis();
	   printTopWords(allWords, 1, 20);
	   double runtime_unigram_InSeconds = (System.currentTimeMillis() - runtimeStart_unigram) / 1000.0;

	   
	   System.out.println("\n\nTop Bigrams:\n------------------------------------------------------------");
	   long runtimeStart_bigram = System.currentTimeMillis();
	   printTopWords(allWords, 2, 20);
	   double runtime_bigram_InSeconds = (System.currentTimeMillis() - runtimeStart_bigram) / 1000.0;

	   
	   System.out.println("\n\nTop Trigrams:\n------------------------------------------------------------");
	   long runtimeStart_trigram = System.currentTimeMillis();
	   printTopWords(allWords, 3, 20);
	   double runtime_trigram_InSeconds = (System.currentTimeMillis() - runtimeStart_trigram) / 1000.0;
	   double totalRuntime_InSeconds = (System.currentTimeMillis() - totalRuntime_start) / 1000.0;
	   
	   
	   System.out.println("\n\nTotal runtime measures:\n------------------------------------------------------------");
	   System.out.println("Number of words in the corpus: " + wordNum);
	   System.out.println("\nTime for reading and processing text files then splitting sentences into words : " + String.format("%.3f", runtime_FileReading_InSeconds) + " second");
	   System.out.println("Running time for combined corpus with 1-gram calculation: " + String.format("%.3f", runtime_unigram_InSeconds) + " second");
	   System.out.println("Running time for combined corpus with 2-gram calculation: " + String.format("%.3f", runtime_bigram_InSeconds) + " second");
	   System.out.println("Running time for combined corpus with 3-gram calculation: " + String.format("%.3f", runtime_trigram_InSeconds) + " second");		
	   System.out.println("Total Runtime: : " + String.format("%.3f", totalRuntime_InSeconds) + " second");
	   
	  // Scanner in = new Scanner(System.in); //  .exe dosyası cmd'yi açıp hemen geri kapatmasın, beklemeye geçsin diye input alma eklendi.
	  // in.nextInt();
	  // in.close();
	}
}
	
