/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils.io;

import java.util.List;
import java.util.Map;

/**
 * Allows children to be serialised.
 * @author james
 */
public interface SerialisableCollectionInterface {
    
    /**
     * Returns serialisable data of child.
     * @return The serialisable data of 
     */
    List<Map<String, Object>> serialise();
}
