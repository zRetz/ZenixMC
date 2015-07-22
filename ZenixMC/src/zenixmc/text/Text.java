/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import zenixmc.utils.JavaUtils;
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
    private final LinkedHashMap<String, String[][]> text = new LinkedHashMap<>();
    
    /**
     * The map that holds the bookmarks.
     */
    private final Map<String, Map<String, Integer>> bookmarks = new HashMap<>();
    
    /**
     * Instantiate.
     * @param timeOfAuthor
     *      The time of author.
     */
    public Text(long timeOfAuthor) {
        this(null, timeOfAuthor);
    }
    
    /**
     * Instantiate.
     * @param author
     *      The author.
     * @param timeOfAuthor
     *      The time of author.
     */
    public Text(UUID author, long timeOfAuthor) {
        this.author = author;
        this.timeOfAuthor = timeOfAuthor;
    }
    
    @Override
    public void addLine(String chapter, String[][] line) {
        addLine(chapter, line, false);
    }

    @Override
    public void addLine(String chapter, String[][] line, boolean replace) {
        addLine(chapter, line, text.size(), replace);
    }
    
    @Override
    public void addLine(String chapter, String[][] line, int index, boolean replace) {
        
        if (text == null) {
            return;
        }
        
        if (index < 0 || index > text.size()) {
            return;
        }
        
        JavaUtils.orderedPut(text, index, chapter, line, replace);
    }

    @Override
    public void removeLine(int index) {
        
        
        if (text == null || isEmpty()) {
            return;
        }
        
        List<String> keys = new ArrayList<>(text.keySet());
        
        if (index > keys.size() || index < 0) {
            return;
        }
        
        String k = keys.get(index);
        removeLine(k);
    }

    @Override
    public void removeLine(String chapter) {
        
        if (text == null || isEmpty()) {
            return;
        }
        
        text.remove(chapter);
    }

    @Override
    public List<String[][]> getLines() {
        
        if (text == null || isEmpty()) {
            return new ArrayList<>();
        }
        
        List<String[][]> result = new ArrayList<>();
        
        for (String[][] ch : text.values()) {
            result.add(ch);
        }
        
        return result;
    }

    @Override
    public List<String> getChapters() {
        
        if (text == null || isEmpty()) {
            return new ArrayList<>();
        }
        
        List<String> result = new ArrayList<>();
        
        for (String ch : text.keySet()) {
            result.add(ch);
        }
        
        return result;
    }

    @Override
    public LinkedHashMap<String, String[][]> getText() {
        return text;
    }
    
    @Override
    public void setBookmark(String bookmarkName, String chapter, int index) {
        
        if (bookmarks == null || text == null || text.isEmpty()) {
            return;
        }
        
        bookmarks.put(bookmarkName, JavaUtils.singleElementsToMap(chapter, index, new WeakHashMap<String, Integer>()));
    }
    
    @Override
    public Map<String, String[][]> getBookmark(String bookmarkName) {
        
        if (bookmarks == null || text == null || text.isEmpty()) {
            return new HashMap<>();
        }
        
        if (!(isBookmarkName(bookmarkName))) {
            return new HashMap<>();
        }
        
        Map<String, Integer> bookmark = bookmarks.get(bookmarkName);
        
        
    }

    @Override
    public List<String> getBookmarkNames() {
        
        if (bookmarks == null) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(bookmarks.keySet());
    }
    
    @Override
    public boolean isBookmarkName(String name) {
        
        for (String n : getBookmarkNames()) {
            if (n.equals(name)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public Map<String, Map<String, Integer>> getBookmarks() {
        return bookmarks;
    }

    @Override
    public long getTimeOfAuthor() {
        return timeOfAuthor;
    }

    @Override
    public UUID getAuthor() {
        return author;
    }
    
    @Override
    public boolean isEmpty() {
        return !(hasChapters() && hasLines());
    }
    
    @Override
    public boolean hasChapters() {
        
        if (text == null || text.isEmpty()) {
            return false;
        }
        
        return text.keySet().size() > 0;
    }

    @Override
    public boolean hasLines() {
        
        if (text == null || text.isEmpty()) {
            return false;
        }
        
        return text.values().size() > 0;
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
