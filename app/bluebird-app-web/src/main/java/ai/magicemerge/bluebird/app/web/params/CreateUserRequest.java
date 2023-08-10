package ai.magicemerge.bluebird.app.web.params;

import ai.magicemerge.bluebird.app.dal.model.User;
import lombok.Data;

@Data
public class CreateUserRequest {
    private User user;

    private String smsCode;
}
