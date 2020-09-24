package cz.cvut.fit.niam1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransformService {
    private final String source;

    private final Pattern DATA_PATTERN = Pattern.compile("===\\s*([^=]*)\\s*===", Pattern.MULTILINE);
    private final Pattern ID_PATTERN = Pattern.compile("id:\\s*\"(.*)\"", Pattern.MULTILINE);
    private final Pattern LOCATION_PATTERN = Pattern.compile("Location:\\s*\"([^\"]*)\"", Pattern.MULTILINE);
    private final Pattern NAME_PATTERN = Pattern.compile("Person:\\s*\"(\\w*)\\s*(\\w*)\"", Pattern.MULTILINE);

    public TransformService(String source) {
        this.source = source;
    }

    public String getTransformedResult() {
        Matcher matcher = DATA_PATTERN.matcher(source);

        if (matcher.find()) {
            String matchedData = matcher.group(1);
            return KeyValueToJSON(matchedData);
        }

        return "Error: could not transform.";
    }

    private String KeyValueToJSON(String input) {
        Matcher idMatcher = ID_PATTERN.matcher(input);
        if (!idMatcher.find()) {
            return "ID NOT FOUND";
        }

        Matcher locationMatcher = LOCATION_PATTERN.matcher(input);
        if (!locationMatcher.find()) {
            return "LOCATION NOT FOUND";
        }

        Matcher nameMatcher = NAME_PATTERN.matcher(input);
        if (!nameMatcher.find() || nameMatcher.groupCount() != 2) {
            return "NAME NOT FOUND";
        }

        return "{" + "\n" +
                String.format("  \"id\": \"%s\"", idMatcher.group(1)) + ",\n" +
                String.format("  \"location\": \"%s\"", locationMatcher.group(1)) + ",\n" +
                "  \"person\": {" + "\n" +
                String.format("    \"name\": \"%s\"", nameMatcher.group(1)) + ",\n" +
                String.format("    \"surname\": \"%s\"", nameMatcher.group(2)) + "\n" +
                "  }" + "\n" +
                "}" + "\n";
    }
}
