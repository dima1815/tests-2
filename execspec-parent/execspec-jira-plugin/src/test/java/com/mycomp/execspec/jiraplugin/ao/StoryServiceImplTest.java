package com.mycomp.execspec.jiraplugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjects;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import junit.framework.Assert;
import net.java.ao.EntityManager;
import net.java.ao.test.jdbc.Data;
import net.java.ao.test.jdbc.DatabaseUpdater;
import net.java.ao.test.jdbc.Jdbc;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * TODO - add at least one line of java doc comment.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
@Ignore
@Jdbc(HsqlDbFileJdbcConfiguration.class)
@RunWith(ActiveObjectsJUnitRunner.class)
@Data(StoryServiceImplTest.StoryServiceImplTestDatabaseUpdater.class)
public class StoryServiceImplTest {

    private EntityManager entityManager;
    private ActiveObjects ao;
    private StoryService storyService;

    @Before
    public void prepare() {
        Assert.assertNotNull(entityManager);
        ao = new TestActiveObjects(entityManager);
//        storyService = new StoryServiceImpl(ao, null, null);
    }

    @Test
    public void test() {


//        List<StoryModel> all = storyService.all();
//        Assert.assertEquals(2, all.size());
//        StoryModel storyModel = all.get(0);
//        Assert.assertEquals(new Integer(1), storyModel.getId());

    }

    public static class StoryServiceImplTestDatabaseUpdater implements DatabaseUpdater {
        @Override
        public void update(EntityManager em) throws Exception {
            em.migrate(Story.class);

            final Story story = em.create(Story.class);
            story.setNarrative("Story description");
            story.save();

            final Story story2 = em.create(Story.class);
            story2.setNarrative("Story description 2");
            story2.save();
        }
    }
}
