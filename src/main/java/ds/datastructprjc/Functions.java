package ds.datastructprjc;


//this is an interface for the functions
public class Functions {
    public static String detectError(String fileText){

    }

    public static String correctError(String fileText){

    }

    public static String prettify(String fileText){

    }

    public static String minify(String fileText){
        return CompDecomp.minifying(fileText);
    }

    public static String compress(String fileText){
        char x = CompDecomp.xmlORjson(filetext);
        if(x == '{')return CompDecomp.jsonEncode(filetext);
        else return CompDecomp.xmlEncode(filetext);
    }

    public static String decompress(String fileText){
        char x = CompDecomp.xmlORjson(filetext);
        if(x == '{')return CompDecomp.jsonDecode(filetext);
        else return CompDecomp.xmlDecode(filetext);
    }

    public static String toJSON(String fileText){

    }
}
