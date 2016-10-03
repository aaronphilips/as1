package ca.as1;

/**
 * Created by aaron on 9/25/16.
 */
public enum DayOfWeek {
    MONDAY("Monday",1),
    TUESDAY("Tuesday",2),
    WEDNESDAY("Wednesday",3),
    THURSDAY("Thursday",4),
    FRIDAY("Friday",5),
    SATURDAY("Saturday",6),
    SUNDAY("Sunday",7);

    private int value;
    private String string;
    DayOfWeek(String toString,int value){
        this.string =toString;
        this.value=value;
    }
    public int getValue(){
        return this.value;
    }
    @Override
    public String toString() {
        return string;
    }


}
