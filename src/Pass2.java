import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
public class Pass2
{
	BufferedReader b1;
	BufferedReader b2;
	BufferedReader b3;

	Pass2(String syttab,String inttab,String littab)throws IOException
	{
		b1 = new BufferedReader(new FileReader(inttab));
		b2 = new BufferedReader(new FileReader(syttab));
		b3 = new BufferedReader(new FileReader(littab));

	}

	public void compute() throws IOException
	{

		FileWriter f1 = new FileWriter(System.getProperty("user.dir")+"\\Pass2.txt");//get refrence to pass2.txt file
		HashMap<Integer, String> symSymbol = new HashMap<Integer, String>();
		HashMap<Integer, String> litSymbol = new HashMap<Integer, String>();
		HashMap<Integer, String> litAddr = new HashMap<Integer, String>();
		String s;
		int symtabPointer=0,littabPointer=0,offset;

		while((s=b2.readLine())!=null)
		{
			String word[]=s.split("\t");
			//System.out.println("-->>>"+symtabPointer++ +"-->"+word[1]);
			symSymbol.put(symtabPointer++,word[1]);
		}

		while((s=b3.readLine())!=null)
		{
			String word[]=s.split("\t");
			litSymbol.put(littabPointer,word[0]);
			litAddr.put(littabPointer++,word[1]);
		}
//understood to baghu
		while((s=b1.readLine())!=null){

			if(s.substring(1,6).compareToIgnoreCase("IS,00")==0){
				f1.write("+ 00 0 000\n");
			}
			else if(s.substring(1,3).compareToIgnoreCase("IS")==0){
				f1.write("+ "+s.substring(4,6)+" ");

				if(s.charAt(9)==')')
				{
					f1.write(s.charAt(8)+" ");
					offset=3;
				}
				else
				{
					f1.write("0 ");
					offset=0;
				}

				if(s.charAt(8+offset)=='S')
					f1.write(symSymbol.get(Integer.parseInt(s.substring(10+offset,s.length()-1)))+"\n");
				else if(s.charAt(8+offset)=='C'&&s.charAt(9+offset)=='C')
				{
					f1.write(litAddr.get(Integer.parseInt(s.substring(11+offset,s.length()-1)))+"\n");

				}
				else{
					f1.write(litAddr.get(Integer.parseInt(s.substring(10+offset,s.length()-1)))+"\n");
				}
			}

			else if(s.substring(1,6).compareToIgnoreCase("DL,01")==0){
				String s1=s.substring(10,s.length()-1);
				String s2="";

				for(int i=0;i<3-s1.length();i++)
					s2+="0";

				s2+=s1;
				f1.write("+ 00 0 "+s2+"\n");
			}

			else{f1.write("\n");}
		}

		f1.close();
		b1.close();
		b2.close();
		b3.close();
	}
}


