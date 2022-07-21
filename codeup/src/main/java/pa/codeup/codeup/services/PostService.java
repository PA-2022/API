package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pa.codeup.codeup.dto.ContentPost;
import pa.codeup.codeup.dto.Post;
import pa.codeup.codeup.repositories.ContentPostRepository;
import pa.codeup.codeup.repositories.PostRepository;

@Service
public class PostService {

    private final ContentPostRepository contentPostRepository;
    private final PostRepository postRepository;

    @Autowired
    public PostService(ContentPostRepository contentPostRepository, PostRepository postRepository) {
        this.contentPostRepository = contentPostRepository;
        this.postRepository = postRepository;
    }
    
    public Post addContentAndPost(Post post, ContentPost[] contentPost) {
        
        Post currentPost = this.postRepository.save(post);

        for (ContentPost content : contentPost) {
            content.setPostId(currentPost.getId());
            this.contentPostRepository.save(content);
        }

        return currentPost;
    }
}
