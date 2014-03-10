package org.cytoscape.sample;

import org.cytoscape.io.read.AbstractInputStreamTaskFactory;

public class GpmlReaderTaskFactory extends AbstractInputStreamTaskFactory {
public GpmlReaderTaskFactory(final StreamUtil streamUtil) {
super(new BasicCyFileFilter(new String[]{"gpml"}, new String[]{"text/xml"}, "GPML files", DataCategory.NETWORK, streamUtil));
}
}