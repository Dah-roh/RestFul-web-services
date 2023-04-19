package com.example.fashionapi14;

import com.example.fashionapi14.Model.Post;
import com.example.fashionapi14.Model.User;
import com.example.fashionapi14.Service.PostService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIntegrationTest {

    @MockBean
    private PostService postService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void getAllPost(){
        Post post = new Post();
        User user =  new User();
        user.setId(2l);
        user.setUsername("dg@gmail.com");
        user.setPassword("hello");
        user.setRole("admin");
        user.setBlocked(false);
        post.setId(1l);
        post.setPost("This is a new post");
        post.setTitle("blog");
        post.setUser(user);
        post.setBanned(false);
        List<Post> postList = Arrays.asList(post);

        Mockito.when(postService.findAll()).thenReturn(postList);

        ResponseEntity<List<Post>> response = restTemplate.exchange("/posts/post-list", HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
        });

        Assert.assertEquals(postList, response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
