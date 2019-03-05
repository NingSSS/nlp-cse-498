import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.List;
//import java.lang.String.*;
//
//import edu.stanford.nlp.ling.CoreLabel;
//import edu.stanford.nlp.ling.HasWord;
//import edu.stanford.nlp.process.CoreLabelTokenFactory;
//import edu.stanford.nlp.process.DocumentPreprocessor;
//import edu.stanford.nlp.process.PTBTokenizer;
//import java.io.BufferedReader;
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

public class Predictor {
    public static void main(String[] args) throws IOException {
        //long t1 = System.currentTimeMillis();
        String faketoken = "/Users/ningsong/project_1_release/data/test_tokens_fake.txt";
        String confusingword = "/Users/ningsong/project_1_release/data/all_confusingWords.txt";
        //String correct = "/Users/ningsong/project_1_release/data/correct.txt";
        //String correcttwo = "/Users/ningsong/project_1_release/data/correct2.txt";
        String s;
        //String sfake;
        BufferedReader fake = new BufferedReader(new FileReader(faketoken));
        BufferedReader confusing = new BufferedReader(new FileReader(confusingword));
        //BufferedWriter bwcorrect = new BufferedWriter(new FileWriter(correct));
        //BufferedWriter bwcorrecttwo = new BufferedWriter(new FileWriter(correcttwo));
        StringBuffer sb = new StringBuffer();

        while ((s = fake.readLine()) != null) {
            sb.append(s + System.lineSeparator());
            //+System.lineSeparator()
        }

        //System.out.println(sb);
        String str = sb.toString().toLowerCase();
        //System.out.println(str);
        //分隔字符串并存入数组
        String[] fakeelements = str.split("\n");
        String[] fakeelementstwo = new String[fakeelements.length];
        List<String> listfake = new ArrayList<>();
        for (int i = 0; i < fakeelements.length; i++) {
            listfake.add(fakeelements[i]);
        }
//        for (int i = 0; i < listfake.size(); i++) {
//            System.out.println(listfake.get(i));
//        }
        //ystem.out.println("hehe"+list.size()+"haha");
//            for(int i=0;i<a.size();i++)
//                System.out.println(a.get(i));


        //String[] elementstwo = str.split("[a-zA-z0-9]");


        for (int i = 1; i < fakeelements.length; i++) {
            fakeelementstwo[i] = fakeelements[i - 1] + " " + fakeelements[i];
        }

        List<String> listconfusing = new ArrayList<>();
        while ((s = confusing.readLine()) != null) {
            listconfusing.add(s);
            //+System.lineSeparator()
        }
        String cc[][] = new String[listconfusing.size()][2];
        for (int i = 0; i < listconfusing.size(); i++) {
            String[] sp = (listconfusing.get(i)).split(":");
            for (int j = 0; j < 2; j++) {
                cc[i][j] = sp[j];
            }
        }

        //System.out.println(cc.length + " " + cc[0].length);

        int ccount = 0;
        int ccounttwo = 0;
        Map<String, Integer> cmyTreeMap = new TreeMap<String, Integer>();
        Map<String, Integer> cmyTreeMaptwo = new TreeMap<String, Integer>();

        for (int i = 0; i < fakeelements.length; i++) {
            if (cmyTreeMap.containsKey(fakeelements[i])) {
                ccount = cmyTreeMap.get(fakeelements[i]);
                cmyTreeMap.put(fakeelements[i], ccount + 1);
            } else {
                cmyTreeMap.put(fakeelements[i], 1);
            }
        }
//
//            for (int i = 1; i < fakeelements.length; i++) {
//                if (cmyTreeMaptwo.containsKey(fakeelementstwo[i])) {
//                    ccounttwo = cmyTreeMaptwo.get(fakeelementstwo[i]);
//                    cmyTreeMaptwo.put(fakeelementstwo[i], ccounttwo + 1);
//                } else {
//                    cmyTreeMaptwo.put(fakeelementstwo[i], 1);
//                }
//            }


        int numcc = 0;
        for (int i = 0; i < cc.length; i++) {
            for (int j = 0; j < cc[0].length; j++) {
                if (cmyTreeMap.get(cc[i][j]) != null) {
                    //for(int j=0; j<cmyTreeMap.size();j++){
                    numcc = numcc + cmyTreeMap.get(cc[i][j]);
                }
            }
        }

        //System.out.println(numcc);

        //System.out.println(numaa);


        String[] elements = ProbEstimator.elements();
        ArrayList<String> a = new ArrayList();
        for (int i = 0; i < (elements.length); i++) {
            if (!a.contains(elements[i]))
                a.add(elements[i]);
        }

        Map<String, Integer> myTreeMapthree = new TreeMap<String, Integer>();
        for (int i = 0; i < a.size(); i++) {
            myTreeMapthree.put(a.get(i), i);
        }

        int aa = 0;
        int numaa = 0;
        String[][] ccc = new String[numcc][3];
        Integer[][] sss = new Integer[numcc][2];
        for (int i = 0; i < numcc; i++) {
            for (int j = 0; j < 2; j++) {
                sss[i][j] = 0;
            }
        }
        //System.out.println(ccc[1][0]);
        //System.out.println("耗时：" + (System.currentTimeMillis() - t1) + "ms");
        int sen = -1;
        int word = 0;
        for (int m = 0; m < listfake.size(); m++) {
            if (listfake.get(m).equals("<s>")) {
                sen++;
                word = 0;
            } else {
                word++;
            }
            for (int i = 0; i < cc.length; i++) {
                for (int j = 0; j < cc[0].length; j++) {

                    //if (a.contains(cc[i][j])) {
                    if (listfake.get(m).equals(cc[i][j]) && m >= 1) {
                        ccc[aa][0] = listfake.get(m - 1);
                        ccc[aa][1] = cc[i][j];
                        ccc[aa][2] = cc[i][1 - j];
                        sss[aa][0] = sen;
                        sss[aa][1] = word;
                        //System.out.println(listfake.get(m - 1) + " " + cc[i][j] + " " + cc[i][1 - j]);
                        //System.out.println(ccc[aa][0] + " " + ccc[aa][1] + " " + ccc[aa][2]);
                        aa = aa + 1;
                        numaa++;
                    }
                    //}
                }
            }
        }
        //System.out.println("耗时：" + (System.currentTimeMillis() - t1) + "ms");
        File Lwritename = new File("/Users/ningsong/project_1_release/results/Laplaceresult.txt");
        Lwritename.createNewFile(); // 创建新文件
        BufferedWriter Lout = new BufferedWriter(new FileWriter(Lwritename));
        int[][] Laplaceporb = ProbEstimator.Laplaceporb();
        int order = 0;
        ArrayList<Integer> bbbb = new ArrayList();
        ArrayList<Integer> bbbbb = new ArrayList();
        //String[] elements=ProbEstimator.elements();
        for (int n = 0; n < ccc.length; n++) {
        for (int i = 0; i < cc.length; i++) {
            for (int j = 0; j < cc[0].length; j++) {
                    if (a.contains(ccc[n][0])&&a.contains(ccc[n][1])&&a.contains(ccc[n][2])) {
                        if (ccc[n][1].equals(cc[i][j])) {
                            //System.out.println(ccc[n][0] + " " + ccc[n][1] + " " + ccc[n][0] + " " + ccc[n][2]);

                            int result;
                            if (Laplaceporb[myTreeMapthree.get(ccc[n][0])][myTreeMapthree.get(ccc[n][1])] < Laplaceporb[myTreeMapthree.get(ccc[n][0])][myTreeMapthree.get(ccc[n][2])]) {
                                result = 1;
                                //Lout.write(sss[n][0] + ":" + sss[n][1] + "\n");
                                bbbb.add(sss[n][0]);
                                bbbbb.add(sss[n][1]);
                            } else {
                                result = 0;
                            }
                            //System.out.println(result);
                            order += result;
                        }
                    }
                }
            }
        }
        for (int i=0;i<order;i++){
            if(i>0&&bbbb.get(i).equals(bbbb.get(i-1))&&i<order-1){
                Lout.write(bbbbb.get(i)+",");
                if(!bbbb.get(i).equals(bbbb.get(i+1))&&i>0){
                    Lout.write("\n");
                }
            }else{
                Lout.write(bbbb.get(i)+":"+bbbbb.get(i)+",");
                if(i<order-1&&!bbbb.get(i).equals(bbbb.get(i+1))){
                    Lout.write("\n");
                }
            }

        }
        Lout.flush();
        Lout.close();
        System.out.println(order);


        File writename = new File("/Users/ningsong/project_1_release/results/GTresult.txt");
        writename.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        //long t2 = System.currentTimeMillis();
        float[][] gt = ProbEstimator.GTtable();
        ArrayList<Integer> aaaa = new ArrayList();
        ArrayList<Integer> aaaaa = new ArrayList();
        //System.out.println("耗时：" + (System.currentTimeMillis() - t2) + "ms");
        //float[][] Laplaceporb = ProbEstimator.Laplaceporb();
        int gtorder = 0;
        //String[] elements=ProbEstimator.elements();
        for (int n = 0; n < ccc.length; n++) {
            for (int i = 0; i < cc.length; i++) {
                for (int j = 0; j < cc[0].length; j++) {
                    if (a.contains(ccc[n][0]) && a.contains(ccc[n][1]) && a.contains(ccc[n][2])) {
                        if (ccc[n][1].equals(cc[i][j])) {
                            //System.out.println(ccc[n][0] + " " + ccc[n][1] + " " + ccc[n][0] + " " + ccc[n][2]+" "+sss[n][0]+" "+sss[n][1]);

                            int result;
                            if (gt[myTreeMapthree.get(ccc[n][0])][myTreeMapthree.get(ccc[n][1])] < gt[myTreeMapthree.get(ccc[n][0])][myTreeMapthree.get(ccc[n][2])]) {
                                result = 1;
                                //out.write(sss[n][0] + ":" + sss[n][1] + "\n");
                                aaaa.add(sss[n][0]);
                                aaaaa.add(sss[n][1]);
                            } else result = 0;

                            //System.out.println(result);
                            gtorder += result;
                        }
                    }
                }
            }
        }
        for (int i=0;i<gtorder;i++){
            if(i>0&&aaaa.get(i).equals(aaaa.get(i-1))&&i<gtorder-1){
                out.write(aaaaa.get(i)+",");
                if(!aaaa.get(i).equals(aaaa.get(i+1))&&i>0){
                    out.write("\n");
                }
            }else{
                out.write(aaaa.get(i)+":"+aaaaa.get(i)+",");
                if(i<gtorder-1&&!aaaa.get(i).equals(aaaa.get(i+1))){
                    out.write("\n");
                }
            }

        }
        out.flush();
        out.close();
        //System.out.println("耗时：" + (System.currentTimeMillis() - t1) + "ms");
        System.out.println(gtorder);
        //System.out.println(gt[myTreeMapthree.get("background")][myTreeMapthree.get("affect")]);
        //System.out.println(gt[myTreeMapthree.get("background")][myTreeMapthree.get("effect")]);
        //System.out.println("耗时：" + (System.currentTimeMillis() - t1) + "ms");
    }
}


