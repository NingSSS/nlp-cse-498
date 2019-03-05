import java.io.*;
import java.util.Set;
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.AbstractMap.*;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashSet;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.List;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
//import wordcont.WordEntity;
import java.util.Scanner;
import java.io.*;
import java.util.*;
//import java.util.Map.Entry;


import Jama.Matrix;

class HMM {
	/* Section for variables regarding the data */
	
	//
	private ArrayList<Sentence> labeled_corpus;
	
	//
	private ArrayList<Sentence> unlabeled_corpus;

	// number of pos tags
	static int num_postags;
	
	// mapping POS tags in String to their indices
	private static Hashtable<String, Integer> pos_tags= new Hashtable<String, Integer>();

	//Hashtable<String,Integer> myword;
	private static Hashtable<String,Integer> myword =new Hashtable<String, Integer>();
	
	// inverse of pos_tags: mapping POS tag indices to their String format
	//Hashtable<Integer, String> inv_pos_tags;
	private static Hashtable<Integer,String> inv_pos_tags =new Hashtable<Integer, String>();
	// vocabulary size
	static int num_words;

	Hashtable<String, Integer> vocabulary;

	private int max_sentence_length;
	
	/* Section for variables in HMM */
	
	// transition matrix
	private Matrix A;

	// emission matrix
	private Matrix B;

	// prior of pos tags
	private Matrix pi;

	private Matrix A_mle;

	private Matrix B_mle;

	private Matrix pi_mle;

	// store the scaled alpha and beta
	private Matrix forwardalpha;
	
	private Matrix backwardbeta;

	private double[][][] xi;
	private double[][] gamma;
	private double[][] aijNum;
	private double[] aijDen;
	private Matrix aup;
	private Matrix adown;
	private Matrix bup;
	private Matrix bdown;
	private Matrix piup;
	private double[][] alpha;
	private double[][] beta;

	// scales to prevent alpha and beta from underflowing
	private Matrix scales;

	// logged v for Viterbi
	private Matrix v;
	private Matrix back_pointer;
	private Matrix pred_seq;
	
	// \xi_t(i): expected frequency of pos tag i at position t. Use as an accumulator.
	//private Matrix gamma;
	
	// \xi_t(i, j): expected frequency of transiting from pos tag i to j at position t.  Use as an accumulator.
	private Matrix digamma;
	
	// \xi_t(i,w): expected frequency of pos tag i emits word w.
	private Matrix gamma_w;

	// \xi_0(i): expected frequency of pos tag i at position 0.
	private Matrix gamma_0;
	
	/* Section of parameters for running the algorithms */

	// smoothing epsilon for the B matrix (since there are likely to be unseen words in the training corpus)
	// preventing B(j, o) from being 0
	private double smoothing_eps = 0.000001;

	// number of iterations of EM
	private int max_iters = 10;

	// \mu: a value in [0,1] to balance estimations from MLE and EM
	// \mu=1: totally supervised and \mu = 0: use MLE to start but then use EM totally.
	private double mu =0.5;
	
	/* Section of variables monitoring training */
	
	// record the changes in log likelihood during EM
	private double[] log_likelihood = new double[max_iters];
	
	/**
	 * Constructor with input corpora.
	 * Set up the basic statistics of the corpora.
	 */
	public HMM(ArrayList<Sentence> _labeled_corpus, ArrayList<Sentence> _unlabeled_corpus){
	//public HMM(ArrayList<Sentence> _labeled_corpus)  {
	}

	/**
	 * Set the semi-supervised parameter \mu
	 */
	public void setMu(double _mu) {
		if (_mu < 0) {
			this.mu = 0.0;
		} else if (_mu > 1) {
			this.mu = 1.0;
		}
		this.mu = _mu;
	}

	/**
	 * Create HMM variables.
	 */
	public void prepareMatrices() {
		A= new Matrix(num_postags,num_postags,0);
		B= new Matrix(num_postags,num_words,0);
		pi= new Matrix(1,num_postags,0);
		A_mle= new Matrix(num_postags,num_postags,0);
		B_mle= new Matrix(num_postags,num_words,0);
		pi_mle= new Matrix(1,num_postags,0);
	}



	/** 
	 *  MLE A, B and pi on a labeled corpus
	 *  used as initialization of the parameters.
	 */
	public void mle(ArrayList<Sentence> labeled_corpus) {

		double[][] Arr = new double[num_postags][num_postags];
		//System.out.println(num_words);
		for (int i = 0; i < labeled_corpus.size(); i++) {
			Sentence eachsent = null;
			eachsent = labeled_corpus.get(i);
			for (int j = 0; j < eachsent.length() - 1; j++) {
				int row = pos_tags.get(eachsent.getWordAt(j).getPosTag());
				int column = pos_tags.get(eachsent.getWordAt(j + 1).getPosTag());
				//if (eachsent.getWordAt(j).getPosTag()!=".")
				Arr[row][column] += 1.0;
			}
		}
		for (int i = 0; i < num_postags; i++) {
			for (int j = 0; j < num_postags; j++) {
				A.set(i, j, Arr[i][j]);
			}
		}


		double[][] Brr = new double[num_postags][num_words];
		for (int i = 0; i < labeled_corpus.size(); i++) {
			Sentence eachsent = null;
			eachsent = labeled_corpus.get(i);
			for (int j = 0; j < eachsent.length(); j++) {
				int row = pos_tags.get(eachsent.getWordAt(j).getPosTag());
				int column = myword.get(eachsent.getWordAt(j).getLemme());
				//if (eachsent.getWordAt(j).getPosTag()!=".")
				Brr[row][column] += 1.0;
			}
		}
		//System.out.println("start");
		for (int i = 0; i < num_postags; i++) {
			for (int j = 0; j < num_words; j++) {
				B.set(i, j, Brr[i][j]);
			}
		}


		double[] pirr = new double[num_postags];
		for (int i = 0; i < labeled_corpus.size(); i++) {
			Sentence eachsent = null;
			eachsent = labeled_corpus.get(i);
			int column = pos_tags.get(eachsent.getWordAt(0).getPosTag());
			pirr[column] += 1;
		}
		for (int i = 0; i < num_postags; i++) {
			pi.set(0, i, pirr[i]);
		}


		//double Aprobsum =0.0;
		double ant=0.000001;
		for(int i=0; i<num_postags;i++){
			double Aprobsum1 =0.0;
			for(int j=0; j<num_postags;j++){
				Aprobsum1+= A.get(i,j);
			}
			for(int j=0; j<num_postags;j++){
				A.set(i,j,(A.get(i,j)+ant)/(Aprobsum1+ant*num_postags));
				A_mle.set(i,j,A.get(i,j));
			}
		}

		//double Bprobsum =0.0;
		for(int i=0; i<num_postags;i++){
			double Bprobsum1 =0.0;
			for(int j=0; j<num_words;j++){
				Bprobsum1+= B.get(i,j);
			}
			//System.out.println(Bprobsum);
			for(int j=0; j<num_words;j++){
				B.set(i,j,(B.get(i,j)+ant)/(Bprobsum1+ant*num_postags));
				B_mle.set(i,j,B.get(i,j));
			}

		}

		//System.out.println(B.get(10,10));

		double pisum1=0.0;
		for(int i=0; i<num_postags; i++){
			pisum1+=pi.get(0,i);
		}

		for(int i=0; i<num_postags; i++){
			pi.set(0,i,(pi.get(0,i)+ant)/(pisum1+ant*num_postags));
			pi_mle.set(0,i,pi.get(0,i));
		}

//		System.out.println(A_mle.get(1,1));
//		System.out.println(B_mle.get(1,1));
//		System.out.println(pi_mle.get(0,1));

	}


	/**
	 * Main EM algorithm. 
	 */
	public double em(ArrayList<Sentence> unlabeled_corpus) throws IOException {
		double gamma1[][][] = new double[unlabeled_corpus.size()][][];
		double forw = 0.0;
		double bacw = 0.0;

		aup = new Matrix(num_postags, num_postags, 0);
		adown = new Matrix(1, num_postags, 0);
		bup = new Matrix(num_postags, num_words, 0);
		bdown = new Matrix(1, num_postags, 0);
		piup = new Matrix(1,num_postags,0);
		for (int i = 0; i < unlabeled_corpus.size(); i++) {

			double[] scaling=new double[unlabeled_corpus.get(i).length()];
			double result1 = forward(unlabeled_corpus.get(i),scaling);
			forw += result1;
			double result2 = backward(unlabeled_corpus.get(i),scaling);
			bacw += result2;
			//System.out.println(result2);
			expectation(unlabeled_corpus.get(i));
			gamma1[i] = gamma;
		}
		//System.out.println(forw);
		//System.out.println(bacw);
		maximization1(unlabeled_corpus);

//		double ant=0.000001;
//		for(int i=0; i<num_postags;i++){
//			double Aprobsum1 =0.0;
//			for(int j=0; j<num_postags;j++){
//				Aprobsum1+= A.get(i,j);
//			}
//			for(int j=0; j<num_postags;j++){
//				A.set(i,j,(A.get(i,j)+ant)/(Aprobsum1+ant*num_postags));
//			}
//		}
//
//		//double Bprobsum =0.0;
//		for(int i=0; i<num_postags;i++){
//			double Bprobsum1 =0.0;
//			for(int j=0; j<num_words;j++){
//				Bprobsum1+= B.get(i,j);
//			}
//			//System.out.println(Bprobsum);
//			for(int j=0; j<num_words;j++){
//				B.set(i,j,(B.get(i,j)+ant)/(Bprobsum1+ant*num_postags));
//			}
//
//		}
//
//		//System.out.println(B.get(10,10));
//
//		double pisum1=0.0;
//		for(int i=0; i<num_postags; i++){
//			pisum1+=pi.get(0,i);
//		}
//
//		for(int i=0; i<num_postags; i++){
//			pi.set(0,i,(pi.get(0,i)+ant)/(pisum1+ant*num_postags));
//
//		}

		return forw;
	}
	
	/**
	 * Prediction
	 * Find the most likely pos tag for each word of the sentences in the unlabeled corpus.
	 */
	public void predict() {
	}
	
	/**
	 * Output prediction
	 */
	public void outputPredictions(String outFileName) throws IOException {
	}
	
	/**
	 * outputTrainingLog
	 */
	public void outputTrainingLog(String outFileName) throws IOException {
	}
	


	/**
	 * Maximization step of the EM (Baum-Welch) algorithm.
	 * Just reestimate A, B and pi using gamma and digamma
	 */
	//private void maximization() {
	//}

	/**
	 * Forward algorithm for one sentence
	 * s: the sentence
	 * alpha: forward probability matrix of shape (num_postags, max_sentence_length)

	 * return: log P(O|\lambda)
	 */

	private void scale(double[] scaling, double[][] alpha, int t) {
		double[] table = alpha[t];

		double sum = 0.0;
		for (int i = 0; i < table.length; i++) {
			sum += table[i];
		}

		scaling[t] = sum;
		for (int i = 0; i < table.length; i++) {
			table[i] /= sum;
		}
	}
	private double forward(Sentence s,double scaling[]) throws IOException {
		//System.out.println(s.getWordAt(0));
		ArrayList<Integer> indexOfWord = new ArrayList<Integer>(s.length());
		for(int i = 0; i <s.length(); i++){
			indexOfWord.add(i,myword.get(s.getWordAt(i).getLemme()));
		}
		int T = s.length();
		int M=num_postags;
		alpha = new double[T][M];
		double sum = 0.0;
		double prob = 0.0;
		// t = 0   用初始概率乘以相应观察矩阵的概率，保存到alpha的第一列中
		for(int j = 0; j < num_postags; j++){
			alpha[0][j] = pi.get(0,j) * B.get(j,indexOfWord.get(0));
			//alpha[j][0] = pi.get(0,j) * 1;

		}

		scale(scaling,alpha,0);

		for (int t = 1; t < T; t++) {
			double colomnsum =0;
			for (int j = 0; j < M; j++) {
				sum = 0.0;
				for (int i = 0; i < M; i++) {
					// 根据t-1时刻的值 乘以转移矩阵的概率 得到t时刻的概率，然后将所有隐藏状态的概率相加
					sum += alpha[t-1][i] * A.get(i,j);
				}
				// sum 乘以 观察矩阵的概率  保存到alpha[j][t] 数组中
				alpha[t][j] = sum * B.get(j,indexOfWord.get(t));
				//colomnsum+= alpha[j][t];
				//alpha[j][t] = sum * 1;
			}
			scale(scaling,alpha,t);
//			for(int n=0;n<M;n++){
//				if(t!=T-1) {
//					alpha[n][t] = alpha[n][t] / colomnsum;
//				}
//			}
		}

//		forwardalpha=new Matrix(T,M,0);
//		for (int i = 0; i < T; i++) {
//			for (int j = 0; j < M; j++) {
//				forwardalpha.set(i, j, alpha[i][j]);
//			}
//		}

		for(int j = 0; j < s.length(); j++){
			prob += Math.log(scaling[j]);
		}

		return prob;
	}

	/**
	 * Backward algorithm for one sentence
	 * 
	 * return: log P(O|\lambda)
	 */
	private double backward(Sentence s, double scaling[]) {
		ArrayList<Integer> indexOfWord = new ArrayList<Integer>(s.length());
		for(int i = 0; i <s.length(); i++){
			indexOfWord.add(i,myword.get(s.getWordAt(i).getLemme()));
		}
		int T = s.length();
		int M=num_postags;
		beta = new double[T][M];
		double sum = 0.0;
		double prob = 0.0;
		for(int j = 0; j < num_postags; j++){
			//beta[j][T] = pi.get(0,j) * B.get(j,myword.get(s.getWordAt(0).getLemme()));
			beta[T-1][j]=1.0/scaling[s.length()-1];
			//alpha[j][0] = pi.get(0,j) * 1;
		}
		for(int t=T-2; t>=0;t--) {
			double colomnsum = 0.0;
			for (int i = 0; i < M; i++) {
				sum = 0.0;
				for (int j = 0; j < M; j++) {
					sum += A.get(i, j) * B.get(j, indexOfWord.get(t+1)) * beta[t+1][j];
				}
				beta[t][i] = sum/scaling[t];
				//colomnsum += beta[i][t];
			}
//			if (t != 0) {
//			for (int n = 0; n < M; n++) {
//					beta[n][t] = beta[n][t] / colomnsum;
//				}
//			}
		}
		//System.out.println(beta[1][1]);
//		backwardbeta=new Matrix(T,M,0);
//		for (int i = 0; i < T; i++) {
//			for (int j = 0; j < M; j++) {
//				backwardbeta.set(i, j, beta[i][j]);
//			}
//		}
		for(int j = 0; j < M; j++){
			prob += beta[0][j]*pi.get(0,j)*B.get(j,myword.get(s.getWordAt(0).getLemme()));
			//prob += beta[j][0];
		}
		return Math.log(prob);
	}

	/**
	 * Viterbi algorithm for one sentence
	 * v are in log scale, A, B and pi are in the usual scale.
	 */
	private int[] viterbi(Sentence s) {
		ArrayList<Integer> indexOfWord = new ArrayList<Integer>(s.length());
		for(int i = 0; i <s.length(); i++){
			indexOfWord.add(i,myword.get(s.getWordAt(i).getLemme()));
		}
		double[][] V=new double[s.length()][num_postags];
		int[][] path = new int[num_postags][s.length()];

		for(int y=0; y<num_postags; y++){
			V[0][y]= Math.exp(Math.log(pi.get(0,y))+Math.log(B.get(y,indexOfWord.get(0))));
			path[y][0] = y;
		}

		for (int t=1; t<s.length();t++){
			int[][] newpath = new int[num_postags][s.length()];
			for(int y=0; y<num_postags; y++){
				double prob=-1;
				int state;
				for (int y0=0; y0<num_postags;y0++){
					double nprob=Math.exp(Math.log(V[t-1][y0])+Math.log(A.get(y0,y))+Math.log(B.get(y,indexOfWord.get(t))));
					if(nprob>prob){
						prob=nprob;
						state=y0;
						V[t][y]=prob;
						System.arraycopy(path[state],0,newpath[y],0,t);
						newpath[y][t]=y;
					}
				}
			}
			path= newpath;
		}

		double prob =-1;
		int state=0;
		for(int y=0; y<num_postags; y++){
			if(V[s.length()-1][y]>prob){
				prob=V[s.length()-1][y];
				state =y;
			}
		}

	return path[state];
	}

	/**
	 * Expectation step of the EM (Baum-Welch) algorithm for one sentence.
	 * \xi_t(i,j) and \xi_t(i) are computed for a sentence
	 */
	private void expectation(Sentence s) {
		ArrayList<Integer> indexOfWord = new ArrayList<Integer>(s.length());
		for(int i = 0; i <s.length(); i++){
			indexOfWord.add(i,myword.get(s.getWordAt(i).getLemme()));
		}
		if(s.length()>=2) {
		int n = s.length() - 1;
		xi = new double[n][num_postags][num_postags];
		//System.out.println(A.get(1,1));
		for (int t = 0; t < n; t++) {
			for (int i = 0; i < num_postags; i++) {
				for (int j = 0; j < num_postags; j++) {
					xi[t][i][j] = alpha[t][i] * A.get(i, j) * B.get(j, indexOfWord.get(t+1)) * beta[t+1][j];
				}
			}
		}
		}
		//System.out.println(xi.length);

		gamma = new double[xi.length+1][num_postags];

//		for (int t = 0; t < xi.length; t++) {
//			for (int i = 0; i < num_postags; i++) {
//				for (int j = 0; j < num_postags; j++) {
//					gamma[t][i] += xi[t][i][j];
//				}
//			}
//		}

//		for (int i = 0; i < num_postags; i++) {
//			for (int t = 0; t < s.length(); t++) {
//				double prob = forwardalpha.get(t, i) * backwardbeta.get(t, i);
//				gamma[t][i] = prob;
//			}
//		}

		for (int t = 0; t < xi.length; t++) {
			for (int i = 0; i < num_postags; i++) {
				for (int j = 0; j < num_postags; j++) {
					gamma[t][i] += xi[t][i][j];
					//gamma.set(t, i, gamma.get(t, i) + xi[t][i][j]);
				}
			}
		}

		int n1 = xi.length-1;

		//System.out.println(" x+"+xi.length);
		for (int j = 0; j < num_postags; j++) {
			for (int i = 0; i < num_postags; i++) {
				gamma[xi.length][j] += xi[n1][i][j];
				//gamma.set(xi.length, j, gamma.get(xi.length, j) + xi[n1][i][j]);
			}
		}


		for (int i = 0; i < num_postags; i++) {
			double aDownSum = 0;
			for (int j = 0; j < num_postags; j++) {
				double aUpSum = 0;
				for (int t = 0; t < s.length() - 1; t++) {
					aUpSum += xi[t][i][j];
					aDownSum += xi[t][i][j];
				}
				aup.set(i, j, aup.get(i, j) + aUpSum);
			}
			adown.set(0, i, adown.get(0, i) + aDownSum);
		}

		for (int i = 0; i < num_postags; i++) {
			double bDownSum = 0;
			for (int t = 0; t < s.length(); t++) {
				Word w = s.getWordAt(t);
				String lemme = w.getLemme();
				int index = myword.get(lemme);
				bup.set(i, index, bup.get(i, index) + gamma[t][i]);

				bDownSum += gamma[t][i];
			}
			bdown.set(0, i, bdown.get(0, i) + bDownSum);
		}

		for(int i=0; i<num_postags;i++){
			piup.set(0,i,piup.get(0,i)+gamma[0][i]);
		}
	}



	private void maximization1(ArrayList<Sentence> unlabeled_corpus) {
		for (int i = 0; i < num_postags; i++) {
			for (int j = 0; j < num_postags; j++) {
				double prob = 1.0 * aup.get(i, j) / adown.get(0, i);
				A.set(i, j, prob*(1-mu)+A_mle.get(i,j)*mu);
			}
			for (int t = 0; t < num_words; t++) {
				double prob = 1.0 * bup.get(i, t) / bdown.get(0, i);
				B.set(i, t, prob*(1-mu)+B_mle.get(i,t)*mu);
			}
			//System.out.println(piup.get(0,i));
			pi.set(0,i,piup.get(0,i)/unlabeled_corpus.size()*(1-mu)+pi_mle.get(0,i)*mu);
		}
	}

//	private void maximization(double[][][] gamma1,ArrayList<Sentence> a) {
//		A= new Matrix(num_postags,num_postags,0);
//		B= new Matrix(num_postags,num_words,0);
//		pi= new Matrix(1,num_postags,0);
//
//			for (int i = 0; i < num_postags; i++) {
//
//				for (int j = 0; j < num_postags; j++) {
//					A.set(i, j, aijNum[i][j] / aijDen[i]);
//				}
//			}
//
//		for (int j = 0; j < a.size(); j++) {
//			for (int i = 0; i < num_postags; i++) {
//
//				pi.set(0,i,pi.get(0,i)+gamma1[j][0][i]);
//
//				//pi.set(0,i,pi.get(0,i)+gamma[j][0][i]);
//			}
//		}
//
//		for (int i = 0; i < num_postags; i++) {
//			//hmm.pi[i] /= sequences.length;
//			pi.set(0,i,pi.get(0,i)/a.size());
//		}
//
//		/*
//		 * emission probability computation
//		 */
//		for (int i = 0; i < num_postags; i++) {
//			double sum = 0.0;
//
//			for (int j = 0; j < a.size(); j++) {
//				Sentence o = a.get(j);
//				for (int t = 0; t < o.length(); t++) {
//					//hmm.b[i][o[t]] += gamma[j][t][i];
//					B.set(i,myword.get(o.getWordAt(t).getLemme()),B.get(i,myword.get(o.getWordAt(t).getLemme()))+gamma1[j][t][i]);
//					sum += gamma1[j][t][i];
//				}
//			}
//
//			for (int j = 0; j < num_words; j++) {
//				//hmm.b[i][j] /= sum;
//				B.set(i,j,B.get(i,j)/sum);
//			}
//		}
//
//	}

	public static void main(String[] args) throws IOException {
//		if (args.length < 2) {
//			System.out.println("Expecting at least 2 parameters");
//			System.exit(0);
//		}long t1 = System.currentTimeMillis();
		long t1 = System.currentTimeMillis();
		String labeledFileName = "./data/p1/train.txt";
		String unlabeledFileName = "./data/p1/test.txt";
		String concatenatedFileName = "./data/p3/concatenated.txt";
		//String predictionFileName = args[2];

		String trainingLogFileName = null;
		double ant = 1;
		if (args.length > 3) {
			trainingLogFileName = args[3];
		}

		double mu = 0.5;

//		if (args.length > 4) {
//			mu = Double.parseDouble(args[4]);
//		}
		// read in labeled corpus
		FileHandler fh = new FileHandler();

		ArrayList<String> tags = new ArrayList();
		ArrayList<String> words = new ArrayList();
		ArrayList<String> tagslarge = new ArrayList();
		ArrayList<String> wordslarge = new ArrayList();
		ArrayList<Sentence> labeled_corpus = fh.readTaggedSentences(labeledFileName);
		ArrayList<Sentence> unlabeled_corpus = fh.readTaggedSentences(unlabeledFileName);
		ArrayList<Sentence> unlabeledlarge_corpus = fh.readTaggedSentences(concatenatedFileName);

		HMM model = new HMM(labeled_corpus, unlabeled_corpus);
		HMM modellarge = new HMM(labeled_corpus,unlabeledlarge_corpus);


		//model.em(unlabeled_corpus);
		//model.predict();

		for (int i = 0; i < labeled_corpus.size(); i++) {
			Sentence eachsent = null;
			eachsent = labeled_corpus.get(i);
			for (int j = 0; j < eachsent.length(); j++) {
				words.add(eachsent.getWordAt(j).getLemme());
				tags.add(eachsent.getWordAt(j).getPosTag());
			}
		}

		for (int i = 0; i < unlabeled_corpus.size(); i++) {
			Sentence eachsent = null;
			eachsent = unlabeled_corpus.get(i);
			for (int j = 0; j < eachsent.length(); j++) {
				words.add(eachsent.getWordAt(j).getLemme());
				//tags.add(eachsent.getWordAt(j).getPosTag());
			}
		}

		LinkedHashSet<String> set = new LinkedHashSet<String>(tags.size());
		set.addAll(tags);
		tags.clear();
		tags.addAll(set);

		LinkedHashSet<String> setone = new LinkedHashSet<String>(words.size());
		setone.addAll(words);
		words.clear();
		words.addAll(setone);

		num_postags = tags.size();
		num_words = words.size();
		System.out.println(num_postags);
		System.out.println(num_words);
		model.prepareMatrices();

		//Map<String, Integer> pos_tags = new Hashtable<String, Integer>();
		//Map<String, Integer> myword = new Hashtable<String, Integer>();

		for (int i = 0; i < tags.size(); i++) {
			pos_tags.put(tags.get(i), i);
			inv_pos_tags.put(i, tags.get(i));
		}

		for (int i = 0; i < words.size(); i++) {
			myword.put(words.get(i), i);
		}

		model.mle(labeled_corpus);

		String fileName1 = "./results/p1/predictions.txt";
		String fileName2 = "./results/p1/log.txt";
		String fileName3 = "./results/p3/predictionssmall.txt";
		String fileName4 = "./results/p3/logsmall.txt";
		String fileName5 = "./results/p3/predictionslarge.txt";
		String fileName6 = "./results/p3/loglarge.txt";

		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName1));
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(fileName2));
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(fileName3));
		BufferedWriter bw3 = new BufferedWriter(new FileWriter(fileName4));
		BufferedWriter bw5 = new BufferedWriter(new FileWriter(fileName5));
		BufferedWriter bw6 = new BufferedWriter(new FileWriter(fileName6));
		double forwardsum = 0.0;
		double backwardsum = 0.0;

//		for (int i = 0; i < unlabeled_corpus.size(); i++) {
//			double[] scaling =new double[unlabeled_corpus.get(i).length()];
//
//			Sentence eachsent = null;
//			eachsent = unlabeled_corpus.get(i);
//			double forwardprob = model.forward(eachsent,scaling);
//			double backwardprob = model.backward(eachsent,scaling);
//			forwardsum += forwardprob;
//			backwardsum+=backwardprob;
//
//			int[] a = model.viterbi(eachsent);
//			for (int r = 0; r < a.length; r++) {
//				bw.write(eachsent.getWordAt(r).getLemme() + " " + inv_pos_tags.get(a[r]) + "\n");
//			}
//			bw.write("\n");
//			bw.flush();
//		}
//		bw1.write(Double.toString(forwardsum));
//		System.out.println(forwardsum);
//		System.out.println(backwardsum);
//		bw1.close();
//		bw.close();

		for (int nnn=0;nnn<31;nnn++) {
			double forw=model.em(unlabeled_corpus);
			bw3.write(Double.toString(forw)+"\n");
			bw3.flush();
		}
		bw3.close();


		for (int i = 0; i < unlabeled_corpus.size(); i++) {
			Sentence eachsent = null;
			eachsent = unlabeled_corpus.get(i);

			int[] a = model.viterbi(eachsent);
			for (int r = 0; r < a.length; r++) {
				bw2.write(eachsent.getWordAt(r).getLemme() + " " + inv_pos_tags.get(a[r]) + "\n");
			}
			//bw1.write(eachsent.getWordAt(r).getLemme()+" "+inv_pos_tags.get(a[r]));
			bw2.write("\n");
			bw2.flush();
			//System.out.println(pos_tags.(r));
		}

		bw2.close();


		for (int i = 0; i < labeled_corpus.size(); i++) {
			Sentence eachsent = null;
			eachsent = labeled_corpus.get(i);
			for (int j = 0; j < eachsent.length(); j++) {
				wordslarge.add(eachsent.getWordAt(j).getLemme());
				tagslarge.add(eachsent.getWordAt(j).getPosTag());
			}
		}

		for (int i = 0; i < unlabeledlarge_corpus.size(); i++) {
			Sentence eachsent = null;
			eachsent = unlabeledlarge_corpus.get(i);
			for (int j = 0; j < eachsent.length(); j++) {
				wordslarge.add(eachsent.getWordAt(j).getLemme());
				//tags.add(eachsent.getWordAt(j).getPosTag());
			}
		}

		LinkedHashSet<String> setlarge = new LinkedHashSet<String>(tags.size());
		setlarge.addAll(tagslarge);
		tagslarge.clear();
		tagslarge.addAll(setlarge);

		LinkedHashSet<String> setonelarge = new LinkedHashSet<String>(words.size());
		setonelarge.addAll(wordslarge);
		wordslarge.clear();
		wordslarge.addAll(setonelarge);


		num_postags = tagslarge.size();
		num_words = wordslarge.size();
		System.out.println(num_postags);
		System.out.println(num_words);
		modellarge.prepareMatrices();

		//Map<String, Integer> pos_tags = new Hashtable<String, Integer>();
		//Map<String, Integer> myword = new Hashtable<String, Integer>();

		for (int i = 0; i < tagslarge.size(); i++) {
			pos_tags.put(tagslarge.get(i), i);
			inv_pos_tags.put(i, tagslarge.get(i));
		}

		for (int i = 0; i < wordslarge.size(); i++) {
			myword.put(wordslarge.get(i), i);
		}

		modellarge.mle(labeled_corpus);


		for (int nnn=0;nnn<1;nnn++) {
			double forw=modellarge.em(unlabeledlarge_corpus);
			bw6.write(Double.toString(forw)+"\n");
			bw6.flush();
		}
		bw6.close();


		for (int i = 0; i < unlabeledlarge_corpus.size(); i++) {
			Sentence eachsent = null;
			eachsent = unlabeledlarge_corpus.get(i);

			int[] a = modellarge.viterbi(eachsent);
			for (int r = 0; r < a.length; r++) {
				bw5.write(eachsent.getWordAt(r).getLemme() + " " + inv_pos_tags.get(a[r]) + "\n");
			}
			//bw1.write(eachsent.getWordAt(r).getLemme()+" "+inv_pos_tags.get(a[r]));
			bw5.write("\n");
			bw5.flush();
			//System.out.println(pos_tags.(r));
		}

		bw5.close();


		
		if (trainingLogFileName != null) {
			model.outputTrainingLog(trainingLogFileName + "_" + String.format("%.1f", mu) + ".txt");
		}
		System.out.println("耗时：" + (System.currentTimeMillis() - t1) + "ms");
	}
}
