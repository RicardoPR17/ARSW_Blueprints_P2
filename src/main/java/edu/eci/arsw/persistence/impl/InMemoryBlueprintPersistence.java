/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.persistence.impl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.eci.arsw.model.Blueprint;
import edu.eci.arsw.model.Point;
import edu.eci.arsw.persistence.BlueprintNotFoundException;
import edu.eci.arsw.persistence.BlueprintPersistenceException;
import edu.eci.arsw.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */
@Component
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    public InMemoryBlueprintPersistence() {
        // load stub data
        Point[] pts = new Point[] { new Point(140, 140), new Point(115, 115) };
        Blueprint bp = new Blueprint("_authorname_", "_bpname_ ", pts);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);

        Point[] pts1 = new Point[] { new Point(140, 140), new Point(115, 115) };
        Blueprint bp1 = new Blueprint("_authorname_", "_bpname1_ ", pts1);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp1.getName()), bp1);

        Point[] pts2 = new Point[] { new Point(140, 140), new Point(115, 115) };
        Blueprint bp2 = new Blueprint("Luis", "_bpname2_ ", pts2);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp2.getName()), bp2);

        Point[] pts3 = new Point[] { new Point(140, 140), new Point(115, 115) };
        Blueprint bp3 = new Blueprint("Mario", "_bpname3_ ", pts3);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp3.getName()), bp3);

    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException {
        HashSet<Blueprint> hashSet = new HashSet<>();
        boolean authorExits = false;
        for (Tuple t : blueprints.keySet()) {
            if (t.getElem1() == author) {
                authorExits = true;
                hashSet.add(blueprints.get(t));
            }
        }

        if (!authorExits) {
            throw new BlueprintNotFoundException("Author not found");
        }

        return hashSet;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        return new HashSet(blueprints.values());
    }
}
