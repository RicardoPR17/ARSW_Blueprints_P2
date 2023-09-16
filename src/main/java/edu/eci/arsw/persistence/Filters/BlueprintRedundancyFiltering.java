package edu.eci.arsw.persistence.Filters;

;
import edu.eci.arsw.model.Blueprint;
import edu.eci.arsw.model.Point;
import edu.eci.arsw.persistence.BlueprintFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Qualifier("Redundancy")
public class BlueprintRedundancyFiltering implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint bp) {
        ArrayList<Point> points=  new ArrayList<>(bp.getPoints());
        for(int i=0; i<bp.getPoints().size()-1; i++){
            if(bp.getPoints().get(i).equals(bp.getPoints().get(i+1))){
               points.remove(i);
            }
        }
        bp.setPoints(points);
        return bp;
    }
}
