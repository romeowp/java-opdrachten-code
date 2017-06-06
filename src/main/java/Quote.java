import java.util.Calendar;

public class Quote {

    public static void main(String... args) {
        String[][] quotes = {
                {"galileo", "eppur si muove"},
                {"archimedes", "eureka!"},
                {"erasmus", "in regione caecorum rex est luscus"},
                {"socrates", "I know nothing except the fact of my ignorance"},
                {"rené descartes", "cogito, ergo sum"},
                {"sir isaac newton", "if I have seen further it is by standing on the shoulders of giants"}
            };
    	String weekDays[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    	String months[] = {"January", "February", "March", "April", "May", "June", 
    			"July", "August", "September", "October", "November", "December"};
    	String ordinal[] = {"st", "nd", "rd"};
    	Calendar cal = Calendar.getInstance();
    	int date = cal.get(Calendar.DATE);
        System.out.println("Quote for " + weekDays[cal.get(Calendar.DAY_OF_WEEK)-1] + " the " + date + 
        		(date%10 > 3 || date == 10+(date%10) ? "th" : ordinal[date-1]) + " of " + 
        		months[cal.get(Calendar.MONTH)] + ":");
        System.out.println("\"" + parseQuote(quotes[(date-1)%6][1]) + "\" -- " + parseName(quotes[(date-1)%6][0]));
        
    }
    
    static String parseQuote(String quote){
    	String prettyQuote = quote.substring(0, 1).toUpperCase() + quote.substring(1);
    	if(!quote.matches(".*[.!?]")){
    		prettyQuote = prettyQuote + ".";
    	}
    	return(prettyQuote);
    }
    
    static String parseName(String name){
    	char[] nameArray = name.toCharArray();
    	boolean capitalize = true;
    	for (int i=0; i<name.length(); i++) {
    		if(capitalize){
    			nameArray[i] = Character.toUpperCase(nameArray[i]);
    			capitalize = false;
    		}else if(nameArray[i] == ' '){
    			capitalize = true;
    		}
    	}
    	String prettyName = new String(nameArray);
    	return(prettyName);
    }

}
