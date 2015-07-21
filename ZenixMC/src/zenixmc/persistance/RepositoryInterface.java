/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

/**
 * Repository to persist information.
 * @author james
 */
public interface RepositoryInterface {
    
    /**
     * Open the repository.
     */
    void open();
    
    /**
     * Close the repository.
     */
    void close();
    
    /**
     * Save a player.
     * 
     * @param object
     *      The object to save.
     */
    void save(final Object object);
}
