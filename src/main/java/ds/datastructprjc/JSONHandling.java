package ds.datastructprjc;
import java.util.Stack;


public class JSONHandling {
    static public String XMLtoJSON (String xmlString)
    {
        StringBuilder json = new StringBuilder();
        boolean makeacoma= false;
        Stack<String> stack = new Stack<>();

        boolean infollowers= false;

        String body;

        boolean openTag = false, between = false;
        StringBuilder content = new StringBuilder();
        StringBuilder insideTagContent = new StringBuilder();

        for (int i = 0; i < xmlString.length(); i++) {
            char currentChar = xmlString.charAt(i);






            if ((insideTagContent.toString()).contains("<users>")) {
                insideTagContent.setLength(0);
                stack.push("<users>");
                json.append("{ \n" + "\t".repeat(stack.size()) + '"' + "users" + '"' + ": ["+"\n");
                makeacoma=false;

            }

            if ((insideTagContent.toString()).contains("<user>")) {
                insideTagContent.setLength(0);
                stack.push("<user>");
                for (int s = 0; s < stack.size(); s++) {
                    json.append('\t');
                }
                json.append('"' + "user" + '"' + ": {"+"\n");

            }


            if ((insideTagContent.toString()).contains("<id>")) {
                if(infollowers)
                {
                    stack.push("<id>");
                    json.append("\t".repeat(stack.size()+1)+'"' + "id" + '"' + ": ");
                    content = extractcontent("<id>", i, xmlString, insideTagContent);
                    json.append(content+"\n");
                    insideTagContent.setLength(0);
                    continue;
                }

                stack.push("<id>");
                json.append("\t".repeat(stack.size())+'"' + "id" + '"' + ": ");
                content = extractcontent("<id>", i, xmlString, insideTagContent);
                json.append(content + ","+"\n");
                insideTagContent.setLength(0);
            }
            if ((insideTagContent.toString()).contains("</id>")) {

                if(infollowers)
                {
                    stack.pop();

                    insideTagContent.setLength(0);


                    continue;
                }

                stack.pop();

                insideTagContent.setLength(0);
            }

            if ((insideTagContent.toString()).contains("<name>")) {

                stack.push("<name>");
                for (int s = 0; s < stack.size(); s++) {
                    json.append('\t');

                }
                json.append('"' + "name" + '"' + ": ");
                content = extractcontent("<name>", i, xmlString, insideTagContent);
                json.append('"' +""+ content +'"' + ","+"\n");
                stack.pop();
                insideTagContent.setLength(0);

            }


            if ((insideTagContent.toString()).contains("<posts>")) {
                stack.push("<posts>");
                for (int s = 0; s < stack.size(); s++) {
                    json.append('\t');
                }
                json.append('"' + "posts" + '"' + ": [");
                insideTagContent.setLength(0);
            }

            if ((insideTagContent.toString()).contains("<post>")) {
                stack.push("<post>");
                if(makeacoma)
                {
                    json.append(",");
                    makeacoma=false;
                }
                json.append("\n"+"\t".repeat(stack.size())+'"' + "post" + '"' + ": {"+"\n");
                insideTagContent.setLength(0);
            }


            if ((insideTagContent.toString()).contains("<body>")) {
                stack.push("<body>");
                for (int s = 0; s < stack.size(); s++) {
                    json.append('\t');
                }
                json.append('"' + "body" + '"' + ": {");
                content = extractcontent("<body>", i, xmlString, insideTagContent);
                body=content.toString();
                body = replaceSubstring(body, "\n", "\n"+"\t".repeat(stack.size()+2)).trim();
                json.append('"' + body + '"');
                insideTagContent.setLength(0);

            }

            if ((insideTagContent.toString()).contains("</body>")) {


                json.append("},"+"\n");
                stack.pop();
                insideTagContent.setLength(0);
            }


            if ((insideTagContent.toString()).contains("<topics>")) {
                stack.push("<topics>");
                json.append("\t".repeat(stack.size())+'"' + "topics" + '"' + ": [");
                insideTagContent.setLength(0);

            }



            if ((insideTagContent.toString()).contains("<topic>")) {

                if(makeacoma)
                {
                    json.append(",");
                    makeacoma=false;
                }
                stack.push("<topic>");
                content = extractcontent("<topic>", i, xmlString, insideTagContent);
                body=content.toString().trim();
                json.append("{");
                json.append('"' + body + '"');
                insideTagContent.setLength(0);
            }

            if ((insideTagContent.toString()).contains("</topic>")) {


                stack.pop();
                json.append("}");
                insideTagContent.setLength(0);
                makeacoma=true;
            }

            if ((insideTagContent.toString()).contains("</topics>")) {

                json.append("]"+"\n");
                insideTagContent.setLength(0);
                stack.pop();
                makeacoma=false;

            }


            if ((insideTagContent.toString()).contains("</post>")) {

                makeacoma=true;
                json.append("\t".repeat(stack.size()+1)+"}");
                stack.pop();
                insideTagContent.setLength(0);
            }




            if ((insideTagContent.toString()).contains("</posts>")) {

                json.append("\n"+"\t".repeat(stack.size()+1)+"],"+"\n");
                insideTagContent.setLength(0);
                stack.pop();
                makeacoma=false;

            }


            if ((insideTagContent.toString()).contains("<followers>")) {
                infollowers=true;
                stack.push("<followers>");
                json.append("\t".repeat(stack.size())+'"' + "followers" + '"' + ": ["+"\n");
                insideTagContent.setLength(0);
            }
            if ((insideTagContent.toString()).contains("<follower>")) {
                stack.push("<follower>");
                if(makeacoma)
                {
                    json.append(","+"\n");
                    makeacoma=false;
                }
                json.append("\t".repeat(stack.size()+1)+"{"+"\n");
                insideTagContent.setLength(0);
            }

            if ((insideTagContent.toString()).contains("</follower>")) {

                makeacoma=true;
                json.append("\t".repeat(stack.size()+1)+"}");

                stack.pop();
                insideTagContent.setLength(0);
            }

            if ((insideTagContent.toString()).contains("</followers>")) {
                infollowers=false;
                json.append("\n"+"\t".repeat(stack.size()+1)+"]"+"\n");
                stack.pop();
                insideTagContent.setLength(0);
                makeacoma=false;


            }
            if ((insideTagContent.toString()).contains("</user>")) {

                json.append("\t".repeat(stack.size()+1)+"}"+"\n");
                insideTagContent.setLength(0);
                stack.pop();

            }

            if (currentChar == '<') {
                openTag = true;
                insideTagContent.setLength(0);
            }

            while (openTag) {
                insideTagContent.append(currentChar);

                if (currentChar == '>') {
                    openTag = false;
                    //System.out.println(insideTagContent);


                    break;
                }

                i++;
                currentChar = xmlString.charAt(i);

            }
        }

        json.append("\t\t"+"]"+"\n");
        json.append("}");
        return json.toString();

    }




    static StringBuilder extractcontent(String tag, int i, String xmlString, StringBuilder insideTagContent) {
        StringBuilder content = new StringBuilder();
        char currentChar = xmlString.charAt(i);
        boolean between = false;
        if ((insideTagContent.toString()).contains(tag)) {
            between = true;
            content.setLength(0);
        }
        while (between) {
            content.append(currentChar);
            i++;
            currentChar = xmlString.charAt(i);
            if (currentChar == '<') {
                between = false;
                return content;
            }
        }
        return content;
    }

    public static String replaceSubstring(String input, String substringToReplace, String replacementSubstring) {
        return input.replace(substringToReplace, replacementSubstring);
    }
}




