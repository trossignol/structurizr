package fr.rossi.structurizr.parser;

import com.structurizr.dsl.StructurizrDslParser;
import com.structurizr.dsl.StructurizrDslParserException;
import com.structurizr.model.Element;
import com.structurizr.model.Relationship;
import fr.rossi.structurizr.parser.dto.Link;
import fr.rossi.structurizr.parser.exception.ModelReaderException;

import java.io.File;
import java.util.Collection;

import static fr.rossi.structurizr.parser.exception.ModelReaderException.assertFalse;

public class ModelReader {

    private final Collection<Element> relationships;

    public ModelReader(String filePath, String containerName) {
        try {
            var parser = new StructurizrDslParser();
            parser.parse(new File(filePath));

            var workspace = parser.getWorkspace();
            var containers = workspace.getModel().getSoftwareSystems().stream()
                    .flatMap(system -> system.getContainers().stream())
                    .filter(container -> containerName.equals(container.getName()))
                    .toList();

            assertFalse(containers.isEmpty(), "Container not found for name=" + containerName);
            assertFalse(containers.size() > 1, containers.size() + " containers found for name=" + containerName);

            this.relationships = containers.get(0).getRelationships()
                    .stream().map(Relationship::getDestination).toList();
        } catch (StructurizrDslParserException e) {
            throw new ModelReaderException("Error parsing DSL for path=" + filePath, e);
        }
    }

    public static void main(String[] args) {
        var reader = new ModelReader("demo/docs/workspace.dsl", "Demo API");
        System.out.println("Weather >> " + reader.knows(new Link("", Link.LinkType.WEBSERVICE, "Weather")));
        System.out.println("Unknown >> " + reader.knows(new Link("", Link.LinkType.WEBSERVICE, "Unknown")));
    }

    public boolean knows(Link link) {
        return this.relationships.stream().anyMatch(r -> r.getName().contains(link.target()));
    }
}
