/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Manages messages and translations.
 *
 * @author james
 */
public class MessageManager {
    /**
     * The logger to debug/error to.
     */
    private Logger logger;

    /**
     * The plugin to load resources of.
     */
    private JavaPlugin plugin;

    /**
     * The cached resources.
     */
    protected Map<String, ResourceBundle> resources = new HashMap<String, ResourceBundle>();

    /**
     * Instantiate from a seperate logger and plugin.
     *
     * @param logger
     *            The logger to use.
     * @param plugin
     *            The plugin to use.
     */
    public MessageManager(Logger logger, JavaPlugin plugin) {
        this.logger = logger;
        this.plugin = plugin;
    }

    /**
     * Instantiate from just a plugin.
     *
     * @param plugin
     *            The plugin to use.
     */
    public MessageManager(JavaPlugin plugin) {
        this(plugin.getLogger(), plugin);
    }

    /**
     * Returns the locale of a player. TODO
     *
     * @param player
     *            The player to get the locale of.
     * @return The locale of the player.
     */
    protected Locale getLocaleFor(CommandSender player) {
        return Locale.UK;
    }

    /**
     * Returns the path to the resource containing the messages of the specified
     * path and locale.
     *
     * @param locale
     *            The locale to get the translations for.
     * @param name
     *            The name of the property to get
     * @return The path to the resource.
     */
    protected String getResourcePathFor(Locale locale, String name) {
        return "messages." + locale.toString() + ".properties";
    }

    /**
     * Returns the resource containing the messages for the specified locale and
     * message.
     *
     * @param locale
     *            The locale to get messages for.
     * @param name
     *            The name of the property to get.
     * @return The resource bundle.
     * @throws IOException
     *             when no resource was found
     */
    protected ResourceBundle getMessagesFor(Locale locale, String name)
            throws IOException {
        final String path = getResourcePathFor(locale, name);

        ResourceBundle bundle = resources.get(path);

        if (bundle == null) {
            final InputStream s = plugin.getResource(path);

            if (s == null) {
                throw new IOException("Could not open resource: " + path);
            }

            bundle = new PropertyResourceBundle(s);
            resources.put(path, bundle);
        }

        return bundle;
    }

    /**
     * Returns the type of plural to use for the specified object.
     *
     * @param locale
     *            The locale to get the plural for.
     * @param object
     *            The object to check the plural form for.
     * @return The name of the plural form.
     */
    protected String getPluralName(Locale locale, Object object) {
        final PluralRules rules = PluralRules.forLocale(locale);

        if (object instanceof Number) {
            return rules.select(((Number) object).doubleValue());
        }

        try {
            final double v = Double.parseDouble(object.toString());
            return rules.select(v);
        } catch (final NumberFormatException nfe) {
            return "one";
        }
    }

    /**
     * The pattern to match plural form names with.
     */
    protected Pattern pluralPattern = Pattern.compile("\\{(.*)\\~(.*)\\}");

    /**
     * Find the message format of a named message.
     *
     * @param player
     *            The player this message is destined for.
     * @param name
     *            The name of the message.
     * @param values
     *            The values to check plural forms for.
     * @return The message format.
     */
    public String getMessageFormat(CommandSender player, String name,
            Map<String, Object> values) {
        final ULocale locale = getLocaleFor(player);
        try {
            final ResourceBundle resources = getMessagesFor(locale, name);
            final Enumeration<String> resourceKeys = resources.getKeys();

            int highestScore = -1;
            String bestKey = null;

            while (resourceKeys.hasMoreElements()) {
                final String key = resourceKeys.nextElement();

                if (key.length() < name.length()) {
                    continue;
                }

                final String keySub = key.substring(0, name.length());

                if (keySub.equals(name)) {
                    final String pluralSub = key.substring(name.length());
                    final Matcher m = pluralPattern.matcher(pluralSub);

                    int currentScore = 0;

                    if (!m.find()) {
                        logger.finest("No matches for: " + key + " -> "
                                + pluralSub);
                    } else {
                        do {
                            final String matchName = m.group(1);
                            final String pluralName = m.group(2);

                            final Object value = values.get(matchName);
                            if (value != null) {
                                currentScore++;
                                final String plural = getpluralName(locale,
                                        value);
                                if (plural.equals(pluralName)) {
                                    currentScore++;
                                }
                            }
                        } while (m.find());
                    }

                    if (currentScore >= highestScore) {
                        highestScore = currentScore;
                        bestKey = key;
                    }
                }
            }

            if (bestKey != null) {
                return resources.getString(bestKey);
            }
        } catch (final IOException err) {
            logger.severe("Could not locate string " + name + " for player "
                    + player + ": " + err.toString());
        }

        return name;
    }

    /**
     * Finds and formats a named message.
     *
     * @param player
     *            The player this message is destined for.
     * @param name
     *            The name of the message.
     * @param values
     *            The values to use in the formatting.
     * @return The formatted message.
     */
    public String formatMessage(CommandSender player, String name,
            Map<String, Object> values) {
        return StringFormatter.format(getMessageFormat(player, name, values),
                values);
    }
}
