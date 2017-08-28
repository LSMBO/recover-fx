package fr.lsmbo.msda.recover.lists;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lsmbo.msda.recover.Config;
import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.model.ParsingRule;

public class ParsingRules {

	// private static ObservableList<ParsingRule> parsingRules =
	// FXCollections.observableArrayList();
	private static ArrayList<ParsingRule> parsingRules = new ArrayList<ParsingRule>();
	private static Integer currentParsingRuleIndex = -1;

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

	public static ArrayList<ParsingRule> get() {
		initialize();
		return parsingRules;
	}

	// public static ParsingRule[] toArray() {
	// ArrayList<ParsingRule> rules = get(); // make sure that parsingRules is
	// loaded
	// ParsingRule[] parsingRulesArray = new ParsingRule[rules.size()]; //
	// prepare array
	// return rules.toArray(parsingRulesArray); // cast and return
	// }

	// public static ArrayList<ParsingRule> get() {
	// if(parsingRules == null) {
	// // lazy loading
	// Integer i = 0;
	// parsingRules = new ArrayList<ParsingRule>();
	// for(String key: Config.getPropertyKeys("title.regex.*")) {
	// try {
	// Pattern p = Pattern.compile("title.regex.(.*).rt");
	// Matcher m = p.matcher(key);
	// if (m.find()) {
	// parsingRules.add(new ParsingRule(m.group(1), Config.get(key), key, i));
	// if(key.equals(Session.CURRENT_REGEX_RT)) {
	// currentParsingRuleIndex = i;
	// }
	// i++;
	// }
	// } catch(Exception ex) {
	// }
	// }
	// }
	// return parsingRules;
	// }

	public static ParsingRule get(String key) {
		for (ParsingRule pr : parsingRules) {
			if (pr.getName().equals(key))
				return pr;
		}
		return null;
	}

	public static void add(ParsingRule rule) {
		parsingRules.add(rule);
	}

	// public static Integer getIndexOfCurrentParsingRule() {
	// return currentParsingRuleIndex;
	// }

	public static ParsingRule getCurrentParsingRule() {
		if (currentParsingRuleIndex != -1)
			return parsingRules.get(currentParsingRuleIndex);
		return null;
	}

	public static void setNewCurrentParsingRule(ParsingRule pr) {
		if (pr != null) {
			Session.CURRENT_REGEX_RT = pr.getPropertyKey();
			currentParsingRuleIndex = pr.getIndex();
		}
	}
}
