import java.util.ArrayList;

public class scan {

    //累計 Token 數量
    //0-4   : Reserved word, Library name, Comment, Identifier, Constant
    //5-9   : Operator, Comparator, Bracket, Format specifier, Pointer
    //10-14 : Address, Punctuation, Printed token, 總token數, Undefinded Token
    private int[] cal_token = new int[15];

    //讀字元指針
    private int p;

    //Reserved word
    private String[] RESERVED_WORD = {"include","main","char","int","float","if","else","elseif","for","while","do","return","switch","case","printf","scanf"};

    //定義變數
    private ArrayList<String> INDENTIFIER_WORD = new ArrayList<>();

    //讀內容
    public void handle(String inputtext)
    {

        for(p = 0; p < inputtext.length(); p++)
        {
            //檢查是否為 Punctuation
            if(inputtext.charAt(p) == ',' || inputtext.charAt(p) == ';' || inputtext.charAt(p) == ':' || inputtext.charAt(p) == '#' || inputtext.charAt(p) == '\'' || inputtext.charAt(p) == '"')
            {
                cal_token[11]++;
                cal_token[13]++;
                System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to punctuation");
            }

            //檢查是否為 Bracket
            else if(inputtext.charAt(p) == '(' || inputtext.charAt(p) == ')' || inputtext.charAt(p) == '[' || inputtext.charAt(p) == ']' || inputtext.charAt(p) == '{' || inputtext.charAt(p) == '}')
            {
                cal_token[7]++;
                cal_token[13]++;
                System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to bracket");
            }

            //檢查是否為 Comparator
            else if((inputtext.charAt(p) == '<' || inputtext.charAt(p) == '!' || inputtext.charAt(p) == '=' || inputtext.charAt(p) == '>') && inputtext.charAt(p+1) == '=')
            {
                cal_token[6]++;
                cal_token[13]++;
                System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p++) + inputtext.charAt(p) + " with length 2 belongs to comparator");
            }
            else if(inputtext.charAt(p) == '<' || inputtext.charAt(p) == '>')
            {
                cal_token[6]++;
                cal_token[13]++;
                System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to comparator");
            }

            //檢查是否為 Comment
            else if(inputtext.charAt(p) == '/' && inputtext.charAt(p+1) == '/')
            {
                String temp = "";
                while(inputtext.charAt(p) != '\n') {
                    temp += inputtext.charAt(p++);
                }
                cal_token[2]++;
                cal_token[13]++;
                System.out.println(cal_token[13] + "\t token " + temp + " with length -1 belongs to comment");
            }
            else if(inputtext.charAt(p) == '/' && inputtext.charAt(p+1) == '*')
            {
                String temp = "";
                while(inputtext.charAt(p-1) != '/' || inputtext.charAt(p-2) != '*') {
                    temp += inputtext.charAt(p++);                    
                }
                cal_token[2]++;
                cal_token[13]++;
                System.out.println(cal_token[13] + "\t token " + temp + " with length -1 belongs to comment");
            }

            //檢查是否為 Operator
            else if((inputtext.charAt(p) == '+' && inputtext.charAt(p+1) == '+') || (inputtext.charAt(p) == '-' && inputtext.charAt(p+1) == '-'))
            {
                cal_token[5]++;
                cal_token[13]++;
                System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p++) + inputtext.charAt(p) + " with length 2 belongs to operator");
            }
            else if((inputtext.charAt(p) == '+' || inputtext.charAt(p) == '/' || inputtext.charAt(p) == '%' || inputtext.charAt(p) == '^' || inputtext.charAt(p) == '&' || inputtext.charAt(p) == '|' || inputtext.charAt(p) == '=') || ((inputtext.charAt(p) == '-' || inputtext.charAt(p) == '*') && (String.valueOf(inputtext.charAt(p-1)).matches("[a-zA-Z0-9_]") || inputtext.charAt(p-1) == ')' || inputtext.charAt(p+1) == ' ')))
            {
                cal_token[5]++;
                cal_token[13]++;
                System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to operator");
            }

            //檢查是否為 Constant
            else if(inputtext.charAt(p) == '-' || String.valueOf(inputtext.charAt(p)).matches("[0-9]"))
            {
                String temp = String.valueOf(inputtext.charAt(p++));
                while(String.valueOf(inputtext.charAt(p)).matches("[0-9]") || inputtext.charAt(p) == '.') {
                    temp += inputtext.charAt(p++);
                }
                p--;
                cal_token[4]++;
                cal_token[13]++;
                System.out.println(cal_token[13] + "\t token " + temp + " with length " + temp.length() + " belongs to comment");
            }

            //檢查是否為 Pointer
            else if(inputtext.charAt(p) == '*')
            {
                String temp = "*";
                for(++p; String.valueOf(inputtext.charAt(p)).matches("[a-zA-Z]"); p++) {
                    temp += inputtext.charAt(p);
                }
                p--;

                int accept = 0;
                for(int k = 0; k < INDENTIFIER_WORD.size(); k++) {
                    if(temp.equalsIgnoreCase(INDENTIFIER_WORD.get(k))) {
                        accept = 1;
                        cal_token[9]++;
                        cal_token[13]++;
                        System.out.println(cal_token[13] + "\t token " + temp + " with length " + temp.length() + " belongs to pointer");
                    }
                }

                if(accept == 0) {
                    cal_token[14]++;
                    System.out.println("\t\t The token '" + temp + "' is Undefinded'");
                }
            }

            //檢查 英文開頭 字串
            else if(String.valueOf(inputtext.charAt(p)).matches("[a-zA-Z]"))
            {
                String temp = String.valueOf(inputtext.charAt(p++));
                while(String.valueOf(inputtext.charAt(p)).matches("[a-zA-Z0-9_]")) {
                    temp += inputtext.charAt(p++);
                }

                int accept = 0; //若有對應到相符字串回傳1
                //判斷是否為 Reserved word
                for(int i = 0; i < 16; i++)
                {
                    if(temp.equalsIgnoreCase(RESERVED_WORD[i]))
                    {
                        accept = 1;
                        cal_token[0]++;
                        cal_token[13]++;
                        System.out.println(cal_token[13] + "\t token " + temp + " with length " + temp.length() + " belongs to reserved word");

                        //處理 scanf 和 printf
                        if(temp.equalsIgnoreCase("scanf") || temp.equalsIgnoreCase("printf")) {
                            int have_adr = 0, accept2 = 0; //檢查是否有 & 和 定義字串相符檢查
                            for(; inputtext.charAt(p) != ';'; p++) {
                                if(inputtext.charAt(p) == '(' || inputtext.charAt(p) == ')') {
                                    cal_token[7]++;
                                    cal_token[13]++;
                                    System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to bracket");
                                }
                                if(inputtext.charAt(p) == '"') {
                                    cal_token[11]++;
                                    cal_token[13]++;
                                    System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to punctuation");

                                    //處理字串
                                    String tempi = "";
                                    for(++p;; p++) {
                                        if(inputtext.charAt(p) == '\\'|| inputtext.charAt(p) == '%') {
                                            cal_token[8]++;
                                            cal_token[13]++;
                                            System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p++) + inputtext.charAt(p) + " with length 2 belongs to format specifier");
                                        }
                                        else if(inputtext.charAt(p) == '"' && tempi.equalsIgnoreCase("")) {
                                            cal_token[11]++;
                                            cal_token[13]++;
                                            System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to punctuation");
                                            break;
                                        }
                                        else if((inputtext.charAt(p) == ' ' || inputtext.charAt(p) == '"') && !tempi.equalsIgnoreCase("")) {
                                            cal_token[12]++;
                                            cal_token[13]++;
                                            System.out.println(cal_token[13] + "\t token " + tempi + " with length " + tempi.length() + " belongs to print token");
                                            tempi = "";
                                            if(inputtext.charAt(p) == '"') {
                                                cal_token[11]++;
                                                cal_token[13]++;
                                                System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to punctuation");
                                                break;
                                            }
                                        }
                                        else
                                            tempi += inputtext.charAt(p);
                                    }
                                }

                                //後續
                                if(inputtext.charAt(p) == ',') {
                                    cal_token[11]++;
                                    cal_token[13]++;
                                    System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to punctuation");
                                }
                                
                                //檢查定義與否
                                if(inputtext.charAt(p) == '&')
                                    have_adr = 1;
                                String temp_ = "";
                                if(String.valueOf(inputtext.charAt(p)).matches("[a-zA-Z]")){
                                    temp_ = String.valueOf(inputtext.charAt(p++));
                                    while(String.valueOf(inputtext.charAt(p)).matches("[a-zA-Z0-9_]")) {
                                        temp_ += inputtext.charAt(p++);
                                    }
                                    p--;
                                    //檢查是否為 INDENTIFIER
                                    for(int k = 0; k < INDENTIFIER_WORD.size(); k++)
                                    {
                                        if(temp_.equalsIgnoreCase(INDENTIFIER_WORD.get(k))) {
                                            accept2 = 1;
                                            if(have_adr == 1) {
                                                temp_ = "&" + temp_;
                                                cal_token[10]++;
                                                cal_token[13]++;
                                                System.out.println(cal_token[13] + "\t token " + temp_ + " with length " + temp_.length() + " belongs to address");
                                                temp_ = "";
                                                have_adr = 0;
                                            }
                                            else {
                                                cal_token[3]++;
                                                cal_token[13]++;
                                                System.out.println(cal_token[13] + "\t token " + temp_ + " with length " + temp_.length() + " belongs to indentifier");
                                            }
                                        }
                                    }

                                    if(accept2 == 0) {
                                        cal_token[14]++;
                                        System.out.println("\t\t The token '" + temp_ + "' is Undefinded'");
                                    }
                                }
                            }
                        }

                        //處理 Library
                        if(temp.equalsIgnoreCase("include")) {
                            String temp1 = "";
                            for(; inputtext.charAt(p-1) != '>'; p++) {
                                if(inputtext.charAt(p) != ' ')
                                    temp1 += inputtext.charAt(p);
                            }
                            cal_token[1]++;
                            cal_token[13]++;
                            System.out.println(cal_token[13] + "\t token " + temp1 + " with length " + temp1.length() + " belongs to library");
                        }

                        //宣告 indentifier
                        if(temp.equalsIgnoreCase("int") || temp.equalsIgnoreCase("float") || temp.equalsIgnoreCase("char")) {
                            int have_pot = 0;
                            for(; inputtext.charAt(p) != ';'  && inputtext.charAt(p) != ')' ; p++) {                                
                                if(inputtext.charAt(p) == ',') {
                                    cal_token[11]++;
                                    cal_token[13]++;
                                    System.out.println(cal_token[13] + "\t token " + inputtext.charAt(p) + " with length 1 belongs to punctuation");
                                }
                                else if(inputtext.charAt(p) == '*')
                                    have_pot++;
                                else if(String.valueOf(inputtext.charAt(p)).matches("[a-zA-Z]")) {
                                    String temp2 = "";
                                    while(String.valueOf(inputtext.charAt(p)).matches("[a-zA-Z0-9_]")) {
                                        temp2 += inputtext.charAt(p++);
                                    }
                                    p--;
                                    if(have_pot == 1) {
                                        temp2 = "*" + temp2;
                                        INDENTIFIER_WORD.add(temp2);
                                        cal_token[9]++;
                                        cal_token[13]++;
                                        System.out.println(cal_token[13] + "\t token " + temp2 + " with length " + temp2.length() + " belongs to pointer");
                                        temp2 = "";
                                        have_pot = 0;
                                    }
                                    else {
                                        INDENTIFIER_WORD.add(temp2);
                                        cal_token[3]++;
                                        cal_token[13]++;
                                            System.out.println(cal_token[13] + "\t token " + temp2 + " with length " + temp2.length() + " belongs to indentifier");
                                    }
                                }
                            }
                        }
                    }
                }

                //檢查是否為 indentifier
                for(int k = 0; k < INDENTIFIER_WORD.size(); k++) {
                    if(temp.equalsIgnoreCase(INDENTIFIER_WORD.get(k))) {
                        accept = 1;
                        cal_token[3]++;
                        cal_token[13]++;
                        System.out.println(cal_token[13] + "\t token " + temp + " with length " + temp.length() + " belongs to indentifier");
                        break;
                    }
                }

                if(accept == 0) {
                    cal_token[14]++;
                    System.out.println("\t\t The token '" + temp + "' is Undefinded'");
                    for(;inputtext.charAt(p) != '\n';p++) ;
                }
                p--;
            }
        }
    }

    public int getTokenValue(int o)
    {
        return cal_token[o];
    }
}