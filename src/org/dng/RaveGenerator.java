package org.dng;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RaveGenerator {
    private static final String fileName = "d:\\text.txt";
    private static List<String> sentenceList=new ArrayList<>();

    static {
        try( BufferedReader br = new BufferedReader(
                new FileReader(fileName))
        ) {
            String text = null;
            LinkedList<String> ll = new LinkedList<>();
            while ((text=br.readLine())!=null){
                final String sentences[] = text.split("[.!?]\\s*");
                for (int i = 0; i < sentences.length; ++i) {
                    ll.add(sentences[i]);
                }
            }
            sentenceList=ll.stream().toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    private static @NotNull List<String> getSentenceList(){
//
//        List<String> sentenceList = new ArrayList<>();
//
//        try( BufferedReader br = new BufferedReader(
//                new FileReader(fileName))
//        ) {
//            String text = null;
//            while ((text=br.readLine())!=null){
//                final String sentences[] = text.split("[.!?]\\s*");
//                for (int i = 0; i < sentences.length; ++i) {
//                    sentenceList.add(sentences[i]);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sentenceList;
//    }

    public static String getSentance(){
        Random random = new Random();
        //get random sentence from range between 0 and  sentenceList.size()-1
        return sentenceList.get(random.nextInt(sentenceList.size()-1));
    }
}
