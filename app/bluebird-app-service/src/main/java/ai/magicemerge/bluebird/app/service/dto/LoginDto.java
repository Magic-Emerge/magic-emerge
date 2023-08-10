package ai.magicemerge.bluebird.app.service.dto;

import ai.magicemerge.bluebird.app.dal.model.User;
import ai.magicemerge.bluebird.app.dal.model.Workspace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    private User user;
    private String token;
    private List<Workspace> workspaceList;

}
