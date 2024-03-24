package fr.rossi.structurizr.parser;

import fr.rossi.structurizr.parser.dto.Link;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassAnalyzerTest {

    @Test
    public void getLinkForWebClient() {
        assertThat(new ClassAnalyzer(TestClient.class).getLinks())
                .containsExactly(new Link(TestClient.class.getCanonicalName(), Link.LinkType.WEBSERVICE, "Test"));
    }

    public static class TestClient {
        private final WebClient client = new WebClient();
    }

    public static class WebClient {
    }
}
