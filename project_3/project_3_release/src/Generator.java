/**
 * Generate sentences from a CFG
 * 
 * @author sihong
 *
 */

import java.io.*;
import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Generator {
	
	private Grammar grammar;

	/**
	 * Constructor: read the grammar.
	 */
	public Generator(String grammar_filename) {
		grammar = new Grammar(grammar_filename);
		RHS node=grammar.findProductions("ROOT").get(0);
		//System.out.println(node.toString());
		//System.out.println(grammar.findLHS(node.first()+" "+node.second()));
		//System.out.println(grammar.findLHS(node.first() + " " + node.second()).get(0));
		//System.out.println(grammar.findProductions("NP").get(0).first());
		//System.out.println(grammar.symbolType("floor"));
		//grammar.
		//grammar.printGrammarInfo();
		//System.out.println(grammar.);
	}



	/**
	 * Generate a number of sentences.
	 */
	public ArrayList<String> generate(int numSentences) {
		//ArrayList<String> sentence = new ArrayList<>();
		ArrayList<String> sentencesum = new ArrayList<>();

		for (int i = 0; i < numSentences; i++) {
			ArrayList<String> sentence = new ArrayList<>();
			Stack<String> nodestack = new Stack<>();
			Random ran = new Random();
			String node = "ROOT";
			nodestack.add(node);
			int sss =0;
			while (!nodestack.isEmpty()){
				node = nodestack.pop();
				//if (grammar.symbolType(node) == 2 && node != "ROOT" && grammar.symbolType(sentence.get(sentence.size()-2))

				if(grammar.findProductions(node) == null){
					//node = nodestack.pop();
					sentence.add(node);
				}else {
					//node = nodestack.pop();
					sentence.add("(" + node + " ");

					RHS rhs;

					if(grammar.findProductions(node).size()==1){
						rhs = grammar.findProductions(node).get(0);
					}else{
						if(grammar.findProductions(node).get(0).getProb() != grammar.findProductions(node).get(1).getProb()){
						double maxprob =0;
						int maxprobnum =0;
						rhs = grammar.findProductions(node).get(maxprobnum);
						for(int numforprob=0; numforprob<grammar.findProductions(node).size(); numforprob++){
							if(grammar.findProductions(node).get(numforprob).getProb()>maxprob) {
								maxprob = grammar.findProductions(node).get(numforprob).getProb();
								maxprobnum = numforprob;
							}
							rhs = grammar.findProductions(node).get(maxprobnum);
						}
					}else{
						int index = ran.nextInt(grammar.findProductions(node).size());
						rhs = grammar.findProductions(node).get(index);
						}
					}


//					if(node.equals("NP")){
//						rhs = grammar.findProductions(node).get(0);
//					}else{
//						int index = ran.nextInt(grammar.findProductions(node).size());
//						rhs = grammar.findProductions(node).get(index);
//					}

					if (rhs.second() != null) {
						nodestack.push(") ");
						nodestack.push(rhs.second());
						nodestack.push(rhs.first());
					} else {
						nodestack.push(") ");
						nodestack.push(rhs.first());
					}
				}
			}



			String r = "";
			for (int j = 0; j < sentence.size(); j++) {
				r = r + sentence.get(j);
			}
			sentencesum.add(r);
		}



		//System.out.println(sentence.get(0));

//		for(int i=1;i<3;i++){
//			if (grammar.symbolType(sentence.get(i-1))!=0){
//				double Maxprob = 0;
//				for(RHS rhs:grammar.findProductions(sentence.get(i-1))){
//					if(rhs.getProb()>Maxprob){
//						sentence.set(i,rhs.toString());
//					}
//				}
//			}
//			else{
//				break;}
//		}
//
//		System.out.println(sentence.get(0));
//		ArrayList<String> sentencesum = new ArrayList<String>();
//		String sum ="";
//		for(int i=0;i<sentence.size();i++){
//			sum = sum + " " + sentence.get(i);
//		}
//		if (sentencesum.size()>numSentences){
//			return sentencesum;
//		}
		return sentencesum;
	}
	
	public static void main(String[] args) throws IOException{
		// the first argument is the path to the grammar file.
		//args[0]="/Users/ningsong/Desktop/project_3_release/data/grammar.gr";
		Generator g = new Generator(args[0]);

		ArrayList<String> res = g.generate(1);
		//File writename = new File("./results/sentence.txt");
		//writename.createNewFile(); // 创建新文件
		//BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		for (String s : res) {
			//out.write(s+"\n");
			System.out.println(s);
		}
		//out.flush();
		//out.close();
	}
}
