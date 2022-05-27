package sic_assembler;
import java.io.*;  
import java.util.Scanner;
import java.util.Hashtable;
import java.lang.String;
import java.util.Enumeration;
import java.util.Random;

public class Pass1 {
    Random rand=new Random();    
    String[] directives={"START","END","BYTE","WORD","RESB","RESW"};
    Hashtable<Integer,String> symtab =new Hashtable<>();
    static int[] locctr=new int[100];
    String loc;
    String first_label;
    int i=0,j=20;
    
    int symtabcon(){
        
        try {
            
            File assembly = new File("compTest.txt");
            Scanner ReaderA = new Scanner(assembly);
            
            while(ReaderA.hasNextLine()){
                
                String dataA = ReaderA.nextLine();
                dataA=dataA.trim();
                String[] datasplitA=dataA.split("\\s");
                
                if (datasplitA.length==3){
                    if(datasplitA[1].equals(directives[0])){
                        first_label=datasplitA[0];
                        if(Integer.parseInt(datasplitA[2],16)>0)
                            locctr[0]=Integer.parseInt(datasplitA[2],16);
                        else
                            locctr[0]=rand.nextInt(1001)+4096;
                        
                        //symtab.put(1,datasplitA[0]+" "+Integer.toHexString(locctr[0]));
                        locctr[i]=locctr[0];
                            
                    }
                    else if(datasplitA[0].equals(directives[1])){
                        
                        break;
                        
                    }
                    else if(datasplitA[1].equals(directives[4])&&Integer.parseInt(datasplitA[2])>1){
                        locctr[i+1]=locctr[i]+Integer.parseInt(datasplitA[2]);
                        symtab.put(j,datasplitA[0]+" "+Integer.toHexString(locctr[i]+locctr[0]));
                        j--;
                    }
                    else if(datasplitA[1].equals(directives[5])&&Integer.parseInt(datasplitA[2])>1){
                        locctr[i+1]=locctr[i]+3*Integer.parseInt(datasplitA[2],16);
                        symtab.put(j,datasplitA[0]+" "+Integer.toHexString(locctr[i]+locctr[0]));
                        j--;
                    }
                    else if(datasplitA[1].equals(directives[2])&&datasplitA[2].charAt(0)=='X'){
                        locctr[i+1]=locctr[i]+1;
                        symtab.put(j,datasplitA[0]+" "+Integer.toHexString(locctr[i]+locctr[0]));
                        j--;
                    }
               
                    else{
                        locctr[i+1]=locctr[i]+3;
                        symtab.put(j,datasplitA[0]+" "+Integer.toHexString(locctr[i]+locctr[0]));
                        j--;
                    }
                    
                }
           
                else
                    locctr[i+1]=locctr[i]+3;
                i++;
            }  
        }
        catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                
        }
        try {       
            Enumeration <Integer> keys = symtab.keys();
            Enumeration <String> Vals=symtab.elements();
            FileWriter myWriter = new FileWriter("SymTab.txt");
            myWriter.write(first_label+" "+Integer.toHexString(locctr[0])+"\n");
            while(keys.hasMoreElements()&& Vals.hasMoreElements()){
               myWriter.write(Vals.nextElement()+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the symbol table.");
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
           
        }
        return i;
    }
}
