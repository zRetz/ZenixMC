/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.text;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Strings with additional information.
 * @author james
 */
public interface TextInterface {
    
    /**
     * Adds a chapter and its line of text.
     * @param chapter
     *      The chapter being added.
     * @param line
     *      The line assigned under the chapter being added.
     * @param index
     *      The index to insert the strings into.
     * @param replace
     *      If it should replace the string that is already at the index
     *      or to expand the text.
     */
    void addLine(String chapter, String line[], int index, boolean replace);
    
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
    List<String[]> getLines();
    
    /**
     * @return The names assigned to the lines of text.
     */
    List<String> getChapters();
    
    /**
     * @return Both the chapters and the raw lines of text assigned under the chapters.
     */
    Map<String, String[]> getText();
    
    /**
     * @return The chapters being bookmark and the index at which the mark is.
     */
    Map<String, Integer> getBookmarks();
    
    /**
     * @return The time at which the text was authored.
     */
    long getTimeOfAuthor();
    
    /**
     * @return The user who authored the text.
     */
    UUID getAuthor();
}
