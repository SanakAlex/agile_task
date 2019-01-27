package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HtmlElement {
    String id;
    List<String> classes;
    String href;
    String title;
    String onClick;
    String text;
    String xPath;

    public boolean isEqualsToOrigin(HtmlElement origin) {
        if (classes.size() == origin.classes.size() && classes.containsAll(origin.classes))
            return true;

        if (onClick.equals(origin.onClick))
            return true;
        return false;
    }
}
