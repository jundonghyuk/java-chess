package chess.domain.userAccess;

import chess.domain.userAccess.room.Room;
import chess.domain.userAccess.room.RoomDao;
import chess.domain.userAccess.user.User;
import chess.domain.userAccess.user.UserDao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GameConnectionService {

    private final UserDao userDao;
    private final RoomDao roomDao;

    public GameConnectionService(UserDao userDao, RoomDao roomDao) {
        this.userDao = userDao;
        this.roomDao = roomDao;
    }

    public User findUserByIdOrElseCreateUser(String userId) {
        Optional<User> user = userDao.findUserById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        User newUser = new User(userId);
        userDao.createUser(newUser);
        return newUser;
    }

    public boolean hasSavedRoom(User user) {
        List<Room> rooms = findRoomsByUser(user);
        if (rooms.isEmpty()) {
            return false;
        }
        return true;
    }

    public Room findUserSelectedRoom(int roomId, User user) {
        return roomDao.findRoomByRoomIdAndUser(roomId, user);
    }

    public List<Room> findRoomsByUser(User user) {
        List<Room> rooms = roomDao.findRoomsByUser(user);
        return Collections.unmodifiableList(rooms);
    }

    public Room makeRoomByUser(User user) {
        roomDao.createRoom(user);
        return roomDao.findRoomByUserIdAndCommands(user.userId(), "");
    }

    public void updateRoomById(int roomId, String commands) {
        if (commands.isEmpty()) {
            roomDao.deleteRoomByRoomId(roomId);
        }
        roomDao.updateRoomByRoomId(roomId, commands);
    }
}
