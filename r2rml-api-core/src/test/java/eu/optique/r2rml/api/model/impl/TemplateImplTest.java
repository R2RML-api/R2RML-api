package eu.optique.r2rml.api.model.impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TemplateImplTest {

    @Test
    public void testGetTemplateStringWithoutColumnNames() {

        assertEquals("http://ex.org/",
                new TemplateImpl("http://ex.org/").getTemplateStringWithoutColumnNames());

        assertEquals("http://ex.org/{}",
                new TemplateImpl("http://ex.org/{name}").getTemplateStringWithoutColumnNames());

        assertEquals("http://ex.org/{}/",
                new TemplateImpl("http://ex.org/{name}/").getTemplateStringWithoutColumnNames());

        assertEquals("http://ex.org/{}/{}",
                new TemplateImpl("http://ex.org/{name}/{AGE}").getTemplateStringWithoutColumnNames());

        assertEquals("http://ex.org/\\{abc\\}",
                new TemplateImpl("http://ex.org/\\{abc\\}").getTemplateStringWithoutColumnNames());

        assertEquals("{}://ex.org/{}",
                new TemplateImpl("{protocol}://ex.org/{abc}").getTemplateStringWithoutColumnNames());
    }


}
