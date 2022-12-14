package online.book.store.validation;

import lombok.Getter;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginValidation extends AbstractValidation {


    @Autowired
    private UserService userService;

    @Autowired
    @Getter
    private ValidateResponse response;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }


    public void validation(Object target) {
        if (!supports(target.getClass())) return;
        deleteErrorsMessages();
        User loadedUser;

        UserDto userDto = (UserDto) target;


        if(userDto.getLogin().isEmpty()){
            response.addError("login", "Login can't be empty");
        }
        loadedUser = userService.getUserByLogin(userDto.getLogin());

        if (loadedUser == null) {
            response.addError("login", "Login not exist");
        }

        else if(!loadedUser.isAccepted()){
            response.addError("login", "Account not confirm");
        }

        else {
            if (!userDto.getPassword().equalsIgnoreCase(loadedUser.getPassword())) {
                response.addError("log-password", "Wrong password");
            }
        }
    }

    @Override
    public boolean hasErrors() {
        return !response.getFieldErrors().isEmpty();
    }

    @Override
    public Map<String, String> validationErrors() {
        return this.response.getFieldErrors();
    }

    @Override
    public void deleteErrorsMessages() {
        this.response = new ValidateResponse();
    }
}
