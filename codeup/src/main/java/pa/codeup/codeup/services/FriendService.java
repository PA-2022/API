package pa.codeup.codeup.services;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.FriendDto;
import pa.codeup.codeup.dto.User;
import pa.codeup.codeup.entities.Friend;
import pa.codeup.codeup.entities.UserAndFriend;
import pa.codeup.codeup.entities.UserIsFriend;
import pa.codeup.codeup.repositories.FriendRepository;
import pa.codeup.codeup.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FriendService {

    private FriendRepository friendRepository;
    private UserRepository userRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository, UserRepository userRepository){
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public Friend addFriend(Long currentUserUd, Long friendId) throws Exception {
        User friend = this.userRepository.getUserById(friendId);
        if(friend == null) {
            throw new Exception("cant find friend");
        }
        FriendDto existsFriend = this.friendRepository.findByUserIdAndFriendId(currentUserUd, friendId);
        if(existsFriend != null){
            throw new Exception("friend demand already exists");
        }
        existsFriend = this.friendRepository.findByUserIdAndFriendId(friendId, currentUserUd);
        if(existsFriend != null && !existsFriend.isAccepted()){
            existsFriend.setAccepted(true);
            return this.friendRepository.saveAndFlush(existsFriend).toEntity();
        }
        return this.friendRepository.save(new Friend(null, currentUserUd, friendId, false).toFriendDto()).toEntity();
    }

    public Friend acceptFriend(Long currentUserId, Long friendId) throws Exception {
        User friend = this.userRepository.getUserById(friendId);
        if(friend == null) {
            throw new Exception("cant find friend");
        }
        FriendDto existsFriend = this.friendRepository.findByUserIdAndFriendId(friendId, currentUserId);
        if(existsFriend == null){
            throw new Exception("friend demand doesnt exists");
        }
        existsFriend.setAccepted(true);
        return this.friendRepository.saveAndFlush(existsFriend).toEntity();

    }

    public void deleteFriend(Long currentUserId, Long friendId) throws Exception {
        User friend = this.userRepository.getUserById(friendId);
        if(friend == null) {
            throw new Exception("cant find friend");
        }
        FriendDto existsFriendA = this.friendRepository.findByUserIdAndFriendId(friendId, currentUserId);
        FriendDto existsFriendB = this.friendRepository.findByUserIdAndFriendId(currentUserId, friendId);
        if(existsFriendA == null && existsFriendB == null){
            throw new Exception("cant find friend");
        }
        this.friendRepository.delete(Objects.requireNonNullElse(existsFriendA, existsFriendB));
    }

    public Friend getFriend(User currentUser, Long friendId) {
        FriendDto existsFriendA = this.friendRepository.findByUserIdAndFriendId(friendId, currentUser.getId());
        FriendDto existsFriendB = this.friendRepository.findByUserIdAndFriendId(currentUser.getId(), friendId);
        if(existsFriendA == null && existsFriendB == null) {
            return null;
        }
        return Objects.requireNonNullElse(existsFriendA, existsFriendB).toEntity();
    }

    public List<UserAndFriend> getList(Long userId) throws Exception {
        User currentUser = this.userRepository.getUserById(userId);
        if(currentUser == null) {
            throw new Exception("User doesnt exists");
        }
        List<UserAndFriend> userAreFriends = new ArrayList<>();
        List<FriendDto> friendsDtos = this.friendRepository.findByUserIdOrFriendId(currentUser.getId(), currentUser.getId());
        for (FriendDto friendDto: friendsDtos) {
            Friend friend = friendDto.toEntity();
            Long friendId = Objects.equals(friend.getUserId(), currentUser.getId()) ? friend.getFriendId() : friend.getUserId();
            User friendUser = this.userRepository.getUserById(friendId);
            userAreFriends.add(new UserAndFriend(friendUser, friend));
        }
        return userAreFriends;
    }

    public List<Long> getUserFriends(Long userId) throws Exception {
        User currentUser = this.userRepository.getUserById(userId);
        if(currentUser == null) {
            throw new Exception("User doesnt exists");
        }
        List<Long> usersIds = new ArrayList<>();
        List<FriendDto> friendsDtos = this.friendRepository.findByUserIdOrFriendId(currentUser.getId(), currentUser.getId());
        for (FriendDto friendDto: friendsDtos) {
            Friend friend = friendDto.toEntity();
            Long friendId = Objects.equals(friend.getUserId(), userId) ? friend.getFriendId() : friend.getUserId();
            usersIds.add(friendId);
        }
        return usersIds;
    }
}
