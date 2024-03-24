package com.example.demo;

import fr.rossi.structurizr.parser.ClassAnalyzer;
import fr.rossi.structurizr.parser.dto.Link;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParserTest {

    @Test
    void getLinkForWebClient() {
        assertThat(new ClassAnalyzer(WeatherClient.class).getLinks())
                .containsExactly(new Link(WeatherClient.class.getCanonicalName(), Link.LinkType.WEBSERVICE, "Weather"));
    }

    @Test
    void isClient() {
        assertThat(new ClassAnalyzer(WeatherClient.class).isClient()).isTrue();
        assertThat(new ClassAnalyzer(Resource.class).isClient()).isFalse();
    }
}
