import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Assignment implements Comparable{
//    Declaring Variables
    private String name;
    private String start;
    private int duration;
    private int importance;
    private boolean maellard;
//     Constructor
    public Assignment(String name, String start, int duration, int importance, boolean maellard){
        this.name=name;
        this.start=start;
        this.duration=duration;
        this.importance=importance;
        this.maellard=maellard;
    }

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isMaellard() {
        return this.maellard;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
//    Gets Finish Time with first converts it to DateTimeFormatter then converts it to String
    public String getFinishTime() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime finish = LocalTime.parse(start);
        return finish.plusHours(duration).toString();
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
//    Does weight Calculations then Gets Weight.
//    If maellard is true then goes with 1001 if false then goes with 1
    public double getWeight()
    {
        double weight = (double)(importance * (maellard ? 1001 : 1))/duration;
        return weight;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
//    Sorts Finish times as Ascending Order
    @Override
    public int compareTo(Object o)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(this.getFinishTime());
            d2 = sdf.parse(((Assignment) o).getFinishTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long elapsed = d1.getTime() - d2.getTime();
        return (elapsed<0) ? -1:((elapsed>0)?1:0);
    }

    /**
     * @return Should return a string in the following form:
     * Assignment{name='Refill vending machines', start='12:00', duration=1, importance=45, maellard=false, finish='13:00', weight=45.0}
     */
//    Attach Objects to their own strings.
    @Override
    public String toString() {
        return "Assignment{name='"+name+"', start='"+start+"', "+"duration="+duration+", importance="+importance+", maellard="+maellard+", finish='"+getFinishTime()+"', weight="+getWeight()+"}";
    }

}
