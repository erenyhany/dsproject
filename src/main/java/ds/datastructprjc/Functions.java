package ds.datastructprjc;


//this is an interface for the functions
public class Functions {
    public static String detectError(String fileText){
        ErrorsHandling.handleErrors(fileText);
        return  ErrorsHandling.errormsg.toString();
    }

    public static String correctError(String fileText){
        ErrorsHandling.handleErrors(fileText);
        return  ErrorsHandling.correctedFile.toString();
    }

    public static String prettify(String fileText){
        return Prettifying.Prettifying(new StringBuffer(fileText)).toString();
    }

    public static String minify(String fileText){
        return CompDecomp.minifying(fileText);
    }

    public static String compress(String fileText){
        char x = CompDecomp.xmlORjson(fileText);
        if(x == '{')return CompDecomp.jsonEncode(fileText);
        else return CompDecomp.xmlEncode(fileText);
    }

    public static String decompress(String fileText){
        char x = CompDecomp.xmlORjson(fileText);
        if(x == '{')return CompDecomp.jsonDecode(fileText);
        else return CompDecomp.xmlDecode(fileText);
    }

    public static String toJSON(String fileText){
        return JSONHandling.XMLtoJSON(fileText);
    }
}
