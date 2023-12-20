package ds.datastructprjc;
import java.util.Stack;


public class JSONHandling {
    static String XmltoJson(String xmlString)
    {
        StringBuffer Json = new StringBuffer();
        Stack<String> stack = new Stack<>();
        boolean open = false;
        boolean iamthelastone=false;
        boolean readytogivetag = false;
        int isarray = 0;
        StringBuffer insideTagContent = new StringBuffer();
        Json.append("{"+'\n');
        for (int i = 0; i < xmlString.length(); i++) {
            insideTagContent.setLength(0);
            char currentChar = xmlString.charAt(i);
            readytogivetag = false;


            ////////////////////////////////////////////////////////////////////
            if (currentChar == '<') {

                i++;
                open = true;

                if (xmlString.charAt(i) == '/') {



                    open = false;
                    while (true) {
                        if (xmlString.charAt(i) == '>') {
                            break;
                        }
                        insideTagContent.append(xmlString.charAt(i));
                        i++;
                    }
                    iamthelastone =givethenexttag(xmlString,i);

                    String st= stack.pop();
                    if(st==":")
                    {

                        if(stack.size()>=1&&iamthelastone) {
                            Json.append(","+'\n');
                        }
                        else Json.append('\n');

                    }
                    else if(st=="[")
                    {
                        Json.append("\t".repeat(stack.size()+1)+']');
                        if(stack.size()>=1&&iamthelastone) {
                            Json.append(","+'\n');
                        }
                        else Json.append('\n');
                    }
                    else if(st=="{")
                    {
                        Json.append("\t".repeat(stack.size()+1)+'}');
                        if(stack.size()>=1&&iamthelastone) {
                            Json.append(","+'\n');
                        }
                        else Json.append('\n');
                    }


                }
                insideTagContent.setLength(0);
            }
            while (open) {
                if (xmlString.charAt(i) == '>') {
                    open = false;

                    readytogivetag = true;
                    break;
                }
                insideTagContent.append(xmlString.charAt(i));
                i++;
            }
            ////////////////////////////////////////////////////////////////////
            if (readytogivetag) {
                isarray = isarray(xmlString,i, insideTagContent.toString());

                if(isarray==1)
                {
                    stack.push("[");
                }
                else if(isarray==0)
                {
                    stack.push("{");

                }
                else if(isarray==-1)
                {

                    stack.push(":");

                }
                Json.append("\t".repeat(stack.size()) +'"'+insideTagContent+'"' +": ");
                if(isarray==1)
                {
                    Json.append("[\n");
                }
                else if(isarray==0)
                {

                    Json.append("{\n");
                }
                else if(isarray==-1)
                {
                    Json.append( '"'+connnnn(xmlString,i)+'"');
                }



            }


        }
        Json.append("}"+'\n');


        return (Json.toString());
    }












    public static int searchclose(String tag, String xmlString, int i) {

        String closetag = "</" + tag + ">";
        boolean open = false;
        StringBuffer insideTagContent = new StringBuffer();
        for (; i < xmlString.length(); i++) {
            char currentChar = xmlString.charAt(i);

            if (currentChar == '<' && xmlString.charAt(i+1)=='/')  {
                open = true;
                insideTagContent.setLength(0);
            }

            while (open) {
                insideTagContent.append(xmlString.charAt(i));

                if (xmlString.charAt(i) == '>') {
                    open = false;
                    if (insideTagContent.toString().contains(closetag)) {
                        return i;
                    }
                    break;
                }

                i++;

            }
        }
        return 20;
    }


    public static String connnnn(String xmlString, int i) {


        boolean open = false;
        String content = "";
        i++;
        while (true)
        {

            char currentChar = xmlString.charAt(i);
            if (currentChar == '<') {
                return (content).trim();

            }
            content = content + currentChar;
            i++;

        }

    }


    public static int isarray(String xmlString, int i, String tag) {


        String closetag =  tag ;
        boolean compare = false;
        boolean open = false;
        boolean itisthefirst = true;
        char currentChar;
        String insideTagContent = "";
        String savetag = "";
        int counter=0;


        while (true) {
            boolean readytogivetag = false;

            ////////////////////////////////////////////////////////////////
            char cur= xmlString.charAt(i);

            if (xmlString.charAt(i) == '<') {
                i++;
                open = true;
                counter++;
                insideTagContent = "";
            }
            while (open) {

                if (xmlString.charAt(i) == '>') {
                    open = false;
                    readytogivetag = true;

                    break;
                }
                insideTagContent = insideTagContent + xmlString.charAt(i);

                i++;
            }
            ////////////////////////////////////////////////////////////////

            if(readytogivetag) {
                //see if it was id or name : content
                if (itisthefirst) {
                    if (insideTagContent.contains(closetag)) {
                        return -1;
                    }
                }
                itisthefirst = false;
                if (insideTagContent.contains(closetag)) {

                    if(counter == 2)
                    {
                        //Json.append(d"MArteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        return 0;
                    }

                    return 1;
                }

                if (!insideTagContent.contains(closetag)) {

                    if (compare) {
                        if (!savetag.contains(insideTagContent)) {
                            return 0;
                        }


                    }

                    savetag = insideTagContent;
                    compare = true;
                    i = searchclose(insideTagContent, xmlString, i);

                }


            }
            i++;


        }



    }


    static boolean givethenexttag(String xml, int i)
    {
        while (true&&i<xml.length())
        {

            if(xml.charAt(i)=='<'&&xml.charAt(i+1)=='/')
            {
                return false;
            }
            if(xml.charAt(i)=='<')
            {
                return true;
            }
            i++;

        }
        return false;

    }


}



