package com.jajeem.whiteboard.server.Module.Data;
/**
 * File Name    : FontData.java
 * Description  : The FontData Class provides a place to store
 *                font information of the system.
 * Author       : Hengfeng Li
 * Team         : TheThreeBytes
 */
import java.io.Serializable;

/**
 * Class FontData provides the font data which can
 * be shared by the system components.
 */
public class FontData implements Serializable {
    /** The current font of the system. */
    private String font;

    /** The current font size of the system. */
    private int fontSize;
    
    /** Whether the current font is bold or not. */
    private boolean isBold;

    /** Whether the current font is istalic or not. */
    private boolean isItalic;

    /** Whether the current font is baseline or not. */
    private boolean isBaseline;

    /**
     * The constructor method of font data.
     * @param font The intial font
     * @param fontSize The initial size of font
     * @param isBold The initial parameters of bold font
     * @param isItalic The initial parameters of italic font
     * @param isBaseline The initial parameters whether
     *      the font has the baseline
     */
    public FontData(String font, int fontSize, boolean isBold,
                    boolean isItalic, boolean isBaseline) {
        this.font = font;
        this.fontSize = fontSize;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.isBaseline = isBaseline;
    }

    /** Another constructor method to copy an existing FontData */
    public FontData(FontData fontData){
        this.font = fontData.getFont();
        this.fontSize = fontData.getFontSize();
        this.isBold = fontData.getIsBold();
        this.isItalic = fontData.getIsItalic();
        this.isBaseline = fontData.getIsBaseline();
    }

    /**
     * Set the font.
     * @param font The specified font.
     */
    public void setFont(String font){
        this.font = font;
    }

    /**
     * Get the font.
     * @return The current font.
     */
    public String getFont(){
        return this.font;
    }

    /**
     * Set the size of font
     * @param fontSize The specified font.
     */
    public void setFontSize(int fontSize){
        this.fontSize = fontSize;
    }

    /**
     * Get the current size of font.
     * @return The current size of font.
     */
    public int getFontSize( ) {
        return this.fontSize;
    }

    /**
     * Set the bold of font
     * @param isBold The parameter indicates whether
     *      the font is bold or not.
     */
    public void setIsBold(boolean isBold) {
        this.isBold = isBold;
    }

    /**
     * Get the parameter of current font whether is bold or not.
     * @return The parameter indicates whether
     *      the font is bold or not.
     */
    public boolean getIsBold(){
        return this.isBold;
    }

    /**
     * Set the italic attribute of font
     * @param isItalic The parameter indicates whether
     *      the font is italic or not.
     */
    public void setIsItalic(boolean isItalic) {
        this.isItalic = isItalic;
    }

    /**
     * Get the parameter of current font whether is italic or not.
     * @return The parameter indicates whether
     *      the font is italic or not.
     */
    public boolean getIsItalic(){
        return this.isItalic;
    }

    /**
     * Set the baseline attribute of current font
     * @param isBaseline The parameter indicates whether
     *      the font has baseline or not.
     */
    public void setIsBaseline(boolean isBaseline) {
        this.isBaseline = isBaseline;
    }

    /**
     * Get the parameter of current font whether has baseline or not.
     * @return The parameter indicates whether
     *      the font has baseline or not.
     */
    public boolean getIsBaseline(){
        return this.isBaseline;
    }
}
