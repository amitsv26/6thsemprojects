import java.util.*;
import java.util.regex.Pattern;

public class prc {
    static int nt;
    static ArrayList<Production> gram=new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
	    String firstcopy,followcopy;
//         
//        S->ACB
//        A->BCa/da
//        B->g/#
//        C->h/#
        String pe[][]=new String[200][200];
        
        System.out.print("Enter Number of Non terminals: ");
        nt=sc.nextInt();
        for(int i=1;i<=nt;i++)
        {
            System.out.print("Enter LHS of production "+i+": ");
            String lhs=sc.next();
            Production p=new Production(lhs);
            System.out.print("Enter number of RHS: ");
            int r=sc.nextInt();
            for(int h=0;h<r;h++)
                p.rhs.add(sc.next());
            gram.add(p);
        }
        System.out.print("Number of terminals: ");
        int T=sc.nextInt();
        String t="";
        for(int f=0;f<T;f++)
        {
            System.out.print("Enter terminal: ");
            t+=sc.next();
        }
        t+="$";
        for(int i=0;i<nt;i++)
        {
            gram.get(i).first=find_first(gram.get(i).lhs);
   int n = gram.get(i).first.toCharArray().length;	
   firstcopy= removeDuplicate(gram.get(i).first.toCharArray(),n);
            System.out.println("First of "+gram.get(i).lhs+" is "+"{ "+firstcopy +" }"   );
	   firstcopy="";
        }
        int ij=0;
        while(true)
        {
            String follow=find_follow(gram.get(ij).lhs.charAt(0));
            if(gram.get(ij).follow.equals(follow))
                break;
            else
                gram.get(ij).follow=(follow);
   int m = gram.get(ij).follow.toCharArray().length;	
   followcopy= removeDuplicate(gram.get(ij).follow.toCharArray(),m);
            System.out.println("Follow of "+gram.get(ij).lhs+" is "+"{ "+followcopy+" }");
            ij=(ij+1)%nt;
	followcopy="";
        }
       
        System.out.println("");
        
        
        
        
    }
    static String find_follow(char c)
    {
        String follow="";
        if(c==gram.get(0).lhs.charAt(0))
        {
            follow="$";
        }
       
        int j;
                    for(j=0;j<nt;j++)
            {
                int k=0;
                while(k<gram.get(j).rhs.size())
                {
                    if(gram.get(j).rhs.get(k).indexOf(c)!=-1)
                    {
                        int split=gram.get(j).rhs.get(k).indexOf(c);
                        if(split==gram.get(j).rhs.get(k).length()-1)
                        {
                            follow+=find_follow(gram.get(j).lhs.charAt(0));
                            
                        }
                        else{
                            if(!find_first(gram.get(j).rhs.get(k).substring(split+1)).contains("#"))
                            {
                            follow+=find_first(gram.get(j).rhs.get(k).substring(split+1));
                            }
                            else
                            {
                                follow+=find_first(gram.get(j).rhs.get(k).substring(split+1)).replace("#","")+find_follow(gram.get(j).lhs.charAt(0));
                            }
                        }
                    }
                    k++;
                }
            }
         return follow;
        
    }
    static String find_first(String s)
    {
        int k=1;
        String first="";
        if(s.length()==1 &&Pattern.matches("[A-Z]",s.charAt(0)+""))
        {
           first=first_cap(s.charAt(0));
            
        }
        else
        {
            if(!Pattern.matches("[A-Z]",s.charAt(0)+""))
                first=""+s.charAt(0);
            else{
                if((!first_cap(s.charAt(0)).contains("#"))||s.length()==1)
                {
                    first=first_cap(s.charAt(0));
                }
                else{
                    first=first_cap(s.charAt(0)).replace("#", "")+find_first(s.substring(1));
                }
            }
        }
        
            
        
             return first;
        
        
    }
    static String first_cap(char c)
    {
        String capfs="";
        int k=0;
        int index=find_num(c);
        while(k<gram.get(index).rhs.size())
        {
            capfs+=find_first(gram.get(index).rhs.get(k));
            k++;
        }
        
        return capfs;
        
        
    }
    static int find_num(char c)
    {
        for(int i=0;i<nt;i++)
        {
            if(gram.get(i).lhs.charAt(0)==c)
                return i;
        }
        return 999;
    }
    

	static String removeDuplicate(char str[], int n) 
	{ 
		// Used as index in the modified string 
		int index = 0; 

		// Traverse through all characters 
		for (int i = 0; i < n; i++) 
		{ 

			// Check if str[i] is present before it 
			int j; 
			for (j = 0; j < i; j++) 
			{ 
				if (str[i] == str[j]) 
				{ 
					break; 
				} 
			} 

			// If not present, then add it to 
			// result. 
			if (j == i) 
			{ 
				str[index++] = str[i]; 
			} 
		} 
		return String.valueOf(Arrays.copyOf(str, index)); 
	} 

}
class Production
{
    String lhs;
    ArrayList<String> rhs;
    String first,follow;
    public Production(String lhs) {
        this.lhs=lhs;
        this.rhs=new ArrayList<>();
        first="";follow="";
    }
    public Production() {
        lhs="";
        this.rhs=new ArrayList<>();
        first="";follow="";
    }
}
