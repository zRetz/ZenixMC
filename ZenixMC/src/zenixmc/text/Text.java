/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.text;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import zenixmc.utils.io.SerialisableObjectInterface;

/**
 * Strings with additional information.
 * @author james
 */
public class Text implements TextInterface, SerialisableObjectInterface {
    
    /**
     * Author of text.
     */
    private final UUID author;
    
    /**
     * The time that this text was authored.
     */
    private final long timeOfAuthor;
    
    /**
     * The map that holds the text.
     */
    private final Map<String, String[]> text;
    
    public Text(String title, String sentence) {
        this.text = new LinkedHashMap<>();
        this.author = null;
        this.timeOfAuthor = System.currentTimeMillis();
        addLine(title, new String[]{sentence}, 0, false);
    }
    
    @Override
    public void addLine(String chapter, String[] line, int index, boolean replace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeLine(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeLine(String chapter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String[]> getLines() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getChapters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String[]> getText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Integer> getBookmarks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getTimeOfAuthor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UUID getAuthor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Object> serialise() {
        
        Map<String, Object> result = new HashMap<>();
        
        result.put("author-uuid", author.toString());
        result.put("time-of-author", timeOfAuthor);
        result.put("chapters", getChapters());
        result.put("lines", getLines());
        
        return result;
    }
}
