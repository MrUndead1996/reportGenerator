import com.texuna.reportGenerator.model.Column;
import com.texuna.reportGenerator.model.PageTemplate;
import com.texuna.reportGenerator.utils.ReportGeneratorUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
public class ReportGeneratorUtilsTest {

    @Test
    public void getCellHeightTest(){
        PageTemplate template = new PageTemplate();
        Column column1 = new Column();
        column1.setTitle("Title");
        column1.setWidth("10");
        Column column2 = new Column();
        column2.setTitle("Title");
        column2.setWidth("10");
        Column column3 = new Column();
        column3.setTitle("Title");
        column3.setWidth("5");
        template.getColumns().add(column1);
        template.getColumns().add(column2);
        template.getColumns().add(column3);
        List<String> data = new ArrayList<>();
        data.add("Title");
        data.add("TitleTitle");
        data.add("TitleTitleTitle");
        int cellHeight = ReportGeneratorUtils.getCellHeight(template,data);
        assertEquals(3,cellHeight);
    }
}
