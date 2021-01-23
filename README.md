# blrb - Full Stack Blog Application Backend
Check out the app here! <a href="https://blog-project-frontend.herokuapp.com/home">blrb</a><br>
<br>
<br>
Backend Tech Stack includes Java 1.8, Maven, Hibernate JPA, SpringBoot, Lombok, MySQL, Amazon S3, JUnit5, Mockito, custom error handling, and Slf4j for logging.
<br>
<br>
The design is MVC REST combined with the Builder pattern for a flexible design to allow for product updates. Amazon S3 configuration handles upload and
retrieval of images for blog posts. 
<br>
<br>
User registration and authentication functionality is handled with JSONWebToken and the Bearer scheme. 
<br>
<br>
<strong>Endpoints</strong><br>
`/signup` Register a new account. POST request. Returns a 201 response for a unique account and a 400 response for a duplicate account.<br>
`/login` Login for an existing user. POST request. Returns a 200 response for a successful login and a 400 for a bad request (incorrect credentials).<br>
`/logout` Logout for a signed in user. POST request. Returns a 201 response for a successful logout.<br>
`/posts` Create a blog post. POST request. Returns a 201 response for a successful post.<br>
`/{id}` Get a blog post by id. GET request. Returns a 200 response for successful retrieval.<br>
`/posts` Gets all of the posts. GET request. Returns a 200 response for suucessful retrieval.<br>
`/posts/tag/{tag}` Gets all of the posts by tag. GET request. Reutns a 200 response for suucessful retrieval.<br>
`posts/blog/username/{username}` Gets all of the posts by username. GET request. Returns a 200 response for suucessful retrieval.<br>
<br>
<br>
The application is deployed to Heroku with a MySQL database instance hosted via Amazon RDS. 
<br>
<br>
JUnit5 and Mockito frameworks were used for testing. 
<br>
<br>
<strong>Future Feature Roadmap:</strong><br>
Update User Details<br>
Update Post<br>
Delete Post<br>
Add comments<br>
Edit comments<br>
Delete comments<br>
Like and Dislike Posts<br>
Homepage ranking based on most popular posts<br>
