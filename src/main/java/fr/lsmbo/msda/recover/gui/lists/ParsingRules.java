/*
 * 
 */
package fr.lsmbo.msda.recover.gui.lists;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lsmbo.msda.recover.gui.Config;
import fr.lsmbo.msda.recover.gui.Session;
import fr.lsmbo.msda.recover.gui.model.ParsingRule;

public class ParsingRules {

	private static ArrayList<ParsingRule> parsingRules = new ArrayList<ParsingRule>();
	private static Integer currentParsingRuleIndex = -1;

	/** Add a new parsing rule */
	public static void add(ParsingRule rule) {
		parsingRules.add(rule);
	}

	public static ArrayList<ParsingRule> get() {
		initialize();
		return parsingRules;
	}

	/**
	 * Return a parsing rule via its key
	 * 
	 * @param key
	 *            the key of parsing rule to retrieve
	 */
	public static ParsingRule get(String key) {
		for (ParsingRule pr : parsingRules) {
			if (pr.getName().equals(key))
				return pr;
		}
		return null;
	}

	/** Return the current parsing rule */
	public static ParsingRule getCurrentParsingRule() {
		if (currentParsingRuleIndex != -1)
			return parsingRules.get(currentParsingRuleIndex);
		return null;
	}

	private static void initialize() {
		if (parsingRules.isEmpty()) {
			// lazy loading
			Integer i = 0;
			for (String key : Config.getPropertyKeys("title.regex.*")) {
				try {
					Pattern p = Pattern.compile("title.regex.(.*).rt");
					Matcher m = p.matcher(key);
					if (m.find()) {
						parsingRules.add(new ParsingRule(m.group(1), Config.get(key), key, i));
						if (key.equals(Session.CURRENT_REGEX_RT)) {
							currentParsingRuleIndex = i;
						}
						i++;
					}
				} catch (Exception ex) {
				}
			}
		}
	}

	/** Set a new current parsing rule */
	public static void setNewCurrentParsingRule(ParsingRule pr) {
		if (pr != null) {
			Session.CURRENT_REGEX_RT = pr.getPropertyKey();
			currentParsingRuleIndex = pr.getIndex();
		}
	}
}
