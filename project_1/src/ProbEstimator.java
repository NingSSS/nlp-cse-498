import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.List;

//import edu.stanford.nlp.ling.CoreLabel;
//import edu.stanford.nlp.ling.HasWord;
//import edu.stanford.nlp.process.CoreLabelTokenFactory;
//import edu.stanford.nlp.process.DocumentPreprocessor;
//import edu.stanford.nlp.process.PTBTokenizer;
////import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;
//import java.util.Set;
//import java.util.StringTokenizer;
import java.util.TreeMap;
//import java.util.TreeSet;
////import wordcont.WordEntity;
//import java.util.Scanner;
import java.io.*;
import java.util.*;
//import java.util.Map.Entry;

//java -Xms32m -Xmx800m class ProbEstimator;

import org.apache.commons.math3.stat.regression.SimpleRegression;


public class ProbEstimator {
    public static void main(String[] args) throws IOException {
        GTtable();
//        SimpleRegression regression = new SimpleRegression();
//        long t1 = System.currentTimeMillis();
//        String s;
//        String fileName1 = "/Users/ningsong/project_1_release/data/train_tokens.txt";
//        String fileName2 = "/Users/ningsong/project_1_release/data/Untitled.txt";
//        String fileName3 = "/Users/ningsong/project_1_release/data/Untitled1.txt";
//        String fileName4 = "/Users/ningsong/project_1_release/data/Untitled2.txt";
//        String fileName5 = "/Users/ningsong/project_1_release/data/Untitled5.txt";
//        String fileName6 = "/Users/ningsong/project_1_release/data/Untitled6.txt";
//        //String fake = "/Users/ningsong/project_1_release/data/test_tokens_fake.txt";
//            BufferedReader br = new BufferedReader(new FileReader(fileName1));
//            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName2));
//            BufferedWriter bwtwo = new BufferedWriter(new FileWriter(fileName3));
//            BufferedWriter bwfour = new BufferedWriter(new FileWriter(fileName4));
//            BufferedWriter bwfive = new BufferedWriter(new FileWriter(fileName5));
//            BufferedWriter bwsix = new BufferedWriter(new FileWriter(fileName6));
//            //BufferedWriter bwfake = new BufferedWriter(new FileWriter(fake));
//            StringBuffer sb = new StringBuffer();
//            //StringBuffer sc = new StringBuffer();
//            //将文件内容存入StringBuffer中
//
////            Map<String, String> map = new HashMap<String, String>();
////            String readLine = null;
////            int i = 0;
////
////            while ((readLine = br.readLine()) != null) {
////                // 每次读取一行数据，与 map 进行比较，如果该行数据 map 中没有，就保存到 map 集合中
////                if (!map.containsValue(readLine)) {
////                    map.put("key" + i, readLine);
////                    i++;
////                }
////            }
//
//            while ((s = br.readLine()) != null) {
//                sb.append(s + System.lineSeparator());
//                //+System.lineSeparator()
//            }
//            //System.out.println(sb);
//            String str = sb.toString().toLowerCase();
//            //System.out.println(str);
//            //分隔字符串并存入数组
//            String[] elements = str.split("\n");
//            String[] elementstwo = new String[elements.length];
//
//
//            //ystem.out.println("hehe"+list.size()+"haha");
////            for(int i=0;i<a.size();i++)
////                System.out.println(a.get(i));
//
//
//            //String[] elementstwo = str.split("[a-zA-z0-9]");
//            for (int i = 1; i < elements.length; i++) {
//                elementstwo[i] = elements[i - 1] + " " + elements[i];
//            }
//            //int a=elements.length;
//            //System.out.println(elements.length);
//            //int[][] numthree;             //定义一个float类型的2维数组
//            //int [][]numthree=new int[10][10];
//            //System.out.println(numthree);
//
////            for (int i = 0; i < 10; i++){
////                System.out.println(elements[i]);
////            }
//            //System.out.println(elements[]);
//            int count = 0;
//            int counttwo = 0;
//            Map<String, Integer> myTreeMap = new TreeMap<String, Integer>();
//            Map<String, Integer> myTreeMaptwo = new TreeMap<String, Integer>();
//            Map<String, Integer> myTreeMapthree = new TreeMap<String, Integer>();
//            //遍历数组将其存入Map<String, Integer>中
//            for (int i = 0; i < elements.length; i++) {
//                if (myTreeMap.containsKey(elements[i])) {
//                    count = myTreeMap.get(elements[i]);
//                    myTreeMap.put(elements[i], count + 1);
//                } else {
//                    myTreeMap.put(elements[i], 1);
//                }
//            }
//            for (int i = 1; i < elements.length; i++) {
//                if (myTreeMaptwo.containsKey(elementstwo[i])) {
//                    counttwo = myTreeMaptwo.get(elementstwo[i]);
//                    myTreeMaptwo.put(elementstwo[i], counttwo + 1);
//                } else {
//                    myTreeMaptwo.put(elementstwo[i], 1);
//                }
//            }
//
//
//            //System.out.println(elementstwo.length);
//
//            ArrayList<String> a = new ArrayList();
//            for (int i = 0; i < elements.length; i++) {
//                if (!a.contains(elements[i]))
//                    a.add(elements[i]);
//            }
//
//            for (int i = 0; i < a.size(); i++) {
//                myTreeMapthree.put(a.get(i), i);
//            }
//            //System.out.println(a.size());
//
//            Integer[][] numthree = new Integer[a.size()][a.size()];
//            float[][] Laplace = new float[a.size()][a.size()];
//            for (int i = 0; i < a.size(); i++) {
//                for (int j = 0; j < a.size(); j++) {
//                    numthree[i][j] = 0;
//                    Laplace[i][j] = 0;
//                }
//            }
//
//            for (int i = 0; i < elements.length - 1; i++) {
//                int row = myTreeMapthree.get(elements[i]);
//                int column = myTreeMapthree.get(elements[i + 1]);
//                numthree[row][column] += 1;
//            }
//
//            int V = myTreeMaptwo.size();
//            int N = elementstwo.length;
//            System.out.println(V+" "+N);
//            System.out.println(a.size());
//
////            double aaa=(numthree[1][1]+1)/(myTreeMap.get(a.get(1))+V);
////            System.out.println(myTreeMap.get(a.get(1))+V);
//
//            Map<Integer, Integer> myTreeMapfour = new TreeMap<Integer, Integer>();
//            for (int i = 0; i < a.size(); i++) {
//                for (int j = 0; j < a.size(); j++) {
//                    if (myTreeMapfour.containsKey(numthree[i][j])) {
//                        count = myTreeMapfour.get(numthree[i][j]);
//                        myTreeMapfour.put(numthree[i][j], count + 1);
//                    } else {
//                        myTreeMapfour.put(numthree[i][j], 1);
//                    }
//                }
//            }
////            List<Map.Entry<Integer, Integer>> listfour = new ArrayList<Map.Entry<Integer, Integer>>(myTreeMapfour.entrySet());
////            System.out.println(listfour);
//            //double data[][] = new double[myTreeMapfour.size()][2];
////            for (int i = 0; i < myTreeMapfour.size()-1; i++) {
////                if (myTreeMapfour.get(i) != null) {
////                    data[i][0] = Math.log(i);
////                    data[i][1] = Math.log(myTreeMapfour.get(i));
////                } else {
////                    data[i][0] = Math.log(i);
////                    data[i][1] = 0;
////                }
////            }
//            int max=0;
//            for (int key : myTreeMapfour.keySet()) {
//                if(key!=0) {
//                    regression.addData(Math.log(key),Math.log(myTreeMapfour.get(key)));
//                        if(key>max) {
//                            max = key;
//                        }
//                    }
//                }
//
//
////            for(int i=0;i<10;i++){
////                for(int j=0;j<2;j++){
////                    System.out.println(data[i][j]);
////                    //System.out.println(numthree[i][j]);
////                }
////            }
//
//            //regression.addData(data);
//            //System.out.println(regression.predict(Math.log(10)));
//
//            Map<Integer, Double> myTreeMapfive = new TreeMap<Integer, Double>();
//            for (int i = 0; i < max+1; i++) {
//                if (myTreeMapfour.containsKey(i)) {
//                    double countfive = (double)myTreeMapfour.get(i);
//                    myTreeMapfive.put(i, countfive);
//                } else {
//                    myTreeMapfive.put(i, Math.pow(Math.E,regression.predict(Math.log(i))));
//                }
//            }
//
//            List<Map.Entry<Integer, Double>> listfive = new ArrayList<Map.Entry<Integer, Double>>(myTreeMapfive.entrySet());
//            //List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
//            int numfive = 1;
//            //将结果写入文件
//            for (Map.Entry<Integer, Double> map : listfive) {
//                //if (num <= 10) {
//                //bw.write("出现次数第" + num + "的单词为：" + map.getKey() + "，出现频率为" + map.getValue() + "次");
//                bwfive.write(Math.log(map.getKey())+" "+Math.log(map.getValue()));
//                //bwfive.write(map.getKey()+" "+map.getValue());
//                bwfive.newLine();
//                //System.out.println(map.getKey() + ":" + map.getValue());
//                numfive++;
//                //} else break;
//            }
//
//            System.out.println(myTreeMapfive.get(0));
//
//
//            Map<Integer, Double> myTreeMapsix = new TreeMap<Integer, Double>();
//            for (int i = 0; i < myTreeMapfive.size()-1; i++) {
//                    double countsixc = (double)myTreeMapfive.get(i);
//                    double countsixcadd1 = (double)myTreeMapfive.get(i+1);
//
//                    myTreeMapsix.put(i, Math.log((i+1)*countsixcadd1/countsixc));
//            }
//
//            List<Map.Entry<Integer, Double>> listsix = new ArrayList<Map.Entry<Integer, Double>>(myTreeMapsix.entrySet());
//            //List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
//            int numsix = 1;
//            //将结果写入文件
//            for (Map.Entry<Integer, Double> map : listsix) {
//                //if (num <= 10) {
//                //bw.write("出现次数第" + num + "的单词为：" + map.getKey() + "，出现频率为" + map.getValue() + "次");
//                //bwfive.write(Math.log(map.getKey())+" "+Math.log(map.getValue()));
//                bwsix.write(map.getKey()+" "+map.getValue()/N);
//                bwsix.newLine();
//                //System.out.println(map.getKey() + ":" + map.getValue());
//                numsix++;
//                //} else break;
//            }
//            int resultgt;
//            //String gtstr=args[0]+" "+args[1];
//            //String gtstr1=args[0]+" "+args[2];
////            if(myTreeMaptwo.containsKey(gtstr)&&myTreeMaptwo.containsKey(gtstr1)){
////                if(myTreeMapsix.get(myTreeMaptwo.get(gtstr))>myTreeMapsix.get(myTreeMaptwo.get(gtstr1))){
////                    resultgt=1;
////                }else{
////                    resultgt=0;
////                }
////            }else if(myTreeMaptwo.containsKey(gtstr)){
////                resultgt=1;
////            }else{
////                resultgt=0;
////            }
//
//            System.out.println(numsix);
//
//            //Laplace smoothing:
//            for (int i=0;i<a.size();i++){
//                for(int j=0;j<a.size();j++){
//                    Laplace[i][j]=(float)(numthree[i][j]+1)/(float)(myTreeMap.get(a.get(i))+V);
//                }
//            }
////            int result;
////            if (Laplace[myTreeMapthree.get(args[0])][myTreeMapthree.get(args[1])]>Laplace[myTreeMapthree.get(args[0])][myTreeMapthree.get(args[1])]){
////                result=1;
////            }
////            else{
////                result=0;
////            }
//
////            File file = new File("/Users/ningsong/project_1_release/data/Laplace.txt");  //存放数组数据的文件
////
////            FileWriter out = new FileWriter(file);  //文件写入流
////
////            //将数组中的数据写入到文件中。每行各数据之间TAB间隔
////            for(int i=0;i<a.size();i++){
////                for(int j=0;j<a.size();j++){
////                    out.write(Laplace[i][j]+"\t");
////                }
////                //out.write("\r\n");
////                out.write("\n");
////            }
////            out.close();
//
//
//
//            //System.out.println("number of N:"+numfive);
//
//            //double pred_Nc = regression.predict(Math.log(c));
//
//            //System.out.println(myTreeMapfour.size());
//
////            for (int i=0; i<myTreeMapfour.size()-1; i++){
////                for(int j=0;j<2; j++){
////                    System.out.println(data[i][j]);
////                }
////            }
//
////            for(int i=0;i<10;i++){
////                    System.out.println(myTreeMapfour.get(i));
////            }
//
//
//
////            for (int i=0; i<10; i++){
////                for(int j=0;j<10; j++){
////                    System.out.println(numthree[i][j]);
////                }
////            }
//
//            //int [][]numthree=new int[elements.length][elements.length];
//            //ArrayList<String> a = new ArrayList<String>();
//
//            //System.out.println(myTreeMaptwo);
//
//            System.out.println("单词统计的结果请见当前目录result.txt和result1.txt");
//            //将map.entrySet()转换成list
//            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(myTreeMap.entrySet());
//            //List<Map.Entry<String, Integer>> list1 = new ArrayList<Map.Entry<String, Integer>>(myTreeMap.entrySet());
//            //String[] elementsaaa=new String[myTreeMap.size()];
//            //String[]elementsaaa = myTreeMap.keySet();
//
//
//            //List<Integer> aa = (List<Integer>)map.getkey("a");
//            //List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
//            int num = 1;
//            //将结果写入文件
//            //String [][]numthree=new String[num+1][num+1];
//            for (Map.Entry<String, Integer> map : list) {
//                //if (num <= 10) {
//                    //bw.write("出现次数第" + num + "的单词为：" + map.getKey() + "，出现频率为" + map.getValue() + "次");
//                    bw.write(map.getKey()+":"+map.getValue());
//                    //numthree[0][] = map.getKey();
//                    //bw.write(map.getKey());
//                    bw.newLine();
//                    //System.out.println(map.getKey() + ":" + map.getValue());
//                    num++;
//                //} else break;
//            }
//            //System.out.println(list);
//
////            int [][]numthree=new int[num][num];
////            for (int i=0;i<elements.length;i++){
////                if (myTreeMaptwo.containsKey(elements[i]+" "+elements[i+1])){
////                    myTreeMaptwo.get(elementstwo[i]+" "+elementstwo[i+1])!=null
////                    numthree[i]
////                }
////            }
////            for (int i = 0; i < elements.length; i++) {
////                if (myTreeMapthree.containsKey(elements[i]+" "+elements[i+1])) {
////                    count = myTreeMapthree.get(elements[i]);
////                    myTreeMap.put(elements[i], count + 1);
////
////                } else {
////                    myTreeMap.put(elements[i], 1);
////                }
////            }
//
////            numthree[0][0]="0";
////            for (int i=1;i<num+1;i++){
////                numthree[0][i]=
//////                for(int j=0;j<num+1;j++){
//////
//////                }
////            }
//
//            //System.out.println(numthree);
//            System.out.println("number of N:"+num);
//
//            List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
//            //List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
//            int numtwo = 1;
//            //将结果写入文件
//            for (Map.Entry<String, Integer> map : listtwo) {
//                //if (num <= 10) {
//                //bw.write("出现次数第" + num + "的单词为：" + map.getKey() + "，出现频率为" + map.getValue() + "次");
//                bwtwo.write(map.getKey()+":"+map.getValue());
//                bwtwo.newLine();
//                //System.out.println(map.getKey() + ":" + map.getValue());
//                numtwo++;
//                //} else break;
//            }
//            System.out.println("number of N:"+numtwo);
//
//            List<Map.Entry<Integer, Integer>> listfour = new ArrayList<Map.Entry<Integer, Integer>>(myTreeMapfour.entrySet());
//            //List<Map.Entry<String, Integer>> list1 = new ArrayList<Map.Entry<String, Integer>>(myTreeMap.entrySet());
//            //String[] elementsaaa=new String[myTreeMap.size()];
//            //String[]elementsaaa = myTreeMap.keySet();
//
//
//            //List<Integer> aa = (List<Integer>)map.getkey("a");
//            //List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
//            int numfour = 1;
//            //将结果写入文件
//            //String [][]numthree=new String[num+1][num+1];
//            for (Map.Entry<Integer, Integer> map : listfour) {
//                //if (num <= 10) {
//                //bw.write("出现次数第" + num + "的单词为：" + map.getKey() + "，出现频率为" + map.getValue() + "次");
//                //bwfour.write(Math.log(map.getKey())+" "+Math.log(map.getValue()));
//                bwfour.write(map.getKey()+" "+map.getValue());
//                //numthree[0][] = map.getKey();
//                //bw.write(map.getKey());
//                bwfour.newLine();
//                //System.out.println(map.getKey() + ":" + map.getValue());
//                num++;
//                //} else break;
//            }
//
//            bw.write("耗时：" + (System.currentTimeMillis() - t1) + "ms");
//            bwtwo.write("耗时：" + (System.currentTimeMillis() - t1) + "ms");
//
//            br.close();
//            bw.close();
//            bwtwo.close();
//            bwfour.close();
//            bwfive.close();
//
//            System.out.println("耗时：" + (System.currentTimeMillis() - t1) + "ms");

    }
    public static int[][] Laplaceporb() throws IOException{
        String[] elements=ProbEstimator.elements();
        String[] elementstwo=ProbEstimator.elementstwo();
        Map<String, Integer> myTreeMap = new TreeMap<String, Integer>();
        Map<String, Integer> myTreeMaptwo = new TreeMap<String, Integer>();
        Map<String, Integer> myTreeMapthree = new TreeMap<String, Integer>();
        int counttwo = 0;
        int count=0;
        for (int i = 0; i < elements.length; i++) {
            if (myTreeMap.containsKey(elements[i])) {
                count = myTreeMap.get(elements[i]);
                myTreeMap.put(elements[i], count + 1);
            } else {
                myTreeMap.put(elements[i], 1);
            }
        }
        for (int i = 1; i < elementstwo.length; i++) {
            if (myTreeMaptwo.containsKey(elementstwo[i])) {
                counttwo = myTreeMaptwo.get(elementstwo[i]);
                myTreeMaptwo.put(elementstwo[i], counttwo + 1);
            } else {
                myTreeMaptwo.put(elementstwo[i], 1);
            }
        }

//        String fileName = "/Users/ningsong/project_1_release/data/bigrams.txt";
//        BufferedWriter bwtwo = new BufferedWriter(new FileWriter(fileName));
//        List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
//        //List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
//        int numtwo = 1;
//        //将结果写入文件
//        for (Map.Entry<String, Integer> map : listtwo) {
//            //if (num <= 10) {
//            //bw.write("出现次数第" + num + "的单词为：" + map.getKey() + "，出现频率为" + map.getValue() + "次");
//            bwtwo.write(map.getKey()+":"+map.getValue());
//            bwtwo.newLine();
//            //System.out.println(map.getKey() + ":" + map.getValue());
//            numtwo++;
//            //} else break;
//        }


        ArrayList<String> a = new ArrayList();
//        for (int i = 0; i < elements.length; i++) {
//            if (!a.contains(elements[i]))
//                a.add(elements[i]);
//        }

        for (int i = 0; i < elements.length; i++) {
            //if (!a.contains(elements[i]))
            a.add(elements[i]);
        }

        LinkedHashSet<String> set = new LinkedHashSet<String>(a.size());
        set.addAll(a);
        a.clear();
        a.addAll(set);


        for (int i = 0; i < a.size(); i++) {
            myTreeMapthree.put(a.get(i), i);
        }
        //System.out.println(a.size());

        int[][] numthree = new int[a.size()][a.size()];
//        float[][] Laplacea = new float[a.size()][a.size()];
//        for (int i = 0; i < a.size(); i++) {
//            for (int j = 0; j < a.size(); j++) {
//                //numthree[i][j] = 0;
//                Laplacea[i][j] = 0;
//            }
//        }

        for (int i = 0; i < elements.length - 1; i++) {
            int row = myTreeMapthree.get(elements[i]);
            int column = myTreeMapthree.get(elements[i + 1]);
            numthree[row][column] += 1;
        }

        int V = myTreeMaptwo.size();
        int N = elementstwo.length;
        //System.out.println(V+" "+N);
        //System.out.println(a.size());

//        //Laplace smoothing:
//        for (int i=0;i<a.size();i++){
//            for(int j=0;j<a.size();j++){
//                Laplacea[i][j]=(float)(numthree[i][j]+1)/(float)(myTreeMap.get(a.get(i))+V);
//            }
//        }
    return numthree;
    }
    public static float[][] GTtable() throws IOException{
        SimpleRegression regression = new SimpleRegression();
        String[] elements=ProbEstimator.elements();
        String[] elementstwo=ProbEstimator.elementstwo();

        //long t2 = System.currentTimeMillis();

        Map<String, Integer> myTreeMap = new TreeMap<String, Integer>();
        Map<String, Integer> myTreeMaptwo = new TreeMap<String, Integer>();
        Map<String, Integer> myTreeMapthree = new TreeMap<String, Integer>();
        int counttwo = 0;
        int count=0;
        for (int i = 0; i < elements.length; i++) {
            if (myTreeMap.containsKey(elements[i])) {
                count = myTreeMap.get(elements[i]);
                myTreeMap.put(elements[i], count + 1);
            } else {
                myTreeMap.put(elements[i], 1);
            }
        }
        for (int i = 1; i < elementstwo.length; i++) {
            if (myTreeMaptwo.containsKey(elementstwo[i])) {
                counttwo = myTreeMaptwo.get(elementstwo[i]);
                myTreeMaptwo.put(elementstwo[i], counttwo + 1);
            } else {
                myTreeMaptwo.put(elementstwo[i], 1);
            }
        }


        String fileName = "/Users/ningsong/project_1_release/results/bigrams.txt";
        String fileName1 = "/Users/ningsong/project_1_release/results/ff.txt";
        String fileName2 = "/Users/ningsong/project_1_release/results/GTTable.txt";
        BufferedWriter bwtwo = new BufferedWriter(new FileWriter(fileName));
        BufferedWriter bwfive = new BufferedWriter(new FileWriter(fileName1));
        BufferedWriter bwsix = new BufferedWriter(new FileWriter(fileName2));
        List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
        //List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
        int numtwo = 1;
        //将结果写入文件
        for (Map.Entry<String, Integer> map : listtwo) {
            //if (num <= 10) {
            //bw.write("出现次数第" + num + "的单词为：" + map.getKey() + "，出现频率为" + map.getValue() + "次");
            bwtwo.write(map.getKey()+":"+map.getValue());
            bwtwo.newLine();
            //System.out.println(map.getKey() + ":" + map.getValue());
            numtwo++;
            //} else break;
        }
        bwtwo.close();
        //System.out.println("耗时2：" + (System.currentTimeMillis() - t2) + "ms");
        ArrayList<String> a = new ArrayList();
        for (int i = 0; i < elements.length; i++) {
            //if (!a.contains(elements[i]))
                a.add(elements[i]);
        }

        LinkedHashSet<String> set = new LinkedHashSet<String>(a.size());
        set.addAll(a);
        a.clear();
        a.addAll(set);

        for (int i = 0; i < a.size(); i++) {
            myTreeMapthree.put(a.get(i), i);
        }
        //System.out.println(a.size());
        //System.out.println("耗时3：" + (System.currentTimeMillis() - t2) + "ms");
        int[][] numthree = new int[a.size()][a.size()];
        float[][] gt = new float[a.size()][a.size()];
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < a.size(); j++) {
                //numthree[i][j] = 0;
                gt[i][j] = 0;
            }
        }
        //System.out.println("耗时3：" + (System.currentTimeMillis() - t2) + "ms");
        for (int i = 0; i < elements.length - 1; i++) {
            int row = myTreeMapthree.get(elements[i]);
            int column = myTreeMapthree.get(elements[i + 1]);
            numthree[row][column] += 1;

        }

        //System.out.println("耗时3：" + (System.currentTimeMillis() - t2) + "ms");
        //System.out.println(elements.length);
        //System.out.println(numthree.length);
        //System.out.println(numthree[0].length);
//        for (int i = 0; i < elements.length - 1; i++){
//            for (int j = 0; j < elements.length - 1; j++){
//                System.out.println(numthree[i][j]);
//            }
//        }

        int V = myTreeMaptwo.size();
        int N = elementstwo.length;
        //System.out.println(V+" "+N);
        //System.out.println(a.size());

        int temp=0;
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < a.size(); j++) {
                if (numthree[i][j] > temp) {
                    temp = numthree[i][j];
                }
            }
        }
        int[] countc =new int[temp+1];
        Map<Integer, Integer> myTreeMapfour = new TreeMap<Integer, Integer>();
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < a.size(); j++) {
//                if (!myTreeMapfour.containsKey(numthree[i][j])) {
//                    myTreeMapfour.put(numthree[i][j], 1);
//                    //count = myTreeMapfour.get(numthree[i][j]);
//                    //myTreeMapfour.put(numthree[i][j], count + 1);
//                } else {
//                    //myTreeMapfour.put(numthree[i][j], 1);
//                    count = myTreeMapfour.get(numthree[i][j]);
//                    myTreeMapfour.put(numthree[i][j], count + 1);
//                }
                countc[numthree[i][j]]++;
            }
        }
        for(int i=0;i<countc.length;i++){
            if(countc[i]>0) myTreeMapfour.put(i,countc[i]);
        }
        //System.out.println("耗时4：" + (System.currentTimeMillis() - t2) + "ms");
        int max=0;
        for (int key : myTreeMapfour.keySet()) {
            if(key!=0) {
                regression.addData(Math.log(key),Math.log(myTreeMapfour.get(key)));
                if(key>max) {
                    max = key;
                }
            }
        }
        //System.out.println("耗时4：" + (System.currentTimeMillis() - t2) + "ms");
        Map<Integer, Double> myTreeMapfive = new TreeMap<Integer, Double>();
        for (int i = 0; i < max+1; i++) {
            if (myTreeMapfour.containsKey(i)) {
                double countfive = (double)myTreeMapfour.get(i);
                myTreeMapfive.put(i, countfive);
            } else {
                myTreeMapfive.put(i, Math.pow(Math.E,regression.predict(Math.log(i))));
            }
        }

        //System.out.println("aaaaaaaa");
        //System.out.println("耗时4：" + (System.currentTimeMillis() - t2) + "ms");
        List<Map.Entry<Integer, Integer>> listfive = new ArrayList<Map.Entry<Integer, Integer>>(myTreeMapfour.entrySet());
        //List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
        int numfive = 1;
        listfive.remove(0);
        //System.out.println(listfive.get(30811)+"\n"+listfive.get(30812)+"\n"+listfive.get(30813));
        //将结果写入文件
        for (Map.Entry<Integer, Integer> map : listfive) {
            //if (num <= 10) {
            //bw.write("出现次数第" + num + "的单词为：" + map.getKey() + "，出现频率为" + map.getValue() + "次");
            bwfive.write(Math.log(map.getKey())+" "+Math.log(map.getValue()));
            //bwfive.write(map.getKey()+" "+map.getValue());
            bwfive.newLine();
            //System.out.println(map.getKey() + ":" + map.getValue());
            numfive++;
            //} else break;
        }
        bwfive.flush();
        bwfive.close();

        //System.out.println(myTreeMapfive.get(0));

        //System.out.println("耗时4：" + (System.currentTimeMillis() - t2) + "ms");
        float nn;
        nn=(float)myTreeMapfour.get(1)/(float)elementstwo.length;
        //System.out.println((float)myTreeMapfour.get(1)/(float)elementstwo.length);
        Map<Integer, Double> myTreeMapsix = new TreeMap<Integer, Double>();
        for (int i = 0; i < myTreeMapfive.size()-1; i++) {
            if(i==0){
                myTreeMapsix.put(0, Math.log((double)nn));
            }else {
                double countsixc = (double) myTreeMapfive.get(i);
                double countsixcadd1 = (double) myTreeMapfive.get(i + 1);
                myTreeMapsix.put(i, Math.log((i + 1) * countsixcadd1 / countsixc));
            }
        }
        //System.out.println("耗时4：" + (System.currentTimeMillis() - t2) + "ms");

        List<Map.Entry<Integer, Double>> listsix = new ArrayList<Map.Entry<Integer, Double>>(myTreeMapsix.entrySet());
        //List<Map.Entry<String, Integer>> listtwo = new ArrayList<Map.Entry<String, Integer>>(myTreeMaptwo.entrySet());
        int numsix = 1;
        //将结果写入文件
        for (Map.Entry<Integer, Double> map : listsix) {
            //if (num <= 10) {
            //bw.write("出现次数第" + num + "的单词为：" + map.getKey() + "，出现频率为" + map.getValue() + "次");
            //bwfive.write(Math.log(map.getKey())+" "+Math.log(map.getValue()));
            bwsix.write(map.getKey()+" "+map.getValue());
            bwsix.newLine();
            //System.out.println(map.getKey() + ":" + map.getValue());
            numsix++;
            //} else break;
        }
        bwsix.flush();
        bwsix.close();
        //System.out.println("耗时4：" + (System.currentTimeMillis() - t2) + "ms");
        //System.out.println(myTreeMapfour.get(1)+" "+elementstwo.length);
        for (int i=0;i<a.size();i++){
            for(int j=0;j<a.size();j++){
                //if(myTreeMapsix.get(numthree[i][j])!=null) {
                    if (numthree[i][j] >0) {
                        gt[i][j]=numthree[i][j];
                        //gt[i][j] = (float) ((myTreeMapsix.get(numthree[i][j])) + 1.0 - 1.0);
                        //System.out.println(myTreeMapsix.get(numthree[i][j]));
                    }
                    else //gt[i][j]=numthree[i][j];
                        //gt[i][j] = (float) ((myTreeMapsix.get(numthree[i][j])) + 1.0 - 1.0);
                        gt[i][j]=nn;
                //}
            }
        }
        //System.out.println("耗时5：" + (System.currentTimeMillis() - t2) + "ms");
        return gt;
    }
    public static String[] elements() throws IOException{
        //long t1 = System.currentTimeMillis();
        String s;
        String fileName1 = "/Users/ningsong/project_1_release/data/train_tokens.txt";
        BufferedReader br = new BufferedReader(new FileReader(fileName1));
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s + System.lineSeparator());
            //+System.lineSeparator()
        }
        //System.out.println(sb);
        String str = sb.toString().toLowerCase();
        //System.out.println(str);
        //分隔字符串并存入数组
        String[] elements = str.split("\n");
        //System.out.println("耗时：" + (System.currentTimeMillis() - t1) + "ms");
        return elements;
    }
    public static String[] elementstwo() throws IOException{
        //long t1 = System.currentTimeMillis();
        String[] elements=ProbEstimator.elements();

        //ystem.out.println("hehe"+list.size()+"haha");
//            for(int i=0;i<a.size();i++)
//                System.out.println(a.get(i));
        ArrayList<String> a = new ArrayList();
        for (int i = 1; i < elements.length; i++) {
            if (!elements[i].equals("<s>"))
            a.add(elements[i - 1] + " " + elements[i]);
        }
        //System.out.println(a.size());
        String[] elementstwo = new String[a.size()];
        //String[] elementstwo = str.split("[a-zA-z0-9]");
        for (int i = 1; i < a.size(); i++) {
            //if(!elements[i].equals("<s>")) {
            elementstwo[i] = a.get(i);
            //}
        }
        //System.out.println(elementstwo[a.size()-1]);
        //System.out.println("耗时：" + (System.currentTimeMillis() - t1) + "ms");
        return elementstwo;
    }
}





