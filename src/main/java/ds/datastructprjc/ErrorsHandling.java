package ds.datastructprjc;
/*************************/

/************************************/

import java.util.*;

public class ErrorsHandling {
    public static StringBuilder correctedFile;
    public static StringBuilder errormsg;
    private static String [] tagNames = {"id","posts","post","users","user","followers","follower","topics","topic","name","body"};
    //making a tree to determine the child of each tags setting children for each node
    private final static TreeNode id  = new TreeNode(tagNames[0],new TreeNode[]{null,null,null,null});
    private final static TreeNode name = new TreeNode(tagNames[9],new TreeNode[]{null,null,null,null});
    private final static TreeNode topic = new TreeNode(tagNames[8],new TreeNode[]{null,null,null,null});
    private final static TreeNode body = new TreeNode(tagNames[10],new TreeNode[]{null,null,null,null});
    private final static TreeNode topics = new TreeNode(tagNames[7],new TreeNode[]{topic,null,null,null});
    private final static TreeNode follower = new TreeNode(tagNames[6],new TreeNode[]{id,null,null,null});
    private final static TreeNode followers = new TreeNode(tagNames[5],new TreeNode[]{follower,null,null,null});
    private final static TreeNode post = new TreeNode(tagNames[2],new TreeNode[]{body,topics,null,null});
    private final static TreeNode posts = new TreeNode(tagNames[1],new TreeNode[]{post,null,null,null});
    private final static TreeNode user = new TreeNode(tagNames[4],new TreeNode[]{id,name,posts,followers});
    private final static TreeNode users = new TreeNode(tagNames[3], new TreeNode[]{user,null,null,null});     //root of the tree


    private static Map<String , TreeNode> map = null;





    //this function detects and corrects errors and put the detected msg in a static variable called errormsg
    //and the correction in a static variable called
    public static void handleErrors(String fileText){

        initializeMap();
        errormsg =new StringBuilder();
        correctedFile = new StringBuilder(fileText);
        Stack<Character>lessThanSignsStack = new Stack<>();
        Stack<String> tagsStack = new Stack<String>();
        Stack<String> tagsHistory = new Stack<String>();
        int lineCounter=1;


        for( int i = 0 ;i<correctedFile.length();i++){        //main loop

            if(correctedFile.charAt(i)=='\n'){
                lineCounter++;

            }

            if(correctedFile.charAt(i) =='<'){
                lessThanSignsStack.push('<');
                //the string below (temp) contains what is between < and >
                String temp = findWordAtIndex(correctedFile,i+1).replaceAll("\\s", "").toLowerCase();

                //detect if Users tag (special case) doesnt exist
                if(tagsStack.empty()){
                    tagsStack.push("users");
                    tagsHistory.push("users");
                    if(!temp.contains("users") ){
                        errormsg.append("line "+(lineCounter-1)+" :you should add <users> at line \n");
                        correctedFile.delete(0, fileText.indexOf('<'));
                        correctedFile.insert(0,"<users>\n");
                        i =8;

                    }

                }

                //detect if '>'is missing in one conditions:(1)if temp contains '<'
                if(temp.contains("<")){
//                    errormsg.append("line "+lineCounter+" :the \'<\' no "+numOflineTags+" needs a \'>\'\n");


                    for(int x=0;x<tagNames.length;x++){
                        if(temp.substring(0,temp.indexOf('<')).contains(tagNames[x])){

                            if(temp.substring(0,temp.indexOf('<')).contains("/")){
                                errormsg.append("line "+lineCounter+" :the </"+tagNames[x]+" needs a \'>\'\n");
                                correctedFile.insert(i+2+tagNames[x].length(),">");
                                temp = "/"+tagNames[x];


                            }else{
                                errormsg.append("line "+lineCounter+" :the <"+tagNames[x]+" needs a \'>\'\n");
                                correctedFile.insert(i+1+tagNames[x].length(),">");
                                temp = tagNames[x];

                            }

                            break;
                        }
                    }


                }


                //detect if the tag doesn't have a '/' :
                // 1) if the top of the stack can be a parent of the current tag then push the current tag
                // 2)else we should detect the error :(a)if peek and temp are sibillings:the tag in the peek should be closed
                //                                    (b)if temp is a grandChild of the peek then we must
                //                                    open the intermediate tag (parent of temp and child of peek)
                if(!temp.contains("/")){
                    if(parentAndChild(tagsStack.peek(),temp)){
                        //CASE1
                        tagsStack.push(temp);
                        tagsHistory.push(temp);
                    }else if (tagsStack.size()>1 &&areSibillings(getElementBeforePeek(tagsStack),tagsStack.peek(),temp)&&intermediateNode(tagsStack.peek(),temp)== null ){
                        //CASE 2.a
                        //in this if condition i added the last condition for the case of missing follower between id and followers (they are sibillings and ascendors)
                        errormsg.append("line "+lineCounter+": close the <"+tagsStack.peek()+">tag before opening <"+temp+">\n");
                        correctedFile.insert(i,"</"+tagsStack.peek()+">");
                        i = i + 3 + (tagsStack.peek().length());
                        tagsStack.pop();

                    }else if (tagsStack.size()>=1 &&intermediateNode(tagsStack.peek(),temp)!= null && !areSibillings(tagsStack.peek(),tagsHistory.peek(),temp)){
                        //CASE 2.b
                        String inter = intermediateNode(tagsStack.peek(),temp);
                        errormsg.append("line "+lineCounter+": open <"+inter+">before <"+temp+">\n");
                        correctedFile.insert(i,"<"+inter+">");
                        i = i + 2 + (inter.length());
                        tagsStack.push(inter);
                        tagsStack.push(temp);
                        tagsHistory.push(inter);
                        tagsHistory.push(temp);

                    }
                }


                //detect if temp has a '/' :
                //1- if temp string == the top of the stack then no errors and pop
                //2-else then we needed to open it's tag and return with (i)to the added '<' with its tag name
                //       to recheck the previous conditions again

                if (temp.contains("/")){

                    if(temp.substring(1).equals(tagsStack.peek())){
                        //CASE1
                        tagsStack.pop();
                    }else if(parentAndChild(temp.substring(1),tagsStack.peek())){
                        correctedFile.insert(i,"</"+tagsStack.peek()+">");
                        errormsg.append("line "+lineCounter + ": expected </"+tagsStack.peek()+"> before closing <"+temp+">");
                        i--;
                    } else {
                        //CASE2
                        while(correctedFile.charAt(i) !='>'){
                            i--;
                        }
                        correctedFile.insert(i+1,"<"+temp.substring(1)+">");
                        errormsg.append("line "+lineCounter+": </"+temp.substring(1)+"> needs to be opened first\n");

                    }
                }

            }

            if(correctedFile.charAt(i) == '>'){
                if(!lessThanSignsStack.empty()){
                    lessThanSignsStack.pop();
                }else{
                    do{
                       i--;
                    }while(Character.isLetter(correctedFile.charAt(i)) || correctedFile.charAt(i)=='/');

                    correctedFile.insert(i+1,"<");
                    errormsg.append("line "+lineCounter+": there is a missing '<'\n");
                }
            }
        }//main loop

        //if at the end of the file there are unclosed tags (stack not empty)
        if(!tagsStack.empty()){
            while(!tagsStack.empty()){
                lineCounter++;
                errormsg.append("line "+lineCounter+": close the <"+tagsStack.peek()+">tag\n");
                correctedFile.append("</"+tagsStack.peek()+">\n");
                tagsStack.pop();
            }
        }

    }





    private static String findWordAtIndex(StringBuilder text, int startIndex) {
        int spaceIndex = text.indexOf(">", startIndex); // Find the next space from startIndex

        // If spaceIndex is -1, set it to the end of the string
        if (spaceIndex == -1) {
            spaceIndex = text.length();
        }

        // Extract the word using substring
        String word = text.substring(startIndex, spaceIndex);

        return word;
    }


    private static void initializeMap(){
        if(map == null){
            map = new HashMap<>();
            map.put("users",users);
            map.put("user",user);
            map.put("posts",posts);
            map.put("id",id);
            map.put("name",name);
            map.put("followers",followers);
            map.put("follower",follower);
            map.put("post",post);
            map.put("body",body);
            map.put("topics",topics);
            map.put("topic",topic);

        }
    }

    public static String getElementBeforePeek(Stack<String> stack) {
        String elementBeforePeek = null;
        if (stack.size() >= 2) {
            String temp = stack.peek();
            stack.pop();
            elementBeforePeek= stack.peek();
            stack.push(temp);
        }
        return elementBeforePeek;
    }



    private static boolean parentAndChild(String parent ,String child){
        return (map.get(parent)).isParent(map.get(child));
    }

    private static boolean areSibillings(String comonFather , String sib1 , String sib2){
        return  (map.get(comonFather)).comonFather(map.get(sib1),map.get(sib2));
    }

    private  static String intermediateNode (String grandParent ,String grandChild){
        TreeNode result = (map.get(grandParent)).getIntermediate(map.get(grandChild));
        if(result!= null)return result.data;

        return null;

    }

    //made for the case of id and follower
//    private boolean isAscenderOrDescender(String tag1 , String tag2){
//        return (map.get(tag1)).isDescendent(map.get(tag2))!=null  ||  (map.get(tag2)).isDescendent(map.get(tag1))!=null;
//    }
  
}



class TreeNode {
    String data;
    TreeNode [] children = new TreeNode[4];

    public TreeNode(String data , TreeNode[]children) {
        this.data = data;
        this.children = children;

    }
    public boolean isParent(TreeNode node){
        boolean success = false;
        for(TreeNode child : children){
            if(child!= null &&node == child) success =true;
        }
        return success;
    }

    public boolean comonFather(TreeNode sibilling1, TreeNode sibilling2){
        return  this.isParent(sibilling1) && this.isParent(sibilling2);
    }

    public TreeNode getIntermediate(TreeNode grandChild){

        for (TreeNode intermediateNode : children){
            if(intermediateNode!=null && intermediateNode.isParent(grandChild)){
                return intermediateNode;
            }
        }
        return null;
    }

    // public TreeNode isDescendent(TreeNode bigNode) {
    //     TreeNode n = null;

    //     for (TreeNode child : bigNode.children) {
    //         if (child != null) {
    //             if (child.equals(this)) {
    //                 return child;
    //             } else {
    //                 n = isDescendent(child);
    //                 if (n != null) {
    //                     return n;
    //                 }
    //             }
    //         }
    //     }

    //     return n;
    // }


}

