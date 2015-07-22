/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Serialisable Wrapper for <code>java.util.List</code>
 * @author james
 */
public class SerialisableList implements SerialisableCollectionInterface {
    
    /**
     * The list to serialise.
     */
    public final List<SerialisableObjectInterface> present;

    /**
     * Instantiate wrapper.
     * @param present
     *      The list to serialise.
     */
    public SerialisableList(List<SerialisableObjectInterface> present) {
        this.present = present;
    }
    
    @Override
    public List<Map<String, Object>> serialise() {
        
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (SerialisableObjectInterface o : present) {
            result.add(o.serialise());
        }
        
        return result;
    }
    
}
