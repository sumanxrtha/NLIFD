package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//filer the unwanted noise from the collection
public class HelperClass {

    //    @SuppressWarnings({"rawtypes"})
    public static ArrayList<String> WordListFilter(Collection coll) {

        ArrayList<String> filteredList = new ArrayList<>();

        for (int i = 0; i < coll.size(); i++) {

            // / is for escaping the charater (
            String[] part = (((List) coll).get(i).toString().split("\\("));

            if (part[0].equals("det")) {

            } else if (part[0].equals("case")) {

            }
            else if (part[0].equals("attr")) {

            } else {
                filteredList.add((((List) coll).get(i).toString()));
            }

        }
        return filteredList;
    }
}
