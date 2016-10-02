package ca.as1;

/**
 * Created by Aaron Philips on 10/1/2016.
 */
public class Helper {
    public static class Functions{
        public static DayOfWeek getDayfromInt(int dayInt){
            DayOfWeek retVal=DayOfWeek.MONDAY;
            switch(dayInt) {
                case 1:
                    retVal= DayOfWeek.MONDAY;
                    break;
                case 2:
                    retVal=  DayOfWeek.TUESDAY;
                    break;
                case 3:
                    retVal=  DayOfWeek.WEDNESDAY;
                    break;
                case 4:
                    retVal=  DayOfWeek.THURSDAY;
                    break;
                case 5:
                    retVal=  DayOfWeek.FRIDAY;
                    break;
                case 6:
                    retVal=  DayOfWeek.SATURDAY;
                    break;
                case 7:
                    retVal=  DayOfWeek.SUNDAY;
                    break;
            }
            return retVal;
        }
    }
}
