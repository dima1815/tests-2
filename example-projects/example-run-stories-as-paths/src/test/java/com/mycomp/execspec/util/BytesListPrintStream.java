package com.mycomp.execspec.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dmytro on 4/29/2014.
 */
public class BytesListPrintStream extends PrintStream {

    protected List<Byte> writtenBytes;

    public BytesListPrintStream() {
        this(new LinkedList<Byte>());
    }

    private BytesListPrintStream(final List<Byte> bytesList) {

        super(new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                bytesList.add((byte) b);
            }

        });

        writtenBytes = bytesList;
    }

    public List<Byte> getWrittenBytes() {
        return writtenBytes;
    }

}
