package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pa.codeup.codeup.dto.*;
import pa.codeup.codeup.entities.Forum;
import pa.codeup.codeup.repositories.*;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ForumService {

    private final ForumRepository forumRepository;


    @Autowired
    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }


    public Forum getForumById(Long id) {
        return this.forumRepository.getForumById(id).toEntity();
    }

    public ForumDao save(Forum forum) {
        return this.forumRepository.save(forum.createDao());
    }

    public List<Forum> findByOrderByIdDesc(int offset, int limit) {
        return this.forumRepository.findByOrderByIdDesc(PageRequest.of(offset, limit))
                .stream().map(ForumDao::toEntity).collect(Collectors.toList());
    }
}
