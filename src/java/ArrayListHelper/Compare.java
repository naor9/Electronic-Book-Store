/*
 Here are all the methods that will help with the comparing of ArrayList<String>
 */
package ArrayListHelper;

import java.util.ArrayList;


public class Compare {
    
    /*This methods gets 2 arraylists<String> and return true if the 2 array lists have the same 
      values in each others and false otherwise.*/
    public static boolean equalsWithoutTheMeaningToTheIndex(ArrayList<String> arr1, ArrayList<String> arr2){
        if(arr1.size()!=arr2.size()){
            return false;
        }
        for(String item: arr1){
            if(!arr2.contains(item)){
                return false;
            }
        }
        return true;
    }
}
