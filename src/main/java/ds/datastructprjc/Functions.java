package ds.datastructprjc;


import java.util.Stack;

//this is an interface for the functions
public class Functions {
    public static Stack<String> undoStack = new Stack<String>();
    public static  Stack<String >redoStack = new Stack<String>();
    public static String detectError(String fileText){
        ErrorsHandling.handleErrors(fileText);
        String temp = ErrorsHandling.errormsg.toString();
        undoStack.push(temp);
        return  temp;
    }

    public static String correctError(String fileText){
        ErrorsHandling.handleErrors(fileText);
        String temp=ErrorsHandling.correctedFile.toString();
        undoStack.push(temp);
        return  temp;
    }

    public static String prettify(String fileText){
        String temp = Prettifying.Prettifying(new StringBuffer(fileText)).toString();
        undoStack.push(temp);
        return temp;
    }

    public static String minify(String fileText){
        String temp =CompDecomp.minifying(fileText);
        undoStack.push(temp);
        return temp;
    }

    public static String compress(String fileText){
        char x = CompDecomp.xmlORjson(fileText);
        if(x == '{'){
            String temp =CompDecomp.jsonEncode(fileText);
            undoStack.push(temp);
            return temp;
        }
        else {
            String temp =CompDecomp.xmlEncode(fileText);
            undoStack.push(temp);
            return temp;
        }
    }

    public static String decompress(String fileText){
        char x = CompDecomp.xmlORjson(fileText);
        if(x == '{'){
            String temp = CompDecomp.jsonDecode(fileText);
            undoStack.push(temp);
            return temp ;
        }
        else{
            String temp = CompDecomp.xmlDecode(fileText);
            undoStack.push(temp);
            return temp ;
        }
    }

    public static String toJSON(String fileText){
        String temp =JSONHandling.XmltoJson(fileText);
        undoStack.push(temp);
        return temp ;
    }

    public static String undo(){
        if(undoStack.size() > 1){
            String temp = undoStack.peek();
            undoStack.pop();
            redoStack.push(temp);
            return undoStack.peek();
        }else {

            return undoStack.peek();
        }
    }

    public static String redo(){

        if(redoStack.size() >=1){
            String temp = redoStack.peek();
            redoStack.pop();
            undoStack.push(temp);
            return temp;
        }
        return "no operation is done";
    }
}
