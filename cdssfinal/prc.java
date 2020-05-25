import java.util.*;
import java.util.regex.Pattern;

public class prc {
    static int nt;     
    static ArrayList<Production> gram=new ArrayList<>();      //creating array of object of type production class.
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
	    String firstcopy,followcopy;
//         
//        S->ACB
//        A->BCa/da
//        B->g/#
//        C->h/#
        String pe[][]=new String[200][200];
        
        System.out.print("Enter Number of Non terminals: ");			//Reading no. of non-terminals
        nt=sc.nextInt();
        for(int i=1;i<=nt;i++)
        {
            System.out.print("Enter LHS of production "+i+": ");		//Reading productions
            String lhs=sc.next();
            Production p=new Production(lhs);
            System.out.print("Enter number of RHS: ");				
            int r=sc.nextInt();
            for(int h=0;h<r;h++)
                p.rhs.add(sc.next());
            gram.add(p);
        }
        System.out.print("Number of terminals: ");			//Reading no. of terminals
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
            gram.get(i).first=find_first(gram.get(i).lhs);			//Calling find first function
   int n = gram.get(i).first.toCharArray().length;				//Removing redundant letters from the string
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
   int m = gram.get(ij).follow.toCharArray().length;				//Calling find follow function
   followcopy= removeDuplicate(gram.get(ij).follow.toCharArray(),m);		//Removing redundant letters from the string
            System.out.println("Follow of "+gram.get(ij).lhs+" is "+"{ "+followcopy+" }");
            ij=(ij+1)%nt;
	followcopy="";
        }
       
        System.out.println("");
        
        
        
        
    }
    static String find_follow(char c)			//Function to compute follow
    {
        String follow="";
        if(c==gram.get(0).lhs.charAt(0))			//Checking if it's first symbol
        {
            follow="$";						//If yes, put $ for follow of that symbol
        }
       
        int j;
                    for(j=0;j<nt;j++)				
            {
                int k=0;
                while(k<gram.get(j).rhs.size())
                {
                    if(gram.get(j).rhs.get(k).indexOf(c)!=-1)		//Check if rhs is empty string
                    {
                        int split=gram.get(j).rhs.get(k).indexOf(c);	//Check if there's any synmbol following the passed character
                        if(split==gram.get(j).rhs.get(k).length()-1)	//If no,
                        {
                            follow+=find_follow(gram.get(j).lhs.charAt(0));//Add follow of lhs to follow of current terminal
                            
                        }
                        else{
                            if(!find_first(gram.get(j).rhs.get(k).substring(split+1)).contains("#"))//Check if there's no epsilon followed by passed character
                            {
                            follow+=find_first(gram.get(j).rhs.get(k).substring(split+1)); //If yes, then, add first of next character
                            }
                            else
                            {
                                follow+=find_first(gram.get(j).rhs.get(k).substring(split+1)).replace("#","")+find_follow(gram.get(j).lhs.charAt(0));    //If there's epsilon, replace epsilon with empty string and union with follow of lhs
                            }
                        }
                    }
                    k++;
                }
            }
         return follow;
        
    }
    static String find_first(String s)				//Function to compute first
    {
        int k=1;
        String first="";
        if(s.length()==1 &&Pattern.matches("[A-Z]",s.charAt(0)+""))	//Check if the passed character is terminal or not
        {
           first=first_cap(s.charAt(0));
            
        }
        else
        {
            if(!Pattern.matches("[A-Z]",s.charAt(0)+""))		//Check if passed character is non-terminal
                first=""+s.charAt(0);					//if yes, add it to the first of that character
            else{
                if((!first_cap(s.charAt(0)).contains("#"))||s.length()==1)//If there's no epsilon, find first of the followed terminal symbol
                {
                    first=first_cap(s.charAt(0));
                }
                else{					//else if there's epsilon, replace epsilon with empty string and find first of previous character
                    first=first_cap(s.charAt(0)).replace("#", "")+find_first(s.substring(1));
                }
            }
        }
        
            
        
             return first;
        
        
    }
    static String first_cap(char c) //Function used to find first for terminals followed by any character
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
    static int find_num(char c)		//Function used to find position of the characters in production rules
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
class Production			//Production class used to store the values of non-terminal and terminal along with first and follow
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
