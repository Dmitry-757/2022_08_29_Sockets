package org.dng.Server;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.util.*;

public class RaveGenerator {
//    private static final String fileName = "d:\\text.txt";

//    getResource("resources/text.txt");
    private static List<String> sentenceList=new ArrayList<>();

    static {
        URL url = RaveGenerator.class.getClassLoader().getResource("resources/text.txt");
        try(
                BufferedReader br = new BufferedReader(
//                new FileReader(fileName))
                new FileReader(url.getPath()))
        ) {

            String text = null;
            LinkedList<String> ll = new LinkedList<>();
            while ((text=br.readLine())!=null){
//                final String sentences[] = text.split("[.!?]\\s*");
//                for (int i = 0; i < sentences.length; ++i) {
//                    ll.add(sentences[i]);
//                }

                //lets to filter sentence
                final LinkedList<String> lll = new LinkedList<>(Arrays.stream(text.split("[.!?]\\s*"))
                        .map(s -> s.trim())
                        .filter(s -> s.length()>0)
                        .toList());
                ll.addAll(lll);
            }
            sentenceList=ll.stream().toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSentance(){
        Random random = new Random();
        //get random sentence from range between 0 and  sentenceList.size()-1
        return sentenceList.get(random.nextInt(sentenceList.size()-1));
    }
}
