package fr.enssat.lanniontech.api.services;

import fr.enssat.lanniontech.api.entities.User;
import fr.enssat.lanniontech.api.entities.UserType;
import fr.enssat.lanniontech.api.entities.map.MapInfo;
import fr.enssat.lanniontech.api.exceptions.EntityNotExistingException;
import fr.enssat.lanniontech.api.exceptions.InvalidParameterException;
import fr.enssat.lanniontech.api.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService extends AbstractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository repository = new UserRepository();

    // ======
    // CREATE
    // ======

    public User create(String email, String password, String lastName, String firstName, UserType type) {
        if (!isValidEmailAddress(email)) {
            throw new InvalidParameterException("The email parameter has an incorrect format.");
        }
        String securePassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = repository.create(email, lastName, firstName, securePassword, type);
        LOGGER.debug("User has been created with id=" + user.getId());
        return user;
    }

    // ===
    // GET
    // ===

    public User get(int id) {
        User user = repository.getFromId(id);
        if (user == null) {
            throw new EntityNotExistingException(User.class);
        }
        return user;
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    // ======
    // UPDATE
    // ======

    public User update(User user) {
        User current = repository.getFromId(user.getId());
        if (current == null) {
            throw new EntityNotExistingException(User.class);
        }
        if (!user.getFirstName().equals(current.getFirstName())) {
            repository.updateFirstName(user, user.getFirstName());
            current.setFirstName(user.getFirstName());
        }
        if (!user.getLastName().equals(current.getLastName())) {
            repository.updateLastName(user, user.getLastName());
            current.setLastName(user.getLastName());
        }
        if (!user.getEmail().equals(current.getEmail())) {
            repository.updateEmail(user, user.getEmail());
            current.setEmail(user.getEmail());
        }
        if (!user.getType().equals(current.getType())) {
            repository.updateType(user, user.getType());
            current.setType(user.getType());
        }
        return current;
    }

    // ======
    // DELETE
    // ======

    public boolean delete(int id) {
        User user = new User();
        user.setId(id);

        MapService mapService = new MapService();
        List<MapInfo> userMaps = mapService.getAllMapsInfo(user);
        for (MapInfo map : userMaps) {
            mapService.delete(map.getId());
        }

        int count = repository.delete(user);
        return count == 1; // If false, something goes wrong (0 or more than 1 rows deleted)
    }

    // =========
    // UTILITIES
    // =========

    private boolean isValidEmailAddress(String email) {
        String format = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
