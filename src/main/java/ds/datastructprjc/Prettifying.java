package ds.datastructprjc;

import java.util.Stack;

public class Prettifying {

    static StringBuffer Prettifying (StringBuffer file) {

        Stack <String> stack = new Stack<>();
        StringBuffer prettyFile = new StringBuffer();
        boolean isbodylabel = false;
        boolean NameOrId = false;
        int i =0;
        char current;
        char next;

        while(i < file.length()){
            current = file.charAt(i);  //current char
            if(i<file.length()-1){
                next = file.charAt(i + 1); //next char
            }else{
                next='"';
            }

            //condition for open label
            if (current == '<' && next != '/') {

                String openlabel = findWordAtIndex(file, i + 1); //get label
                for (int j = 0; j <= openlabel.length(); j++) {
                    i++;
                }
                current = file.charAt(i);  //current char

                //pushing label onto the stack
                stack.push(openlabel + "");

                prettyFile = prettyFile.append('<'+openlabel+'>');


                if(openlabel.equals("id") || openlabel.equals("name")){
                    NameOrId = true;
                    ///find first char that isn't a \s \n \t then append all to string until </
                }else{
                    prettyFile = prettyFile.append('\n');
                    for(int j=0;j< stack.size();j++){
                        prettyFile = prettyFile.append('\t');
                    }
                }
                if(isbodylabel){
                    //find first char that isn't a \s \n \t then append all to string until </
                }
            } //condition for closedlabel
            else if (current == '<' && next == '/') {
                String closedlabel = findWordAtIndex(file, i + 2); //get closing label
                for (int j = 0; j <= closedlabel.length(); j++) {
                    i++;
                }
                current = file.charAt(i);  //current char

                //pop stack
                stack.pop();


                if(!NameOrId){
                    NameOrId = false;
                    removeLastChar(prettyFile);
                }
                prettyFile = prettyFile.append("</"+closedlabel+'>');
                prettyFile = prettyFile.append('\n');
                for(int j=0;j< stack.size();j++){
                    prettyFile = prettyFile.append('\t');
                }
                if(NameOrId){
                    NameOrId = false;
                }
            }
            i++;
        }
        return prettyFile;
    }

    //function to get labels
    public static String findWordAtIndex(StringBuffer text, int startIndex) {
        int spaceIndex = text.indexOf(">", startIndex); // Find the next space from startIndex

        // If spaceIndex is -1, set it to the end of the string
        if (spaceIndex == -1) {
            spaceIndex = text.length();
        }

        // Extract the word using substring
        String word = text.substring(startIndex, spaceIndex);

        return word;
    }

    //function to remove last char (using it for tags only)in a stringbuffer
    public static void removeLastChar(StringBuffer strBuffer) {
        if (strBuffer.length() > 0) {
            strBuffer.deleteCharAt(strBuffer.length() - 1);
        }
    }
}




