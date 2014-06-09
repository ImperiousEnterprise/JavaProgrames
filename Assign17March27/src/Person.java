import java.io.Serializable;


/**
 * This class just represents a person.
 * It is not well documented, because it is just for a class
 * exercise.
 * @author Keith Frikken
 *
 */
public class Person implements Serializable {

	private String name;
	private int age;
	
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public Person() {
		this("Pat Doe",40);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String toString() {
		return name+ " " + age;
	}
	
	public boolean equals(Person other) {
		if (other == null)
			return false;
		return (other.age==age)&&(other.name.equals(name));
	}
	
}
