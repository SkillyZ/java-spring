/**
 * Created by ${skilly} on 2018/3/5.
 */
import java.util.ArrayList;
import java.util.List;

public class Main3 {


    public static void main(String[] args){

        List<Integer> gradeA = new ArrayList<Integer>();
        gradeA.add(723);
        gradeA.add(612);
        gradeA.add(600);
        gradeA.add(582);
        gradeA.add(566);

        List<Integer> gradeB = new ArrayList<Integer>();

        gradeB.add(730);
        gradeB.add(610);
        gradeB.add(600);
        gradeB.add(591);
        gradeB.add(578);


        List<Integer> mergeGrade = new ArrayList<Integer>();


        int i = 0;
        int j = 0;

        for(;i <(gradeA.size()-1) || j < (gradeB.size()-1);){
            if(i >= (gradeA.size()-1) ){
                mergeGrade.add(gradeB.get(j));
                j++;
                continue;
            }else if(j >= (gradeB.size() -1) ){
                mergeGrade.add(gradeA.get(i));
                i++;
                continue;
            }

            if(gradeA.get(i) > gradeB.get(j)){
                mergeGrade.add(gradeA.get(j));
                i++;
            }else if(gradeA.get(i) < gradeB.get(j)){
                mergeGrade.add(gradeB.get(j));
                j++;
            }else{
                mergeGrade.add(gradeA.get(i));
                mergeGrade.add(gradeB.get(j));
                i++;
                j++;
            }
        }

        for(Integer g : mergeGrade){
            System.out.println(g);
        }
    }




}