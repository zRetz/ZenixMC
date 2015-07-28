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
}
