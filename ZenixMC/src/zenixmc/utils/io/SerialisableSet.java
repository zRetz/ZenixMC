/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Serialisable Wrapper for <code>java.util.Set</code>
 * @author james
 */
public class SerialisableSet implements SerialisableCollectionInterface {
    
    /**
     * The set to serialise.
     */
    public final Set<SerialisableObjectInterface> present;

    /**
     * Instantiate wrapper.
     * @param present
     *      The set to serialise.
     */
    public SerialisableSet(Set<SerialisableObjectInterface> present) {
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
