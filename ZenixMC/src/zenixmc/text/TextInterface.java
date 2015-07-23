/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.text;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Strings with additional information.
 * @author james
 */
public interface TextInterface {
    
    /**
     * Adds a chapter and its line of text onto the last index of text.
     * @param chapter
     *      The chapter to add.
     * @param line
     *      The line assigned under the chapter to add.
     */
    void addLine(String chapter, String[][] line);
    
    /**
     * Adds a chapter and its line of text onto the last index of text. This can overwrite the previous index of text.
     * @param chapter
     *      The chapter to add.
     * @param line
     *      The line assigned under the chapter to add.
     * @param replace 
     *      If it should replace previous index of text.
     */
    void addLine(String chapter, String[][] line, boolean replace);
    
    /**
     * Adds a chapter and its line of text.
     * @param chapter
     *      The chapter to add.
     * @param line
     *      The line assigned under the chapter to add.
     * @param index
     *      The index to insert the strings into.
     * @param replace
     *      If it should replace the string that is already at the index
     *      or to expand the text.
     */
    void addLine(String chapter, String[][] line, int index, boolean replace);
    
    /**
     * Removes a chapter and its line of text specified by index.
     * @param index
     *      The index to delete the text at.
     */
    void removeLine(int index);
    
    /**
     * Removes a chapter and its line of text specified by chapter.
     * @param chapter
     *      The chapter to delete.
     */
    void removeLine(String chapter);
    
    /**
     * @return The raw lines of text that are assigned under a chapter.
     */
    List<String[][]> getLines();
    
    /**
     * @return The names assigned to the lines of text.
     */
    List<String> getChapters();
    
    /**
     * @return Both the chapters and the raw lines of text assigned under the chapters.
     */
    LinkedHashMap<String, String[][]> getText();
    
    /**
     * Sets bookmark at index in chapter.
     * @param bookmarkName
     *      The name of the bookmark.
     * @param chapter
     *      The chapter of text.
     * @param index 
     *      The index of line.
     */
    void setBookmark(String bookmarkName, String chapter, int index);
    
    /**
     * Returns a bookmark.
     * @param bookmarkName
     *      The name of the bookmark.
     * @return The chapter and its line.
     */
    Map<Map<String, String[][]>, Integer> getBookmark(String bookmarkName);
    
    /**
     * @return All of the names of the bookmarks.
     */
    List<String> getBookmarkNames();
    
    /**
     * @return All of the bookmark chapters.
     */
    List<String> getBookmarkedChapters();
    
    /**
     * @return All of the bookmark indices.
     */
    List<Integer> getBookmarkedIndices();
    
    /**
     * @return The chapters being bookmark and the index at which the mark is.
     */
    Map<String, Map<String, Integer>> getBookmarks();
    
    /**
     * Checks if name is a bookmark name.
     * @param name
     *      The name to check.
     * @return <code>true</code> If the name is a bookmark name.
     */
    boolean isBookmarkName(String name);
    
    /**
     * @return The time at which the text was authored.
     */
    long getTimeOfAuthor();
    
    /**
     * @return The user who authored the text.
     */
    UUID getAuthor();
    
    /**
     * @return <code>true</code> If the text is empty.
     */
    boolean isEmpty();
    
    /**
     * @return <code>true</code> If the text has chapters.
     */
    boolean hasChapters();
    
    /**
     * @return <code>true</code> If the text has lines.
     */
    boolean hasLines();
}
