JPA Repository
==============

Repository programming model with JPA 2 and [Specification](http://en.wikipedia.org/wiki/Specification_pattern) pattern provides a simple and easy way to build the data access layer.

Usage
-----------

**Create an example entity User**

<pre><code>
    @Entity
    public class User extends EntityObject<Long> {
    	private String name;
    	private Integer age;
    	private String email;
    
    	public User() {}
    
    	public User(final String name, final Integer age) {
    		this.name = name;
    		this.age = age;
    	}

        //getter/setter here
    
    }
</code></pre>

**First, we need to create an instance of <code>Repository</code>**
<pre><code>
EntityManager em = ...;
Repository repo = new JPARepository(em);

</code></pre>

**CRUD query**

<pre><code>
//find all users
List<User> users = repo.findAll(User.class)

//find user by id
User user = repo.findById(User.class, 1L);

</code></pre>

Query with Specification
------------------------

**Find all users has name "Duy"**
<pre><code>
Specification<User> hasName = Specifications.equal("name", "Duy");
List<User> users = repo.findBySpecification(User.class, hasName).asList();
</code></pre>

**We can combine specifications with AND, OR**
<pre><code>
//Find users has name "Duy" and age is "28"
Specification<User> hasName = Specifications.equal("name", "Duy");
Specification<User> hasAge28 = Specifications.equal("age", 28);
List<User> users = repo.findBySpecification(User.class, hasName.and(hasAge28)).asList();
</code></pre>
