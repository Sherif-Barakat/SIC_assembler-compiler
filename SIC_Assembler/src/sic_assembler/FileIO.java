package sic_assembler;
import java.io.*;  
import java.util.Scanner;
import java.lang.String;
import java.util.Arrays;

public class FileIO {
    
    String[] directives={"START","END","BYTE","WORD","RESB","RESW"};
    Pass1 p1=new Pass1();
    int number_of_lines=p1.symtabcon();
    int count=0;
    static int locc=0;
    String[] opcode=new String[number_of_lines+1];
    String[] labels=new String[number_of_lines+1];
    String[] address=new String[number_of_lines+1];
    String[] instructions=new String[number_of_lines+1];
    String[] symbols=new String[number_of_lines+1];
    String[] location=new String[number_of_lines+1];
    int[] line=new int[number_of_lines+1];
    String[] lineS=new String[number_of_lines+1];
    int i=0;
    int j=0;
    
    void Readf(){
 
        try {
            
            File assembly = new File("compTest.txt");
            Scanner ReaderA = new Scanner(assembly);
            
            
            while (ReaderA.hasNextLine()) {
                File symtab=new File("SymTab.txt");
                Scanner ReaderS=new Scanner(symtab);
                File optab=new File("OpTab.txt");
                Scanner ReaderO=new Scanner(optab);
                
                String dataA = ReaderA.nextLine();
                dataA=dataA.trim();
                String[] datasplitA=dataA.split("\\s");

                switch (datasplitA.length){
                    case 1:
                        locc+=3;
                        location[count+1]=Integer.toHexString(locc);
                        address[count]="0000";
                        instructions[count]="RSUB";
                        labels[count]="";
                        symbols[count]="";
                        opcode[count]="4C";
                        line[count]=line[count-1]+1;
                        break;
                        
                    case 2: 
                        
                        labels[count]=" ";
                        while(ReaderO.hasNextLine()){
                            String dataO = ReaderO.nextLine();
                            dataO=dataO.trim();
                            String[] datasplitO=dataO.split("\\s");
                            if(datasplitA[0].equals(directives[1])){
                              /* 
                                instructions[count]=directives[1];
                                labels[count-1]=" ";
                                opcode[count-1]=" ";
                                address[count-1]=" ";
                                symbols[count-1]=datasplitA[1];
                                line[count]=line[count-1]+1;
                                location[count+1]=" ";*/
                              break;
                               
                            }
                            else if(datasplitA[0].equals(datasplitO[0])){
                                instructions[count]=datasplitO[0];
                                opcode[count]=datasplitO[1];
                                String tempSp=datasplitA[1].replaceAll(",X","");
                                while(ReaderS.hasNextLine()){
                                    String dataS = ReaderS.nextLine();
                                    dataS=dataS.trim();
                                    String[] dataSsplit=dataS.split("\\s");
                                    
                                    if(datasplitA[1].equals(dataSsplit[0])){
                                        symbols[count]=dataSsplit[0]; 
                                        line[count]=line[count-1]+1;
                                        if(datasplitA[0].equals(directives[4])){
                                            address[count]=dataSsplit[1];
                                            locc=locc+Integer.parseInt(dataSsplit[1]);
                                            location[count+1]=Integer.toHexString(locc);
                                        }
                                        else if(datasplitA[0].equals(directives[5])){
                                            address[count]=dataSsplit[1];
                                            locc=locc+(3*Integer.parseInt(dataSsplit[1],16));
                                            location[count+1]=Integer.toHexString(locc);
                                        }

                                        else{
                                            address[count]=dataSsplit[1];
                                            locc+=3;
                                            location[count+1]=Integer.toHexString(locc);
                                        }
                                    }
                                    else if(tempSp.equals(dataSsplit[0])){ 
                                            symbols[count]=datasplitA[1]; 
                                            line[count]=line[count-1]+1;
                                            locc+=3;
                                            address[count]=Integer.toHexString((Integer.parseInt("8000",16))+Integer.parseInt(dataSsplit[1],16));
                                            location[count+1]=Integer.toHexString(locc);
                                    }
                                   
                                }  
                            }
                           
                        }
                        
                        break;
                    case 3:
                        labels[count]=datasplitA[0];
                        
                        while(ReaderO.hasNextLine()){
                            String dataO = ReaderO.nextLine();
                            dataO=dataO.trim();
                            String[] datasplitO=dataO.split("\\s");
                            if(datasplitA[1].equals(directives[0])){
                                instructions[0]=directives[0];
                                locc=p1.locctr[0];
                                location[0]=Integer.toHexString(locc);
                                symbols[0]=location[0];
                                opcode[0]="";
                                address[0]="";
                                line[0]=1;
                                location[count+1]=Integer.toHexString(locc);
                                break;
                            }
                            else if(datasplitA[1].equals(datasplitO[0])){
                                instructions[count]=datasplitO[0];
                                opcode[count]=datasplitO[1];
                                while(ReaderS.hasNextLine()){
                                    String dataS = ReaderS.nextLine();
                                    dataS=dataS.trim();
                                    String[] dataSsplit=dataS.split("\\s");
                                    if(datasplitA[2].equals(dataSsplit[0])){
                                        symbols[count]=dataSsplit[0];
                                        line[count]=line[count-1]+1;
                                        if(datasplitA[1].equals(directives[4])){
                                            address[count]=dataSsplit[1];
                                            locc+=Integer.parseInt(dataSsplit[1]);
                                            location[count+1]=Integer.toHexString(locc);
                                        }
                                        else if(datasplitA[1].equals(directives[5])){
                                            address[count]=dataSsplit[1];
                                            locc+=(3*Integer.parseInt(dataSsplit[1],16));
                                            location[count+1]=Integer.toHexString(locc);
                                        }

                                        else{
                                            address[count]=dataSsplit[1];
                                            locc+=3;
                                            location[count+1]=Integer.toHexString(locc);
                                        }
                                    } 
                                }  
                            }
                            else if(datasplitA[1].equals(directives[2])||datasplitA[1].equals(directives[3])||datasplitA[1].equals(directives[4])
                                    ||datasplitA[1].equals(directives[5])){
                                while(ReaderA.hasNextLine()&&ReaderS.hasNextLine()){
                                    String dataS = ReaderS.nextLine();
                                    dataS=dataS.trim();
                                    String[] dataSsplit=dataS.split("\\s");
                                    if(datasplitA[0].equals(dataSsplit[0])&&datasplitA[1].equals(directives[2])){
                                        instructions[count]=directives[2];
                                        labels[count]=datasplitA[0];
                                        String[] byteSplit=datasplitA[2].split("'");
                                        String ascii="";
                                        if (byteSplit[0].equals("C")){
                                            for(j=0;j<byteSplit[1].length();j++){
                                                ascii=ascii+Integer.toHexString((int) byteSplit[1].charAt(j));
                                            }
                                            address[count]=ascii;
                                            locc+=3;
                                        }
                                        else{
                                            address[count]=byteSplit[1];
                                            locc+=1;
                                        }
                                        
                                        opcode[count]="";
                                        line[count]=line[count-1]+1;
                                        
                                        location[count+1]=Integer.toHexString(locc);
                                        symbols[count]=datasplitA[2];
                                    }
                                    else if(datasplitA[1].equals(directives[5])&&datasplitA[0].equals(dataSsplit[0])){
                                        instructions[count]=directives[5];
                                        labels[count]=datasplitA[0];
                                        address[count]="";
                                        opcode[count]="";
                                        line[count]=line[count-1]+1;
                                        symbols[count]=datasplitA[2];
                                        locc+=(3*Integer.parseInt(datasplitA[2],16));
                                        location[count+1]=Integer.toHexString(locc);
                                    }
                                    else if(datasplitA[1].equals(directives[4])&&datasplitA[0].equals(dataSsplit[0])){
                                        instructions[count]=directives[4];
                                        labels[count]=datasplitA[0];
                                        address[count]="";
                                        opcode[count]="";
                                        symbols[count]=datasplitA[2];
                                        locc+=(1*Integer.parseInt(datasplitA[2]));
                                        line[count]=line[count-1]+1;
                                        location[count+1]=Integer.toHexString(locc);
                                    }
                                    else if(datasplitA[1].equals(directives[3])&&datasplitA[0].equals(dataSsplit[0])){
                                        instructions[count]=directives[3];
                                        labels[count]=datasplitA[0];
                                        address[count]=Integer.toHexString(0000+Integer.parseInt(datasplitA[2]));
                                        opcode[count]="";
                                        line[count]=line[count-1]+1;
                                        symbols[count]=datasplitA[2];
                                        locc+=3;
                                        location[count+1]=Integer.toHexString(locc);
                                    }
                                }
                            
                            }
                        }
                        break;
                   
                    default:
                        break;
               }
                
                
               count++; 
            }
            
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            
        }
      
        labels[count-1]="   ";
        opcode[count-1]="   ";
        address[count-1]="   ";
        location[count-1]="    ";
        instructions[count-1]=directives[1];
        symbols[count-1]=labels[1];
        line[count-1]=line[count-2]+1;
        try {
            FileWriter myWriter = new FileWriter("Output.txt");
            myWriter.write("Line  Location   Source statements     Object code\n");
            for(i=0;i<count;i++){
                lineS[i]=Integer.toString(line[i]);
                if(lineS[i].length()==1)
                    lineS[i]=lineS[i]+" ";
                if(labels[i].length()==0)
                    labels[i]=labels[i]+("      ");
                if (labels[i].length()==1)
                    labels[i]=labels[i]+("     ");
                if (labels[i].length()==3)
                    labels[i]=labels[i]+("   ");
                if (labels[i].length()==4)
                    labels[i]=labels[i]+("  ");
                if (labels[i].length()==5)
                    labels[i]=labels[i]+(" ");
                if (instructions[i].length()==1)
                    instructions[i]=instructions[i]+("    ");
                if (instructions[i].length()==2)
                    instructions[i]=instructions[i]+("   ");
                if (instructions[i].length()==3)
                    instructions[i]=instructions[i]+("  ");
                if (instructions[i].length()==4)
                    instructions[i]=instructions[i]+(" ");
                if (symbols[i].length()==0)
                    symbols[i]=symbols[i]+("        ");
                if (symbols[i].length()==1)
                    symbols[i]=symbols[i]+("       ");
                if (symbols[i].length()==1)
                    symbols[i]=symbols[i]+("      ");
                if (symbols[i].length()==3)
                    symbols[i]=symbols[i]+("     ");
                if (symbols[i].length()==4)
                    symbols[i]=symbols[i]+("    ");
                if (symbols[i].length()==5)
                    symbols[i]=symbols[i]+("   ");
                if (symbols[i].length()==6)
                    symbols[i]=symbols[i]+("  ");
                myWriter.write(lineS[i]+"    "+location[i]+"      "+labels[i]+" "+instructions[i]+" "+symbols[i]+"  "+opcode[i]+address[i]+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the output file.");
            FileWriter Writer2 = new FileWriter("BinaryOBcode.txt");
            Writer2.write("Binary object code \n");
            String[] tobinary=new String[number_of_lines+1];
            int temp;
            for(i=0;i<count-1;i++){
                opcode[i]=opcode[i].trim();
                address[i]=address[i].trim();
                if(!opcode[i].equals("")&&!address[i].equals("")){
                    tobinary[i]=opcode[i]+address[i];
                    temp=Integer.parseInt(tobinary[i],16);
                    tobinary[i]=Integer.toBinaryString(temp);
                    if(tobinary[i].length()<24){
                        char[] zeroes=new char[24-tobinary[i].length()];
                        Arrays.fill(zeroes, '0');
                        tobinary[i]=new String(zeroes)+tobinary[i];
                    }
                    Writer2.write(tobinary[i]+"\n");
                }
            }
            Writer2.close();
            System.out.println("Successfully wrote to the binary file");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        
        }
        
    }
}
