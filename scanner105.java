import java.util.Scanner;
import java.io.*;

public class scanner105 {

    public static Scanner sc = new Scanner(System.in);
    public static scan scan_use = new scan();

    //主程式
    public static void main(String[] args) throws FileNotFoundException {
        String all_file = "";


        //讀入檔案
        System.out.println("請輸入檔案名稱(需含副檔名)");
        String file = sc.next();
        Scanner inputStream = null;
        try
        {
            inputStream = new Scanner(new FileInputStream(file));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File : " + file + " was not found,");
            System.out.println("or could not be opened.");
            System.exit(0);
        }

        while(inputStream.hasNext()) {
            all_file += inputStream.nextLine();
            all_file += "\n";  
        }
        inputStream.close();
        
        //重新定義OUT
        PrintStream out = new PrintStream(new
            BufferedOutputStream(new FileOutputStream("scanner_" + file, true)));
            System.setOut(out); // 轉換輸出流
        
        scan_use.handle(all_file);

        int[] cal_token = new int[15];
        for(int m=0; m < 15; m++) {
            cal_token[m] = scan_use.getTokenValue(m);
        }

        System.out.println("");
        System.out.println("\nTotal : " + cal_token[13] + "tokens\n");
        System.out.println("");
        if(cal_token[0] != 0)
            System.out.println("Reserved word : " + cal_token[0]);
        if(cal_token[1] != 0)
            System.out.println("Library name : " + cal_token[1]);
        if(cal_token[2] != 0)
            System.out.println("Comment : " + cal_token[2]);
        if(cal_token[3] != 0)
            System.out.println("Identifier : " + cal_token[3]);
        if(cal_token[4] != 0)
            System.out.println("Constant : " + cal_token[4]);
        if(cal_token[5] != 0)
            System.out.println("Operator : " + cal_token[5]);
        if(cal_token[6] != 0)
            System.out.println("Comparator : " + cal_token[6]);
        if(cal_token[7] != 0)
            System.out.println("Bracket : " + cal_token[7]);
        if(cal_token[8] != 0)
            System.out.println("Format specifier : " + cal_token[8]);
        if(cal_token[9] != 0)
            System.out.println("Pointer : " + cal_token[9]);
        if(cal_token[10] != 0)
            System.out.println("Address : " + cal_token[10]);
        if(cal_token[11] != 0)
            System.out.println("Punctuation : " + cal_token[11]);
        if(cal_token[12] != 0)
            System.out.println("Printed token : " + cal_token[12]);

        out.close();

    }
}