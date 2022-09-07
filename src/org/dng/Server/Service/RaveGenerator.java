package org.dng.Server.Service;


import java.io.*;
import java.net.URL;
import java.util.*;

public class RaveGenerator implements RaveGeneratorI {
    private static volatile RaveGenerator instance;
//    private static final String fileName = "d:\\text.txt";

    private static List<String> sentenceList=new ArrayList<>();

    static {
        URL url = RaveGenerator.class.getClassLoader().getResource("resources/text.txt");
        try(
                BufferedReader br = new BufferedReader(
                new FileReader(url.getPath()))
        ) {

            String text = null;
            LinkedList<String> ll = new LinkedList<>();
            while ((text=br.readLine())!=null){

                //lets to clean up the sentence of spaces by left (spaces by right are clearing during splitting)
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

    //try to realise singleton pattern
    //constructor disabled
    private RaveGenerator() {
    }

    public static RaveGenerator getInstance(){
        if (instance == null){
            synchronized (RaveGenerator.class){
                if (instance == null){
                    instance = new RaveGenerator();
                }
            }
        }
        return instance;
    }

    @Override
    public String getSentence(){
        Random random = new Random();
        //get random sentence from range between 0 and  sentenceList.size()-1
        return sentenceList.get(random.nextInt(sentenceList.size()-1));
    }

}
