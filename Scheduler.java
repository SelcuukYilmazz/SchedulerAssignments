import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Scheduler {

    private Assignment[] assignmentArray;
    private Integer[] C;
    private Double[] max;
    private ArrayList<Assignment> solutionDynamic;
    private ArrayList<Assignment> solutionGreedy;

    /**
     * @throws IllegalArgumentException when the given array is empty
     */
//    Scheduler Constructor
    public Scheduler(Assignment[] assignmentArray) throws IllegalArgumentException {
        // Should be instantiated with an Assignment array
        // All the properties of this class should be initialized here
        if (assignmentArray.length==0){
            throw new IllegalArgumentException();
        }
        this.assignmentArray=assignmentArray;
        this.C = new Integer[assignmentArray.length];
        this.max = new Double[assignmentArray.length];
        this.solutionDynamic = new ArrayList<Assignment>();
        this.solutionGreedy = new ArrayList<Assignment>();
    }

    /**
     * @param index of the {@link Assignment}
     * @return Returns the index of the last compatible {@link Assignment},
     * returns -1 if there are no compatible assignments
     */
//    Searchs for first compatible assignment before the index using binary search method
    private int binarySearch(int index) {
        int first=0;
        int last=index-1;
        int mid =(first+last)/2;

        while( first <= last ){

            LocalTime d1 = LocalTime.parse(assignmentArray[mid].getFinishTime());
            LocalTime d2 = LocalTime.parse(assignmentArray[index].getStartTime());
            if (d1.compareTo(d2)<0||d1.compareTo(d2)==0){
                d1 = LocalTime.parse(assignmentArray[mid+1].getFinishTime());
                if (d1.compareTo(d2)<0||d1.compareTo(d2)==0){
                    first = mid + 1;
                }
                else{
                    return mid;
                }
             }
            else{
                last=mid-1;
            }
            mid = (first +last)/2;
        }
        return -1;
    }


    /**
     * {@link #C} must be filled after calling this method
     */
//    C means firs compatible assignment before that index's assignment. It uses binary search for calculation
    private void calculateC() {
        for(int i =0;i<assignmentArray.length;i++){
            C[i] = binarySearch(i);
        }
    }


    /**
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
//    Uses findSolutionDynamic method to calculate dynamic solution for all compatible assignments then returns it.
    public ArrayList<Assignment> scheduleDynamic() {
        if (assignmentArray.length!=0){
            calculateC();
            calculateMax(assignmentArray.length-1);
            findSolutionDynamic(assignmentArray.length-1);
            Collections.reverse(solutionDynamic);
        }
        return solutionDynamic;
    }

    /**
     * {@link #solutionDynamic} must be filled after calling this method
     */
//    Recursive method uses max values for calculation.
    private void findSolutionDynamic(int i) {
        if(i!=-1){
            if(i == 0){
                System.out.println("findSolutionDynamic("+i+")");
                solutionDynamic.add(assignmentArray[i]);
                System.out.println("Adding "+assignmentArray[i]+" to the dynamic schedule");
            }
            else{
                System.out.println("findSolutionDynamic("+i+")");
                if(max[i - 1]<max[i]){
                        solutionDynamic.add(assignmentArray[i]);
                        System.out.println("Adding "+assignmentArray[i]+" to the dynamic schedule");
                        findSolutionDynamic(C[i]);

                }
                else{
                    findSolutionDynamic(i-1);
                }
            }
        }

    }

    /**
     * {@link #max} must be filled after calling this method
     */
//    Calculates Maximum Value for every assignment with recursive call. If previous value is null then calculates it if not null
//    Then uses previous value to calculates it. Maximum value = Maximum value of(weight + max[C],max[i-1])
    private Double calculateMax(int i) {
        double temp = 0.0;

        if (i==0){
            max[i]=assignmentArray[i].getWeight();
            System.out.println("calculateMax("+i+"): Zero");
        }
        else if (max[i]==null){

            if (C[i]<0){
                System.out.println("calculateMax("+i+"): Prepare");
                max[i]=Math.max(assignmentArray[i].getWeight(),calculateMax(i-1));
            }
            else{
                System.out.println("calculateMax("+i+"): Prepare");
                max[i] = Math.max(assignmentArray[i].getWeight()+calculateMax(C[i]),calculateMax(i-1));
            }

        }
        else{
            System.out.println("calculateMax("+i+"): Present");
            return max[i];
        }
        return max[i];

    }

    /**
     * {@link #solutionGreedy} must be filled after calling this method
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
//    In this method nothing matters only thing we use is assignments sort. First we take first element and then we
//    Compare other elements with it so we can calculate all compatible to that element
    public ArrayList<Assignment> scheduleGreedy() {
        for (int i =0;i<assignmentArray.length;i++){
            if (i==0){
                System.out.println("Adding "+assignmentArray[i]+" to the greedy schedule");
                solutionGreedy.add(assignmentArray[i]);
            }
            else if(LocalTime.parse(assignmentArray[i].getStartTime()).compareTo(LocalTime.parse(solutionGreedy.get(solutionGreedy.size()-1).getFinishTime()))>=0){
                System.out.println("Adding "+assignmentArray[i]+" to the greedy schedule");
                solutionGreedy.add(assignmentArray[i]);
            }
        }
        return solutionGreedy;
    }
}
