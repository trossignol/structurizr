package fr.rossi.structurizr.parser;

import fr.rossi.structurizr.parser.dto.Link;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ClassAnalyzer {

    private final Class<?> clazz;

    public ClassAnalyzer(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Set<Link> getLinks() {
        final Set<Link> links = new HashSet<>();
        if (this.isClient()) {
            links.add(new Link(this.clazz.getCanonicalName(), Link.LinkType.WEBSERVICE, this.getNameForWebService()));
        }
        return links;
    }

    public boolean isClient() {
        return Arrays.stream(this.clazz.getDeclaredFields())
                .map(Field::getType).map(Class::getSimpleName)
                .anyMatch("WebClient"::equals);
    }

    private String getNameForWebService() {
        var patterns = Stream.of("WebClient", "Client", "Caller")
                .map(pattern -> Pattern.compile(pattern, Pattern.CASE_INSENSITIVE))
                .toList();

        var result = this.clazz.getSimpleName();
        for (var pattern : patterns) {
            result = result.replaceAll("(?i)" + pattern, StringUtils.EMPTY);
        }
        return result;
    }
}
