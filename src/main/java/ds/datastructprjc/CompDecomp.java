//Mina Hany Hanna
package ds.datastructprjc;
import java.util.*;

public class CompDecomp {
  
  //function removes all spaces and newlines
  public static String minifying(String input) {
        return input.replaceAll("\\s", "");
    }

  //function to determine xml or json
  public static char xmlORjson(String input) {
        for (char ch : input.toCharArray()) {
            if (!Character.isWhitespace(ch)) {
                return ch;
            }
        }
        return '\0';
    }

  //function replace spaces with number
  private static String replaceSpacesWithCount(String input) {
        StringBuilder result = new StringBuilder();
        int count = 0;

        //count number of consecutive spaces
        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c) ) {
                count++;
            } else {
                if (count > 0) {
                    result.append("#"+count);
                    count = 0;
                }
                result.append(c);
            }
        }

        //handle trailing spaces
        if (count > 0) {
            result.append("#"+count);
        }

        return result.toString();
    }

  //function to return spaces in string
  private static String replaceCountWithSpaces(String input) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            if (input.charAt(i) == '#') {
                //find the number after "#"
                int j = i + 1;
                while (j < input.length() && Character.isDigit(input.charAt(j))) {
                    j++;
                }

                //replace the number with spaces
                int numSpaces = Integer.parseInt(input.substring(i + 1, j));
                result.append(" ".repeat(numSpaces));

                //move the index to the character after the number
                i = j;
            } else {
                //keep other characters unchanged
                result.append(input.charAt(i));
                i++;
            }
        }
        return result.toString();
    }

  static List<String> map_xml;
    static int map_ixml;
    public static String xmlEncode(String input){
        String r = input;

        map_xml = new ArrayList<>();
        map_ixml=0;

        int i=0, encodedNo=192;
        String y;

        //creating Map for encoding
        while(i<input.length()){
            if(input.charAt(i)=='<'){
                int j=i;
                while(input.charAt(j)!='>')j++;
                y = r.substring(i,j+1);

                //append string in Map
                map_xml.add(map_ixml++,y);

                //i = last char index
                i=j;
            }
            else i++;
        }

        for(int n=map_ixml-1; n>=0; n--){
            r=r.replaceAll(map_xml.get(n),String.valueOf((char)(192+n)));
        }

        r = r.replaceAll("\n", "ص");
        r = replaceSpacesWithCount(r);
        return r;
    }

  //function decode xml string
  public static String xmlDecode(String encodedInput) {
        String r = encodedInput;

        for(int n=map_ixml-1; n>=0; n--){
            r=r.replaceAll(String.valueOf((char)(192+n)),map_xml.get(n));
        }

        r = replaceCountWithSpaces(r);
        r = r.replaceAll("ص", "\n");

        return r;
    }

  //function encode json string
  static List<String> map_json;
    static int map_ijson;
    public static String jsonEncode(String input){
        String r = input;

        map_json = new ArrayList<>();
        map_ijson=0;

        int i=0, encodedNo=192;
        String y;

        //creating Map for encoding
        while(i<input.length()){
            if(input.charAt(i)=='\"'){
                int j=i;
                while(input.charAt(j)!=':' && (j-i<14))j++;
                if((j-i<14)) {
                    y = r.substring(i, j + 1);

                    //append string in Map
                    map_json.add(map_ijson++, y);

                    //i = last char index
                    i = j;
                }
                else{i=j;}
            }
            else i++;
        }

        for(int n=map_ijson-1; n>=0; n--){
            r=r.replaceAll(map_json.get(n),String.valueOf((char)(192+n)));
        }

        r = r.replaceAll("\n", "ص");
        r = replaceSpacesWithCount(r);
        return r;
    }

  //function decode json string
  public static String jsonDecode(String input){

        String r = input;

        for(int n=map_ijson-1; n>=0; n--){
            r=r.replaceAll(String.valueOf((char)(192+n)),map_json.get(n));
        }

        r = replaceCountWithSpaces(r);
        r = r.replaceAll("ص", "\n");

        return r;
    }
  
}
