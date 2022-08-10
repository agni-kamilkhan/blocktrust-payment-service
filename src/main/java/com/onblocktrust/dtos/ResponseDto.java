package com.onblocktrust.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Kamil Khan
 */

@Builder
@Data
public class ResponseDto {

    public static final int UNKNOWN = -1;

    public static final int SUCCESS = 0;

    public static final int ERROR = 1;

    public static final int WARNING = 2;

    private int type;

    private String message;

    private Object data;

    @com.onblocktrust.Generated
    public void fileDataAsStackTrackMessage(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        this.data = stringWriter.toString();
    }

}
