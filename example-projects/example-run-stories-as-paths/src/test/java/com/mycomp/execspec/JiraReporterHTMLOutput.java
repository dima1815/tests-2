package com.mycomp.execspec;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.HtmlOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dmytro on 4/15/2014.
 */
public class JiraReporterHTMLOutput extends HtmlOutput {

    private String storyPath;
    private String jiraVersion;

    private final JiraUploadPrintStream printStream;

    public JiraReporterHTMLOutput(Keywords keywords) {
        this(new JiraUploadPrintStream(), keywords);
    }

    public JiraReporterHTMLOutput(JiraUploadPrintStream printStream, Keywords keywords) {
        super(printStream, keywords);
        this.printStream = printStream;
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {

        storyPath = story.getPath();
        String version = story.getMeta().getProperty("jira-version");
//        Validate.notNull(version);
        jiraVersion = version;

        super.beforeStory(story, givenStory);
    }

    @Override
    public void afterStory(boolean givenStory) {

        if (!givenStory) {
            // send the report to JIRA
            List<Byte> writtenBytes = printStream.getWrittenBytes();
            Byte[] bytes = writtenBytes.toArray(new Byte[writtenBytes.size()]);
            byte[] bytesArray = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                Byte aByte = bytes[i];
                bytesArray[i] = aByte;
            }
            String out = new String(bytesArray);
            System.out.println("out:\n" + out);
        }

        super.afterStory(givenStory);
    }

    private static class JiraUploadPrintStream extends PrintStream {

        private final List<Byte> writtenBytes;

        private JiraUploadPrintStream(){
            this(new LinkedList<Byte>());
        }

        private JiraUploadPrintStream(final List<Byte> bytesList) {

            super(new OutputStream() {

                @Override
                public void write(int b) throws IOException {
                    byte[] bytes = {(byte) b};
                    System.out.println("byte - " + new String(bytes));
                    bytesList.add((byte) b);
                }

                @Override
                public void close() throws IOException {
                    // upload to jira
                    bytesList.clear();
                    super.close();
                }
            });

            writtenBytes = bytesList;
        }

        List<Byte> getWrittenBytes() {
            return writtenBytes;
        }
    }

}
