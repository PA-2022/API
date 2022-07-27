package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.NotificationDao;
import pa.codeup.codeup.entities.Notification;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public boolean insertNotification(Notification notification) {
        this.notificationRepository.save(notification.createDao());
        return true;
    }

    public void readNotification(Long notificationId) throws Exception {
        NotificationDao dao = this.notificationRepository.findById(notificationId).orElse(null);
        if(dao == null) {
            throw new Exception("Cannot find notification");
        }
        dao.setRead(true);
        this.notificationRepository.saveAndFlush(dao);
    }

    public void readAllNotifications(User user) {
        List<NotificationDao> daos = this.notificationRepository.findAllByUserIdAndReadIsFalse(user.getId());
        daos.forEach(dao -> {
            dao.setRead(true);
            this.notificationRepository.save(dao);
        });
    }

    public int countUserNotifications(User user){
        return this.notificationRepository.countAllByUserIdAndReadIsFalse(user.getId());
    }

    public List<Notification> getAllByUser(User user){
        return this.notificationRepository.findAllByUserId(user.getId()).stream().map(NotificationDao::toEntity).collect(Collectors.toList());
    }
}
