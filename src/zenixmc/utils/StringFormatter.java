/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

/**
 * String formatting utility.
 *
 */
public class StringFormatter {
	
	private static final Pattern integer = Pattern.compile("<integer>");
	private static final Pattern string = Pattern.compile("<string>");
	private static final Pattern zenixuser = Pattern.compile("<zenixUser>");
	private static final Pattern bendingplayer = Pattern.compile("<bendingPlayer>");
	private static final Pattern orgplayer = Pattern.compile("<orgPlayer>");
	private static final Pattern clan = Pattern.compile("<clan>");
	
	public enum MessageOccasion {
		ERROR, ESSENTIAL, BENDING, CLAN, HANDLED, ZENIX;
	}
	
	public static String format(String msg, Object... obs) {
		
		String result = msg;
		
		for (int i = 0; i < obs.length; i++) {
			if (obs[i] instanceof Integer) {
				Matcher st = integer.matcher(result);
				if (st.find()) {
					result = st.replaceFirst(((Integer) obs[i]).toString());
				}
			}
			if (obs[i] instanceof String) {
				Matcher st = string.matcher(result);
				if (st.find()) {
					result = st.replaceFirst((String) obs[i]);
				}
			}
			if (obs[i] instanceof ZenixUserInterface) {
				Matcher zu = zenixuser.matcher(result);
				if (zu.find()) {
					result = zu.replaceFirst(((ZenixUserInterface) obs[i]).getName());
				}
			}
			if (obs[i] instanceof BendingPlayerInterface) {
				Matcher bp = bendingplayer.matcher(result);
				if (bp.find()) {
					result = bp.replaceFirst(((BendingPlayerInterface) obs[i]).toString());
				}
			}
			if (obs[i] instanceof OrganizationPlayerInterface) {
				Matcher op = orgplayer.matcher(result);
				if (op.find()) {
					result = op.replaceFirst(((OrganizationPlayerInterface) obs[i]).toString());
				}
			}
			if (obs[i] instanceof Clan) {
				Matcher c = clan.matcher(result);
				if (c.find()) {
					result = c.replaceFirst(((Clan) obs[i]).toString());
				}
			}
		}
		
		return result;
	}
	
	public static String format(String msg, MessageOccasion occasion, ZenixMCInterface inst) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(ChatColor.BLACK + "[");
	
		String p = "";
		ChatColor msgc = null;
		
		switch (occasion) {
		case ESSENTIAL:
			p = inst.getSettings().getEssentialColor() + "Essential";
			msgc = inst.getSettings().getMatchingEssentialColor();
			break;
		case BENDING:
			p = inst.getSettings().getBendingColor() + "Bending";
			msgc = inst.getSettings().getMatchingBendingColor();
			break;
		case CLAN:
			p = inst.getSettings().getClanColor() + "Clan";
			msgc = inst.getSettings().getMatchingClanColor();
			break;
		case ERROR:
			p = inst.getSettings().getErrorColor() + "Error";
			msgc = inst.getSettings().getErrorColor();
			break;
		case HANDLED:
			p = inst.getSettings().getSortedColor() + "Handled";
			msgc = inst.getSettings().getSortedColor();
			break;
		case ZENIX:
			p = inst.getSettings().getMatchingNotificationColor() + "Zenix";
			msgc = inst.getSettings().getNotificationColor();
			break;
		}
		
		sb.append(p);
		sb.append(ChatColor.BLACK + "] ");
		sb.append(msgc);
		sb.append(msg);
		
		return sb.toString();
	}
	
    public static String format(Location loc) {
        return "Location: " + "X - " + loc.getX() + "; Y - " + loc.getY() + "; Z - " + loc.getZ() + ";";
    }
    
    /**
     * @return A map to be used as the value set.
     */
    public static Map<String, Object> createFormat() {
        return new Hashtable<>();
    }

    /**
     * Format the string {@code format} by placing in the specified
     * {@code values}.
     *
     * <b>Example usage:</b>
     *
     * <pre>
     * Map&lt;String, Object&gt; format = StringFormatter.createFormat();
     * format.put(&quot;format&quot;, &quot;String !&quot;);
     * StringFormatter.format(&quot;What is this %{format}s&quot;, format);
     * </pre>
     *
     * This would result in {@code "What is this String !"}.
     *
     * @param format
     *            The format as used in normal string formatting (%s %i %f and
     *            so on) but a named tag placed after the percent sign.
     *
     *            Example: {@code "Bound slot % slot}i to:
     *            %{ability.displayName}s"}
     *
     * @param values
     *            The name object mapping for embedding the values into the
     *            format.
     * @return The formatted string
     */
    public static String format(String format, Map<String, Object> values) {
        final StringBuilder convFormat = new StringBuilder(format);
        final List<Object> valueList = new ArrayList<Object>();
        int currentPos = 1;
        for (final Entry<String, Object> value : values.entrySet()) {
            final String key = value.getKey();
            final String formatKey = "%%{" + key + "}", formatPos = "%"
                    + Integer.toString(currentPos) + "$";
            for (int index = -1; (index = convFormat.indexOf(formatKey, index)) != -1; index += formatPos
                    .length()) {
                convFormat
                        .replace(index, index + formatKey.length(), formatPos);
            }
            valueList.add(value.getValue());
            ++currentPos;
        }

        try {
            return String.format(convFormat.toString(), valueList.toArray());
        } catch (final IllegalFormatException e) {
            throw new RuntimeException("Illegal format (format: \"" + format
                    + "\", value names: " + values.keySet() + " )", e);
        }
    }
}
