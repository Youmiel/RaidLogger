package youmiel.tmcdebuglogger;

import java.util.ArrayList;
import java.util.HashMap;

public class LoggerValueContainer {
    public static HashMap<String, Integer> INT_MAP = new HashMap<String, Integer>();
    public static ArrayList<Integer> INT_LIST = new ArrayList<Integer>();

    public static void clearContainer(){
        INT_MAP.clear();
        INT_LIST.clear();    
    }
}
