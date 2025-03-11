package ro.mpp2024;

import java.util.List;

public interface UserRepository extends Repository<Integer, User> {
    List<User> findByUsername(String username);
    List<User> findByEmail(String email);
}