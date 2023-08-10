package ai.magicemerge.bluebird.app.web.params;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendSmsRequest implements Serializable {
    private static final long serialVersionUID = 578163346793512356L;

    private String countryCode;

    private String phoneNumber;

    private String email;
}
