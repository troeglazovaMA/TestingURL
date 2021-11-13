package mpr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 The class is designed to work with a url. At the moment, the main method is getStatisticFromURL().
 */

public class ContentAction {
    private String userURL;


    public ContentAction(String userURL) {
        this.userURL = userURL;
    }
    /**
     In this method, the page is accessed by URL and the number of unique words on the page is counted.
     */
    public String getStatisticFromURL() {
        String toOutput = "Statistics of words in " + userURL + ": " + "\n";
        try {
            ArrayList<String> listOfSeparators = new ArrayList<String>();
            listOfSeparators.add(" ");
            listOfSeparators.add("+");
            listOfSeparators.add("-");
            listOfSeparators.add("\\");
            listOfSeparators.add("*");
            listOfSeparators.add("=");
            listOfSeparators.add("»");
            listOfSeparators.add("«");
            listOfSeparators.add(",");
            listOfSeparators.add(".");
            listOfSeparators.add("!");
            listOfSeparators.add("?");
            listOfSeparators.add("(");
            listOfSeparators.add(")");
            listOfSeparators.add("[");
            listOfSeparators.add("]");
            listOfSeparators.add("-");
            listOfSeparators.add(";");
            listOfSeparators.add(":");
            listOfSeparators.add("/");
            listOfSeparators.add("{");
            listOfSeparators.add("}");
            listOfSeparators.add("%");
            listOfSeparators.add("\"");
            listOfSeparators.add("\n");
            listOfSeparators.add("\r");
            listOfSeparators.add("\t");
            String separatorsString = String.join("|\\", listOfSeparators);

            Document page = Jsoup.parse(new URL(userURL), 30000);
            String pagetext = page.text();
            ByteArrayInputStream myInputStream=new ByteArrayInputStream(pagetext.getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(new InputStreamReader(myInputStream));
            String line;
            Map<String, Integer> result = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(separatorsString);
                for (String word : words) {
                    if (!word.equals("")) {
                        String resword = word.toUpperCase();
                        if (result.containsKey(resword)) {
                            Integer k = result.get(resword);
                            k++;
                            result.put(resword, k);
                        } else {
                            result.put(resword, 1);
                        }
                    } else {
                        continue;
                    }
                }
            }
            List<Map.Entry<String, Integer>> valuesList = new ArrayList(result.entrySet());
            Collections.sort(valuesList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            for (Map.Entry<String, Integer> el : valuesList) {
                toOutput = toOutput + "\"" + el.getKey() + "\"" + " - " + el.getValue() + "\n";
            }


        } catch (RuntimeException e) {
            toOutput = "We have some problem: \n" + e.toString() + "\n Please try again";
        } catch (IOException e) {
            toOutput = "We have some problem: \n" + e.toString() + "\n Please try again";
        }

        return toOutput;
    }

    public String getUserURL() {
        return userURL;
    }

    public void setUserURL(String userURL) {
        this.userURL = userURL;
    }
}
