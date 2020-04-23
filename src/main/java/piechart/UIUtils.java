package piechart;

import org.javatuples.Pair;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.List;

public class UIUtils {

    public static PieDataset convertPairsToPieDataset(List<Pair<String, Integer>> pairs) {
        DefaultPieDataset PieData = new DefaultPieDataset();

        for(Pair<String,Integer> entry : pairs){
            PieData.setValue(entry.getValue0(),entry.getValue1());
        }

        return PieData;
    }
}