package utils;

public class Utils {
	
	
	public static boolean isValidStringNumber(String numberOfVertex) {
		
	    if(numberOfVertex.isEmpty()){
	        return false;
	    }

	    numberOfVertex = numberOfVertex.replaceAll(" ", "");

	    char [] charNumber = numberOfVertex.toCharArray();
	    for(int i =0 ; i<charNumber.length ;i++){
	        if(!Character.isDigit(charNumber[i])){
	            return false;
	        }
	    }
	    return true;
	}
}

