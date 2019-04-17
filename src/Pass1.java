import java.io.*;
import java.util.ArrayList;

public class Pass1 {

    String infile;
    Pass1(String infile)
    {
        this.infile=infile;
    }

    int compute() {
        int lineno = 0;
        try {
            FileReader fr = new FileReader(infile);    //main file.
            BufferedReader br = new BufferedReader(fr);

            FileReader fr_op = new FileReader("C:mot.txt");    //operand file.
            BufferedReader br_op = new BufferedReader(fr_op);
            ///////////////////////////////////
            //File file = new File("symbol.txt");
            FileWriter sfw = new FileWriter("symbol.txt");
            //sfw.flush();
            //file = new File("intermediate.txt");
            FileWriter ifw = new FileWriter("intermediate.txt");
            //ifw.flush();
            //file = new File("literal.txt");
            FileWriter lfw = new FileWriter("literal.txt");
            //lfw.flush();

            String optab[][] = new String[50][4];   //array declarations for displaying tables.
            ArrayList<String> symtab = new ArrayList<>();//String symtab[][]=new String[20][2];
            ArrayList<String> littab = new ArrayList<>();// String littab[][]=new String[20][2];
            ArrayList<Integer> pooltab = new ArrayList<>();//String pooltab[]=new String[10];

            ArrayList<Integer> symtad = new ArrayList<>();
            ArrayList<Integer> litad = new ArrayList<>();
            String line = null, line_mot = null;
            int address = 0, optptr = 0, symtptr = 0, litptr = 0, poolptr = 0;

            while ((line_mot = br_op.readLine()) != null) {                   //extract operand file into an array token_mot.
                String[] token = line_mot.split(" ");
                for (int m = 0; m < 3; m++) {  //token_mot[count_mot]=token[m]; count_mot++;}//
                    optab[optptr][m] = token[m];
                }
                optptr++;
            }

            while ((line = br.readLine()) != null) {
                lineno++;
                System.out.println("line no "+lineno+" started");
                String outline = null;
                String[] tokens = line.split(" ");              //splitting it into words.
                int length = tokens.length;
                int flag = 0;

                if (tokens[0].equalsIgnoreCase("START")) {
                    address = new Integer(tokens[1]).intValue();
                    ifw.write("(AD,01)(C," + tokens[1] + ")\n");
                    continue;
                } else if (tokens[0].equalsIgnoreCase("STOP")) {
                    ifw.write("(IS,00)\n");
                    continue;
                } else if (tokens[0].equalsIgnoreCase("ORIGIN")) {
                    String exp = tokens[1];
                    String exp1;
                    int ind, opval = 0;
                    if (exp.indexOf('+') > 0) {
                        opval = 1;
                        ind = exp.indexOf('+');

                    } else if (exp.indexOf('-') > 0) {
                        opval = 2;
                        ind = exp.indexOf('-');

                    } else {
                        address = new Integer(exp).intValue();
                        continue;
                    }

                    ////////////////////////
                    exp1 = exp.substring(0, ind);
                    exp = exp.substring(ind + 1);
                    flag = 0;
                    for (int i = 0; i < symtptr; i++) {
                        if (symtab.get(i).equalsIgnoreCase(exp1)) {
                            flag = 1;
                            address = Integer.parseInt(symtab.get(i));
                        }
                    }
                    if (flag == 0) {
                        System.out.println("Hlo"+tokens[2]);

                        return lineno;
                    }
                    if (opval == 1) {
                        address = address + Integer.parseInt(exp);
                    } else {
                        address = address - Integer.parseInt(exp);

                    }
                    ////////////////////////
                } else if (tokens[0].equalsIgnoreCase("LTORG")) {
                    //gives address to literal
                    flag = 0;
                    for (int i = 0; i < litptr; i++) {
                        if (litad.get(i).intValue() == 0) {
                            if (flag == 0) {
                                flag=1;
                                pooltab.add(poolptr, new Integer(address));
                                poolptr++;
                            }
                            litad.add(i, new Integer(address));
                            ifw.write("(DL,02)(c,"+litad.get(i).intValue()+")\n");
                            System.out.println("i="+i);
                            System.out.println("litptr="+litptr);
                            //System.out.println("i="+i);

                            address++;
                        }
                    }
                    continue;

                } else if (tokens[0].equalsIgnoreCase("END")) {
                    ifw.write("(AD,02)\n");
                    for (int i = 0; i < symtptr; i++) {
                        sfw.write(symtab.get(i) + "\t" + symtad.get(i) + "\n");
                    }
                    for (int i = 0; i < litptr; i++) {
                        lfw.write(littab.get(i) + "\t" + litad.get(i)+"\n");
                    }
                    ifw.close();
                    lfw.close();
                    sfw.close();
                    return -1;
                } else {

                }

                for (int j = 0; j < optptr; j++) {
                    if (tokens[0].equalsIgnoreCase(optab[j][0])) {
                        flag = 1;
                        outline = "(" + optab[j][1] + "," + optab[j][2] + ")";
                    }
                }
                //if first is a symbol
                if (flag == 0) {
                    symtab.add(tokens[0]);
                    symtptr++;
                    if (tokens[1].equalsIgnoreCase("DS")) {
                        symtad.add(address);
                        ifw.write("(DL,01)(C," + tokens[2] + ")\n");
                        address = address + Integer.parseInt(tokens[2]);
                        continue;
                    } else if (tokens[1].equalsIgnoreCase("DC")) {
                        symtad.add(address);

                        ifw.write("(DL,02)(C," + tokens[2] + ")\n");
                        address = address + 1;
                        continue;
                    } else if (tokens[1].equalsIgnoreCase("EQU")) {
                        String exp = tokens[1];
                        String exp1;
                        int ind, opval = 0, address1 = 0;
                        if (exp.indexOf('+') > 0) {
                            opval = 1;
                            ind = exp.indexOf('+');

                        } else if (exp.indexOf('-') > 0) {
                            opval = 2;
                            ind = exp.indexOf('-');

                        } else {
                            address1 = new Integer(exp).intValue();
                            symtad.add(address1);
                            ifw.write("(AD,04)(C," + address1 + ")\n");
                            continue;
                        }
                        exp1 = exp.substring(0, ind);
                        exp = exp.substring(ind + 1);
                        flag = 0;
                        for (int i = 0; i < symtptr; i++) {
                            if (symtab.get(i).equalsIgnoreCase(exp1)) {
                                flag = 1;
                                address1 = Integer.parseInt(symtab.get(i));
                            }
                        }
                        if (flag == 0) {

                            return lineno;
                        }
                        if (opval == 1) {
                            address1 = address1 + Integer.parseInt(exp);
                        } else {
                            address1 = address1 - Integer.parseInt(exp);

                        }
                        ifw.write("(AD,04)(C," + address1 + ")\n");
                        symtad.add(address1);

                    }else {
                        symtad.add(address);
                        for (int j = 0; j < optptr; j++) {
                            if (tokens[1].equalsIgnoreCase(optab[j][0])) {
                                flag = 1;
                                outline = "(" + optab[j][1] + "," + optab[j][2] + ")";
                                System.out.println(outline);
                                break;
                            }
                        }
                        if (flag == 0) {

                            return lineno;
                        }
                    }
                    flag = 0;
                    for (int j = 0; j < optptr; j++) {
                        if (tokens[2].equalsIgnoreCase(optab[j][0])) {
                            flag = 1;
                            if(optab[j][1].equalsIgnoreCase("RG")) {
                                outline = outline.concat("(" + optab[j][2] + ")");
                            }else {
                                outline = outline.concat("(" + optab[j][1] + "," + optab[j][2] + ")");
                            }
                        }
                    }
                    if (flag == 0) {
                        return lineno;
                    }
                    flag = 0;
                    if (tokens[4].equalsIgnoreCase("=")) {
                        littab.add(tokens[5]);
                        litad.add(new Integer(0));
                        outline=outline.concat("(L," + littab.indexOf(tokens[5]) + ")");
                        ifw.write(outline+"\n");
                        address = address + 1;
                        litptr++;
                        continue;
                    }else if (tokens[4].equalsIgnoreCase("AREG")) {
                        outline = "(1)";
                        ifw.write(outline+"\n");
                        address = address + 1;
                    } else if (tokens[4].equalsIgnoreCase("BREG")) {
                        outline = "(2)";
                        ifw.write(outline+"\n");
                        address = address + 1;
                    } else if (tokens[4].equalsIgnoreCase("CREG")) {
                        outline = "(3)";
                        ifw.write(outline);
                        address = address + 1;
                    } else {
                        flag = 0;
                        for (int j = 0; j < symtptr; j++) {
                            if (tokens[4].equalsIgnoreCase(symtab.get(j))) {
                                flag = 1;
                                outline =outline.concat( "(S," + j + ")");
                                ifw.write(outline+"\n");
                                break;
                            }
                        }
                        if (flag == 0) {

                            return lineno;
                        }else
                        {
                            continue;
                        }
                    }

                    address = address + 1;

                }
                ///////if first is not  a symbol

                if (tokens[1].equalsIgnoreCase("AREG")) {
                    outline = outline.concat("(1)");
                    System.out.println(outline);
                    //ifw.write(outline+"\n");
                    address = address + 1;
                } else if (tokens[1].equalsIgnoreCase("BREG")) {
                    outline = outline.concat("(2)");
                    System.out.println(outline);
                    //ifw.write(outline+"\n");
                    address = address + 1;
                } else if (tokens[1].equalsIgnoreCase("CREG")) {
                    outline = outline.concat("(3)");
                    System.out.println(outline);
                    //ifw.write(outline+"\n");
                    address = address + 1;
                } else {
                    int fl = 0;
                    for (int i = 0; i < symtptr; i++) {
                        if (symtab.get(i).equalsIgnoreCase(tokens[1])) {
                            fl = 1;
                            System.out.println(tokens[1]);

                            outline=outline.concat("(S," + i + ")");
                            // outline=outline.concat("vande");
                            System.out.println(outline);
                            //ifw.write(outline+"\n");
                            break;

                        }
                    }
                    if(fl==0) {
                        for (int i = 0; i < optptr; i++) {
                            if (optab[i][0].equalsIgnoreCase(tokens[1])) {
                                fl = 1;
                                outline= outline.concat("(" + optab[i][1] + "," + optab[i][2] + ")");
                                //ifw.write(outline+"\n");
                                break;

                            }
                        }
                    }
                    if (fl == 0) {
                        return lineno;
                    }else
                    {
                        ifw.write(outline+"\n");
                        continue;
                    }
                }
                /////////////////////

                if (tokens[3].equalsIgnoreCase("=")) {
                    littab.add(tokens[4]);
                    litad.add(new Integer(0));
                    outline=outline.concat("(L," + littab.indexOf(tokens[4]) + ")");
                    ifw.write(outline+"\n");
                    address = address + 1;
                    litptr++;
                    continue;
                }

                if (tokens[3].equalsIgnoreCase("AREG")) {
                    outline = outline.concat("(1)");
                    // ifw.write(outline+"\n");
                    address = address + 1;
                } else if (tokens[3].equalsIgnoreCase("BREG")) {
                    outline = outline.concat("(2)");
                    //ifw.write(outline+"\n");
                    address = address + 1;
                } else if (tokens[3].equalsIgnoreCase("CREG")) {
                    outline = outline.concat("(3)");
                    //ifw.write(outline+"\n");
                    address = address + 1;
                } else {
                    flag = 0;
                    for (int j = 0; j < symtptr; j++) {
                        if (tokens[3].equalsIgnoreCase(symtab.get(j))) {
                            flag = 1;
                            outline = outline.concat("(S," + j + ")");
                            //ifw.write(outline+"\n");
                            break;
                        }
                    }
                    if (flag == 0) {
                        return lineno;
                    }
                }
                ifw.write(outline+"\n");
                address = address + 1;


            }
            for (int i = 0; i < symtptr; i++) {
                sfw.write(symtab.get(i) + "\t" + symtad.get(i) + "\n");
            }
            for (int i = 0; i < litptr; i++) {
                lfw.write(littab.get(i) + "\t" + litad.get(i)+"\n");
            }
            ifw.close();
            lfw.close();
            sfw.close();
        }catch(Exception e)
        {
            System.out.println("Exception ccurred="+lineno);
            e.printStackTrace();
        }

        return -1;

    }
}
