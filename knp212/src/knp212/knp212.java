package knp212;

import com.google.common.collect.ImmutableList;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
class Person {
    private String name;
    private int age;
}

public class knp212 {

	public static void main(String args[]) {

        ImmutableList<String> fruits = ImmutableList.of("Apple", "Banana", "Orange");
        System.out.println("Immutable List of fruits: " + fruits);

        String testString = "";
        if (Strings.isNullOrEmpty(testString)) {
            System.out.println("The string is null or empty.");
        } else {
            System.out.println("The string is not empty.");
        }

    }

}
