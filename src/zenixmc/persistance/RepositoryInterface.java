/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.io.File;
import java.util.Set;

/**
 * Repository to persist information.
 * @author james
 */
public interface RepositoryInterface {
    
    /**
     * Open the repository.
     * @param openMessage
     * 		The message to log when opening.
     */
    void open(String openMessage);
    
    /**
     * Close the repository.
     * @param closeMessage
     * 		The message to log when closing.
     */
    void close(String closeMessage);
    
    /**
     * Save data.
     * 
     * @param object
     *      The object to save.
     */
    void save(final Object object);
    
    /**
     * Deletes data.
     * @param object
     * 		The object to delete.
     */
    void delete(final Object object);
    
    /**
     * @return All the names of the files in the repository directory.
     */
    Set<String> fileNames();
    
    /**
     * @return An array of all the files in the repository directory.O
     */
    File[] files();
}
