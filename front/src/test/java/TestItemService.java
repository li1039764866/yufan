import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yufan.bean.Item;
import org.yufan.exception.MyException;
import org.yufan.service.ItemService;

import java.io.IOException;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")*/
public class TestItemService {
    
    @Autowired
    private ItemService itemService;

    //@Test
    public void testItem() throws IOException, MyException {

        itemService.queryItemById(41L);

    }
    

}
