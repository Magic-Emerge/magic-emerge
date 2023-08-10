package ai.magicemerge.bluebird.app.service.args;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendSMSArs {

    private String countryCode;

    private String phoneNumber;

    private String email;

    public SendSMSArs(String countryCode, String phoneNumber) {
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
    }

}
