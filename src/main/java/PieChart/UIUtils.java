package PieChart;

import org.javatuples.Pair;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.List;

public class UIUtils {

    public static PieDataset convertPairsToPieDataset(List<Pair<String, Integer>> pairs) {
        //Create a DefaultPieDataset
        DefaultPieDataset PieData = new DefaultPieDataset();
        //Set values of it with the ones in the pairs
        for(Pair<String,Integer> entry : pairs){
            PieData.setValue(entry.getValue0(),entry.getValue1());
        }
        return PieData;
    }
}