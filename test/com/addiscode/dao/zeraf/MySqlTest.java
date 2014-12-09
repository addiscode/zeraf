package com.addiscode.dao.zeraf;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.junit.Before;
import org.junit.Test;

import com.addiscode.dao.zeraf.database.DBConfig;
import com.addiscode.dao.zeraf.database.Database;
import com.addiscode.dao.zeraf.database.Restriction;
import com.addiscode.dao.zeraf.database.MySql.MySqlDB;
import com.addiscode.dao.zeraf.database.command.CommandStrategy;
import com.addiscode.dao.zeraf.database.command.UpdateCommandStrategy;
import com.addiscode.dao.zeraf.database.criteria.AndCriteria;
import com.addiscode.dao.zeraf.database.criteria.Criteria;
import com.addiscode.dao.zeraf.database.criteria.OrCriteria;
import com.addiscode.dao.zeraf.database.criteria.SingleCriteria;
import com.apposit.videorental.model.Video;

public class MySqlTest {

	Database db = null;
	Video vid = null;
	
	@Before
	public void init() throws Exception {
		DBConfig dbConfig = new DBConfig();
		dbConfig.setHost("localhost").setPort(3306).setDbName("video_rental")
		.setDbUser("video_rental").setDbPassword("v!d9@55");
		
		db = MySqlDB.getNewInstance(dbConfig);
		
		SingleCriteria cr = SingleCriteria.newInstance(Restriction.eq("video_id", "1"));
		vid = (Video) db.find(cr, Video.class).get(0);
		
	}
	
	@Test
	public void testInsert() throws Exception {
		Video videoModel = new Video();
		videoModel.setTitle("The Transporter");
		videoModel.setType_id(2);
		videoModel.setGenre_id(1);
		
		db.insert(videoModel);
		
		assertTrue(true);
	}

	@Test(expected=InvalidInputException.class)
	public void testFind() throws Exception {
		
		SingleCriteria videoCriteria = new SingleCriteria(Restriction.gte("video_id", "1"));
		
		SingleCriteria genreCriteria = SingleCriteria.newInstance(Restriction.gt("genre_id", "3")).addAnd(Restriction.eq("genre_title", "Action"));
		
		AndCriteria andCriteria = new AndCriteria();
		andCriteria.setFirstCriteria(videoCriteria);
		andCriteria.setSecondCriteria(genreCriteria);
		
		
		
		OrCriteria orCriteria = new OrCriteria();
		//expected InvalidInputException
		orCriteria.setFirstCriteria(orCriteria);
		orCriteria.setSecondCriteria(SingleCriteria.newInstance(Restriction.eq("type_id", "1")));
		
		System.out.println( andCriteria.getQueryString());
		
//		Method queryMethod = MySqlDB.class.getDeclaredMethod("getSelectQuery", Criteria.class, Class.class);
//		queryMethod.setAccessible(true);
//		
//		String query = queryMethod.invoke(db, orCriteria, Video.class).toString();
//		System.out.println(query);
		
		List<Video> vids = db.find(videoCriteria, Video.class);
		for(Video vid: vids) {
			System.out.println("\nVIDEO\n" + vid);
		}
		
		assertTrue(true);
	}

	@Test
	public void testUpdate() throws Exception {
		vid.setGenre_id(4);
		vid.setType_id(3);
		
		db.update(vid);
		
		assertTrue(true);
	}

	@Test
	public void testDelete() throws Exception {
		vid.setVideo_id(28);
		db.delete(vid);
		
		assertTrue(true);
	}

}
