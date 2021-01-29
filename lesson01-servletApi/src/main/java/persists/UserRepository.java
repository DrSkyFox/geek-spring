package persists;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository {

    private Map<Integer, User> userMap = new ConcurrentHashMap<>();
    private AtomicInteger identity = new AtomicInteger(0);

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    public User findBydId(Integer id) {
        return userMap.get(id);
    }

    public void insert(User user) {
        int id = identity.incrementAndGet();
        user.setId(id);
        userMap.put(id, user);

    }

    public void update(User user) {
        userMap.put(user.getId(), user);
    }

    public void delete(Integer id) {
        userMap.remove(id);
    }



}
