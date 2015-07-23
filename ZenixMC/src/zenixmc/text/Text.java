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
import java.util.logging.Level;
import java.util.logging.Logger;
import zenixmc.utils.JavaUtils;
import zenixmc.utils.io.SerialisableObjectInterface;

/**
 * Strings with additional information.
 * @author james
 */
public class Text implements TextInterface, SerialisableObjectInterface {
    
    /**
     * Logger to debug/log.
     */
    private final Logger log;
    
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
     * @param log
     *      The logger to debug/log.
     */
    public Text(long timeOfAuthor, Logger log) {
        this(null, timeOfAuthor, log);
    }
    
    /**
     * Instantiate.
     * @param author
     *      The author.
     * @param timeOfAuthor
     *      The time of author.
     * @param log
     *      The logger to debug/log.
     */
    public Text(UUID author, long timeOfAuthor, Logger log) {
        this.author = author;
        this.timeOfAuthor = timeOfAuthor;
        this.log = log;
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
        
        return new ArrayList<>(text.values());
    }

    @Override
    public List<String> getChapters() {
        
        if (text == null || isEmpty()) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(text.keySet());
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
    public Map<Map<String, String[][]>, Integer> getBookmark(String bookmarkName) {
        
        if (bookmarks == null || text == null || text.isEmpty()) {
            return new HashMap<>();
        }
        
        if (!(isBookmarkName(bookmarkName))) {
            return new HashMap<>();
        }
        
        Map<String, Integer> bookmark = bookmarks.get(bookmarkName);
        
        if (bookmark.size() > 1) {
            log.log(Level.WARNING, "bookmark map bigger than 1");
            return new HashMap<>();
        }
        
        Map<Map<String, String[][]>, Integer> result = new HashMap<>();
        
        String key = new ArrayList<>(bookmark.keySet()).get(0);
        
        Map<String, String[][]> t = new HashMap<>();
        t.put(key, text.get(key));
        
        result.put(t, bookmark.get(key));
        
        return result;
    }
    
    @Override
    public List<String> getBookmarkedChapters() {
        
        if (bookmarks == null || bookmarks.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Map<String, Integer>> temp = new ArrayList<>();
        
        for (Map.Entry<String, Map<String, Integer>> e : bookmarks.entrySet()) {
            temp.add(e.getValue());
        }
        
        List<String> result = new ArrayList<>();
        
        for (Map<String, Integer> m : temp) {
            for (String s : m.keySet()) {
                result.add(s);
            }
        }
        
        return result;
    }

    @Override
    public List<Integer> getBookmarkedIndices() {
        
        if (bookmarks == null || bookmarks.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Map<String, Integer>> temp = new ArrayList<>();
        
        for (Map.Entry<String, Map<String, Integer>> e : bookmarks.entrySet()) {
            temp.add(e.getValue());
        }
        
        List<Integer> result = new ArrayList<>();
        
        for (Map<String, Integer> m : temp) {
            for (Integer i : m.values()) {
                result.add(i);
            }
        }
            
        return result;
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
        
        return getChapters().size() > 0;
    }

    @Override
    public boolean hasLines() {
        
        if (text == null || text.isEmpty()) {
            return false;
        }
        
        return getLines().size() > 0;
    }
    
    @Override
    public Map<String, Object> serialise() {
        
        Map<String, Object> result = new HashMap<>();
        
        result.put("author-uuid", author.toString());
        result.put("time-of-author", timeOfAuthor);
        result.put("chapters", getChapters());
        result.put("lines", getLines());
        result.put("bookmark-names", getBookmarkNames());
        result.put("bookmarked-chapters", getBookmarkedChapters());
        result.put("bookmarked-pointers", getBookmarkedIndices());
        
        return result;
    }

}
