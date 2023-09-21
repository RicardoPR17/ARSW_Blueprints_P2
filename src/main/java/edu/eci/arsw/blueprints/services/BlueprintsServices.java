/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;


import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.annotation.ManagedBean;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    BlueprintsPersistence bpp = null;

    @Autowired
    @Qualifier("Redundancy")
    BlueprintFilter blueprintFilter=null;


    public void addNewBlueprint(Blueprint bp) {
        try {
            Blueprint blueprint=blueprintFilter.filter(bp);
            bpp.saveBlueprint(blueprint);
        } catch (BlueprintPersistenceException e) {
        }
    }

    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> blueprints= bpp.getAllBlueprints();
        bpp.deleteBlueprints();
        for (Blueprint bp: blueprints){
            addNewBlueprint(bp);
        }
        return bpp.getAllBlueprints();
    }

    /**
     * 
     * @param author blueprint's author
     * @param name   blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        try {
            Set<Blueprint> blueprints= bpp.getAllBlueprints();
            bpp.deleteBlueprints();
            for (Blueprint bp: blueprints){
                addNewBlueprint(bp);
            }
            return bpp.getBlueprint(author, name);
        } catch (BlueprintNotFoundException e) {
            throw new BlueprintNotFoundException("Blueprint not found");
        }
    }

    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        try {
            Set<Blueprint> blueprints= bpp.getAllBlueprints();
            bpp.deleteBlueprints();
            for (Blueprint bp: blueprints){
                addNewBlueprint(bp);
            }
            return bpp.getBlueprintByAuthor(author);
        } catch (BlueprintNotFoundException e) {
            throw new BlueprintNotFoundException("Author not found");
        }
    }


}
