JPA Repository
==============

Repository programming model with JPA 2 and [Specification](http://en.wikipedia.org/wiki/Specification_pattern) pattern provides a simple and easy way to build the data access layer.

Usage
=====

**Create an example entity User**

<pre><code>
    @Entity
    public class User extends EntityObject<Long> {
    	private String name;
    	private Integer age;
    	private String email;
    
    	public User() {}
        //getter/setter here
    
    }
</code></pre>

**First, we need to create an instance of <code>Repository</code>**
<pre><code>
EntityManager em = ...;
Repository repo = new JPARepository(em);

</code></pre>

**Basic Query**

<pre><code>
//find all users
List<User> users = repo.findAll(User.class)

//find user by id
User user = repo.findById(User.class, 1L);

//create
User user = new User();
repo.save(user);

//update
User user = repo.findById(User.class, 1L);
user.setName("Duy Do");
repo.update(user);

// delete
repo.remove(user);

</code></pre>

Query with Specification
------------------------
**Find unique user with email is abc@example.com**
<pre><code>
Specification<User> hasEmail = Specifications.equal("email", "abc@example.com");
User user = repo.findBySpecification(User.class, hasEmail).asSingle();
</code></pre>

**Find all users has name "Duy"**
<pre><code>
Specification<User> hasName = Specifications.equal("name", "Duy");
List<User> users = repo.findBySpecification(User.class, hasName).asList();
</code></pre>

**Find all users which name is not "Duy"*
<pre><code>
Specification<User> notName = Specifications.equal("name", "Duy").not();
List<User> users = repo.findBySpecification(User.class, notName).asList();
</code></pre>

**We can combine specifications with AND, OR**
<pre><code>
//Find users has name "Duy" and age is "28"
Specification<User> hasName = Specifications.equal("name", "Duy");
Specification<User> hasAge28 = Specifications.equal("age", 28);
List<User> users = repo.findBySpecification(User.class, hasName.and(hasAge28)).asList();
</code></pre>

Sorting and Paging:
------------------

1. Sorting by using method <code>sortXXX()</code> of <code>SepecificationResult</code>:

**Find all users has name "Duy" and sort ascending by name **
<pre><code>
Specification<User> hasName = Specifications.equal("name", "Duy");
List<User> users = repo.findBySpecification(User.class, hasName).sortAscending("name").asList();
</code></pre>

2. Paging by using method <code>skip(int count)</code> and <code>get(int count)</code> of <code>SepecificationResult</code>:

**Find 10 users has name "Duy"**
<pre><code>
Specification<User> hasName = Specifications.equal("name", "Duy");
List<User> users = repo.findBySpecification(User.class, hasName).get(10).asList();
</code></pre>

**Find all users has name "Duy" but skip 10 first users**
<pre><code>
Specification<User> hasName = Specifications.equal("name", "Duy");
List<User> users = repo.findBySpecification(User.class, hasName).skip(10).asList();
</code></pre>

**Find 20 users has name "Duy" but skip 10 first users**
<pre><code>
Specification<User> hasName = Specifications.equal("name", "Duy");
List<User> users = repo.findBySpecification(User.class, hasName).skip(10).get(20).asList();
</code></pre>
