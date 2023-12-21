//Mina Hany Hanna
package ds.datastructprjc;

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

  //function encode xml string
  public static String xmlEncode(String input){

        String r = input;
        code = new HashMap<>();
        int i=0, encodedNo=192;
        String y;

        //creating Map for encoding
        while(i<input.length()){
            if(input.charAt(i)=='<'){
                int j=i;
                while(input.charAt(j)!='>')j++;
                y = r.substring(i,j);

                //append string in Map
                code.put(y, String.valueOf(encodedNo++));

                //i = last char index
                i=j;
            }
            else i++;
        }

        for (Map.Entry<String, String> entry : code.entrySet()) {
            r = r.replaceAll(entry.getKey(), entry.getValue());
        }

        r = r.replaceAll("\n", "ص");
        r = replaceSpacesWithCount(r);
        return r;
    }

  //function decode xml string
  public static String xmlDecode(String encodedInput) {
        String r = encodedInput;

        for (Map.Entry<String, String> entry : code.entrySet()) {
            r = r.replaceAll(entry.getValue(), entry.getKey());
        }
        r = replaceCountWithSpaces(r);
        r = r.replaceAll("ص", "\n");

        return r;
    }

  //function encode json string
  public static String jsonEncode(String input){

        //encode repeated strings
        String result = input.replaceAll("\"users\":","ا");

        result = result.replaceAll("<\"user\":>", "ب");

        result = result.replaceAll("\"id\":", "ت");

        result = result.replaceAll("\"name\":", "ث");

        result = result.replaceAll("\"posts\":", "ج");

        result = result.replaceAll("\"post\":", "ح");

        result = result.replaceAll("\"body\":", "خ");

        result = result.replaceAll("\"topics\":", "ط");

        result = result.replaceAll("\"topic\":", "ظ");

        result = result.replaceAll("\"followers\":", "د");

        result = result.replaceAll("\"follower\":", "ذ");

        result = result.replaceAll("\n", "ص");
        result = replaceSpacesWithCount(result);

        return result;
    }

  //function decode json string
  public static String jsonDecode(String input){

        //encode repeated strings
        String result = input.replaceAll("ا","\"users\":");

        result = result.replaceAll("ب", "<\"user\":>");

        result = result.replaceAll("ت", "\"id\":");

        result = result.replaceAll("ث", "\"name\":");

        result = result.replaceAll("ج", "\"posts\":");

        result = result.replaceAll("ح", "\"post\":");

        result = result.replaceAll("خ", "\"body\":");

        result = result.replaceAll("ط", "\"topics\":");

        result = result.replaceAll("ظ", "\"topic\":");

        result = result.replaceAll("د", "\"followers\":");

        result = result.replaceAll("ذ", "\"follower\":");

        result = replaceCountWithSpaces(result);
        result = result.replaceAll("ص", "\n");

        return result;
    }
  
}
