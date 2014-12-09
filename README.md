Zeraf! DAO(Data Access Object) in Java
=====
Zeraf! is a simple data access object API for most common database operations. note that's zeraf doesn't implement JPA aka. Java Persistance API like hibernate and such ORM frameworks do.


HOW IT WORKS
=====
you can create the database connection like this

		
		DBConfig dbConfig = new DBConfig();
		dbConfig.setHost("localhost").setPort(3306).setDbName("video_rental")
		.setDbUser("video_rental").setDbPassword("v!d9@55");
		
		db = DbConnectionFactory.getDatabase(DbConnectionFactory.DBTypes.MYSQL, dbConfig);
		

The connection factory is designed as with FlyWeight pattern to reuse opened database connections


Your models
=====


		//video.java
		
		@Table(name="video") #Tells zeraf! which table you want to map this model with
		class Video {
		  @Id #Tell it it's a unique primary key
		  @Column("video_id") #tells which column from the table this attribute associated with
		  private int id;
		  
		  @Column("video_title")
		  private String title;
		  
		  @Column("video_genre_id")
		  private int genre_id;
		  
		  @Column("video_type_id")
		  private int type_id;
		  
		  @Rel(on="video_genre_id", model="com.path.to.Genre") #One to One relation with genre table
		  private Genre genre;
		  
		  @Rel(on="video_type_id", model="com.path.to.Type") #on tells how to join video table with table associated with type model
		  private Type type;
		  
		  #YOUR SETTERS AND GETTERS BELOW
		  ...
		  ...
		  ...
		
		}
		

Your Services
=====

  Persist model to table
  =====
  
		
		Video vid = new Video();
		vid.setTitle("A million ways to die in the west");
		vid.set.....
		db.insert(vid);
		
		

  
  Find a record using criteria
  =====
    
    SingleCriteria videoCriteria = new SingleCriteria(Restriction.gte("video_id", "1"));
		
		SingleCriteria genreCriteria = SingleCriteria.newInstance(Restriction.gt("genre_id", "3")).addAnd(Restriction.eq("genre_title", "Action"));
		
		AndCriteria andCriteria = new AndCriteria();
		andCriteria.setFirstCriteria(videoCriteria);
		andCriteria.setSecondCriteria(genreCriteria);
		
		List<Video> vids = db.find(videoCriteria, Video.class);
		for(Video vid: vids) {
		  Genre genre = vid.getGenre();
		  Type type= vid.getType();
		}
		

  Update a record
  =====
    #Let's assume you already have a model object of type Video called vid
		vid.setGenreId(5); 
		#do whatever modifications you like to do
		db.update(vid);
		

 

  Delete a record
  =====
		
		db.delete(vid);
		



Let's fork, collaborate and share!!!
