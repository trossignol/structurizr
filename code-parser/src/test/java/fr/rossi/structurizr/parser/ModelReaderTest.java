package fr.rossi.structurizr.parser;

import fr.rossi.structurizr.parser.dto.Link;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelReaderTest {

    @Test
    public void readModel() throws IOException {
        try (var source = ModelReaderTest.class.getResourceAsStream("model.dsl")) {
            if (source == null) throw new FileNotFoundException("Model not found");
            var file = File.createTempFile("tmp_model", ".dsl");
            IOUtils.copy(source, new FileOutputStream(file));

            var model = new ModelReader(file.getAbsolutePath(), "TestContainer");

            assertThat(model.knows(new Link("", Link.LinkType.WEBSERVICE, "Test"))).isTrue();
            assertThat(model.knows(new Link("", Link.LinkType.WEBSERVICE, "Unknown"))).isFalse();
        }
    }
}
