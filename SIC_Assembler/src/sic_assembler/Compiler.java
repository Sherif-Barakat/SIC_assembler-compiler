package sic_assembler;
import java.io.*;  
import java.util.*;

public class Compiler {
    
    String[] allowed_char={"x","y","z","a"};
    String[] identifiers={"int","if","OP(+)","OP(-)","OP(*)"};
    String[] allowed_comparisons={"<","=",">"};  
    static int i=1;
    int flag=0;
    
    void Compile(){
        try{
            File comp = new File("Example.txt");
            Scanner ReaderA = new Scanner(comp);
            
            //Lexical Analysis
            LinkedList<String> column1=new LinkedList<String>();
            LinkedList<String> column2=new LinkedList<String>();
            LinkedList<String> column3=new LinkedList<String>();
            while(ReaderA.hasNextLine()){
                String dataA = ReaderA.nextLine();
                dataA=dataA.trim();
                String[] datasplitA=dataA.split("\\s");
                if(datasplitA.length!=3){
                    System.out.println("Lexical Error detected in line ("+i+"). Exiting compiler.\n\n");
                    System.exit(0);
                    break;
                }
                
                for(int j=0;j<5;j++){
                    if(!datasplitA[0].equals(identifiers[j]))
                        flag=1;
                    else{
                        flag=0;
                        break;
                    }
                }
                if (flag==1){
                    
                    System.out.println("Lexical Error detected in line ("+i+"). Exiting compiler.\n\n");
                    System.exit(0);
                    break;
                }
                else
                    column1.add(datasplitA[0]);
                flag=0;
                for(int j=0;j<5;j++){
                    if(!Character.toString(datasplitA[1].charAt(0)).equals(allowed_char[j]))
                        flag=1;
                    else{
                        flag=0;
                        break;
                    }
                }
                if (flag==1){
                    System.out.println("Lexical Error detected in line ("+i+"). Exiting compiler.\n\n");
                    System.exit(0);
                    break;
                }
                else
                    column2.add(datasplitA[1]);
                flag=0;
                column3.add(datasplitA[2]);
                i++;   
            }
            
            LinkedList <String> outputC1=new LinkedList <String> ();
            LinkedList <String> outputC2=new LinkedList <String> ();
            LinkedList <String> outputC3=new LinkedList <String> ();
            LinkedList <String> VarC1=new LinkedList <String> ();
            LinkedList <String> VarC2=new LinkedList <String> ();
            LinkedList <String> VarC3=new LinkedList <String> ();
            
            for(int j=0;j<i-1;j++){
                try{
                    if(Integer.parseInt(column3.get(j))>0){
                        VarC1.add(column3.get(j));
                        VarC2.add("WORD");
                        VarC3.add(column3.get(j));  
                    }      
                }
                catch(Exception e){
                    
                }
            }
                       
            outputC1.add("PROGRAM"); outputC2.add("START");outputC3.add("0000");
            
            //parser
            for(int j=0;j<i-1;j++){
                switch(column1.get(j)){
                    
                    case "int":
                        
                        outputC1.add("      ");
                        outputC1.add("      ");
                        outputC2.add("LDA");
                        outputC2.add("STA");
                        outputC3.add(column3.get(j));
                        outputC3.add(column2.get(j));
                       
                        VarC1.add(column2.get(j));
                        VarC2.add("RESW");
                        VarC3.add("1");
                        break;
                        
                    case "if":
                        
                        outputC1.add("      ");
                        outputC1.add("      ");
                        outputC1.add("      ");
                        outputC2.add("LDA");
                        outputC2.add("COMP");
                        switch(Character.toString(column2.get(j).charAt(1))){
                            case "<":
                                outputC2.add("JLT");
                                break;
                            case ">":
                                outputC2.add("JGT");
                                break;
                            case "=":
                                outputC2.add("JEQ");
                                break;
                            default:
                                System.out.println("Syntax error detected. Exiting compiler.");
                                System.exit(0);
                                break;
                        }
                        outputC3.add(Character.toString(column2.get(j).charAt(0)));
                        outputC3.add(column3.get(j));
                        outputC3.add("LOOP"+Integer.toString(j));
                        outputC1.add("LOOP"+Integer.toString(j));
                        outputC2.add("LDA");
                        outputC3.add(column2.get(j+1));
                        outputC1.add("      ");
                        if(column1.get(j+1).equals(identifiers[2])){
                            outputC2.add("ADD");
                            outputC3.add(column3.get(j+1));
                        }else if(column1.get(j+1).equals(identifiers[3])){
                            outputC2.add("SUB");
                            outputC3.add(column3.get(j+1));
                        }else if(column1.get(j+1).equals(identifiers[4])){
                            outputC2.add("MUL");
                            outputC3.add(column3.get(j+1));
                        }
                        else
                        {
                            System.out.println("Syntax error detected. Exiting compiler.");
                            System.exit(0);
                            break;
                        }
                        outputC1.add("      ");
                        outputC2.add("RSUB");
                        outputC3.add("      ");
                        j++;
                        break;
                    case "OP(+)":
                        outputC1.add("      ");
                        outputC1.add("      ");
                        outputC1.add("      ");
                        outputC2.add("LDA");
                        outputC3.add(column2.get(j));
                        outputC2.add("ADD");
                        outputC3.add(column3.get(j));
                        outputC2.add("STA");
                        outputC3.add(column2.get(j));
                        break;
                    case "OP(-)":
                        outputC1.add("      ");
                        outputC1.add("      ");
                        outputC1.add("      ");
                        outputC2.add("LDA");
                        outputC3.add(column2.get(j));
                        outputC2.add("SUB");
                        outputC3.add(column3.get(j));
                        outputC2.add("STA");
                        outputC3.add(column2.get(j));
                        break;
                    case "OP(*)":    
                        outputC1.add("      ");
                        outputC1.add("      ");
                        outputC1.add("      ");
                        outputC2.add("LDA");
                        outputC3.add(column2.get(j));
                        outputC2.add("MUL");
                        outputC3.add(column3.get(j));
                        outputC2.add("STA");
                        outputC3.add(column2.get(j));
                        break;
                    default:
                        System.out.println("Syntax error detected. Exiting compiler.");
                        System.exit(0);
                        break;
                        
                }
            }
          // Code generation
            try {
                FileWriter myWriter = new FileWriter("compTest.txt");
               
                Iterator <String> iteratorM1=outputC1.iterator();
                Iterator <String> iteratorM2=outputC2.iterator();
                Iterator <String> iteratorM3=outputC3.iterator();
                while(iteratorM1.hasNext()){
                    myWriter.write(iteratorM1.next()+" "+iteratorM2.next()+" "+iteratorM3.next()+"\n");
                }
               
                Iterator<String> iterator1=VarC1.iterator();
                Iterator<String> iterator2=VarC2.iterator();
                Iterator<String> iterator3=VarC3.iterator();
                while(iterator1.hasNext()){
                    myWriter.write(iterator1.next()+" "+iterator2.next()+" "+iterator3.next()+"\n");
                }
                myWriter.write("      "+" "+"END"+" "+"PROGRAM"+"\n");
                System.out.println("Done");
                myWriter.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        catch(Exception e){
             System.out.println(e);
        }
    }
}